package com.google.apps.domaincontactsmanager.server;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apps.domaincontactsmanager.client.BackendService;
import com.google.apps.domaincontactsmanager.shared.Contact;
import com.google.apps.domaincontactsmanager.shared.ContactList;
import com.google.apps.domaincontactsmanager.shared.OAuthCredential;
import com.google.apps.domaincontactsmanager.shared.User;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthSigner;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.contacts.Website;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the Rpc service.
 */
public class BackendServiceImpl extends RemoteServiceServlet implements BackendService {

	private static final long serialVersionUID = 1L;

	private Hashtable<String,ContactsService> contactsServices = new Hashtable<String,ContactsService>();
	private UserService userService = UserServiceFactory.getUserService();

	public User isUserLoggedIn() {
		User user = new User();
		if (userService.isUserLoggedIn()) {
			String email = userService.getCurrentUser().getEmail();
			String domain = email.substring(email.indexOf('@') + 1);
			user.setEmail(email);
			user.setDomain(domain);
			user.setLoggedIn(true);
			user.setAdmin(userService.isUserAdmin());
			user.setLogoutUrl(userService.createLogoutURL("/"));
			
			OAuthCredential oauthCredential = getOAuthCredential(domain);
			if (oauthCredential != null) {
				user.setResitered(true);
				user.setOauthKey(oauthCredential.getOAuthKey());
				user.setAdminEmail(oauthCredential.getEmailAdmin());
				createService(domain, oauthCredential.getOAuthKey());
			} else {
				user.setResitered(false);
			}
		} else {
			user.setLoggedIn(false);
			user.setResitered(false);
			user.setAdmin(false);
			user.setLoginUrl(userService.createLoginURL("/"));
		}
		return user;
	}

