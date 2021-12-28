<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="validation.GetRole" %>
<%@ page import="validation.BlockAccount"%>
<% 
response.setHeader("Cache-Control","no-cache, no-store");
String contactnumber=null;
String csrfToken=null;
int count=0;
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
if(contactnumber==null || !role.equals("user"))
{
	response.sendRedirect("Index.jsp");
}
else if(contactnumber!=null)
{
	count=BlockAccount.getWrongPasswordCount(contactnumber);
	if(count==5)
	{
		response.sendRedirect("Index.jsp");
	}
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>My vaccine status</h1>
<form action="usersession" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
<input type="submit" value="GetMyStatus">
</form>
<h1>Change user name</h1>
<form action="changeusername" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
Enter new user name:<input type="text" name="newusername"><br>
Enter password:<input type="password" name="password"><br>
<input type="submit" value="Change name">
</form>
<h1>Change Password</h1>
<form action="changepassword" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
Enter old password:<input type="password" name="oldpassword"><br>
Enter new password:<input type="password" name="newpassword"><br>
Confirm new password:<input type="password" name="confirmpassword"><br>
<input type="submit" value="Change Password">
</form>
<h1>Logout</h1>
<form action="logout" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
<input type="submit" value="Logout">
</form>
</body>
</html>