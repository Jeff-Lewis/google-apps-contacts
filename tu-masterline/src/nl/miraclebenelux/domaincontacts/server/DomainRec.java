package nl.miraclebenelux.domaincontacts.server;

import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DomainRec 
{
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private String Username;

    @Persistent
    private String Principal;
    
    @Persistent
    private String Token2;
    
    @Persistent
    private String Token;
    
    @Persistent
    private String Domain;
    
    @Persistent
    private boolean Admin;
    
    public DomainRec()
    {
    	
    }
    
    public DomainRec(String Username, String Token, boolean Admin)
    {
    	this.setUsername(Username);
    	this.setToken(Token);
    	this.setAdmin(Admin);
    }
    
    public DomainRec(String Username, String Token2, String Token, String Domain, boolean Admin)
    {
    	this.Username = Username;
    	this.Domain   = Domain;
    	this.setToken2(Token2);
    	this.setToken(Token);
    	this.setAdmin(Admin);
    }
    
    public Key getkey()
    {
    	return this.key;
    }
    
    public void setKey(Key key)
    {
    	this.key = key;
    }
    
    public String getUsername()
    {
    	return this.Username;
    }
    
    public void setUsername(String Username)
    {
    	this.Username = Username;
    }
     
    public String getDomain()
    {
    	return this.Domain;    	
    }
    
    public void setDomain(String Domain)
    {
    	this.Domain = Domain;
    }

	public void setToken(String token) {
		Token = token;
	}

	public String getToken() {
		return Token;
	}

	public void setAdmin(boolean admin) {
		Admin = admin;
	}

	public boolean getAdmin() {
		return Admin;
	}

	public void setToken2(String token2) {
		Token2 = token2;
	}

	public String getToken2() {
		return Token2;
	}

	public void setPrincipal(String principal) {
		Principal = principal;
	}

	public String getPrincipal() {
		return Principal;
	}
}