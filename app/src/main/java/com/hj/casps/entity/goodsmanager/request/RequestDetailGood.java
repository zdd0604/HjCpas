package com.hj.casps.entity.goodsmanager.request;

/**
 * Created by Administrator on 2017/5/15.
 */

public class RequestDetailGood {

    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//	string	会员id


    private String goodsId;
    private String createTime;
    private String categoryId;//商品分类id
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
    private String imageIds;//
    //         string	  轮播图片Id的集合
    private String name;


    public RequestDetailGood(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String goodsId, String categoryId, String createAddress, String factory, String productNum, String brand, String stockNum, String productTime, String specification, String unitSpecification, String minPrice, String maxPrice, String unitPrice, String described, String imgId, String imgPath, String imageIds,String name,String createTime) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.goodsId = goodsId;
        this.categoryId = categoryId;
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
        this.createTime=createTime;
    }
}
