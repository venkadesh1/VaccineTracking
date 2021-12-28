<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="validation.CSRF" %>
<%@ page import="validation.GetRole"%>
<%@ page import="validation.BlockAccount"%>
<%@ page import="dao.Recovery"%>
<%
response.setHeader("Cache-Control","no-cache, no-store");

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
       /* if(cookie.getName().equals("contactnumbercode"))
        {
        	isCodeSent=cookie.getValue(); 
        }
        if(cookie.getName().equals("contactnumberchangepassword"))
        {
        	canUpdatePassword=cookie.getValue(); 
        }*/
	}
}
/*if(isCodeSent!=null)
{
	response.sendRedirect("SecretCode.jsp");
}
if(canUpdatePassword!=null)
{
	response.sendRedirect("ChangePasswordWithCode.jsp");
}*/


if(contactnumber!=null)
{
	count=BlockAccount.getWrongPasswordCount(contactnumber);
	secretcode=Recovery.getSecretCode(contactnumber);
	if(count==5 && secretcode!=null)
	{
		response.sendRedirect("SecretCode.jsp");
		return;
	}
	else if(count==5 && secretcode==null)
	{
		response.sendRedirect("ChangePasswordWithCode.jsp");
		return;
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


String csrfToken = CSRF.getToken();

javax.servlet.http.Cookie cooki = new javax.servlet.http.Cookie("csrftoken", csrfToken);
response.addCookie(cooki);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index Page</title>
</head>
<body>
<h1 align="center">Covid Vaccine Tracking Application</h1>
<h1>Login</h1>
<form action="login" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
Contact Number:<input type="text" name="contactnumber" id="contactnumber"><br>
Password:<input type="password" name="password"><br>
<img src="captchaimage"/><br>
Captcha:<input type="text" name="captcha">
<input type="submit" value="Login">
</form>
New User:<a href="Signup.jsp">Sign Up</a>
</body>
</html>