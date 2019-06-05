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

package com.liferay.gs.importer.implementation.validator;

import com.liferay.gs.importer.exception.CSVNoRecordsException;
import com.liferay.gs.importer.exception.MissingCSVHeaderException;
import com.liferay.gs.util.validator.ValidatorRule;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

/**
 * @author Andrew Betts
 */
public class CSVRuleGenerator {

	public List<ValidatorRule<List<String>>> getHeaderRules(
		String fileName, String[] headers) {

		if (ArrayUtil.isEmpty(headers)) {
			return null;
		}

		List<ValidatorRule<List<String>>> rules = new ArrayList<>(
			headers.length);

		for (String header : headers) {
			ValidatorRule<List<String>> rule =
				new ValidatorRule<List<String>>() {

					@Override
					public void onFailure(List<String> headers)
						throws PortalException {

						throw new MissingCSVHeaderException(fileName, header);
					}

					@Override
					public void onSuccess(List<String> headers)
						throws PortalException {
					}

					@Override
					public boolean test(List<String> headers) {
						return headers.contains(header);
					}

				};

			rules.add(rule);
		}

		return rules;
	}

	public List<ValidatorRule<List<CSVRecord>>> getRecordRules(
		String fileName) {

		List<ValidatorRule<List<CSVRecord>>> rules = new ArrayList<>();

		ValidatorRule<List<CSVRecord>> rule =
			new ValidatorRule<List<CSVRecord>>() {

				@Override
				public void onFailure(List<CSVRecord> records)
					throws PortalException {

					throw new CSVNoRecordsException(fileName);
				}

				@Override
				public void onSuccess(List<CSVRecord> records)
					throws PortalException {
				}

				@Override
				public boolean test(List<CSVRecord> records) {
					return !records.isEmpty();
				}

			};

		rules.add(rule);

		return rules;
	}

}

