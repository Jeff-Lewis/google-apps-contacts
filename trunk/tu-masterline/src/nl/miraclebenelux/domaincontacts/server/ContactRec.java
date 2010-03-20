package nl.miraclebenelux.domaincontacts.server;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ContactRec 
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private String Name;

    @Persistent
    private String Id;
    
    @Persistent
    private String Domain;
    
    public ContactRec(String Domain, String Name, String Id)
    {
    	this.Name = Name;
    	this.Id   = Id;
    }
    
    public Key getkey()
    {
    	return this.key;
    }
    
    public String getName()
    {
    	return this.Name;
    }
    
    public void setName(String Name)
    {
    	this.Name = Name;
    }

    public String getId()
    {
    	return this.Id;	
    }
    
    public void setId(String Id)
    {
    	this.Id = Id;
    }
    
    public String getDomain()
    {
    	return this.Domain;    	
    }
    
    public void SetDomain(String Domain)
    {
    	this.Domain = Domain;
    }
}
