package com.google.apps.domaincontactsmanager.client;

import com.google.apps.domaincontactsmanager.shared.ContactList;
import com.google.apps.domaincontactsmanager.shared.OAuthCredential;
import com.google.apps.domaincontactsmanager.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;

/**
 * The client side stub for the Rpc service.
 */
@RemoteServiceRelativePath("BackendService")
public interface BackendService extends RemoteService {
	ContactList getRegularContacts();
	ContactList getSharedContacts();
	ContactList getProfiles();

	User isUserLoggedIn();
	List<OAuthCredential> getOAuthCredentials();
	String saveOAuthCredentials( String domain, String key, String adminEmail);
}
