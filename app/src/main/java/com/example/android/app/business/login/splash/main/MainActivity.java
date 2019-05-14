package com.example.android.app.business.login.splash.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.blankj.utilcode.util.ToastUtils;
import com.example.R;
import com.example.android.app.business.login.LoginActivity;
import com.example.android.base.activity.BaseActivity;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.app.business.login.splash.main.MessageFragment.MessageFragment;
import com.example.android.app.business.login.splash.main.NewsFragment.NewsFragment;
import com.example.android.app.business.login.splash.main.ScheduleFragment.ScheduleFragment;
import com.example.android.app.business.login.splash.main.UserFragment.UserFragment;
import com.example.android.app.business.login.splash.main.WorkFragment.WorkFragment;
import com.example.android.tool.JurisdictionUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private long exitTime = 0;
    @BindView(R.id.bottomBar_home)
    BottomBar bottomBarHome;
    private List<Fragment> mFragmentList;
    private MessageFragment mMessageFragment;
    private WorkFragment mWorkFragment;
    private ScheduleFragment mScheduleFragment;
    private NewsFragment mNewsFragment;
    private UserFragment mUserFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**是否需要标题栏*/
        ALLOW_TITLEBAR_SHOW = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

        bottomBarHome.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_1:
                        showFragment(mMessageFragment);
                        break;
                    case R.id.tab_2:
                        showFragment(mScheduleFragment);
                        break;
                    case R.id.tab_3:
                        showFragment(mWorkFragment);
                        break;
                    case R.id.tab_4:
                        showFragment(mNewsFragment);
                        break;
                    case R.id.tab_5:
                        showFragment(mUserFragment);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

        /**初始化Fragmentt*/
        initFragments();
        /**默认显示中间一个*/
        bottomBarHome.setDefaultTab(R.id.tab_3);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }


    /**
     * description:  初始化fragment控件
     */
    private void initFragments() {
        mFragmentList = new ArrayList<>();
        mMessageFragment = new MessageFragment();
        mScheduleFragment = new ScheduleFragment();
        mWorkFragment = new WorkFragment();
        mNewsFragment = new NewsFragment();
        mUserFragment = new UserFragment();
        mFragmentList.add(mMessageFragment);
        mFragmentList.add(mScheduleFragment);
        mFragmentList.add(mWorkFragment);
        mFragmentList.add(mNewsFragment);
        mFragmentList.add(mUserFragment);
    }

    /**
     * description: 动态添加fragment,降低首界面加载时间
     */
    private void showFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        if (fragment.isVisible()) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment f : mFragmentList) {
            if (f.isAdded() && f.isVisible()) {
                fragmentTransaction.hide(f);
            }
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_home_container, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }
    /*
     * 重写onKeyDown方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //2s之内按返回键就会推出
            if((System.currentTimeMillis() - exitTime) > 2000){
                ToastUtils.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
