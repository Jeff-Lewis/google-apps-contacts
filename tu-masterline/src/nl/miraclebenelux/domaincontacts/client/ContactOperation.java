package nl.miraclebenelux.domaincontacts.client;

/* import com.google.gdata.data.DateTime; */
import com.google.gwt.user.client.rpc.IsSerializable;

public class ContactOperation implements IsSerializable 
{
	static  enum operation { DELETE, UPDATE, INSERT};
	private String Etag;
	private String EditLink;
	private String Id;
//	private DateTime UpdateTime;
	
	public ContactOperation()
	{
		
	}

	public void setEtag(String etag) 
	{
		Etag = etag;
	}

	public String getEtag() 
	{
		return Etag;
	}

	public void setEditLink(String editLink) 
	{
		EditLink = editLink;
	}

	public String getEditLink() 
	{
		return EditLink;
	}
/*
	public void setUpdateTime(DateTime updateTime) {
		UpdateTime = updateTime;
	}

	public DateTime getUpdateTime() {
		return UpdateTime;
	}
	*/

	public void setId(String id) {
		Id = id;
	}

	public String getId() {
		return Id;
	}
	
}
