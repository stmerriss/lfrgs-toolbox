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

import java.util.List;

import com.liferay.gs.util.validator.InputValidator;
import org.apache.commons.csv.CSVRecord;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrew Betts
 */
@Component(
	immediate = true,
	property = "inputClass=java.lang.List<org.apache.commons.csv.CSVRecord>",
	service = InputValidator.class
)
public class CSVRecordListValidator implements InputValidator<List<CSVRecord>> {
}
