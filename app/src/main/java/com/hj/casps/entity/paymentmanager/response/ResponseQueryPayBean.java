package com.hj.casps.entity.paymentmanager.response;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/16.
 */
@Entity
public class ResponseQueryPayBean  {

    /**
     * accountlist : [{"accountno":"12312","bankname":"123123","id":"4d4c7214e64a4dc696de9d5edc168dfd"},{"accountno":"622156561677611","bankname":"农业银行","id":"e2084e95335243c29f5903565d379610"},{"accountno":"6222189929039864012","bankname":"中国建设银行","id":"fc992fffb07442339239a25c521b7ed5"}]
     * exePaymoneyNum : 15995
     * goodsName : 油菜
     * id : 7c63a11218124fecb5fef58e2ea3b922
     * money : 15996
     * ordertitleId : e5805c08f2d047569f307549dc7783ae
     * ordertitleNumber : 10036100
     * paymoneyNum : 1
     * sellersName : cyh
     */
    //是否选中
    private boolean isChecked;
    private double exePaymoneyNum;
    private String goodsName;
    private String id;
    private int money;
    private String ordertitleId;
    private int ordertitleNumber;
    private double paymoneyNum;
    private String sellersName;
    private String accountlist;

    @Generated(hash = 77736808)
    public ResponseQueryPayBean(boolean isChecked, double exePaymoneyNum, String goodsName, String id, int money, String ordertitleId, int ordertitleNumber, double paymoneyNum, String sellersName, String accountlist) {
        this.isChecked = isChecked;
        this.exePaymoneyNum = exePaymoneyNum;
        this.goodsName = goodsName;
        this.id = id;
        this.money = money;
        this.ordertitleId = ordertitleId;
        this.ordertitleNumber = ordertitleNumber;
        this.paymoneyNum = paymoneyNum;
        this.sellersName = sellersName;
        this.accountlist = accountlist;
    }

    @Generated(hash = 227151661)
    public ResponseQueryPayBean() {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public double getExePaymoneyNum() {
        return exePaymoneyNum;
    }

    public void setExePaymoneyNum(double exePaymoneyNum) {
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

    public double getPaymoneyNum() {
        return paymoneyNum;
    }

    public void setPaymoneyNum(double paymoneyNum) {
        this.paymoneyNum = paymoneyNum;
    }

    public String getSellersName() {
        return sellersName;
    }

    public void setSellersName(String sellersName) {
        this.sellersName = sellersName;
    }

    public String getAccountlist() {
        return accountlist;
    }

    public void setAccountlist(String accountlist) {
        this.accountlist = accountlist;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
