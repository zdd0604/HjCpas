package cn.common.updata;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;
import cn.common.app.R;
import cn.common.updata.Utils.AppPatchBmobBean;
import cn.common.updata.Utils.PackageUtils;
import cn.common.updata.Utils.UpdatePatchUtils;

/**
 * Created by Administrator on 2016/7/16.<br>
 * UpdateStatus.TimeOut    =-2：应用配置文件没有设置BMOB_KEY<br>
 * UpdateStatus.TimeOut    =-1：查询出错或超时<br>
 * UpdateStatus.Yes        = 0：有更新<br>
 * UpdateStatus.No         = 1：没有更新<br>
 * UpdateStatus.IGNORED    = 3：该版本已被忽略更新<br>
 * UpdateStatus.EmptyField = 2：字段值为空，请检查以下内容：<br>
 * 1)、是否已填写target_size目标apk大小（以字节为单位）；<br>
 * 2)、path或者android_url两者是否必填其中一项（若两者都填写，则默认下载path字段下的apk文件）<br>
 * UpdateStatus.ErrorSizeFormat = 4：请检查target_size填写的格式，请使用file.length()方法获取apk大小<br>
 * UpdateStatus.Update     =6： 代表点击的是“立即更新”<br>
 * UpdateStatus.NotNow     =7： 代表点击的是“以后再说”<br>
 * UpdateStatus.Close      =8： 代表关闭对话框-->只有在强制更新状态下才会在更新对话框的右上方出现close按钮,
 * 如果用户不点击”立即更新“按钮，这时候开发者可做些操作，比如直接退出应用等<br>
 * UpdateStatus =100： 增量升级文件下载失败<br>
 * UpdateStatus =101： 增量升级检查到新版本 没有差分包<br>
 */
public class AppUpdateUtils {
    /**
     * 通过bmob内置的升级方式升级，表使用app_version,<br>
     * 升级规则相同的channel,versioncode大于当前的，最大的1个(及最新的)，<br>
     * 有新版本会有对话框提示
     *
     * @param context  不能为空
     * @param listener 可为空
     */
    public static void updateNormal(Context context, BmobUpdateListener listener) {
        if (!initBmob(context, listener)) {
            return;
        }
        if (listener != null) {
            BmobUpdateAgent.setUpdateListener(listener);
        }
        BmobUpdateAgent.forceUpdate(context);
    }

    /**
     * 检查配置中是否设置了bmonkey
     */
    private static boolean initBmob(Context context, BmobUpdateListener listener) {
        String bmobKey = PackageUtils.getBmobKey(context);
        if (bmobKey == null || bmobKey.length() < 2) {
            if (listener != null) {
                listener.onUpdateReturned(-2, null);
            }
            return false;
        }
        Bmob.initialize(context, bmobKey);
        return true;
    }

