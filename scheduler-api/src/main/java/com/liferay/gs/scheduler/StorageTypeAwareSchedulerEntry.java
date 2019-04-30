package com.liferay.gs.scheduler;

import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.StorageTypeAware;

/**
 * @author Andrew Betts
 */
public class StorageTypeAwareSchedulerEntry
	extends SchedulerEntryImpl implements StorageTypeAware {

	@Override
	public StorageType getStorageType() {
		return _storageType;
	}

	public void setStorageType(StorageType storageType) {
		_storageType = storageType;
	}

	private StorageType _storageType;

}