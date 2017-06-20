package com.hj.casps.entity.appUser;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Admin on 2017/4/21.
 * 获取当前会员的操作员列表（获取 操作员管理 页面所需数据）
 * item参数数据
 */
@Entity
public class QueryUserEntity implements Serializable {
    private static final long serialVersionUID = -3760402741218952986L;
    private String userId;//	string	操作员ID
    private String account;//	string	操作员账号
    private String name;//	string	操作员名称
    @Generated(hash = 868011197)
    public QueryUserEntity(String userId, String account, String name) {
        this.userId = userId;
        this.account = account;
        this.name = name;
    }
    @Generated(hash = 49268270)
    public QueryUserEntity() {
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
