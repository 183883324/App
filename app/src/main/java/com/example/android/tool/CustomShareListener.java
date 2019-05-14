package com.example.android.tool;

import android.app.Activity;

import com.example.android.app.business.login.LoginActivity;
import com.example.android.tool.Util;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.ref.WeakReference;

/**
 * 友盟回调
 */
public class CustomShareListener implements UMShareListener {
    private WeakReference<LoginActivity> mActivity;

    public CustomShareListener(Activity activity) {
        mActivity = new WeakReference(activity);
    }

    @Override
    public void onStart(SHARE_MEDIA platform) {
    }

    @Override
    public void onResult(SHARE_MEDIA platform) {
        if (platform.name().equals("WEIXIN_FAVORITE")) {
            Util.Toast("收藏成功啦");
        } else {
            if (platform != SHARE_MEDIA.MORE) {
                Util.Toast("分享成功啦");
            }
        }
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        if (platform != SHARE_MEDIA.MORE) {
            Util.Toast("分享失败啦");
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
        Util.Toast("分享取消了");
    }
}