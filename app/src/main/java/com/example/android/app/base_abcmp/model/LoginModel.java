package com.example.android.app.base_abcmp.model;

import android.content.Context;

import com.example.android.app.api.Api;
import com.example.android.base.okhttp.BaseModel;
import com.example.android.base.okhttp.ObserverResponseListener;

import java.util.HashMap;

import io.reactivex.ObservableTransformer;

public class LoginModel <T> extends BaseModel {

    public void login(Context context, HashMap<String,String> map, boolean isDialog, boolean cancelable,
                      ObservableTransformer<T,T> transformer, ObserverResponseListener observerListener){


        subscribe(context, Api.getApiService().login(map), observerListener,transformer,isDialog,cancelable);
    }

    //// TODO: 2017/12/27 其他需要请求、数据库等等的操作

    public void logout(Context context, HashMap<String,String> map, boolean isDialog, boolean cancelable,
                       ObservableTransformer<T,T> transformer, ObserverResponseListener observerListener){

        subscribe(context, Api.getApiService().logout(map), observerListener,transformer,isDialog,cancelable);
    }

}
