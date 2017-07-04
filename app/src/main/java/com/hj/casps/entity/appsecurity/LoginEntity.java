package com.hj.casps.entity.appsecurity;

import java.io.Serializable;

/**
 * Created by Admin on 2017/7/4.
 */

public class LoginEntity implements Serializable {
    private static final long serialVersionUID = -6344185776987626547L;
    private String sys_user;
    private String sys_account;
    private String sys_pwd;
    private String sys_message;

    public LoginEntity(String sys_user, String sys_account, String sys_pwd, String sys_message) {
        this.sys_user = sys_user;
        this.sys_account = sys_account;
        this.sys_pwd = sys_pwd;
        this.sys_message = sys_message;
    }

    public LoginEntity() {
    }
}
