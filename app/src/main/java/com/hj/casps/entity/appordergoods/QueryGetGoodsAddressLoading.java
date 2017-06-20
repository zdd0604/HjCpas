package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/4.
 * 查询待收货列表 提交JSON
 */

public class QueryGetGoodsAddressLoading implements Serializable {
    private static final long serialVersionUID = 1783213614848744565L;
    private String sys_token;//	string	令牌号
    private String sys_uuid;//	string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_name;//	string	用户名
    private String sys_member;//	string	会员id
    private String orderId;//string	订单号
    private String goodsName;//	string	商品名称
    private String startFirst;//	int	开始条数
    private String startEnd;//	int	展示条数

    public QueryGetGoodsAddressLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String orderId, String goodsName, String startFirst, String startEnd) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.orderId = orderId;
        this.goodsName = goodsName;
        this.startFirst = startFirst;
        this.startEnd = startEnd;
    }

    public QueryGetGoodsAddressLoading() {
    }

    public String getSys_token() {
        return sys_token;
    }

    public void setSys_token(String sys_token) {
        this.sys_token = sys_token;
    }

    public String getSys_uuid() {
        return sys_uuid;
    }

    public void setSys_uuid(String sys_uuid) {
        this.sys_uuid = sys_uuid;
    }

    public String getSys_func() {
        return sys_func;
    }

    public void setSys_func(String sys_func) {
        this.sys_func = sys_func;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public String getSys_member() {
        return sys_member;
    }

    public void setSys_member(String sys_member) {
        this.sys_member = sys_member;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStartFirst() {
        return startFirst;
    }

    public void setStartFirst(String startFirst) {
        this.startFirst = startFirst;
    }

    public String getStartEnd() {
        return startEnd;
    }

    public void setStartEnd(String startEnd) {
        this.startEnd = startEnd;
    }
}
