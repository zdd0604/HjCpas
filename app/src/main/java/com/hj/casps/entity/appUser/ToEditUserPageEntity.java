package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 编辑操作员页面（获得启动编辑操作员页面所需数据）
 */

public class ToEditUserPageEntity implements Serializable {
    private static final long serialVersionUID = 5822320366612341164L;
    private String userId;//	 string	用户ID
    private String account;//	string	账号
    private String name;//	string	名称
    private String email;//string	 邮箱
    private String telephone;//	string	电话号码
    private int state;//	int	状态

    public ToEditUserPageEntity() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ToEditUserPageEntity(String userId, String account, String name, String email, String telephone, int state) {
        this.userId = userId;
        this.account = account;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.state = state;
    }
}
