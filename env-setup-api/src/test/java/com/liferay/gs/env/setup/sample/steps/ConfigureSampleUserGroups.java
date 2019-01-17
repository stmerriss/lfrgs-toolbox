package com.liferay.gs.env.setup.sample.steps;

import com.liferay.gs.env.setup.AssignUserGroupGroupRoles;
import com.liferay.gs.env.setup.sample.SampleData;
import com.liferay.gs.env.setup.EnvSetupStep;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class ConfigureSampleUserGroups implements EnvSetupStep {

	@Override
	public void doStep(long companyId) throws PortalException {
		Group group = groupLocalService.getGroup(
			companyId, SampleData.SAMPLE_SITE_NAME);

		assignUserGroupGroupRoles.assignUserGroupGroupRoles(
			companyId, group, SampleData.SAMPLE_USER_GROUP_CONFIGURATION[0],
			SampleData.SAMPLE_ROLE_CONFIGURATION);
	}

	@Reference
	private AssignUserGroupGroupRoles assignUserGroupGroupRoles;

	@Reference
	private GroupLocalService groupLocalService;

}