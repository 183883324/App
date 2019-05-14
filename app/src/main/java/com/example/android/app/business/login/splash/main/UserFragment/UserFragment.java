package com.example.android.app.business.login.splash.main.UserFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.R;
import com.example.android.app.business.login.splash.main.SetActivity;
import com.example.android.base.fragment.BaseFragment;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserFragment extends BaseFragment {


    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;
    @BindView(R.id.bt4)
    Button bt4;
    @BindView(R.id.bt5)
    Button bt5;
    @BindView(R.id.bt6)
    Button bt6;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ALLOW_TITLEBAR_SHOW = true;

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Log.e("Fragment", "UserFragment");
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                ActivityUtils.startActivity(RvNineImActivity.class);
                break;
            case R.id.bt2:
                break;
            case R.id.bt3:
                break;
            case R.id.bt4:
                break;
            case R.id.bt5:
                break;
            case R.id.bt6:
                break;
        }
    }
}