    /**
     * 先检查增量包，增量包不存在，则普通检查
     *
     * @param context
     * @param listener
     */
    public static void update(final Context context, final BmobUpdateListener listener) {
        BmobUpdateListener patchListener = new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                if (i != UpdateStatus.Yes) {
                    updateNormal(context, listener);
                }
            }
        };
        updatePatch(context, patchListener, true);
    }

    /**
     * 增量包的形式升级<br/>
     * app-patch表中，包名与channel相同，旧版本(当前应用)versionCode versionName相同，数据中的versionCode大于当前的<br>
     * 有新版本检查文件大小是否与网络文件大小一致，一致则不下载，否则下载到后文件合并，提示安装<br>
     *
     * @param context        不能为空 使用生命周期较长的application
     * @param listener       可为空
     * @param showViewDialog 有新版本时是否弹出对话框,只检测时有无新版时使用 true显示，false不显示
     */
    public static void updatePatch(final Context context, final BmobUpdateListener listener, final boolean showViewDialog) {
       /*无监听*/
        if (listener == null) {
            return;
        }
        /*bmob未设置key*/
        if (!initBmob(context, listener)) {
            return;
        }
        final Context contextApplication = context.getApplicationContext();
        PackageInfo appInfo = UpdatePatchUtils.getPackageInfo(contextApplication);
        if (appInfo == null) {
            /*参数出错*/
            if (listener != null) {
                listener.onUpdateReturned(UpdateStatus.EmptyField, null);
            }
        }
        UpdatePatchUtils.bmobFind(contextApplication, appInfo, new FindListener<AppPatchBmobBean>() {

            @Override
            public void done(final List<AppPatchBmobBean> patchList, BmobException e) {

                if (patchList != null && patchList.size() > 0) {
                         /*有新版本*/
                    if (listener != null) {
                        listener.onUpdateReturned(UpdateStatus.Yes, null);
                    }
                    if (showViewDialog) {
                        /*提示下载新版本*/
                        if (context != null) {
                            showConfirmDialog(context, patchList.get(0), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hasNewVersionDo(patchList, contextApplication, listener);
                                }
                            });
                        } else {
                            hasNewVersionDo(patchList, contextApplication, listener);
                        }
                    }
                } else {
                        /*没有新版本*/
                    if (listener != null) {
                        listener.onUpdateReturned(1, null);
                    }
                }

            }
        });
    }

    private static void log(String s) {
        Log.d(AppUpdateUtils.class.getSimpleName(), s);
    }

    /*显示对话框*/
    private static void showConfirmDialog(Context context, AppPatchBmobBean appPatchBmobBean, final View.OnClickListener onSubmitClickListener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.bmob_update_dialog, null);
        view.findViewById(R.id.bmob_update_wifi_indicator).setVisibility(View.GONE);
        /*升级日志*/
        TextView mlogTv = (TextView) view.findViewById(R.id.bmob_update_content);
        StringBuffer sb = new StringBuffer();
        sb.append("The latest version:" + appPatchBmobBean.getNewVersionName() + "\n");
        float v = appPatchBmobBean.getPatchFileLength() * 1f / 1024 / 1024;
        DecimalFormat df = new DecimalFormat("0.0");
        sb.append("New version size:" + df.format(v) + "M\n");
        sb.append("\n");
        sb.append("Update content:" + appPatchBmobBean.getUpdateLog() + "\n");
        mlogTv.setText(sb.toString());
        /*对话框按纽*/
        view.findViewById(R.id.bmob_update_id_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onSubmitClickListener != null) {
                    onSubmitClickListener.onClick(v);
                }
            }
        });
        view.findViewById(R.id.bmob_update_id_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private static void hasNewVersionDo(List<AppPatchBmobBean> patchList, final Context contextApplication, final BmobUpdateListener listener) {
    /*下载文件*/
        final AppPatchBmobBean appPatchBmobBean = patchList.get(0);
        BmobFile pathFile = appPatchBmobBean.getPatchFile();
        if (pathFile != null) {
            if (UpdatePatchUtils.checkPatchFilePath(contextApplication, appPatchBmobBean)) {
                /*文件已存在，生成新文件并安装*/
                File newApkPath = UpdatePatchUtils.getApkFilePath(contextApplication, appPatchBmobBean);
                BmobUpdateAgent.startInstall(contextApplication, newApkPath);
            } else {
                /*文件不存在*/
                DownloadFileListener downloadFileListener = new DownloadFileListener() {
                    @Override
                    public void onProgress(Integer integer, long l) {

                    }

                    @Override
                    public void done(String s, BmobException e) {
                         /*生成新的文件 并安装*/
                        File newApkPath = UpdatePatchUtils.getApkFilePath(contextApplication, appPatchBmobBean);
                        BmobUpdateAgent.startInstall(contextApplication, newApkPath);
                    }

                    @Override
                    public void doneError(int code, String msg) {
                        if (listener != null) {
                            listener.onUpdateReturned(100, null);
                        }
                    }
                };
                File filePath = UpdatePatchUtils.getPatchFilePath(contextApplication, appPatchBmobBean);
                pathFile.download(filePath, downloadFileListener);
            }
        } else {
            /*没有下载文件*/
            if (listener != null) {
                listener.onUpdateReturned(101, null);
            }
        }
    }
}
