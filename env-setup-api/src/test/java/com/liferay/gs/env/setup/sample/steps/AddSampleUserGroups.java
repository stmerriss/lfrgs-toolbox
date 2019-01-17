package com.liferay.gs.env.setup.sample.steps;

import com.liferay.gs.env.setup.AddUserGroups;
import com.liferay.gs.env.setup.sample.SampleData;
import com.liferay.gs.env.setup.EnvSetupStep;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class AddSampleUserGroups implements EnvSetupStep {

	@Override
	public void doStep(long companyId) throws PortalException {
		addUserGroups.addUserGroups(
			companyId, SampleData.SAMPLE_USER_GROUP_CONFIGURATION);
	}

	@Reference
	private AddUserGroups addUserGroups;

}