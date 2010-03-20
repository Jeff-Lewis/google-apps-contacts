package nl.miraclebenelux.domaincontacts.client;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContactServiceAsync  
{
    void listContacts(String Domain,                                         AsyncCallback<ArrayList<Contact>> asyncCallback);
    void updateContact(String Domain, ContactDetails c,                      AsyncCallback<String> callback);
    void login(String username, String Password, String Domain,              AsyncCallback<Void> callback);
	void listDomains(boolean Admin,                                          AsyncCallback<ArrayList<String>> callback);
	void syncContacts(String fromDomain, String toDomain, Integer startindx, AsyncCallback<String> callback);
	void removeContact(String domain, ArrayList<ContactOperation> ids,       AsyncCallback<String> callback);
	void listGroups(String Domain,                                           AsyncCallback<ArrayList<Group>> callback);
	void addGroup(String Domain, String group,                               AsyncCallback<Void> callback);
	void removeGroup(String domain, String group,                            AsyncCallback<String> callback);
	void updateGroup(String domain, String oldgroup, String newgroup,        AsyncCallback<Void> callback);
	void syncContacts(String from, ArrayList<String> ids, String toDomain,   AsyncCallback<String> callback);
	void addContact(String domain, ContactDetails c,                         AsyncCallback<String> asyncCallback);
	void retrieveContact(String Domain, String id,                           AsyncCallback<ContactDetails> callback);
}