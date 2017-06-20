package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/5.
 * 获取收/付款方列表 入口参数
 */

public class QueryOppositeListLoading implements Serializable {
    private static final long serialVersionUID = -6737053997463844014L;
    private String sys_token;//	string	令牌号
    private String sys_uuid;//	string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//string	会员id
    private String isBuy;//	string	本方付款isbuy=buy、对方付款isbuy=sell

    public QueryOppositeListLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String isbuy) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        isBuy = isbuy;
    }

    public QueryOppositeListLoading() {
    }

}
