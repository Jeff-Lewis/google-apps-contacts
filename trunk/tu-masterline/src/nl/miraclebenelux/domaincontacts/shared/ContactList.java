package nl.miraclebenelux.domaincontacts.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactList implements Serializable {

  private static final long serialVersionUID = 1L;

  protected String title = "";
  protected String id = "";
  protected Boolean systemGroup = false;
  public List<Contact> contacts = new ArrayList<Contact>();

  public ContactList(){
  }
  
  public void sort() {
    Collections.sort( contacts, new ContactComparator() );
  }
  
  public String getTitle() {
    return title;
  }	
  
  public void setTitle(String title) {
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getSystemGroup() {
    return systemGroup;
  }

  public void setSystemGroup(Boolean systemGroup) {
    this.systemGroup = systemGroup;
  }
}