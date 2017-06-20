package com.hj.casps.entity.paymentmanager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */

public class ResPayMoneyOffline {

    private String sys_token;//  string	令牌号
    private String sys_uuid;//             string	操作唯一编码（防重复提交）
    private String sys_func;//        string	功能编码（用于授权检查）
    private String sys_user;//                 string	用户id
    private String sys_member;//                               	string	会员id
    private List<ReqPayMoneyOffine> list;

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

    public List<ReqPayMoneyOffine> getList() {
        return list;
    }

    public void setList(List<ReqPayMoneyOffine> list) {
        this.list = list;
    }

    public ResPayMoneyOffline(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, List<ReqPayMoneyOffine> list) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.list = list;
    }
}
