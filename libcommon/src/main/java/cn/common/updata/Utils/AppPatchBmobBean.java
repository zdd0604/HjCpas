package cn.common.updata.Utils;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/7/16.
 */
public class AppPatchBmobBean extends BmobObject {
    private String platform;
    private String channel;
    private String newVersionName;
    private String oldVersionName;
    private int newVersionCode;
    private long patchFileLength; /*文件大小*/
    private int oldVersionCode;
    private BmobFile patchFile;
    private String updateLog;/*更新日志*/

    public String getPlatform() {
        return platform;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public long getPatchFileLength() {
        return patchFileLength;
    }

    public void setPatchFileLength(long patchFileLength) {
        this.patchFileLength = patchFileLength;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNewVersionName() {
        return newVersionName;
    }

    public void setNewVersionName(String newVersionName) {
        this.newVersionName = newVersionName;
    }

    public String getOldVersionName() {
        return oldVersionName;
    }

    public void setOldVersionName(String oldVersionName) {
        this.oldVersionName = oldVersionName;
    }

    public int getNewVersionCode() {
        return newVersionCode;
    }

    public void setNewVersionCode(int newVersionCode) {
        this.newVersionCode = newVersionCode;
    }

    public int getOldVersionCode() {
        return oldVersionCode;
    }

    public void setOldVersionCode(int oldVersionCode) {
        this.oldVersionCode = oldVersionCode;
    }

    public BmobFile getPatchFile() {
        return patchFile;
    }

    public void setPatchFile(BmobFile patchFile) {
        this.patchFile = patchFile;
    }
}
