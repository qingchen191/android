package cn.smarthome.sap.util;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtils {

	/**
	 * 获取当前日期时间
	 * */
	public static String getCurrentDateTime() {
		Date currentDate = new Date();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(currentDate);

	}

	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date simpleString2Date(String time) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyMMddHHmmss");
		Date resultDate = null;
		try {
			resultDate = dateformat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	public static Date string2Date(String dateTime) {

		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date resultDate = null;
		try {
			resultDate = dateformat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	public static String date2String(Date date) {

		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformat.format(date);
	}

	/**
	 * 计算时间间隔， return: 间隔秒数
	 * */
	public static Long getInterval(String startDateStr, String endDateStr) {
		Long interval = 0L;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = sdf.parse(startDateStr);
			Date endDate = sdf.parse(endDateStr);
			interval = getInterval(startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return interval;
	}

	public static Long getInterval(Date startDate, Date endDate) {
		Long interval = 0L;
		interval = endDate.getTime() - startDate.getTime();
		interval = interval / 1000;
		return interval;
	}

}
