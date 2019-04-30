package com.liferay.gs.scheduler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;
import java.util.Map;

/**
 * @author Andrew Betts
 */
public abstract class BaseSchedulerMessageListener extends BaseMessageListener {

	public boolean isEnabled() {
		if (!_enabled) {
			return false;
		}

		return true;
	}

	protected void activate(
		String jobName, String eventListenerClass,
		SchedulerEngineHelper schedulerEngineHelper,
		TriggerFactory triggerFactory, Map<String, Object> properties) {

		_jobName = jobName;
		_eventListenerClass = eventListenerClass;
		_schedulerEngineHelper = schedulerEngineHelper;
		_triggerFactory = triggerFactory;

		setProperties(properties);

		registerSchedulerJob();
	}

	protected void deactivate() {
		unregisterSchedulerJob();

		_jobName = null;
		_eventListenerClass = null;
		_schedulerEngineHelper = null;
		_triggerFactory = null;
		_schedulerDisplayName = null;
		_cronExpression = null;
		_enabled = false;
		_storageType = null;
	}

	protected void modified(Map<String, Object> properties) {
		unregisterSchedulerJob();

		setProperties(properties);

		registerSchedulerJob();
	}

	protected void registerSchedulerJob() {
		try {
			Trigger jobTrigger = _triggerFactory.createTrigger(
				_jobName, _GROUP_NAME, new Date(), null, _cronExpression);

			StorageTypeAwareSchedulerEntry schedulerEntryImpl =
				new StorageTypeAwareSchedulerEntry();

			schedulerEntryImpl.setDescription(
				"schedulerEntry for " + _jobName + _GROUP_NAME);
			schedulerEntryImpl.setEventListenerClass(_eventListenerClass);
			schedulerEntryImpl.setTrigger(jobTrigger);
			schedulerEntryImpl.setStorageType(_storageType);

			_schedulerEngineHelper.register(
				this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);

			if (_log.isInfoEnabled()) {
				_log.info("Registered: " + _schedulerDisplayName);
			}
		}
		catch (Exception e) {
			_log.error("Unable to register " + _schedulerDisplayName, e);
		}
	}

	protected void setProperties(Map<String, Object> properties) {
		_setCronExpression(properties);
		_setEnabled(properties);
		_setStorageType(properties);
	}

	protected void unregisterSchedulerJob() {
		try {
			_schedulerEngineHelper.unregister(this);

			if (_log.isInfoEnabled()) {
				_log.info("Unregistered: " + _schedulerDisplayName);
			}
		}
		catch (Exception e) {
			_log.error("Unable to unregister " + _schedulerDisplayName, e);
		}
	}

	private void _setCronExpression(Map<String, Object> properties) {
		_cronExpression = GetterUtil.getString(
			properties.get("cronExpression"), _DEFAULT_CRON_EXPRESSION);

		if (_log.isDebugEnabled()) {
			_log.debug("cronExpression: " + _cronExpression);
		}
	}

	private void _setEnabled(Map<String, Object> properties) {
		_enabled = GetterUtil.getBoolean(properties.get("enabled"));

		if (_log.isDebugEnabled()) {
			_log.debug("enabled:" + _enabled);
		}
	}

	private void _setStorageType(Map<String, Object> properties) {
		String storageTypeName = GetterUtil.getString(
			properties.get("storageType"));

		switch (storageTypeName) {
			case "MEMORY":
				_storageType = StorageType.MEMORY;

				break;

			case "PERSISTED" :
				_storageType = StorageType.PERSISTED;

				break;

			default:
				_storageType = StorageType.MEMORY_CLUSTERED;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("storageType:" + _storageType);
		}
	}

	private static final String _DEFAULT_CRON_EXPRESSION = "0 0 0 * * ?";

	private static final String _GROUP_NAME = "fhlb_atl_scheduled_tasks";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSchedulerMessageListener.class);

	private String _cronExpression;
	private boolean _enabled;
	private String _eventListenerClass;
	private String _jobName;
	private String _schedulerDisplayName;
	private SchedulerEngineHelper _schedulerEngineHelper;
	private StorageType _storageType;
	private TriggerFactory _triggerFactory;

}