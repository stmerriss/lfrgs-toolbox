package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AddUsers;
import com.liferay.gs.env.setup.AssignRoles;
import com.liferay.gs.env.setup.config.OrganizationConfig;
import com.liferay.gs.env.setup.config.RoleConfig;
import com.liferay.gs.env.setup.config.UserConfig;
import com.liferay.gs.env.setup.config.UserGroupConfig;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component
public class AssignRolesImpl implements AssignRoles {

	@Override
	public void assignOrganizations(
			long companyId, RoleConfig roleConfig,
			OrganizationConfig[] organizationConfigs)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			companyId, roleConfig.getRoleName());

		for (OrganizationConfig organizationConfig : organizationConfigs) {
			Organization organization =
				_organizationLocalService.getOrganization(
					companyId, organizationConfig.getOrganizationName());

			_roleLocalService.addGroupRole(
				organization.getGroupId(), role.getRoleId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned organizations and role");
		}
	}

	@Override
	public void assignOrganizations(
			long companyId, RoleConfig[] roleConfigs,
			OrganizationConfig organizationConfig)
		throws PortalException {

		Organization organization =
			_organizationLocalService.getOrganization(
				companyId, organizationConfig.getOrganizationName());

		for (RoleConfig roleConfig : roleConfigs) {
			Role role = _roleLocalService.getRole(
				companyId, roleConfig.getRoleName());

			_roleLocalService.addGroupRole(
				organization.getGroupId(), role.getRoleId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned organization and roles");
		}
	}

	@Override
	public void assignUsers(
			long companyId, RoleConfig roleConfig, UserConfig[] userConfigs)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			companyId, roleConfig.getRoleName());

		for (UserConfig userConfig : userConfigs) {
			User user = AddUsers.fetchUser(
				companyId, userConfig, _userLocalService);

			_roleLocalService.addUserRole(user.getUserId(), role.getRoleId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned users and role");
		}
	}

	@Override
	public void assignUsers(
			long companyId, RoleConfig[] roleConfigs, UserConfig userConfig)
		throws PortalException {

		User user = AddUsers.fetchUser(
			companyId, userConfig, _userLocalService);

		for (RoleConfig roleConfig : roleConfigs) {
			Role role = _roleLocalService.getRole(
				companyId, roleConfig.getRoleName());

			_roleLocalService.addUserRole(user.getUserId(), role.getRoleId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned user and roles");
		}
	}

	@Override
	public void assignUserGroups(
		long companyId, RoleConfig roleConfig,
		UserGroupConfig[] userGroupConfigs)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			companyId, roleConfig.getRoleName());

		for (UserGroupConfig userGroupConfig: userGroupConfigs) {
			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				companyId, userGroupConfig.getUserGroupName());

			_roleLocalService.addGroupRole(
				userGroup.getGroupId(), role.getRoleId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned users and role");
		}
	}

	@Override
	public void assignUserGroups(
		long companyId, RoleConfig[] roleConfigs,
		UserGroupConfig userGroupConfig)
		throws PortalException {

		UserGroup userGroup = _userGroupLocalService.getUserGroup(
			companyId, userGroupConfig.getUserGroupName());

		for (RoleConfig roleConfig : roleConfigs) {
			Role role = _roleLocalService.getRole(
				companyId, roleConfig.getRoleName());

			_roleLocalService.addGroupRole(
				userGroup.getGroupId(), role.getRoleId());
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned user and roles");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssignRolesImpl.class);

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}