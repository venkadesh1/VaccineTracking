<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="validation.GetRole" %>
<%
String csrfToken=null;
String role=null;
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
role=GetRole.role(contactnumber);
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

<h1>Covid result update</h1>
<form action="affected" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
Contact Number:<input type="text" name="contactnumber"><br>
Do you affected by covid:<select name="status">
<option value="no">No</option>
<option value="yes">Yes</option>
</select><br>
Date of affected:<input type="date" name="dateofaffected"><br>
<input type="submit" value="Update">
</form>

</body>
</html>