package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.OrganizationConfig;
import com.liferay.gs.env.setup.config.RoleConfig;
import com.liferay.gs.env.setup.config.UserConfig;
import com.liferay.gs.env.setup.config.UserGroupConfig;
import com.liferay.portal.kernel.exception.PortalException;

public interface AssignRoles {

	void assignOrganizations(
			long companyId, RoleConfig roleConfig,
			OrganizationConfig[] organizationConfigs)
		throws PortalException;

	void assignOrganizations(
			long companyId, RoleConfig[] roleConfigs,
			OrganizationConfig organizationConfig)
		throws PortalException;

	void assignUsers(
			long companyId, RoleConfig roleConfig, UserConfig[] userConfigs)
		throws PortalException;

	void assignUsers(
			long companyId, RoleConfig[] roleConfigs, UserConfig userConfig)
		throws PortalException;

	void assignUserGroups(
			long companyId, RoleConfig roleConfig,
			UserGroupConfig[] userGroupConfigs)
		throws PortalException;

	void assignUserGroups(
			long companyId, RoleConfig[] roleConfigs,
			UserGroupConfig userGroupConfig)
		throws PortalException;

}