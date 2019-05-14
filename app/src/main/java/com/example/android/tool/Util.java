package com.example.android.tool;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.R;
import com.example.android.app.BaseApp;
import com.example.android.app.business.login.splash.main.MainActivity;
import com.example.android.imview.PowerfulEditText;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

public class Util {
    private String totalCacheSize;

    /**
     * 获取全局app上下文
     */
    public static Context getContext() {
        return BaseApp.getContext();
    }

    //返回指定colorId对应的颜色值
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    /**
     * String字符串不为空
     */
    public static Boolean getTextNull(String Id) {
        return Id != null && Id.trim().length() != 0 && !Id.equals("null");


    }

    /**
     * 获取缓存
     */
    public static String GetCache() throws Exception {
        String totalCacheSize = CleanMessageUtil.getTotalCacheSize(Util.getContext());
        return totalCacheSize;
    }

    /**
     * 清除缓存
     */
    public static void ClearCache() {
        CleanMessageUtil.clearAllCache(Util.getContext());
    }

    /**
     * 吐司
     */
    public static void Toast(String string) {
        ToastUtils.showShort(string);
    }

    /**
     * 图片正确颜色吐司
     */
    public static void ToastSuccess(String string) {
        ToastComponent.success(Util.getContext(), string);
    }

    /**
     * 图片错误颜色吐司
     */
    public static void ToastError(String string) {
        ToastComponent.error(Util.getContext(), string);
    }

    /**
     * 信息颜色吐司
     */
    public static void ToastInfo(String string) {
        ToastComponent.info(Util.getContext(), string);
    }

    /**
     * 图片加载
     */
    public static void LoadIm(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.error_im);
        options.placeholder(R.drawable.seizeseat);
        Glide.with(Util.getContext()).load(url).apply(options).into(imageView);
    }

    /**
     * ditText 屏蔽选择、复制、粘贴等一切剪切板的操作
     *
     * @param EditText
     * @return
     */
    public static void EditShield(PowerfulEditText EditText) {
        EditTextUtils.EditTextUtils(EditText);
    }

    /**
     * 判断是否有  存取位置  写外部存储   照相机  权限
     */
    public static Boolean JudgementAuthority(Activity activity) {
        boolean Jurisdiction = PermissionUtil.checkPermission(activity);
        return Jurisdiction;
    }

    /**
     * 根据图片URL地址保存图片
     */
    public static void SaveIm(String url, Activity activity) {
        if (Util.JudgementAuthority(activity)) {
            ImgUtils.returnBitMap(url, activity);
        } else {
            JurisdictionUtils.isNetConnected(activity);
        }
    }

    /**
     * 获取整个窗口的截图
     *
     * @param activity
     * @return
     */
    public static Bitmap ScreenActivity(Activity activity) {
        Bitmap bitmap = ScreenshotIM.captureScreen(activity);
        return bitmap;
    }

    /**
     * 对WebView进行截图
     *
     * @param webView
     * @return
     */
    public static Bitmap ScreenWeb(WebView webView) {
        Bitmap bitmap = ScreenshotIM.captureWebView1(webView);
        return bitmap;
    }


    /**
     * 弹出软键盘时滚动视图
     *
     * @param root         最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    public static void SoftKeyboard(final View root, final View scrollToView) {
        KeyBoardUtils.autoScrollView(root, scrollToView);//弹出软键盘时滚动视图
    }


    /**
     * 分享图片和文字  1：代表文字   2：代表图片
     */
    public static void Share(String thumbnailUrl, Activity activity) {
        ShareAction shareAction;
        CustomShareListener customShareListener = new CustomShareListener(activity);
        //网络图片
        shareAction = new ShareAction(activity).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.MORE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media.equals(SHARE_MEDIA.WEIXIN) || share_media.equals(SHARE_MEDIA.WEIXIN_CIRCLE) || share_media.equals(SHARE_MEDIA.WEIXIN_FAVORITE)) {
                            if (!IfInstallApp.Install_WEIIXIN(activity)) {//判断是否安装了客户端
                                Util.ToastError("您还未安装微信客户端");
                                return;
                            }
                        }
                        if (share_media.equals(SHARE_MEDIA.SINA)) {
                            if (!IfInstallApp.Install_SINA(activity)) {
                                Util.ToastError("您还未安装微博客户端");
                                return;
                            }
                        }
                        if (share_media.equals(SHARE_MEDIA.QQ) || share_media.equals(SHARE_MEDIA.QZONE)) {
                            if (!IfInstallApp.Install_QQ(activity)) {
                                Util.ToastError("您还未安装QQ客户端");
                                return;
                            }
                        }

                        UMImage image = new UMImage(activity, thumbnailUrl);//网络图片
                        image.setThumb(image);
                        image.compressStyle = UMImage.CompressStyle.SCALE;
                        new ShareAction(activity)
                                .withText("来自" + AppUtils.getAppName() + "App")
                                .withMedia(image)
                                .setPlatform(share_media)
                                .setCallback(customShareListener)
                                .share();

                        //文字
                        //                        new ShareAction(activity).withText(thumbnailUrl)
                        //                                .setPlatform(share_media)
                        //                                .setCallback(customShareListener)
                        //                                .share();

                        //App分享的图标
                        //                        int appLogo = R.drawable.no_data;
                        //网页
                        //                        UMImage image = new UMImage(activity, appLogo); // TODO: 换成APP图标资源
                        //                        UMWeb web = new UMWeb(url);
                        //                        web.setTitle("This is music title");//标题
                        //                        web.setThumb(image);  //缩略图
                        //                        web.setDescription("my description");//描述
                        //                        new ShareAction(activity).withMedia(web)
                        //                                .setPlatform(share_media)
                        //                                .setCallback(customShareListener)
                        //                                .share();
                        //                   //音乐
                        //                        UMVideo video = new UMVideo(url);
                        //                        video.setTitle("This is music title");//视频的标题
                        //                        video.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");//视频的缩略图
                        //                        video.setDescription("my description");//视频的描述
                        //                        new ShareAction(activity).withText("hello")//文本
                        //                                .withMedia(video).share();
                        //
                        //                   //视频
                        //                        UMusic music = new UMusic(url);
                        //                        music.setTitle("This is music title");//音乐的标题
                        //                        music.setmTargetUrl(Defaultcontent.url);//QQ好友微信好友可以设置跳转链接
                        //                        music.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");//音乐的缩略图
                        //                        music.setDescription("my description");//音乐的描述
                        //                        new ShareAction(activity).withText("hello")
                        //                                .withMedia(music).share();

                    }

                });
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);//设置控制面板显示的位置
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);//设置菜单背景形状
        shareAction.open(config);
    }

}
