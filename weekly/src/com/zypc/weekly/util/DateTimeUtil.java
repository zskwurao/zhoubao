package com.zypc.weekly.util;

import java.util.Date;

import org.joda.time.DateTime;

public class DateTimeUtil {

	
	public static Date getBeginThisWeek(){
		
		DateTime dateTime = new DateTime();
		
		// 当天时间清零
		int x = dateTime.getMillisOfDay();
		dateTime = dateTime.plusMillis(-x);
		
		// 天数回退
		int d = dateTime.getDayOfWeek();
		dateTime = dateTime.plusDays(-d);
		
		return dateTime.toDate();
	}
}
