package org.utmost.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static SimpleDateFormat getDateFormat(String str) {
		return new java.text.SimpleDateFormat(str);
	}

	public static String getNowDate() {
		return DateUtil.getDateFormat("yyyyMMdd HH:ss:mm").format(new Date());
	}
	public static String formatDate(Date date,String formateStr){
		SimpleDateFormat sdf = new SimpleDateFormat(formateStr);
		
		return sdf.format(date);
	}
}
