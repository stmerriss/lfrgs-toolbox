package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.UserGroupConfig;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrew Betts
 */
public interface AddUserGroups {

	public void addUserGroups(
		long companyId, UserGroupConfig[] userGroupConfigs)
		throws PortalException;

}