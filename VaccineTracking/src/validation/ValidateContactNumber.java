package validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.*;

public class ValidateContactNumber 
{
	public static boolean isValid(String contactnumber)
    {
		Pattern p = Pattern.compile("^[7-9][0-9]{9}$");
        Matcher m = p.matcher(contactnumber);
        if(m.matches())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public static boolean isDoNotExist(String contactnumber) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
	    String username="root";
	    String password="root";
	    String query="select name from personaldetails where contactnumber=?";
	    String check1="";
	    Class.forName("com.mysql.jdbc.Driver");
	    try
	    {
	    	Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, contactnumber);
            ResultSet rs=ps.executeQuery();
            rs.next();
            check1= rs.getString(1);
            ps.close();
            con.close();
	    }
	    catch(Exception e)
	    {
	    }
	    if(check1.equals(""))
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	}
}