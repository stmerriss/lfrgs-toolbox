package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.LayoutConfig;
import com.liferay.gs.env.setup.config.PortletConfig;
import com.liferay.gs.env.setup.config.SiteConfig;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrew Betts
 */
public interface AddPortlets {

	void addPortlets(
		long companyId, SiteConfig siteConfig,
		PortletConfig[] portletConfigs, LayoutConfig[] layoutConfigs)
		throws PortalException;
}
