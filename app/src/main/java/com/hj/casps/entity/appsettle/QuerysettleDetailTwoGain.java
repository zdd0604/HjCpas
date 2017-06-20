package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/15.
 * 获取结款单详情
 * 二级列表 商品清单
 */

public class QuerysettleDetailTwoGain implements Serializable {
    private static final long serialVersionUID = -6673249736484865936L;
    private String oredertitleNumber;//	string	订单号
    private String goodsName;//string	商品名称
    private double ordermoney;//	double	商品金额
    private double money;//	double	实际结款金额

    public String getOredertitleNumber() {
        return oredertitleNumber;
    }

    public void setOredertitleNumber(String oredertitleNumber) {
        this.oredertitleNumber = oredertitleNumber;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getOrdermoney() {
        return ordermoney;
    }

    public void setOrdermoney(double ordermoney) {
        this.ordermoney = ordermoney;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public QuerysettleDetailTwoGain(String oredertitleNumber, String goodsName, double ordermoney, double money) {
        this.oredertitleNumber = oredertitleNumber;
        this.goodsName = goodsName;
        this.ordermoney = ordermoney;
        this.money = money;
    }

    public QuerysettleDetailTwoGain() {
    }
}
