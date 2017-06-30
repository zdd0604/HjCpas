package com.hj.casps.entity.appQuote;

import java.io.Serializable;

/**
 * Created by Admin on 2017/6/30.
 * 接口url：appQuote/getMmb
 * 功能描述：点击发布范围查询合作会员
 */

public class GetMmbEntity implements Serializable {
    private static final long serialVersionUID = 1302799367742552486L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//string	会员id
    private String quoteId;//string	报价id

    public GetMmbEntity() {
    }

    public GetMmbEntity(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String quoteId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.quoteId = quoteId;
    }
}
