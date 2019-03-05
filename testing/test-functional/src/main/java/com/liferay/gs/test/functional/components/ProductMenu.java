package com.liferay.gs.test.functional.components;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Shane Merriss
 */
public class ProductMenu extends FluentPage {

	@FindBy(id = "_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_sidenavSliderId")
	private FluentWebElement _sidenavSliderIcon;

}
