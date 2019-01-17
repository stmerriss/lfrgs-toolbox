package com.liferay.gs.env.setup.config;

import com.liferay.portal.kernel.model.RoleConstants;

import java.util.Locale;
import java.util.Map;

/**
 * @author Andrew Betts
 */
public class RoleConfig {

	public RoleConfig(String roleName) {
		this.roleName = roleName;

		this.descriptionMap = null;
		this.titleMap = null;
		this.roleType = RoleConstants.TYPE_SITE;
		this.subType = null;
	}

	public Map<Locale, String> getDescriptionMap() {
		return descriptionMap;
	}

	public String getRoleName() {
		return roleName;
	}

	public int getRoleType() {
		return roleType;
	}

	public String getSubType() {
		return subType;
	}

	public Map<Locale, String> getTitleMap() {
		return titleMap;
	}

	public RoleConfig setRoleType(int roleType) {
		this.roleType = roleType;

		return this;
	}

	public RoleConfig setTitleMap(Map<Locale, String> titleMap) {
		this.titleMap = titleMap;

		return this;
	}

	public RoleConfig setDescriptionMap(Map<Locale, String> descriptionMap) {
		this.descriptionMap = descriptionMap;

		return this;
	}

	public RoleConfig setSubType(String subType) {
		this.subType = subType;

		return this;
	}

	private final String roleName;

	private Map<Locale, String> descriptionMap;
	private int roleType;
	private String subType;
	private Map<Locale, String> titleMap;

}
