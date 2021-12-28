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
import validation.BlockAccount;
import validation.Encrypt;
import validation.GetRole;

@WebServlet("/changepasswordwithcode")
public class ChangePasswordWithCode extends HttpServlet
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
		String l1="location='ChangePasswordWithCode.jsp';";
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
		String newpassword=req.getParameter("newpassword");
		String confirmpassword=req.getParameter("confirmpassword");
		
		if(!newpassword.equals(confirmpassword))
		{
			out.println(js1+"alert('New password and Confirm password does not match');"+l1+js2);
			return;
		}
		else if(newpassword.length()<6)
		{
			out.println(js1+"alert('Password length cannot be less than 6');"+l1+js2);
			return;
		}
		String passworddb=null;
		try {
			passworddb = LoginDao.getPass(contactnumber);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(Encrypt.encryptValidate(newpassword, passworddb))
		{
			out.println(js1+"alert('New password and Old password cannot be same');"+l1+js2);
			return;
		}
		else
		{
			String encrypytedpassword=Encrypt.getEncrypted(newpassword);
			try {
				ChangePasswordDao.changePassword(contactnumber,encrypytedpassword);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try
			{
				BlockAccount.setDetails(contactnumber, 0);
				role=GetRole.role(contactnumber);
			}
			catch(Exception e)
			{
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
			out.println(js1+"alert('Password updated');"+l2+js2);
		}
	}
}
