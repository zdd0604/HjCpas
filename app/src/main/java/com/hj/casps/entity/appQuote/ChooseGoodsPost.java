package com.hj.casps.entity.appQuote;

//商品选择提交类
public class ChooseGoodsPost {
    private String sys_token;
    private String sys_uuid;
    private String sys_func;
    private String sys_user;
    private String sys_name;
    private String sys_member;
    private String stringsId;
    private String type;
    private String index;
    private String categoryId;
    private String pageno;
    private String pagesize;
    private String goodStatus;
    private String name;

    public ChooseGoodsPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String stringsId, String type, String index) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.stringsId = stringsId;
        this.type = type;
        this.index = index;
    }

    public ChooseGoodsPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String categoryId, String pageno, String pagesize, String goodStatus, String name) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.categoryId = categoryId;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.goodStatus = goodStatus;
        this.name = name;
    }
}