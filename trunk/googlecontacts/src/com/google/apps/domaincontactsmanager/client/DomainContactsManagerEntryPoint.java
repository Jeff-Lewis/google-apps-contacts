package com.google.apps.domaincontactsmanager.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.apps.domaincontactsmanager.shared.Contact;
import com.google.apps.domaincontactsmanager.shared.ContactList;
import com.google.apps.domaincontactsmanager.shared.Email;
import com.google.apps.domaincontactsmanager.shared.OAuthCredential;
import com.google.apps.domaincontactsmanager.shared.Organization;
import com.google.apps.domaincontactsmanager.shared.PhoneNumber;
import com.google.apps.domaincontactsmanager.shared.StructuredPostalAddress;
import com.google.apps.domaincontactsmanager.shared.User;
import com.google.apps.domaincontactsmanager.shared.Website;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DomainContactsManagerEntryPoint implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */

  private List<ContactList> contactLists = new ArrayList<ContactList>();

  private final BackendServiceAsync backendService = GWT.create(BackendService.class);
  private final VerticalPanel container = new VerticalPanel();
  private final HorizontalPanel panelLogin = new HorizontalPanel();
  private final HorizontalPanel panelTop = new HorizontalPanel();
  private final HorizontalPanel panelTopImages = new HorizontalPanel();
  
  
  private final ScrollPanel contactsScrollPanel = new ScrollPanel();
  
  final HTML linkLogin = new HTML();

  private final DecoratedTabPanel tabPanel = new DecoratedTabPanel();
  
  final HorizontalPanel panelMain1 = new HorizontalPanel();
  final VerticalPanel panelMain2 = new VerticalPanel();
  final VerticalPanel panelMain3 = new VerticalPanel();

  final Image image1 = new Image("contacts.png");
  final Image image2 = new Image("appengine.png");
  final Image image3 = new Image("gwt.png");

  final ListBox listBox1 = new ListBox();
  final ListBox listBox2 = new ListBox();
  final FlexTable contactsGrid = new FlexTable();

  FlexTable credentialsFlexTable = new FlexTable();

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
	mainPanel();
	isUserLoggedIn();
    addListBoxEvents();
  }

  private void mainPanel() {
    container.setWidth("100%");
    container.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
    RootPanel.get().addStyleName("root");
    RootPanel.get().add(container);

    panelLogin.setStyleName("panelLogin");
    container.add(panelLogin);
    panelLogin.add(linkLogin);
    linkLogin.setStyleName("linkLogin");

    panelTop.setStyleName("panelTop");

    panelTop.setWidth("100%");
    panelTop.setHeight("35px");
    panelTop.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
    container.add(panelTop);
    panelTopImages.add(image1);
    panelTopImages.add(image2);
    panelTopImages.add(image3);
    panelTop.add(panelTopImages);

    panelMain1.addStyleName("panelMain");
    tabPanel.addStyleName("tabPanel");
    container.add(tabPanel);
    tabPanel.add(panelMain1, "Main");
    tabPanel.selectTab(0);

    tabPanel.setWidth("60%");
    panelMain1.setWidth("920px");
    panelMain1.setHeight("500px");

    listBox1.setVisibleItemCount(30);
    listBox1.setWidth("250px");
    listBox1.setHeight("500px");
    panelMain1.add(listBox1);

    listBox2.setVisibleItemCount(30);
    listBox2.setWidth("250px");
    listBox2.setHeight("500px");
    panelMain1.add(listBox2);

    contactsScrollPanel.add(contactsGrid);
    contactsScrollPanel.setHeight("500px");
    
    panelMain1.add(contactsScrollPanel);
    contactsGrid.setWidth("400px");
    contactsGrid.setStyleName("contactsGrid");
  }

  private void drawAdmin() {
    panelMain2.clear();
    credentialsFlexTable = new FlexTable();

    panelMain2.setWidth("920px");
    panelMain2.setHeight("500px");
    tabPanel.add(panelMain2, "SuperAdmin");
    panelMain2.setStyleName("panelMain2");

    credentialsFlexTable.setText(0, 0, "OAuth Key (domain)");
    credentialsFlexTable.setText(0, 1, "OAuth Secret");
    credentialsFlexTable.setText(0, 2, "Admin email");
    panelMain2.add(credentialsFlexTable);

    final TextBox textBoxNewOAuthKey = new TextBox();
    final TextBox textBoxNewOAuthSecret = new TextBox();
    final TextBox textBoxNewAdminEmail = new TextBox();

    credentialsFlexTable.setWidget(1, 0, textBoxNewOAuthKey);
    credentialsFlexTable.setWidget(1, 1, textBoxNewOAuthSecret);
    credentialsFlexTable.setWidget(1, 2, textBoxNewAdminEmail);

    HorizontalPanel panelButtons = new HorizontalPanel();
    panelButtons.setStyleName("panelButtons");

    Button adminSubmitButton = new Button("Save changes", new ClickHandler() {
      public void onClick(ClickEvent event) {
        String domain = textBoxNewOAuthKey.getText();
        String key = textBoxNewOAuthSecret.getText();
        String email = textBoxNewAdminEmail.getText();
        if (domain.length() > 0 && key.length() > 0 && email.length() > 0)
          saveOAuthCredentials(domain, key, email);
      }
    });
    panelButtons.add(adminSubmitButton);
    Button adminCancelButton = new Button("Cancel", new ClickHandler() {
      public void onClick(ClickEvent event) {
        tabPanel.selectTab(0);
      }
    });
    panelButtons.add(adminCancelButton);
    panelMain2.add(panelButtons);

    getOAuthCredentials();
  }

  private void drawDomainAdmin(User user) {
    panelMain3.clear();
    FlexTable adminFlexTable = new FlexTable();

    panelMain3.setWidth("920px");
    panelMain3.setHeight("500px");
    tabPanel.add(panelMain3, "Admin");
    panelMain3.setStyleName("panelMain2");

    adminFlexTable.setText(0, 0, "OAuth Key (domain)");
    adminFlexTable.setText(0, 1, "OAuth Secret");
    adminFlexTable.setText(0, 2, "Admin email");
    panelMain3.add(adminFlexTable);

    final TextBox textBoxNewOAuthKey = new TextBox();
    final TextBox textBoxNewOAuthSecret = new TextBox();
    final TextBox textBoxNewAdminEmail = new TextBox();
        
    textBoxNewOAuthKey.setReadOnly(true);
    textBoxNewOAuthKey.setText(user.getDomain());    
    textBoxNewOAuthSecret.setText(user.getOauthKey());
    textBoxNewAdminEmail.setReadOnly(true);
    textBoxNewAdminEmail.setText(user.getAdminEmail());

    adminFlexTable.setWidget(1, 0, textBoxNewOAuthKey);
    adminFlexTable.setWidget(1, 1, textBoxNewOAuthSecret);
    adminFlexTable.setWidget(1, 2, textBoxNewAdminEmail);

    HorizontalPanel panelButtons = new HorizontalPanel();
    panelButtons.setStyleName("panelButtons");

    Button adminSubmitButton = new Button("Save changes", new ClickHandler() {
      public void onClick(ClickEvent event) {
        String domain = textBoxNewOAuthKey.getText();
        String key = textBoxNewOAuthSecret.getText();
        String email = textBoxNewAdminEmail.getText();
        if (domain.length() > 0 && key.length() > 0 && email.length() > 0)
          saveOAuthCredentials(domain, key, email);
      }
    });
    panelButtons.add(adminSubmitButton);
    Button adminCancelButton = new Button("Cancel", new ClickHandler() {
      public void onClick(ClickEvent event) {
        tabPanel.selectTab(0);
      }
    });
    panelButtons.add(adminCancelButton);
    panelMain3.add(panelButtons);    
  }
  
  private void addListBoxEvents() {
	  
  listBox1.addChangeHandler(new ChangeHandler() {
   public void onChange(ChangeEvent event) {
    listBox2.clear();
    contactsGrid.clear();
    for (Contact contact : contactLists.get(listBox1.getSelectedIndex()).contacts) {
      if (contact.getTitle().length() > 0)
        listBox2.addItem(contact.getTitle(), contact.getTitle());
      else {
        if (contact.emails.size() > 0)
          listBox2.addItem(contact.emails.get(0).getAddress(), contact.getTitle());
        else if (contact.phoneNumbers.size() > 0)
          listBox2.addItem(contact.phoneNumbers.get(0).getPhoneNumber(), contact.getTitle());
        else
          listBox2.addItem("(Unnamed Contact)", contact.getTitle());
      }
    }
   }
  });

  listBox2.addChangeHandler(new ChangeHandler() {
    public void onChange(ChangeEvent event) {
      int index1 = listBox1.getSelectedIndex();
      int index2 = listBox2.getSelectedIndex();
      if (index1 >= 0 && index2 >= 0) {
        Contact contact = contactLists.get(index1).contacts.get(index2);
        contactsGrid.clear();
        int verticalPosition = 0;
        Label labelTitle = new Label(contact.getTitle());
        labelTitle.setStyleName("labelTitle");
        contactsGrid.setWidget(verticalPosition, 0, labelTitle);

        // Display contact's Organizations
        if (contact.organizations.size() > 0) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelOrganization = new Label("Organizartions");
          labelOrganization.addStyleName("labelBold");
          row.add(labelOrganization);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }
        for (Organization organization : contact.organizations) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelOrganization = new Label(
        		  organization.getOrgName() + " " +
        		  organization.getOrgDepartment() + " " +
        		  organization.getOrgJobDescription() + " " +
        		  organization.getOrgTitle());
          labelOrganization.addStyleName("labelMargin");
          row.add(labelOrganization);
          Label labelRel = new Label(organization.getRel());
          labelRel.setStyleName("labelLight");
          row.add(labelRel);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }
        
        // Display contact's emails
        if (contact.emails.size() > 0) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelEmail = new Label("Emails");
          labelEmail.addStyleName("labelBold");
          row.add(labelEmail);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }
        for (Email email : contact.emails) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelEmail = new Label(email.getAddress());
          labelEmail.addStyleName("labelEmail");
          row.add(labelEmail);
          Label labelRel = new Label(email.getRel());
          labelRel.setStyleName("labelLight");
          row.add(labelRel);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }

        // Display contact's phone numbers
        if (contact.phoneNumbers.size() > 0) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelEmail = new Label("Phone Numbers");
          labelEmail.addStyleName("labelBold");
          row.add(labelEmail);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }
        for (PhoneNumber phoneNumber : contact.phoneNumbers) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelEmail = new Label(phoneNumber.getPhoneNumber());
          labelEmail.addStyleName("labelMargin");
          row.add(labelEmail);
          Label labelRel = new Label(phoneNumber.getRel());
          labelRel.setStyleName("labelLight");
          row.add(labelRel);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }
        
        // Display contact's postal addresses
        if (contact.structuredPostalAddresses.size() > 0) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelPostal = new Label("Postal Address");
          labelPostal.addStyleName("labelBold");
          row.add(labelPostal);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }
        for (StructuredPostalAddress structuredPostalAddress : contact.structuredPostalAddresses) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelPostal = new Label(structuredPostalAddress.getFormattedAddress());
          labelPostal.addStyleName("labelMargin");
          row.add(labelPostal);
          Label labelRel = new Label(structuredPostalAddress.getRel());
          labelRel.setStyleName("labelLight");
          row.add(labelRel);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }

        // Display contact's websites
        if (contact.websites.size() > 0) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelWebsite = new Label("Websites:");
          labelWebsite.addStyleName("labelBold");
          row.add(labelWebsite);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }
        for (Website website : contact.websites) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelWebsite = new Label(website.getUrl());
          labelWebsite.addStyleName("labelMargin");
          row.add(labelWebsite);
          Label labelRel = new Label(website.getRel());
          labelRel.setStyleName("labelLight");
          row.add(labelRel);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }        
        
        // Display contact's groups
        if (contact.groups.size() > 0) {
          verticalPosition++;
          HorizontalPanel row = new HorizontalPanel();
          Label labelGroup = new Label("Groups:");
          labelGroup.addStyleName("labelBold");
          row.add(labelGroup);

          String groupsText = "";
          Iterator<String> iterator = contact.groups.values().iterator();
          while(iterator.hasNext()){
            groupsText = groupsText + iterator.next() + ", ";                        
          }
            
          Label labelGroup2 = new Label(groupsText.substring(0, groupsText.length() - 2));
          row.add(labelGroup2);
          contactsGrid.setWidget(verticalPosition, 0, row);
        }
        
        String notes = contact.getNotes();
        if (notes != null && notes.length() > 0 ) {
        	verticalPosition++;
            HorizontalPanel row = new HorizontalPanel();
            Label labelNotes = new Label("Notes:");
            labelNotes.addStyleName("labelBold");
            row.add(labelNotes);
            contactsGrid.setWidget(verticalPosition, 0, row);
            
            verticalPosition++;
            HorizontalPanel row2 = new HorizontalPanel();
            TextArea textAreaNotes = new TextArea();
            textAreaNotes.setText(notes);
            row2.add(textAreaNotes);
            contactsGrid.setWidget(verticalPosition, 0, row2);
        }
      }
    }
  });
}
  
  private void getOAuthCredentials() {
	  backendService.getOAuthCredentials(new AsyncCallback<List<OAuthCredential>>() {
      public void onFailure(Throwable caught) {
        GWT.log(caught.getMessage(), caught);
      }

      public void onSuccess(List<OAuthCredential> credentialsList) {
        int rows;
        for (OAuthCredential credential : credentialsList) {
          rows = credentialsFlexTable.getRowCount();
          credentialsFlexTable.setText(rows, 0, credential.getDomain());
          credentialsFlexTable.setText(rows, 1, credential.getOAuthKey());
          credentialsFlexTable.setText(rows, 2, credential.getEmailAdmin());
        }
      }
    });
  }

  private void saveOAuthCredentials(String domain, String key, String adminEmail) {
	  backendService.saveOAuthCredentials(domain, key, adminEmail, new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        GWT.log(caught.getMessage(), caught);
      }

      public void onSuccess(String result) {
        drawAdmin();
        tabPanel.selectTab(0);
      }
    });
  }

  private void isUserLoggedIn() {
	  backendService.isUserLoggedIn(new AsyncCallback<User>() {
      public void onFailure(Throwable caught) {
        GWT.log(caught.getMessage(), caught);
      }

      public void onSuccess(User user) { 
    	if (!user.isLoggedIn()) {
          Window.Location.assign(user.getLoginUrl());
        } else {
          linkLogin.setHTML("<b>" + user.getEmail() 
        		  + "</b>\n|\n<a href=\"" 
        		  + user.getLogoutUrl() + "\">Sign out</a>");
          // If the user is an App admin, draws the admin panel
          if (user.isAdmin()) {
            drawAdmin();
          }
          if (user.isResitered()) {
            if (user.getAdminEmail().equals(user.getEmail())) {
              drawDomainAdmin(user);
            }

            getProfiles();
            
            // Wait for OAuth to finish getting the AuthToken.
            Timer timer = new Timer() {
              public void run() {
                getSharedContacts();
                getRegularContacts();
              }
            };
            timer.schedule(1000);
          }
        }
      }
    });
  }

  private void getRegularContacts() {
	  backendService.getRegularContacts(new AsyncCallback<ContactList>() {
      public void onFailure(Throwable caught) {
        GWT.log(caught.getMessage(), caught);
      }

      public void onSuccess(ContactList contactList) {
        contactLists.add( contactList );
        String numberOfContacts = " (" + contactList.contacts.size() + ")";
        listBox1.addItem(contactList.getTitle() + numberOfContacts, contactList.getId());
      }
    });
  }
  
  private void getSharedContacts() {
	  backendService.getSharedContacts(new AsyncCallback<ContactList>() {
      public void onFailure(Throwable caught) {
        GWT.log(caught.getMessage(), caught);
      }

      public void onSuccess(ContactList contactList) {
        contactLists.add( contactList );
        String numberOfContacts = " (" + contactList.contacts.size() + ")";
        listBox1.addItem(contactList.getTitle() + numberOfContacts, contactList.getId());
      }
    });
  }
  
  private void getProfiles() {
	  backendService.getProfiles(new AsyncCallback<ContactList>() {
      public void onFailure(Throwable caught) {
        GWT.log(caught.getMessage(), caught);
      }

      public void onSuccess(ContactList contactList) {
        contactLists.add( contactList );
        String numberOfContacts = " (" + contactList.contacts.size() + ")";
        listBox1.addItem(contactList.getTitle() + numberOfContacts, contactList.getId());
      }
    });
  }  
}
