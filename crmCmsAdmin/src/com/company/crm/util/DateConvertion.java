package com.company.crm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConvertion {
	
	public static String dateIncreementByOne(String dateStr) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Date date =   sdf.parse(dateStr);
		sdf = new SimpleDateFormat("yyyy-mm-dd");
		String str = sdf.format(date);	
		
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(str));
		c.add(Calendar.DAY_OF_MONTH, 1);  // number of days to add
			
		return sdf.format(c.getTime());
	}
	
public static String dateFormatConversion(String dateStr) throws ParseException{
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	Date date =   sdf.parse(dateStr);
	
	 sdf = new SimpleDateFormat("yyyy-mm-dd");
	
	 String str = sdf.format(date);
						
		return str;
	}


public static String dateFormatWithoutTime(String dateStr) throws ParseException{
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
	Date date =   sdf.parse(dateStr);
	
	 sdf = new SimpleDateFormat("dd/mm/yyyy");
	
	 String str = sdf.format(date);
						
		return str;
	}

}
