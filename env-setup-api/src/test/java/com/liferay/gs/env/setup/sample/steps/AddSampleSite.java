package com.liferay.gs.env.setup.sample.steps;

import com.liferay.gs.env.setup.AddSites;
import com.liferay.gs.env.setup.sample.SampleData;
import com.liferay.gs.env.setup.EnvSetupStep;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class AddSampleSite implements EnvSetupStep {

	@Override
	public void doStep(long companyId) throws PortalException {
		_addSites.addSites(companyId, SampleData.SAMPLE_SITE_CONFIGURATION);
	}

	@Reference
	private AddSites _addSites;

}