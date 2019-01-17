package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AddOrganizations;
import com.liferay.gs.env.setup.config.OrganizationConfig;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class AddOrganizationsImpl implements AddOrganizations {

	@Override
	public void addOrganizations(
			long companyId, Organization parent,
			OrganizationConfig[] organizationConfigs)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		long parentOrganizationId =
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

		if (parent != null) {
			parentOrganizationId = parent.getOrganizationId();
		}

		for (OrganizationConfig organizationConfig : organizationConfigs) {
			Organization organization =
				_organizationLocalService.addOrganization(
					companyGroup.getCreatorUserId(), parentOrganizationId,
					organizationConfig.getOrganizationName(),
					organizationConfig.getType(), 0, 0,
					organizationConfig.getStatus(), "",
					organizationConfig.getSite(), new ServiceContext());

			if (_log.isDebugEnabled()) {
				_log.debug(
					"added organization " +
						organizationConfig.getOrganizationName());
			}

			if (organizationConfig.getChildren() != null) {
				addOrganizations(
					companyId, organization, organizationConfig.getChildren());
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("organizations loaded");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddOrganizationsImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}