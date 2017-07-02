package com.hj.casps.entity.paymentmanager.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/20.
 */
@Entity
public class ResQueryGetMoneyEntity {
    /**
     * goodsName : 散装农场青菜
     * id : 2b03575b364b4466bd9099a732ea8bad
     * money : 155
     * ordertitleId : 82469a58cdaa45d7a8f6a24f09fe36aa
     * ordertitleNumber : 10039016
     * paymoneyName : 奥森学校
     * paymoneyTime : 1497863399000
     */
//    private String goodsName;
//    private String id;
//    private double money;
//    private String ordertitleId;
//    private int ordertitleNumber;
//    private String paymoneyName;
//    private long paymoneyTime;
//    @Transient
//    private boolean isChecked;
    private String goodsName;
    private String id;
    private int money;
    private String ordertitleId;
    private int ordertitleNumber;
    private String paymoneyName;
    private long paymoneyTime;
    @Transient
    private boolean isChecked;

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        isChecked = checked;
    }

    @Generated(hash = 252814618)
    public ResQueryGetMoneyEntity(String goodsName, String id, int money,
            String ordertitleId, int ordertitleNumber, String paymoneyName,
            long paymoneyTime) {
        this.goodsName = goodsName;
        this.id = id;
        this.money = money;
        this.ordertitleId = ordertitleId;
        this.ordertitleNumber = ordertitleNumber;
        this.paymoneyName = paymoneyName;
        this.paymoneyTime = paymoneyTime;
    }
    @Generated(hash = 652790676)
    public ResQueryGetMoneyEntity() {
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
    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
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
    public String getPaymoneyName() {
        return this.paymoneyName;
    }
    public void setPaymoneyName(String paymoneyName) {
        this.paymoneyName = paymoneyName;
    }
    public long getPaymoneyTime() {
        return this.paymoneyTime;
    }
    public void setPaymoneyTime(long paymoneyTime) {
        this.paymoneyTime = paymoneyTime;
    }

    @Override
    public String toString() {
        return "ResQueryGetMoneyEntity{" +
                "goodsName='" + goodsName + '\'' +
                ", id='" + id + '\'' +
                ", money=" + money +
                ", ordertitleId='" + ordertitleId + '\'' +
                ", ordertitleNumber=" + ordertitleNumber +
                ", paymoneyName='" + paymoneyName + '\'' +
                ", paymoneyTime=" + paymoneyTime +
                ", isChecked=" + isChecked +
                '}';
    }
}
