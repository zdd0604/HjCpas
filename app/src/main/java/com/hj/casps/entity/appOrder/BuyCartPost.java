package com.hj.casps.entity.appOrder;

//请求类
public class BuyCartPost {
    private String sys_token;
    private String sys_uuid;
    private String sys_func;
    private String sys_user;
    private String sys_name;
    private String sys_member;
    private String type;
    private String index;

    //请求获取列表使用的参数
    public BuyCartPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String type) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.type = type;
    }

    //请求删除时使用的参数
    public BuyCartPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String type, String index) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.type = type;
        this.index = index;
    }
}