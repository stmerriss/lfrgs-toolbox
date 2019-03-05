Feature: Login with valid users

	Background: Start on the login page
		Given I am on the login page

	Scenario: Login with valid user
		And I login as 'lfrgs-liferay-jarvis@liferay.com' with 'test'
		Then I am logged in as 'GS Admin'