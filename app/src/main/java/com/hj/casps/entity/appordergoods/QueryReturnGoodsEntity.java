package com.hj.casps.entity.appordergoods;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/4.
 * 查询退货列表 返回参数
 */
@Entity
public class QueryReturnGoodsEntity implements Serializable {

    private static final long serialVersionUID = 5824666278497722785L;

    @Transient
    private boolean isCheck;
    @Transient
    private String num; //本次退货数量
    @Transient
    private String address;//退货地址
    @Transient
    private String addressid;//退货地址

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public int getExe_returngoods_num() {
        return this.exe_returngoods_num;
    }

    public void setExe_returngoods_num(int exe_returngoods_num) {
        this.exe_returngoods_num = exe_returngoods_num;
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

    public String getSellers_name() {
        return this.sellers_name;
    }

    public void setSellers_name(String sellers_name) {
        this.sellers_name = sellers_name;
    }

    /**
     * exe_returngoods_num : 130
     * goodsName : 放心大米
     * goods_num : 130
     * orderId : f01927dbc3f94ed8a5acd259b982c935
     * ordertitleCode : 3e36dc337b3446b782416162c9c33f5e
     * ordertitleNumber : 10038441
     * sellers_name : 北京尚德粮油商贸公司
     */

    private int exe_returngoods_num;
    private String goodsName;
    private int goods_num;
    private String orderId;
    private String ordertitleCode;
    private int ordertitleNumber;
    private String sellers_name;

    @Generated(hash = 1367362024)
    public QueryReturnGoodsEntity(int exe_returngoods_num, String goodsName,
                                  int goods_num, String orderId, String ordertitleCode,
                                  int ordertitleNumber, String sellers_name) {
        this.exe_returngoods_num = exe_returngoods_num;
        this.goodsName = goodsName;
        this.goods_num = goods_num;
        this.orderId = orderId;
        this.ordertitleCode = ordertitleCode;
        this.ordertitleNumber = ordertitleNumber;
        this.sellers_name = sellers_name;
    }

    @Generated(hash = 419095103)
    public QueryReturnGoodsEntity() {
    }

    public void clearData() {
        this.num = ""; //本次退货数量
    }

    @Override
    public String toString() {
        return "QueryReturnGoodsEntity{" +
                "isCheck=" + isCheck +
                ", num='" + num + '\'' +
                ", address='" + address + '\'' +
                ", addressid='" + addressid + '\'' +
                ", exe_returngoods_num=" + exe_returngoods_num +
                ", goodsName='" + goodsName + '\'' +
                ", goods_num=" + goods_num +
                ", orderId='" + orderId + '\'' +
                ", ordertitleCode='" + ordertitleCode + '\'' +
                ", ordertitleNumber=" + ordertitleNumber +
                ", sellers_name='" + sellers_name + '\'' +
                '}';
    }
}
