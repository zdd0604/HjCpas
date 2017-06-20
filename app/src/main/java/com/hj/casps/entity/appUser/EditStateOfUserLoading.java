package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 修改操作员状态（停用、启用操作动作）
 */

public class EditStateOfUserLoading implements Serializable {
    private static final long serialVersionUID = -5023691649430654942L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//	string	会员id
    private String userId;//string	操作员用户Id
    private int state;//	int	状态值

    public EditStateOfUserLoading() {
    }

    public EditStateOfUserLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String userId, int state) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.userId = userId;
        this.state = state;
    }
}
