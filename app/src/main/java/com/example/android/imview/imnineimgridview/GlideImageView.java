package com.example.android.imview.imnineimgridview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class GlideImageView extends com.sunfusheng.GlideImageView {

    public GlideImageView(Context context) {
        super(context);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desireWidth;
        float aspect;

        Drawable drawable = getDrawable();
        if (drawable == null) {
            desireWidth = 0;
            aspect = 1;
        } else {
            desireWidth = drawable.getIntrinsicWidth();
            aspect = drawable.getIntrinsicWidth() * 1.0f / drawable.getIntrinsicHeight();
        }

        //获取期望值,基于MeasureSpec
        int widthSize = View.resolveSize(desireWidth, widthMeasureSpec);
        int heightSize = (int) (widthSize / aspect);

        //检查heightSize不要太大
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY || mode == MeasureSpec.AT_MOST) {
            if (heightSize > size) {
                heightSize = size;
                widthSize = (int) (aspect * heightSize);
            }
        }

        setMeasuredDimension(widthSize, heightSize);
    }
}

