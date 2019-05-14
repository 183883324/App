package com.example.android.app.business.login.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.R;
import com.example.android.app.business.login.LoginActivity;
import com.example.android.base.activity.BaseActivity;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {
;
    private boolean isClick = false;
    private int i ;
    private Disposable mdDisposable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**是否需要标题栏*/
        ALLOW_TITLEBAR_SHOW = false;
        /**是否使用沉侵式*/
        ISIMMERSIONBARENABLED=false;
        //全屏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splassh;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        RxPermissions rxPermission = new RxPermissions(SplashActivity.this);
        rxPermission.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,//存取位置
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写外部存储
                Manifest.permission.CAMERA)//照相机
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        i++;
                        if (i==3){
                            Looding();
                        }
                    }
                });
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isClick = true;

    }

    private void Looding() {
        mdDisposable = Flowable.intervalRange(0, 1, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        ActivityUtils.startActivity(LoginActivity.class);
                        ActivityUtils.finishActivity(SplashActivity.class);

                    }
                })
                .subscribe();
    }




}
