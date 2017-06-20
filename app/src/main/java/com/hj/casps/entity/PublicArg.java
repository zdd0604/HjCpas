package com.hj.casps.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */

public class PublicArg implements Serializable{

    private static final long serialVersionUID = 9093827840070818604L;
    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_name;//string	用户名
    private String sys_member;//	string	会员id
    private String sys_mmbname;/*当前会员名称*/
    private String sys_username;/*操作员名称*/

    public PublicArg(String sys_token, String sys_user, String sys_name, String sys_member) {
        this.sys_token = sys_token;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
    }
    public PublicArg(String sys_token, String sys_user, String sys_name, String sys_member,String sys_func,String sys_uuid,String sys_mmbname,String sys_username) {
        this.sys_username=sys_username;
        this.sys_mmbname=sys_mmbname;
        this.sys_token = sys_token;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.sys_func=sys_func;
        this.sys_uuid=sys_uuid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public String getSys_member() {
        return sys_member;
    }

    public void setSys_member(String sys_member) {
        this.sys_member = sys_member;
    }

    public String getSys_mmbname() {
        return sys_mmbname;
    }

    public void setSys_mmbname(String sys_mmbname) {
        this.sys_mmbname = sys_mmbname;
    }

    public String getSys_username() {
        return sys_username;
    }

    @Override
    public String toString() {
        return "PublicArg{" +
                "sys_token='" + sys_token + '\'' +
                ", sys_uuid='" + sys_uuid + '\'' +
                ", sys_func='" + sys_func + '\'' +
                ", sys_user='" + sys_user + '\'' +
                ", sys_name='" + sys_name + '\'' +
                ", sys_member='" + sys_member + '\'' +
                ", sys_mmbname='" + sys_mmbname + '\'' +
                ", sys_username='" + sys_username + '\'' +
                '}';
    }
}
