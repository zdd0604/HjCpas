package com.hj.casps.entity.goodsmanager.request;

/**
 * Created by Administrator on 2017/5/13.
 */

public class RequestUpdateStatus {

    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//	string	会员id
    private String goodsId;//商品id
    private String status;   //停用启用

    public RequestUpdateStatus(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String goodsId,String status) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;

        this.sys_member = sys_member;
        this.goodsId = goodsId;
        this.status=status;
    }
}
