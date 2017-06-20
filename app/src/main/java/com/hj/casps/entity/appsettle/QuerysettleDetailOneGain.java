package com.hj.casps.entity.appsettle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/15.
 * 获取结款单详情
 * 一级列表详情
 */

public class QuerysettleDetailOneGain implements Serializable {


    private static final long serialVersionUID = 6489711399513927795L;
    /**
     * ctrMoney : 10
     * ctrTime : 1493827200000
     * list : [{"goodsName":"油菜","money":1200,"ordermoney":1200,"oredertitleNumber":10039015}]
     * mmbgetName : 长城商行
     * mmbpayName : 北京交通大学
     * return_code : 0
     * return_message : 查询成功
     * settleCode : 40039117
     * settleMoney : 1200
     * status : 3
     * statusRegist : 1
     * statusSingn : 1
     */

    private double ctrMoney;
    private long ctrTime;
    private String mmbgetName;
    private String mmbpayName;
    private int return_code;
    private String return_message;
    private int settleCode;
    private double settleMoney;
    private int status;
    private int statusRegist;
    private int statusSingn;
    private List<ListBean> list;

    public double getCtrMoney() {
        return ctrMoney;
    }

    public void setCtrMoney(double ctrMoney) {
        this.ctrMoney = ctrMoney;
    }

    public long getCtrTime() {
        return ctrTime;
    }

    public void setCtrTime(long ctrTime) {
        this.ctrTime = ctrTime;
    }

    public String getMmbgetName() {
        return mmbgetName;
    }

    public void setMmbgetName(String mmbgetName) {
        this.mmbgetName = mmbgetName;
    }

    public String getMmbpayName() {
        return mmbpayName;
    }

    public void setMmbpayName(String mmbpayName) {
        this.mmbpayName = mmbpayName;
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public int getSettleCode() {
        return settleCode;
    }

    public void setSettleCode(int settleCode) {
        this.settleCode = settleCode;
    }

    public double getSettleMoney() {
        return settleMoney;
    }

    public void setSettleMoney(double settleMoney) {
        this.settleMoney = settleMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatusRegist() {
        return statusRegist;
    }

    public void setStatusRegist(int statusRegist) {
        this.statusRegist = statusRegist;
    }

    public int getStatusSingn() {
        return statusSingn;
    }

    public void setStatusSingn(int statusSingn) {
        this.statusSingn = statusSingn;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * goodsName : 油菜
         * money : 1200
         * ordermoney : 1200
         * oredertitleNumber : 10039015
         */

        private String goodsName;
        private double money;
        private double ordermoney;
        private int oredertitleNumber;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public double getOrdermoney() {
            return ordermoney;
        }

        public void setOrdermoney(double ordermoney) {
            this.ordermoney = ordermoney;
        }

        public int getOredertitleNumber() {
            return oredertitleNumber;
        }

        public void setOredertitleNumber(int oredertitleNumber) {
            this.oredertitleNumber = oredertitleNumber;
        }
    }
}
