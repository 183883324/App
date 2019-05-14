package com.example.android.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.view.View;
import com.tencent.smtt.sdk.WebView;


public class ScreenshotIM {
    /**
     * 获取整个窗口的截图
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static Bitmap captureScreen(Activity context) {
        View cv = context.getWindow().getDecorView();

        cv.setDrawingCacheEnabled(true);
        cv.buildDrawingCache();
        Bitmap bmp = cv.getDrawingCache();
        if (bmp == null) {
            return null;
        }

        bmp.setHasAlpha(false);
        bmp.prepareToDraw();
        return bmp;
    }
    /**
     * 对WebView进行截图
     *
     * @param webView
     * @return
     */
    public static Bitmap captureWebView1(WebView webView) {//可执行
        Picture snapShot = webView.capturePicture();
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),
                snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }

}
