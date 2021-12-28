<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="validation.BlockAccount"%>
<%@ page import="dao.Recovery"%>
<%@ page import="validation.GetRole"%>
<%
response.setHeader("Cache-Control","no-cache, no-store");
String csrfToken=null;
String contactnumber=null;
int count=0;
String secretcode=null;
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
if(contactnumber==null)
{
	response.sendRedirect("Index.jsp");
}
if(contactnumber!=null)
{
	String role=GetRole.role(contactnumber);
	count=BlockAccount.getWrongPasswordCount(contactnumber);
	secretcode=Recovery.getSecretCode(contactnumber);
	if(count==5 && secretcode!=null)
	{
		response.sendRedirect("SecretCode.jsp");
	}
	else if(count<5)
	{
		if(role.equals("superadmin"))
		{
			response.sendRedirect("SuperAdmin.jsp");
		}
		else if(role.equals("admin"))
		{
			response.sendRedirect("Admin.jsp");
		}
		else
		{
			response.sendRedirect("User.jsp");
		}
	}
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Change Password</h1>
<form action="changepasswordwithcode" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
Enter new password:<input type="password" name="newpassword"><br>
Confirm new password:<input type="password" name="confirmpassword"><br>
<input type="submit" value="Change Password">
</form>
</body>
</html>