package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AssignUserGroupGroupRoles;

import com.liferay.gs.env.setup.config.RoleConfig;
import com.liferay.gs.env.setup.config.UserGroupConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.StringBundler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component
@SuppressWarnings("Duplicates")
public class AssignUserGroupGroupRolesImpl
	implements AssignUserGroupGroupRoles {

	@Override
	public void assignUserGroupGroupRoles(
			long companyId, Group group, UserGroupConfig[] userGroupConfigs,
			RoleConfig roleConfig)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			companyId, roleConfig.getRoleName());

		for (UserGroupConfig userGroupConfig : userGroupConfigs) {
			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				companyId, userGroupConfig.getUserGroupName());

			assignUserGroupGroupRole(companyId, group, userGroup, role);
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned userGroups group and role");
		}
	}

	@Override
	public void assignUserGroupGroupRoles(
			long companyId, Group group, UserGroupConfig userGroupConfig,
			RoleConfig[] roleConfigs)
		throws PortalException {

		UserGroup userGroup = _userGroupLocalService.getUserGroup(
			companyId, userGroupConfig.getUserGroupName());

		for (RoleConfig roleConfig: roleConfigs) {
			Role role = _roleLocalService.getRole(
				companyId, roleConfig.getRoleName());

			assignUserGroupGroupRole(companyId, group, userGroup, role);
		}

		if (_log.isInfoEnabled()) {
			_log.info("assigned userGroup group and roles");
		}
	}

	@Override
	public void assignUserGroupGroupRole(
		long companyId, Group group, UserGroup userGroup, Role role) {

		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroup.getUserGroupId(), group.getGroupId(),
			new long[] {role.getRoleId()});

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(7);

			sb.append("added UserGroupGroupRole {userGroupId:");
			sb.append(userGroup.getUserGroupId());
			sb.append(", groupId:");
			sb.append(group.getGroupId());
			sb.append(", roleId:");
			sb.append(role.getRoleId());
			sb.append("}");

			_log.debug(sb.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssignUserGroupGroupRolesImpl.class);

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}