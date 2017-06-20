package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/17.
 * 订单查询详情（所有出订单详情和订单编辑页面都用这个）
 */

public class AppOrderCheckOrderLoading implements Serializable {
    private static final long serialVersionUID = -7505207254564860660L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_name;//string	用户名
    private String sys_member;//string	会员id
    private String orderId;//string	订单号

    public AppOrderCheckOrderLoading() {
    }

    public AppOrderCheckOrderLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String orderId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.orderId = orderId;
    }
}
