package com.example.android.tool;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**正则表达式
 * */
public class Check {

    private static String PHONE_PATTERN = "(13[0-9]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|14[5|7])[0-9]{8}$";

    /**
     * 验证是否是手机号
     */
    public static boolean isPhoneNumeric(String str) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    public static boolean isEmptyText(EditText text) {
        if (null == text) {
            return true;
        }
        if (null == text.getText() || "".equals(text.getText().toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 字符串不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s.trim());
    }

    /**
     * 字符串为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim());
    }

    /**
     * 正则表达式:验证用户名(不包含中文和特殊字符)如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式:验证密码(不包含特殊字符)
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式:验证手机号
     */
    public static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
    public static final String REGEX_MOBILE_PHONE = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";


    /**
     * 正则表达式:验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式:验证汉字(1-9个汉字)  {1,9} 自定义区间
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{1,9}$";

    /**
     * 正则表达式:验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    /**
     * 正则表达式:验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式:验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }


    /**
     * 校验手机号电话号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobileAndPhone(String mobile) {
        return Pattern.matches(REGEX_MOBILE_PHONE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddress
     * @return
     */
    public static boolean isIPAddress(String ipAddress) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddress);
    }


}
