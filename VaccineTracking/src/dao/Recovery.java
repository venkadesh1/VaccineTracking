package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Random;

public class Recovery 
{
	public static void setSecretCode(String contactnumber,String secretcode,String datetime)
	{
		String url = "jdbc:mysql://localhost:3306/covidvaccine";
		String user = "root";
		String pass = "root";
		String query = "update personaldetails set secretcode=?,secretcodetime=? where contactnumber=?";
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(url,user,pass);
            PreparedStatement ps=con.prepareStatement(query); 
            ps.setString(1, secretcode);
            ps.setString(2, datetime);
            ps.setString(3, contactnumber);
			ps.executeUpdate(); 
		}
		catch(Exception e)
		{
			
		}
	}
	public static String getSecretCode(String contactnumber)
	{
		String url = "jdbc:mysql://localhost:3306/covidvaccine";
		String user = "root";
		String pass = "root";
		String query = "SELECT secretcode FROM personaldetails where contactnumber=?";
		String secretcode=null;
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(url,user,pass);
            PreparedStatement ps=con.prepareStatement(query); 
            ps.setString(1, contactnumber);
			ResultSet rs=ps.executeQuery(); 
			rs.next();
			secretcode=rs.getString(1);
		}
		catch(Exception e)
		{
			
		}
		return secretcode;
	}
	public static String getDateTime(String contactnumber)
	{
		String url = "jdbc:mysql://localhost:3306/covidvaccine";
		String user = "root";
		String pass = "root";
		String query = "SELECT secretcodetime FROM personaldetails where contactnumber=?";
		String datetime=null;
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(url,user,pass);
            PreparedStatement ps=con.prepareStatement(query); 
            ps.setString(1, contactnumber);
			ResultSet rs=ps.executeQuery(); 
			rs.next();
			datetime=rs.getString(1);
		}
		catch(Exception e)
		{
			
		}
		return datetime;
	}
	public static String getEmailid(String contactnumber)
	{
		String url = "jdbc:mysql://localhost:3306/covidvaccine";
		String user = "root";
		String pass = "root";
		String query = "SELECT emailid FROM personaldetails where contactnumber=?";
		String emailid=null;
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(url,user,pass);
            PreparedStatement ps=con.prepareStatement(query); 
            ps.setString(1, contactnumber); 
			ResultSet rs=ps.executeQuery(); 
			rs.next();
			emailid=rs.getString(1);
		}
		catch(Exception e)
		{
			
		}
		return emailid;
	}
	public static String getCode()
	{
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}
}
