package com.hj.casps.entity.goodsmanager.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/13.
 */
@Entity
public class GoodInfoEntity {

    /**
     * brand :
     * categoryId : 1001003
     * categoryName : 杂粮
     * createAddress :
     * createTime : 1467734400000
     * described :
     * factory :
     * imgPath : /v2content/upload/img/newImg/UM/min/81b4107e65304290a361e907c51d9f41.jpg
     * maxPrice : 1000
     * minPrice : 1
     * name : 小米
     * productNum :
     * productTime : 1110
     * specification :
     * status : 1
     * stockNum : 11111
     * unitPrice : ￥
     * unitSpecification : 1
     */
    @Id
    private String goodsId;
    private String brand;
    private String categoryId;
    private String categoryName;
    private String createAddress;
    private String createTime;
    private String described;
    private String factory;
    private String imgPath;
    private Double maxPrice;
    private Double minPrice;
    private String name;
    private String productNum;
    private String productTime;
    private String specification;
    private int status;
    private String stockNum;
    private String unitPrice;
    private String unitSpecification;
    private String goodsImages;


    @Generated(hash = 1490665928)
    public GoodInfoEntity(String goodsId, String brand, String categoryId,
            String categoryName, String createAddress, String createTime, String described,
            String factory, String imgPath, Double maxPrice, Double minPrice, String name,
            String productNum, String productTime, String specification, int status,
            String stockNum, String unitPrice, String unitSpecification, String goodsImages) {
        this.goodsId = goodsId;
        this.brand = brand;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createAddress = createAddress;
        this.createTime = createTime;
        this.described = described;
        this.factory = factory;
        this.imgPath = imgPath;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.name = name;
        this.productNum = productNum;
        this.productTime = productTime;
        this.specification = specification;
        this.status = status;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
        this.unitSpecification = unitSpecification;
        this.goodsImages = goodsImages;
    }

    @Generated(hash = 1854206929)
    public GoodInfoEntity() {
    }


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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
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

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(String goodsImages) {
        this.goodsImages = goodsImages;
    }
}
