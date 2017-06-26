package com.hj.casps.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.hj.casps.common.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by YaoChen on 2017/4/21.
 * 判断网络工具类
 */
public class NetUtil {
    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;

    public static int getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    /**
     * 判断某一网段网络是否可以连接
     * @return
     */
    public static final boolean ping() {

        String result = null;
        try {
            String ip = Constant.HTTPURLALL.substring(Constant.HTTPURLALL.indexOf("//")+2,Constant.HTTPURLALL.lastIndexOf(":"));;// ping 的地址，可以换成任何一种可靠的外网

            Process p = null;// ping网址3次
            //其中参数-c 1是指ping的次数为1次，-w是指执行的最后期限,单位为秒，也就是执行的时间为3秒，超过3秒则失败.
            try {
                p = Runtime.getRuntime().exec("ping -c 1 -w 3 " + ip);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 读取ping的内容，可以不加
            /*InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }*/
            /*Log.d("------ping-----", "result content : " + stringBuffer.toString());*/
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }
}
