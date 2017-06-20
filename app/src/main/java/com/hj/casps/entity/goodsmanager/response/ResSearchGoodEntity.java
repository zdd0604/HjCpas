package com.hj.casps.entity.goodsmanager.response;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public class ResSearchGoodEntity {


    /**
     * dataList : [{"goodsId":"4c0a8e1ab8154ed7bfce6dd3abdc62a1","imgPath":"/v2content/upload/img/newImg/GY/min/0ceb874c454c444cbc756a4939e824eb.jpg","name":"罗非鱼","status":0},{"goodsId":"5b85cb496379472797b8c58d93cad949","imgPath":"/v2content/upload/img/newImg/AR/min/6ef0e1b796184bda99e0f470d46e7419.jpg","name":"金三胖1号水产鱼","status":0},{"goodsId":"6c8c78d7afed4a14a520dbdfcc84eab9","imgPath":"/v2content/upload/v2content/upload/img/newImg/BJ/min/d5415f429a2e4e63a0d64179426e6cdc.jpg","name":"水鱼2号","status":0},{"goodsId":"a8727080262d464c8d4f4be788281e8c","imgPath":"/v2content/upload/img/newImg/JY/min/aaa80c4328a14863b637641d6ae5d176.jpg","name":"草鱼","status":0},{"goodsId":"e6ba6becded84ff5aa90d6a719dacba1","imgPath":"/v2content/upload/img/newImg/GY/min/0ceb874c454c444cbc756a4939e824eb.jpg","name":"小草鱼1号","status":1}]
     * pageno : 1
     * pagesize : 5
     * return_code : 0
     * return_message : 成功
     * totalCount : 6
     * totalPage : 2
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

}
