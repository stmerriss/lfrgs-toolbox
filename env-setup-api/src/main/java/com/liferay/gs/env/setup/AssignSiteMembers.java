package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.OrganizationConfig;
import com.liferay.gs.env.setup.config.SiteConfig;
import com.liferay.gs.env.setup.config.UserConfig;
import com.liferay.gs.env.setup.config.UserGroupConfig;
import com.liferay.portal.kernel.exception.PortalException;

public interface AssignSiteMembers {
	void assignOrganizations(
		long companyId, SiteConfig siteConfig,
		OrganizationConfig[] organizationConfigs)
		throws PortalException;

	void assignOrganizations(
		long companyId, SiteConfig[] siteConfigs,
		OrganizationConfig organizationConfig)
		throws PortalException;

	void assignUsers(
		long companyId, SiteConfig siteConfig, UserConfig[] userConfigs)
		throws PortalException;

	void assignUsers(
		long companyId, SiteConfig[] siteConfigs, UserConfig userConfig)
		throws PortalException;

	void assignUserGroups(
		long companyId, SiteConfig siteConfig,
		UserGroupConfig[] userGroupConfigs)
		throws PortalException;

	void assignUserGroups(
		long companyId, SiteConfig[] siteConfigs,
		UserGroupConfig userGroupConfig)
		throws PortalException;
}