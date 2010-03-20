package nl.miraclebenelux.domaincontacts.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import nl.miraclebenelux.domaincontacts.shared.ContactList;
import nl.miraclebenelux.domaincontacts.shared.Contact;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.Query;
import com.google.gdata.client.batch.BatchInterruptedException;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.provisioning.UserFeed;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchStatus;
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
import com.google.gdata.model.batch.BatchUtils;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class MirContacts 
{
	// YYYY-MM-DDTHH:MM:SS
	static int batchsize = 200;
	static ContactsService thisService=null;
    private static final Logger log = Logger.getLogger(MirContacts.class.getName());
	private static final long serialVersionUID = 1L;

	private Hashtable<String,ContactsService> contactsServices = new Hashtable<String,ContactsService>();
	private UserService userService = UserServiceFactory.getUserService();
	
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
            Transfer.TransferFromEntry(contactEntry, contact);
			contacts.add(contact);
		}
		return contacts;
	}

	public ContactList getProfiles() {
		ContactList contactList = new ContactList();
		try {
			if (userService.isUserLoggedIn()) 
			{
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
			log.info("Profiles: Error = " + e.getMessage());
		}
		return contactList;
	}
	
	
	public static ContactEntry getContactInternal(String domain, String id)
    throws IOException, ServiceException 
    {
        FindDomain   dom = new FindDomain(domain);

		if (thisService == null)
		{
	       thisService = new ContactsService("MiracleContacts");
	       thisService.setCookieManager(null);
		}
	    thisService.setUserCredentials(dom.getUser(), dom.getToken());	

		if (id != null)
		{
			String str = id.replace("/base/", "/full/");
			URL    u   = new URL(str);
			if (u == null)
				return null;
		
            ContactEntry c = thisService.getEntry(u, ContactEntry.class);
            return c;
		}
		else 
		{
			return null;
		}
    }
	
	public static ContactGroupFeed   retrieveGroupsFull(ContactsService grpService, String name)
	throws IOException, ServiceException
	{	
		URL u = new URL("http://www.google.com/m8/feeds/groups/" + name + "/full");
		ContactGroupFeed resultFeed = new ContactGroupFeed();
		ContactGroupFeed currentPage;
		Link nextLink;
		
	    do {
	        currentPage = grpService.getFeed(u, ContactGroupFeed.class);
	        resultFeed.getEntries().addAll(currentPage.getEntries());
	        nextLink = currentPage.getLink(Link.Rel.NEXT, Link.Type.ATOM);
	        if (nextLink != null) {
	          u = new URL(nextLink.getHref());
	        }
	    } while (nextLink != null);
	    
		return resultFeed;
	}
	
	public static ContactFeed retrieveContactsFull(String usrname, String passwd, String name, Integer indx) 
	throws AppsForYourDomainException, ServiceException, IOException 
	{
		return retrieveContactsFull(usrname, passwd, name, indx, null);
	}
	
	public static ContactFeed retrieveContactsFull(String usrname, String passwd, String name, DateTime starttime) 
	throws AppsForYourDomainException, ServiceException, IOException 
	{
		return retrieveContactsFull(usrname, passwd, name, 1, starttime);
	}
	
	public static ContactFeed retrieveContactsFull(String usrname, String passwd, String name, Integer indx, DateTime starttime) 
	throws AppsForYourDomainException, ServiceException, IOException 
	{
		URL             myURL;
		String          srvURL = "http://www.google.com/m8/feeds/contacts/default/full?";

		if (thisService == null)
		{
	       thisService = new ContactsService("MiracleContacts");
	       thisService.setCookieManager(null);
		}
	    thisService.setUserCredentials(usrname, passwd);
        myURL = new URL(srvURL); 	 
		ContactFeed resultFeed = new ContactFeed();
		ContactFeed currentPage;
		Link nextLink;
     
        Query myQuery = new Query(myURL);
        if (starttime != null)
           myQuery.setUpdatedMin(starttime);
        myQuery.setStringCustomParameter("orderby","lastmodified");
        myQuery.setStringCustomParameter("sortorder", "descending");
        myQuery.setStringCustomParameter("showdeleted", "false"); 
        
        if (indx > 1)
        {
        	myQuery.setStartIndex(indx);
        }
        else
        {
        	myQuery.setStartIndex(1);
        }

        myQuery.setMaxResults(batchsize);
        
        currentPage = thisService.query(myQuery, ContactFeed.class);
	    do 
	    {   
	        resultFeed.getEntries().addAll(currentPage.getEntries());
	        nextLink = currentPage.getLink(Link.Rel.NEXT, Link.Type.ATOM);
	        if (nextLink != null) {
	          myURL = new URL(nextLink.getHref());
	        }
	        currentPage = thisService.getFeed(myURL, ContactFeed.class);
	    }
	    while (nextLink != null);
	    return resultFeed;
	}
	
	public static String batchRemove(String type, String Domain, List<ContactOperation> ids) throws MalformedURLException, AuthenticationException 

	{
		String          srvURL = "http://www.google.com/m8/feeds/" + type + "/"  + Domain + "/full/batch";
		URL             myURL;
	    FindDomain      dom    = new FindDomain(Domain);
		myURL = new URL(srvURL);
		if (thisService == null)
		{
	       thisService = new ContactsService("MiracleContacts");
	       thisService.setCookieManager(null);
		}
	    thisService.setUserCredentials(dom.getUser(), dom.getToken());
        ContactFeed feed = new ContactFeed();
        
        String res = "Deleting: ";
        
	    for (ContactOperation id : ids)
	    {
	    	ContactEntry toDelete = new ContactEntry();
	    	toDelete.setId(id.getEditLink().replace("/base/", "/full/"));
	    	toDelete.setEtag(id.getEtag());
	    	BatchUtils.setBatchOperationType(toDelete, BatchOperationType.DELETE);
	    	feed.getEntries().add(toDelete);
	    }

    	ContactFeed result;
		try 
		{
			result = thisService.batch(myURL, feed);
		} 
		catch (BatchInterruptedException e)
		{
			// TODO Auto-generated catch block
			res = res + "BI: " + e.getMessage() + " " + e.getResponseBody();
			return res;
		}
		catch (IOException e) 
		{
			res = res + "IO: " + e.getMessage() + " " ;
			return res;
		} 
		catch (ServiceException e) 
		{
			res = res + "SE: " + e.getMessage() + " " + e.getResponseBody();
			return res;
		}
	
	    for (ContactEntry entry : result.getEntries())
	    {
	        if (BatchUtils.isSuccess(entry) && BatchUtils.getBatchOperationType(entry) != BatchOperationType.DELETE)
	        {	
	        	BatchStatus stat = (BatchStatus) BatchUtils.getStatus(entry);
	        	res = res + stat.getReason() + " " + stat.getContent();
	        }
	    }
	    thisService = null;
	    return res;	       
	}
	
    public static void deleteEntry(String domain, String id)
    throws IOException, ServiceException 
    {
    	ContactEntry contact = getContactInternal(domain, id);
    	if (contact == null) 
    	{
    		return;
    	}    	
    	contact.delete();
    }
    
    public static ContactEntry insertEntry(String Domain, URL id, ContactEntry ins)
    throws IOException, ServiceException 
    {
	    FindDomain      dom    = new FindDomain(Domain);
		if (thisService == null)
		{
	       thisService = new ContactsService("MiracleContacts");
	       thisService.setCookieManager(null);
		   thisService.setUserCredentials(dom.getUser(), dom.getToken());
		}

		return thisService.insert(id, ins);
    }
    
    public static ContactEntry updateEntry(String Domain, ContactEntry entryToUpdate)
    throws IOException, ServiceException 
    {
	    FindDomain      dom    = new FindDomain(Domain);
		if (thisService == null)
		{
	       thisService = new ContactsService("MiracleContacts");
	       thisService.setCookieManager(null);
		   thisService.setUserCredentials(dom.getUser(), dom.getToken());
		}

		URL url = new URL(entryToUpdate.getEditLink().getHref());
		
    	return thisService.update(url, entryToUpdate);
    }
    
    public static ContactEntry createEntry(ContactEntry entryToCreate)
    throws IOException, ServiceException
    {
    	return null;
 //   	URL postUrl = new URL(sURL);
 //   	return myService.insert(postUrl, entryToCreate);
    }
    
    public static int getBatchsize()
    {
    	return batchsize;
    }
    
    public static ContactsService ConnectSerivce(String usrname, String passwd) throws AuthenticationException
    {
	    if (thisService == null)
	    {
    	   thisService = new ContactsService("MiracleContacts");
	       thisService.setCookieManager(null);
	    }
	    thisService.setUserCredentials(usrname, passwd);
	    return thisService;
    }

	public static ContactDetails retrieveContact(String domain, String id) {
		// TODO Auto-generated method stub
		ContactEntry E;
		try {
			E = getContactInternal(domain, id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		ContactDetails D = new ContactDetails();
		
		Transfer.TransferFromEntry(E, D);
		return D;
	}
}
