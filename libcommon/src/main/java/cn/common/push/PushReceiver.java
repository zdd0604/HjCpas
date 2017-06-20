package cn.common.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/11/14.
 */

public class PushReceiver extends BroadcastReceiver {
    public static final String PushIntentAction = "cn.common.push.activity";
    public static final String PushExtra = "data";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        final String action = intent.getAction();
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {
            try {
                Intent goIntent = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                    goIntent.setPackage(context.getPackageName());
                }
                goIntent.setAction(PushIntentAction);
                goIntent.putExtra(PushExtra, bundle);
                goIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(goIntent);
            } finally {

            }
        }
    }
}
