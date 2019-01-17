package com.liferay.gs.env.setup.json;

import com.liferay.gs.env.setup.AddUsers;
import com.liferay.gs.env.setup.config.LayoutConfig;
import com.liferay.gs.env.setup.config.OrganizationConfig;
import com.liferay.gs.env.setup.config.PortletConfig;
import com.liferay.gs.env.setup.config.RoleConfig;
import com.liferay.gs.env.setup.config.SiteConfig;
import com.liferay.gs.env.setup.config.UserConfig;
import com.liferay.gs.env.setup.config.UserGroupConfig;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrew Betts
 */
public class ConfigurationJSONReader {

	public LayoutConfig readLayoutConfig(JSONObject jsonObject) {
		LayoutConfig[] children = null;

		JSONArray childrenJSONArray = jsonObject.getJSONArray("children");

		if (childrenJSONArray != null) {
			children = new LayoutConfig[childrenJSONArray.length()];

			for (int i = 0; i < childrenJSONArray.length(); i++) {
				children[i] = readLayoutConfig(
					childrenJSONArray.getJSONObject(i));
			}
		}

		LayoutConfig layoutConfig = new LayoutConfig(
			jsonObject.getString("friendlyURL"), children);

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			switch (key) {
				case "description":
					layoutConfig.setDescription(jsonObject.getString(key));

					break;

				case "layoutType":
					layoutConfig.setLayoutType(jsonObject.getString(key));

					break;

				case "title":
					layoutConfig.setTitle(jsonObject.getString(key));

					break;

				case "name":
					layoutConfig.setName(jsonObject.getString(key));

					break;

				case "hidden":
					layoutConfig.setHidden(jsonObject.getBoolean(key));

					break;

				case "private":
					layoutConfig.setPrivateLayout(jsonObject.getBoolean(key));

					break;
			}
		}

		return layoutConfig;
	}

	public PortletConfig readPortletConfig(JSONObject jsonObject) {
		PortletConfig portletConfig = new PortletConfig(
			jsonObject.getString("portletId"));

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			switch (key) {
				case "ownerId":
					portletConfig.setOwnerId(jsonObject.getLong(key));

					break;

				case "ownerType":
					portletConfig.setOwnerType(jsonObject.getInt(key));

					break;

				case "xml":
					portletConfig.setXml(jsonObject.getString(key));

					break;
			}
		}

		return portletConfig;
	}

	public OrganizationConfig readOrganizationConfig(JSONObject jsonObject) {
		OrganizationConfig[] children = null;

		JSONArray childrenJSONArray = jsonObject.getJSONArray("children");

		if (childrenJSONArray != null) {
			children = new OrganizationConfig[childrenJSONArray.length()];

			for (int i = 0; i < childrenJSONArray.length(); i++) {
				children[i] = readOrganizationConfig(
					childrenJSONArray.getJSONObject(i));
			}
		}

		OrganizationConfig organizationConfig = new OrganizationConfig(
			jsonObject.getString("organizationName"), children);

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			switch (key) {
				case "site":
					organizationConfig.setSite(jsonObject.getBoolean(key));

					break;

				case "status":
					organizationConfig.setStatus(jsonObject.getLong(key));

					break;

				case "type":
					organizationConfig.setType(jsonObject.getString(key));

					break;
			}
		}

		return organizationConfig;
	}

	public RoleConfig readRoleConfig(JSONObject jsonObject) {
		RoleConfig roleConfig = new RoleConfig(
			jsonObject.getString("roleName"));

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			switch (key) {
				case "descriptionMap":
				case "titleMap":
					Map<Locale, String> localizedMap = _getLocalizedMap(
						jsonObject.getJSONObject(key));

					if (key.equals("descriptionMap")) {
						roleConfig.setDescriptionMap(localizedMap);
					}
					else {
						roleConfig.setTitleMap(localizedMap);
					}

					break;

				case "roleType":
					roleConfig.setRoleType(jsonObject.getInt(key));

					break;

				case "subType":
					roleConfig.setSubType(jsonObject.getString(key));

					break;
			}
		}

		return roleConfig;
	}

	public SiteConfig readSiteConfig(JSONObject jsonObject) {
		SiteConfig siteConfig = new SiteConfig(
			jsonObject.getString("friendlyURL"));

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			switch (key) {
				case "descriptionMap":
				case "nameMap":
					Map<Locale, String> localizedMap = _getLocalizedMap(
						jsonObject.getJSONObject(key));

					if (key.equals("descriptionMap")) {
						siteConfig.setDescriptionMap(localizedMap);
					}
					else {
						siteConfig.setNameMap(localizedMap);
					}

					break;

				case "groupType":
					siteConfig.setGroupType(jsonObject.getInt(key));

					break;

				case "manualMembership":
					siteConfig.setManualMembership(jsonObject.getBoolean(key));

					break;

				case "membershipRestriction":
					siteConfig.setMembershipRestriction(jsonObject.getInt(key));

					break;
			}
		}

		return siteConfig;
	}

	public UserConfig readUserConfig(JSONObject jsonObject) {
		AddUsers.Find findBy = AddUsers.Find.valueOf(
			jsonObject.getString("findBy"));

		UserConfig userConfig = new UserConfig(
			jsonObject.getString("login"), findBy);

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			switch (key) {
				case "autoPassword":
					userConfig.setAutoPassword(jsonObject.getBoolean(key));

					break;

				case "autoScreenName":
					userConfig.setAutoScreenName(jsonObject.getBoolean(key));

					break;

				case "contactId":
					userConfig.setContactId(jsonObject.getLong(key));

					break;

				case "email":
					userConfig.setEmailAddress(jsonObject.getString(key));

					break;

				case "facebookId":
					userConfig.setFacebookId(jsonObject.getLong(key));

					break;

				case "firstName":
					userConfig.setFirstName(jsonObject.getString(key));

					break;

				case "googleUserId":
					userConfig.setGoogleUserId(jsonObject.getString(key));

					break;

				case "jobTitle":
					userConfig.setJobTitle(jsonObject.getString(key));

					break;

				case "lastName":
					userConfig.setLastName(jsonObject.getString(key));

					break;

				case "middleName":
					userConfig.setMiddleName(jsonObject.getString(key));

					break;

				case "openId":
					userConfig.setOpenId(jsonObject.getString(key));

					break;

				case "password":
					userConfig.setPassword(jsonObject.getString(key));

					break;

				case "screenName":
					userConfig.setScreenName(jsonObject.getString(key));

					break;

				case "languageId":
					Locale locale = LocaleUtil.fromLanguageId(key,false, false);

					if (locale != null) {
						userConfig.setUserLocale(locale);
					}

					break;
			}
		}

		return userConfig;
	}

	public UserGroupConfig readUserGroupConfig(JSONObject jsonObject) {
		UserGroupConfig userGroupConfig = new UserGroupConfig(
			jsonObject.getString("userGroupName"));

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			switch (key) {
				case "roleType":
					userGroupConfig.setDescription(jsonObject.getString(key));

					break;
			}
		}

		return userGroupConfig;
	}

	private Map<Locale, String> _getLocalizedMap(
		JSONObject localizedJSONObject) {

		Map<Locale, String> localizedMap = new HashMap<>();

		Iterator<String> languageIds = localizedJSONObject.keys();

		while (languageIds.hasNext()) {
			String languageId = languageIds.next();

			Locale locale = LocaleUtil.fromLanguageId(
				languageId, false, false);

			if (locale != null) {
				localizedMap.put(
					locale,
					localizedJSONObject.getString(languageId));
			}
		}

		return localizedMap;
	}

}