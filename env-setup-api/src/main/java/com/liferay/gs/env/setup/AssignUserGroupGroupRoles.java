package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.RoleConfig;
import com.liferay.gs.env.setup.config.UserGroupConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;

/**
 * @author Andrew Betts
 */
public interface AssignUserGroupGroupRoles {

	void assignUserGroupGroupRoles(
			long companyId, Group group, UserGroupConfig[] userGroupConfigs,
			RoleConfig roleConfig)
		throws PortalException;

	void assignUserGroupGroupRoles(
			long companyId, Group group, UserGroupConfig userGroupConfig,
			RoleConfig[] roleConfigs)
		throws PortalException;

	void assignUserGroupGroupRole(
		long companyId, Group group, UserGroup userGroup, Role role);

}