package com.yuu.android.component.voice.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.core.content.FileProvider;

import java.io.File;
import java.security.MessageDigest;


public class ApkUtils {

    /**
     * App是否已安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        boolean isAppInstalled = false;
        if (packageName == null || packageName.isEmpty()) return isAppInstalled;

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            isAppInstalled = packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            isAppInstalled = false;
        }
        return isAppInstalled;
    }

    /**
     * 获取程序名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appname = null;
        try {
            String packageName = context.getPackageName();
            appname = packageName.substring(packageName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appname;
    }

    /**
     * 得到软件版本号
     *
     * @param context 上下文
     * @return 当前版本Code
     */
    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            String packageName = context.getPackageName();
            verCode = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    public static int getVersionCode(Context context, String packageName) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取apk包信息
     *
     * @param context
     * @param apkPath
     * @return
     */
    public static PackageInfo getPackageInfo(Context context, String apkPath) {
        PackageInfo pi = context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        return pi;
    }

    /**
     * 得到软件版本名
     *
     * @param context 上下文
     * @return 当前版本Name
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            String packageName = context.getPackageName();
            verName = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取应用签名
     *
     * @param context 上下文
     * @param pkgName 包名
     * @return 返回应用的签名
     */
    public static String getSign(Context context, String pkgName) {
        try {
            PackageInfo pis = context.getPackageManager()
                    .getPackageInfo(pkgName,
                            PackageManager.GET_SIGNATURES);
            return hexdigest(pis.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将签名字符串转换成需要的32位签名
     *
     * @param paramArrayOfByte 签名byte数组
     * @return 32位签名字符串
     */
    private static String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
                98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

//    /**
//     * 安装apk
//     *
//     * @param context  上下文
//     * @param filePath APK文件路径
//     */
//    public static void installApk(Context context, String filePath) {
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(new File(filePath)),
//                "application/vnd.android.package-archive");
//        context.startActivity(intent);
//    }

    public static void installApk(Context context, String filePath) {
        installApk(context, new File(filePath), context.getPackageName());
    }

    /**
     * 安装apk
     *
     * @param context   上下文
     * @param file      APK文件
     * @param authority FileProvider
     */
    public static void installApk(Context context, File file, String authority) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, authority, file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件uri
     */
    public static void installApk(Context context, Uri file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 卸载apk
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    public static Drawable getIcon(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return getIcon(context, packageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Apk中最高清图标
     *
     * @param context
     * @param packageInfo
     * @return
     */
    public static Drawable getIcon(Context context, PackageInfo packageInfo) {
        try {
            Context otherAppCtx = context.createPackageContext(packageInfo.packageName, 2);
            int displayMetrics[] = {DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV};
            int length = displayMetrics.length;
            int i = 0;
            while (i < length) {
                try {
                    Drawable d = otherAppCtx.getResources().getDrawableForDensity(packageInfo.applicationInfo.icon, displayMetrics[i]);
                    if (d != null) {
                        return d;
                    }
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        return packageInfo.applicationInfo.loadIcon(context.getPackageManager());
    }

    /**
     * 获取Activity图标
     *
     * @param context
     * @param activityInfo
     * @return
     */
    public static Drawable getIcon(Context context, ActivityInfo activityInfo) {
        try {
            Context otherAppCtx = context.createPackageContext(activityInfo.packageName, Context.CONTEXT_IGNORE_SECURITY);
            int displayMetrics[] = {DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV};
            int length = displayMetrics.length;
            int i = 0;
            while (i < length) {
                try {
                    Drawable d = otherAppCtx.getResources().getDrawableForDensity(activityInfo.getIconResource(), displayMetrics[i]);
                    if (d != null) {
                        return d;
                    }
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        return activityInfo.loadIcon(context.getPackageManager());
    }
}
