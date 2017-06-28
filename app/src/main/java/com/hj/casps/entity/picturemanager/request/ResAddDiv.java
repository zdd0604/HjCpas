package com.hj.casps.entity.picturemanager.request;

/**
 * Created by Administrator on 2017/5/24.
 */

public class ResAddDiv {
    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//	string	会员id

   private String divName	 ;//目录名称
   private String parentId	 ;// 父目录Id(如果增加顶级目录，parentId传空)
   private String baseId	;//素材库Id


    public ResAddDiv(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String divName, String parentId, String baseId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.divName = divName;
        this.parentId = parentId;
        this.baseId = baseId;
    }


}
