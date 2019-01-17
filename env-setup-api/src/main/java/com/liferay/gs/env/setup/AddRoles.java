package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.RoleConfig;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrew Betts
 */
public interface AddRoles {

	public void addRoles(
		long companyId, RoleConfig[] roleConfigs)
		throws PortalException;

}