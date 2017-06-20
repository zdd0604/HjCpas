package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 编辑操作员（编辑页面的确认提交动作）
 */

public class EditUserLoading implements Serializable {
    private static final long serialVersionUID = 8890032969676971966L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//	string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//string	会员id
    private String userId ;// string 当前被编辑用户Id
    private String account;//	string	账号
    private String name;//	string	用户名称
    private String telephone;//	string	电话
    private String email;//string	邮箱
    private String password;//string	密码
    private String password1;//string	确认密码
    private String roles;//string	角色Id,多个使用逗号隔开

    public EditUserLoading(String sys_token, String sys_uuid, String sys_func, String sys_user,
                           String sys_member, String userId, String account, String name, String telephone,
                           String email, String password, String password1, String roles) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.userId = userId;
        this.account = account;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.password1 = password1;
        this.roles = roles;
    }

    public EditUserLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String account,
                           String name, String telephone, String email, String password, String password1, String roles) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.account = account;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.password1 = password1;
        this.roles = roles;
    }

    public EditUserLoading() {
    }
}
