package com.sega.check_lunchbox.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Get_Date
{
	public static String Get_date_now()
	{
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM", Locale.getDefault() );
		return format.format(now);
	}
}
