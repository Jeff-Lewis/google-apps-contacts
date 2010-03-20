package nl.miraclebenelux.domaincontacts.server;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.gdata.client.Query;
import com.google.gdata.client.batch.BatchInterruptedException;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchStatus;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.model.batch.BatchUtils;
import com.google.gdata.util.ServiceException;

public class SyncContacts 
{
		static int batchsize = 25;  // This is the max batch size for Batch Operations
		static int insertbatch = 25;
		static URL feedURL = null;
		static String sURL = null;
		static ContactsService fromService=null, 
		                         toService=null;
		static String projection = "full";
		static ContactFeed feed = null;
		
		public static String syncContact(String fromDomain, List<String> fromId, String toDomain)
		throws AppsForYourDomainException, ServiceException, IOException 
		{
			int teller = 0;
			String res = "";
			
			try
			{
			   FindDomain fromRec = new FindDomain(fromDomain);
			   FindDomain toRec   = new FindDomain(toDomain);
			
			   URL toURL   = new URL("http://www.google.com/m8/feeds/contacts/" + toDomain + "/full/batch");
			
			   ContactsService fromServ = new ContactsService("MiracleContacts3");
			   ContactsService toServ   = new ContactsService("MiracleContacts4");

			   connect(fromServ, fromRec.getUser(), fromRec.getPassword());
			   connect(toServ  ,   toRec.getUser(),   toRec.getPassword());	
			
	        ContactFeed feed = new ContactFeed();
            ContactFeed result;
			
			for (String id : fromId)
			{
		        ContactEntry c = fromServ.getEntry(new URL(id.replace("/base/", "/" + projection + "/")), ContactEntry.class);
		        
				ContactEntry dup = new ContactEntry(c);

				if (dup.hasGroupMembershipInfos())
				{
					List<GroupMembershipInfo> grp = dup.getGroupMembershipInfos();
					grp.clear();
				}
				
		    	BatchUtils.setBatchOperationType(dup, BatchOperationType.INSERT);
		    	feed.getEntries().add(dup);

				teller++;
			}
			try 
			{
				result = toServ.batch(toURL, feed);
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
		        if (BatchUtils.isSuccess(entry) && BatchUtils.getBatchOperationType(entry) == BatchOperationType.DELETE)
		        {
		        		res = res + "e";
		        }
		        else
		        {	
		        	BatchStatus stat = (BatchStatus) BatchUtils.getStatus(entry);
		        	res = res + stat.getReason() + " " + stat.getContent();
		        }
		    }
			}
			catch (AppsForYourDomainException e) 
			{
				return "AE: " + e.getInternalReason();
			} 
			catch (ServiceException e) 
			{
				return "SE: " + e.getMessage() + e.getResponseBody();
			} 
			catch (IOException e) 
			{
				return "IO: " + e.getMessage();
			}
			if (res != "")
			{
				return res;
			}
			return teller + " Contacts synced from " + fromDomain + " to " + toDomain;
		}
		
