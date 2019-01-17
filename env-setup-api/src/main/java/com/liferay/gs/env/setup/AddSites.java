package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.SiteConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;

/**
 * @author Andrew Betts
 */

public interface AddSites {

	Group[] addSites(
			long companyId, SiteConfig[] siteContexts)
		throws PortalException;

}