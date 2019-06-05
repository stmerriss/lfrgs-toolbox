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

import com.liferay.gs.importer.factory.ServiceFactory;
import com.liferay.gs.importer.parser.BOMExtractor;
import com.liferay.gs.importer.parser.BOMExtractorFactory;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Andrew Betts
 */
@Component(immediate = true, service = BOMExtractorFactory.class)
public class BOMExtractorFactoryImpl implements BOMExtractorFactory {

	@Override
	@SuppressWarnings("unchecked")
	public <D> BOMExtractor<D> getBOMExtractor(
		Class<D> dataClass, String extension) {

		return _serviceFactory.getService(() -> {
			StringBundler sb = new StringBundler(5);

			sb.append("(&(dataClass=");
			sb.append(dataClass.getName());
			sb.append(")(extension=");
			sb.append(extension);
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

	private ServiceFactory<BOMExtractor> _serviceFactory;

}