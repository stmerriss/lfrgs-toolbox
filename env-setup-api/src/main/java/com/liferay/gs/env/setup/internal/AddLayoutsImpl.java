package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AddLayouts;
import com.liferay.gs.env.setup.AssignModelPermissions;
import com.liferay.gs.env.setup.config.LayoutConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component
public class AddLayoutsImpl implements AddLayouts {

	public void addLayouts(
			Layout parent, Group group, LayoutConfig[] layoutConfigs,
			ModelPermissions addPermissions, ModelPermissions removePermissions)
		throws PortalException {

		long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		if (parent != null) {
			parentLayoutId = parent.getLayoutId();
		}

		for (LayoutConfig layoutConfig : layoutConfigs) {
			String friendlyURL = layoutConfig.getFriendlyURL();

			Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				group.getGroupId(), layoutConfig.getPrivateLayout(),
				friendlyURL);

			if (layout == null) {
				layout = _layoutLocalService.addLayout(
					group.getCreatorUserId(), group.getGroupId(),
					layoutConfig.getPrivateLayout(), parentLayoutId,
					layoutConfig.getName(), layoutConfig.getTitle(),
					layoutConfig.getDescription(), layoutConfig.getLayoutType(),
					layoutConfig.getHidden(), friendlyURL,
					new ServiceContext());
			}

			if (addPermissions != null) {
				_assignModelPermissions.addAuditedModelPermissions(
					layout.getGroupId(), addPermissions, layout);
			}

			if (removePermissions != null) {
				_assignModelPermissions.removeAuditedModelPermissions(
					layout.getGroupId(), removePermissions, layout);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("added page " + friendlyURL);
			}

			if (layoutConfig.getChildren() != null) {
				addLayouts(
					layout, group, layoutConfig.getChildren(), addPermissions,
					removePermissions);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("pages loaded");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(AddLayoutsImpl.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private AssignModelPermissions _assignModelPermissions;

}