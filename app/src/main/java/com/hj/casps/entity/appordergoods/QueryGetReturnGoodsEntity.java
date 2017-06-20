package com.hj.casps.entity.appordergoods;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Admin on 2017/5/19.
 * 收退货列表展示数据库
 */
@Entity
public class QueryGetReturnGoodsEntity implements Serializable {
    private static final long serialVersionUID = 1094524506563541876L;
    /**
     * address_list : [{"address":"nnnnn","id":"9411e91820294fde9a2d888863ce94cc"},{"address":"北京市朝阳区立水桥5号库","id":"ff3bce69cc094a0ca3488e833c40e5bb"}]
     * buyers_name : 奥森学校
     * exeGetreturngoodsNum : 22
     * getreturngoodsNum : 31
     * goodsName : 山东大米
     * goods_num : 100
     * orderId : 51934a2ec6c249508f5134dc3bbf66df
     * ordertitleCode : 3dff11627afd44a18152b93c0aee9008
     * ordertitleNumber : 10038708
     */
    private String buyers_name;
    private int exeGetreturngoodsNum;
    private int getreturngoodsNum;
    private String goodsName;
    private int goods_num;
    private String orderId;
    private String ordertitleCode;
    private int ordertitleNumber;
    private String address_list;
    @Generated(hash = 1972289682)
    public QueryGetReturnGoodsEntity(String buyers_name, int exeGetreturngoodsNum, int getreturngoodsNum, String goodsName, int goods_num, String orderId,
            String ordertitleCode, int ordertitleNumber, String address_list) {
        this.buyers_name = buyers_name;
        this.exeGetreturngoodsNum = exeGetreturngoodsNum;
        this.getreturngoodsNum = getreturngoodsNum;
        this.goodsName = goodsName;
        this.goods_num = goods_num;
        this.orderId = orderId;
        this.ordertitleCode = ordertitleCode;
        this.ordertitleNumber = ordertitleNumber;
        this.address_list = address_list;
    }
    @Generated(hash = 1294138045)
    public QueryGetReturnGoodsEntity() {
    }
    public String getBuyers_name() {
        return this.buyers_name;
    }
    public void setBuyers_name(String buyers_name) {
        this.buyers_name = buyers_name;
    }
    public int getExeGetreturngoodsNum() {
        return this.exeGetreturngoodsNum;
    }
    public void setExeGetreturngoodsNum(int exeGetreturngoodsNum) {
        this.exeGetreturngoodsNum = exeGetreturngoodsNum;
    }
    public int getGetreturngoodsNum() {
        return this.getreturngoodsNum;
    }
    public void setGetreturngoodsNum(int getreturngoodsNum) {
        this.getreturngoodsNum = getreturngoodsNum;
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
    public String getAddress_list() {
        return this.address_list;
    }
    public void setAddress_list(String address_list) {
        this.address_list = address_list;
    }

}
