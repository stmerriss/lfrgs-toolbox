package com.liferay.gs.env.setup.sample.steps;

import com.liferay.gs.env.setup.AddLayouts;
import com.liferay.gs.env.setup.config.RoleConfig;
import com.liferay.gs.env.setup.sample.SampleData;
import com.liferay.gs.env.setup.EnvSetupStep;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;

import org.osgi.service.component.annotations.Reference;

public class AddSampleLayouts implements EnvSetupStep {

	@Override
	public void doStep(long companyId) throws PortalException {
		Group group = groupLocalService.getGroup(
			companyId, SampleData.SAMPLE_SITE_NAME);

		ModelPermissions addPermissions = new ModelPermissions();

		for (RoleConfig roleConfig : SampleData.SAMPLE_ROLE_CONFIGURATION) {
			addPermissions.addRolePermissions(
				roleConfig.getRoleName(), ActionKeys.VIEW);
		}

		ModelPermissions removePermissions = new ModelPermissions();

		removePermissions.addRolePermissions(
			RoleConstants.SITE_MEMBER, ActionKeys.VIEW);
		removePermissions.addRolePermissions(
			RoleConstants.SITE_MEMBER, ActionKeys.CUSTOMIZE);

		addLayouts.addLayouts(
			null, group, SampleData.SAMPLE_LAYOUT_CONFIGURATION, addPermissions,
			removePermissions);
	}

	@Reference
	private GroupLocalService groupLocalService;

	@Reference
	private AddLayouts addLayouts;

}