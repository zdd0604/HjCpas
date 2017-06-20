package com.hj.casps.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.util.DataCleanManager;
import com.hj.casps.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;
import cn.common.updata.AppUpdateUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen on 2017/4/14.
 * 设置
 */

public class ActivitySetting extends ActivityBaseHeader2 implements View.OnClickListener {
    private MyDialog selfDialog;
    private TextView updataTv;
    private TextView cacheTv;
    private TextView version;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(getString(R.string.settings));
        initView();
        isUpdate();
        checkCache();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            cacheTv.setText(totalCacheSize);
            System.out.println("totalCacheSize=" + totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        findViewById(R.id.setting_notice_Re).setOnClickListener(this);
        findViewById(R.id.setting_clean_Re).setOnClickListener(this);
        findViewById(R.id.setting_new_version_Re).setOnClickListener(this);
        findViewById(R.id.setting_about_version_Re).setOnClickListener(this);
        findViewById(R.id.setting_about_us_Re).setOnClickListener(this);

        updataTv = (TextView) findViewById(R.id.setting_new_version_tv);
        cacheTv = (TextView) findViewById(R.id.setting_cache_size);
        version = (TextView) findViewById(R.id.setting_about_version_tv);





     /*   try {
            version.setText("v:" + getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //通知
            case R.id.setting_notice_Re:
                break;
            //清理
            case R.id.setting_clean_Re:
                selfDialog = new MyDialog(ActivitySetting.this);
                selfDialog.setMessage(getString(R.string.cleanask));
                selfDialog.setYesOnclickListener(getString(R.string.cleanfast), new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfDialog.dismiss();
                        DataCleanManager.cleanApplicationData(ActivitySetting.this);
                        DataCleanManager.CleanSomeDbData(ActivitySetting.this);
                        checkCache();
                        toast(getString(R.string.cleancomplete));
                    }
                });
                selfDialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();
                break;
            //更新
            case R.id.setting_new_version_Re:
                //BMOB更新
//                AppUpdateUtils.update(this, null);

                //  自定义下载
                downLoadNewVersion();


                break;
            //关于版本
            case R.id.setting_about_version_Re:
                startActivity(new Intent(ActivitySetting.this, ActivityAboutVersion.class));
                break;
            //关于我们
            case R.id.setting_about_us_Re:
                startActivity(new Intent(ActivitySetting.this, ActivityAboutUs.class));
                break;
        }
    }

    //下载新版本
    private void downLoadNewVersion() {
        if(!hasInternetConnected()){
            ToastUtils.showToast(this,"网络连接失败");
            return;
        }
        dialog = new ProgressDialog(ActivitySetting.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage("正在下载新版本");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMax(100);
        dialog.show();
        // 都是测试数据    需要替换
        OkGo.get("http://192.168.1.103:8080/1.apk").execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath(),"1.apk"){
            @Override
            public void onSuccess(File file, Call call, Response response) {
                dialog.dismiss();
                System.out.println("下载成功file = [" + file + "], call = [" + call + "], response = [" + response + "]");
                String str = "/1.apk";
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + str;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtils.showToast(ActivitySetting.this,"远程服务器异常");
                dialog.dismiss();
                System.out.println("下载失败call = [" + call + "], response = [" + response + "], e = [" + e + "]");
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                System.out.println("currentSize = [" + currentSize + "], totalSize = [" + totalSize + "], progress = [" + progress + "], networkSpeed = [" + networkSpeed + "]");
                System.out.println("thread="+Thread.currentThread().getName());
                dialog.setProgress((int) (progress*100));

            }
        });

    }

    /*
  * 检查缓存的大小
  */
    private void checkCache() {
        try {
            cacheTv.setText(DataCleanManager.getTotalCacheSize(this));
//            cacheTv.setText("0M");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 检查是否有更新
    */
    private void isUpdate() {
        AppUpdateUtils.updatePatch(this, new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                if (i != UpdateStatus.Yes) {
                    updataTv.setText(getString(R.string.isnew_version));
                } else {
                    updataTv.setText(getString(R.string.have_new_version));
                }
            }
        }, false);
    }


    private String getVersionName() throws Exception {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }
}
