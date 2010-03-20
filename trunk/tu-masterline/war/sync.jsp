<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.gdata.client.Service" %>
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
<%@ page import="nl.miraclebenelux.domaincontacts.server.SyncContacts" %>
<%@ page import="nl.miraclebenelux.domaincontacts.server.DomainRec" %>
<%@ page import="nl.miraclebenelux.domaincontacts.server.PMF" %>
<%@ page import="javax.jdo.annotations.IdGeneratorStrategy" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.jdo.annotations.PrimaryKey" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<html>
  <body>
<%    
    String todomain   = request.getParameter("todomain");
    String fromdomain    = request.getParameter("fromdomain");
    
    if (todomain == null && fromdomain == null)
    {
%>    	
<form action="/sync.jsp" method="post">
<table>
<th>
<td colspan="2">
Configure Domain
</td>
</th>
<tr>
<td>
From Domain
</td>
<td>
<input type="text" name="fromdomain" value="<%=fromdomain%>">
</td>
</tr>
<tr>
<td>
To Domain
</td>
<td>
<input type="text" name="todomain" value"<%=todomain%>">
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
    else if (fromdomain != null)
    {
    }
%>
  </body>
</html>