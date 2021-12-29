<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="dao.LoginDao" %>
<%
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
	}
}
if(contactnumber==null)
{
	response.sendRedirect("Index.jsp");
}


String name=LoginDao.login1(contactnumber);
String[] result=LoginDao.login2(contactnumber).split(" ");
String typeofvaccine=result[0];
String lastaffecteddb=result[1];
String date1db=result[2];
String date2db=result[3];
String recovereddatedb=result[4];




%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Details</title>
<style type="text/css">
body{
font-size:30px;
margin: 30px 20px 600px;
background: linear-gradient(#b3daff, #ccffff);
}
table{
font-size:30px;
position: relative;
margin: 100px 600px 60px;
}
h1{
margin: 60px 600px 60px;
}
.udbutton{
    width: 45%;
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
<h1>Account Details</h1>
<table>
<tbody>

<tr>
<td>Contact Number:</td>
<td><%=contactnumber %></td>
</tr>

<tr>
<td>Name:</td>
<td><%=name%></td>
</tr>

<tr>
<td>Type of vaccine:</td>
<td><%=typeofvaccine%></td>
</tr>

<tr>
<td>Date of first dose:</td>
<td><%=date1db%></td>
</tr>

<tr>
<td>Date of second dose:</td>
<td><%=date2db%></td>
</tr>

<tr>
<td>Recovered date:</td>
<td><%=recovereddatedb%></td>
</tr>

<tr>
<td rowspan="2"><a href="Index.jsp"><input type="button" value="Back" class="udbutton"></a></td>
</tr>

</tbody>




</table>
</body>
</html>