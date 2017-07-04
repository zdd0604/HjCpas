package com.hj.casps.entity.appquote;

import java.io.Serializable;

/**
 * Created by Admin on 2017/6/30.
 * 接口url：appQuote/deleteScopeId
 * 功能描述：删除报价范围
 */

public class DeleteScopeIdEntity implements Serializable {
    private static final long serialVersionUID = 7897701030636660822L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//	string	会员id
    private String quoteId;//string	报价id
    private String scopeId;//string	选中的id

    public DeleteScopeIdEntity() {
    }

    public DeleteScopeIdEntity(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String quoteId, String scopeId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.quoteId = quoteId;
        this.scopeId = scopeId;
    }
}
