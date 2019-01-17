package com.liferay.gs.env.setup;

import com.liferay.gs.env.setup.config.OrganizationConfig;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;

public interface AddOrganizations {

	void addOrganizations(
			long companyId, Organization parent,
			OrganizationConfig[] organizationConfigs)
		throws PortalException;

}