	private void createService(String domain, String oauthKey) {
		if (!contactsServices.containsKey(domain)) {
			try {
				// Create ContactService and authenticate using OAuth credentials
				ContactsService contactsService = new ContactsService("googleContactsSample");
				OAuthSigner signer = new OAuthHmacSha1Signer();
				GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
				oauthParameters.setOAuthConsumerKey(domain);
				oauthParameters.setOAuthConsumerSecret(oauthKey);
				oauthParameters.setScope("http://www.google.com/m8/feeds/");
				contactsService.setOAuthCredentials(oauthParameters, signer);
				contactsServices.put(domain, contactsService);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String saveOAuthCredentials(String domain, String oauthKey, String adminEmail) {
		if (userService.isUserAdmin()) {
			OAuthCredential credentials = new OAuthCredential(domain, oauthKey, adminEmail);
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(credentials);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public List<OAuthCredential> getOAuthCredentials() {
		List<OAuthCredential> credentialModelList = new ArrayList<OAuthCredential>();
		if (userService.isUserAdmin()) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				List<OAuthCredential> credentialsList =
					(List<OAuthCredential>) pm.newQuery(OAuthCredential.class).execute();
				for (OAuthCredential credential : credentialsList) {
					credentialModelList.add(new OAuthCredential(credential.getDomain(), credential
							.getOAuthKey(), credential.getEmailAdmin()));
				}
			} catch (JDOObjectNotFoundException e) {
				e.printStackTrace();
			}
		}
		return credentialModelList;
	}

	
	private OAuthCredential getOAuthCredential(String domain) {
		if (userService.isUserLoggedIn()) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				return (OAuthCredential)pm.getObjectById(OAuthCredential.class, domain);
			} catch (JDOObjectNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ContactList getRegularContacts() {
		ContactList contactList = new ContactList();
		try {
			if (userService.isUserLoggedIn()) {
				String adminEmail = userService.getCurrentUser().getEmail();
				String domain = adminEmail.substring(adminEmail.indexOf('@') + 1);
				ContactsService contactsService = contactsServices.get(domain);
	
				// Gets all the Contacts for the user
				URL contactsFeedUrl =
					new URL("http://www.google.com/m8/feeds/contacts/default/full?xoauth_requestor_id="
							+ adminEmail + "&max-results=10000");
				ContactFeed contactsResultFeed = contactsService.getFeed(contactsFeedUrl, ContactFeed.class);
	
				// Gets all the contacts groups for the user
				URL contactsGroupsFeedUrl =
					new URL("http://www.google.com/m8/feeds/groups/default/full?xoauth_requestor_id="
							+ userService.getCurrentUser().getEmail() + "&max-results=10000");
				ContactGroupFeed contactsGroupsResultFeed =
					contactsService.getFeed(contactsGroupsFeedUrl, ContactGroupFeed.class);
				contactList.setTitle("All Contacts");
				contactList.setId("All Contacts");
				contactList.contacts = getContactsFromFeed( contactsResultFeed, contactsGroupsResultFeed );
				contactList.sort();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactList;
	}  

	public ContactList getSharedContacts() {
		ContactList contactList = new ContactList();
		try {
			if (userService.isUserLoggedIn()) {
				String adminEmail = userService.getCurrentUser().getEmail();
				String domain = adminEmail.substring(adminEmail.indexOf('@') + 1);  
				ContactsService contactsService = contactsServices.get(domain);
				// Gets all domain Shared Contacts
				URL contactsFeedUrl =
					new URL("http://www.google.com/m8/feeds/contacts/" + domain
							+ "/full?xoauth_requestor_id=" + adminEmail + "&max-results=10000");
				ContactFeed contactsResultFeed = contactsService.getFeed(contactsFeedUrl, ContactFeed.class); 
				contactList.setTitle("Shared Contacts");
				contactList.setId("Shared Contacts");
				contactList.contacts = getContactsFromFeed( contactsResultFeed, new ContactGroupFeed() );
				contactList.sort();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactList;
	}

	public ContactList getProfiles() {
		ContactList contactList = new ContactList();
		try {
			if (userService.isUserLoggedIn()) {
				String adminEmail = userService.getCurrentUser().getEmail();
				String domain = adminEmail.substring(adminEmail.indexOf('@') + 1);
				ContactsService contactsService = contactsServices.get(domain);
				
				// Gets all domain Profiles
				URL contactsFeedUrl =
					new URL("http://www.google.com/m8/feeds/profiles/domain/" + domain
							+ "/full?xoauth_requestor_id=" + adminEmail + "&max-results=10000"); 
				ContactFeed contactsResultFeed = contactsService.getFeed(contactsFeedUrl, ContactFeed.class);      
				contactList.setTitle("Profiles");
				contactList.setId("Profiles");
				contactList.contacts = getContactsFromFeed( contactsResultFeed, new ContactGroupFeed() );
				contactList.sort();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactList;
	}

	private List<Contact> getContactsFromFeed(ContactFeed contactFeed, ContactGroupFeed contactGroupFeed) {    
		// Gets Groups Names 
		Hashtable<String,String> groupsHash = new Hashtable<String,String>();
		for ( ContactGroupEntry groupEntry : contactGroupFeed.getEntries() ) {
			String title = groupEntry.getTitle().getPlainText().replaceAll("System Group:", "");
			groupsHash.put(groupEntry.getId(), title);
		}
		
		List<Contact> contacts = new ArrayList<Contact>();
		for (ContactEntry contactEntry :  contactFeed.getEntries() ) {
			Contact contact = new Contact();
			contact.setTitle(contactEntry.getTitle().getPlainText());
			contact.setId(contactEntry.getId());
			
			// Add the Contact's Notes
			try {
				String notes = contactEntry.getTextContent().getContent().getPlainText();
				contact.setNotes(notes);
			} catch (IllegalStateException e) {
			   contact.setNotes("");
			}
			// Add Emails to the Contact      
			for (Email email : contactEntry.getEmailAddresses()) {
				String rel = null;
				if (email.getRel() != null)
					rel = email.getRel().replace("http://schemas.google.com/g/2005#", "");
				contact.emails.add(
						new com.google.apps.domaincontactsmanager.shared.Email(email.getAddress(),rel));
			}
			// Add Phones to the Contact
			for (PhoneNumber phoneNumber : contactEntry.getPhoneNumbers()) {
				String rel = null;
				if (phoneNumber.getRel() != null)
					rel = phoneNumber.getRel().replace("http://schemas.google.com/g/2005#", "");
				contact.phoneNumbers.add(
						new com.google.apps.domaincontactsmanager.shared.PhoneNumber(
								phoneNumber.getPhoneNumber(),rel));
			}
			// Add Organizations to the Contact
			for ( Organization organization : contactEntry.getOrganizations()){
				com.google.apps.domaincontactsmanager.shared.Organization newOrganization = 
					new com.google.apps.domaincontactsmanager.shared.Organization(); 
				if (organization.getOrgName() != null)
					newOrganization.setOrgName(organization.getOrgName().getValue());
				if (organization.getOrgTitle() != null)
					newOrganization.setOrgTitle(organization.getOrgTitle().getValue());
				if (organization.getOrgDepartment() != null)
					newOrganization.setOrgDepartment(organization.getOrgDepartment().getValue());
				if (organization.getOrgJobDescription() != null)
					newOrganization.setOrgJobDescription(organization.getOrgJobDescription().getValue());
				if (organization.getLabel() != null)
					newOrganization.setLabel(organization.getLabel());
				if (organization.getPrimary() != null)
					newOrganization.setPrimary(organization.getPrimary());
				contact.organizations.add(newOrganization);
			}
			// Add StructuredPostalAddresses to the Contact
			for (StructuredPostalAddress structuredPostalAddress : contactEntry.getStructuredPostalAddresses()){
				com.google.apps.domaincontactsmanager.shared.StructuredPostalAddress newAddress = 
					new com.google.apps.domaincontactsmanager.shared.StructuredPostalAddress();
				if (structuredPostalAddress.getFormattedAddress() != null)
					newAddress.setFormattedAddress(structuredPostalAddress.getFormattedAddress().getValue());
				contact.structuredPostalAddresses.add(newAddress);
			}
			// Add Websites to the Contact
			for (Website website : contactEntry.getWebsites()){
				com.google.apps.domaincontactsmanager.shared.Website newWebsite = 
					new com.google.apps.domaincontactsmanager.shared.Website();
				if (website.getHref() != null)
					newWebsite.setUrl(website.getHref());
				if (website.getLabel() != null)
					newWebsite.setLabel(website.getLabel());
				if (website.getPrimary() != null)
					newWebsite.setPrimary(website.getPrimary());
				if (website.getRel() != null)
					newWebsite.setRel(website.getRel().toValue());
				contact.websites.add(newWebsite);
			}
		    // Add Group information
		    for (GroupMembershipInfo groupInfo : contactEntry.getGroupMembershipInfos() ) {
		    	String groupId = groupInfo.getHref();
		      	String groupName = groupsHash.get(groupId);
		      	contact.groups.put(groupId,groupName);  
		    }
			contacts.add(contact);
		}
		return contacts;
	}
}