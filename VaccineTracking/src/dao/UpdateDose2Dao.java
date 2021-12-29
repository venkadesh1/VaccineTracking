package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateDose2Dao 
{
	public static void update2(String contactnumber,String date2) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
        String username="root";
        String password="root";
        String query="update vaccinated set dateofseconddose=(?) where contactnumber=?";
        Class.forName("com.mysql.jdbc.Driver");
        try{
        	Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, date2);
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