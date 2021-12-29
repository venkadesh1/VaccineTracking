<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="validation.GetRole" %>
<%
String csrfToken=null;
//String csrfToken=(String)request.getAttribute("csrftoken");
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
<title>Links</title>
<style type="text/css">
body{
font-size:30px;
margin: 30px 20px 240px;
background: linear-gradient(#b3daff, #ccffff);
}
.udbutton{
top:-10px;
    width: 10%;
    padding: 8px 5px;
    position: relative;
    
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
.udbutton:hover {
    box-shadow: 
        inset 0 1px rgba(255,255,255,0.2), 
        inset 0 20px 30px rgba(99,64,86,0.5);
}
</style>
</head>
<body>
<h1>To update first dose:<a href="UpdateDose1.jsp"><input type="button" class="udbutton" value="Dose 1"></a></h1><br>
<h1>To update second dose:<a href="UpdateDose2.jsp"><input type="button" class="udbutton" value="Dose 2"></a></h1><br>
<h1>To update covid status:<a href="Affected.jsp"><input type="button" class="udbutton" value="Covid result update"></a></h1><br>
<h1>To delete user:<a href="Delete.jsp"><input type="button" class="udbutton" value="Delete user"></a></h1><br>


</body>
</html>