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

import com.mysql.cj.Session;

import dao.UpdateDose2Dao;
import validation.CalculateDaysBetween;
import validation.GetDate1;
import validation.GetDate2;
import validation.GetLastAffected;
import validation.GetRole;
import validation.ValidateContactNumber;

@WebServlet("/updatedose2")
public class UpdateDose2 extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		String admincontactnumber=null;
		String contactnumber=req.getParameter("contactnumber");
		String date2=req.getParameter("dateofseconddose");
		String typeofvaccine=req.getParameter("typeofvaccine");

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
		String l2="location='UpdateDose2.jsp';";
		String l3="location='Index.jsp';";
		String js2="</script>";

		PrintWriter out=res.getWriter();

		//res.setHeader("Cache-Control","no-cache, no-store");

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
			try {
				if(ValidateContactNumber.isValid(contactnumber) && ValidateContactNumber.isDoNotExist(contactnumber))
				{
					out.println(js1+"alert('Contact number not registered....Please sign up');"+l2+js2);
				}
				else if(!ValidateContactNumber.isValid(contactnumber))
				{
					out.println(js1+"alert('Invalid contact number');"+l2+js2);
				}
				else
				{
					synchronized(this)
					{
						String lastaffecteddb=GetLastAffected.getLastAffected(contactnumber);
						String date1db=GetDate1.getDateOfFirstDose(contactnumber);
						String date2db=GetDate2.getDateOfSecondDose(contactnumber);
						String typeofvaccinedb=GetDate1.getTypeOfVaccine(contactnumber);
						if(date1db==null)
						{
							out.println(js1+"alert('Please update first dose before second dose');"+l1+js2);
						}
						else if(!(date2db==null))
						{
							out.println(js1+"alert('Second dose already updated');"+l1+js2);
						}
						else if(!(typeofvaccine.equals(typeofvaccinedb)))
						{
							out.println(js1+"alert('Type of vaccine does not match');"+l2+js2);
						}
						else if(CalculateDaysBetween.getDays(date1db, date2)<0)
						{
							out.println(js1+"alert('Second dose cannot be before first dose');"+l1+js2);
						}
						else if(CalculateDaysBetween.getDays(date1db, date2)<90 && typeofvaccinedb.equals("covidshield"))
						{
							out.println(js1+"alert('You can have second dose only after:"+(90-CalculateDaysBetween.getDays(date1db, date2))+" days');"+l1+js2);
						}
						else if(CalculateDaysBetween.getDays(date1db, date2)<27 && typeofvaccinedb.equals("covaxin"))
						{
							out.println(js1+"alert('You can have second dose only after:"+(27-CalculateDaysBetween.getDays(date1db, date2))+" days');"+l1+js2);
						}
						else if(!(lastaffecteddb==null))
						{
							if(CalculateDaysBetween.getDays(lastaffecteddb, date2)<30)
							{
								out.println(js1+"alert('You can have second dose only after:"+(30-CalculateDaysBetween.getDays(lastaffecteddb, date2))+" days');"+l1+js2);
							}
							else
							{
								UpdateDose2Dao.update2(contactnumber, date2);
								out.println(js1+"alert('Date of dose 2 updated');"+l1+js2);
							}
						}
						else
						{
							UpdateDose2Dao.update2(contactnumber, date2);
							out.println(js1+"alert('Date of dose 2 updated');"+l1+js2);
						}
					}
				}
			}
			catch(Exception e)
			{
			}
		}
	}

}