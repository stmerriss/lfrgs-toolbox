package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AddUsers;
import com.liferay.gs.env.setup.config.UserConfig;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component
public class AddUsersImpl implements AddUsers {

	@Override
	public void addUsers(long companyId, UserConfig[] userConfigs)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		for (UserConfig userConfig : userConfigs) {
			User user = AddUsers.fetchUser(
				companyId, userConfig, _userLocalService);

			if (user == null) {
				user = _userLocalService.addUser(
					companyGroup.getCreatorUserId(), companyId,
					userConfig.getAutoPassword(), userConfig.getPassword(),
					userConfig.getPassword(), userConfig.getAutoScreenName(),
					userConfig.getScreenName(), userConfig.getEmailAddress(),
					userConfig.getFacebookId(), userConfig.getOpenId(),
					userConfig.getUserLocale(), userConfig.getFirstName(),
					userConfig.getMiddleName(), userConfig.getLastName(),
					0, 0, false, 1, 1, 1970, userConfig.getJobTitle(),
					new long[0], new long[0], new long[0], new long[0], false,
					new ServiceContext());

				if (_log.isDebugEnabled()) {
					_log.debug("added user " + user.getLogin());
				}
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("users loaded");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(AddUsersImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}