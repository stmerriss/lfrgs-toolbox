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

import com.liferay.gs.importer.dataImporter.DataImporter;
import com.liferay.gs.importer.factory.ServiceFactory;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.gs.importer.dataImporter.DataImporterFactory;

import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Andrew Betts
 */
@Component(immediate = true, service = DataImporterFactory.class)
public class DataImporterFactoryImpl implements DataImporterFactory {

	@Override
	@SuppressWarnings("unchecked")
	public <D, I> DataImporter<D, I> getDataImporter(
		Class<D> dataClass, Class<I> inputClass, String provider) {

		return _serviceFactory.getService(() -> {
			StringBundler sb = new StringBundler(7);

			sb.append("(&(dataClass=");
			sb.append(dataClass.getName());
			sb.append(")(inputClass=");
			sb.append(inputClass.getName());
			sb.append(")(provider=");
			sb.append(provider);
			sb.append("))");

			return sb.toString();
		});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceFactory = new ServiceFactory<>();

		_serviceFactory.open(bundleContext);
	}

	@Deactivate
	protected void deactivate() {
		if (!Objects.isNull(_serviceFactory)) {
			_serviceFactory.close();
		}

		_serviceFactory = null;
	}

	private ServiceFactory<DataImporter> _serviceFactory;

}
