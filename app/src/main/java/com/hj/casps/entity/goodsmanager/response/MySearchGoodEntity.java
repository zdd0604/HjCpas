package com.hj.casps.entity.goodsmanager.response;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class MySearchGoodEntity {

    /**
     * dataList : [{"goodsId":"ad70a62a65b54bebbc297821ab24fc8e","imgPath":"/v2content/uploadnull","name":"mingc","status":0},{"goodsId":"f71aaf1730b441cf9cf4c52ec86e4fdc","imgPath":"/v2content/upload/v2content/upload/img/newImg/ME/min/59980ac183f54bbea79c0c7c29fbc672.jpg","name":"wo","status":0}]
     * pageno : 1
     * pagesize : 5
     * return_code : 0
     * return_message : 成功
     * totalCount : 2
     * totalPage : 1
     */

    private int pageno;
    private int pagesize;
    private int return_code;
    private String return_message;
    private int totalCount;
    private int totalPage;
    private List<DataListBean> dataList;

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * goodsId : ad70a62a65b54bebbc297821ab24fc8e
         * imgPath : /v2content/uploadnull
         * name : mingc
         * status : 0
         */

        private String goodsId;
        private String imgPath;
        private String name;
        private int status;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
