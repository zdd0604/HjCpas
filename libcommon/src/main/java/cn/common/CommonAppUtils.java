package cn.common;

import android.content.Context;

import cn.common.http.HttpRxUtils;

/**
 * Created by Administrator on 2016/11/14.
 */

public class CommonAppUtils {
    public static String MainPackageName = "";
    public static boolean isDebug;

    public static void onCreate(Context context, boolean debug) {
        MainPackageName = context.getPackageName();
        isDebug = debug;
//        MobclickAgent.setScenarioType(context, EScenarioType.E_UM_NORMAl);
        HttpRxUtils.setDebugMode(debug);
    }
}
