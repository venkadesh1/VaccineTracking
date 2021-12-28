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

import dao.SignUpDao;
import model.SignUpModel;
import validation.Encrypt;


import validation.ValidateContactNumber;
import validation.ValidateEmail;
import validation.ValidateUserName;

@WebServlet("/signup")
public class SignUp extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{


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


		String contactnumber=req.getParameter("contactnumber");
		String name=req.getParameter("username");
		String password=req.getParameter("password");
		String captchaentered=req.getParameter("captcha");
		String emailid=req.getParameter("emailid");
		HttpSession session=req.getSession(false);
		String captcha =session.getAttribute("captcha_security").toString();

		res.setContentType("text/html");
		String js1="<script type=\"text/javascript\">";
		String l1="location='Index.jsp';";
		String l2="location='User.jsp';";
		String js2="</script>";

		PrintWriter out=res.getWriter();
		try {
			if(!ValidateUserName.isOnlyAlpha(name) || name.length()<3)
			{
				out.println(js1+"alert('Invalid user name');"+l1+js2);
				return;
			}
			else if(!ValidateContactNumber.isValid(contactnumber))
			{
				out.println(js1+"alert('Invalid contact number');"+l1+js2);
				return;
			}
			else if(password.length()<6)
			{
				out.println(js1+"alert('Invalid password length');"+l1+js2);
				return;
			}
			else if(!ValidateEmail.isValid(emailid))
			{
				out.println(js1+"alert('Invalid Email id');"+l1+js2);
				return;
			}
			else if(!captcha.equals(captchaentered))
			{
				out.println(js1+"alert('Invalid captcha');"+js2);
				return;
			}
			synchronized(this)
			{
				if(!ValidateContactNumber.isDoNotExist(contactnumber))
				{
					out.println(js1+"alert('Contact number exist');"+l1+js2);
				}
				else if(ValidateContactNumber.isValid(contactnumber) && ValidateContactNumber.isDoNotExist(contactnumber) && password.length()>=6)
				{
					password=Encrypt.getEncrypted(password);
					SignUpModel s=new SignUpModel();
					s.setContactnumber(contactnumber);
					s.setName(name);
					s.setPassword(password);
					s.setEmailid(emailid);
					SignUpDao.signup1(s);
					SignUpDao.signup2(contactnumber);

					Cookie ck=new Cookie("contactnumber",contactnumber);  
					res.addCookie(ck);  
					out.println(js1+"alert('Sign up successful');"+l2+js2);

					//res.sendRedirect("usersession");
					//req.getRequestDispatcher("usersession").forward(req, res);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}