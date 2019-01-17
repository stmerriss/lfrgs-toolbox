package com.liferay.gs.env.setup.config;

import com.liferay.gs.env.setup.AddUsers;

import java.util.Locale;

public class UserConfig {

	public UserConfig(String login, AddUsers.Find findBy) {
		this.autoPassword = true;
		this.autoScreenName = true;
		this.firstName = "";
		this.googleUserId = "";
		this.jobTitle = "";
		this.lastName = "";
		this.middleName = "";
		this.openId = "";
		this.password = "";
		this.screenName = "";
		this.userLocale = Locale.US;

		if (findBy == null) {
			findBy = AddUsers.Find.BY_SCREEN_NAME;
		}

		switch (findBy) {
			case BY_CONTACT_ID:
				contactId = Long.parseLong(login);

				break;

			case BY_EMAIL_ADDRESS:
				emailAddress = login;

				break;

			case BY_FACEBOOK_ID:
				facebookId = Long.parseLong(login);

				break;

			case BY_GOOGLE_USER_ID:
				googleUserId = login;

				break;

			case BY_OPEN_ID:
				openId = login;

				break;

			case BY_SCREEN_NAME:
				screenName = login;

				break;

			case BY_USER_ID:
				id = Long.parseLong(login);
		}

		this.findBy = findBy;
	}

	public AddUsers.Find getFindBy() {
		return findBy;
	}

	// getters

	public String getEmailAddress() {
		return emailAddress;
	}

	public long getId() {
		return id;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getGoogleUserId() {
		return googleUserId;
	}

	public long getFacebookId() {
		return facebookId;
	}

	public String getOpenId() {
		return openId;
	}

	public long getContactId() {
		return contactId;
	}

	public boolean getAutoPassword() {
		return autoPassword;
	}

	public String getPassword() {
		return password;
	}

	public boolean getAutoScreenName() {
		return autoScreenName;
	}

	public Locale getUserLocale() {
		return userLocale;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	// setters

	public UserConfig setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;

		return this;
	}

	public UserConfig setScreenName(String screenName) {
		this.screenName = screenName;

		return this;
	}

	public UserConfig setGoogleUserId(String googleUserId) {
		this.googleUserId = googleUserId;

		return this;
	}

	public UserConfig setFacebookId(long facebookId) {
		this.facebookId = facebookId;

		return this;
	}

	public UserConfig setOpenId(String openId) {
		this.openId = openId;

		return this;
	}

	public UserConfig setContactId(long contactId) {
		this.contactId = contactId;

		return this;
	}

	public UserConfig setAutoPassword(boolean autoPassword) {
		this.autoPassword = autoPassword;

		return this;
	}

	public UserConfig setPassword(String password) {
		this.password = password;

		return this;
	}

	public UserConfig setAutoScreenName(boolean autoScreenName) {
		this.autoScreenName = autoScreenName;

		return this;
	}

	public UserConfig setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;

		return this;
	}

	public UserConfig setFirstName(String firstName) {
		this.firstName = firstName;

		return this;
	}

	public UserConfig setLastName(String lastName) {
		this.lastName = lastName;

		return this;
	}

	public UserConfig setMiddleName(String middleName) {
		this.middleName = middleName;

		return this;
	}

	public UserConfig setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;

		return this;
	}

	private final AddUsers.Find findBy;

	private boolean autoPassword;
	private boolean autoScreenName;
	private long contactId;
	private String emailAddress;
	private long facebookId;
	private String firstName;
	private String googleUserId;
	private long id;
	private String jobTitle;
	private String lastName;
	private String middleName;
	private String openId;
	private String password;
	private String screenName;
	private Locale userLocale;

}