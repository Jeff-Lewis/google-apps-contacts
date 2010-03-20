package nl.miraclebenelux.domaincontacts.server;

import java.io.Serializable;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class OAuthCredential implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @PrimaryKey
  private String domain;
  
  @Persistent
  private String oauthKey;
  
  @Persistent
  private String emailAdmin;
  
  public OAuthCredential() {  
  }
  
  public OAuthCredential( String domain, String oauthKey, String emailAdmin ) {
    this.domain = domain;
    this.oauthKey = oauthKey;
    this.emailAdmin = emailAdmin;
  }
  
  public String  getDomain() {
    return domain;
  }
  
  public String  getOAuthKey() {
    return oauthKey;
  }
  
  public String  getEmailAdmin() {
    return emailAdmin;    
  }
  
  public String setDomain( String domain ) {
    return this.domain = domain;
  }
  
  public String setOAuthKey( String oauthKey ) {
    return this.oauthKey = oauthKey;
  }
  
  public String setEmailAdmin(String emailAdmin) {
    return this.emailAdmin = emailAdmin;
  }
}