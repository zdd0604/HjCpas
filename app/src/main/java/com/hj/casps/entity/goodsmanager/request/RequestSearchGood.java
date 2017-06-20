package com.hj.casps.entity.goodsmanager.request;

/**
 * Created by Administrator on 2017/5/17.
 */

public class RequestSearchGood {

    private String sys_token;//       string	令牌号
    private String sys_uuid;//                 string	操作唯一编码（防重复提交）
    private String sys_func;//            	string	功能编码（用于授权检查）
    private String sys_user;//              	string	用户id
    private String sys_member;//              	string	会员id

    private String categoryId;//         	string	商品分类ID
    private String pageno;//               string	 页码
    private String pagesize;//       string	 每页数量

    public RequestSearchGood(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String categoryId, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.categoryId = categoryId;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }
}
