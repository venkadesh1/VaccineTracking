package dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import validation.CalculateDaysBetween;
import validation.GetRole;

public class LoginDao 
{
	public static String login1(String contactnumber) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
		String username="root";
		String password="root";
		String query="select * from personaldetails where contactnumber=?";
		Class.forName("com.mysql.jdbc.Driver");
		String result="";
		try
		{
			Connection con = DriverManager.getConnection(url,username,password);
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, contactnumber);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(2);
			ps.close();
			con.close();
		}
		catch(Exception e)
		{

		}
		return result;
	}
	public static String login2(String contactnumber) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
		String username="root";
		String password="root";
		String query="select * from vaccinated where contactnumber=?";
		String result=null;
		Class.forName("com.mysql.jdbc.Driver");
		try
		{
			Connection con = DriverManager.getConnection(url,username,password);
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, contactnumber);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(2);
			result+=" "+rs.getString(3);
			result+=" "+rs.getString(4);
			result+=" "+rs.getString(5);
			result+=" "+rs.getString(6);
			
			
			
			
			
			String typeofvaccinedb=rs.getString(2);
			String lastaffecteddb=rs.getString(3);
			String date1db=rs.getString(4);
			String date2db=rs.getString(5);
			String recovereddatedb=rs.getString(6);
		}


/*
			String js1="<script type=\"text/javascript\"> ";
			String js2="</script>";


			  if(date1db==null || date2db==null)
            {
            	if(lastaffecteddb==null)
            	{
            		out.println(js1+"if (window.confirm('Click \"ok\" to know about vaccines. Cancel to view your details ')) \n" + 
            				"{\n" + 
            				"window.location.href='https://en.wikipedia.org/wiki/Oxford%E2%80%93AstraZeneca_COVID-19_vaccine';\n" + 
            				"};"+js2);
            		//out.println(js1+"alert('Available vaccines:<br>"+covidshield+"<br>"+covaxin+"');"+js2);
            	}
            	else
            	{
            		if(recovereddatedb!=null && CalculateDaysBetween.getDays(recovereddatedb, lastaffecteddb)<0)
            		{
            			out.println(js1+"if (window.confirm('Click \"ok\" to know about vaccines. Cancel to view your details ')) \n" + 
                				"{\n" + 
                				"window.location.href='https://en.wikipedia.org/wiki/Oxford%E2%80%93AstraZeneca_COVID-19_vaccine';\n" + 
                				"};"+js2);
            		}
            		else
            		{
            			out.println(js1+"alert('You affected by covid on "+lastaffecteddb+" Please stay safe');"+js2);
            		}
            	}
            }









			if(typeofvaccinedb==null)
			{
				out.println("Type Of Vaccine:Not vaccinated yet<br>");
			}
			else
			{
				out.println("Type Of Vaccine:"+rs.getString(2)+"<br>");
			}
			if(lastaffecteddb==null)
			{
				out.println("Last Affected Date:Not affected<br>");
			}
			else
			{
				out.println("Last Affected Date:"+lastaffecteddb+"<br>");
				if(recovereddatedb!=null)
				{
					out.println("Recovered Date:"+recovereddatedb+"<br>");
				}
			}
			if(date1db==null)
			{
				out.println("Date of First Dose:Not vaccinated yet<br>");
				out.println("Available vaccines:<br>");
				out.println("<a href='https://en.wikipedia.org/wiki/Oxford%E2%80%93AstraZeneca_COVID-19_vaccine'>CovidShield</a><br>");
				out.println("<a href='https://en.wikipedia.org/wiki/Covaxin'>Covaxin</a>");
			}
			else
			{
				out.println("Date of First Dose:"+date1db+"<br>");;
			}
			if(!(date2db==null))
			{
				out.println("Date of Second Dose:"+date2db+"<br>");
			}
			 else if(date2db==null)
            {
            	out.println("Date of Second Dose:Not vaccinated yet<br>");
            } 
			else if(lastaffecteddb==null && typeofvaccinedb.equals("covidshield"))
			{
				out.println("Second Dose Expected date:"+CalculateDaysBetween.getDate(date1db, 90)+"<br>");
			}
			else if(lastaffecteddb==null && typeofvaccinedb.equals("covaxin"))
			{
				out.println("Second Dose Expected date:"+CalculateDaysBetween.getDate(date1db, 27)+"<br>");
			}
			else if(!(lastaffecteddb==null) && typeofvaccinedb.equals("covidshield"))
			{
				String expectedwithlastaffected=CalculateDaysBetween.getDate(lastaffecteddb, 30);
				String expectedwithdate1=CalculateDaysBetween.getDate(date1db, 90);
				if(CalculateDaysBetween.getDays(expectedwithlastaffected, expectedwithdate1)>0)
				{
					out.println("Second Dose Expected date:"+expectedwithdate1+"<br>");
					out.println("Available vaccines:<br>");
					out.println("<a href='https://en.wikipedia.org/wiki/Oxford%E2%80%93AstraZeneca_COVID-19_vaccine'>CovidShield</a><br>");
					out.println("<a href='https://en.wikipedia.org/wiki/Covaxin'>Covaxin</a>");
				}
				else
				{
					out.println("Second Dose Expected date:"+expectedwithlastaffected+"<br>");
					out.println("Available vaccines:<br>");
					out.println("<a href='https://en.wikipedia.org/wiki/Oxford%E2%80%93AstraZeneca_COVID-19_vaccine'>CovidShield</a><br>");
					out.println("<a href='https://en.wikipedia.org/wiki/Covaxin'>Covaxin</a>");
				}
			}
			else if(!(lastaffecteddb==null) && typeofvaccinedb.equals("covaxin"))
			{
				String expectedwithlastaffected=CalculateDaysBetween.getDate(lastaffecteddb, 30);
				String expectedwithdate1=CalculateDaysBetween.getDate(date1db, 27);
				if(CalculateDaysBetween.getDays(expectedwithlastaffected, expectedwithdate1)>0)
				{
					out.println("Second Dose Expected date:"+expectedwithdate1+"<br>");
					out.println("Available vaccines:<br>");
					out.println("<a href='https://en.wikipedia.org/wiki/Oxford%E2%80%93AstraZeneca_COVID-19_vaccine'>CovidShield</a><br>");
					out.println("<a href='https://en.wikipedia.org/wiki/Covaxin'>Covaxin</a>");
				}
				else
				{
					out.println("Second Dose Expected date:"+expectedwithlastaffected+"<br>");
					out.println("Available vaccines:<br>");
					out.println("<a href='https://en.wikipedia.org/wiki/Oxford%E2%80%93AstraZeneca_COVID-19_vaccine'>CovidShield</a><br>");
					out.println("<a href='https://en.wikipedia.org/wiki/Covaxin'>Covaxin</a>");
				}
			}
			out.println("</body></html>");
			ps.close();
			con.close();
		}*/
		catch(Exception e)
		{

		}
		return result;
	}
	public static String getPass(String contactnumber) throws ClassNotFoundException
	{
		String result="";
		String url="jdbc:mysql://localhost:3306/covidvaccine";
		String username="root";
		String password="root";
		String query="select password from personaldetails where contactnumber=?";
		Class.forName("com.mysql.jdbc.Driver");
		try
		{
			Connection con = DriverManager.getConnection(url,username,password);
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, contactnumber);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
		}
		return result;
	}
}