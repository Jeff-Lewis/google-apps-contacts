package nl.miraclebenelux.domaincontacts.server;

import java.io.IOException;
import java.net.URL;


import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import com.google.gdata.client.appsforyourdomain.UserService;
import com.google.gdata.data.Link;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.provisioning.UserFeed;
import com.google.gdata.util.AuthenticationException;

public class DomainUsers 
{
   private   static final String APPS_FEEDS_URL_BASE = "https://apps-apis.google.com/a/feeds/";
   protected static final String SERVICE_VERSION = "2.0";
   protected String domainUrlBase;
   protected UserFeed allUsers;
   protected UserService userService;
   protected FindDomain D = null;
   
   
	public DomainUsers(String Domain)
	{
		domainUrlBase =  APPS_FEEDS_URL_BASE + Domain + "/";
		allUsers = new UserFeed();
		userService = new UserService("MiracleUserService");


	}
	
	public UserFeed retrieveAllUsers(String Domain)
      throws ServiceException, IOException, com.google.gdata.util.ServiceException {

	if (D == null)
	{
		D = new FindDomain(Domain);
	    userService.setUserCredentials(D.getUser(), D.getPassword());
	}

    URL retrieveUrl = new URL(domainUrlBase + "user/" + SERVICE_VERSION + "/");

    UserFeed currentPage;
    Link nextLink;

    do {
      currentPage = userService.getFeed(retrieveUrl, UserFeed.class);
      allUsers.getEntries().addAll(currentPage.getEntries());
      nextLink = currentPage.getLink(Link.Rel.NEXT, Link.Type.ATOM);
      if (nextLink != null) {
        retrieveUrl = new URL(nextLink.getHref());
      }
    } while (nextLink != null);

    return allUsers;
  }
}
