package org.utmost.util;

import java.text.SimpleDateFormat;

public class DateUtil {
	public static SimpleDateFormat getDateFormat(String str) {
		return new java.text.SimpleDateFormat(str);
	}
}
