package com.example.android.tool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


import com.example.R;

import es.dmoral.toasty.Toasty;

/**
 * @createtime: 2017/11/24
 * @author: dpliuying
 * description:  吐司工具
 */

public class ToastComponent {

    /**
     * description:  在主工程的Application重初始化
     */
    public static void initToast(Context context) {
        Toasty.Config.getInstance()
                .setErrorColor(ContextCompat.getColor(context, R.color.color_text_red_E10601))//错误
                .setInfoColor(ContextCompat.getColor(context, R.color.color_theme))// 设置信息颜色
                .setSuccessColor(ContextCompat.getColor(context, R.color.color_text_green_32CD32))//成功
                .setWarningColor(ContextCompat.getColor(context, R.color.color_text_orange_FFC125))//设置警告颜色
                .setTextColor(Color.WHITE)
                .setTextSize(12)
                .apply();
    }

    public static void success(Context context, String msg) {
        Toasty.success(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void success(Context context, String msg, int length, boolean withIcon) {
        Toasty.success(context, msg, length, withIcon).show();
    }

    public static void error(Context context, String msg) {
        Toasty.error(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void error(Context context, String msg, int length, boolean withIcon) {
        Toasty.error(context, msg, length, withIcon).show();
    }

    public static void info(Context context, String msg) {
        Toasty.info(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void info(Context context, String msg, int length, boolean withIcon) {
        Toasty.info(context, msg, length, withIcon).show();
    }

    public static void warning(Context context, String msg) {
        Toasty.warning(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void warning(Context context, String msg, int length, boolean withIcon) {
        Toasty.warning(context, msg, length, withIcon).show();
    }

    public static void normal(Context context, String msg) {
        Toasty.normal(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void normal(Context context, String msg, int length) {
        Toasty.normal(context, msg, length).show();
    }

    public static void normal(Context context, String msg, Drawable icon) {
        Toasty.normal(context, msg, Toast.LENGTH_SHORT, icon).show();
    }

    public static void normal(Context context, String msg, int length, Drawable icon) {
        Toasty.normal(context, msg, length, icon).show();
    }

    public static void custom(Context context, String msg, @ColorInt int textColor, int iconResouce) {
        Toasty.Config.getInstance()
                .setTextColor(textColor)
                .apply();
        Toasty.custom(context, msg, context.getResources().getDrawable(iconResouce),
                Color.BLACK, Toast.LENGTH_SHORT, true, true).show();
        Toasty.Config.reset(); // Use this if you want to use the configuration above only once
    }
}
