package validation;

public class ValidateUserName 
{
	public static boolean isOnlyAlpha(String str)
	{
	    return ((!str.equals(""))
	            && (str != null)
	            && (str.matches("^[a-zA-Z]*$")));
	}
}
