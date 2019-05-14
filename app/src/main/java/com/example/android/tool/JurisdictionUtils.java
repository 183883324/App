package com.example.android.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 必要权限
 */
public class JurisdictionUtils {

    /**
     * 申请权限
     */
    public static void isNetConnected(Context context) {
        RxPermissions rxPermission = new RxPermissions((FragmentActivity) context);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,//存取位置
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//写外部存储
                        Manifest.permission.CAMERA)//照相机
                //                        Manifest.permission.READ_CALENDAR,//读日历
                //                        Manifest.permission.READ_CALL_LOG,//读写调用日志
                //                        Manifest.permission.READ_CONTACTS,//读写联系人
                //                        Manifest.permission.READ_PHONE_STATE,//读音状态
                //                        Manifest.permission.READ_SMS,//读写短信
                //                        Manifest.permission.RECORD_AUDIO,//录音音频
                //                        Manifest.permission.CALL_PHONE,//来电电话
                //                        Manifest.permission.SEND_SMS)//发送短信
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            //    ToastUtils.showShort("用户已经同意该权限");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            //      ToastUtils.showShort("用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            //   ToastUtils.showShort("用户拒绝了该权限，并且选中『不再询问』框");
                        }
                    }
                });
    }

}
