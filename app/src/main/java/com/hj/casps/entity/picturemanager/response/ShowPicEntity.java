package com.hj.casps.entity.picturemanager.response;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ShowPicEntity {

    /**
     * dataList : [{"materialId":"62a19e33193f472c9ccd85fc299c0df2","materialPath":"/v2content/upload/img/newImg/PN/62a19e33193f472c9ccd85fc299c0df2.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/PN/min/62a19e33193f472c9ccd85fc299c0df2.jpg"},{"materialId":"ca5d4c0a1f1949299ccbf6e4cec154e7","materialPath":"/v2content/upload/img/newImg/KG/ca5d4c0a1f1949299ccbf6e4cec154e7.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/KG/min/ca5d4c0a1f1949299ccbf6e4cec154e7.jpg"},{"materialId":"ae6354194fe542da97f7e5ea7d1499d2","materialPath":"/v2content/upload/img/newImg/XC/ae6354194fe542da97f7e5ea7d1499d2.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/XC/min/ae6354194fe542da97f7e5ea7d1499d2.jpg"},{"materialId":"a24697fa6bdd402284a06a0915cdfbfb","materialPath":"/v2content/upload/img/newImg/MP/a24697fa6bdd402284a06a0915cdfbfb.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/MP/min/a24697fa6bdd402284a06a0915cdfbfb.jpg"},{"materialId":"0807341c0b9b4c32aee4a1ca30e59b2d","materialPath":"/v2content/upload/img/newImg/TX/0807341c0b9b4c32aee4a1ca30e59b2d.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/TX/min/0807341c0b9b4c32aee4a1ca30e59b2d.jpg"},{"materialId":"54dfe13e59a94191ad84180367f074fd","materialPath":"/v2content/upload/img/newImg/PQ/54dfe13e59a94191ad84180367f074fd.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/PQ/min/54dfe13e59a94191ad84180367f074fd.jpg"},{"materialId":"ab702050c6c942e9a206f90204a5580a","materialPath":"/v2content/upload/img/newImg/ID/ab702050c6c942e9a206f90204a5580a.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/ID/min/ab702050c6c942e9a206f90204a5580a.jpg"},{"materialId":"dab6fd5278844a248a1e43419657d36c","materialPath":"/v2content/upload/img/newImg/VU/dab6fd5278844a248a1e43419657d36c.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/VU/min/dab6fd5278844a248a1e43419657d36c.jpg"},{"materialId":"2feabd6a462b488182ab00942b231cc6","materialPath":"/v2content/upload/img/newImg/XW/2feabd6a462b488182ab00942b231cc6.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/XW/min/2feabd6a462b488182ab00942b231cc6.jpg"},{"materialId":"59980ac183f54bbea79c0c7c29fbc672","materialPath":"/v2content/upload/img/newImg/ME/59980ac183f54bbea79c0c7c29fbc672.jpg","name":"生产车间","picPath":"/v2content/upload/img/newImg/ME/min/59980ac183f54bbea79c0c7c29fbc672.jpg"}]
     * pageNo : 1
     * pageSize : 10
     * return_code : 0
     * return_message : 成功
     * totalCount : 30
     * totalPage : 3
     */

    private int pageNo;
    private int pageSize;
    private int return_code;
    private String return_message;
    private int totalCount;
    private int totalPage;
    private List<DataListBean> dataList;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
         * materialId : 62a19e33193f472c9ccd85fc299c0df2
         * materialPath : /v2content/upload/img/newImg/PN/62a19e33193f472c9ccd85fc299c0df2.jpg
         * name : 生产车间
         * picPath : /v2content/upload/img/newImg/PN/min/62a19e33193f472c9ccd85fc299c0df2.jpg
         */

        private String materialId;
        private String materialPath;
        private String name;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }
    }
}
