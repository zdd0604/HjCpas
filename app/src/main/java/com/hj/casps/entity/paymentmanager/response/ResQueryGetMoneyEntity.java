package com.hj.casps.entity.paymentmanager.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/20.
 */
@Entity
public class ResQueryGetMoneyEntity {
    @Transient
    private boolean isChecked;
    /**
     * goodsName : 水果
     * id : 379f82b779154f6184b24fb7c18b51ea
     * money : 2
     * ordertitleId : 6837722b629943fbbacd2b16afe05afb
     * ordertitleNumber : 10002241
     * paymoneyName : 合肥市上好食品贸易公司
     * paymoneyTime : 1499045928000
     * remark : 破坏民工
     */

    private String goodsName;
    private String id;
    private double money;
    private String ordertitleId;
    private int ordertitleNumber;
    private String paymoneyName;
    private long paymoneyTime;
    private String remark;

    @Generated(hash = 1408055739)
    public ResQueryGetMoneyEntity(String goodsName, String id, double money,
            String ordertitleId, int ordertitleNumber, String paymoneyName,
            long paymoneyTime, String remark) {
        this.goodsName = goodsName;
        this.id = id;
        this.money = money;
        this.ordertitleId = ordertitleId;
        this.ordertitleNumber = ordertitleNumber;
        this.paymoneyName = paymoneyName;
        this.paymoneyTime = paymoneyTime;
        this.remark = remark;
    }
    @Generated(hash = 652790676)
    public ResQueryGetMoneyEntity() {
    }

    public boolean getIsChecked() {
        return isChecked;
    }
    public void setIsChecked(boolean checked) {
        isChecked = checked;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOrdertitleId() {
        return ordertitleId;
    }

    public void setOrdertitleId(String ordertitleId) {
        this.ordertitleId = ordertitleId;
    }

    public int getOrdertitleNumber() {
        return ordertitleNumber;
    }

    public void setOrdertitleNumber(int ordertitleNumber) {
        this.ordertitleNumber = ordertitleNumber;
    }

    public String getPaymoneyName() {
        return paymoneyName;
    }

    public void setPaymoneyName(String paymoneyName) {
        this.paymoneyName = paymoneyName;
    }

    public long getPaymoneyTime() {
        return paymoneyTime;
    }

    public void setPaymoneyTime(long paymoneyTime) {
        this.paymoneyTime = paymoneyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
