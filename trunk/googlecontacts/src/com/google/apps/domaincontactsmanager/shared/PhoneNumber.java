package com.google.apps.domaincontactsmanager.shared;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
	
  private static final long serialVersionUID = 1L;
  
  /** Google data (GD) namespace */
  public static final String g = "http://schemas.google.com/g/2005";
  public static final String gPrefix = g + "#";

  /** The phone number type. */
  public static final class Rel {
    public static final String GENERAL = null;
    public static final String MOBILE = gPrefix + "mobile";
    public static final String HOME = gPrefix + "home";
    public static final String WORK = gPrefix + "work";
    public static final String WORK_MOBILE = gPrefix + "work_mobile";
    public static final String CALLBACK = gPrefix + "callback";
    public static final String ASSISTANT = gPrefix + "assistant";
    public static final String COMPANY_MAIN = gPrefix + "company_main";
    public static final String INTERNAL_EXTENSION = gPrefix + "internal-extension";
    public static final String FAX = gPrefix + "fax";
    public static final String HOME_FAX = gPrefix + "home_fax";
    public static final String WORK_FAX = gPrefix + "work_fax";
    public static final String OTHER_FAX = gPrefix + "other_fax";
    public static final String PAGER = gPrefix + "pager";
    public static final String WORK_PAGER = gPrefix + "work_pager";
    public static final String CAR = gPrefix + "car";
    public static final String SATELLITE = gPrefix + "satellite";
    public static final String RADIO = gPrefix + "radio";
    public static final String TTY_TDD = gPrefix + "tty_tdd";
    public static final String ISDN = gPrefix + "isdn";
    public static final String TELEX = gPrefix + "telex";
    public static final String OTHER = gPrefix + "other";
    public static final String MAIN = gPrefix + "main";
  }
  
  protected String rel;
  protected String label;
  protected String uri;
  protected String phoneNumber;
  protected boolean primary;

  public PhoneNumber() {
  }
  
  public PhoneNumber(String phoneNumber, String rel){
    this.phoneNumber = phoneNumber;
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

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public boolean isPrimary() {
    return primary;
  }

  public void setPrimary(boolean primary) {
    this.primary = primary;
  }
}
