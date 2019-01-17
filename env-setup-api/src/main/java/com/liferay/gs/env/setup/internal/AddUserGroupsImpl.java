package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AddUserGroups;
import com.liferay.gs.env.setup.config.UserGroupConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component
public class AddUserGroupsImpl implements AddUserGroups {

	@Override
	public void addUserGroups(
			long companyId, UserGroupConfig[] userGroupConfigs)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		for (UserGroupConfig userGroupConfig : userGroupConfigs) {
			UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
				companyId, userGroupConfig.getUserGroupName());

			if (userGroup == null) {
				userGroup = _userGroupLocalService.addUserGroup(
					companyGroup.getCreatorUserId(), companyId,
					userGroupConfig.getUserGroupName(),
					userGroupConfig.getDescription(),
					new ServiceContext());
			}

			if (_log.isDebugEnabled()) {
				_log.debug("added userGroup" + userGroup.getName());
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("userGroups loaded");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddUserGroupsImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}