<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="validation.GetRole" %>
<%
String csrfToken=null;
response.setHeader("Cache-Control","no-cache, no-store");
String contactnumber=null;

Cookie cookie = null;
Cookie ck[]=request.getCookies();  
if(ck!=null)
{  
	for (int i = 0; i < ck.length; i++) 
	{
        cookie = ck[i];
        if(cookie.getName().equals("contactnumber"))
        {
        	contactnumber=cookie.getValue(); 
        }
        if(cookie.getName().equals("csrftoken"))
        {
        	csrfToken=cookie.getValue(); 
        }
	}
}
String role=GetRole.role(contactnumber);
if(contactnumber==null || role.equals("user"))
{
	response.sendRedirect("Index.jsp");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="delete" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
<h1>Contact number:</h1><input type="text" name="contactnumber"><br>
<input type="submit" value="delete">
</form>
</body>
</html>