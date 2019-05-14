package com.example.android.app.business.login.splash.main.MessageFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.android.base.fragment.BaseFragment;
import com.example.android.base.okhttp.BaseView;
import com.example.android.app.base_abcmp.contract.LoginContract;
import com.example.android.app.base_abcmp.presenter.LoginPresenter;
import com.example.android.app.business.login.splash.main.MessageFragment.TabLayoutFragment.onefragment.OneFragment;
import com.example.android.app.business.login.splash.main.MessageFragment.TabLayoutFragment.threefrragment.ThreeFragment;
import com.example.android.app.business.login.splash.main.MessageFragment.TabLayoutFragment.twofragment.TwoFragmeent;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class MessageFragment extends BaseFragment{


    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"热门", "iOS", "Android"};
    private CommonTabLayout SlidingTab;
    private ViewPager SlidingTabVP;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        } else {  // 在最前端显示 相当于调用了onResume();
            //网络数据刷新

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ALLOW_TITLEBAR_SHOW = false;

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        SlidingTab = (CommonTabLayout) mBaseView.findViewById(R.id.SlidingTab);
        SlidingTabVP = (ViewPager) mBaseView.findViewById(R.id.SlidingTabVP);
        mTabEntities.add(new TabEntity("热门", 0, 0));//后面两个值是选中图标和未选中(R.drawable.xxx)不要图标就填0
        mFragments.add(new OneFragment());
        mTabEntities.add(new TabEntity("iOS", 0, 0));//后面两个值是选中图标和未选中(R.drawable.xxx)不要图标就填0
        mFragments.add(new TwoFragmeent());
        mTabEntities.add(new TabEntity("Android", 0, 0));//后面两个值是选中图标和未选中(R.drawable.xxx)不要图标就填0
        mFragments.add(new ThreeFragment());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Log.e("Fragment","MessageFragment");
        SlidingTab.setTabData(mTabEntities);
        SlidingTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SlidingTabVP.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        SlidingTabVP.setOffscreenPageLimit(2);
        SlidingTabVP.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mFragments, mTitles));
        SlidingTabVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                SlidingTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        SlidingTabVP.setCurrentItem(0);

    }


    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(mContext);
    }

    @Override
    public BaseView createView() {
        return null;
    }


}
