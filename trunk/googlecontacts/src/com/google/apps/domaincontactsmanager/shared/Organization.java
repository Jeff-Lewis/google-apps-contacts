package com.google.apps.domaincontactsmanager.shared;

import java.io.Serializable;

public class Organization implements Serializable {

  /** Google data (GD) namespace */
  public static final String g = "http://schemas.google.com/g/2005";
  public static final String gPrefix = g + "#";
  
  /** The organization type. */
  public static final class Rel {
    public static final String WORK = gPrefix + "work";
    public static final String OTHER = gPrefix + "other";
  }
  
  protected String rel = "";
  protected String label = "";
  protected boolean primary = false;
  protected String orgDepartment = "";
  protected String orgJobDescription = "";
  protected String orgName = "";
  protected String  orgTitle = "";

  public Organization()  {
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

  public boolean isPrimary() {
    return primary;
  }

  public void setPrimary(boolean primary) {
    this.primary = primary;
  }

  public String getOrgDepartment() {
    return orgDepartment;
  }

  public void setOrgDepartment(String orgDepartment) {
    this.orgDepartment = orgDepartment;
  }

  public String getOrgJobDescription() {
    return orgJobDescription;
  }

  public void setOrgJobDescription(String orgJobDescription) {
    this.orgJobDescription = orgJobDescription;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getOrgTitle() {
    return orgTitle;
  }

  public void setOrgTitle(String orgTitle) {
    this.orgTitle = orgTitle;
  }
}
