package com.liferay.gs.test.framework;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.concurrent.TimeUnit;

/**
 * @author Shane Merriss
 */
public abstract class BasePortletComponent extends FluentPage {

	public abstract void waitForPortlet();

	public FluentWebElement waitFor(FluentWebElement fluentWebElement) {
		this.await()
			.atMost(10, TimeUnit.SECONDS)
			.until(fluentWebElement)
			.displayed();

		return fluentWebElement;
	}

	protected FluentWebElement _portlet;
}
