package com.hj.casps.quotes.wyt;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ResQuoteDetailEntity {


    /**
     * areaName : 北京市
     * explan : 我们会为你提供最优质的服务，请你购物愉快！
     * goodsId : 586e0ae535974723a67e5d7c0c436ee3
     * goodsName : 食品1
     * maxPrice : 100
     * minPrice : 1
     * mmbName : cyh
     * num : 1
     * pathlist : ["/img/newImg/MW/4d017aa6992245c089c244e72eafd777.jpg","/img/newImg/RC/bdf7b5bd7baa4ff1b1265d816b8b13b2.jpg","/img/newImg/DP/460aab6e654848bcb580aba95b075698.jpg","/img/newImg/FH/b911da52778940ca8ca56e14e95e738c.jpg","/img/newImg/AJ/5ae6c4a27e0c4816806af8d22c4025c7.jpg"]
     * quoteId : 30038606
     * return_code : 0
     * return_message : success
     * startEnd : 1522857600000
     * startTime : 1491321600000
     */

    private String quoteId;
    private String areaName;
    private String explan;
    private String goodsId;
    private String goodsName;
    private double maxPrice;
    private double minPrice;
    private String mmbName;
    private int num;
    private int return_code;
    private String return_message;
    private long startEnd;
    private long startTime;
    private List<String> pathlist;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
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

    public List<String> getPathlist() {
        return pathlist;
    }

    public void setPathlist(List<String> pathlist) {
        this.pathlist = pathlist;
    }
}
