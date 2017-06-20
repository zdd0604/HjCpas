package cn.common.local;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.io.File;

import cn.common.CommonAppUtils;

/**
 * Created by 鑫 Administrator on 2017/4/24.
 */

public class XUtils {
    /**
     * @param context
     * @param pathName 不可为空
     * @return
     */
    public static String getDir(Context context, String pathName) {
        boolean hasSDCard = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable();
        File file = null;
        if (hasSDCard && context != null) {
            file = new File(context.getExternalCacheDir(), pathName);
        } else if (!hasSDCard && context != null) {
            file = new File(context.getCacheDir(), pathName);
        } else {
            file = new File(Environment.getExternalStorageDirectory(), CommonAppUtils.MainPackageName);
            file = new File(file, pathName);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 检测网络，并弹出对话框
     *
     * @param context
     */
    public static void checkNetWork(final Context context) {
        if (!isConnected(context)) {
            TextView textview = new TextView(context);
            textview.setTextSize(20);
            textview.setTextColor(Color.BLUE);
            textview.setText("是否打开网络?");
            new AlertDialog.Builder(context).setTitle("网络连接失败")
                    .setView(textview)
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_WIRELESS_SETTINGS);
                            context.startActivity(intent);
                        }
                    }).create().show();
        }
    }

    /**
     * 判断是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        boolean flag = false;
        // 得到网络连接管理对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Service.CONNECTIVITY_SERVICE);
        // 获取所有网络信息
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        if (networkInfos != null) {
            for (NetworkInfo networkInfo : networkInfos) {
                if ((networkInfo.getState() == NetworkInfo.State.CONNECTED)
                        && networkInfo.isAvailable()) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 判断是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        boolean flag = false;
        // 得到网络连接管理对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState();
        // 获取所有网络信息
        if (wifiState != null && (NetworkInfo.State.CONNECTED == wifiState)) {
            return true;
        }
        return flag;
    }
}
