package nl.miraclebenelux.domaincontacts.shared;

import java.io.Serializable;

public class Name  implements Serializable {
  
  protected String aditionalName;
  protected String familyName;
  protected String fullName;
  protected String givenName;
  protected String namePrefix;
  protected String nameSuffix;
  
  public Name() {  
  }
  
  public String getAditionalName() {
    return aditionalName;
  }

  public void setAditionalName(String aditionalName) {
    this.aditionalName = aditionalName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getNamePrefix() {
    return namePrefix;
  }

  public void setNamePrefix(String namePrefix) {
    this.namePrefix = namePrefix;
  }

  public String getNameSuffix() {
    return nameSuffix;
  }

  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }
}
