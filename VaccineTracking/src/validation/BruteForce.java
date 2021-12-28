package validation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

public class BruteForce 
{
	public static final int MAX_COUNT=5;
	public static final Long WAITING_TIME=(long) (60*5);
	public static long currenttime=System.currentTimeMillis();
	public static ConcurrentHashMap<String,Integer> count=new ConcurrentHashMap<>();
	public static ConcurrentHashMap<String,LocalDateTime> time=new ConcurrentHashMap<>();
	public static boolean isTimeNotExceed(LocalDateTime lastloggedintime)
	{
		long seconds = ChronoUnit.SECONDS.between(lastloggedintime, LocalDateTime.now());
		if(seconds<WAITING_TIME)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static final int IPMAX_COUNT=5;
	public static final Long IPWAITING_TIME=(long) (5);
	public static ConcurrentHashMap<String,Integer> ipcount=new ConcurrentHashMap<>();
	public static ConcurrentHashMap<String,LocalDateTime> iptime=new ConcurrentHashMap<>();
	public static boolean ip_isTimeNotExceed(LocalDateTime iplastloggedintime)
	{
		long seconds = ChronoUnit.SECONDS.between(iplastloggedintime, LocalDateTime.now());
		if(seconds<IPWAITING_TIME)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static ConcurrentHashMap<String,LocalDateTime> ip_blocked=new ConcurrentHashMap<>();
	public static int reqcount=0;
	public static LocalDateTime now=null;
	public static void req()
	{
		if(reqcount==0)
		{
			now=LocalDateTime.now();
		}
	}
	public static synchronized LocalDateTime getDateTime()
	{
		req();
		return now;
	}
	public static synchronized int getReqCount()
	{
		return reqcount;
	}
	public static synchronized void putDateTime(LocalDateTime datetimenow)
	{
		now=datetimenow;
	}
	public static synchronized void putReqCount(int count)
	{
		reqcount=count;
	}
}
