package com.hj.casps.entity.paymentmanager.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/21.
 * 收退款
 */
@Entity
public class ResqueryGetRefundMoneyEntity {
    @Transient
    private  boolean isChecked;

    /**
     * goodsName : 散装农场青菜
     * id : c6c2f5c11b4e44c69b4443c42d9c1516
     * money : 1.0
     * ordertitleId : 82469a58cdaa45d7a8f6a24f09fe36aa
     * ordertitleNumber : 10039016
     * paymoneyName : 长城商行
     * paymoneyTime : 1498963761000
     * remark : 已付款1元
     */

    private String goodsName;
    private String id;
    private double money;
    private String ordertitleId;
    private int ordertitleNumber;
    private String paymoneyName;
    private long paymoneyTime;
    private String remark;

    @Generated(hash = 94600498)
    public ResqueryGetRefundMoneyEntity(String goodsName, String id, double money,
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

    @Generated(hash = 1891069808)
    public ResqueryGetRefundMoneyEntity() {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
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
