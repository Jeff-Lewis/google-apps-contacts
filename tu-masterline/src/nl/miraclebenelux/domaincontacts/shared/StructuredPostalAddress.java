package nl.miraclebenelux.domaincontacts.shared;

import java.io.Serializable;

public class StructuredPostalAddress implements Serializable {
  
  private static final long serialVersionUID = 1L;
	
  /** Google data (GD) namespace */
  public static final String g = "http://schemas.google.com/g/2005";
  public static final String gPrefix = g + "#";
  
  /** The email type. */
  public static final class Rel {    
    public static final String HOME = gPrefix + "home";
    public static final String WORK = gPrefix + "work";
    public static final String OTHER = gPrefix + "other";
  }
  
  /** The mail class. */
  public static final class MailClass {
    public static final String BOTH = gPrefix + "both";    
    public static final String LETTERS = gPrefix + "letters";    
    public static final String NEITHER = gPrefix + "neither";    
    public static final String PARCELS = gPrefix + "parcels";

  }
  
  /** The context for the address use. */
  public static final class Usage {
    public static final String GENERAL = gPrefix + "general";
    public static final String LOCAL = gPrefix + "local";
  }
  
  protected String rel = "";
  protected String label = "";
  protected String usage = "";
  protected String mailClass = "";
  protected boolean primary = false;
  
  protected String Agent = "";
  protected String City = "";
  protected String Country = "";
  protected String FormattedAddress = "";
  protected String HouseName = "";
  protected String Neighborhood = "";
  protected String PoBox = "";
  protected String PostCode = "";
  protected String Region = "";
  protected String Street = "";
  protected String Subregion = "";
      
  public StructuredPostalAddress() {    
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

  public String getUsage() {
    return usage;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public String getMailClass() {
    return mailClass;
  }

  public void setMailClass(String mailClass) {
    this.mailClass = mailClass;
  }

  public boolean isPrimary() {
    return primary;
  }

  public void setPrimary(boolean primary) {
    this.primary = primary;
  }

  public String getAgent() {
    return Agent;
  }

  public void setAgent(String agent) {
    Agent = agent;
  }

  public String getCity() {
    return City;
  }

  public void setCity(String city) {
    City = city;
  }

  public String getCountry() {
    return Country;
  }

  public void setCountry(String country) {
    Country = country;
  }

  public String getFormattedAddress() {
    return FormattedAddress;
  }

  public void setFormattedAddress(String formattedAddress) {
    FormattedAddress = formattedAddress;
  }

  public String getHouseName() {
    return HouseName;
  }

  public void setHouseName(String houseName) {
    HouseName = houseName;
  }

  public String getNeighborhood() {
    return Neighborhood;
  }

  public void setNeighborhood(String neighborhood) {
    Neighborhood = neighborhood;
  }

  public String getPoBox() {
    return PoBox;
  }

  public void setPoBox(String poBox) {
    PoBox = poBox;
  }

  public String getPostCode() {
    return PostCode;
  }

  public void setPostCode(String postCode) {
    PostCode = postCode;
  }

  public String getRegion() {
    return Region;
  }

  public void setRegion(String region) {
    Region = region;
  }

  public String getStreet() {
    return Street;
  }

  public void setStreet(String street) {
    Street = street;
  }

  public String getSubregion() {
    return Subregion;
  }

  public void setSubregion(String subregion) {
    Subregion = subregion;
  }
}
