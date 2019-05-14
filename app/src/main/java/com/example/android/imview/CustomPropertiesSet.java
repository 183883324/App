package com.example.android.imview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.R;

public class CustomPropertiesSet extends RelativeLayout {
    private ImageView setxml_im;
    private TextView setxml_tx;
    //标题
    private String title;
    //菜单图片资源
    private int menuSrc;

    public CustomPropertiesSet(Context context) {
        this(context, null);
    }

    public CustomPropertiesSet(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPropertiesSet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //绑定布局
        LayoutInflater.from(context).inflate(R.layout.custom_properties_set, this);
        setxml_im = (ImageView) findViewById(R.id.setxml_im);
        setxml_tx = (TextView) findViewById(R.id.setxml_tx);
        //获取属性
        //获取属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomPropertiesSet, defStyleAttr, 0);
        //获取标题属性文字
        title = typedArray.getString(R.styleable.CustomPropertiesSet_title);
        //获取菜单图片资源属性，未设置菜单图片资源则默认为-1，后面通过判断此值是否为-1决定是否设置图片
        menuSrc = typedArray.getResourceId(R.styleable.CustomPropertiesSet_menuSrc, -1);
        //TypedArray使用完后需手动回收
        typedArray.recycle();
        setxml_tx.setText(title);
        if (menuSrc != -1) {
            setxml_im.setImageResource(menuSrc);
        }
    }
    /**
     * 获取图片控件
     *
     */
    public ImageView setIm() {
       return setxml_im;
    }
    /**
     * 获取文字控件
     *
     */
    public TextView setIv() {
        return setxml_tx;
    }

}
