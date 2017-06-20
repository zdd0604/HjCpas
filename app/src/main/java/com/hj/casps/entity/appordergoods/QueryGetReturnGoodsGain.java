package com.hj.casps.entity.appordergoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/4.
 * 查询收退货列表 返回参数
 */

public class QueryGetReturnGoodsGain implements Serializable {

    private static final long serialVersionUID = 3963108219954518538L;

    private boolean isCheck;
    private String num; //本次退货数量
    private String addressName;//退货地址
    private String addressId; //选择后的地址ID
    public void clearData() {
        this.num = ""; //本次退货数量
    }
    /**
     * address_list : [{"address":"111","id":"2118a6bd011e437eb1ac7d12ccc3cafe"},{"address":"我可以","id":"78d5b989f2f3436f9b871a4756ac0657"},{"address":"北京通州大街小巷","id":"7ecfbd02b40f4d63adbaa527823f4ea1"},{"address":"就咯啦咯啦","id":"9aad3093e5114341a77958ef07e6a27d"},{"address":"图兔兔","id":"a6136995f3c64117b5c4260e1994c162"},{"address":"经济开发区田都路101号","id":"b1f52b0e8d8644f29263b73aa7568161"},{"address":"nnnn","id":"c260c9e3ad674a07b95571be0c9ab50d"},{"address":"高新技术开发区","id":"c66016403fc04c5f8a9ae909efc8a164"},{"address":"1234","id":"c8dbc362ce4c443dadd0d1d7d0a06981"},{"address":"北京市海定区","id":"cb1fbcfeafff41a5b0594f8e20ea92f4"},{"address":"经济开发区","id":"d641b5b2cd554a2f91cc4974b698d184"},{"address":"Haerbin","id":"df32d5d53e324e52b42bd3ee597a9f32"},{"address":"hefei","id":"df9d103526b0484f8957f998ae43551a"},{"address":"北京市海淀区中关村大街11号财富E世界A座12层","id":"e043abcb33424c1e85bf88ec738ff269"},{"address":"仓库1","id":"f11f56fb24f142408827472221b12139"}]
     * buyers_name : 奥森学校
     * exeGetreturngoodsNum : 90
     * getreturngoodsNum : 90
     * goodsName : 散装农场青菜
     * goods_num : 280
     * orderId : 71b9df408dd646588b9da79a919f8b8a
     * ordertitleCode : 2e3403cf4f60417694a55c6ba941b817
     * ordertitleNumber : 10038424
     */

    private String buyers_name;
    private int exeGetreturngoodsNum;
    private int getreturngoodsNum;
    private String goodsName;
    private int goods_num;
    private String orderId;
    private String ordertitleCode;
    private int ordertitleNumber;
    private List<AddressListBean> address_list;

    public String getBuyers_name() {
        return buyers_name;
    }

    public void setBuyers_name(String buyers_name) {
        this.buyers_name = buyers_name;
    }

    public int getExeGetreturngoodsNum() {
        return exeGetreturngoodsNum;
    }

    public void setExeGetreturngoodsNum(int exeGetreturngoodsNum) {
        this.exeGetreturngoodsNum = exeGetreturngoodsNum;
    }

    public int getGetreturngoodsNum() {
        return getreturngoodsNum;
    }

    public void setGetreturngoodsNum(int getreturngoodsNum) {
        this.getreturngoodsNum = getreturngoodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrdertitleCode() {
        return ordertitleCode;
    }

    public void setOrdertitleCode(String ordertitleCode) {
        this.ordertitleCode = ordertitleCode;
    }

    public int getOrdertitleNumber() {
        return ordertitleNumber;
    }

    public void setOrdertitleNumber(int ordertitleNumber) {
        this.ordertitleNumber = ordertitleNumber;
    }

    public List<AddressListBean> getAddress_list() {
        return address_list;
    }

    public void setAddress_list(List<AddressListBean> address_list) {
        this.address_list = address_list;
    }

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

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public static class AddressListBean {
        /**
         * address : 111
         * id : 2118a6bd011e437eb1ac7d12ccc3cafe
         */

        private String address;
        private String id;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
