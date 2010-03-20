package com.google.apps.domaincontactsmanager.shared;

import java.io.Serializable;

public class Email implements Serializable {
  
  /** Google data (GD) namespace */
  public static final String g = "http://schemas.google.com/g/2005";
  public static final String gPrefix = g + "#";
  
  /** The email type. */
  public static final class Rel {    
    public static final String HOME = gPrefix + "home";
    public static final String WORK = gPrefix + "work";
    public static final String OTHER = gPrefix + "other";
  }
  
  protected String rel;
  protected String label;  
  protected String address;
  protected boolean primary;
  protected String displayName;
    
  public Email() {
  }
  
  public Email(String address, String rel) {
    this.address = address;
    this.rel = rel;
  }
   
  public String getRel() {
    return rel;
  }

  public void setRel(String rel) {
    this.rel = rel;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean isPrimary() {
    return primary;
  }

  public void setPrimary(boolean primary) {
    this.primary = primary;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}