package com.example.android.app.business.login.splash.main.MessageFragment.TabLayoutFragment.twofragment;

import android.util.Log;

import com.example.R;
import com.example.android.base.fragment.BaseFragment;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.tool.Util;

public class TwoFragmeent extends BaseFragment{


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_twofragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Log.e("BaseFragment", "TwoFragmeent");


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
