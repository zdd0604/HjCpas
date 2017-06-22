package com.hj.casps.entity.appContract;

/**
 * 跳转同意协议输入界面请求参数类
 */
public class ShowAgreePost {
    private String sys_token;
    private String sys_uuid;
    private String sys_func;
    private String sys_user;
    private String sys_member;
    private String contract_id;
    private String contract_status;
    private String contract_type;
    private String address_name;
    private String bank_accountno;
    private String bank_accountname;

    public ShowAgreePost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String contract_id, String contract_type, String address_name, String bank_accountno, String bank_accountname) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.contract_id = contract_id;
        this.contract_type = contract_type;
        this.address_name = address_name;
        this.bank_accountno = bank_accountno;
        this.bank_accountname = bank_accountname;
    }

    public ShowAgreePost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String contract_id, String contract_status, String contract_type) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.contract_id = contract_id;
        this.contract_status = contract_status;
        this.contract_type = contract_type;
    }
}