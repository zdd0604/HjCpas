package com.hj.casps.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.hj.casps.R;
import com.hj.casps.app.ApiList;
import com.hj.casps.app.Constants;
import com.hj.casps.app.DataFilePersistenceUtils;
import com.hj.casps.app.HejiaApp;
import com.hj.casps.base.ActivityBase;

import cn.common.http.HttpRxUtils;

/**
 * Created by YaoChen on 2017/4/18.
 */

public class ActivityStartPage extends ActivityBase {
    private final int SdcardPermissionRequestCode = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
    }

    /*android6.0文件权限问题 http://www.jianshu.com/p/4f587f742d85*/
    private void checkPermission() {
        int writePermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (writePermissionCheck != PackageManager.PERMISSION_GRANTED
                || readPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(this, permissions, SdcardPermissionRequestCode);
        } else {
//            new StartPageGetApiList(url).execute();
            next();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SdcardPermissionRequestCode) {
            if (grantResults.length > 0) {
                boolean allGranted = true;
                for (int t : grantResults) {
                    if (t != PackageManager.PERMISSION_GRANTED) {
                        allGranted = false;
                        break;
                    }
                }
                if (allGranted) {
                    next();
                    return;
                } else {
                    toast(getString(R.string.user_PleaseOpenPermission));
                    /*跳到开启权限的设置页面*/
                    goPermissionSet();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void goPermissionSet() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            String uriString = "package:" + getPackageName();
            intent.setData(Uri.parse(uriString));
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    private void next() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivityStartPage.this, ActivityLoginBefore.class);
                intent.setPackage(getPackageName());
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
    /**
     * 加载完成接口列表后，跳到首页
     */
    private class StartPageGetApiList {
        private final String url;

        /*接口列表的根地址*/
        public StartPageGetApiList(String url) {
            this.url = url;
        }

        protected void onPostExecute(String s) {
            if (s != null) {
                ApiList apiList = new ApiList(s);
                DataFilePersistenceUtils.write(Constants.ApiListFileName, apiList);
                ((HejiaApp) getApplication()).setApiList(apiList);
            } else {
                Object localApiList = DataFilePersistenceUtils.readObj(Constants.ApiListFileName);
                if (localApiList != null) {
                    ApiList apiList = (ApiList) localApiList;
                    ((HejiaApp) getApplication()).setApiList(apiList);
                }
                toast(getString(R.string.user_NetError));
            }
            next();
        }

        public void execute() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", 1);
            HttpRxUtils.nextPostJson(ActivityStartPage.this, url, jsonObject, String.class, null, false)
                    .subscribe(new HttpRxUtils.NextSubscriber<String>(ActivityStartPage.this) {
                        @Override
                        public void onSuccessNext(String s) {
                            onPostExecute(s);
                        }
                    });
        }

    }
}
