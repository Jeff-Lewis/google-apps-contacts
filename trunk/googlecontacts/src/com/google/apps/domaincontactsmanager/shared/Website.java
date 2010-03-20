package com.google.apps.domaincontactsmanager.shared;

import java.io.Serializable;

public class Website implements Serializable {
  /** Google data (GD) namespace */
  public static final String g = "http://schemas.google.com/g/2005";
  public static final String gPrefix = g + "#";
  
  /** The website type. */
  public static final class Rel {    
    public static final String HOMEPAGE = gPrefix + "home-page";
    public static final String BLOG = gPrefix + "blog";
    public static final String PROFILE = gPrefix + "profile";
    public static final String HOME = gPrefix + "home";
    public static final String WORK = gPrefix + "work";
    public static final String OTHER = gPrefix + "other";
    public static final String FTP = gPrefix + "ftp";
  }
  
  protected String rel = "";
  protected String label = "";
  protected String url = "";
  protected boolean primary = false;
  
  public Website(){
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isPrimary() {
    return primary;
  }

  public void setPrimary(boolean primary) {
    this.primary = primary;
  }  
}
