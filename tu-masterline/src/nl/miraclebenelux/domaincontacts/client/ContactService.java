package nl.miraclebenelux.domaincontacts.client;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("contacts")
public interface ContactService extends RemoteService 
{
    ArrayList<Contact> listContacts(String Domain);
    ArrayList<Group>   listGroups(String Domain);
    ArrayList<String>  listDomains(boolean Admin);
    void               addGroup(String Domain, String group);
    ContactDetails     retrieveContact(String Domain, String id);
    String             removeContact(String domain, ArrayList<ContactOperation> ids);
    String             removeGroup(String domain, String group);
    void               updateGroup(String domain, String oldgroup, String newgroup);
	void               login(String username, String Password, String Domain);
	String             syncContacts(String fromDomain, String toDomain, Integer startindx);
	String             syncContacts(String fromDomain, ArrayList<String> ids, String toDomain);
	String               updateContact(String Domain, ContactDetails c);
	String               addContact(String domain, ContactDetails c);
}