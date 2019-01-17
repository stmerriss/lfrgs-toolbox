package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AddUsers;
import com.liferay.gs.env.setup.AssignSiteMembers;
import com.liferay.gs.env.setup.config.OrganizationConfig;
import com.liferay.gs.env.setup.config.SiteConfig;
import com.liferay.gs.env.setup.config.UserConfig;
import com.liferay.gs.env.setup.config.UserGroupConfig;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class AssignSiteMembersImpl implements AssignSiteMembers {

	@Override
	public void assignOrganizations(
		long companyId, SiteConfig siteConfig,
		OrganizationConfig[] organizationConfigs)
		throws PortalException {

		Group group = _groupLocalService.getFriendlyURLGroup(
			companyId, siteConfig.getGroupFriendlyURL());

		for (OrganizationConfig organizationConfig : organizationConfigs) {
			Organization organization =
				_organizationLocalService.getOrganization(
					companyId, organizationConfig.getOrganizationName());

			_groupLocalService.addOrganizationGroup(
				organization.getOrganizationId(), group.getGroupId());

			if (organizationConfig.getChildren() != null) {
				assignOrganizations(
					companyId, siteConfig, organizationConfig.getChildren());
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned organizations and group");
		}
	}

	@Override
	public void assignOrganizations(
		long companyId, SiteConfig[] siteConfigs,
		OrganizationConfig organizationConfig)
		throws PortalException {

		Organization organization =
			_organizationLocalService.getOrganization(
				companyId, organizationConfig.getOrganizationName());

		for (SiteConfig siteConfig : siteConfigs) {
			Group group = _groupLocalService.getFriendlyURLGroup(
				companyId, siteConfig.getGroupFriendlyURL());

			_groupLocalService.addOrganizationGroup(
				organization.getOrganizationId(), group.getGroupId());

			if (organizationConfig.getChildren() != null) {
				assignOrganizations(
					companyId, siteConfig, organizationConfig.getChildren());
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned organization and groups");
		}
	}

	@Override
	public void assignUsers(
		long companyId, SiteConfig siteConfig, UserConfig[] userConfigs)
		throws PortalException {

		Group group = _groupLocalService.getFriendlyURLGroup(
			companyId, siteConfig.getGroupFriendlyURL());

		for (UserConfig userConfig : userConfigs) {
			User user = AddUsers.fetchUser(
				companyId, userConfig, _userLocalService);

			_groupLocalService.addUserGroup(
				user.getUserId(), group.getGroupId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned users and group");
		}
	}

	@Override
	public void assignUsers(
		long companyId, SiteConfig[] siteConfigs, UserConfig userConfig)
		throws PortalException {

		User user = AddUsers.fetchUser(
			companyId, userConfig, _userLocalService);

		for (SiteConfig siteConfig: siteConfigs) {
			Group group = _groupLocalService.getFriendlyURLGroup(
				companyId, siteConfig.getGroupFriendlyURL());

			_groupLocalService.addUserGroup(
				user.getUserId(), group.getGroupId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned user and groups");
		}
	}

	@Override
	public void assignUserGroups(
		long companyId, SiteConfig siteConfig,
		UserGroupConfig[] userGroupConfigs)
		throws PortalException {

		Group group = _groupLocalService.getFriendlyURLGroup(
			companyId, siteConfig.getGroupFriendlyURL());

		for (UserGroupConfig userGroupConfig: userGroupConfigs) {
			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				companyId, userGroupConfig.getUserGroupName());

			_groupLocalService.addUserGroupGroup(
				userGroup.getUserGroupId(), group.getGroupId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned userGroups and group");
		}
	}

	@Override
	public void assignUserGroups(
		long companyId, SiteConfig[] siteConfigs,
		UserGroupConfig userGroupConfig)
		throws PortalException {

		UserGroup userGroup = _userGroupLocalService.getUserGroup(
			companyId, userGroupConfig.getUserGroupName());

		for (SiteConfig siteConfig: siteConfigs) {
			Group group = _groupLocalService.getFriendlyURLGroup(
				companyId, siteConfig.getGroupFriendlyURL());

			_groupLocalService.addUserGroupGroup(
				userGroup.getUserGroupId(), group.getGroupId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned userGroup and groups");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssignRolesImpl.class);

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;
}