package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 编辑操作员页面（获得启动编辑操作员页面所需数据）
 */

public class ToEditUserPageLoading implements Serializable {
    private static final long serialVersionUID = -5564573920104180589L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//string	会员id
    private String userId;//    string   操作用户Id

    public String getSys_token() {
        return sys_token;
    }

    public void setSys_token(String sys_token) {
        this.sys_token = sys_token;
    }

    public String getSys_uuid() {
        return sys_uuid;
    }

    public void setSys_uuid(String sys_uuid) {
        this.sys_uuid = sys_uuid;
    }

    public String getSys_func() {
        return sys_func;
    }

    public void setSys_func(String sys_func) {
        this.sys_func = sys_func;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    public String getSys_member() {
        return sys_member;
    }

    public void setSys_member(String sys_member) {
        this.sys_member = sys_member;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ToEditUserPageLoading() {
    }

    public ToEditUserPageLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String userId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.userId = userId;
    }
}
