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

package com.liferay.gs.importer.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrew Betts
 */
public class DataImporterFileFormatException extends PortalException {

	public DataImporterFileFormatException(
		String fileName, String... validFormats) {

		super(
			fileName + " must be a valid format [" +
				StringUtil.merge(validFormats) + "]");

		_fileName = fileName;
		_validFormats = validFormats;
	}

	public String getFileName() {
		return _fileName;
	}

	public String[] getValidFormats() {
		return _validFormats;
	}

	private String _fileName;
	private String[] _validFormats;

}
