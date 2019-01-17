package com.liferay.gs.env.setup.config;

import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrew Betts
 */
public class SiteConfig {

	public SiteConfig(String groupFriendlyURL) {
		this.groupFriendlyURL = groupFriendlyURL;

		descriptionMap = null;

		groupType = GroupConstants.TYPE_SITE_PRIVATE;

		manualMembership = true;

		membershipRestriction =
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;

		nameMap = new HashMap<>();

		nameMap.put(
			LocaleUtil.getDefault(),
			groupFriendlyURL.substring(1));

	}

	// getters

	public String getGroupFriendlyURL() {
		return groupFriendlyURL;
	}

	public Map<Locale, String> getNameMap() {
		return nameMap;
	}

	public Map<Locale, String> getDescriptionMap() {
		return descriptionMap;
	}

	public int getGroupType() {
		return groupType;
	}

	public boolean getManualMembership() {
		return manualMembership;
	}

	public int getMembershipRestriction() {
		return membershipRestriction;
	}

	// setters

	public SiteConfig setDescriptionMap(
		Map<Locale, String> descriptionMap) {

		this.descriptionMap = descriptionMap;

		return this;
	}

	public SiteConfig setGroupType(int groupType) {
		this.groupType = groupType;

		return this;
	}

	public SiteConfig setManualMembership(boolean manualMembership) {
		this.manualMembership = manualMembership;

		return this;
	}

	public SiteConfig setMembershipRestriction(
		int membershipRestriction) {

		this.membershipRestriction = membershipRestriction;

		return this;
	}

	public SiteConfig setNameMap(Map<Locale, String> nameMap) {
		this.nameMap = nameMap;

		return this;
	}

	private final String groupFriendlyURL;

	private Map<Locale, String> descriptionMap;
	private int groupType;
	private boolean manualMembership;
	private int membershipRestriction;
	private Map<Locale, String> nameMap;

}
