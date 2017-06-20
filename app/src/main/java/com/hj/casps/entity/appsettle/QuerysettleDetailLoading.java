package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/5.
 * 获取结款单详情
 */

public class QuerysettleDetailLoading implements Serializable {
    private static final long serialVersionUID = 4398084925329953906L;
    private String sys_token;//	string	令牌号
    private String 	sys_uuid	;//string	操作唯一编码（防重复提交）
    private String 	sys_func;//	string	功能编码（用于授权检查）
    private String 	sys_user	;//string	用户id
    private String 	sys_member;//	string	会员id
    private String 	id	;//string	结款单id

    public QuerysettleDetailLoading() {
    }

    public QuerysettleDetailLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String id) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.id = id;
    }
}
