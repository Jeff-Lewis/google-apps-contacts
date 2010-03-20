<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.gdata.client.Service.*" %>
<%@ page import="com.google.gdata.client.contacts.ContactsService" %>
<%@ page import="com.google.gdata.client.*" %>
<%@ page import="com.google.gdata.client.contacts.*" %>
<%@ page import="com.google.gdata.data.*" %>
<%@ page import="com.google.gdata.data.contacts.*" %>
<%@ page import="com.google.gdata.data.contacts.ContactFeed" %>
<%@ page import="com.google.gdata.data.contacts.ContactEntry" %>
<%@ page import="com.google.gdata.data.extensions.*" %>
<%@ page import="com.google.gdata.util.*" %>
<%@ page import="java.net.URL" %>
<%@ page import="nl.miraclebenelux.domaincontacts.server.MirContacts" %>
<%@ page import="nl.miraclebenelux.domaincontacts.server.DomainRec" %>
<%@ page import="nl.miraclebenelux.domaincontacts.server.DomainUsers" %>
<%@ page import="nl.miraclebenelux.domaincontacts.server.PMF" %>
<%@ page import="javax.jdo.annotations.IdGeneratorStrategy" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.jdo.annotations.PrimaryKey" %>
<%@ page import="javax.jdo.*" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<html>
  <body>
<%    
    String activate = request.getParameter("activate");
    String key1     = request.getParameter("key");
    String domain   = request.getParameter("domain");
    String admin    = request.getParameter("admin");
    String passwd   = request.getParameter("passwd");
    String passwd2  = request.getParameter("passwd2");
    
    if (activate == null && admin == null)
    {
%>    	
<form action="/configure.jsp" method="post">
<table>
<th>
<td colspan="2">
Configure Domain
</td>
</th>
<tr>
<td>
Domain
</td>
<td>
<input type="text" name="domain" value="<%=domain%>">
</td>
</tr>
<tr>
<td>
(Administrator) userid
</td>
<td>
<input type="text" name="admin" value"<%=admin%>">
</td>
</tr>
<tr>
<td>
Password
</td>
<td>
<input type="password" name="passwd" value"<%=passwd%>">
</td>
</tr>
<tr>
<td>
Replace/Reset Password
</td>
<td>
<input type="password" name="passwd2" value"<%=passwd2%>">
</td>
</tr>
<tr>
<td colspan="2">
<input type="submit" value="submit">
</td>
</tr>
</table>
</form>    	
<%
    }
    else if (domain != null)
    {
    	  ContactFeed feed;
   		  DomainUsers DU;
    		  boolean exists = false;
		  
    	      PersistenceManager pm = PMF.get().getPersistenceManager();
    	      
    	      DomainRec e;
    	      Key k = null;
    	      
    	      try 
    	      {
    	         k = KeyFactory.createKey(DomainRec.class.getSimpleName(), domain);

    	         e = pm.getObjectById(DomainRec.class, k);
    	      }
    	      catch (JDOObjectNotFoundException pEx)
    	      {
        	      k = KeyFactory.createKey(DomainRec.class.getSimpleName(), domain);
    	    	  e = new DomainRec(admin, passwd, domain);
    	    	  e.setKey(k);
    	      }
    	      if (e.getUsername() != null && passwd2 == "OverWrite")
    	      {
    	    	  e.setDomain(domain);
    	    	  e.setPassword(passwd);
    	      }
    	      else
    	      {
    	          if (e.getUsername() != null)
    	          {
    	        	  out.print("Supply the right Replace password to overwrite the existing information.");
    	          }
    	          else
    	          {
    	       	      e = new DomainRec(admin, passwd, domain); 
    	    	      e.setKey(k);
    	          }
    	      }
 
    	      try
    	      {
    	         pm.makePersistent(e);
    	      } 
 		      finally
    	      {
    	         pm.close();
    	      }
    	      response.sendRedirect("/");

    }
%>
  </body>
</html>