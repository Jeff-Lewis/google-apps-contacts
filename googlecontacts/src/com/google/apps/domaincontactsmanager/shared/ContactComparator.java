package com.google.apps.domaincontactsmanager.shared;


import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {
  public int compare(Contact contact1, Contact contact2) {
    if (contact1.title.length() > 0 && contact2.title.length() > 0 )
      return contact1.title.compareToIgnoreCase(contact2.title);
    if ( contact1.title.length() > 0 )
      return -1;
    else
      return 1;
  }
}