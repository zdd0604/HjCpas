package com.hj.casps.entity.appordermoney;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/16.
 * 获取代付款订单列表
 */

public class QueryPayMoneyOrderForSettleEntity implements Serializable {
    private static final long serialVersionUID = 8718096391024199150L;

    /**
     * buyersId : testschool001
     * buyersName : 奥森学校
     * exePaymoneyNum : 240
     * goodsName : 糯米
     * id : f1018be9f1e14d78a131b77973056982
     * money : 1100
     * ordertitleCode : 913eb5f4a81a42cfaa3308e309defb4f
     * ordertitleNumber : 10038435
     * paymoneyNum : 480
     * sellersId : a844cb008f2643a4b5ec7d9fc664f1ce
     * sellersName : 北京尚德粮油商贸公司
     * workflowType : 2
     */

    private String buyersId;
    private String buyersName;
    private int exePaymoneyNum;
    private String goodsName;
    private String id;
    private int money;
    private String ordertitleCode;
    private int ordertitleNumber;
    private int paymoneyNum;
    private String sellersId;
    private String sellersName;
    private int workflowType;
    private boolean isCheck;//是否选中
    private String endPaymoneyNum;//结款师傅金额

    @Override
    public String toString() {
        return "QueryPayMoneyOrderForSettleEntity{" +
                "buyersId='" + buyersId + '\'' +
                ", buyersName='" + buyersName + '\'' +
                ", exePaymoneyNum=" + exePaymoneyNum +
                ", goodsName='" + goodsName + '\'' +
                ", id='" + id + '\'' +
                ", money=" + money +
                ", ordertitleCode='" + ordertitleCode + '\'' +
                ", ordertitleNumber=" + ordertitleNumber +
                ", paymoneyNum=" + paymoneyNum +
                ", sellersId='" + sellersId + '\'' +
                ", sellersName='" + sellersName + '\'' +
                ", workflowType=" + workflowType +
                ", isCheck=" + isCheck +
                ", endPaymoneyNum='" + endPaymoneyNum + '\'' +
                '}';
    }

    public String getEndPaymoneyNum() {
        return endPaymoneyNum;
    }

    public void setEndPaymoneyNum(String endPaymoneyNum) {
        this.endPaymoneyNum = endPaymoneyNum;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getBuyersId() {
        return buyersId;
    }

    public void setBuyersId(String buyersId) {
        this.buyersId = buyersId;
    }

    public String getBuyersName() {
        return buyersName;
    }

    public void setBuyersName(String buyersName) {
        this.buyersName = buyersName;
    }

    public int getExePaymoneyNum() {
        return exePaymoneyNum;
    }

    public void setExePaymoneyNum(int exePaymoneyNum) {
        this.exePaymoneyNum = exePaymoneyNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getOrdertitleCode() {
        return ordertitleCode;
    }

    public void setOrdertitleCode(String ordertitleCode) {
        this.ordertitleCode = ordertitleCode;
    }

    public int getOrdertitleNumber() {
        return ordertitleNumber;
    }

    public void setOrdertitleNumber(int ordertitleNumber) {
        this.ordertitleNumber = ordertitleNumber;
    }

    public int getPaymoneyNum() {
        return paymoneyNum;
    }

    public void setPaymoneyNum(int paymoneyNum) {
        this.paymoneyNum = paymoneyNum;
    }

    public String getSellersId() {
        return sellersId;
    }

    public void setSellersId(String sellersId) {
        this.sellersId = sellersId;
    }

    public String getSellersName() {
        return sellersName;
    }

    public void setSellersName(String sellersName) {
        this.sellersName = sellersName;
    }

    public int getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(int workflowType) {
        this.workflowType = workflowType;
    }
}
