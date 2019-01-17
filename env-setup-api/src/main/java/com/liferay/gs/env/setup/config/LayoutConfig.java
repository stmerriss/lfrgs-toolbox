package com.liferay.gs.env.setup.config;

import com.liferay.portal.kernel.model.LayoutConstants;

/**
 * @author Andrew Betts
 */
public class LayoutConfig {

	public static final String LAYOUT_TYPE_NODE = "node";

	public LayoutConfig(String friendlyURL, LayoutConfig[] children) {

		this.friendlyURL = friendlyURL;
		this.children = children;
		this.privateLayout = true;

		if (children == null) {
			layoutType = LayoutConstants.TYPE_PORTLET;
		}
		else {
			layoutType = LAYOUT_TYPE_NODE;
		}

	}

	// getters

	public String getDescription() {
		return description;
	}

	public boolean getHidden() {
		return hidden;
	}

	public String getFriendlyURL() {
		return friendlyURL;
	}

	public String getName() {
		return name;
	}

	public boolean getPrivateLayout() {
		return privateLayout;
	}

	public String getTitle() {
		return title;
	}

	public LayoutConfig[] getChildren() {
		return children;
	}

	public String getLayoutType() {
		return layoutType;
	}

	// setters

	public LayoutConfig setDescription(String description) {
		this.description = description;

		return this;
	}

	public LayoutConfig setHidden(boolean hidden) {
		this.hidden = hidden;

		return this;
	}

	public LayoutConfig setLayoutType(String layoutType) {
		this.layoutType = layoutType;

		return this;
	}

	public LayoutConfig setName(String name) {
		this.name = name;

		return this;
	}

	public LayoutConfig setPrivateLayout(boolean privateLayout) {
		this.privateLayout = privateLayout;

		return this;
	}

	public LayoutConfig setTitle(String title) {
		this.title = title;

		return this;
	}

	private final String friendlyURL;

	private String description;
	private boolean hidden;
	private String layoutType;
	private String name;
	private boolean privateLayout;
	private String title;

	private LayoutConfig[] children;

}