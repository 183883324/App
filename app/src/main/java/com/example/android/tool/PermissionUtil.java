package com.example.android.tool;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.Utils;

import static android.os.Build.VERSION.SDK_INT;

public class PermissionUtil {
    public static final int REQUEST_PERMISSION = 0x001;
    //app需要的全部危险权限在这里定义
    public static final String[] ALL_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private PermissionUtil() {

    }

    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public static String[] getPermissions() {
        return getPermissions(Utils.getApp().getPackageName());
    }

    /**
     * 检测权限
     *
     * @param activity 上下文
     * @return 是否需要申请权限
     */
    public static boolean checkPermission(Activity activity) {
        return checkPermission(activity, ALL_PERMISSIONS);
    }

    /**
     * 检测权限
     *
     * @param activity 上下文
     * @return 是否需要申请权限 true：不需要
     */
    public static boolean checkPermission(Activity activity, String... permissions) {
        return SDK_INT < Build.VERSION_CODES.M || checkPermissionAllGranted(permissions);
    }

    /**
     * 在fragment 检测权限
     *
     * @param fragment 上下文
     * @return 是否需要申请权限
     */
    public static boolean checkPermission(Fragment fragment) {
        return checkPermission(fragment, ALL_PERMISSIONS);
    }

    /**
     * 在fragment 检测权限
     *
     * @param fragment 上下文
     * @return 是否需要申请权限
     */
    public static boolean checkPermission(Fragment fragment, String... permissions) {
        return SDK_INT < Build.VERSION_CODES.M || checkPermissionAllGranted(permissions);
    }

    /**
     * 判断是否具有危险权限，是否获得此权限授权
     *
     * @param permissions 危险权限组
     * @return 是否具有全部权限
     */
    private static boolean checkPermissionAllGranted(String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(Utils.getApp(), permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;

    }

    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
    /**
     * 打开 APP 的详情设置
     */
/*    public static void openAppDetails(final Activity context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("去手动授权", (dialog, which) -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
        });
        builder.setNegativeButton("取消", (dialog, which) -> checkPermission(context));
        builder.setNeutralButton("退出软件", (dialog, which) -> context.finish());
        builder.show();
    }*/

    /**
     * 获取应用清单列表权限
     *
     * @param packageName 应用包名
     * @return 应用清单列表权限 permissions
     */
    public static String[] getPermissions(final String packageName) {
        PackageManager manager = Utils.getApp().getPackageManager();
        try {
            return manager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
                    .requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            return new String[]{};
        }
    }

    /**
     * Should show rationale boolean.
     *
     * @param activity   the activity
     * @param permission the permission
     * @return true:用户获取权限被拒绝；false:用户获取权限被拒绝并且选择"不再询问"
     */
    public static boolean shouldShowRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * Should show rationale boolean.
     *
     * @param fragment   the fragment
     * @param permission the permission
     * @return true:用户获取权限被拒绝；false:用户获取权限被拒绝并且选择"不再询问"
     */
    public static boolean shouldShowRationale(Fragment fragment, String permission) {
        return fragment.shouldShowRequestPermissionRationale(permission);
    }

}
