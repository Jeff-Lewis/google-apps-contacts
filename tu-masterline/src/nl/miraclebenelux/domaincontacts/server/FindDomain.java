package nl.miraclebenelux.domaincontacts.server;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class FindDomain 
{	
    DomainRec e;
    
    FindDomain(String Token)
    {	
        PersistenceManager pm = PMF.get().getPersistenceManager();

        Key k = KeyFactory.createKey(DomainRec.class.getSimpleName(), Token);

        this.e = pm.getObjectById(DomainRec.class, k);
    }
    
    
    public String getUser()
    {
    	return e.getUsername();
    }
    
    public String getToken()
    {
    	return e.getToken();
    }
    
    public String getToken2()
    {
    	return e.getToken2();
    }
    
    public boolean getAdmin()
    {
    	return e.getAdmin();
    }
    
    public String getDomain()
    {
    	return e.getDomain();
    }
}
