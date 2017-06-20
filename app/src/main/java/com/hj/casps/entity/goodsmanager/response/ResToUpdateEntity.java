package com.hj.casps.entity.goodsmanager.response;

import java.util.List;

/**商品编辑的实体类
 * Created by Administrator on 2017/5/21.
 */

public class ResToUpdateEntity {


    /**
     * goodsImages : [{"materialId":"8517870bb05f49debd04c44f1ddb7f97","materialPath":"/v2content/upload/img/newImg/XY/8517870bb05f49debd04c44f1ddb7f97.jpg","picPath":"/v2content/upload/img/newImg/XY/min/8517870bb05f49debd04c44f1ddb7f97.jpg"},{"materialId":"81277dffeef94b4a96cd925cb8b95144","materialPath":"/v2content/upload/img/newImg/AH/81277dffeef94b4a96cd925cb8b95144.jpg","picPath":"/v2content/upload/img/newImg/AH/min/81277dffeef94b4a96cd925cb8b95144.jpg"}]
     * goodsInfo : {"brand":"绿地","categoryId":"1002005001","categoryName":"水产鲜品","createAddress":"广东","createTime":1467216000000,"described":"绿色健康，无污染","factory":"绿的食品有限公司","imgId":"0ceb874c454c444cbc756a4939e824eb","imgPath":"/v2content/upload/img/newImg/GY/min/0ceb874c454c444cbc756a4939e824eb.jpg","maxPrice":25,"minPrice":20,"name":"罗非鱼","productNum":"","productTime":"7","specification":"1","status":0,"stockNum":2500,"unitPrice":"￥","unitSpecification":"1"}
     * return_code : 0
     * return_message : 成功
     */

    private GoodsInfoBean goodsInfo;
    private int return_code;
    private String return_message;
    private List<GoodsImagesBean> goodsImages;

    public GoodsInfoBean getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfoBean goodsInfo) {
        this.goodsInfo = goodsInfo;
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

    public List<GoodsImagesBean> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<GoodsImagesBean> goodsImages) {
        this.goodsImages = goodsImages;
    }

    public static class GoodsInfoBean {
        /**
         * brand : 绿地
         * categoryId : 1002005001
         * categoryName : 水产鲜品
         * createAddress : 广东
         * createTime : 1467216000000
         * described : 绿色健康，无污染
         * factory : 绿的食品有限公司
         * imgId : 0ceb874c454c444cbc756a4939e824eb
         * imgPath : /v2content/upload/img/newImg/GY/min/0ceb874c454c444cbc756a4939e824eb.jpg
         * maxPrice : 25
         * minPrice : 20
         * name : 罗非鱼
         * productNum :
         * productTime : 7
         * specification : 1
         * status : 0
         * stockNum : 2500
         * unitPrice : ￥
         * unitSpecification : 1
         */

        private String brand;
        private String categoryId;
        private String categoryName;
        private String createAddress;
        private long createTime;
        private String described;
        private String factory;
        private String imgId;
        private String imgPath;
        private double maxPrice;
        private double minPrice;
        private String name;
        private String productNum;
        private String productTime;
        private String specification;
        private int status;
        private int stockNum;
        private String unitPrice;
        private String unitSpecification;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCreateAddress() {
            return createAddress;
        }

        public void setCreateAddress(String createAddress) {
            this.createAddress = createAddress;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDescribed() {
            return described;
        }

        public void setDescribed(String described) {
            this.described = described;
        }

        public String getFactory() {
            return factory;
        }

        public void setFactory(String factory) {
            this.factory = factory;
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

        public void setMinPrice(int minPrice) {
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

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getUnitSpecification() {
            return unitSpecification;
        }

        public void setUnitSpecification(String unitSpecification) {
            this.unitSpecification = unitSpecification;
        }
    }

    public static class GoodsImagesBean {
        /**
         * materialId : 8517870bb05f49debd04c44f1ddb7f97
         * materialPath : /v2content/upload/img/newImg/XY/8517870bb05f49debd04c44f1ddb7f97.jpg
         * picPath : /v2content/upload/img/newImg/XY/min/8517870bb05f49debd04c44f1ddb7f97.jpg
         */

        private String materialId;
        private String materialPath;
        private String picPath;

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }

        public String getMaterialPath() {
            return materialPath;
        }

        public void setMaterialPath(String materialPath) {
            this.materialPath = materialPath;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }
    }
}
