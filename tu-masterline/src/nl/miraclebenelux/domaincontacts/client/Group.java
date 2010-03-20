package nl.miraclebenelux.domaincontacts.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Group implements IsSerializable
{
	private static final long serialVersionUID = 1L;
	
	private String Id;
	private String Name;
	private String Updated = null;
	private boolean system = false;
	private String groupurl;
	
	public Group()
	{
		setSystem(false);
		setUpdated(null);
	}
	
	public void setName(String name)
	{
    	if (name.startsWith("System Group: "))
    	{	
    		this.Name = name.substring(14);
    		this.setSystem();
    	}
    	else
    	{
		    this.Name = name;
    	}
	}
	
	public String getName()
	{
		
		return this.Name;
	}
	
	public void setHref(String id)
	{
		groupurl = id.substring(0, id.lastIndexOf("/")+1);
		this.Id = id.substring(id.lastIndexOf("/")+1);
	}
	
	public String getHref()
	{
		return this.Id;
	}
	
	public String getBaseURL()
	{
		return groupurl;
	}
	public void setUpdated(String updated) {
		Updated = updated;
	}

	public String getUpdated() {
		return Updated;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}

	public boolean getSystem() {
		return system;
	}
	
	public void setSystem()
	{
		this.system = true;
	}
}
