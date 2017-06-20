package com.hj.casps.entity.appordermoney;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/16.
 * 获取代付款订单列表
 */

public class QueryPayMoneyOrderForSettleLoading implements Serializable {
    private static final long serialVersionUID = 1717065094925407288L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//	string	会员id
    private String buyersId;//string	付款方id
    private String sellersId;//string		收款方id
    private String goodsName;//string	商品名
    private String orderId;//string	订单号

    public QueryPayMoneyOrderForSettleLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String buyersId, String sellersId, String goodsName, String orderId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.buyersId = buyersId;
        this.sellersId = sellersId;
        this.goodsName = goodsName;
        this.orderId = orderId;
    }

    public QueryPayMoneyOrderForSettleLoading() {
    }
}
