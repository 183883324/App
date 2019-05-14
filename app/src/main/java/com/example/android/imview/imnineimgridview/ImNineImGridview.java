package com.example.android.imview.imnineimgridview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.R;
import com.example.android.imview.assninegridview.assImg_preview.AssImgPreviewActivity;
import com.example.android.tool.GridItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImNineImGridview extends RelativeLayout {
    private List<String> data = new ArrayList<>();
    private RecyclerView imNineImGridviewRv;
    private ImNineImGridviewAdapter imNineImGridviewAdapter;

    public ImNineImGridview(Context context) {
        super(context);
    }

    public ImNineImGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.im_nine_im_gridview_rv, this, true);
        imNineImGridviewRv = (RecyclerView) findViewById(R.id.im_nine_im_gridview_rv);
        imNineImGridviewRv.setLayoutManager(new GridLayoutManager(context, 3));
        imNineImGridviewRv.addItemDecoration(new GridItemDecoration(5, 5, 5));
        imNineImGridviewRv.getItemAnimator().setChangeDuration(0);// 通过设置动画执行时间为0来解决闪烁问题
    }


    private class ImNineImGridviewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public ImNineImGridviewAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            com.sunfusheng.GlideImageView ImageGlide = (com.sunfusheng.GlideImageView) helper.getView(R.id.ImageGlide);
            com.sunfusheng.progress.CircleProgressView GlideProgress = (com.sunfusheng.progress.CircleProgressView) helper.getView(R.id.GlideProgress);
            ImageGlide.centerCrop().error(R.mipmap.image_load_err).diskCacheStrategy(DiskCacheStrategy.NONE).load(item, R.color.placeholder, (isComplete, percentage, bytesRead, totalBytes) -> {
                Log.d("Glide--->" + helper.getAdapterPosition(), isComplete+"");
                if (isComplete) {
                    GlideProgress.setVisibility(View.GONE);
                } else {
                    GlideProgress.setVisibility(View.VISIBLE);
                    GlideProgress.setProgress(percentage);
                }
            });

        }
    }

    /**
     * description:  给重试按钮添加点击事件
     *
     * @param data:数据
     */
    public void setImNineImGridviewData(List data, Context context) {
        this.data = data;
        imNineImGridviewAdapter = new ImNineImGridviewAdapter(R.layout.im_nine_im_gridview_rv_item, data);
        imNineImGridviewRv.setAdapter(imNineImGridviewAdapter);
        imNineImGridviewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, AssImgPreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("imageInfo", (Serializable) data);
                bundle.putInt("currentIndex", position);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        imNineImGridviewAdapter.notifyDataSetChanged();
    }

}
