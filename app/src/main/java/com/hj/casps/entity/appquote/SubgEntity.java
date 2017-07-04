package com.hj.casps.entity.appquote;

import java.io.Serializable;

/**
 * Created by Admin on 2017/6/30.
 * 接口url：appQuote/subg
 * 功能描述：公开确认按钮(和选择发布范围确认按钮一样)
 */

public class SubgEntity implements Serializable {
    private static final long serialVersionUID = -6513500218881531121L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//string	会员id
    private String rangType;//	int	发布类型
    private String quoteId;//string	报价id

    public SubgEntity() {
    }

    public SubgEntity(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String rangType, String quoteId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.rangType = rangType;
        this.quoteId = quoteId;
    }
}
