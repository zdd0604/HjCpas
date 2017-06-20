package com.hj.casps.quotes.wyt;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/17.
 */
@Entity
public class QtListEntity  implements Serializable{
    private static final long serialVersionUID = -5528071592531434337L;
    /**
     * goodname : 葵花油
     * goodsid : 97d25f2853c24dc585f50a2498fe8d2d
     * id : 30036814
     * imgPath : /img/newImg/XY/min/0944c49618184647a0df2553ea34363c.jpg
     * maxPrice : 1000000
     * minPrice : 1
     * mmbId : testshop001
     * mmbName : 长城商行
     * num : 1000000
     * startEnd : 1514476800000
     * startTime : 1476806400000
     */

    private String goodname;
    private String goodsid;
    private String id;
    private String imgPath;
    private double maxPrice;
    private double minPrice;
    private String mmbId;
    private String mmbName;
    private int num;
    private long startEnd;
    private long startTime;

    @Generated(hash = 1287873865)
    public QtListEntity(String goodname, String goodsid, String id, String imgPath,
            double maxPrice, double minPrice, String mmbId, String mmbName, int num,
            long startEnd, long startTime) {
        this.goodname = goodname;
        this.goodsid = goodsid;
        this.id = id;
        this.imgPath = imgPath;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.mmbId = mmbId;
        this.mmbName = mmbName;
        this.num = num;
        this.startEnd = startEnd;
        this.startTime = startTime;
    }

    @Generated(hash = 458001722)
    public QtListEntity() {
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
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
}
