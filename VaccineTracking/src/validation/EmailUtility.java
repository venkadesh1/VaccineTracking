package validation;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.omg.CORBA.portable.ApplicationException;


public class EmailUtility 
{
	public static void sendMail(String message,String email_to) throws ApplicationException{
	     String sender_Email = "vaccinetrackerproject@gmail.com";
	     String sender_email_pass = "vaccinetracker";
	       try {
	    	   Properties props = new Properties();  
	    	    props.setProperty("mail.transport.protocol", "smtp");     
	    	    props.setProperty("mail.host", "smtp.gmail.com");  
	    	    props.put("mail.smtp.auth", "true");  
	    	    props.put("mail.smtp.port", "465");  
	    	    props.put("mail.debug", "true");  
	    	    props.put("mail.smtp.socketFactory.port", "465");  
	    	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	    	    props.put("mail.smtp.socketFactory.fallback", "false");
	           Session session = Session.getDefaultInstance(props,  
	        		    new javax.mail.Authenticator() {  
	        	      protected PasswordAuthentication getPasswordAuthentication() {  
	        	    return new PasswordAuthentication(sender_Email,sender_email_pass);  
	        	      }  
	        	    });
	    
	           session.setDebug(true);
	           Message msg = new MimeMessage(session);
	           InternetAddress addressFrom = new InternetAddress(sender_Email);
	           msg.setFrom(addressFrom);
	           String addressTo=email_to;
	           msg.setRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
	           msg.setContent(message,"text/HTML");
	           Transport.send(msg);
	       } catch (Exception ex) {
	           
	       } 
	   }
}
