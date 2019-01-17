package com.liferay.gs.env.setup.config;

/**
 * @author Andrew Betts
 */
public class UserGroupConfig {

	public UserGroupConfig(String userGroupName) {
		this.userGroupName = userGroupName;
		this.description = "";
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public String getDescription() {
		return description;
	}

	public UserGroupConfig setDescription(String description) {
		this.description = description;

		return this;
	}

	private final String userGroupName;
	private String description;

}