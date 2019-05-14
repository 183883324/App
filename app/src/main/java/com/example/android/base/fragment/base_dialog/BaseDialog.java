package com.example.android.base.fragment.base_dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.R;


/**
 * Dialog通用样式
 */
public abstract class BaseDialog extends DialogFragment {

    @LayoutRes
    protected int mLayoutResId;
    private boolean PlayingKeyboard = false;//是否弹起键盘
    private float mDimAmount = 0.5f;//背景昏暗度
    private boolean mShowBottomEnable;//是否底部显示
    private int mMargin = 0;//左右边距
    private int mAnimStyle = 0;//进入退出动画
    private boolean mOutCancel = true;//点击外部取消
    private Context mContext;
    private int mWidth;
    private int mHeight;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PlayingKeyboard){
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog);
        }else {
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialogKeyboard);
        }

        mLayoutResId = setUpLayoutId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(mLayoutResId, container, false);
        convertView(ViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = mDimAmount;

            //设置dialog显示位置
            if (mShowBottomEnable) {
                params.gravity = Gravity.BOTTOM;
            }

            //设置dialog宽度
            if (mWidth == 0) {
                params.width = getScreenWidth(getContext()) - 2 * dp2px(getContext(), mMargin);
            } else {
                params.width = dp2px(getContext(), mWidth);
            }
            //设置dialog高度
            if (mHeight == 0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = dp2px(getContext(), mHeight);
            }
            //设置dialog动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle);
            }
            //TODO 自定义BaseDialog显示的位置
            /**
             * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以params.x就表示相对左边的偏移
            当参数值包含Gravity.RIGHT时,对话框出现在右边,所以params.x就表示相对右边的偏移
            当参数值包含Gravity.TOP时,对话框出现在上边,所以params.y就表示相对上边的偏移
            当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以params.y就表示相对下边的偏移
            注意 负值无效
             */

            /*params.gravity = Gravity.TOP;  显示从顶部开始（状态栏下面）
            params.x = 10;  //想右边偏移10px，
            params.y = 100;*/
            window.setAttributes(params);
        }
        setCancelable(mOutCancel);
    }

    /**
     * 设置背景昏暗度
     *
     * @param dimAmount
     * @return
     */
    public BaseDialog setDimAmout(@FloatRange(from = 0, to = 1) float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    /**
     * 是否显示底部
     *
     * @param showBottom
     * @return
     */
    public BaseDialog setShowBottom(boolean showBottom) {
        mShowBottomEnable = showBottom;
        return this;
    }
    /**
     * 是否弹起键盘
     *
     * @param showBottom
     * @return
     */
    public BaseDialog SetPlayingKeyboard(boolean showBottom) {
        PlayingKeyboard = showBottom;
        return this;
    }
    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    public BaseDialog setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    /**
     * 设置左右margin
     *
     * @param margin
     * @return
     */
    public BaseDialog setMargin(int margin) {
        mMargin = margin;
        return this;
    }

    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * @return
     */
    public BaseDialog setAnimStyle(@StyleRes int animStyle) {
        mAnimStyle = animStyle;
        return this;
    }

    /**
     * 设置是否点击外部取消
     *
     * @param outCancel
     * @return
     */
    public BaseDialog setOutCancel(boolean outCancel) {
        mOutCancel = outCancel;
        return this;
    }

    public BaseDialog show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }

    /**
     * 设置dialog布局
     *
     * @return
     */
    public abstract int setUpLayoutId();

    /**
     * 操作dialog布局
     *
     * @param holder
     * @param dialog
     */
    public abstract void convertView(ViewHolder holder, BaseDialog dialog);

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
