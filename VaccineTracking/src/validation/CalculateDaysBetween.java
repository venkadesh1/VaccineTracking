package validation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculateDaysBetween 
{
	public static long getDays(String d1,String d2)
	{
		LocalDate dateBefore = LocalDate.parse(d1);
		LocalDate dateAfter = LocalDate.parse(d2);
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		return noOfDaysBetween;
	}
	public static String getDate(String d1,int days)
	{
		LocalDate date = LocalDate.parse(d1);
		String requireddate =date.plusDays(days).toString();
		return requireddate;
	}
}