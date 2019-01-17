package com.liferay.gs.env.setup.sample.steps;

import com.liferay.gs.env.setup.AddUsers;
import com.liferay.gs.env.setup.EnvSetupStep;
import com.liferay.gs.env.setup.sample.SampleData;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Reference;

public class AddSampleUsers implements EnvSetupStep {
	@Override
	public void doStep(long companyId) throws PortalException {
		addUsers.addUsers(companyId, SampleData.SAMPLE_USER_CONFIGURATION);
	}

	@Reference
	private AddUsers addUsers;

}