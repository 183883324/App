package com.example.android.base.fragment.dialog_fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.R;
import com.example.android.base.event.BaseEvent;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseDialogFragment<V extends BaseView, P extends BasePresenter<V>> extends BottomSheetDialogFragment {
    //引用V层和P层
    private P presenter;
    private V view;
    private Unbinder unbinder;

    public P getPresenter() {
        return presenter;
    }

    /**
     * 是否注册消息线程
     */
    protected boolean SendMessage = false;
    /**
     * 状态栏是透明的还是白底黑字
     */
    protected boolean StatusBar = false;


    /**
     * 顶部向下偏移量
     */
    private int topOffset = 0;
    private View Dialogview;
    private BottomSheetBehavior<FrameLayout> behavior;

    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback
            = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //设置为收缩状态
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.TransBottomSheetDialogStyle);//去除背景阴影
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Dialogview = inflater.inflate(R.layout.base_bottom_dialog, null);
       setChildRootView(getLayoutId());
        registerMSGBus();

        return Dialogview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       ((View) Dialogview.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//设置背景透明
        if (!StatusBar) {
            //状态栏白底黑字
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            }
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        /*// 设置软键盘不自动弹出
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);*/
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getHeight();
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mBottomSheetBehavior = (BottomSheetBehavior) behavior;
            mBottomSheetBehavior.setPeekHeight(getHeight());//禁止折叠
        }

    }


    /**
     * 获取屏幕高度
     *
     * @return height
     */
    private int getHeight() {
        int height = 2000;
        if (getContext() != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (wm != null) {
                // 使用Point已经减去了状态栏高度
                wm.getDefaultDisplay().getSize(point);
                height = point.y - getTopOffset();
            }
        }
        return height;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;

    }

    /**
     * description:  在跟容器上加载布局
     */
    public void setChildRootView(int layoutId) {
        /*加载根容器布局*/
        FrameLayout baseContainer = Dialogview.findViewById(R.id.dialog_rootview);
        LayoutInflater.from(getActivity()).inflate(layoutId, baseContainer, true);
        unbinder = ButterKnife.bind(this, Dialogview);
        initView();
        initListener();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterMSGBus();
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
     * description:  注册消息总线
     */
    private void registerMSGBus() {
        if (SendMessage) {
            EventBus.getDefault().register(this);
        }

    }

    /**
     * description:  解绑消息总线
     */
    private void unRegisterMSGBus() {
        if (SendMessage) {
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
}

