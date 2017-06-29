package com.hj.casps.entity.appQuote;

/**
 * 返回值的基类
 */
public class CreateModel {

    /**
     * map : {"areaId":"1","categoryId":"1002004001","createName":"刘长城","createTime":1488297600000,"explan":"优惠价","goodsId":"b6aa48901e134341a63c24c00c7264e1","goodsName":"土鸡","id":"30038403","imgPath":"","maxPrice":22,"minPrice":20,"mmbId":"testshop001","mmbName":"长城商行","num":1000,"publishId":"a29d2326763546a4b0063c202cff08ff","publishName":"刘长城","rangType":1,"startEnd":1493568000000,"startTime":1488297600000,"status":0,"titlePic":"76c179ef3f2a4130824798041e8e7345","type":1,"unitPrice":"￥","userId":"a29d2326763546a4b0063c202cff08ff"}
     * return_code : 0
     * return_message : success
     */

    private MapBean map;
    private int return_code;
    private String return_message;

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
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

    public static class MapBean {
        /**
         * areaId : 1
         * categoryId : 1002004001
         * createName : 刘长城
         * createTime : 1488297600000
         * explan : 优惠价
         * goodsId : b6aa48901e134341a63c24c00c7264e1
         * goodsName : 土鸡
         * id : 30038403
         * imgPath :
         * maxPrice : 22
         * minPrice : 20
         * mmbId : testshop001
         * mmbName : 长城商行
         * num : 1000
         * publishId : a29d2326763546a4b0063c202cff08ff
         * publishName : 刘长城
         * rangType : 1
         * startEnd : 1493568000000
         * startTime : 1488297600000
         * status : 0
         * titlePic : 76c179ef3f2a4130824798041e8e7345
         * type : 1
         * unitPrice : ￥
         * userId : a29d2326763546a4b0063c202cff08ff
         */

        private String areaId;
        private String categoryId;
        private String createName;
        private long createTime;
        private String explan;
        private String goodsId;
        private String goodsName;
        private String id;
        private String imgPath;
        private double maxPrice;
        private double minPrice;
        private String mmbId;
        private String mmbName;
        private int num;
        private String publishId;
        private String publishName;
        private int rangType;
        private long startEnd;
        private long startTime;
        private int status;
        private String titlePic;
        private int type;
        private String unitPrice;
        private String userId;

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getExplan() {
            return explan;
        }

        public void setExplan(String explan) {
            this.explan = explan;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

        public String getMmbId() {
            return mmbId;
        }

        public void setMmbId(String mmbId) {
            this.mmbId = mmbId;
        }

        public String getMmbName() {
            return mmbName;
        }

        public void setMmbName(String mmbName) {
            this.mmbName = mmbName;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPublishId() {
            return publishId;
        }

        public void setPublishId(String publishId) {
            this.publishId = publishId;
        }

        public String getPublishName() {
            return publishName;
        }

        public void setPublishName(String publishName) {
            this.publishName = publishName;
        }

        public int getRangType() {
            return rangType;
        }

        public void setRangType(int rangType) {
            this.rangType = rangType;
        }

        public long getStartEnd() {
            return startEnd;
        }

        public void setStartEnd(long startEnd) {
            this.startEnd = startEnd;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitlePic() {
            return titlePic;
        }

        public void setTitlePic(String titlePic) {
            this.titlePic = titlePic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}