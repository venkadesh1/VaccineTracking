package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChangeUserNameDao;
import dao.LoginDao;
import validation.Encrypt;
import validation.GetRole;
import validation.GetUserName;
import validation.ValidateUserName;

@WebServlet("/changeusername")
public class ChangeUserName extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		String contactnumber=null;
		String csrfCookie = null;
		String role=null;
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("contactnumber")) {
				contactnumber = cookie.getValue();
			}
			if (cookie.getName().equals("csrftoken")) {
				csrfCookie = cookie.getValue();
			}
		}

		res.setContentType("text/html");
		String js1="<script type=\"text/javascript\">";
		String l1="location='Index.jsp';";
		String l2=null;
		String js2="</script>";
		PrintWriter out=res.getWriter();



		String csrfField = req.getParameter("csrfToken");

		if (csrfCookie == null || csrfField == null || !csrfCookie.equals(csrfField)) {
			try {
				res.sendError(401);
			} catch (IOException e) {
			}
			return;
		}
		if(contactnumber==null)
		{
			out.println(js1+"alert('Please login first');"+l1+js2);
		}

		try {
			role=GetRole.role(contactnumber);
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		if(role.equals("superadmin"))
		{
			l2="location='SuperAdmin.jsp';";
		}
		else if(role.equals("admin"))
		{
			l2="location='Admin.jsp';";
		}
		else
		{
			l2="location='User.jsp';";
		}
		
		
		
		
		String newusername=req.getParameter("newusername");
		String password=req.getParameter("password");
		if(!ValidateUserName.isOnlyAlpha(newusername) || newusername.length()<3)
		{
			out.println(js1+"alert('Invalid user name');"+l2+js2);
		}
		synchronized(this)
		{
			String usernamedb=null;
			String passworddb=null;
			try {
				passworddb=LoginDao.getPass(contactnumber);
				usernamedb=GetUserName.getUserName(contactnumber);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			if(newusername.equals(usernamedb))
			{
				out.println(js1+"alert('User name cannot be same');"+l2+js2);
			}
			else if(!Encrypt.encryptValidate(password, passworddb))
			{
				out.println(js1+"alert('Incorrect password');"+l2+js2);
			}
			else
			{
				try {
					ChangeUserNameDao.changeUserName(contactnumber, newusername);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				req.setAttribute("username", newusername);
				req.getRequestDispatcher("logout").forward(req, res);
				//out.println(js1+"alert('User name updated as "+newusername+"');"+l2+js2);
			}
		}
	}
}
