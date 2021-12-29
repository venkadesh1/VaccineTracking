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
<title>Sign Up</title>

<style type="text/css">

body{
font-size:20px;
margin: 30px 20px 300px;
background: linear-gradient(#b3daff, #ccffff);
}
div{
font-size:20px;
}
.form h1 {
    color:black;
    font-size: 30px;
    padding-bottom: 20px;
}
.form {
    /* Size and position */
    width: 300px;
    margin: 60px auto 30px;
    padding: 25px;
    position: relative;
    width: 40%;
    height: 1000%;
    border-radius: 0.4em;
    border: 3px solid #191919;

    /* Font styles */
    font-family: 'Raleway', 'Lato', Arial, sans-serif;
    color: green;
    text-shadow: 0 2px 1px rgba(0,0,0,0.2);
}

.form h1 {
    color:black;
    font-size: 30px;
    padding-bottom: 20px;
}
.form input {
    /* Size and position */
    width: 60%;
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
.form input[type=submit] {
    /* Size and position */
    width: 40%;
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

.form input[type=submit]:hover {
    box-shadow: 
        inset 0 1px rgba(255,255,255,0.2), 
        inset 0 20px 30px rgba(99,64,86,0.5);
}
</style>


</head>
<body>
<form action="signup" method="post" class="form">
<h1>Sign Up</h1>
<label>Contact Number:<br></label>
<input type="text" name="contactnumber"><br>
<label>Name:<br></label>
<input type="text" name="username"><br>
<label>Password:<br></label>
<input type="password" name="password"><br>
<label>Email id:<br></label>
<input type="text" name="emailid"><br>
<img src="captchaimage"/><br>
<input type="text" name="captcha">
<input type="hidden" name="csrfToken" value="<%= csrfToken %>"/>
<input type="submit" value="SignUp">
<div>Have an account:<a href="Index.jsp">Login</a></div>
</form>
</body>
</html>