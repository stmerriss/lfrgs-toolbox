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

import com.liferay.gs.util.log.ElapsedTimeLogger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Andrew Betts
 */
public abstract class BaseDataImporter<D, I> implements DataImporter<D, I> {

	@Override
	public List<D> importData(I input, ServiceContext serviceContext)
		throws PortalException {

		ElapsedTimeLogger elapsedTimeLogger = new ElapsedTimeLogger(_log);

		List<D> data = doImportData(input, serviceContext);

		Supplier<String> logMessageSupplier = () -> {
			String logMessage = getClass().getName();

			if (!data.isEmpty()) {
				D d = data.get(0);

				logMessage = "Importing " + d.getClass();
			}

			return logMessage;
		};

		elapsedTimeLogger.log(logMessageSupplier);

		return data;
	}

	@Override
	public void validateData(I input, ServiceContext serviceContext)
		throws PortalException {

		doValidateData(input, serviceContext);
	}

	protected abstract List<D> doImportData(
			I input, ServiceContext serviceContext)
		throws PortalException;

	protected abstract void doValidateData(
			I input, ServiceContext serviceContext)
		throws PortalException;

	private static Log _log = LogFactoryUtil.getLog(BaseDataImporter.class);

}
