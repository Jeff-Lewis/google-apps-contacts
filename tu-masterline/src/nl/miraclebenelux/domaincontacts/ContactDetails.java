package nl.miraclebenelux.domaincontacts;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContactDetails implements IsSerializable {
	private static final long serialVersionUID = 1L;
	private String name = null;
	private ArrayList<String> titel;
	private ArrayList<String> email;
	private ArrayList<String> email_type;
	private ArrayList<String> phone;
	private ArrayList<String> phone_type;
	private ArrayList<String> adress;
	private ArrayList<String> adress_type;
	private ArrayList<String> imaddress;
	private ArrayList<String> imaddress_type;
	private ArrayList<String> groups;
	private ArrayList<String> organization;
	private HashMap<String, String> extendedproperties;
	private String photolink;
	private String note;
	private String etag;
	private String photo_etag;
	private String id;
	private String editlink;
	private String groupurl;
	private String source;
	private String updatedtime;
	private String birthday;
	// private DateTime updated;
	
	public ContactDetails()
	{
		super();
        this.email        = new ArrayList<String>();
		this.email_type   = new ArrayList<String>();
		this.phone        = new ArrayList<String>();
		this.phone_type   = new ArrayList<String>();
		this.adress       = new ArrayList<String>();
		this.adress_type  = new ArrayList<String>();
		this.groups       = new ArrayList<String>();
		this.organization = new ArrayList<String>();
		this.titel        = new ArrayList<String>();
		this.extendedproperties = new HashMap<String, String>();
	}
	
	public void setExtendedProperty(String name, String value)
	{
		extendedproperties.put(new String(name), new String(value));	      	
	}
	
	public String getExtenedProperty(String name)
	{
		return this.extendedproperties.get(name).toString();
	}
	
	public boolean hasExtendedProperty()
	{
		return (this.extendedproperties.size() > 0);
	}
	
	public String getName()
	{
		if (name == null)
		{
			return getEmail(0);
		}
		else
		{
			return name;
		}
	}
	
	public void setOrganization(String name)
	{
		this.organization.add(name);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setCompany(String company)
	{
		this.organization.add(company);
	}
	
	public void setBirthDay(String birthday)
	{
		this.birthday = birthday;
	}
	
	public String getBirthDay()
	{
		return this.birthday;
	}
	
	public ArrayList<String> getTitelEntries()
	{
		return this.titel;
	}
	
	public String getTitel(int i)
	{
		if (i > this.titel.size())
		{
			return "";
		}
		return this.titel.get(i);
	}
	
	public boolean hasTitels()
	{
		return (this.titel.size() > 0);
	}
	
	public void setTitel(String titel)
	{
	    this.titel.add(titel);	
	}
	
	public boolean hasOrganizations()
	{
		return (this.organization.size() > 0);
	}
	
	public ArrayList<String> getOrganizationEntries()
	{
		return this.organization;
	}
	
	public ArrayList<String> getEmailEntries()
	{
		return email;
	}
	
	public boolean hasEmail()
	{
		return (this.email.size() > 0);
	}
	
	public String getEmail(int indx)
	{
		if (email.size() > 0)
		{
		   return email.get(indx);
		}
		return "";
	}
	
	public void setEmail(String email, String ptype)
	{
		this.email.add(email);
		ptype = ptype.substring(ptype.lastIndexOf("#")+1);		
		this.email_type.add(ptype);
	}
	
	public void setEmail(ArrayList<String> email, ArrayList<String> rel)
	{
		this.email.clear();
		this.email.addAll(email);
		this.email_type.clear();
		this.email_type.addAll(rel);
	}
	
	public String getRel(int indx)
	{
		if (indx >= 0 && indx < email_type.size() )
		{
		   return email_type.get(indx);
		}
		return "";
	}
	
	public boolean hasAdress()
	{
		if (adress == null)
		{
			return false;
		}
		return (this.adress.size() > 0);
	}
	
	public String getAdress()
	{
		if (adress.size() > 0)
		{
		   return adress.get(0);
		}
		return "";
	}
	
	public ArrayList<String> getAdressEntries()
	{
		return adress;
	}
	
	public void setAdress(String adress, String ptype)
	{
		this.adress.add(adress);
		ptype = ptype.substring(ptype.lastIndexOf("#")+1);		
		this.adress_type.add(ptype);
	}
	
	public void setAdress(ArrayList<String> adres, ArrayList<String> rel)
	{
		if (adres != null & adres.size() > 0)
		{
		   this.adress.clear();
		   this.adress.addAll(adres);
		   this.adress_type.clear();
		   this.adress_type.addAll(rel);
		}
		else 
		{
			this.adress = null;
			this.adress_type = null;
		}
	}
	
	public String getAdressType(int i)
	{
		if (adress_type != null && adress_type.size() > 0)
		{
		   if (this.adress_type.size() > i)
		   {
		      return this.adress_type.get(i);
		   }
		   else 
		   {
			  return "";
		   }
		}
		return "";
	}
	
	public ArrayList<String> getPhoneEntries()
	{
		return (ArrayList<String>) phone;
	}
	
	public boolean hasPhone()
	{
		return (this.phone.size() > 0);
	}
	
	public String getPhone()
	{
		if (phone.size() > 0)
		{
		   return phone.get(0);
		}
		return "";
	}
	
	public void setPhone(String phone, String ptype)
	{
		this.phone.add(phone);
		ptype = ptype.substring(ptype.lastIndexOf("#")+1);	
		this.phone_type.add(ptype);
	}
	
	public void setPhone(ArrayList<String> phone, ArrayList<String> rel)
	{
		this.phone.clear();
		this.phone.addAll(phone);
		this.phone_type.clear();
		this.phone_type.addAll(rel);
	}
	
	public String getPhoneType(int i)
	{
		if (this.phone_type.size() > i)
		{
		   return this.phone_type.get(i);
		}
		else
		{
		   return "";
		}
	}
	
	public String getNote()
	{
		return note;
	}
	
	public void setNote(String note)
	{
		this.note = note;
	}
	
	public void setEtag(String etag)
	{
		this.etag = etag;
	}
	
	public String getEtag()
	{
		return this.etag;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void addGroup(String grp)
	{
		setGroupurl(grp.substring(0, grp.lastIndexOf("/")+1));
		groups.add(grp.substring(grp.lastIndexOf("/")+1));
	}	
	
	public boolean hasGroups()
	{
		return (this.groups.size() > 0);
	}
	
	public ArrayList<String> getGroups()
	{
		return (ArrayList<String>)groups;
	}
	public void setImaddress(ArrayList<String> imaddress) {
		this.imaddress = imaddress;
	}
	public ArrayList<String> getImaddress() {
		return imaddress;
	}
	public void setImaddress_type(ArrayList<String> imaddress_type) {
		this.imaddress_type = imaddress_type;
	}
	public ArrayList<String> getImaddress_type() {
		return imaddress_type;
	}
	public void setPhotolink(String photolink) {
		this.photolink = photolink;
	}
	public String getPhotolink() {
		return photolink;
	}
	public void setPhoto_etag(String photo_etag) {
		this.photo_etag = photo_etag;
	}
	public String getPhoto_etag() {
		return photo_etag;
	}
	public void setGroupurl(String groupurl) {
		this.groupurl = groupurl;
	}
	public String getGroupurl() 
	{
		return groupurl;
	}

	public void setEditlink(String editlink) 
	{
		this.editlink = editlink;
	}

	public String getEditlink() {
		return editlink;
	}
	
	public void setSource(String src)
	{
		this.source = src;
	}
	
	public String getSource()
	{
		return this.source;
	}

	public void setUpdatedtime(String updatedtime) 
	{
		this.updatedtime = updatedtime;
	}

	public String getUpdatedtime() {
		return updatedtime;
	}
}
