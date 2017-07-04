package com.hj.casps.entity.appquote;

import java.util.List;

/**
 * 返回类
 */
public class ChooseGoodsBack {


    /**
     * mtCount : 75
     * mtList :
     * return_code : 0
     * return_message : success
     */

    private int mtCount;
    private int return_code;
    private String return_message;
    private List<MtListBean> mtList;

    public int getMtCount() {
        return mtCount;
    }

    public void setMtCount(int mtCount) {
        this.mtCount = mtCount;
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public List<MtListBean> getMtList() {
        return mtList;
    }

    public void setMtList(List<MtListBean> mtList) {
        this.mtList = mtList;
    }

    public static class MtListBean {
        /**
         * brand :
         * createAddress : 合肥
         * factory :
         * goodsId : 00743204dbd54a48a81642ad38933d5e
         * imgId : 84f9f0c70e774824b99670c2256b7fc1
         * imgPath : /img/newImg/SX/min/84f9f0c70e774824b99670c2256b7fc1.jpg
         * maxPrice : 20
         * minPrice : 10
         * name : 白菜3号
         * productNum :
         * productTime : 3
         * status : 0
         */

        private String brand;
        private String createAddress;
        private String factory;
        private String goodsId;
        private String imgId;
        private String imgPath;
        private double maxPrice;
        private double minPrice;
        private String name;
        private String productNum;
        private String productTime;
        private int status;
        private boolean isChoose;

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCreateAddress() {
            return createAddress;
        }

        public void setCreateAddress(String createAddress) {
            this.createAddress = createAddress;
        }

        public String getFactory() {
            return factory;
        }

        public void setFactory(String factory) {
            this.factory = factory;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProductNum() {
            return productNum;
        }

        public void setProductNum(String productNum) {
            this.productNum = productNum;
        }

        public String getProductTime() {
            return productTime;
        }

        public void setProductTime(String productTime) {
            this.productTime = productTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "MtListBean{" +
                    "brand='" + brand + '\'' +
                    ", createAddress='" + createAddress + '\'' +
                    ", factory='" + factory + '\'' +
                    ", goodsId='" + goodsId + '\'' +
                    ", imgId='" + imgId + '\'' +
                    ", imgPath='" + imgPath + '\'' +
                    ", maxPrice=" + maxPrice +
                    ", minPrice=" + minPrice +
                    ", name='" + name + '\'' +
                    ", productNum='" + productNum + '\'' +
                    ", productTime='" + productTime + '\'' +
                    ", status=" + status +
                    ", isChoose=" + isChoose +
                    '}';
        }
    }
}