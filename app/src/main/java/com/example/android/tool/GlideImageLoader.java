package com.example.android.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.R;
import com.example.android.imview.assninegridview.AssNineGridView;

/**
 * 9宫格图片加载格式化
 */
public class GlideImageLoader implements AssNineGridView.ImageLoader{

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.error_im);
        Glide.with(context).load(url).apply(options).into(imageView);
    //   Glide.with(context).load(url).into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
