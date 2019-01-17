package com.liferay.gs.env.setup.sample.steps;

import com.liferay.gs.env.setup.AddRoles;
import com.liferay.gs.env.setup.sample.SampleData;
import com.liferay.gs.env.setup.EnvSetupStep;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class AddSampleRoles implements EnvSetupStep {

	@Override
	public void doStep(long companyId) throws PortalException {
		addRoles.addRoles(companyId, SampleData.SAMPLE_ROLE_CONFIGURATION);
	}

	@Reference
	private AddRoles addRoles;

}