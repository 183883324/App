package com.example.android.app.base_abcmp.contract;

import com.example.android.app.base_abcmp.bean.LoginBean;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;

import java.util.HashMap;

import io.reactivex.ObservableTransformer;

public interface LoginContract {
    interface View extends BaseView {

        void result(LoginBean o);
        void logoutResult(Object o);
        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        //请求1
        public abstract void login(HashMap<String, String> map, boolean isDialog, boolean cancelable);
        //请求2
        public abstract void logout(HashMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}
