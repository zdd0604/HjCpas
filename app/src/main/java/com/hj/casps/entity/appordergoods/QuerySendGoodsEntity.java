package com.hj.casps.entity.appordergoods;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Admin on 2017/5/4.
 * 查询发货列表返回参数
 */
@Entity
public class QuerySendGoodsEntity implements Serializable {
    private static final long serialVersionUID = -2452691325298468573L;

    
    @Transient
    private String num;//我要发货的数量
    @Transient
    private boolean isCheck;
    /**
     * buyers_name : bjjtdx
     * exe_sendgoods_num : 6
     * goodsName : 时令青菜
     * goods_num : 20
     * orderId : 286ca97c562645be8e8cbabbfb4cda85
     * ordertitleCode : b6abe08e20b349aeb343dea3a0c64d73
     * ordertitleNumber : 10037706
     * sendgoods_num : 14
     */

    private String buyers_name;
    private int exe_sendgoods_num;
    private String goodsName;
    private int goods_num;
    private String orderId;
    private String ordertitleCode;
    private int ordertitleNumber;
    private int sendgoods_num;

    @Generated(hash = 1942788625)
    public QuerySendGoodsEntity(String buyers_name, int exe_sendgoods_num,
            String goodsName, int goods_num, String orderId, String ordertitleCode,
            int ordertitleNumber, int sendgoods_num) {
        this.buyers_name = buyers_name;
        this.exe_sendgoods_num = exe_sendgoods_num;
        this.goodsName = goodsName;
        this.goods_num = goods_num;
        this.orderId = orderId;
        this.ordertitleCode = ordertitleCode;
        this.ordertitleNumber = ordertitleNumber;
        this.sendgoods_num = sendgoods_num;
    }

    @Generated(hash = 1659582190)
    public QuerySendGoodsEntity() {
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getBuyers_name() {
        return this.buyers_name;
    }

    public void setBuyers_name(String buyers_name) {
        this.buyers_name = buyers_name;
    }

    public int getExe_sendgoods_num() {
        return this.exe_sendgoods_num;
    }

    public void setExe_sendgoods_num(int exe_sendgoods_num) {
        this.exe_sendgoods_num = exe_sendgoods_num;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoods_num() {
        return this.goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrdertitleCode() {
        return this.ordertitleCode;
    }

    public void setOrdertitleCode(String ordertitleCode) {
        this.ordertitleCode = ordertitleCode;
    }

    public int getOrdertitleNumber() {
        return this.ordertitleNumber;
    }

    public void setOrdertitleNumber(int ordertitleNumber) {
        this.ordertitleNumber = ordertitleNumber;
    }

    public int getSendgoods_num() {
        return this.sendgoods_num;
    }

    public void setSendgoods_num(int sendgoods_num) {
        this.sendgoods_num = sendgoods_num;
    }

    public void clearData() {
        this.num = ""; //本次退货数量
    }

    @Override
    public String toString() {
        return "QuerySendGoodsEntity{" +
                "num='" + num + '\'' +
                ", isCheck=" + isCheck +
                ", buyers_name='" + buyers_name + '\'' +
                ", exe_sendgoods_num=" + exe_sendgoods_num +
                ", goodsName='" + goodsName + '\'' +
                ", goods_num=" + goods_num +
                ", orderId='" + orderId + '\'' +
                ", ordertitleCode='" + ordertitleCode + '\'' +
                ", ordertitleNumber=" + ordertitleNumber +
                ", sendgoods_num=" + sendgoods_num +
                '}';
    }
}