		public static String syncContact(String fromDomain, String toDomain, int startindx, DateTime updated) 
		throws AppsForYourDomainException, ServiceException, IOException 
		{
			String res = "";
		
			FindDomain fromRec = new FindDomain(fromDomain);
			FindDomain toRec   = new FindDomain(toDomain);
			ContactFeed feedFrom;
			int reti = 0;

			int indx = startindx;

			if (fromService != null)
			{
				fromService = null;
			}
			if (toService != null)
			{
				toService = null;
			}
			
			try 
			{
				URL toURL   = new URL("http://www.google.com/m8/feeds/contacts/" + toDomain + "/full/batch");
				fromService = new ContactsService("MiracleContacts1");
				connect(fromService, fromRec.getUser(), fromRec.getPassword());
				do
				{			
					feedFrom = retrieveContactsFull(fromRec.getDomain(), indx);
			
					for (ContactEntry contact : feedFrom.getEntries())
					{
						ContactEntry dup = new ContactEntry(contact);

						 if (dup.hasGroupMembershipInfos())
						 {
							List<GroupMembershipInfo> grp = dup.getGroupMembershipInfos();
							grp.clear();
						 }
	
						 res = BatchAdd(dup, toURL, toRec);
								
						 reti++;
					 }
				     if (res != "")
				    	return res;
				    
				     toService = null;
				     feed = null;
					 indx += batchsize;	
					 feedFrom = retrieveContactsFull(fromRec.getDomain(), indx);
				} 
				while (feedFrom.getEntries().size() > 0);
				
                BatchAdd(null, toURL, toRec);
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
			return "RET: " + Integer.toString(reti);
		}	
		
		private static String BatchAdd(ContactEntry dup, URL toURL, FindDomain toRec) throws BatchInterruptedException, IOException, ServiceException
		{
			String res = "";
			
			if (feed == null)
			{
				feed = new ContactFeed();
				toService   = new ContactsService("MiracleContacts2");
				connect(toService  ,   toRec.getUser(),   toRec.getPassword());	
				toService.setConnectTimeout(3600);
				toService.setReadTimeout(3600);
			}
			if (dup != null)
			{
          	   BatchUtils.setBatchOperationType(dup, BatchOperationType.INSERT);
	    	   feed.getEntries().add(dup);
			}
	    	if (dup == null || feed.getEntries().size() <= insertbatch )
	    	{
		       ContactFeed result;
		       result = toService.batch(toURL, feed);
		       
			   for (ContactEntry entry : result.getEntries())
			   {
			        if (BatchUtils.isSuccess(entry) && BatchUtils.getBatchOperationType(entry) == BatchOperationType.INSERT)
			        {
			        		
			        }
			        else
			        {	
			        	BatchStatus stat = (BatchStatus) BatchUtils.getStatus(entry);
			        	res = res + stat.getReason() + " " + stat.getContent();
			        }
			   }
			   feed = null;
			   toService = null;
	    	}
	    	return res;
		}
		
		private static void connect(ContactsService serv, String usrname, String passwd)
		throws AppsForYourDomainException, ServiceException, IOException
		{
	        serv.setCookieManager(null);
	        serv.setUserCredentials(usrname, passwd);		
		}
		
		public static ContactFeed retrieveContactsFull(String name, Integer indx) 
		throws AppsForYourDomainException, ServiceException, IOException 
		{       
		   URL feedURL = new URL("http://www.google.com/m8/feeds/contacts/" + name + "/full");
	        
	       Query myQuery = new Query(feedURL);
	        
	       //myQuery.setStringCustomParameter("sortorder", "ascending");
	       // 
	       if (indx > 1)
	       {
	        	myQuery.setStartIndex(indx);
	       }
	       else
	       {
	        	myQuery.setStartIndex(1);
	       }
	       myQuery.setStringCustomParameter("show-deleted", "false");        
	       myQuery.setMaxResults(batchsize);
	       return fromService.query(myQuery, ContactFeed.class);
		}
		
		private static ContactEntry getContactInternal(ContactsService serv, String id)
	    throws IOException, ServiceException 
	    {
	       return serv.getEntry(new URL(id.replace("/base/", "/" + projection + "/")), ContactEntry.class);
	    }
		
	    public static void deleteEntry(String id)
	    throws IOException, ServiceException 
	    {
	    	System.out.println("Deleting: "+id);
	    	// get the contact then delete them
	    	ContactEntry contact = getContactInternal(toService, id);
	    	if (contact == null) 
	    	{
	    		System.out.println("contact was null");
	    		return;
	    	}
	    	
	    	contact.delete();
	    }
	    
	    public static void insertEntry(String id)
	    throws IOException, ServiceException 
	    {
	    	// get the contact then delete them
	    	ContactEntry contact = getContactInternal(toService, id);
	    	if (contact == null) 
	    	{
	    		return;
	    	}	
	    	contact.delete();
	    }
	    
	    public static ContactEntry updateEntry(ContactEntry entryToUpdate)
	    throws IOException, ServiceException 
	    {
	    	URL editUrl = new URL(entryToUpdate.getEditLink().getHref());
	        return toService.update(editUrl, entryToUpdate);
	    }
	    
	    public static ContactEntry createEntry(ContactEntry entryToCreate)
	    throws IOException, ServiceException
	    {
	    	URL postUrl = new URL(sURL);
	    	return toService.insert(postUrl, entryToCreate);
	    }
	    
	    public int getBatchsize()
	    {
	    	return batchsize;
	    }
}