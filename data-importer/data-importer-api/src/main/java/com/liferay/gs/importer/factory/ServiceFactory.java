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

package com.liferay.gs.importer.factory;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Supplier;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Andrew Betts
 */
public class ServiceFactory<S> {

	public void close() {
		if (!Objects.isNull(_serviceTrackerMap)) {
			_serviceTrackerMap.forEach((key, dist) -> {
				if (!Objects.isNull(dist)) {
					dist.close();
				}
			});

			_serviceTrackerMap = null;
		}

		if (!Objects.isNull(_bundleContext)) {
			_bundleContext = null;
		}
	}

	public S getService(Supplier<String> filterStringSupplier) {
		String filterString = filterStringSupplier.get();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"using filter string " + filterString + " to get service");
		}

		ServiceTracker<S, S> serviceTracker =
			_serviceTrackerMap.get(filterString);

		if (Objects.isNull(serviceTracker)) {
			try {
				Filter filter = _bundleContext.createFilter(filterString);

				serviceTracker = new ServiceTracker<>(
					_bundleContext, filter, null);
			}
			catch (InvalidSyntaxException ise) {
				if (_log.isDebugEnabled()) {
					_log.debug(ise, ise);
				}

				return null;
			}

			serviceTracker.open();

			_serviceTrackerMap.put(filterString, serviceTracker);
		}

		return serviceTracker.getService();
	}

	public void open(BundleContext bundleContext) {
		_bundleContext = bundleContext;
		_serviceTrackerMap = new ConcurrentHashMap<>();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceFactory.class);

	private BundleContext _bundleContext;
	private Map<String, ServiceTracker<S, S>> _serviceTrackerMap;

}