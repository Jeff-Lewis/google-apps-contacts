package nl.miraclebenelux.domaincontacts.client;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Options implements IsSerializable 
{
    private boolean sortorder;
    private boolean showdeleted;
    private int     startindx;
    private String  updated_min;

    public Options()
    {
    }
    
}
