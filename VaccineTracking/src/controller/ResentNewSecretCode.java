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
import validation.EmailUtility;

@WebServlet("/resentcode")
public class ResentNewSecretCode extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		res.setContentType("text/html");
		String js1="<script type=\"text/javascript\">";
		String l1="location='SecretCode.jsp';";
		String js2="</script>";
		PrintWriter out=res.getWriter();
		
		
		
		
		
		String contactnumber=null;
		String csrfCookie = null;
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("contactnumbercode")) {
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
		
		
		String emailid=Recovery.getEmailid(contactnumber);
		LocalDateTime secretcodetime=LocalDateTime.parse(Recovery.getDateTime(contactnumber));
		long sec=ChronoUnit.SECONDS.between(secretcodetime, LocalDateTime.now());
		if(sec>60)
		{
			String secretcode=Recovery.getCode();
			String datetime=LocalDateTime.now().toString();
			String message="Hii "+emailid+ " Your secret code is: "+secretcode+" valid for 3 minutes";
			Recovery.setSecretCode(contactnumber, secretcode,datetime);
			try {
				EmailUtility.sendMail(message, emailid);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			out.println(js1+"alert('New secret code sent');"+l1+js2);
			return;
		}
		else if(sec<=60)
		{
			out.println(js1+"alert('Please wait for "+(60-sec)+" seconds to sent new code');"+l1+js2);
		}
	}
}
