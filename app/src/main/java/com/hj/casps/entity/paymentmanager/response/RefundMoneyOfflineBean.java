package com.hj.casps.entity.paymentmanager.response;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/20.
 */
@Entity
public class RefundMoneyOfflineBean {
    private String buyersName;
    private double exeRefundNum;
    private String goodsName;
    private String id;
    private String ordertitleId;
    private int ordertitleNumber;
    private String accountlist;
    @Generated(hash = 466176072)
    public RefundMoneyOfflineBean(String buyersName, double exeRefundNum,
            String goodsName, String id, String ordertitleId, int ordertitleNumber,
            String accountlist) {
        this.buyersName = buyersName;
        this.exeRefundNum = exeRefundNum;
        this.goodsName = goodsName;
        this.id = id;
        this.ordertitleId = ordertitleId;
        this.ordertitleNumber = ordertitleNumber;
        this.accountlist = accountlist;
    }
    @Generated(hash = 1947975896)
    public RefundMoneyOfflineBean() {
    }
    public String getBuyersName() {
        return this.buyersName;
    }
    public void setBuyersName(String buyersName) {
        this.buyersName = buyersName;
    }
    public double getExeRefundNum() {
        return this.exeRefundNum;
    }
    public void setExeRefundNum(double exeRefundNum) {
        this.exeRefundNum = exeRefundNum;
    }
    public String getGoodsName() {
        return this.goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrdertitleId() {
        return this.ordertitleId;
    }
    public void setOrdertitleId(String ordertitleId) {
        this.ordertitleId = ordertitleId;
    }
    public int getOrdertitleNumber() {
        return this.ordertitleNumber;
    }
    public void setOrdertitleNumber(int ordertitleNumber) {
        this.ordertitleNumber = ordertitleNumber;
    }
    public String getAccountlist() {
        return this.accountlist;
    }
    public void setAccountlist(String accountlist) {
        this.accountlist = accountlist;
    }



}
