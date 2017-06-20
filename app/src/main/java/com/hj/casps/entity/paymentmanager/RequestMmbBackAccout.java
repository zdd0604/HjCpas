package com.hj.casps.entity.paymentmanager;

/**
 * Created by Administrator on 2017/5/16.
 */

public class RequestMmbBackAccout {


    private String sys_token;//tring	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//string	会员id
    private  String id;
    private String accountno;//   string	账户号码
    private String accountname;//	string		账户全称
    private String bankname;//    string		银行名称
    private String contact;//          string		联系人名称
    private String mobilephone;//	                                     string		手机号码
    private String phone;//   string	电话号码


    public RequestMmbBackAccout(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String accountno, String accountname, String bankname, String contact, String mobilephone, String phone) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.accountno = accountno;
        this.accountname = accountname;
        this.bankname = bankname;
        this.contact = contact;
        this.mobilephone = mobilephone;
        this.phone = phone;
    }

    public RequestMmbBackAccout(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String id, String accountno, String accountname, String bankname, String contact, String mobilephone, String phone) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.id = id;
        this.accountno = accountno;
        this.accountname = accountname;
        this.bankname = bankname;
        this.contact = contact;
        this.mobilephone = mobilephone;
        this.phone = phone;
    }
}
