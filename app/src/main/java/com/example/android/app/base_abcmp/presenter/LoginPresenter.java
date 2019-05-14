package com.example.android.app.base_abcmp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.example.android.app.base_abcmp.bean.LoginBean;
import com.example.android.app.base_abcmp.contract.LoginContract;
import com.example.android.app.base_abcmp.model.LoginModel;
import com.example.android.base.okhttp.ObserverResponseListener;
import com.example.android.tool.ExceptionHandle;
import com.example.android.tool.JSONUtils;

import java.util.HashMap;

public class LoginPresenter extends LoginContract.Presenter {

    private LoginModel model;
    private Context context;

    public LoginPresenter(Context context) {
        this.model = new LoginModel();
        this.context = context;
    }

    @Override
    public void login(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.login(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    //判断是否成功status是否返回成功状态
                        LoginBean loginBean = JSONUtils.parseJSON(String.valueOf(o), LoginBean.class);
                        getView().result(loginBean);

                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    //// TODO: 2017/12/28 自定义处理异常
                    getView().logoutResult(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }


    @Override
    public void logout(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.logout(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().result((LoginBean) o);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    ToastUtils.showShort(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }

}