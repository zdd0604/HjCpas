package com.hj.casps.entity.appquote;

import java.util.List;

/**
 * 报价检索
 */
public class MmbBack {
    private int return_code;
    private String return_message;
    private String successMsg;
    private String errorMsg;
    private int groupCount;
    private List<ShowMmbModel> groupList;
    private List<ShowMmbModel> list;

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public List<ShowMmbModel> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<ShowMmbModel> groupList) {
        this.groupList = groupList;
    }

    public List<ShowMmbModel> getList() {
        return list;
    }

    public void setList(List<ShowMmbModel> list) {
        this.list = list;
    }
}
