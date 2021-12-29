package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateDose1Dao 
{
	public static void update1(String contactnumber,String typeofvaccine,String date1) throws ClassNotFoundException
	{
		String url="jdbc:mysql://localhost:3306/covidvaccine";
        String username="root";
        String password="root";//
        String query="update vaccinated set typeofvaccine=?,dateoffirstdose=? where contactnumber=?";
        Class.forName("com.mysql.jdbc.Driver");
        try{
        	Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, typeofvaccine);
            ps.setString(2, date1);
            ps.setString(3, contactnumber);
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch(Exception e)
        {
        	
        }
	}
}