package nl.miraclebenelux.domaincontacts.server;
 
import nl.miraclebenelux.domaincontacts.client.Contact;
import nl.miraclebenelux.domaincontacts.client.ContactDetails;
import nl.miraclebenelux.domaincontacts.client.ContactOperation;
import nl.miraclebenelux.domaincontacts.client.Group;

import java.util.ArrayList;

public interface ContactDAO
{
   String             addContact(String Domain, ContactDetails contact);
   String             removeContact(String domain, ArrayList<ContactOperation> ids);
   String             updateContact(String domain, ContactDetails contact);
   ContactDetails	  retrieveContact(String Domain, String Id);
   ArrayList<Contact> listContacts(String Domain);  
   ArrayList<String>  listDomains(boolean admin);
   String             syncContacts(String fromDomain, String toDomain, int startindx);
   String             syncContacts(String fromDomain, ArrayList<String> ids, String toDomain);
   boolean            login(String username, String password, String domain);
   ArrayList<Group>   listGroups(String domain);
}
