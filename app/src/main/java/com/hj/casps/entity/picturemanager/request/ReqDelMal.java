package com.hj.casps.entity.picturemanager.request;

/**
 * Created by Administrator on 2017/5/27.
 */

public class ReqDelMal {

    private String sys_token;//	令牌号
    private String sys_uuid;//	操作唯一编码（防重复提交）
    private String sys_func;//	功能编码（用于授权检查）
    private String sys_user;//	用户id
    private String sys_member;//	会员id

    private String materialId;//	 素材Id

    public ReqDelMal(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String materialId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.materialId = materialId;
    }
}
