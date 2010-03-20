package nl.miraclebenelux.domaincontacts.server;

import java.util.Hashtable;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.Birthday;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.contacts.Website;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.ParseException;

import nl.miraclebenelux.domaincontacts.shared.Contact;
import nl.miraclebenelux.domaincontacts.client.Group;

public class Transfer 
{
	public static void TransferToEntry(Contact from, ContactEntry to)
	{
        int indx;
       
		Name name = new Name();
        name.setFullName(new FullName(from.getName(), null));
        to.setName(name);
        to.setContent(new PlainTextConstruct(from.getNote()));
        to.setId(from.getId());
        to.setEtag(from.getEtag());
     
        if (from.hasOrganizations())
        {
        	int titindx = 0;
        	for (String org1 : from.getOrganizationEntries())
        	{
                Organization org      = new Organization();
                OrgName      orgnam   = new OrgName();
                OrgTitle     orgTitle = new OrgTitle();
                String       tit      = from.getTitel(titindx);
                
                orgnam.setValue(org1);
                org.setRel("http://schemas.google.com/g/2005#work");
                org.setOrgName(orgnam);
                orgTitle.setValue(tit);
                org.setOrgTitle(orgTitle);
               
                to.getOrganizations().clear();
                
                to.addOrganization(org);
                titindx++;
        	}       
        }
	    
        indx = 0;
        if (from.hasEmail())
        {
           to.getEmailAddresses().clear();
           for (String usermail : from.getEmailEntries())
           {
               Email mail = new Email();
               mail.setAddress(usermail);
               mail.setRel("http://schemas.google.com/g/2005#"+ from.getRel(indx));
               to.addEmailAddress(mail);
               indx++;
           }
        }
        
        indx = 0;
        if (from.hasAdress())
        {
           to.getStructuredPostalAddresses().clear();
           for (String adres : from.getAdressEntries())
           {
               StructuredPostalAddress address = new StructuredPostalAddress();
               address.setRel("http://schemas.google.com/g/2005#" + from.getAdressType(indx));
            
               address.setPrimary(false);
               FormattedAddress p = new FormattedAddress(adres);
            
               address.setFormattedAddress(p);

                  to.addStructuredPostalAddress(address);

           }	  
        }
        
        Birthday birthday = new Birthday();
        birthday.setValue(from.getBirthDay());
        to.setBirthday(birthday);
        
        indx = 0;
        if (from.hasPhone())
        {
 
        	to.getPhoneNumbers().clear();
        	for (String phone : from.getPhoneEntries())
        	{
        		PhoneNumber phoneNumber = new PhoneNumber();
        		phoneNumber.setPhoneNumber(phone);
        		phoneNumber.setRel("http://schemas.google.com/g/2005#" + from.getPhoneType(indx));

        	    to.addPhoneNumber(phoneNumber);

        		indx++;
        	}
        }
        from.getBirthDay();      
	}
	
	public static void TransferFromEntry(ContactEntry contactEntry, Contact contact)
	{
		Hashtable<String,String> groupsHash = new Hashtable<String,String>();
		for ( ContactGroupEntry groupEntry : contactGroupFeed.getEntries() ) {
			String title = groupEntry.getTitle().getPlainText().replaceAll("System Group:", "");
			groupsHash.put(groupEntry.getId(), title);
		}
		
		contact.setTitle(contactEntry.getTitle().getPlainText());
		contact.setId(contactEntry.getId());
		
		// Add the Contact's Notes
		try {
			String notes = contactEntry.getTextContent().getContent().getPlainText();
			contact.setNotes(notes);
		} catch (IllegalStateException e) {
		   contact.setNotes("");
		}
		// Add Emails to the Contact      
		for (Email email : contactEntry.getEmailAddresses()) {
			String rel = null;
			if (email.getRel() != null)
				rel = email.getRel().replace("http://schemas.google.com/g/2005#", "");
			contact.emails.add(
					new nl.miraclebenelux.domaincontacts.shared.Email(email.getAddress(),rel));
		}
		// Add Phones to the Contact
		for (PhoneNumber phoneNumber : contactEntry.getPhoneNumbers()) {
			String rel = null;
			if (phoneNumber.getRel() != null)
				rel = phoneNumber.getRel().replace("http://schemas.google.com/g/2005#", "");
			contact.phoneNumbers.add(
					new nl.miraclebenelux.domaincontacts.shared.PhoneNumber(
							phoneNumber.getPhoneNumber(),rel));
		}
		// Add Organizations to the Contact
		for ( Organization organization : contactEntry.getOrganizations()){
			nl.miraclebenelux.domaincontacts.shared.Organization newOrganization = 
				new nl.miraclebenelux.domaincontacts.shared.Organization(); 
			if (organization.getOrgName() != null)
				newOrganization.setOrgName(organization.getOrgName().getValue());
			if (organization.getOrgTitle() != null)
				newOrganization.setOrgTitle(organization.getOrgTitle().getValue());
			if (organization.getOrgDepartment() != null)
				newOrganization.setOrgDepartment(organization.getOrgDepartment().getValue());
			if (organization.getOrgJobDescription() != null)
				newOrganization.setOrgJobDescription(organization.getOrgJobDescription().getValue());
			if (organization.getLabel() != null)
				newOrganization.setLabel(organization.getLabel());
			if (organization.getPrimary() != null)
				newOrganization.setPrimary(organization.getPrimary());
			contact.organizations.add(newOrganization);
		}
		// Add StructuredPostalAddresses to the Contact
		for (StructuredPostalAddress structuredPostalAddress : contactEntry.getStructuredPostalAddresses()){
			nl.miraclebenelux.domaincontacts.shared.StructuredPostalAddress newAddress = 
				new nl.miraclebenelux.domaincontacts.shared.StructuredPostalAddress();
			if (structuredPostalAddress.getFormattedAddress() != null)
				newAddress.setFormattedAddress(structuredPostalAddress.getFormattedAddress().getValue());
			contact.structuredPostalAddresses.add(newAddress);
		}
		// Add Websites to the Contact
		for (Website website : contactEntry.getWebsites()){
			nl.miraclebenelux.domaincontacts.shared.Website newWebsite = 
				new nl.miraclebenelux.domaincontacts.shared.Website();
			if (website.getHref() != null)
				newWebsite.setUrl(website.getHref());
			if (website.getLabel() != null)
				newWebsite.setLabel(website.getLabel());
			if (website.getPrimary() != null)
				newWebsite.setPrimary(website.getPrimary());
			if (website.getRel() != null)
				newWebsite.setRel(website.getRel().toValue());
			contact.websites.add(newWebsite);
		}
	    // Add Group information
	    for (GroupMembershipInfo groupInfo : contactEntry.getGroupMembershipInfos() ) {
	    	String groupId = groupInfo.getHref();
	      	String groupName = groupsHash.get(groupId);
	      	contact.groups.put(groupId,groupName);  
	    }
	}
		

	

	
	public static void TransferGroupToEntry(Group from, ContactGroupEntry to)
	{
		
	}
	
	public static void TransferGroupFromEntry(ContactGroupEntry from, Group to)
	{
		
	}
}
