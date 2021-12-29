package validation;

import java.security.MessageDigest;

public class Encrypt 
{
	public static String getEncrypted(String password)
	{
		String encryptedpassword = null;  
        try   
        {   
            MessageDigest m = MessageDigest.getInstance("MD5");  
            m.update(password.getBytes());
            byte[] bytes = m.digest();  
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            } 
            encryptedpassword = s.toString();  
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }
        return encryptedpassword;
	}
	public static boolean encryptValidate(String password,String passworddb)
	{
		String encryptedpassword=Encrypt.getEncrypted(password);
		boolean result;
		if(encryptedpassword.equals(passworddb))
		{
			result=true;
		}
		else
		{
			result=false;
		}
		return result;
	}
}