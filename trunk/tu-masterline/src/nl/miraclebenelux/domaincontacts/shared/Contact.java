package nl.miraclebenelux.domaincontacts.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;
	protected String etag;
	protected String title;  
	protected String photoLink;
	protected String nickname;
	protected boolean deleted;
	protected String notes;
	protected Name name;

	public ArrayList<Email> emails = new ArrayList<Email>();
	public ArrayList<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
	public ArrayList<Organization> organizations = new ArrayList<Organization>();
	public ArrayList<Website> websites = new ArrayList<Website>();
	public ArrayList<StructuredPostalAddress> structuredPostalAddresses = new ArrayList<StructuredPostalAddress>();

	// <Id,Name>
	public Map<String,String> groups = new HashMap<String,String>();  

	public Contact() 
	{
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhotoLink() {
		return photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public ArrayList<Email> getEmails() {
		return emails;
	}

	public void setEmails(ArrayList<Email> emails) {
		this.emails = emails;
	}

	public ArrayList<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public ArrayList<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(ArrayList<Organization> organizations) {
		this.organizations = organizations;
	}

	public ArrayList<Website> getWebsites() {
		return websites;
	}

	public void setWebsites(ArrayList<Website> websites) {
		this.websites = websites;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}