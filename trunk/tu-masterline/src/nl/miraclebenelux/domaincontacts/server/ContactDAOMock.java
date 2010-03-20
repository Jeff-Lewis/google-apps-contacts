package nl.miraclebenelux.domaincontacts.server;
 
import nl.miraclebenelux.domaincontacts.client.Contact;
import nl.miraclebenelux.domaincontacts.client.ContactDetails;
import nl.miraclebenelux.domaincontacts.client.ContactOperation;
import nl.miraclebenelux.domaincontacts.client.Group;
import nl.miraclebenelux.domaincontacts.server.MirContacts;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.net.URL;

public class ContactDAOMock implements ContactDAO 
{
	 ArrayList<Contact> temp = null;
	 String t_domain         = null;

     public String syncContacts(String fromDomain, String toDomain, int startindx)
	 {
	    String temp;
		try 
		{
			return SyncContacts.syncContact(fromDomain, toDomain, startindx, null);
		} 
		catch (AppsForYourDomainException e) 
		{
			// TODO Auto-generated catch block
			temp = e.getInternalReason();
		} 
		catch (ServiceException e) 
		{
			// TODO Auto-generated catch block
			temp = e.getInternalReason();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			temp = e.getMessage();;
		}
		return temp;
	 }
 
	 public boolean login(String username, String password, String domain)
	 {
		 	return true;
	 }
	 
	@SuppressWarnings("unchecked")
	public ArrayList<String>  listDomains(boolean admin)
	 {
	    	ArrayList<String> temp;
	        PersistenceManager pm = PMF.get().getPersistenceManager();
	    	temp = new ArrayList<String>();
	    	
	    	Query query = pm.newQuery(DomainRec.class);
	    	List<DomainRec> result = (List<DomainRec>) query.execute();
	    	
	    	for (DomainRec dom : result)
	    	{
	    		temp.add(dom.getDomain());
	    	}    	
	    	return temp;
	 }

	 public ArrayList<Group> listGroups(String Domain)
	 {
         FindDomain       dom = new FindDomain(Domain);
         ArrayList<Group> temp = new ArrayList<Group>(); 
         ContactsService grpService = null;

			try {
				grpService = MirContacts.ConnectService(dom, dom.password);
			} 
			catch (AuthenticationException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	        ContactGroupFeed feed1 = null;
			try {
				feed1 = MirContacts.retrieveGroupsFull(grpService, Domain);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				return null;
			}
	        for (ContactGroupEntry grp : feed1.getEntries())
	        {
	        	Group group = new Group();
	        	group.setName(grp.getTitle().getPlainText());
	        	group.setHref(grp.getId());
	           temp.add(group);	
	        }
	        return temp;
	 }
	 
	 public ArrayList<Contact> listContacts(String Token) 
	 {
		ArrayList<Contact> temp = null;
	    ContactFeed feed1 = null;

	    temp = new ArrayList<Contact>();
        FindDomain   dom = new FindDomain(Token);

	    try 
	    {
		    feed1 = MirContacts.retrieveContactsFull(dom.getUser(), dom.getToken(), dom.getDomain(), 1);
	    } 
	    catch (AppsForYourDomainException e) 
	    {
		           return temp;
	    } 
	    catch (ServiceException e) 
	    {
		           return temp;
	    } 
	    catch (IOException e) 
	    {
		           return temp;
	    }
		
		feed1.getEntries().size();
		for (ContactEntry entry : feed1.getEntries()) 
		{				
		    		Contact c = new Contact();
					Transfer.TransferFromEntry(entry, c);
	    		    temp.add(c);	
		}
	    return temp;
	 }

	    public String removeContact(String domain, ArrayList<ContactOperation> ids) 
	    {
	    	String temp = "";
	        try 
	        {
				return MirContacts.batchRemove("contacts", domain, ids);
			} 
	        catch (IOException e) 
	        {
				// TODO Auto-generated catch block
				temp = "io: " + e.getMessage();
			} catch (ServiceException e) 
			{
				// TODO Auto-generated catch block
				temp = "se: " + e.getInternalReason() + e.getResponseBody();
			}
			return temp;
	    }

	    public String addContact(String Domain, ContactDetails contact) 
	    {
	    	ContactEntry insertcontact = new ContactEntry();
			Transfer.TransferToEntry(contact, insertcontact); 
	    	    
	    	try 
	    	{
	    		String          srvURL = "http://www.google.com/m8/feeds/contacts/" + Domain + "/full";
	    		URL url = new URL(srvURL);
	    		
		    	MirContacts.insertEntry(Domain, url, insertcontact);
		    	return "Added.";
		    } 
	    	catch (AppsForYourDomainException e) 
		    {
				// TODO Auto-generated catch block	                 
		           return "AE: " + e.getInternalReason() + e.getResponseBody() ;
			} 
		    catch (ServiceException e) 
			{
				// TODO Auto-generated catch block
		           return "SE: " + e.getInternalReason() + e.getResponseBody();
			} 
		    catch (IOException e) 
		    {
				// TODO Auto-generated catch block
		           return "IO: " + e.getMessage();
			}
	    }
	    
	    public String updateContact(String domain, ContactDetails contact) 
	    {        
	    	

	    	try {
				ContactEntry E = MirContacts.getContactInternal(domain, contact.getId());
				;
			/*	
				if (contact.getEtag() != E.getEtag())
				{
					return "Contact has changed. Do a Refresh and try again (" + contact.getEtag() + ", " + E.getEtag() + ")";
				}
				*/
				Transfer.TransferToEntry(contact, E); 
				MirContacts.updateEntry(domain, E);
				return "Updated.";
			} catch (IOException e) {
		           return "IO: " + e.getMessage();
			} catch (ServiceException e) {
		           return "SE: " + e.getInternalReason() + e.getResponseBody();
			}
	    }
	    
		public String syncContacts(String fromDomain, ArrayList<String> ids,
				String toDomain) {
			// TODO Auto-generated method stub
		    String temp;
			try 
			{
				return SyncContacts.syncContact(fromDomain, ids, toDomain);
			} 
			catch (AppsForYourDomainException e) 
			{
				// TODO Auto-generated catch block
				temp = e.getInternalReason();
			} 
			catch (ServiceException e) 
			{
				// TODO Auto-generated catch block
				temp = e.getInternalReason();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				temp = e.getMessage();;
			}
			return temp;
		}

		public ContactDetails retrieveContact(String Domain, String Id) {
			// TODO Auto-generated method stub
			return MirContacts.retrieveContact(Domain, Id);
		}

}
