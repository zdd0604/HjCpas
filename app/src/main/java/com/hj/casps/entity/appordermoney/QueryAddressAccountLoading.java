package com.hj.casps.entity.appordermoney;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/16.
 * 获取收/付款方银行账号
 */

public class QueryAddressAccountLoading implements Serializable {
    private static final long serialVersionUID = -9145066131836563720L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//string	会员id
    private String buyersId;//string	付款方id

    public QueryAddressAccountLoading() {
    }

    public QueryAddressAccountLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String buyersId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.buyersId = buyersId;
    }
}
