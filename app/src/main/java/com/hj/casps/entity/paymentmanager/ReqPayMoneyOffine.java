package com.hj.casps.entity.paymentmanager;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ReqPayMoneyOffine {

    private String id;
    private String getPayRemark;
    private String payMoneyCode;
    private String num;
    private String goodName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGetPayRemark() {
        return getPayRemark;
    }

    public void setGetPayRemark(String getPayRemark) {
        this.getPayRemark = getPayRemark;
    }

    public String getPayMoneyCode() {
        return payMoneyCode;
    }

    public void setPayMoneyCode(String payMoneyCode) {
        this.payMoneyCode = payMoneyCode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }


    @Override
    public String toString() {
        return "ReqPayMoneyOffine{" +
                "id='" + id + '\'' +
                ", getPayRemark='" + getPayRemark + '\'' +
                ", payMoneyCode='" + payMoneyCode + '\'' +
                ", num='" + num + '\'' +
                ", goodName='" + goodName + '\'' +
                '}';
    }
}
