package nl.miraclebenelux.domaincontacts.shared;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	protected boolean isResitered = false;
	protected boolean isAdmin = false;
	protected boolean isLoggedIn = false;
	protected String domain;
	protected String email;
	protected String adminEmail;
	protected String loginUrl;
	protected String logoutUrl;
	protected String oauthKey;

	public User() {
	}

	public boolean isResitered() {
		return isResitered;
	}

	public void setResitered(boolean isResitered) {
		this.isResitered = isResitered;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getOauthKey() {
		return oauthKey;
	}

	public void setOauthKey(String oauthKey) {
		this.oauthKey = oauthKey;
	}
}
