package com.hj.casps.entity.appordergoods;

import java.util.List;

/**
 * 商品信息解析
 */
public class GoodsBack {

    /**
     * goodsImages : ["/v2content/upload/img/newImg/XY/8517870bb05f49debd04c44f1ddb7f97.jpg","/v2content/upload/img/newImg/AH/81277dffeef94b4a96cd925cb8b95144.jpg"]
     * goodsInfo : {"brand":"绿地","categoryId":"1002005001","categoryName":"水产鲜品","createAddress":"广东","createTime":1467216000000,"described":"绿色健康，无污染","factory":"绿的食品有限公司","imgPath":"/v2content/upload/img/newImg/GY/min/0ceb874c454c444cbc756a4939e824eb.jpg","maxPrice":25,"minPrice":20,"name":"罗非鱼","productNum":"","productTime":"7","specification":"1","status":0,"stockNum":2500,"unitPrice":"￥","unitSpecification":"1"}
     * return_code : 0
     * return_message : 成功
     */

    private GoodsInfoBean goodsInfo;
    private int return_code;
    private String return_message;
    private List<String> goodsImages;

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

    public List<String> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<String> goodsImages) {
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

        private double maxPrice;
        private double minPrice;

        public double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(int maxPrice) {
            this.maxPrice = maxPrice;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(int minPrice) {
            this.minPrice = minPrice;
        }

        @Override
        public String toString() {
            return "GoodsInfoBean{" +
                    "maxPrice=" + maxPrice +
                    ", minPrice=" + minPrice +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsBack{" +
                "goodsInfo=" + goodsInfo +
                ", return_code=" + return_code +
                ", return_message='" + return_message + '\'' +
                ", goodsImages=" + goodsImages +
                '}';
    }
}