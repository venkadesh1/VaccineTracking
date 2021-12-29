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

import dao.AffectedDao;
import validation.GetRole;
import validation.ValidateContactNumber;

@WebServlet("/delete")
public class DeleteUser extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		String contactnumber=req.getParameter("contactnumber");
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
		String l2="location='Delete.jsp';";
		String l3="location='Index.jsp';";
		String js2="</script>";

		PrintWriter out=res.getWriter();

		if(admincontactnumber==null)
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
				synchronized(this)
				{
					if(ValidateContactNumber.isDoNotExist(contactnumber) || !(ValidateContactNumber.isValid(contactnumber)))
					{
						out.println(js1+"alert('Invalid contact number');"+l2+js2);
					}
					else
					{
						AffectedDao.delete1(contactnumber);
						AffectedDao.delete2(contactnumber);
						out.println(js1+"alert('"+contactnumber+" deleted');"+l1+js2);
					}
				}
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}