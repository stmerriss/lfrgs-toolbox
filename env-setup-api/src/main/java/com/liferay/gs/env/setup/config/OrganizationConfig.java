package com.liferay.gs.env.setup.config;

import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.OrganizationConstants;

public class OrganizationConfig {

	public OrganizationConfig(
		String organizationName, OrganizationConfig[] children) {

		this.organizationName = organizationName;

		site = false;
		status = ListTypeConstants.ORGANIZATION_STATUS_DEFAULT;
		type = OrganizationConstants.TYPE_ORGANIZATION;

		this.children = children;
	}

	// getters

	public OrganizationConfig[] getChildren() {
		return children;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public boolean getSite() {
		return site;
	}

	public long getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	// setters

	public OrganizationConfig setStatus(long status) {
		this.status = status;

		return this;
	}

	public OrganizationConfig setSite(boolean site) {
		this.site = site;

		return this;
	}

	public OrganizationConfig setType(String type) {
		this.type = type;

		return this;
	}

	private final String organizationName;
	private final OrganizationConfig[] children;

	private long status;
	private boolean site;
	private String type;

}
