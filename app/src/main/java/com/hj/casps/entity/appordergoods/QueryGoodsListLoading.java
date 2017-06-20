package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/11.
 * 功能描述：
 * 查询收货列表
 * 发货列表展示
 * 退货列表展示
 * 收退货里列表展示
 */

public class QueryGoodsListLoading implements Serializable {
    private static final long serialVersionUID = 1783213614848744565L;
    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//string	会员id
    private String ordertitleNumber;//	string	订单编号
    private String goodsName;//string	商品名称
    private String pageno;// int	开始条数
    private String pagesize;//int	展示条数
    public QueryGoodsListLoading() {
    }

    public QueryGoodsListLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String ordertitle_number, String goodsName, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.ordertitleNumber = ordertitle_number;
        this.goodsName = goodsName;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }
}
