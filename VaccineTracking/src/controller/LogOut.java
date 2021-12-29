package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/logout")
public class LogOut extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		res.setContentType("text/html");
		String js1="<script type=\"text/javascript\">";
		String js2="location='Index.jsp';</script>";

		String csrfCookie = null;
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("csrftoken")) {
				csrfCookie = cookie.getValue();
			}
		}

		String csrfField = req.getParameter("csrfToken");

		if (csrfCookie == null || csrfField == null || !csrfCookie.equals(csrfField)) {
			try {
				res.sendError(401);
			} catch (IOException e) {
			}
			return;
		}



		String newusername=(String) req.getAttribute("username");
		String ispasswordupdated=(String) req.getAttribute("ispasswordupdated");





		Cookie cookie=null;
		Cookie ck[]=req.getCookies();
		if(ck!=null)
		{  
			for (int i = 0; i < ck.length; i++) 
			{
				cookie = ck[i];
				if(cookie.getName().equals("contactnumber"))
				{
					cookie.setValue(null);
					cookie.setMaxAge(0);
					res.addCookie(cookie);
				}
				if(cookie.getName().equals("csrftoken"))
				{
					cookie.setValue(null);
					cookie.setMaxAge(0);
					res.addCookie(cookie);
				}

			} 
		}
		PrintWriter out=res.getWriter();
		if(newusername==null && ispasswordupdated==null)
		{
		out.println(js1+"alert('Logout successful');"+js2);
		}
		else if(newusername!=null)
		{
			out.println(js1+"alert('User name updated as "+newusername+"');"+js2);
		}
		else
		{
			out.println(js1+"alert('Password updated');"+js2);
		}
	}
}