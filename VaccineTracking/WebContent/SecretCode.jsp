<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="validation.BlockAccount"%>
<%@ page import="dao.Recovery"%>
<%@ page import="validation.GetRole"%>
<%
response.setHeader("Cache-Control","no-cache, no-store");
//Object canUpdatePassword=request.getAttribute("canUpdatePassword");
String contactnumber=null;
int count=0;
String secretcode=null;
String csrfToken=null;
String js1="<script type=\"text/javascript\">";
String l1="location='Index.jsp';";
String js2="</script>";

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
	out.println(js1 + "alert('Invalid page request');" + l1 + js2);
	return;
}
if(contactnumber!=null)
{
	String role=GetRole.role(contactnumber);
	count=BlockAccount.getWrongPasswordCount(contactnumber);
	secretcode=Recovery.getSecretCode(contactnumber);
	if(count==5 && secretcode==null)
	{
		response.sendRedirect("ChangePasswordWithCode.jsp");
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
<form action="validatesecretcode" method="post">
Enter secret code:<input type="text" name="secretcode"><br>
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
<input type="submit" value="Submit">
</form>
<form action="resentcode" method="post">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
<input type="submit" value="GetNewCode">
</form>
</body>
</html>