package com.hj.casps.entity.appordergoods;

public class RequestProtocal {
    private String sys_token;
    private String sys_uuid;
    private String sys_func;
    private String sys_user;
    private String sys_member;
    private String goodsId;

    public RequestProtocal(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String goodsId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.goodsId = goodsId;
    }
}
