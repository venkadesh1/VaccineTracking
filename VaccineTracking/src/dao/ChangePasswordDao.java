package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ChangePasswordDao 
{
	public static void changePassword(String contactnumber,String pass) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
        String username="root";
        String password="root";//
        String query="update personaldetails set password=? where contactnumber=?";
        Class.forName("com.mysql.jdbc.Driver");
        try{
        	Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, pass);
            ps.setString(2, contactnumber);
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch(Exception e)
        {
        	
        }
	}
}
