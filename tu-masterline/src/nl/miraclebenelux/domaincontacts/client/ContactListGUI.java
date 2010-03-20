package nl.miraclebenelux.domaincontacts.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class ContactListGUI implements ClickHandler 
{
	//private boolean inedit = false;
	private int     row    = -2;
    /* Constants. */	
	String          Domain;
	private DockLayoutPanel dock;
    private ScrollPanel     SP1;
    private FlowPanel       FP0;
    private VerticalPanel   VP0, VP1;
   // private RichTextArea    RE0;
    private FlexTable		FT0, FT1, FTC, FTG;

    FlexCellFormatter FT1Format, FTCFormat, FTGFormat;
    /* GUI Widgets */
    protected Button addButton;
    protected Button editButton;
    protected Button deleteButton;
    protected Button cancelButton;
    protected Button syncButton;
    protected Button syncContactButton;
    protected Button searchButton;
    protected Button refreshButton;
    protected TextBox nameField;
    protected TextBox titleField;
    protected TextBox companyField;
    protected List<TextBox> emailField;
    protected List<ListBox>  emailType;
    protected List<TextBox> phoneField;
    protected List<CheckBox> grpcb;
    protected List<ListBox>  phoneType;
    protected TextBox etagField;
    protected SuggestBox searchField;
    protected MultiWordSuggestOracle oracle;
    protected List<TextArea> adresField;
    protected TextArea       noteField;
    protected List<ListBox>  adresType;
    protected ListBox emailType1;
    protected ListBox phoneType1;
    protected ListBox listDom;
    protected ListBox contactList;
    protected ListBox fromDomainList;
    protected ListBox toDomainList;
    protected List<CheckBox> contactBoxList;
    protected List<TextBox> groupField;
    protected Label status;
    protected Grid formGrid;
    protected Tree contactTree;
    
    protected String TypePhone;
    protected String TypeEmail;
    
    /* Data model */
    List<Contact> contacts;
    private ContactDetails currentContact;
    protected ContactServiceDelegate contactService;
        
    public void init() 
    {
    	addButton      = new Button("Add");
    	editButton     = new Button("Edit");
    	cancelButton   = new Button("Cancel");
    	cancelButton.setVisible(false);
    	contactList    = new ListBox();  	
    	
    	contactList.setVisibleItemCount(1);
    	listDom        = new ListBox();
    	listDom.setVisibleItemCount(1);
        fromDomainList = new ListBox();
        toDomainList   = new ListBox();
        fromDomainList.setVisibleItemCount(1);
        toDomainList.setVisibleItemCount(1);
        syncButton     = new Button("Sync");
        deleteButton   = new Button("Delete");
        searchButton   = new Button("Search");
        refreshButton  = new Button("Refresh");
        syncContactButton = new Button("Sync Contact");
        oracle         = new MultiWordSuggestOracle();
        nameField      = new TextBox();
        companyField   = new TextBox();
        titleField     = new TextBox();
        etagField      = new TextBox();
        searchField    = new SuggestBox(oracle);
        emailField     = new ArrayList<TextBox>();
        emailType      = new ArrayList<ListBox>();
        adresField     = new ArrayList<TextArea>();
        noteField      = new TextArea();
        adresType      = new ArrayList<ListBox>();
        groupField     = new ArrayList<TextBox>();
        phoneField     = new ArrayList<TextBox>();
        phoneType      = new ArrayList<ListBox>();
        grpcb          = new ArrayList<CheckBox>();    
        status         = new Label();
        placeWidgets();
    }  

    private void placeWidgets() 
    {	
    	// General Layout
        dock = new DockLayoutPanel(Unit.EM);
        dock.setSize("100%","100%");
        dock.setStyleName("con-menudock");    
        dock.addSouth(status, 4);
        
        // Top Menu 
        FP0 = new FlowPanel();
        FP0.add(listDom);
        FP0.add(refreshButton);
        FP0.add(searchField);
        FP0.add(searchButton);
        FP0.add(editButton);
        FP0.add(cancelButton);
        FP0.add(addButton);
        FP0.add(deleteButton);
        FP0.add(fromDomainList);
        FP0.add(toDomainList);
        FP0.add(syncButton);
        FP0.add(syncContactButton);

        DecoratorPanel P1= new DecoratorPanel();
        P1.setWidget(FP0);
        dock.addNorth(P1, 4);
 
        FTG = new FlexTable();
        FTGFormat = FTG.getFlexCellFormatter();

        FTG.addStyleName("cw-FlexTable");
        FTG.setWidth("15em");
        FTG.setCellPadding(0);
        FTG.setCellSpacing(0);
        
        VP0 = new VerticalPanel();
        VP1 = new VerticalPanel();
        VP1.setHeight("100%");
        VP1.setWidth("100%");
            
        FT0 = new FlexTable();
        FT0.addStyleName("cw-FlexTable");
        FT0.setWidth("24em");
        FT0.setCellSpacing(5);
        FT0.setCellPadding(3);
        
        FTC = new FlexTable();
        FTCFormat = FTC.getFlexCellFormatter();

        FTC.addStyleName("cw-FlexTable");
        FTC.setWidth("15em");
        FTC.setCellPadding(0);
        FTC.setCellSpacing(0);

        FT1 = new FlexTable(); 
        FT1Format = FT1.getFlexCellFormatter();
        VP0.add(FT1);
        VP0.setWidth("100%");
        
        DecoratorPanel P4 = new DecoratorPanel();     
        P4.setWidget(FTC);
        SP1 = new ScrollPanel();   
        SP1.add(FTC);
        SP1.setHeight("100%");
        SP1.setWidth("100%");
 

        dock.addWest(SP1, 20);    
        DecoratorPanel P2 = new DecoratorPanel();
        P2.setWidget(FTG);
        dock.addWest(P2, 20);
        DecoratorPanel P3 = new DecoratorPanel();
        P3.setWidget(VP0);
        dock.addEast(P3, 60);

        RootPanel.get().add(dock);
    }
    
    public void gui_eventEditButtonClicked()
    {
    	status.setText("Clicked: row=" + this.row);
    	if (editButton.getText() == "Edit")
    	{
 
    		editButton.setText("Save");
    		cancelButton.setVisible(true);
    		for (int row = 0; row < FTC.getRowCount(); row++)
    		{
    			CheckBox cb = (CheckBox) FTC.getWidget(row, 0);
    		    if (cb.getValue())
    		    {
    		    	this.row = row;
    		    	status.setText("Edit (" + row + ") " + contacts.get(row).getName());
    		    	displayContact(row, true);
    		    	return;
    		    } 
    		 }
    		this.row = -1;
    		displayContact(-1, true);    		
    	}
    	else
    	{
    		ContactDetails c = null;
    		if (this.row < 0)
    		{
    			c = new ContactDetails();
    		}
    		else
    		{
    			c = currentContact;
    		}
    		ArrayList<String> l1 = new ArrayList<String>();
    		ArrayList<String> l2 = new ArrayList<String>();
    		
    		for (int i=0; i < adresField.size(); i++)
    		{
    		     TextArea a = adresField.get(i);
    		     if (a.getText().length() == 0)
    		    	 continue;
    		     
    		    l1.add(a.getText());  
    		    ListBox b = adresType.get(i);
    		    l2.add(b.getItemText(b.getSelectedIndex())); 		    
    		}
    		
    		if (l1.size() > 0)
          		c.setAdress(l1, l2);
    		
    		l1.clear();
    		l2.clear();
   
    		for (int i=0; i < emailField.size(); i++)
    		{
    			 TextBox a = emailField.get(i);
    			 if (a.getText().length() ==0)
    				 continue;
    			 
    			l1.add(a.getText());
                ListBox b = emailType.get(i);               
    		    l2.add(b.getItemText(b.getSelectedIndex()));   	
    		}
    		
    		c.setEmail(l1, l2);
    		c.setName(nameField.getText());
    		c.setTitel(titleField.getText());
    		c.setCompany(companyField.getText());
    		
    		l1.clear();
    		l2.clear();
    		for (int i=0; i < phoneField.size(); i++)
    		{
    		    TextBox a = phoneField.get(i);
    			if (a.getText().length() ==0)
    				continue;
    		    l1.add(a.getText());
    			ListBox b = phoneType.get(i);
    		    l2.add(b.getItemText(b.getSelectedIndex()));   	
    		}
    		c.setPhone(l1, l2);
    		
    		c.setNote(noteField.getText());
    		
    		editButton.setText("Edit");
    		if (this.row < 0)
    		{
    			this.contactService.addContact(c);
    		}
    		else
    		{
    			this.contactService.updateContact(c);
    		}
    	}
    }

    public void gui_eventAddButtonClicked() 
    {
    	editButton.setText("Save");
    	addButton.setVisible(false);
    	cancelButton.setVisible(true);
        displayContact(-2, true);
    }

    public void gui_eventUpdateButtonClicked() 
    {
        this.contactService.updateContact(currentContact);
    }
    
    public void gui_eventRefreshButtonClicked()
    {
    	this.FTC.clear();
        int index = listDom.getSelectedIndex();
        Domain = listDom.getItemText(index);       
        status.setText("Retrieving contacts for: " + Domain);
        this.contactService.listContacts(Domain);
    }

    public void gui_syncButtonClicked()
    {
    	String from = this.fromDomainList.getItemText(this.fromDomainList.getSelectedIndex());
    	String to   = this.toDomainList.getItemText(this.toDomainList.getSelectedIndex());
        this.contactService.syncContacts(from, to, 1);
    }
    
    public void gui_syncContactButtonClicked()
    {
    	String from = this.listDom.getItemText(this.listDom.getSelectedIndex());
       	String to   = this.toDomainList.getItemText(this.toDomainList.getSelectedIndex());
    	ArrayList<String> ids = new ArrayList<String>();
    	
		for (int row = 0; row < FTC.getRowCount(); row++)
		{
			CheckBox cb = (CheckBox) FTC.getWidget(row, 0);
		    if (cb.getValue())
		    {
		        Contact c = this.contacts.get(row);
		        ids.add(c.getId());		        
		    } 
		} 
		this.contactService.syncContacts(from, ids, to );
    }

    public void service_eventListDomainsRetrievedFromService(ArrayList<String> result)
    {
    	status.setText("Domains Retrieved");
    	int s = result.size();
    	for (int i=0; i < s; i++)
    	{
    		String res = result.get(i);
    		this.listDom.addItem(res);
    		this.fromDomainList.addItem(res);
    		this.toDomainList.addItem(res);
    	}
    }
    
    public void service_eventListRetrievedFromService(ArrayList<Contact> result) 
    {
    	this.contacts = null;
    	this.contacts = result;
    	status.setText("Retrieved contact list");
        int row = 0;
   
        oracle.clear();
        this.FTC.clear();
        int s = this.contacts.size();
        
        for (int i = 0; i < s; i++)
        {
            Contact contact = this.contacts.get(i);
        	CheckBox cb;
        	Label    lb;
        	lb = new Label();
        	if (contact.getName() != null)
        	   oracle.add(contact.getName());
        	lb.setText(contact.getName());
        	lb.addClickHandler(this);
        	cb = new CheckBox();
        	cb.setVisible(true);
        	cb.addClickHandler(this); 
            FTCFormat.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
            FTCFormat.setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
            FTC.setWidget(row, 0, cb);
            FTC.setWidget(row, 1, lb);
        	row++;
        }    
        status.setText("All contacts (" + row + ") retrieved.");
    }
    
	public void onClick(ClickEvent event) 
	{
		if (cancelButton.isVisible())
		{
			return;
		}
		Object sender = (Object) event.getSource();
		if (sender == nameField)
		{
			nameField.setReadOnly(false);
			return;
		}
		
		for (int row = 0; row < FTC.getRowCount(); row++)
		{
			CheckBox cb = (CheckBox) FTC.getWidget(row, 0);
			Label    lb = (Label)    FTC.getWidget(row, 1);
		    if (sender  == cb)
		    {
		    	status.setText("Found (" + row + ") " + contacts.get(row).getName());
		    	this.row = row;
		        this.contactService.retrieveContact(row);
		    	// displayContact(row, false);
		    	return;
		    } 
		    else if (sender == lb)
		    {
		    	status.setText("Found (" + row + ") " + contacts.get(row).getName());
		        this.contactService.retrieveContact(row);
		    	//displayContact(row, false);
		        this.row = row;
		    	return;
		    }
		} 
		status.setText("Not found .....");
		return;
	}
	
	public void onSearchClicked(String search)
	{
		int tot = FTC.getRowCount();
		search = search.toLowerCase();
		
	    for (int row = tot-1; row >= 0; row--)
	    {
	        CheckBox cb = (CheckBox) FTC.getWidget(row, 0);
		    Label    lb = (Label)    FTC.getWidget(row, 1);
		    lb.getText().toLowerCase().indexOf(search);
		    
		    if (lb.getText().toLowerCase().indexOf(search) < 0)
		    {
		    	tot--;
		    	cb.setVisible(false);
		    	lb.setVisible(false);
		    }	
	    } 
	    
	    if (tot > 0)
	    {
	    	status.setText("Found " + tot + " contacts.");
	    }
	    else
	    {
	    	
	    }
	    SP1.onResize();
	    return;
	}
	
	public void onClearClicked()
	{
		int row = 0 ;
        this.FTC.clear();
        for (Contact contact : this.contacts) 
        {
        	CheckBox cb;
        	Label    lb;
        	lb = new Label();
        	lb.setText(contact.getName());
        	lb.addClickHandler(this);
        	cb = new CheckBox();
        	cb.setVisible(true);
        	cb.addClickHandler(this);  
            FTCFormat.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
            FTCFormat.setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
            FTC.setWidget(row, 0, cb);
            FTC.setWidget(row, 1, lb);
        	row++;
        }    
        status.setText("All contacts (" + row + ") retrieved.");
        SP1.onResize();
	}
	
	public void displayContact(int row, boolean edit)
	{
		//this.row = row;
		if (row == -2)
		{
			ContactDetails c = new ContactDetails();
			status.setText("Adding new Contact.");
			displayContact(c);
			return;
		}
		if (row == -1)
		{
			setEdit(edit);
			return;
		}
		contactService.retrieveContact(row);
	}
	
	public void displayContact(ContactDetails c)
	{
		int start = 2;
        currentContact = c;
        
		status.setText("Displaying ....");
		FT1.removeAllRows();
		
		nameField.setValue(null);
		titleField.setValue(null);
		companyField.setValue(null);
        FT1.clear(); 
   	    emailField.clear();
	    emailType.clear();
	    phoneField.clear();
	    phoneType.clear();
 
        FT1.addStyleName("cw-FlexTable");
        FT1.setWidth("32em");
        FT1.setCellSpacing(5);
        FT1.setCellPadding(3);
        FT1Format.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
        FT1Format.setColSpan(0, 0, 2);
        FT1.setWidget(0, 0, nameField);

        nameField.setWidth("100%");
        nameField.addClickHandler(this);
        FT1Format.setColSpan(0, 0, 2);

        FT1.setWidget(1, 0, titleField);

        FT1.setWidget(1, 1, companyField);
        if (c != null)
	       nameField.setText(c.getName());
	    if (c!= null)
        for (String tit : c.getTitelEntries())
	    {	    	
	       titleField.setText(tit);
	    }
	    if (c != null)
	    for (String com : c.getOrganizationEntries())
	    {
	       companyField.setText(com);
	    }
    
	    int i=0;
	    if (c.hasEmail())
	    {
           for (String em : c.getEmailEntries())
           {
        	   TextBox tb = new TextBox();
        	   ListBox lb = new ListBox();
        	   tb.setText(em);
        	   emailField.add(tb);
        	   emailType.add(lb);
        	   FT1.setWidget(start, 0, tb);
        	   lb.setVisibleItemCount(1);
        	   lb.insertItem("work", 0);
        	   lb.insertItem("home", 1);
        	   lb.insertItem("other", 2);
        	   String label = c.getRel(i);
        	
        	   if (label == "work")
        	   {
                lb.setSelectedIndex(0);
        	   }
        	   else if (label == "home")
        	   {
                lb.setSelectedIndex(1);
        	   }
        	   else if (label == "other")
        	   {
                lb.setSelectedIndex(2);
        	   }
        	Button remove = new Button();
        	remove.addClickHandler(this);
        	HorizontalPanel P = new HorizontalPanel();
        	P.add(lb);
        	P.add(remove);
        	FT1.setWidget(start, 1, P);
        	// status.setText(em);
        	start++;
        }
	    }
	    else
	    {
        	TextBox tb = new TextBox();
        	ListBox lb = new ListBox();

        	emailField.add(tb);
        	emailType.add(lb);
        	FT1.setWidget(start, 0, tb);
        	lb.setVisibleItemCount(1);
        	lb.insertItem("work", 0);
        	lb.insertItem("home", 1);
        	lb.insertItem("other", 2);
        	String label = c.getRel(i);
        	
        	if (label == "work")
        	{
                lb.setSelectedIndex(0);
        	}
        	else if (label == "home")
        	{
                lb.setSelectedIndex(1);
        	}
        	else if (label == "other")
        	{
                lb.setSelectedIndex(2);
        	}
        	Button remove = new Button();
        	remove.addClickHandler(this);
        	HorizontalPanel P = new HorizontalPanel();
        	P.add(lb);
        	P.add(remove);
        	FT1.setWidget(start, 1, P);
        	start++;
	    }
  
        adresField.clear();
        adresType.clear();
        int a=0;
        if (c.hasAdress())
        {
           for (String em : c.getAdressEntries())
           {
        	   TextArea tb = new TextArea();
        	   ListBox  lb = new ListBox();
        	   
        	   tb.setText(em);
        	   tb.setSize("100%", "5em");
        	   adresField.add(tb);
        	   adresType.add(lb);
        	   FT1.setWidget(start, 0, tb);
               lb.setVisibleItemCount(1);
               lb.insertItem("work", 0);
               lb.insertItem("home", 1);
               lb.insertItem("other", 2);
               String label = c.getAdressType(a);
     
               if (label == "work")
               {
                  lb.setSelectedIndex(0);
               }
               else if (label == "home")
               {
                  lb.setSelectedIndex(1);
               }
               else if (label == "other")
               {
                  lb.setSelectedIndex(2);
               }
               FT1.setWidget(start, 1, lb);
               i++;
        	   start++;
        	   a++;
           }
        }
        else
        {
            adresField.clear();
            adresType.clear();
            
     	    TextArea tb = new TextArea();
    	    ListBox  lb = new ListBox();
    	   
    	    tb.setSize("100%", "5em");
    	    adresField.add(tb);
    	    adresType.add(lb);
    	    
        	FT1.setWidget(start, 0, tb);
            lb.setVisibleItemCount(1);
            lb.insertItem("work", 0);
            lb.insertItem("home", 1);
            lb.insertItem("other", 2);
            String label = c.getAdressType(a);
     
            if (label == "work")
            {
                lb.setSelectedIndex(0);
            }
            else if (label == "home")
            {
                lb.setSelectedIndex(1);
            }
            else if (label == "other")
            {
                lb.setSelectedIndex(2);
            }
            FT1.setWidget(start, 1, lb);
        	start++;
        }
    
        int p=0;
        if (c.hasPhone())
        {
           for (String ph : c.getPhoneEntries())
           {
        	   TextBox tb =new TextBox();
        	   ListBox lb = new ListBox();
        	   tb.setText(ph);

        	   phoneField.add(tb);
        	   phoneType.add(lb);
        	   FT1.setWidget(start, 0, tb);
               lb.setVisibleItemCount(1);
               lb.insertItem("work", 0);
               lb.insertItem("home", 1);
               lb.insertItem("mobile", 2);
               lb.insertItem("home_fax", 3);
               lb.insertItem("work_fax", 4);
               lb.insertItem("pager", 5);
               lb.insertItem("other", 6);
            	
               String label = c.getPhoneType(p);
            	
               if (label == "work")
               {
                  lb.setSelectedIndex(0);
               }
               else if (label == "home")
               {
                  lb.setSelectedIndex(1);
               }
               else if (label == "mobile")
               {
                  lb.setSelectedIndex(2);
               }
            	else if (label == "home_fax")
            	{
                    lb.setSelectedIndex(3);
            	}
            	else if (label == "work_fax")
            	{
                    lb.setSelectedIndex(4);
            	}
            	else if (label == "pager")
            	{
                    lb.setSelectedIndex(5);
            	}
            	else if (label == "other")
            	{
                    lb.setSelectedIndex(6);
            	}
            	FT1.setWidget(start, 1, lb);
            	p++;
        	   start++;
           }
        }
        else
        {
        	   TextBox tb =new TextBox();
        	   ListBox lb = new ListBox();

        	   phoneField.add(tb);
        	   phoneType.add(lb);
        	   FT1.setWidget(start, 0, tb);
             	lb.setVisibleItemCount(1);
            	lb.insertItem("work", 0);
            	lb.insertItem("home", 1);
            	lb.insertItem("mobile", 2);
            	lb.insertItem("home fax", 3);
            	lb.insertItem("work fax", 4);
            	lb.insertItem("pager", 5);
            	lb.insertItem("other", 6);
            	String label = c.getPhoneType(p);
            	
            	if (label == "work")
            	{
                    lb.setSelectedIndex(0);
            	}
            	else if (label == "home")
            	{
                    lb.setSelectedIndex(1);
            	}
            	else if (label == "mobile")
            	{
                    lb.setSelectedIndex(2);
            	}
            	else if (label == "home_fax")
            	{
                    lb.setSelectedIndex(3);
            	}
            	else if (label == "work_fax")
            	{
                    lb.setSelectedIndex(4);
            	}
            	else if (label == "pager")
            	{
                    lb.setSelectedIndex(5);
            	}
            	else if (label == "other")
            	{
                    lb.setSelectedIndex(6);
            	}       	
 
            	FT1.setWidget(start, 1, lb);
        	   start++;
        }
     
        if (c.hasGroups())
        {
           for (String grp : c.getGroups())
           {
        	   TextBox tb = new TextBox();
        	   tb.setText(grp);
        	   groupField.add(tb);
        	   FT1.setWidget(start, 0, tb);
        	   start++;
           }
        }
        

 	       FT1.setWidget(start, 0, noteField);
           FT1Format.setColSpan(start, 0, 2);
           FT1Format.setRowSpan(start, 0, 10);
 	       noteField.setValue(c.getNote());
 	       noteField.setWidth("100%");

        //setEdit(edit);
        return;
	} 
	
	public void setEdit(boolean edit)
	{
        nameField.setReadOnly(!edit);
        companyField.setReadOnly(!edit);
		titleField.setReadOnly(!edit);
	    for (TextArea tb : adresField)
	    {
	    	tb.setReadOnly(!edit);
	    }
	    
	    for (TextBox tb : emailField)
	    {
	    	tb.setReadOnly(!edit);
	    }
        for (TextBox tb : phoneField)
        {
			tb.setReadOnly(!edit);
        }
	   
		
		for (TextBox tb : groupField)
		{
			tb.setReadOnly(!edit);
		}
	
		noteField.setReadOnly(!edit);
	}
	
    public void service_eventAddContactSuccessful(String result) 
    {
        status.setText("Contact Added: " + result);
        this.contactService.listContacts(this.Domain);
    }

    public void service_eventUpdateSuccessful(String result) 
    {
        status.setText("Contact " + result);
        this.contactService.listContacts(this.Domain);
    }
    
    public void service_eventRemoveContactSuccessful(String result) 
    {
        status.setText("Contact was removed: " + result);
        this.contactService.listContacts(this.Domain);     
    }
    
    public void servicesyncContactsSuccess(String res)
    {
    	status.setText("Sync complete! ("+res+")");
    }

    public void service_eventUpdateContactFailed(Throwable caught) 
    {
        status.setText("Update contact failed: " + caught.getMessage());
    }

    public void service_eventAddContactFailed(Throwable caught) 
    {
        status.setText("Unable to insert contact: " + caught.getMessage());
    }

    public void service_eventRemoveContactFailed(Throwable caught) 
    {
       status.setText("Remove contact failed: " + caught.getMessage() + " : " + caught.toString());
    }

    public void service_eventListContactsFailed(Throwable caught) 
    {
        status.setText("Unable to get contact list: " + caught.getMessage());      
    }

    public void serviceListDomainFailed(Throwable caught) 
    {
    	status.setText("Unable to get Domain list: " + caught.getMessage());
    }
    
    public void servicesyncContactsFailure(Throwable caught)
    {
    	status.setText("Unable to Sync: "+ caught.getMessage());
    }

	public void gui_deleteButtonClicked() 
	{
		ArrayList<ContactOperation>  ids = new ArrayList<ContactOperation>();
		List<Integer>           rem = new ArrayList<Integer>();
		status.setText("Starting to delete ....");
		FT1.clear();
		this.row = -2;
		for (int row = 0; row < FTC.getRowCount(); row++)
		{
			CheckBox cb = (CheckBox) FTC.getWidget(row, 0);
		    if (cb.getValue())
		    {
		    	//status.setText("Starting to delete on " + row);
		        rem.add(row);
		        Contact c = this.contacts.get(row);
		        ContactOperation co = new ContactOperation();
		        co.setEditLink(c.getEditlink());
		        co.setEtag(c.getEtag());	        
		        ids.add(co);	        
		    } 
		} 
		
		if (ids.size() > 0)
		{
			//status.setText("Starting to delete 2....");
			this.contactService.removeContact(ids);
			for (Integer r: rem)
			{
				FTC.removeRow(r);
			}
		}
		return;
	}

	public void service_eventListGroupsFailure() 
	{
		// TODO Auto-generated method stub
		FTG.clear();
		status.setText("Error Retrieving Groups.");
	}

	public void service_eventListGroupsSuccess(ArrayList<Group> result) 
	{
		// TODO Auto-generated method stub
		int row = 0;
		FTG.clear();
		grpcb.clear();
		
		int s = result.size();
		for (int i = 0; i < s; i++)
		{
			Group grp = result.get(i);
			CheckBox cb = new CheckBox();
			grpcb.add(cb);
        	cb.setVisible(true);
        	cb.addClickHandler(this); 
			FTG.setWidget(row, 0, cb);
			FTG.setWidget(row, 1, new Label(grp.getName()));
			row++;
		}
	}

	public void gui_eventCancelButtonClicked() 
	{
		// TODO Auto-generated method stub
		cancelButton.setVisible(false);
		addButton.setVisible(true);
		if (editButton.getText().equals("Edit"))
		{
			
		}
		else
		{
		   editButton.setText("Edit");
		   FT1.removeAllRows();
		   //displayContact(this.row, false);
		   //setEdit(false);
		}
	}

	public void serviceretrieveContactFailure(Throwable caught) {
		// TODO Auto-generated method stub
		status.setText("Couldn't retrieve Contact: " + caught.getMessage());
	}

	public void serviceretrieveContactSuccess(ContactDetails result) {
		// TODO Auto-generated method stub
		displayContact(result);
	}

}