package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AssignModelPermissions;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.*;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.StringBundler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component
public class AssignModelPermissionsImpl implements AssignModelPermissions {

	public <M extends AuditedModel> void addAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions, M m)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		serviceContext.setCompanyId(m.getCompanyId());
		serviceContext.setUserId(m.getUserId());

		serviceContext.setModelPermissions(modelPermissions);

		_resourceLocalService.updateModelResources(m, serviceContext);

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("added permissions {model:");
			sb.append(m.getModelClassName());
			sb.append(", primKey:");
			sb.append(m.getPrimaryKeyObj());
			sb.append("}");

			_log.debug(sb);
		}
	}

	public <M extends AuditedModel> void removeAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions, M m)
		throws PortalException {

		long companyId = m.getCompanyId();

		for (String roleName : modelPermissions.getRoleNames()) {
			Role role = _roleLocalService.fetchRole(companyId, roleName);

			if (role == null) {
				if (_log.isTraceEnabled()) {
					_log.trace("no role round {name:" + roleName + "}");
				}

				continue;
			}

			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					companyId, m.getModelClassName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(m.getPrimaryKeyObj()), role.getRoleId());

			if (resourcePermission == null) {
				if (_log.isTraceEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("no resourcePremission found {name:");
					sb.append(m.getModelClassName());
					sb.append(", scope: 4 (individual), primKey:");
					sb.append(m.getPrimaryKeyObj());
					sb.append("}");

					_log.trace(sb);
				}

				continue;
			}

			for (String actionId : modelPermissions.getActionIds(roleName)) {
				resourcePermission.removeResourceAction(actionId);
			}

			_resourcePermissionLocalService.updateResourcePermission(
				resourcePermission);
		}

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("removed permissions {model:");
			sb.append(m.getModelClassName());
			sb.append(", primKey:");
			sb.append(m.getPrimaryKeyObj());
			sb.append("}");

			_log.debug(sb);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssignModelPermissionsImpl.class);

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}