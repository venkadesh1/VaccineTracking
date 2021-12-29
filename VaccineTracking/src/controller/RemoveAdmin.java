package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RemoveAdminDao;
import validation.GetRole;

@WebServlet("/removeadmin")
public class RemoveAdmin extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{
		res.setContentType("text/html");
		String js1="<script type=\"text/javascript\">";
		String l1="location='SuperAdmin.jsp';";
		String js2="</script>";
		PrintWriter out=res.getWriter();





		String csrfCookie = null;
		String contactnumber=null;
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
		String adminrole=null;
		try {
			adminrole=GetRole.role(contactnumber);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if(contactnumber==null || !adminrole.equals("superadmin"))
		{
			res.sendRedirect("Index.jsp");
		}



		String csrfField = req.getParameter("csrfToken");

		if (csrfCookie == null || csrfField == null || !csrfCookie.equals(csrfField)) {
			try {
				res.sendError(401);
			} catch (IOException e) {
			}
			return;
		}


		String usercontactnumber=req.getParameter("contactnumber");
		String userrole=null;
		synchronized(this)
		{
			try {
				userrole=GetRole.role(usercontactnumber);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			if(userrole.equals("user"))
			{
				out.println(js1+"alert('"+usercontactnumber+" was already an user');"+l1+js2);
			}
			else if(userrole.equals("superadmin"))
			{
				out.println(js1+"alert('Super admin cannot be a user');"+l1+js2);
			}
			else
			{
				try {
					RemoveAdminDao.removeAdmin(usercontactnumber);
					out.println(js1+"alert('"+usercontactnumber+" made as user');"+l1+js2);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
