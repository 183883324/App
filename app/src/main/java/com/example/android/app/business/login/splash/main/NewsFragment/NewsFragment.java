package com.example.android.app.business.login.splash.main.NewsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.android.base.fragment.BaseFragment;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;

public class NewsFragment extends BaseFragment {



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            //网络数据刷新

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ALLOW_TITLEBAR_SHOW = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_news;
    }

    @Override
    protected void initView() {



    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {


        Log.e("Fragment","NewsFragment");
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }


}
