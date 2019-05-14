package com.example.android.tool;

import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.regex.Pattern;


/**
 * 字符串处理共同方法
 * 
 *
 */
public class TextUtils {
	/**
	 * 判断输入的字符串是否符合Email样式.
	 * 
	 */
	public static boolean isEmail(String str) {
		Pattern pattern = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 不为空白,包括null和""
	 */
	public static boolean isBlank(String str) {
		return str != null && str.trim().length() != 0 ;
	}

	/**
	 * 转换url
	 * 
	 */
	public static String getURLEncoderStr(String strValue) {
		if (isBlank(strValue)) {
			return "";
		}
		String categoryvalue;
		try {
			categoryvalue = URLEncoder.encode(strValue, null);
		} catch (Exception e1) {
			categoryvalue = "";
		}
//		if(Constants.LOGPRINTLN){
//			return strValue;
//		}
		return categoryvalue;
	}

	/**
	 * 将String数组元素用逗号组装成String
	 * 
	 */
	public static String CombinationComma(String[] str) {
		StringBuffer s;
		if (str != null && str.length != 0) {
			s = new StringBuffer();
			for (String aStr : str) {
				s.append(aStr);
				s.append(",");
			}
			return s.toString();

		} else {
			return "";
		}
	}

	/**
	 * 将月份或者时间前面加0返回
	 * 
	 */
	public static String monthChange(int month) {
		StringBuilder str = new StringBuilder();
		if (month < 10) {
			str.append("0");
			str.append(month);
		} else {
			str.append(month);
		}
		return str.toString();
	}
	
	/**
	 * 根据逗号分割字符串
	 * @return String[]
	 *//*
	public static String[] cutStringByComma(String url){
		if(Check.isEmpty(url)){
			return new String[]{Constant.DEFAULT_ZIMG_ID};
		}
		return url.split(",");
	}*/
	
	/**
	 * 钱数格式化
	 */
	public static String fromatMoneyString(double money, boolean isShowUnit){
		NumberFormat df = NumberFormat.getNumberInstance();
		df.setMaximumFractionDigits(2);
		if(isShowUnit){
			return "￥" + df.format(money) + "元";
		}
		return  df.format(money);
	}
	
	/**
	 * 钱数格式化
	 */
	public static String fromatMoney￥(double money){
		NumberFormat df = NumberFormat.getNumberInstance();
		df.setMaximumFractionDigits(2);
		return "￥" + df.format(money);
	}
}
