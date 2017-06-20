package com.hj.casps.entity.picturemanager.request;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ReqMark {


    private String sys_token;     //                       string	令牌号
    private String sys_uuid;//           s tring	操作唯一编码（防重复提交）
    private String sys_func;//     string	功能编码（用于授权检查）
    private String sys_user;//              string	用户id
    private String sys_member;     //                string	会员id
    private String member_ids;


    public ReqMark(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String member_ids) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.member_ids = member_ids;
    }

}