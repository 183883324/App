package com.example.android.app.api;

import com.example.android.app.base_abcmp.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

/**
 * api service
 */
public interface ApiService {

    @POST("Appapi/Category/Category/get_category_consult")
    Observable<Object> login(@QueryMap Map<String, String> map);

    @POST("Appapi/User/Login/login")
    Observable<LoginBean> logout(@QueryMap Map<String, String> map);


}
