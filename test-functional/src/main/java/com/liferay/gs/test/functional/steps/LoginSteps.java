package com.liferay.gs.test.functional.steps;

import com.liferay.gs.test.functional.components.LoginComponent;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.assertj.core.api.Assertions;
import org.fluentlenium.core.annotation.Page;

/**
 * @author Shane Merriss
 */
public class LoginSteps {
	@Given("^I am on the login page$")
	public void iAmOnTheHomePage() {
		_loginComponent.go();
	}

	@And("^I login as '(.*?)' with '(.*?)'$")
	public void iLogin(String login, String password) {
		_loginComponent.signIn(login, password);
	}

	@Then("^I am logged in as '(.*?)'$")
	public void userIsLoggedIn(String name) {
		String signedInUserFullName = _loginComponent.getSignedInUserFullName();

		Assertions.assertThat(signedInUserFullName).isEqualToIgnoringCase(name);
	}

	@Page
	private LoginComponent _loginComponent;

}
