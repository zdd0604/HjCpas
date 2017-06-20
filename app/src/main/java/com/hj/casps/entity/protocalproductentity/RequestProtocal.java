package com.hj.casps.entity.protocalproductentity;

public class RequestProtocal {
    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_name;//string	用户名
    private String sys_member;//	string	会员id
    private String ctrId;//	string	协议ID
    private String sellMmbId;//	string	供应商ID
    private String memberId;//	string	要查询的分类会员Id

    public RequestProtocal(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String ctrId, String sellMmbId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.ctrId = ctrId;
        this.sellMmbId = sellMmbId;
    }

    public RequestProtocal(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String memberId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.memberId = memberId;
    }
}