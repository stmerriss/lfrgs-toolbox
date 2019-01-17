package com.liferay.gs.env.setup.upgrade;

import com.liferay.gs.env.setup.EnvSetupStep;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Andrew Betts
 */
public class CompanyAwareUpgradeProcess extends UpgradeProcess {

	public CompanyAwareUpgradeProcess(
		EnvSetupStep envSetupStep, Portal portal) {

		_envSetupStep = envSetupStep;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws PortalException {
		long[] companyIds = _portal.getCompanyIds();

		if (_log.isInfoEnabled()) {
			_log.info(
				"### running env setup step" +
					_envSetupStep.getClass().getName());
		}

		for (long companyId : companyIds) {
			_envSetupStep.doStep(companyId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyAwareUpgradeProcess.class);

	private EnvSetupStep _envSetupStep;
	private Portal _portal;

}