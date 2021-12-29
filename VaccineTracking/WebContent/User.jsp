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
<style type="text/css">
body{
position:relative;
background: linear-gradient(#b3daff, #ccffff); 
}
.form div{
    /* Size and position */
    width: 300px;
    margin: 30px 30px 30px;
    padding: 25px;
    position: relative;
    width: 20%;
    height: 500%;

    /* Font styles */
    font-family: 'Raleway', 'Lato', Arial, sans-serif;
    color: green;
    text-shadow: 0 2px 1px rgba(0,0,0,0.2);
}
.form input{
    /* Size and position */
    width: 100%;
    padding: 8px 4px 8px 10px;
    margin-bottom: 15px;

    border: 1px solid rgba(78,48,67, 0.8);
    background: rgba(0,0,0,0.15);
    border-radius: 2px;

    /* Font styles */
    font-family: 'Raleway', 'Lato', Arial, sans-serif;
    color: black;
    font-size: 13px;
}
.form div input[type=submit] {
    /* Size and position */
    width: 45%;
    padding: 8px 5px;
    
    /* Styles */
    background: linear-gradient(rgba(99,64,86,0.5), rgba(76,49,65,0.7));    
    border-radius: 5px;
    border: 1px solid #4e3043;
    box-shadow: 
    	inset 0 1px rgba(255,255,255,0.4), 
    	0 2px 1px rgba(0,0,0,0.1);
    cursor: pointer;
    transition: all 0.3s ease-out;

    /* Font styles */
    color: white;
    text-shadow: 0 1px 0 rgba(0,0,0,0.3);
    font-size: 16px;
    font-weight: bold;
    font-family: 'Raleway', 'Lato', Arial, sans-serif;
}

.getmystatus input[type=submit] {
    /* Size and position */
    width: 8%;
    padding: 8px 5px;
    position: relative;
    left: 50px;
    
    /* Styles */
    background: linear-gradient(rgba(99,64,86,0.5), rgba(76,49,65,0.7));    
    border-radius: 5px;
    border: 1px solid #4e3043;
    box-shadow: 
    	inset 0 1px rgba(255,255,255,0.4), 
    	0 2px 1px rgba(0,0,0,0.1);
    cursor: pointer;
    transition: all 0.3s ease-out;

    /* Font styles */
    color: white;
    text-shadow: 0 1px 0 rgba(0,0,0,0.3);
    font-size: 16px;
    font-weight: bold;
    font-family: 'Raleway', 'Lato', Arial, sans-serif;
}
.logout input[type=submit] {
    /* Size and position */
    width: 8%;
    padding: 8px 5px;
    position: relative;
    left: 220px;
    top: -38px;
    
    /* Styles */
    background: linear-gradient(rgba(99,64,86,0.5), rgba(76,49,65,0.7));    
    border-radius: 5px;
    border: 1px solid #4e3043;
    box-shadow: 
    	inset 0 1px rgba(255,255,255,0.4), 
    	0 2px 1px rgba(0,0,0,0.1);
    cursor: pointer;
    transition: all 0.3s ease-out;

    /* Font styles */
    color: white;
    text-shadow: 0 1px 0 rgba(0,0,0,0.3);
    font-size: 16px;
    font-weight: bold;
    font-family: 'Raleway', 'Lato', Arial, sans-serif;
}
input[type=submit]:hover {
    box-shadow: 
        inset 0 1px rgba(255,255,255,0.2), 
        inset 0 20px 30px rgba(99,64,86,0.5);
}
</style>
</head>
<body>
<form action="changeusername" method="post" class="form">
<div>
<h1>Change user name</h1>
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
Enter new user name:<input type="text" name="newusername"><br>
Enter password:<input type="password" name="password"><br>
<input type="submit" value="Change name">
</div>
</form>
<form action="changepassword" method="post" class="form">
<div>
<h1>Change Password</h1>
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
Enter old password:<input type="password" name="oldpassword"><br>
Enter new password:<input type="password" name="newpassword"><br>
Confirm new password:<input type="password" name="confirmpassword"><br>
<input type="submit" value="Change Password">
</div>
</form>
<form action="usersession" method="post" class="getmystatus">
<div>
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
<input type="submit" value="GetMyStatus">
</div>
</form>
<form action="logout" method="post" class="logout">
<div>
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
<input type="submit" value="Logout">
</div>
</form>
</body>
</html>