package com.liferay.gs.env.setup;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrew Betts
 */
public interface EnvSetupStep {

	public void doStep(long companyId) throws PortalException;

}
