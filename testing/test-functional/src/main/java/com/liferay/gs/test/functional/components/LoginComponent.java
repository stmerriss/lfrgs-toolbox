package com.liferay.gs.test.functional.components;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Shane Merriss
 */
@PageUrl("web/guest/home")
public class LoginComponent extends FluentPage {

	public void signIn(String login, String password) {
		_loginInput.fill().with(login);
		_passwordInput.fill().with(password);
		_signInButton.click();
	}

	public String getSignedInUserFullName() {
		return _userFullName.textContent().trim();
	}

	@FindBy(xpath = "//button[@type='submit']")
	private FluentWebElement _signInButton;

	@FindBy(id = "_com_liferay_login_web_portlet_LoginPortlet_password")
	private FluentWebElement _passwordInput;

	@FindBy(id = "_com_liferay_login_web_portlet_LoginPortlet_login")
	private FluentWebElement _loginInput;

	@FindBy(className = "user-full-name")
	private FluentWebElement _userFullName;

}
