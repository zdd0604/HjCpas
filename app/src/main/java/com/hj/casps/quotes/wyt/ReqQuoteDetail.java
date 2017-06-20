package com.hj.casps.quotes.wyt;

/**
 * Created by Administrator on 2017/5/17.
 */

public class ReqQuoteDetail {
    private String sys_token;//      string	令牌号
    private String sys_uuid;//         string	操作唯一编码（防重复提交）
    private String sys_func;//   string	功能编码（用于授权检查）
    private String sys_user;//  string	用户id
    private String sys_name;//               string	用户名
    private String sys_member;//          string	会员id
    private String quoteId		;//          string	会员id

    public ReqQuoteDetail(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String quoteId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.quoteId = quoteId;
    }

    @Override
    public String toString() {
        return "ReqQuoteDetail{" +
                "sys_token='" + sys_token + '\'' +
                ", sys_uuid='" + sys_uuid + '\'' +
                ", sys_func='" + sys_func + '\'' +
                ", sys_user='" + sys_user + '\'' +
                ", sys_name='" + sys_name + '\'' +
                ", sys_member='" + sys_member + '\'' +
                ", quoteId='" + quoteId + '\'' +
                '}';
    }
}
