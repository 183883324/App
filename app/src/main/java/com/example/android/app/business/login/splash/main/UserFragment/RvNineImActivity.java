package com.example.android.app.business.login.splash.main.UserFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.R;
import com.example.android.base.activity.BaseActivity;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.demo.nine_palace.UrlData;
import com.example.android.imview.assninegridview.assImg_preview.AssImgPreviewActivity;
import com.example.android.imview.imnineimgridview.ImNineImGridview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvNineImActivity extends BaseActivity {
    @BindView(R.id.im_nine_im_gridview_activity)
    ImNineImGridview imNineImGridviewActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rv_nine_im;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        imNineImGridviewActivity.setImNineImGridviewData( UrlData.getImageLists(),RvNineImActivity.this);

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
