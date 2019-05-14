package com.example.android.tool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.example.android.imview.LoadingDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周旭 on 2017/4/9.
 */

public class ImgUtils {
    private static LoadingDialog instance;
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            instance.dismiss();
            if (msg.what == 100) {
                Util.ToastSuccess("保存图片成功");
            }
            if (msg.what == 200) {
                Util.ToastError("保存图片失败，请稍后重试");

            }
        }
    };
    //更具url保存图片
    public static void returnBitMap(final String url,Context context){
        instance = LoadingDialog.getInstance(context);
        instance.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    boolean isSaveSuccess = ImgUtils.saveImageToGallery(com.example.android.tool.Util.getContext(), BitmapFactory.decodeStream(is));
                    if (isSaveSuccess) {
                        mHandler.sendEmptyMessage(100);
                    } else {
                        mHandler.sendEmptyMessage(200);
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +  AppUtils.getAppName();
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
