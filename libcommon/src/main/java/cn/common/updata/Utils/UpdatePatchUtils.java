package cn.common.updata.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.io.File;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.common.updata.patch.PatchUtils;

/**
 * Created by Administrator on 2016/7/16.
 */
public class UpdatePatchUtils {
    public static PackageInfo getPackageInfo(Context context) {
        try {
            PackageInfo appInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return appInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void bmobFind(Context contet, PackageInfo appInfo, FindListener<AppPatchBmobBean> callback) {
        BmobQuery query = new BmobQuery();
        query.addWhereEqualTo("platform", "Android");
        query.addWhereEqualTo("channel", appInfo.packageName);
        query.addWhereEqualTo("oldVersionCode", appInfo.versionCode);
        query.addWhereEqualTo("oldVersionName", appInfo.versionName);
        query.addWhereGreaterThan("newVersionCode", appInfo.versionCode);
        query.order("-newVersionCode,-createdAt");
        query.setLimit(1);
        query.findObjects(callback);
    }

    /**
     * 生成新的文件 合并差分包
     *
     * @param appPatchBmobBean
     * @return
     */
    public static File getApkFilePath(Context context, AppPatchBmobBean appPatchBmobBean) {
        File newApkFile = getNewApkFile(context, appPatchBmobBean);
        String patchPath = getPatchFilePath(context, appPatchBmobBean).getAbsolutePath();
        String oldApk = getApkFile(context).getAbsolutePath();
        String newApkFilePath = newApkFile.getAbsolutePath();
        PatchUtils.patch(oldApk, newApkFilePath, patchPath);
        return newApkFile;
    }

    private static File getApkFile(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            String sourceDir = applicationInfo.sourceDir;
            return new File(sourceDir);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static File getNewApkFile(Context context, AppPatchBmobBean appPatchBmobBean) {
        File file = new File(context.getExternalCacheDir(), PatchPath);
        StringBuffer sb = new StringBuffer();
        sb.append(appPatchBmobBean.getOldVersionCode() + "_");
        sb.append(appPatchBmobBean.getOldVersionName() + "_");
        sb.append(appPatchBmobBean.getNewVersionCode() + "_");
        sb.append(appPatchBmobBean.getNewVersionName() + ".apk");
        String fileName = sb.toString();
        return new File(file, fileName);
    }

    /**
     * 检查差分包是否存在, 并比较文件大小是否一致
     *
     * @param appPatchBmobBean
     * @return
     */
    public static boolean checkPatchFilePath(Context context, AppPatchBmobBean appPatchBmobBean) {
        File file = getPatchFilePath(context, appPatchBmobBean);
        if (file.exists()) {
            if (appPatchBmobBean.getPatchFileLength() > 0) {
                if (file.length() == appPatchBmobBean.getPatchFileLength()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String PatchPath = "patch";

    /**
     * 差分包的中径
     *
     * @param appPatchBmobBean
     * @return
     */
    public static File getPatchFilePath(Context context, AppPatchBmobBean appPatchBmobBean) {
        File file = new File(context.getExternalCacheDir(), PatchPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(file, getPatchFileName(appPatchBmobBean));
        return file;
    }

    private static String getPatchFileName(AppPatchBmobBean appPatchBmobBean) {
        StringBuffer sb = new StringBuffer();
        sb.append(appPatchBmobBean.getOldVersionCode() + "_");
        sb.append(appPatchBmobBean.getOldVersionName() + "_");
        sb.append(appPatchBmobBean.getNewVersionCode() + "_");
        sb.append(appPatchBmobBean.getNewVersionName() + "_");
        sb.append(appPatchBmobBean.getPatchFile().getFilename());
        return sb.toString();
    }
}
