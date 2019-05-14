package com.example.android.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class TimeDateUtils {

	/**
	 * 根据格式转换时间
	 * @param type
	 * @return
	 */
	public static String formatDateByType(String type, Date date){
		if(null == date){
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(date);
	}
	
	/**
	 * 比较当前时间是否超过指定时间
	 * @Description: TODO
	 * @param date
	 * @return
	 * @return boolean  true 大于等于  false 小于
	 */
	public static boolean littleTime(Date date){
		if(null == date){
			return false;
		}
		return new Date().getTime() >= date.getTime();
	}
	
	/**
	 * 转成成友好时间
	 * @Description: TODO
	 * @param pubTime
	 * @return
	 * @return String
	 */
	public static String getFriendlyDateTime(Date pubTime) {
		if (pubTime == null) {
			return "未知";
		}
		// 获取秒
		long result = (new Date().getTime() - pubTime.getTime()) / 1000;
		if (result < 60) {
			return "刚刚";
		} else if (result < 60 * 60) {
			return result / 60 + "分钟前";
		} else if (result < 60 * 60 * 24) {
			return (int) Math.floor(result / (60 * 60)) + "小时前";
		} else if (result < 60 * 60 * 24 * 3) {
			if (Math.floor(result / (60 * 60 * 24)) == 1) {
				return "昨天";
			} else {
				return "前天";
			}
		} else {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");// 格式化时间
			return simpleDateFormat.format(pubTime);
		}

	}

	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(long currentTime, String formatType)
			throws ParseException {
		Date date = longToDate(currentTime, formatType); // long类型转成Date类型
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType)
			throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}


	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime, String formatType)
			throws ParseException {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}




	/**
	 * 获取当前时间的前一天时间
	 * @param cl
	 * @return
	 */
	public static Calendar getBeforeDay(Calendar cl){
		//使用roll方法进行向前回滚
		//cl.roll(Calendar.DATE, -1);
		//使用set方法直接进行设置
		int day = cl.get(Calendar.DATE);
		cl.set(Calendar.DATE, day-1);
		return cl;
	}

	/**
	 * 获取当前时间的后一天时间
	 * @param cl
	 * @return
	 */
	public static Calendar getAfterDay(Calendar cl){
		//使用roll方法进行回滚到后一天的时间
		//cl.roll(Calendar.DATE, 1);
		//使用set方法直接设置时间值
		int day = cl.get(Calendar.DATE);
		cl.set(Calendar.DATE, day+1);
		return cl;
	}


	/**
	 * 获取当前时间的后n天时间
	 */
	public static Calendar getAfterDay(Calendar cl,int num){
		//使用roll方法进行回滚到后一天的时间
		//cl.roll(Calendar.DATE, 1);
		//使用set方法直接设置时间值
		int day = cl.get(Calendar.DATE);
		cl.set(Calendar.DATE, day+num);
		return cl;
	}

}
