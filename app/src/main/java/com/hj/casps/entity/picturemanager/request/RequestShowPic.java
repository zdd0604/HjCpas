package com.hj.casps.entity.picturemanager.request;

/**
 * Created by Administrator on 2017/5/14.
 */

public class RequestShowPic {
    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//	string	会员id


    private String divId;//       string	目录Id
    private String type;//      	int             	素材类型
    private String materialName;//      int 素材名称
    private String pageno;//    int	 页码
    private String pagesize;//    int	 每页数量

    public RequestShowPic(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String divId, String type, String materialName, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.divId = divId;
        this.type = type;
        this.materialName = materialName;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }
}
