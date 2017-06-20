package cn.common.updata.patch;

/**
 * Created by Administrator on 2016/7/16.
 */
public class PatchUtils {
    static {
        System.loadLibrary("AppUpdate");
    }
    public native static int patch(String oldApk, String newApkFilePath, String patchPath) ;
}
