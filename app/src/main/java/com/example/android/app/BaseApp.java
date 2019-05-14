package com.example.android.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.example.android.base.dao.DaoMaster;
import com.example.android.base.dao.DaoSession;
import com.example.android.base.dao.util.GreenDaoUtils;
import com.example.android.base.dao.util.Helper;
import com.example.android.imview.assninegridview.AssNineGridView;
import com.example.android.tool.GlideImageLoader;
import com.example.android.tool.ToastComponent;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


public class BaseApp extends Application {
    //全局上下文对象
    private static Context mContext;
    private Helper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static BaseApp instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        /**友盟初始化*/
        InitUmeng();
        /**工具类初始化*/
        InitUtil();

    }

    //64k方法数限制原理与解决
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return instances;
    }

    /**
     * 工具初始化
     */
    private void InitUtil() {
        /**初始化开源工具类*/
        Utils.init(this);
        /**初始化Log工具，Log总管理开关*/
        LogUtils.getConfig().setLogSwitch(true);
        /**9宫格Glide初始化*/
        AssNineGridView.setImageLoader(new GlideImageLoader());
        /**初始化Toast组件*/
        ToastComponent.initToast(this);
        /**数据库初始化*/
        setDatabase();
        /**腾讯x5内核*/
        InitX5TenXun();
    }

    /**
     * 友盟初始化
     */
    private void InitUmeng() {
        //初始化友盟SDK
        UMShareAPI.get(this);
        //开启debug模式，方便定位错误，具体错误检查方式可以查看
        Config.DEBUG = true;
        //微信
        PlatformConfig.setWeixin("wxdc1e388c1111c80b", "3baf1193c85774b3fd9d11117d76cab0");
        //新浪微博(第三个参数为回调地址)
        PlatformConfig.setSinaWeibo("3111100954", "04b48b094faeb16683c32111124ebdad",
                "http://sns.whalecloud.com/sina2/callback");
        // QQ
        PlatformConfig.setQQZone("1111114798", "aEIMjd6vWSADA5ol");
    }

    /**
     * 数据库初始化
     */
    private void setDatabase() {
        mHelper = new Helper(new GreenDaoUtils(this));
        db = mHelper.getWritableDatabase();
        if (null == mDaoMaster) {
            mDaoMaster = new DaoMaster(db);
        }
        if (null == mDaoSession) {
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public static BaseApp getInstances() {
        return instances;
    }

    /**
     * X5内核初始化
     */
    private void InitX5TenXun() {
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.iTag("InitX5TenXun", arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
