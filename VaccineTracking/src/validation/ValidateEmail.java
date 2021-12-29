package validation;

import java.util.regex.*;    
import java.util.*;

public class ValidateEmail 
{
	public static boolean isValid(String emailid)
	{
	 String regex = "^(.+)@(.+)$";
	 Pattern pattern = Pattern.compile(regex);  
	 Matcher matcher = pattern.matcher(emailid);
	 if(matcher.matches())
	 {
		 return true;
	 }
	 else
	 {
		 return false;
	 }
	}
}
