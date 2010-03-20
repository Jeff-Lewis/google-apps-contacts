package com.google.apps.domaincontactsmanager.client;

import com.google.apps.domaincontactsmanager.shared.ContactList;
import com.google.apps.domaincontactsmanager.shared.OAuthCredential;
import com.google.apps.domaincontactsmanager.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * The async counterpart of <code>BackendService</code>.
 */
public interface BackendServiceAsync {
	void getRegularContacts(AsyncCallback<ContactList> callback);
	void getSharedContacts(AsyncCallback<ContactList> callback);
	void getProfiles(AsyncCallback<ContactList> callback);
	void isUserLoggedIn(AsyncCallback<User> callback);
	void getOAuthCredentials(AsyncCallback<List<OAuthCredential>> callback);
	void saveOAuthCredentials( String domain, String key, String adminEmail, AsyncCallback<String> callback);
}