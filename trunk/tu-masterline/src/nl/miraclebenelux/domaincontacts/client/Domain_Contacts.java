package nl.miraclebenelux.domaincontacts.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Domain_Contacts implements EntryPoint 
{
    private ContactListGUI gui;
    private ContactServiceDelegate delegate;  
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() 
    {    
        gui = new ContactListGUI();
        delegate = new ContactServiceDelegate();
        gui.contactService = delegate;
        delegate.gui = gui;
        gui.init();
        delegate.gui.status.setText("Retrieving Domains ....");
        delegate.listDomains(true);
        wireGUIEvents();
    }

    private void wireGUIEvents() 
    {
    	gui.searchButton.addClickHandler(new ClickHandler()
    	{
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (gui.searchField.getValue() == "")
				{
					gui.onClearClicked();
					gui.status.setText("Nothing to search.");
					return;
				}
				gui.onSearchClicked(gui.searchField.getText());		
			}	
    	});
        gui.deleteButton.addClickHandler(new ClickHandler()
        {
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				gui.gui_deleteButtonClicked();
			}   	
        });       
        gui.cancelButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				gui.gui_eventCancelButtonClicked();
			}
        });
        gui.addButton.addClickHandler(new ClickHandler()
        {
            public void onClick(ClickEvent event) {
                gui.gui_eventAddButtonClicked();
            }
        });
        gui.editButton.addClickHandler(new ClickHandler()
        {
        	public void onClick(ClickEvent event)
        	{
        		gui.gui_eventEditButtonClicked();
        	}
        });      
        gui.syncButton.addClickHandler(new ClickHandler()
        {
        	public void onClick(ClickEvent event) {
        		gui.gui_syncButtonClicked();
        	}
        });
        gui.syncContactButton.addClickHandler(new ClickHandler()
        {
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				gui.gui_syncContactButtonClicked();
			}
        });
        gui.refreshButton.addClickHandler(new ClickHandler() 
        {
            public void onClick(ClickEvent event) 
            {
                gui.gui_eventRefreshButtonClicked();
            }
        });     
        gui.listDom.addChangeHandler(new ChangeHandler() 
        {
        	public void onChange(ChangeEvent event)
            {
                int index = gui.listDom.getSelectedIndex();
                delegate.gui.Domain = gui.listDom.getItemText(index);       
                delegate.gui.status.setText("Retrieving contacts for: " + delegate.gui.Domain);
                delegate.listContacts(delegate.gui.Domain);
            }
        });        
    }
}
