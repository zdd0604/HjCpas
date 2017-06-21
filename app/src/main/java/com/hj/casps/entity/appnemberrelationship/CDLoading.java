package com.hj.casps.entity.appnemberrelationship;

/**
 * 详单中需要提交的数据
 */
public class CDLoading {
    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_name;//string	用户名
    private String sys_member;//	string	会员id
    private String relation_member;//	string	点击的会员id
    private String type;//	string	传固定值"1"
    private String buztype;//	string	是买还是卖的关系 0：买 1：卖 2：借 3：贷

    public CDLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String relation_member, String type, String buztype) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.relation_member = relation_member;
        this.type = type;
        this.buztype = buztype;
    }

    public CDLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String relation_member, String type) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.relation_member = relation_member;
        this.type = type;
    }

    public CDLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String relation_member) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.relation_member = relation_member;
    }


    public String getSys_token() {
        return sys_token;
    }

    public void setSys_token(String sys_token) {
        this.sys_token = sys_token;
    }

    public String getSys_uuid() {
        return sys_uuid;
    }

    public void setSys_uuid(String sys_uuid) {
        this.sys_uuid = sys_uuid;
    }

    public String getSys_func() {
        return sys_func;
    }

    public void setSys_func(String sys_func) {
        this.sys_func = sys_func;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public String getSys_member() {
        return sys_member;
    }

    public void setSys_member(String sys_member) {
        this.sys_member = sys_member;
    }

    public String getRelation_member() {
        return relation_member;
    }

    public void setRelation_member(String relation_member) {
        this.relation_member = relation_member;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuztype() {
        return buztype;
    }

    public void setBuztype(String buztype) {
        this.buztype = buztype;
    }
}
