package nl.miraclebenelux.domaincontacts.server;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import com.google.gdata.data.appsforyourdomain.Email;
import com.google.gdata.data.appsforyourdomain.Login;
import com.google.gdata.data.appsforyourdomain.provisioning.UserEntry;

@SuppressWarnings("serial")
public class SyncJob extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		String domain = req.getParameter("domain");
		String temp   = req.getParameter("number");
		String full   = req.getParameter("full");
		
	    ServletOutputStream out = resp.getOutputStream();  
	    
	    DomainUsers UP = new DomainUsers(domain);
	    
	    try {
			for (UserEntry U : UP.retrieveAllUsers(domain).getEntries())
			{
				Email em = U.getEmail();

				Login L = U.getLogin();
				if (L.getAdmin())
				{
					out.print("* ");
				}
				else 
				{
					out.print("  ");
				}

				//out.println(L.getUserName() + " email: " + em.toString() + " kind: " + U.getKind());
				out.println(L.getUserName());
			}
		} 
	    catch (ServiceException e) 
		{
			out.println(e.getMessage());
		} 
		catch (com.google.gdata.util.ServiceException e) 
		{
              out.println(e.getInternalReason());
		}
	    
	    out.println("SyncJob: Hello");
		
	     return;  
	}
	
	
}
