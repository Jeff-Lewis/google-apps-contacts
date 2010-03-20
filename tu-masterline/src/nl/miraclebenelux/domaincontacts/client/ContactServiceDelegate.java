package nl.miraclebenelux.domaincontacts.client;

import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContactServiceDelegate 
{
    private ContactServiceAsync contactService = GWT.create(ContactService.class);
    ContactListGUI gui;
    
    int syncContacts(String fromDomain, String toDomain, int startindx)
    {
    	gui.status.setText("Syncing "+ fromDomain + " to " + toDomain);
    	contactService.syncContacts(fromDomain, toDomain, startindx, new AsyncCallback<String> () 
    	{
    		public void onFailure(Throwable caught) 
    		{
    			gui.servicesyncContactsFailure(caught);
    		}
    		public void onSuccess(String res) 
    		{		
    			gui.servicesyncContactsSuccess(res);
    		}
    	});
    	return 1;
    }
    
    void listDomains(final boolean admin)
    {
    	gui.status.setText("Retrieving Domains ....");
    	contactService.listDomains(admin, new AsyncCallback<ArrayList<String>> () 
    	{
    		public void onFailure(Throwable caught) 
    		{
    			gui.serviceListDomainFailed(caught);
    		}		
    		public void onSuccess(ArrayList<String> result) 
    		{
    			gui.service_eventListDomainsRetrievedFromService(result);
    		}
    	});  	
    }
     
    void listContacts(final String Domain) 
    {
        contactService.listContacts(Domain, new AsyncCallback<ArrayList<Contact>> () 
        {
                    public void onFailure(Throwable caught) {
                        gui.service_eventListContactsFailed(caught);
                    }
        
                    public void onSuccess(ArrayList<Contact> result) {
                        gui.service_eventListRetrievedFromService(result);
                        
                    }
        }//end of inner class
        );//end of listContacts method call.
        
        contactService.listGroups(Domain, new AsyncCallback<ArrayList<Group>> () 
        {
        	    public void onFailure(Throwable caught)
        		{
        		    gui.service_eventListGroupsFailure();
        		}

				public void onSuccess(ArrayList<Group> result) 
				{
					// TODO Auto-generated method stub
					gui.service_eventListGroupsSuccess(result);
				}
        	
        		});
    }
    
    void addContact(final ContactDetails c) {
        contactService.addContact(this.gui.Domain, c, new AsyncCallback<String> () {
            public void onFailure(Throwable caught) {
                gui.service_eventAddContactFailed(caught);
            }

            public void onSuccess(String result) {
                gui.service_eventAddContactSuccessful(result);
            }
        }//end of inner class
        );//end of addContact method call.        
    }

    void updateContact(final ContactDetails c) 
    {
        contactService.updateContact(this.gui.Domain, c, new AsyncCallback<String> () 
        {
            public void onFailure(Throwable caught) 
            {
                gui.service_eventUpdateContactFailed(caught);
            }
            public void onSuccess(String result) 
            {
                gui.service_eventUpdateSuccessful(result);
            }
        }//end of inner class
        );//end of updateContact method call.        
    }

    void removeContact(final ArrayList<ContactOperation> ids) 
    {
        contactService.removeContact(gui.listDom.getValue(gui.listDom.getSelectedIndex()), ids, new AsyncCallback<String> () 
        {
            public void onFailure(Throwable caught) 
            {
                gui.service_eventRemoveContactFailed(caught);
            }

            public void onSuccess(String result) 
            {
                gui.service_eventRemoveContactSuccessful(result);
              //  listContacts(gui.listDom.getValue(gui.listDom.getSelectedIndex()));
            }
        }//end of inner class
        );//end of updateContact method call.        
    }

	public void syncContacts(String from, ArrayList<String> ids, String to) {
		// TODO Auto-generated method stub
		contactService.syncContacts(from, ids, to, new AsyncCallback<String> ()
		{

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
    			gui.servicesyncContactsFailure(caught);
			}

			public void onSuccess(String result) {
				// TODO Auto-generated method stub
	  			gui.servicesyncContactsSuccess(result);
			}
		});
	}

	public void retrieveContact(int row) {
		// TODO Auto-generated method stub
		Contact c = gui.contacts.get(row);
		contactService.retrieveContact(gui.listDom.getValue(gui.listDom.getSelectedIndex()), c.getId(), 
				new AsyncCallback<ContactDetails> ()
				{

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						gui.serviceretrieveContactFailure(caught);
					}

					public void onSuccess(ContactDetails result) {
						// TODO Auto-generated method stub
						gui.serviceretrieveContactSuccess(result);
					}
				});
	}
}