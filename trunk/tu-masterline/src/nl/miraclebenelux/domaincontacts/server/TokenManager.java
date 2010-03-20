package nl.miraclebenelux.domaincontacts.server;

import java.util.Hashtable;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Manages and stores AuthSub tokens.
 *
 * The TokenManager currently just uses a Hashtable to store the tokens
 * in-memory.  This is just for the purposes of the sample. Ideally, the
 * token should be stored in a database with the same security restrictions
 * as other sensitive material like user passwords.  Google limits the
 * number of AuthSub tokens generated per user per target and thus the tokens
 * have to be stored permanently and reused.
 *
 * 
 */
public class TokenManager {

  public static synchronized void storeToken(String principal, String token) 
  {
	      PersistenceManager pm = PMF.get().getPersistenceManager();
	      
	      DomainRec e;
	      Key k = null;
	      
	      try 
	      {
	         k = KeyFactory.createKey(DomainRec.class.getSimpleName(), principal);

	         e = pm.getObjectById(DomainRec.class, k);
	      }
	      catch (JDOObjectNotFoundException pEx)
	      {
    	      k = KeyFactory.createKey(DomainRec.class.getSimpleName(), principal);
	    	  e = new DomainRec(principal, token, "");
	    	  e.setKey(k);
	      }
	      try
	      {
	         pm.makePersistent(e);
	      } 
		      finally
	      {
	         pm.close();
	      }
  }

  public static synchronized String retrieveToken(String principal) 
  { 
	  FindDomain F = new FindDomain(principal);
      return F.getPassword();
  }

  public static synchronized void removeToken(String principal) {
 
  }
}