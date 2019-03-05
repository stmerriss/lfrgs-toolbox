package com.liferay.gs.test.functional.runners;

import com.liferay.gs.test.framework.BaseRunner;
import cucumber.api.CucumberOptions;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runner.RunWith;

/**
 * @author Shane Merriss
 */
@RunWith(FluentCucumber.class)
@CucumberOptions(
	features = {"classpath:features/Login.feature"},
	glue = "com.liferay.gs.test.functional.steps",
	plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"}
)
@FluentConfiguration(
	webDriver = "chrome",
	baseUrl = "http://localhost:8080/"
)
public class LoginRunner extends BaseRunner {
}