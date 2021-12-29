package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;

import dao.Recovery;
import validation.BlockAccount;
import validation.EmailUtility;
import validation.GetRole;

@WebServlet("/validatesecretcode")
public class SecretCode extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		res.setContentType("text/html");
		String js1="<script type=\"text/javascript\">";
		String l1="location='Index.jsp';";
		String l2="location='ChangePasswordWithCode.jsp';";
		String l3="location='SecretCode.jsp';";
		String js2="</script>";
		PrintWriter out=res.getWriter();


		String contactnumber=null;
		String csrfCookie = null;
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("contactnumber")) {
				contactnumber = cookie.getValue();
			}
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


		String secretcodetyped=req.getParameter("secretcode");
		String secretcode=Recovery.getSecretCode(contactnumber);
		String emailid=Recovery.getEmailid(contactnumber);
		LocalDateTime secretcodetime=LocalDateTime.parse(Recovery.getDateTime(contactnumber));
		if(secretcodetyped.equals(secretcode))
		{
			long sec=ChronoUnit.SECONDS.between(secretcodetime, LocalDateTime.now());
			if(sec>180)
			{
				secretcode=Recovery.getCode();
				String datetime=LocalDateTime.now().toString();
				String message="Hii "+emailid+ " Your secret code is: "+secretcode+" valid for 3 minutes";
				Recovery.setSecretCode(contactnumber, secretcode,datetime);
				try {
					EmailUtility.sendMail(message, emailid);
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
				out.println(js1+"alert('Secret code expired. New secret code sent');"+l3+js2);
				return;
			}
			Recovery.setSecretCode(contactnumber, null, null);
			out.println(js1+"alert('Please reset your password');"+l2+js2);
		}
		else
		{
			out.println(js1+"alert('Secret code did not match');"+l3+js2);
		}
	}
}
