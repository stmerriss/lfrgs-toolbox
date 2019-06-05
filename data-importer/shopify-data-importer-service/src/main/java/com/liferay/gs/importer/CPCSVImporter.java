/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.gs.importer;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLocalization;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.gs.importer.enums.ProductCSVHeader;
import com.liferay.gs.importer.enums.ProductTypes;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.gs.importer.dataImporter.CSVDataImporter;
import com.liferay.gs.importer.dataImporter.DataImporter;

import java.io.File;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.csv.CSVRecord;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jared Domio
 */
@Component(
	immediate = true,
	property = {
		"dataClass=com.liferay.commerce.product.model.CPDefinition",
		"inputClass=java.io.File",
		"provider=shopify-csv-importer"
	},
	service = DataImporter.class
)
public class CPCSVImporter extends CSVDataImporter<CPDefinition> {

	@Override
	protected List<CPDefinition> doImportData(File file, ServiceContext serviceContext) throws PortalException {

		List<CSVRecord> records = getCSVRecords(file);

		List<CPDefinition> cpDefinitions = new ArrayList<>(records.size());

		for (CSVRecord record : records) {
			String handle = record.get(ProductCSVHeader.HANDLE);

			if (Validator.isNull(handle)) {
				continue;
			}

			CPDefinition cpDefinition =
				_cpDefinitionLocalService.fetchCPDefinitionByCProductExternalReferenceCode(serviceContext.getCompanyId(), handle);

			if (Objects.isNull(cpDefinition)) {
				cpDefinition = _addCPDefinition(serviceContext, record);
			}

			_importProductImage(serviceContext, record, cpDefinition);

			cpDefinitions.add(cpDefinition);
		}

		return cpDefinitions;
	}

	protected String[] getRequiredHeaders() {
		return null;
	}

	private CPDefinition _addCPDefinition(ServiceContext serviceContext, CSVRecord record) {
		Locale locale = serviceContext.getLocale();

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(serviceContext.getTimeZone());

		displayCalendar.add(Calendar.YEAR, -1);

		String handle = record.get(ProductCSVHeader.HANDLE);
		String title = record.get(ProductCSVHeader.TITLE);

		String sku = record.get(ProductCSVHeader.VARIANT_SKU);

		Map<Locale, String> nameMap =
			Collections.singletonMap(locale, record.get(ProductCSVHeader.TITLE));

		Map<Locale, String> descriptionMap =
			Collections.singletonMap(locale, record.get(ProductCSVHeader.BODY));

		Map<Locale, String> urlTitleMap =
			Collections.singletonMap(locale, record.get(ProductCSVHeader.HANDLE));

		Map<Locale, String> metaTitleMap =
			_getLocalizedValue(record, locale, ProductCSVHeader.SEO_TITLE, "metaTitle");

		Map<Locale, String> metaDescriptionMap =
			_getLocalizedValue(record, locale, ProductCSVHeader.SEO_DESCRIPTION, "metaDescription");

		String type = _getProductTypeName(record);

		boolean published = GetterUtil.getBoolean(record.get(ProductCSVHeader.PUBLISHED));

		CPDefinition definition;

		try {
			definition = _cpDefinitionLocalService.addCPDefinition(
				nameMap, null, descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap, null, type, true, true,
				false, false, 0, 0, 0, 0, 0, 0, true, false, null, published, displayCalendar.get(Calendar.MONTH),
				displayCalendar.get(Calendar.DAY_OF_MONTH), displayCalendar.get(Calendar.YEAR),
				displayCalendar.get(Calendar.HOUR), displayCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, sku,
				handle, serviceContext);
		}
		catch (PortalException pe) {
			_log.error("Unable to import product [handle:title]: [" + handle + ":" + title + "]", pe);

			return null;
		}

		return definition;
	}

	private DLFolder _createImageFolder(ServiceContext serviceContext) throws PortalException {

		long groupId = serviceContext.getScopeGroupId();
		long userId = serviceContext.getUserId();

		DLFolder folder = _dlFolderLocalService.fetchFolder(groupId, groupId, _DL_FOLDER_NAME);

		if (Objects.isNull(folder)) {
			folder = _dlFolderLocalService.addFolder(
				userId, groupId, groupId, false, 0L, _DL_FOLDER_NAME, null, false, serviceContext);
		}

		return folder;
	}

