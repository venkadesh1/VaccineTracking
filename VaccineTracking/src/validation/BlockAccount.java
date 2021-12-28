package validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BlockAccount 
{
	public static void setDetails(String contactnumber,int wrongpasswordcount) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
        String username="root";
        String password="root";
        String query="update personaldetails set wrongpasswordcount=? where contactnumber=?";
        Class.forName("com.mysql.jdbc.Driver");
        try{
        	Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1, wrongpasswordcount);
            ps.setString(2, contactnumber);
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch(Exception e)
        {
        	
        }
	}
	public static void updateCount(String contactnumber,int wrongpasswordcount) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
        String username="root";
        String password="root";
        String query="update personaldetails set wrongpasswordcount=? where contactnumber=?";
        Class.forName("com.mysql.jdbc.Driver");
        try{
        	Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1, wrongpasswordcount);
            ps.setString(2, contactnumber);
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch(Exception e)
        {
        	
        }
	}
	public static int getWrongPasswordCount(String contactnumber) throws ClassNotFoundException
	{
		int result=0;
		String url="jdbc:mysql://localhost:3306/covidvaccine";
	    String username="root";
	    String password="root";
	    String query="select wrongpasswordcount from personaldetails where contactnumber=?";
	    Class.forName("com.mysql.jdbc.Driver");
	    try
	    {
	    	Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, contactnumber);
            ResultSet rs=ps.executeQuery();
            rs.next();
            result=rs.getInt(1);
            ps.close();
            con.close();
	}
	    catch(Exception e)
	    {
	    }
	    return result;
	}
}
