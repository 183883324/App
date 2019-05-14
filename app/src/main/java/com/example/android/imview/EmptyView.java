package com.example.android.imview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.R;


/**
 * description:  带有"网络加载失败,请重试!"的EmptyView
 *
 * @author LiuYing
 * create at 2018/5/9 14:31
 */
public class EmptyView extends RelativeLayout {

    private com.allen.library.SuperButton mTvRetry;

    public EmptyView(Context context) {
        super(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ll_customview_emptyview, this, true);
        mTvRetry = (com.allen.library.SuperButton)findViewById(R.id.tv_retry);
    }

    /**
     * description:  给重试按钮添加点击事件
     *
     * @param listener:点击事件
     */
    public void setOnRetryClickListener(OnClickListener listener) {
        mTvRetry.setOnClickListener(listener);
    }
}