	private Map<Locale, String> _getLocalizedValue(
		CSVRecord record, Locale locale, ProductCSVHeader columnHeader, String fieldName) {

		Map<Locale, String> metaTitleMap = null;

		String metaTitle = record.get(columnHeader);

		int maxMetaTitleLength = ModelHintsUtil.getMaxLength(
			CPDefinitionLocalization.class.getName(), fieldName);

		if (metaTitle.length() < maxMetaTitleLength) {
			metaTitleMap = Collections.singletonMap(
				locale, record.get(columnHeader));
		}

		return metaTitleMap;
	}

	private String _getProductTypeName(CSVRecord record) {
		boolean giftCard = GetterUtil.getBoolean(record.get(ProductCSVHeader.GIFT_CARD));

		if (giftCard) {
			return ProductTypes.VIRTUAL.toString();
		}

		return ProductTypes.SIMPLE.toString();
	}

	private CPAttachmentFileEntry _importProductImage(
		ServiceContext serviceContext, CSVRecord record, CPDefinition cpDefinition) {

		String imageSrc = record.get(ProductCSVHeader.IMAGE_SRC);

		int imagePos = GetterUtil.getInteger(record.get(ProductCSVHeader.IMAGE_POSITION));

		if (imageSrc.isEmpty()) {
			return null;
		}

		URL url = null;

		try {
			url = new URL(imageSrc);
		}
		catch (MalformedURLException murle) {
			_log.error("Malformed URL: " + url, murle);

			return null;
		}

		long userId = serviceContext.getUserId();

		long groupId = serviceContext.getScopeGroupId();

		long classNameId = _portal.getClassNameId(CPDefinition.class.getName());

		long classPK = cpDefinition.getPrimaryKey();

		DLFolder folder = _dlFolderLocalService.fetchFolder(groupId, 0L, _DL_FOLDER_NAME);

		try {
			if (Objects.isNull(folder)) {
				folder = _createImageFolder(serviceContext);
			}
		}
		catch (PortalException pe) {
			_log.error("Unable to create image folder", pe);

			return null;
		}

		String sourceFileName =
			imageSrc.substring(imageSrc.lastIndexOf(StringPool.SLASH) + 1, imageSrc.lastIndexOf(StringPool.QUESTION));

		String mimeType = MimeTypesUtil.getContentType(sourceFileName);

		String title = sourceFileName.substring(0, sourceFileName.lastIndexOf(StringPool.PERIOD));

		String description = null;

		String changeLog = null;

		FileEntry fileEntry;

		try {
			InputStream imageInputStream = url.openStream();

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchFileEntry(groupId, folder.getFolderId(), title);

			if (Validator.isNull(dlFileEntry)) {
				_log.debug("Downloading image: " + url);

				fileEntry =
					_dlAppLocalService.addFileEntry(userId, groupId, folder.getFolderId(), sourceFileName, mimeType,
						title, description, changeLog, imageInputStream, 0, serviceContext);
			}
			else {
				fileEntry = _dlAppLocalService.getFileEntry(dlFileEntry.getFileEntryId());
			}

		}
		catch (Exception pe) {
			_log.error("Unable to upload file to document library: [" + url + "]");

			return null;
		}

		Map<Locale, String> titleMap = Collections.singletonMap(serviceContext.getLocale(), sourceFileName);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(serviceContext.getTimeZone());

		displayCalendar.add(Calendar.YEAR, -1);

		try {
			return _cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
				classNameId, classPK, fileEntry.getFileEntryId(), displayCalendar.get(Calendar.MONTH),
				displayCalendar.get(Calendar.DAY_OF_MONTH), displayCalendar.get(Calendar.YEAR),
				displayCalendar.get(Calendar.HOUR), displayCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, titleMap,
				null, imagePos, CPAttachmentFileEntryConstants.TYPE_IMAGE, serviceContext);
		}
		catch (PortalException pe) {
			_log.error("Unable to add CP Attachment file entry", pe);

			return null;
		}
	}

	private static final String _DL_FOLDER_NAME = "Product Images";

	@Reference
	private CPAttachmentFileEntryLocalService _cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	private Log _log = LogFactoryUtil.getLog(CPCSVImporter.class);

	@Reference
	private Portal _portal;

}

