package com.hj.casps.entity.goodsmanager.request;

/**
 * Created by Administrator on 2017/5/15.
 */

public class RequestCreateGood {
    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//	string	会员id
    private String categoryId;//商品分类id

    private  String createTime;
    private String createAddress;//                          string	 生产地址
    private String factory;//   string	 生产厂家
    private String productNum;//                    string	 生产编号
    private String brand;//                  string	 品牌
    private String stockNum;//    string	 库存
    private String productTime;//                 	string	 保质期
    private String specification;//                                string	 规格
    private String unitSpecification;//                             string	 规格单位
    private String minPrice;//       Double	 最小价格
    private String maxPrice;//    	 最高价格
    private String unitPrice;//      string	 价格单位
    private String described;//               string	 商品描述
    private String imgId;//                 string	 商品图片Id
    private String imgPath;//       string	 商品图片路径((要求去掉前缀))
    private String imageIds;//          string	  轮播图片Id的集合
    private String name;//商品名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestCreateGood(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String categoryId,String createTime,String createAddress, String factory, String productNum, String brand, String stockNum, String productTime, String specification, String unitSpecification, String minPrice, String maxPrice, String unitPrice, String described, String imgId, String imgPath, String imageIds,String name) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.categoryId = categoryId;
        this.createTime=createTime;
        this.createAddress = createAddress;
        this.factory = factory;
        this.productNum = productNum;
        this.brand = brand;
        this.stockNum = stockNum;
        this.productTime = productTime;
        this.specification = specification;
        this.unitSpecification = unitSpecification;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.unitPrice = unitPrice;
        this.described = described;
        this.imgId = imgId;
        this.imgPath = imgPath;
        this.imageIds = imageIds;
        this.name=name;

    }


    public String getSys_token() {
        return sys_token;
    }

    public void setSys_token(String sys_token) {
        this.sys_token = sys_token;
    }

    public String getSys_uuid() {
        return sys_uuid;
    }

    public void setSys_uuid(String sys_uuid) {
        this.sys_uuid = sys_uuid;
    }

    public String getSys_func() {
        return sys_func;
    }

    public void setSys_func(String sys_func) {
        this.sys_func = sys_func;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    public String getSys_member() {
        return sys_member;
    }

    public void setSys_member(String sys_member) {
        this.sys_member = sys_member;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreateAddress() {
        return createAddress;
    }

    public void setCreateAddress(String createAddress) {
        this.createAddress = createAddress;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
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

    public String getUnitSpecification() {
        return unitSpecification;
    }

    public void setUnitSpecification(String unitSpecification) {
        this.unitSpecification = unitSpecification;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
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

    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    @Override
    public String toString() {
        return "RequestCreateGood{" +
                "sys_token='" + sys_token + '\'' +
                ", sys_uuid='" + sys_uuid + '\'' +
                ", sys_func='" + sys_func + '\'' +
                ", sys_user='" + sys_user + '\'' +
                ", sys_member='" + sys_member + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createAddress='" + createAddress + '\'' +
                ", factory='" + factory + '\'' +
                ", productNum='" + productNum + '\'' +
                ", brand='" + brand + '\'' +
                ", stockNum='" + stockNum + '\'' +
                ", productTime='" + productTime + '\'' +
                ", specification='" + specification + '\'' +
                ", unitSpecification='" + unitSpecification + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", described='" + described + '\'' +
                ", imgId='" + imgId + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", imageIds='" + imageIds + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
