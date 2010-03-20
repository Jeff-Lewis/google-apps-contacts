package nl.miraclebenelux.domaincontacts.server;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class SyncRec 
{
	    @PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    private Key key;
	    
	    @Persistent
	    private DateTime when;
	    
	    @Persistent
	    private String fromDomain;

	    @Persistent
	    private String toDomain;
	    
	    @Persistent
	    private int    contacts;
	    
	    @Persistent
	    private boolean error;
	    
	    @Persistent
	    private int startIndx;
	    
	    
	    public SyncRec(DateTime when, String fromDomain, String toDomain, int contacts, boolean error, DateTime updatedMin, int startIndx)
	    {
	    	this.setWhen(when);
	    	this.setFromDomain(fromDomain);
	    	this.setToDomain(toDomain);
	    	this.setContacts(contacts);
	    	this.setError(error);
	    	this.setStartIndx(startIndx);
	    }
	    
	    public Key getkey()
	    {
	    	return this.key;
	    }

		public void setWhen(DateTime when) {
			this.when = when;
		}

		public DateTime getWhen() {
			return when;
		}

		public void setFromDomain(String fromDomain) {
			this.fromDomain = fromDomain;
		}

		public String getFromDomain() {
			return fromDomain;
		}

		public void setToDomain(String toDomain) {
			this.toDomain = toDomain;
		}

		public String getToDomain() {
			return toDomain;
		}

		public void setContacts(int contacts) {
			this.contacts = contacts;
		}

		public int getContacts() {
			return contacts;
		}

		public void setError(boolean error) {
			this.error = error;
		}

		public boolean isError() {
			return error;
		}

		public void setStartIndx(int startIndx) {
			this.startIndx = startIndx;
		}

		public int getStartIndx() {
			return startIndx;
		}   
}
