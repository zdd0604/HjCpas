package com.hj.casps.entity.paymentmanager;

/**
 * Created by Administrator on 2017/5/16.
 */

public class RequestBackAccount {

    private String sys_token;//          string	令牌号
    private String sys_uuid;//       string	操作唯一编码（防重复提交）
    private String sys_func;//              string	功能编码（用于授权检查）
    private String sys_user;//          	string	用户id
    private String sys_member;//        string	会员id
    private String accountname;//                  	string	账户名称
    private String pageno;//            int	页号
    private String pagesize;//          	int	页行数

    public RequestBackAccount(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

    public RequestBackAccount(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String accountname, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.accountname = accountname;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

    @Override
    public String toString() {
        return "RequestBackAccount{" +
                "sys_token='" + sys_token + '\'' +
                ", sys_uuid='" + sys_uuid + '\'' +
                ", sys_func='" + sys_func + '\'' +
                ", sys_user='" + sys_user + '\'' +
                ", sys_member='" + sys_member + '\'' +
                ", accountname='" + accountname + '\'' +
                ", pageno='" + pageno + '\'' +
                ", pagesize='" + pagesize + '\'' +
                '}';
    }
}
