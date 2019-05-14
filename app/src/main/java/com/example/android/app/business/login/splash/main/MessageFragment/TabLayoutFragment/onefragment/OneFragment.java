package com.example.android.app.business.login.splash.main.MessageFragment.TabLayoutFragment.onefragment;

import android.util.Log;
import android.view.View;

import com.example.R;
import com.example.android.base.fragment.BaseFragment;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.imview.EmptyView;
import com.example.android.tool.Util;

/**
 * 预加载了全部页面的数据，但是每次显示oneFragment的时候都会从新去加载数据
 * 但是第一次add的时候OneFragment会加载两次initdata方法，暂时没找到解决的方法
 * */
public class OneFragment extends BaseFragment {

    //Fragment的View加载完毕的标记
    private boolean isLoading = false;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private EmptyView imjz;

    //setUserVisibleHint和lazyLoad两个方法是为了去除viewPager+fragment的懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            LoadData();//加载数据的方法
        } else {
            isUIVisible = false;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_onefragment;
    }

    @Override
    protected void initView() {
        imjz = (EmptyView) mBaseView.findViewById(R.id.imjz);
        imjz.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.Toast("sasasas");
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (!isLoading && isUIVisible) { isLoading = true;isUIVisible = false; }
    }
    /**每次需要重新去加载数据的方法写这里*/
    private void LoadData() {
        Log.e("BaseFragment", "OneFragment");
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
