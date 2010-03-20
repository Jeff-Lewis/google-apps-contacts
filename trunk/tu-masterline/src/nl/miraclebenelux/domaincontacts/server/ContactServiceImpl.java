package nl.miraclebenelux.domaincontacts.server;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import nl.miraclebenelux.domaincontacts.client.Contact;
import nl.miraclebenelux.domaincontacts.client.ContactDetails;
import nl.miraclebenelux.domaincontacts.client.ContactOperation;
import nl.miraclebenelux.domaincontacts.client.ContactService;
import nl.miraclebenelux.domaincontacts.client.Group;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ContactServiceImpl extends RemoteServiceServlet implements ContactService 
{
    private static final long serialVersionUID = 1L;
    private static String USER_SESSION = "GWTAppUser";
    
    private ContactDAO contactDAO = new ContactDAOMock();

    public void setUserInSession(String user)
    {
    	HttpSession session = getThreadLocalRequest().getSession();
    	session.setAttribute(USER_SESSION, user);
    }
    
    public String getUserFromSession()
    {
    	HttpSession session = getThreadLocalRequest().getSession();
    	return (String) session.getAttribute(USER_SESSION);
    }
    
    public String addContact(String Domain, ContactDetails contact) 
    {
        return contactDAO.addContact(Domain, contact);
    }
    
    public String syncContacts(String fromDomain, String toDomain, Integer startindx)
    {
			return contactDAO.syncContacts(fromDomain, toDomain, startindx);
    }
    
    public ArrayList<String>  listDomains(boolean admin)
    {
        ArrayList<String>  listDomains = contactDAO.listDomains(admin);
        return new ArrayList<String>(listDomains);
    }
    
    public ArrayList<Contact> listContacts(String Domain) 
    {
        ArrayList<Contact> listContacts = contactDAO.listContacts(Domain);
        return new ArrayList<Contact> (listContacts);
    }
  
    public String removeContact(String domain, ArrayList<ContactOperation> ids) 
    {
        return contactDAO.removeContact(domain, ids);    
    }

    public String updateContact(String domain, ContactDetails contact) 
    {
        return contactDAO.updateContact(domain, contact);
    }
    
    public void login(String username, String password, String Domain)
    {
    	contactDAO.login(username, password, Domain);
    }

	public void addGroup(String Domain, String group) {
		// TODO Auto-generated method stub		
	}

	public ArrayList<Group> listGroups(String Domain) 
	{
		// TODO Auto-generated method stub
		return contactDAO.listGroups(Domain);
	}

	public String removeGroup(String domain, String group) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateGroup(String domain, String oldgroup, String newgroup) {
		// TODO Auto-generated method stub
		
	}

	public String syncContacts(String fromDomain, ArrayList<String> ids,
			String toDomain) {
		// TODO Auto-generated method stub
		return contactDAO.syncContacts(fromDomain, ids, toDomain);

	}

	public String syncContacts(String fromDomain, List<String> ids,
			String toDomain) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContactDetails retrieveContact(String Domain, String id) {
		// TODO Auto-generated method stub
		return contactDAO.retrieveContact(Domain, id);
	}
}