package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/6/20.
 * 接口url：appOrderGoods/getTreeModal
 * 功能描述：获取地域树
 */

public class GetTreeModalEntity implements Serializable {
    private static final long serialVersionUID = 3666796469835460951L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//string	会员id

    public GetTreeModalEntity() {
    }

    public GetTreeModalEntity(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
    }
}
