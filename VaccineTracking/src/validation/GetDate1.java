package validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetDate1 
{
	public static String getDateOfFirstDose(String contactnumber) throws ClassNotFoundException
	{
		String result="";
		String url="jdbc:mysql://localhost:3306/covidvaccine";
	    String username="root";
	    String password="root";
	    String query="select dateoffirstdose from vaccinated where contactnumber=?";
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
	public static String getTypeOfVaccine(String contactnumber) throws ClassNotFoundException
	{
		String result="";
		String url="jdbc:mysql://localhost:3306/covidvaccine";
	    String username="root";
	    String password="root";
	    String query="select typeofvaccine from vaccinated where contactnumber=?";
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