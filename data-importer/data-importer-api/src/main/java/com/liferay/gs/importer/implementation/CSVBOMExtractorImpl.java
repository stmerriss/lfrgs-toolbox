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

package com.liferay.gs.importer.implementation;

import com.liferay.gs.importer.parser.BOMExtractor;
import com.liferay.gs.importer.parser.BaseStringBOMExtractor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ashish Dadhich
 * @author Swaran Singh
 * @author Shane Merriss
 */
@Component(
	immediate = true,
	property = {
		"dataClass=java.lang.String",
		"extension=csv"
	},
	service = BOMExtractor.class
)
public class CSVBOMExtractorImpl extends BaseStringBOMExtractor {

	@Override
	public List<String> extractColumn(
			File file, String... headers)
		throws IOException, PortalException {

		List<String> columnList = new ArrayList<>();

		CSVParser csvParser =
			CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.DEFAULT);

		int headerIndex = -1;

		for (CSVRecord csvRecord : csvParser) {
			if (headerIndex != -1) {
				String cellValue = csvRecord.get(headerIndex);

				Matcher skuBlacklistMather = BaseStringBOMExtractor.valueBlacklistPattern.matcher(
					cellValue);

				if (Validator.isNotNull(cellValue) && !skuBlacklistMather.find()) {
					columnList.add(cellValue);
				}
			}
			else {
				for (int i = 0; i < csvRecord.size(); i++) {
					String csvValue = StringUtil.trim(csvRecord.get(i));

					for (String header : headers) {
						if (header.equalsIgnoreCase(csvValue)) {
							headerIndex = i;

							break;
						}
					}

					if (headerIndex > -1) {
						break;
					}
				}
			}
		}

		if (headerIndex == -1) {
			_log.debug(StringUtil.merge(headers) + " not found");

			return null;
		}

		return columnList;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSVBOMExtractorImpl.class);

}
