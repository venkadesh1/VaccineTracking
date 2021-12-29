package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import model.SignUpModel;

public class SignUpDao 
{
	public static void signup1(SignUpModel s) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
	    String username="root";
	    String password="root";
	    String query="Insert into personaldetails (contactnumber,name,password,role,emailid,wrongpasswordcount) values(?,?,?,?,?,?)";
	    Class.forName("com.mysql.jdbc.Driver");
	    try{
	            Connection con = DriverManager.getConnection(url,username,password);
	            PreparedStatement ps=con.prepareStatement(query);
	            ps.setString(1, s.getContactnumber());
	            ps.setString(2, s.getName());
	            ps.setString(3, s.getPassword());
	            ps.setString(4, s.getRole());
	            ps.setString(5, s.getEmailid());
	            ps.setInt(6, 0);
	            ps.executeUpdate();
	            ps.close();
	            con.close();
	       }
	    catch(Exception e)
	    {
	    }
	}
	public static void signup2(String contactnumber) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
	    String username="root";
	    String password="root";
	    String query="Insert into vaccinated (contactnumber) values(?)";
	    Class.forName("com.mysql.jdbc.Driver");
	    try{
	            Connection con = DriverManager.getConnection(url,username,password);
	            PreparedStatement ps=con.prepareStatement(query);
	            ps.setString(1, contactnumber);
	            ps.executeUpdate();
	            ps.close();
	            con.close();
	       }
	    catch(Exception e)
	    {
	    }
	}
}