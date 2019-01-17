package com.liferay.gs.env.setup.config;

import com.liferay.portal.kernel.util.PortletKeys;

/**
 * @author Andrew Betts
 */
public class PortletConfig {

	public PortletConfig(String portletId) {
		this.portletId = portletId;

		ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
		xml = null;
	}

	// getters

	public String getPortletId() {
		return portletId;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public int getOwnerType() {
		return ownerType;
	}

	public String getXml() {
		return xml;
	}

	// setters

	public PortletConfig setOwnerId(long ownerId) {
		this.ownerId = ownerId;

		return this;
	}

	public PortletConfig setOwnerType(int ownerType) {
		this.ownerType = ownerType;

		return this;
	}

	public PortletConfig setXml(String xml) {
		this.xml = xml;

		return this;
	}

	private String portletId;
	private long ownerId;
	private int ownerType;
	private String xml;

}