package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.UserConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

/**
 * @author Andrew Betts
 */
public interface AddUsers {

	enum Find {
		BY_CONTACT_ID,
		BY_EMAIL_ADDRESS,
		BY_FACEBOOK_ID,
		BY_GOOGLE_USER_ID,
		BY_OPEN_ID,
		BY_SCREEN_NAME,
		BY_USER_ID
	}

	static User fetchUser(
		long companyId, UserConfig userConfig,
		UserLocalService userLocalService) {

		User user = null;

		switch (userConfig.getFindBy()) {
			case BY_CONTACT_ID:
				user = userLocalService.fetchUserByContactId(
					userConfig.getContactId());

				break;

			case BY_EMAIL_ADDRESS:
				user = userLocalService.fetchUserByEmailAddress(
					companyId, userConfig.getEmailAddress());

				break;

			case BY_FACEBOOK_ID:
				user = userLocalService.fetchUserByFacebookId(
					companyId, userConfig.getFacebookId());

				break;

			case BY_GOOGLE_USER_ID:
				user = userLocalService.fetchUserByGoogleUserId(
					companyId, userConfig.getGoogleUserId());

				break;

			case BY_OPEN_ID:
				user = userLocalService.fetchUserByOpenId(
					companyId, userConfig.getOpenId());

				break;

			case BY_SCREEN_NAME:
				user = userLocalService.fetchUserByScreenName(
					companyId, userConfig.getScreenName());

				break;

			case BY_USER_ID:
				user = userLocalService.fetchUserById(userConfig.getId());
		}

		return user;
	}

	void addUsers(long companyId, UserConfig[] userConfigs)
		throws PortalException;

}
