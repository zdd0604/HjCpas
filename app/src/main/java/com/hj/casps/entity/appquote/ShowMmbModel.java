package com.hj.casps.entity.appquote;

public class ShowMmbModel {
    private int mmbStatus;
    private String mmbSname;
    private String mmbId;
    private String groupName;
    private String id;
    private int groupStatus;
    private boolean isChoose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMmbId() {
        return mmbId;
    }

    public void setMmbId(String mmbId) {
        this.mmbId = mmbId;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getMmbStatus() {
        return mmbStatus;
    }

    public void setMmbStatus(int mmbStatus) {
        this.mmbStatus = mmbStatus;
    }

    public String getMmbSname() {
        return mmbSname;
    }

    public void setMmbSname(String mmbSname) {
        this.mmbSname = mmbSname;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }
}