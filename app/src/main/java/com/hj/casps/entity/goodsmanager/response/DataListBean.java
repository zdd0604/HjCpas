package com.hj.casps.entity.goodsmanager.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2017/6/9.
 */
@Entity
public class DataListBean {
    /**
     * goodsId : 4c0a8e1ab8154ed7bfce6dd3abdc62a1
     * imgPath : /v2content/upload/img/newImg/GY/min/0ceb874c454c444cbc756a4939e824eb.jpg
     * name : 罗非鱼
     * status : 0
     */

    @Id
    private String id;
    private String categoryId;
    private String goodsId;
    private String imgPath;
    private String name;
    private int status;


    @Generated(hash = 33804257)
    public DataListBean(String id, String categoryId, String goodsId, String imgPath,
            String name, int status) {
        this.id = id;
        this.categoryId = categoryId;
        this.goodsId = goodsId;
        this.imgPath = imgPath;
        this.name = name;
        this.status = status;
    }

    @Generated(hash = 587602649)
    public DataListBean() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
