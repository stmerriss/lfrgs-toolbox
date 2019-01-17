package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.LayoutConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.permission.ModelPermissions;

/**
 * @author Andrew Betts
 */

public interface AddLayouts {

	void addLayouts(
			Layout parent, Group group, LayoutConfig[] layoutConfigs,
			ModelPermissions addPermissions, ModelPermissions removePermissions)
		throws PortalException;

}