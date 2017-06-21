package com.hj.casps.cooperate;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zy on 2017/6/8.
 * 关系管理：群组管理返回类，用来做数据缓存
 */
@Entity
public class GroupManagerListBean {
    /**
     * groupName : 广东高校群
     * groupStatus : 1
     * id : gd001
     * remark : 1
     */
    private String groupName;
    private int groupStatus;
    private String id;
    private String remark;

    @Generated(hash = 409516706)
    public GroupManagerListBean(String groupName, int groupStatus, String id,
            String remark) {
        this.groupName = groupName;
        this.groupStatus = groupStatus;
        this.id = id;
        this.remark = remark;
    }

    @Generated(hash = 57751778)
    public GroupManagerListBean() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
