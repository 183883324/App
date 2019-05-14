package com.example.android.base.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;
import com.example.android.base.event.BaseEvent;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.tool.StatusBarUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends RxAppCompatActivity {
    /**
     * 运行一次
     */
    private boolean runInOnResumeOnlyOnce;
    //引用V层和P层
    private P presenter;
    private V view;

    public P getPresenter() {
        return presenter;
    }

    /**
     * 是否竖屏,横屏.
     */
    protected boolean ALLOW_SCREEN_ROATE = true;
    /**
     * 是否注册消息线程
     */
    protected boolean SendMessage = false;
    /**
     * 是否允许全屏
     */
    protected boolean ALLOW_SCREEN_FULL = false;

    /**
     * 是否需要标题栏
     */
    protected boolean ALLOW_TITLEBAR_SHOW = true;
    /**
     * 是否显示退出按钮
     */
    protected boolean ALLOW_GOBACK = true;
    /**
     * 是否可以使用沉浸式
     */
    protected boolean ISIMMERSIONBARENABLED = true;
    /**
     * android 4.4以上沉浸式状态栏和沉浸式导航栏管理，包括状态栏字体颜色，一句代码轻松实现，以及对bar的其他设置，
     */
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //加载配置
        setScreenConfig();
        //去掉titlebar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setRootView(getLayoutId());
        //是否使用沉侵式
        if (ISIMMERSIONBARENABLED) {
            initImmersionBar();
        }
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (view == null) {
            view = createView();
        }
        if (presenter != null && view != null) {
            presenter.attachView(view);
        }


    }

    /**
     * description:  在根容器上加载布局
     */
    public void setRootView(int layoutId) {
        /**
         * 是否需要标题栏
         */
        if (ALLOW_TITLEBAR_SHOW) {
            findViewById(R.id.rl_titlebar).setVisibility(View.VISIBLE);
            if (ALLOW_GOBACK) {
                addBackButtonClickListener();
            } else {
                findViewById(R.id.ibtn_titlebar_goback).setVisibility(View.GONE);
            }
        } else {
            findViewById(R.id.rl_titlebar).setVisibility(View.GONE);
        }

        /*加载根容器布局*/
        FrameLayout frameLayout = findViewById(R.id.fl_rootview);
        LayoutInflater.from(this).inflate(layoutId, frameLayout, true);
        unbinder = ButterKnife.bind(this);
        /** 初始化子布局*/
        initView();
        /**  初始化子布局监听器*/
        initListener();
        initData();
        if (SendMessage) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!runInOnResumeOnlyOnce) {
            runOnResumeOnlyOnce();
            runInOnResumeOnlyOnce = true;
        }
    }


    //初始化
    protected void initImmersionBar() {
        StatusBarUtils.InitBar(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
        unbinder.unbind();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
        if (SendMessage) {
            EventBus.getDefault().removeAllStickyEvents();
            EventBus.getDefault().unregister(this);
        }

    }

    /**
     * description:  设置自定义标题栏
     */
    public void setCustomTitleBar(int layoutId) {
        LayoutInflater.from(this).inflate(layoutId, (ViewGroup) findViewById(R.id.fl_custom_title), true);
    }

    /**
     * description:  为左侧返回键设置退出当前Activity监听器
     */
    protected void addBackButtonClickListener() {
        findViewById(R.id.ibtn_titlebar_goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * description:  为左侧返回键设置自定义监听器
     */
    protected void setGoBackClickListener(View.OnClickListener listener) {
        findViewById(R.id.ibtn_titlebar_goback).setOnClickListener(listener);
    }

    /**
     * description:  为右侧扩展按钮设置自定义监听器
     */
    protected void onExtendButtonClick(View.OnClickListener onClickListener) {
        findViewById(R.id.rl_titlebar_right).setOnClickListener(onClickListener);
    }

    /**
     * description:  为右侧扩展按钮设置图标
     */
    protected void setExtendButtonImage(int res) {
        ImageView imageView = findViewById(R.id.iv_titlebar_right);
        imageView.setImageResource(res);
    }

    /**
     * description:  为右侧扩展按钮设置文字
     */
    protected void setExtendButtonText(String text) {
        TextView textView = findViewById(R.id.tv_titlebar_right);
        textView.setText(text);
    }

    /**
     * description:  为右侧扩展按钮设置是否可见
     */
    protected void setExtendButtonVisible(int visible) {
        findViewById(R.id.tv_titlebar_right).setVisibility(visible);
    }

    /**
     * description:  设置标题栏文字
     */
    protected void setTitleBarText(String title) {
        TextView tv = findViewById(R.id.tv_titlebar_title);
        tv.setText(title);
    }


    /**
     * description:  继承此方法中的操作只会在OnResume中执行一次
     */
    protected void runOnResumeOnlyOnce() {
    }


    /**
     * description:  设置子布局ID
     */
    protected abstract int getLayoutId();

    /**
     * description:  初始化子布局
     */
    protected abstract void initView();

    /**
     * description:  初始化子布局监听器
     */
    protected abstract void initListener();

    /**
     * description:  初始化子布局界面数据
     */
    protected abstract void initData();

    /**
     * description:  显示fragment
     */
    protected void showFragment(int layoutID, Fragment fragment) {
        if (fragment == null) {
            return;
        }
        if (fragment.isVisible()) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(layoutID, fragment);
        }
        fragmentTransaction.commit();
    }

    /**
     * description:  隐藏fragment
     */
    protected void hideFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        if (fragment.isHidden()) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            return;
        }
        if (fragment.isAdded()) {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
    }

    public abstract P createPresenter();

    public abstract V createView();

    /**
     * description:  配置屏幕参数
     */
    private void setScreenConfig() {

        if (ALLOW_SCREEN_ROATE) {
            /**始终保持竖屏*/
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            /**始终保持横屏*/
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        /*全屏*/
        if (ALLOW_SCREEN_FULL) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * description:  注册消息总线
     */
    private void registerMSGBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * description:  解绑消息总线
     */
    private void unRegisterMSGBus() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    /**
     * description:  接收订阅消息,并在主线程中执行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveToMainThread(BaseEvent baseEvent) {

    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * EditText隐藏焦点并显示软键盘
     */
    public void hideSoftInputFromWindow() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 隐藏虚拟按键,并且全屏
     */
    protected void hideBottomUIMenu() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


}
