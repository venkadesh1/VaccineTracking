<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="validation.GetRole"%>
<% String contactnumber=null;
String csrfToken=null;
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
if(contactnumber!=null && role.equals("superadmin"))
{
	response.sendRedirect("SuperAdmin.jsp");
}
else if(contactnumber!=null && role.equals("admin"))
{
	response.sendRedirect("Admin.jsp");
}
else if(contactnumber!=null && role.equals("user"))
{
	response.sendRedirect("User.jsp");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Sign Up</h1>
<form action="signup" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
Contact Number:<input type="text" name="contactnumber"><br>
Name:<input type="text" name="username"><br>
Password:<input type="password" name="password"><br>
Email id:<input type="text" name="emailid"><br>
<img src="captchaimage"/><br>
Captcha:<input type="text" name="captcha">
<input type="submit" value="SignUp">
</form>
Have an account:<a href="Index.jsp">Login</a>
</body>
</html>