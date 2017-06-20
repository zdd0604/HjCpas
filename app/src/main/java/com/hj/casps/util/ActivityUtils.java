package com.hj.casps.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity处理类
 */

public class ActivityUtils {
    private static List<WeakReference> activitys = new ArrayList<>();

    /**
     * 添加activiyt到列表 以便退出时用到
     *
     * @param a
     */
    public static void addActivity(Activity a) {
        activitys.add(new WeakReference(a));
    }

    public static void contextAnimal(Activity context) {
//        context.overridePendingTransition(R.anim.slide_left_in,
//                R.anim.slide_right_in);
    }

    /**
     * 判断某个界面是否在栈顶
     *
     * @param context
     * @param className 某个界面名称
     */
    public static boolean activityIsTaskTop(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        //只能获到简单的信息
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前栈内是否包含该activity实例
     *
     * @param className 否包含该activity实例
     */
    public static boolean activityInTask(String className) {
        for (WeakReference activity : activitys) {
            if (activity != null) {
                Object o = activity.get();
                if (o instanceof Activity) {
                    Activity _activity = ((Activity) o);
                    if (_activity.getClass().getName().equals(className)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 关闭位于className顶部的activity className不删除， 最顶部的不删除
     *
     * @param className
     * @param finishClass
     */
    public static void activityFinishAfter(String className, boolean finishClass) {
        boolean beginDestory = false;
        for (int i = 0; i < activitys.size() - 1; i++) {
            WeakReference activity = activitys.get(i);
            if (activity != null) {
                Object o = activity.get();
                if (o instanceof Activity) {
                    Activity _activity = ((Activity) o);
                    if (_activity.getClass().getName().equals(className) && !beginDestory) {
                        /*当前类 且 未开始清除时*/
                        beginDestory = true;
                        if (finishClass) {
                            _activity.finish();
                        }
                        continue;
                    }
                    if (beginDestory) {
                        _activity.finish();
                    }
                }
            }
        }
    }

    /**
     * 退出每个activity以退出应用
     */
    public static void exit() {
        // System.exit(0); //不能退出
        for (WeakReference activity : activitys) {
            if (activity != null) {
                Object o = activity.get();
                if (o instanceof Activity) {
                    ((Activity) o).finish();
                }
            }
        }
        Process.killProcess(Process.myPid());
    }

    /**
     * 跳转activity
     *
     * @param context
     * @param c
     */
    public static void startActivity(Activity context, Class c) {
        Intent intent = new Intent(context, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(context, intent);
    }

    public static void startActivity(Fragment context, Class c) {
        startActivity(context.getActivity(), c);
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
        contextAnimal(context);
    }

    public static void startActivityForResult(Activity context, Intent intent,
                                              int requestCode) {
        context.startActivityForResult(intent, requestCode);
        contextAnimal(context);
    }

    /**
     * 通过包名打开一个应用
     *
     * @param context
     * @param packageName
     */
    public static void startApp(Activity context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(context, intent);
        } else {
            startMarket(context, packageName);
        }
    }

    /**
     * 打开地图应用
     *
     * @param context
     */
    public static void startMapApp(Activity context) {
        Uri location = Uri
                .parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        // Or map point based on latitude/longitude
        // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param
        // is zoom level
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(context, mapIntent);
    }

    /**
     * 打开市场应用跳到包名指定的应用
     *
     * @param context
     * @param packageName
     */
    public static void startMarket(Activity context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(context, intent);
    }

    /*除当前页面，其他全部关闭*/
    public static void activityDestoryOutThis(Activity context) {
        for (int i = 0; i < activitys.size(); i++) {
            WeakReference activity = activitys.get(i);
            if (activity != null) {
                Object o = activity.get();
                if (o instanceof Activity) {
                    Activity _activity = ((Activity) o);
                    if (_activity != context) {
                        _activity.finish();
                    }
                }
            }
        }
    }
}
