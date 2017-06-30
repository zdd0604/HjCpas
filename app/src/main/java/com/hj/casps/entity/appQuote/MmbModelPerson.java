package com.hj.casps.entity.appQuote;

//会员群组提交类
public class MmbModelPerson {
    private String id;
    private String mmbSname;
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMmbSname() {
        return mmbSname;
    }

    public void setMmbSname(String mmbSname) {
        this.mmbSname = mmbSname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}