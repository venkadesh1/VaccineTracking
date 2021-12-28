package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LoginDao;
@WebServlet("/usersession")
public class UserSession extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{  



		res.setHeader("Cache-Control","no-cache, no-store");
		res.setContentType("text/html");  
		PrintWriter out=res.getWriter();  








		String contactnumber=null;
		String csrfCookie = null;
		Cookie cookie = null;
		Cookie ck[]=req.getCookies();  
		if(ck!=null)
		{  
			for (int i = 0; i < ck.length; i++) 
			{
				cookie = ck[i];
				if(cookie.getName().equals("contactnumber"))
				{
					contactnumber=cookie.getValue(); 
				}
				if (cookie.getName().equals("csrftoken")) {
					csrfCookie = cookie.getValue();
				}
			} 
		}
		//HttpSession session=req.getSession(false);
		//String contactnumber=(String) session.getAttribute("contactnumber");
		String csrfField = req.getParameter("csrfToken");

		if (csrfCookie == null || csrfField == null || !csrfCookie.equals(csrfField)) {
			try {
				res.sendError(401);
			} catch (IOException e) {
			}
			return;
		}

		if(contactnumber!=null)
		{  
			try {
					String name=LoginDao.login1(contactnumber);
					out.println("Name:"+name+"<br>");
					LoginDao.login2(contactnumber,req,res);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//req.getRequestDispatcher("Update.jsp").include(req, res);
		}
		else
		{
			res.sendRedirect("Index.jsp");
		}
		out.close(); 
	}  
}