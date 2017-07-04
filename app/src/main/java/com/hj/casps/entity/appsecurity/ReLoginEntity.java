package com.hj.casps.entity.appsecurity;

import java.io.Serializable;

/**
 * Created by Admin on 2017/7/4.
 */

public class ReLoginEntity implements Serializable {
    private static final long serialVersionUID = 7732672844444530916L;

    private String sys_account;
    private String sys_pwd;
    private String sys_user;
    private String sys_token;

    public ReLoginEntity(String sys_account, String sys_pwd, String sys_user, String sys_token) {
        this.sys_account = sys_account;
        this.sys_pwd = sys_pwd;
        this.sys_user = sys_user;
        this.sys_token = sys_token;
    }

    public ReLoginEntity() {
    }
}
