package com.hj.casps.entity.appordergoods;

import java.io.Serializable;
import java.util.List;

public class GetGoodsEntity implements Serializable {
        private static final long serialVersionUID = -8419382535385414036L;
        private int total;//	int	总条数
        private String ordertitleNumber;//订单号
        private String sellersName;//发货方
        private String goodsName;//商品方
        private String id;//	string	订单id
        private String getgoodsNum;//已收
        private String exeGetgoodsNum;//待收数量
        private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private List<AddressList> addressList;

        public GetGoodsEntity(int total, String ordertitleNumber, String sellersName, String goodsName, String id, String getgoodsNum, String exeGetgoodsNum, List<AddressList> addressList) {
            this.total = total;
            this.ordertitleNumber = ordertitleNumber;
            this.sellersName = sellersName;
            this.goodsName = goodsName;
            this.id = id;
            this.getgoodsNum = getgoodsNum;
            this.exeGetgoodsNum = exeGetgoodsNum;
            this.addressList = addressList;
        }

        public GetGoodsEntity() {
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getOrdertitleNumber() {
            return ordertitleNumber;
        }

        public void setOrdertitleNumber(String ordertitleNumber) {
            this.ordertitleNumber = ordertitleNumber;
        }

        public String getSellersName() {
            return sellersName;
        }

        public void setSellersName(String sellersName) {
            this.sellersName = sellersName;
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

        public String getGetgoodsNum() {
            return getgoodsNum;
        }

        public void setGetgoodsNum(String getgoodsNum) {
            this.getgoodsNum = getgoodsNum;
        }

        public String getExeGetgoodsNum() {
            return exeGetgoodsNum;
        }

        public void setExeGetgoodsNum(String exeGetgoodsNum) {
            this.exeGetgoodsNum = exeGetgoodsNum;
        }

        public List<AddressList> getAddressList() {
            return addressList;
        }

        public void setAddressList(List<AddressList> addressList) {
            this.addressList = addressList;
        }

        public static class AddressList implements Serializable {
            private static final long serialVersionUID = 1659524184406669342L;
            private String address;//string	收货地址

            public AddressList(String address) {
                this.address = address;
            }

            public AddressList() {
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }