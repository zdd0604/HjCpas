package com.hj.casps.entity.appnemberrelationship;

/**
 * 搜索群组提交类，搜索供应商，谁关注我，通用
 */
public class QueryMMBConcerns {
    private String sys_token;
    private String sys_uuid;
    private String sys_func;
    private String sys_user;
    private String sys_member;
    private String group_name;
    private String goods_catergory;
    private String member_name;
    private String province;
    private String pageno;
    private String pagesize;
    private String member_ids;
    private String biz_type;

    public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String member_ids, String biz_type) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.member_ids = member_ids;
        this.biz_type = biz_type;
    }

    //查询关注我的会员列表（企业专用）
    public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String member_name, String province, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.member_name = member_name;
        this.province = province;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

    //查询所有供应商（学校专用）
    public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String goods_catergory, String member_name, String province, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.goods_catergory = goods_catergory;
        this.member_name = member_name;
        this.province = province;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

    public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String group_name) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.group_name = group_name;
    }
}