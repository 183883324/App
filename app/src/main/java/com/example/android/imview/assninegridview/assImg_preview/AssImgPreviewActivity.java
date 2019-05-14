package com.example.android.imview.assninegridview.assImg_preview;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.R;
import com.example.android.app.api.ApiService;
import com.example.android.app.api.Cofing;
import com.example.android.imview.assninegridview.AssNineGridView;
import com.example.android.imview.assninegridview.ImageInfo;
import com.example.android.tool.CustomShareListener;
import com.example.android.tool.ImgUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.tls.OkHostnameVerifier;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author assionhonty
 * Created on 2018/9/19 9:41.
 * Email：assionhonty@126.com
 * Function:图片预览页面
 */
public class AssImgPreviewActivity extends AppCompatActivity {

    private List<String> imageInfos;
    private int currentIndex;
    private int Index;

    private TextView tvNum;
    private ImageView AssP_More;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ass_img_preview_activity);


        Intent intent = getIntent();
        imageInfos = (List<String>) intent.getSerializableExtra("imageInfo");
        currentIndex = intent.getIntExtra("currentIndex", 0);
        Index = currentIndex;
        AssViewPager vp = findViewById(R.id.vp);
        tvNum = findViewById(R.id.tv_num);
        AssP_More = findViewById(R.id.AssP_More);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(this);
        vp.setAdapter(myPagerAdapter);
        vp.setOffscreenPageLimit(4);
        vp.setCurrentItem(currentIndex);
        tvNum.setText((currentIndex + 1) + " / " + imageInfos.size());

        vp.addOnPageChangeListener(new AssViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Index = position;
                tvNum.setText((position + 1) + " / " + imageInfos.size());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        AssP_More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertView("图片操作", null, "取消", null,
                        new String[]{"保存图片", "分享图片"},
                        AssImgPreviewActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        String thumbnailUrl = imageInfos.get(Index);
                        if (position == 0) {
                            com.example.android.tool.Util.SaveIm(thumbnailUrl,AssImgPreviewActivity.this);
                        }
                        if (position == 1) {
                            //com.example.android.tool.Util.ShareIM(thumbnailUrl,AssImgPreviewActivity.this);
                            com.example.android.tool.Util.Share(thumbnailUrl,AssImgPreviewActivity.this);
                        }
                    }
                }).show();
            }
        });
    }

    private class MyPagerAdapter extends PagerAdapter implements PhotoViewAttacher.OnPhotoTapListener {
        private Context mContext;

        MyPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return imageInfos.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.ass_img_preview_item, null);
            final PhotoView imageView = view.findViewById(R.id.pv);
            final com.sunfusheng.GlideImageView ImagePreView = view.findViewById(R.id.ImagePreView);
            final com.sunfusheng.progress.CircleProgressView GlidePreView = view.findViewById(R.id.GlidePreView);
            String info = imageInfos.get(position);
            imageView.setOnPhotoTapListener(this);
            showExcessPic(info, imageView);
         //   AssNineGridView.getImageLoader().onDisplayImage(view.getContext(), imageView, info);
            ImagePreView.centerCrop().error(R.mipmap.image_load_err).diskCacheStrategy(DiskCacheStrategy.NONE).load(info, R.color.placeholder, (isComplete, percentage, bytesRead, totalBytes) -> {
                //            Log.d("--->", "load percentage: " + percentage + " totalBytes: " + totalBytes + " bytesRead: " + bytesRead);
                if (isComplete) {
                    GlidePreView.setVisibility(View.GONE);
                    ImagePreView.setVisibility(View.GONE);
                    AssNineGridView.getImageLoader().onDisplayImage(view.getContext(), imageView, info);
                } else {
                    GlidePreView.setVisibility(View.VISIBLE);
                    GlidePreView.setProgress(percentage);
                }
            });
            //        pb.setVisibility(View.VISIBLE);
            //        Glide.with(context).load(info.bigImageUrl)//
            //                .placeholder(R.drawable.ic_default_image)//
            //                .error(R.drawable.ic_default_image)//
            //                .diskCacheStrategy(DiskCacheStrategy.ALL)//
            //                .listener(new RequestListener<String, GlideDrawable>() {
            //                    @Override
            //                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //                        pb.setVisibility(View.GONE);
            //                        return false;
            //                    }
            //
            //                    @Override
            //                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            //                        pb.setVisibility(View.GONE);
            //                        return false;
            //                    }
            //                }).into(imageView);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            // super.destroyItem(container,position,object); 这一句要删除，否则报错
            container.removeView((View) object);
        }

        @Override
        public void onPhotoTap(View view, float x, float y) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    /**
     * 展示过度图片
     */
    private void showExcessPic(String imageInfo, PhotoView imageView) {
        //先获取大图的缓存图片
        Bitmap cacheImage = AssNineGridView.getImageLoader().getCacheImage(imageInfo);
        //如果大图的缓存不存在,在获取小图的缓存
        if (cacheImage == null) {
            cacheImage = AssNineGridView.getImageLoader().getCacheImage(imageInfo);
        }
        //如果没有任何缓存,使用默认图片,否者使用缓存
        if (cacheImage == null) {
            imageView.setImageResource(R.drawable.ic_default_color);
        } else {
            imageView.setImageBitmap(cacheImage);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }


}
