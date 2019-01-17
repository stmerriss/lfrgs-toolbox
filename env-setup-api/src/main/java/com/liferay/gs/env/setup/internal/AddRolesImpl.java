package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AddRoles;
import com.liferay.gs.env.setup.config.RoleConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component
public class AddRolesImpl implements AddRoles {

	@Override
	public void addRoles(
			long companyId, RoleConfig[] roleConfigs)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		for (RoleConfig roleConfig : roleConfigs) {
			String className = Role.class.getName();

			long classPK = 0;

			Role role = _roleLocalService.fetchRole(
				companyId, roleConfig.getRoleName());

			if (role == null) {
				role = _roleLocalService.addRole(
					companyGroup.getCreatorUserId(), className, classPK,
					roleConfig.getRoleName(), roleConfig.getTitleMap(),
					roleConfig.getDescriptionMap(), roleConfig.getRoleType(),
					roleConfig.getSubType(), new ServiceContext());
			}

			if (_log.isDebugEnabled()) {
				_log.debug("added role" + role.getName());
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("roles loaded");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(AddRolesImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}