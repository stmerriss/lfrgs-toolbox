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

package com.liferay.gs.importer.dataImporter;

import com.liferay.gs.importer.exception.DataImporterFileFormatException;
import com.liferay.gs.util.accessor.StringPairAccessor;
import com.liferay.gs.util.dto.Pair;
import com.liferay.gs.util.validator.InputValidator;
import com.liferay.gs.util.validator.StringListValidator;
import com.liferay.gs.util.validator.ValidatorRule;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.gs.importer.implementation.validator.CSVRecordListValidator;
import com.liferay.gs.importer.implementation.validator.CSVRuleGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author Andrew Betts
 */
public abstract class CSVDataImporter<D> extends BaseDataImporter<D, File> {

	public static final Charset[] CHARSETS = {
		StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1,
		StandardCharsets.US_ASCII, StandardCharsets.UTF_16
	};

	public static final CSVRuleGenerator csvRuleGenerator =
		new CSVRuleGenerator();

	@Override
	protected void doValidateData(File input, ServiceContext serviceContext)
		throws PortalException {

		String fileName = GetterUtil.getString(
			serviceContext.getAttribute("fileName"), null);

		if (Objects.isNull(fileName)) {
			fileName = input.getName();
		}

		List<ValidatorRule<List<String>>> headerRules =
			csvRuleGenerator.getHeaderRules(fileName, getRequiredHeaders());

		List<ValidatorRule<List<CSVRecord>>> recordRules =
			csvRuleGenerator.getRecordRules(fileName);

		List<String> headers = ListUtil.toList(
			getHeaders(input), new StringPairAccessor());

		_headerValidator.validate(headers, headerRules);

		List<CSVRecord> records = getCSVRecords(input);

		_recordValidator.validate(records, recordRules);
	}

	protected List<CSVRecord> getCSVRecords(File file)
		throws DataImporterFileFormatException {

		return getCSVRecords(file, CHARSETS);
	}

	protected List<CSVRecord> getCSVRecords(File file, Charset[] charsets)
		throws DataImporterFileFormatException {

		for (Charset charset : charsets) {
			try {
				return _getRecords(file, charset);
			}
			catch (IOException ioe) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioe, ioe);
				}
			}
		}

		if (_log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("No records found for file[");
			sb.append(file.getName());
			sb.append("] and charsets[");
			sb.append(StringUtil.merge(charsets));
			sb.append(StringPool.CLOSE_BRACKET);

			_log.warn(sb.toString());
		}

		return Collections.emptyList();
	}

	protected List<Pair<String>> getHeaders(File file) throws PortalException {
		List<Pair<String>> headers;

		try {
			try (FileReader fileReader = new FileReader(file);
				 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

				Stream<String> stream = bufferedReader.lines();

				stream = stream.limit(1);

				headers = stream.map(
					line -> line.split(",")).flatMap(Stream::of).map(
						header -> new Pair<>(
							header,
							header.replaceAll("[^A-Za-z0-9]", "")
						)).collect(Collectors.toList());
			}

			return headers;
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	protected abstract String[] getRequiredHeaders();

	protected boolean isNumeric(String columnValue) {
		try {
			Long.parseLong(columnValue);

			return true;
		}
		catch (NumberFormatException nfe) {
			return false;
		}
	}

	protected boolean validRecordSize(int validSize, CSVRecord record)
		throws PortalException {

		if (record.size() != validSize) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid record size: " + record);

				return false;
			}
		}

		return true;
	}

	private List<CSVRecord> _getRecords(File file, Charset charset)
		throws IOException {

		CSVParser csvParser =
			CSVParser.parse(
				file, charset, CSVFormat.EXCEL.withHeader());

		List<CSVRecord> records = csvParser.getRecords();

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler();

			sb.append("Found ");
			sb.append(records.size());
			sb.append(" records to import in file[");
			sb.append(file.getName());
			sb.append("] with charset[");
			sb.append(charset);
			sb.append(StringPool.CLOSE_BRACKET);

			_log.info(sb.toString());
		}

		return records;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSVDataImporter.class);

	private static final InputValidator<List<String>> _headerValidator =
		new StringListValidator();
	private static final InputValidator<List<CSVRecord>> _recordValidator =
		new CSVRecordListValidator();

}