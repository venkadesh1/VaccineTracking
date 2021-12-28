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

import validation.CalculateDaysBetween;
import validation.GetDate2;
import validation.GetRole;
import validation.ValidateContactNumber;
import dao.AffectedDao;

@WebServlet("/affected")
public class Affected extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{

		String admincontactnumber=null;
		String csrfCookie = null;
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("csrftoken")) {
				csrfCookie = cookie.getValue();
			}
			if (cookie.getName().equals("contactnumber")) {
				admincontactnumber = cookie.getValue();
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
		String value=req.getParameter("status");
		String affecteddate=req.getParameter("dateofaffected");
		String adminrole=null;
		String userrole=null;
		try {
			adminrole=GetRole.role(admincontactnumber);
			userrole=GetRole.role(contactnumber);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		res.setContentType("text/html");
		String js1="<script type=\"text/javascript\">";
		String l1="location='Link.jsp';";
		String l2="location='Affected.jsp';";
		String l3="location='Index.jsp';";
		String js2="</script>";

		PrintWriter out=res.getWriter();

		//res.setHeader("Cache-Control","no-cache, no-store");

		if(admincontactnumber==null || adminrole.equals("user"))
		{
			out.println(js1+"alert('Please login first');"+l3+js2);
		}
		else if(adminrole.equals("admin") && userrole.equals("admin"))
		{
			out.println(js1+"alert('Admin cannot update admin');"+l2+js2);
		}
		else if(adminrole.equals("superadmin") && admincontactnumber.equals(contactnumber))
		{
			out.println(js1+"alert('You cannot update details yourself');"+l2+js2);
		}
		else
		{
			try{
				if(ValidateContactNumber.isDoNotExist(contactnumber) || !(ValidateContactNumber.isValid(contactnumber)))
				{
					out.println(js1+"alert('Invalid contact number');"+l2+js2);
				}
			}
			catch(Exception e)
			{
			}
		}
		if(value.equals("yes"))
		{
			synchronized(this)
			{
				int count=0;
				try {
					AffectedDao.updateAffected(contactnumber,affecteddate);
					count++;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				try {
					String date2db=GetDate2.getDateOfSecondDose(contactnumber);
					if(!(date2db==null) && CalculateDaysBetween.getDays(date2db, affecteddate)>0)
					{
						AffectedDao.delete1(contactnumber);
						AffectedDao.delete2(contactnumber);
						count++;
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(count==2)
				{
					out.println(js1+"alert('"+contactnumber+" deleted');"+l1+js2);
				}
				else if(count==1)
				{
					out.println(js1+"alert('Date of affected updated');"+l1+js2);
				}
				//res.sendRedirect("Update.jsp");
			}
		}
		else
		{
			synchronized(this)
			{
				try {
					AffectedDao.updateRecovered(contactnumber,affecteddate);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				out.println(js1+"alert('Result is negative');"+l1+js2);
			}
		}
	}
}