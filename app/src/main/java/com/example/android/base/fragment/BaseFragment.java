package com.example.android.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment <V extends BaseView, P extends BasePresenter<V>> extends RxFragment {
    //引用V层和P层
    private P presenter;
    private V view;
    protected Context mContext;
    private Unbinder unbinder;

    public P getPresenter() {
        return presenter;
    }
    /**
     * 是否注册消息线程
     */
    protected boolean SendMessage = false;

    /**
     * 是否需要标题栏
     */
    protected boolean ALLOW_TITLEBAR_SHOW = false;
    /**
     * 跟容器View
     */
    protected View mBaseView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {

        mBaseView = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_base, container, false);
        setChildRootView(getLayoutId());
        unbinder=ButterKnife.bind(this, mBaseView);
        initView();
        initListener();
        initData();
        registerMSGBus();

        return mBaseView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterMSGBus();
    }




    /**
     * description:  在跟容器上加载布局
     */
    public void setChildRootView(int layoutId) {
        if (ALLOW_TITLEBAR_SHOW) {
            mBaseView.findViewById(R.id.rl_titlebar).setVisibility(View.VISIBLE);
        } else {
            mBaseView.findViewById(R.id.rl_titlebar).setVisibility(View.GONE);
        }

        /*加载根容器布局*/
        FrameLayout baseContainer = mBaseView.findViewById(R.id.fl_rootview);
        LayoutInflater.from(getActivity()).inflate(layoutId, baseContainer, true);

    }


    /**
     * description:  设置自定义标题栏
     */
    public void setCustomTitleBar(int layoutId) {
        LayoutInflater.from(mContext).inflate(layoutId, (ViewGroup) mBaseView.findViewById(R.id.fl_custom_title), true);
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
     * description:  设置标题栏文字
     */
    protected void setTitleBarText(String title) {
        TextView tv = mBaseView.findViewById(R.id.tv_titlebar_title);
        tv.setText(title);
    }

    /**
     * description:  为右侧扩展按钮设置自定义监听器
     */
    protected void onExtendButtonClick(View.OnClickListener onClickListener) {
        mBaseView.findViewById(R.id.rl_titlebar_right).setOnClickListener(onClickListener);
    }

    /**
     * description:  为右侧扩展按钮设置图标
     */
    protected void setExtendButtonImage(int res) {
        ImageView imageView = mBaseView.findViewById(R.id.iv_titlebar_right);
        imageView.setImageResource(res);
    }

    /**
     * description:  为右侧扩展按钮设置文字
     */
    protected void setExtendButtonText(String text) {
        TextView textView = mBaseView.findViewById(R.id.tv_titlebar_right);
        textView.setText(text);
    }

    /**
     * description:  为右侧扩展按钮设置是否可见
     */
    protected void setExtendButtonVisible(int visible) {
        mBaseView.findViewById(R.id.tv_titlebar_right).setVisibility(visible);
    }
    /**
     * description:  注册消息总线
     */
    private void registerMSGBus() {
        if (SendMessage){
            EventBus.getDefault().register(this);
        }
    }
    /**
     * description:  解绑消息总线
     */
    private void unRegisterMSGBus() {
        if (SendMessage){
            EventBus.getDefault().removeAllStickyEvents();
            EventBus.getDefault().unregister(this);
        }
    }
    /**
     * description:  接收订阅消息,并在主线程中执行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveToMainThread(BaseEvent baseEvent) {
    }
    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        //for new api versions.
        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    /**
     * EditText隐藏焦点并显示软键盘
     */
    public void hideSoftInputFromWindow() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }
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
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
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
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
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

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }

    }
}
