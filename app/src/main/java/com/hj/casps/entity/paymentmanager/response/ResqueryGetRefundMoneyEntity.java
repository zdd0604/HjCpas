package com.hj.casps.entity.paymentmanager.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/21.
 */
@Entity
public class ResqueryGetRefundMoneyEntity {


    /**
     * goodsName : 冷冻大肉
     * id : 29c13bd8e3c04a5a8aa1cb59be742a45
     * money : 800
     * ordertitleNumber : 10036804
     * paymoneyName : 长城商行
     * paymoneyTime : 1476854463000
     * remark :
     */
    private  boolean isChecked;
    private String goodsName;
    private String id;
    private int money;
    private  String ordertitleId;
    private int ordertitleNumber;
    private String paymoneyName;
    private long paymoneyTime;
    private String remark;
    @Generated(hash = 855057742)
    public ResqueryGetRefundMoneyEntity(boolean isChecked, String goodsName,
            String id, int money, String ordertitleId, int ordertitleNumber,
            String paymoneyName, long paymoneyTime, String remark) {
        this.isChecked = isChecked;
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
    public boolean getIsChecked() {
        return this.isChecked;
    }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
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
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }


}
