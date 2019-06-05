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

package com.liferay.gs.importer.web.commands.action;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.gs.importer.web.constants.DataImporterPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.gs.importer.dataImporter.DataImporter;
import com.liferay.gs.importer.dataImporter.DataImporterFactory;

import java.io.File;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jared Domio
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DataImporterPortletKeys.DATA_IMPORTER,
		"mvc.command.name=/import/products"
	},
	service = MVCActionCommand.class
)
public class ImportCSVMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) {
		try {
			UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);

			File file = uploadPortletRequest.getFile("csv-file");

			String fileExtension = FileUtil.getExtension(file.getName());

			if (!fileExtension.equals(_FILE_EXTENSION_CSV)) {
				throw new FileExtensionException(
					"Received file extension was " + fileExtension + " but required file extension is csv");
			}

			DataImporter<CPDefinition, File> commerceProductImporter =
				_dataImporterFactory.getDataImporter(
					CPDefinition.class, File.class, "shopify-csv-importer");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);

			ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			serviceContext.setTimeZone(themeDisplay.getTimeZone());

			if (Validator.isNotNull(commerceProductImporter)) {
				commerceProductImporter.importData(file, serviceContext);
			}
			else {
				_log.error("commerceProductImporter is null");
			}
		}
		catch (Exception e) {
			_log.error("Unable to import csv data", e);

			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	private static final String _FILE_EXTENSION_CSV = "csv";

	private static Log _log = LogFactoryUtil.getLog(ImportCSVMVCActionCommand.class);

	@Reference
	private DataImporterFactory _dataImporterFactory;

	@Reference
	private UserLocalService _userLocalService;

}
