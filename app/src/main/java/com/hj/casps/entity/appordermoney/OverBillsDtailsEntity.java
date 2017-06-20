package com.hj.casps.entity.appordermoney;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/4/27.
 * 创建结款单以及目录
 */

public class OverBillsDtailsEntity implements Serializable {
    private static final long serialVersionUID = 1886022026032428490L;
    private String buyersName;//		string		付款
    private String sellersName;//	string	收款
    private String buyersId;//		string	付款账号
    private String sellersId;//	string	收款账号
    private String ctrTime;//	date		约定结款时间

    public String getBuyersName() {
        return buyersName;
    }

    public void setBuyersName(String buyersName) {
        this.buyersName = buyersName;
    }

    public String getSellersName() {
        return sellersName;
    }

    public void setSellersName(String sellersName) {
        this.sellersName = sellersName;
    }

    public String getBuyersId() {
        return buyersId;
    }

    public void setBuyersId(String buyersId) {
        this.buyersId = buyersId;
    }

    public String getSellersId() {
        return sellersId;
    }

    public void setSellersId(String sellersId) {
        this.sellersId = sellersId;
    }

    public String getCtrTime() {
        return ctrTime;
    }

    public void setCtrTime(String ctrTime) {
        this.ctrTime = ctrTime;
    }

    public OverBillsDtailsEntity() {
    }

    public OverBillsDtailsEntity(String buyersName, String sellersName, String buyersId, String sellersId, String ctrTime) {
        this.buyersName = buyersName;
        this.sellersName = sellersName;
        this.buyersId = buyersId;
        this.sellersId = sellersId;
        this.ctrTime = ctrTime;
    }
}
