package com.example.android.tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.R;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarUtils {
    /**
     * 状态栏字体颜色为亮色（默认就是亮色）    字体为白色
     */
    public static void InitBar(Activity activity) {
        ImmersionBar.with(activity)
                .statusBarColor(R.color.white)     //状态栏颜色，不写默认透明色
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .flymeOSStatusBarFontColor(R.color.black)  //修改flyme OS状态栏字体颜色
                .fitsSystemWindows(true)
                .init();  //必须调用方可沉浸式
    }

    /**
     * 状态栏字体颜色为亮色（默认就是亮色）    字体为白色
     */
    public static void TbrightBar(Activity activity) {
        ImmersionBar.with(activity).statusBarDarkFont(false).init();
    }
    /**
     * 状态栏字体颜色为深色(android6.0以上或者miuiv6以上或者flymeOS4+)    字体为黑色
     */
    public static void DarkColorBar(Activity activity) {
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(activity).statusBarDarkFont(true).init();
        } else {
            ToastComponent.success(activity,"当前设备不支持状态栏字体变色");
        }
    }

    /**
     * 隐藏导航栏
     */
    public static void HiddenNavigationBar(Activity activity) {
        if (ImmersionBar.hasNavigationBar(activity)) {
            ImmersionBar.with(activity).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        } else {
            ToastComponent.success(activity, "当前设备没有导航栏或者低于4.4系统");
        }
    }
    /**
     * 隐藏状态栏
     */
    public static void HiddenStatusBar(Activity activity) {
        ImmersionBar.with(activity).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
    }
 /**
     * 状态栏全变白了，解决状态栏白色问题
     */
    public static void WhiteStatusBar(Activity activity) {
        ImmersionBar.with(activity)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarDarkIcon(true, 0.2f)
                .init();
    }

}
