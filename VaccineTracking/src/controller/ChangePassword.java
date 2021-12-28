package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChangePasswordDao;
import dao.LoginDao;
import validation.Encrypt;
import validation.GetRole;

@WebServlet("/changepassword")
public class ChangePassword extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		String contactnumber=null;
		String csrfCookie=null;
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


		String oldpassword=req.getParameter("oldpassword");
		String newpassword=req.getParameter("newpassword");
		String confirmpassword=req.getParameter("confirmpassword");

		if(contactnumber==null)
		{
			out.println(js1+"alert('Please login first');"+l1+js2);
		}
		if(!newpassword.equals(confirmpassword))
		{
			out.println(js1+"alert('New password and Confirm password does not match');"+l2+js2);
		}
		else if(newpassword.length()<6)
		{
			out.println(js1+"alert('Password length cannot be less than 6');"+l2+js2);
		}

		synchronized(this)
		{
			String passworddb=null;
			try {
				passworddb = LoginDao.getPass(contactnumber);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(!passworddb.equals(Encrypt.getEncrypted(oldpassword)))
			{
				out.println(js1+"alert('Old password not matches with the existing password');"+l2+js2);
			}
			else if(newpassword.equals(oldpassword) || Encrypt.encryptValidate(newpassword, passworddb))
			{
				out.println(js1+"alert('New password and Old password cannot be same');"+l2+js2);
			}
			else
			{
				String encrypytedpassword=Encrypt.getEncrypted(newpassword);
				try {
					ChangePasswordDao.changePassword(contactnumber,encrypytedpassword);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				req.setAttribute("ispasswordupdated", "yes");
				req.getRequestDispatcher("logout").forward(req, res);
				//out.println(js1+"alert('Password updated');"+l2+js2);
			}
		}

	}
}
