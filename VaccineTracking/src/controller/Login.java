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
import javax.servlet.http.HttpSession;

import org.omg.CORBA.portable.ApplicationException;

import dao.Recovery;
import validation.BlockAccount;
import validation.BruteForce;
import validation.EmailUtility;
import dao.LoginDao;
import validation.Encrypt;
import validation.GetRole;
import validation.ValidateContactNumber;

@WebServlet("/login")
public class Login extends HttpServlet
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

		res.setContentType("text/html");
		String js1="<script type=\"text/javascript\">";
		String js2="location='Index.jsp';</script>";
		String l2="location='SecretCode.jsp';";
		String js3="</script>";
		PrintWriter out=res.getWriter();
		
		LocalDateTime now=BruteForce.getDateTime();
		if(ChronoUnit.SECONDS.between(now, LocalDateTime.now())>60)
		{
			BruteForce.putDateTime(LocalDateTime.now());
		}
		else if(ChronoUnit.SECONDS.between(now, LocalDateTime.now())<60)
		{
			int count=BruteForce.getReqCount()+1;
			BruteForce.putReqCount(count);
		}
		if(BruteForce.getReqCount()>200)
		{
			return;
		}
		
		
		
		
		String IPAddr = req.getRemoteAddr();
		if(BruteForce.ip_blocked.size()>0 && BruteForce.ip_blocked.contains(IPAddr))
		{
			LocalDateTime blockedfrom=BruteForce.ip_blocked.get(IPAddr);
			long timebetweenblockedandnow=ChronoUnit.SECONDS.between(blockedfrom, LocalDateTime.now());
			if(timebetweenblockedandnow<300)
			{
				out.println(js1+"alert('Too many attempts');"+js2);
				return;
			}
			else
			{
				BruteForce.ip_blocked.remove(IPAddr);
			}
		}

		if(BruteForce.ipcount.containsKey(IPAddr))
		{
			BruteForce.ipcount.compute(IPAddr, (k,v)->v=v+1);
		}
		else
		{
			BruteForce.ipcount.put(IPAddr, 1);
			BruteForce.iptime.put(IPAddr, LocalDateTime.now());
		}



		String contactnumber=req.getParameter("contactnumber");
		String password=req.getParameter("password");
		String captchaentered=req.getParameter("captcha");

		//HttpSession session=req.getSession();
		//session.setAttribute("contactnumber", contactnumber);

		//res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");

		HttpSession session=req.getSession(false);
		String captcha =session.getAttribute("captcha_security").toString();

		LocalDateTime iplastloggedintime=null;
		if(BruteForce.ipcount.size()>0)
		{
			if(BruteForce.ipcount.containsKey(IPAddr))
			{
				iplastloggedintime=BruteForce.iptime.get(IPAddr);
			}
			if(BruteForce.ipcount.containsKey(IPAddr) && BruteForce.ipcount.get(IPAddr)>=BruteForce.IPMAX_COUNT && !BruteForce.ip_isTimeNotExceed(iplastloggedintime))
			{
				BruteForce.ipcount.compute(IPAddr, (k,v)->v=1);
				BruteForce.iptime.compute(IPAddr, (k,v)->v=LocalDateTime.now());
			}
			else if(BruteForce.ipcount.containsKey(IPAddr) && BruteForce.ipcount.get(IPAddr)>=BruteForce.IPMAX_COUNT && BruteForce.ip_isTimeNotExceed(iplastloggedintime))
			{
				out.println(js1+"alert('Too many attempts');"+js2);
				return;
			}
		}






		/*LocalDateTime lastloggedintime=null;
		if(BruteForce.count.size()>0)
		{
			if(BruteForce.count.containsKey(contactnumber))
			{
				lastloggedintime=BruteForce.time.get(contactnumber);
			}
			if(BruteForce.count.containsKey(contactnumber) && BruteForce.count.get(contactnumber)>=BruteForce.MAX_COUNT && !BruteForce.isTimeNotExceed(lastloggedintime))
			{
				BruteForce.count.remove(contactnumber);
				BruteForce.time.remove(contactnumber);
			}
			else if(BruteForce.count.containsKey(contactnumber) && BruteForce.count.get(contactnumber)>=BruteForce.MAX_COUNT && BruteForce.isTimeNotExceed(lastloggedintime))
			{
				long seconds = ChronoUnit.SECONDS.between(lastloggedintime, LocalDateTime.now());
				int remainingseconds=(int) (300-seconds);
				out.println(js1+"alert('Please wait for "+remainingseconds+" seconds');"+js2);
				return;
			}
		}*/




		try {
			if(ValidateContactNumber.isValid(contactnumber) && ValidateContactNumber.isDoNotExist(contactnumber))
			{
				out.println(js1+"alert('Contact number not registered Please signup');"+js2);
			}
			else if(!ValidateContactNumber.isValid(contactnumber))
			{
				out.println(js1+"alert('Invalid contact number');"+js2);
			}
			else if(password.length()<6)
			{
				out.println(js1+"alert('Invalid password');"+js2);
			}
			else if(!captcha.equals(captchaentered))
			{
				out.println(js1+"alert('Invalid captcha');"+js2);
				return;
			}
			else
			{
				String passworddb=LoginDao.getPass(contactnumber);
				int blockcount=BlockAccount.getWrongPasswordCount(contactnumber);
				String emailid=Recovery.getEmailid(contactnumber);
				String secretcode=Recovery.getSecretCode(contactnumber);
				LocalDateTime secretcodetime=null;
				if(Recovery.getDateTime(contactnumber)!=null)
				{
					secretcodetime=LocalDateTime.parse(Recovery.getDateTime(contactnumber));
				}
				if(blockcount==5 && secretcode!=null)
				{
					long sec=ChronoUnit.SECONDS.between(secretcodetime, LocalDateTime.now());
					if(sec<180)
					{
						Cookie ck=new Cookie("contactnumber",contactnumber);  
						res.addCookie(ck);
						//req.getRequestDispatcher("SecretCode.jsp").forward(req, res);
						out.println(js1+"alert('Secret code already sent to your registered email id');"+l2+js3);
						return;
					}
					else if(sec>180)
					{
						Cookie ck=new Cookie("contactnumber",contactnumber);  
						res.addCookie(ck);
						secretcode=Recovery.getCode();
						String datetime=LocalDateTime.now().toString();
						String message="Hii "+emailid+ " Your secret code is: "+secretcode+" valid for 3 minutes";
						Recovery.setSecretCode(contactnumber, secretcode,datetime);
						EmailUtility.sendMail(message, emailid);
						out.println(js1+"alert('Secret code sent to your registered email id');"+l2+js3);
						return;
					}
				}
				else if(blockcount==5 && secretcode==null)
				{
					Cookie ck=new Cookie("contactnumber",contactnumber);  
					res.addCookie(ck);
					secretcode=Recovery.getCode();
					String datetime=LocalDateTime.now().toString();
					String message="Hii "+emailid+ " Your secret code is: "+secretcode+" valid for 3 minutes";
					Recovery.setSecretCode(contactnumber, secretcode,datetime);
					EmailUtility.sendMail(message, emailid);
					out.println(js1+"alert('Secret code sent to your registered email id');"+l2+js3);
					return;
				}
				else if(Encrypt.encryptValidate(password, passworddb))
				{
					Cookie ck=new Cookie("contactnumber",contactnumber);  
					res.addCookie(ck);
					BruteForce.count.remove(contactnumber);
					BruteForce.time.remove(contactnumber);
					String role=GetRole.role(contactnumber);
					if(role.equals("superadmin"))
					{
						res.sendRedirect("SuperAdmin.jsp");
					}
					else if(role.equals("admin"))
					{
						res.sendRedirect("Admin.jsp");
					}
					else
					{
						res.sendRedirect("User.jsp");
					}
					//res.sendRedirect("usersession");
				}
				else
				{

					if(BlockAccount.getWrongPasswordCount(contactnumber)==0)
					{
						BlockAccount.setDetails(contactnumber, 1);
					}
					else
					{
						int existingcount=BlockAccount.getWrongPasswordCount(contactnumber);
						BlockAccount.updateCount(contactnumber, existingcount+1);
					}


					/*if(BruteForce.count.containsKey(contactnumber))
					{
						BruteForce.count.compute(contactnumber, (k,v)->v=v+1);
						BruteForce.time.compute(contactnumber, (k,v)->v=LocalDateTime.now());
					}
					else
					{
						BruteForce.count.put(contactnumber, 1);
						BruteForce.time.put(contactnumber, LocalDateTime.now());
					}*/
					out.println(js1+"alert('Incorrect password');"+js2);
				}
			}
		}
		catch(Exception e)
		{
		}
	}

}