package com.hj.casps.entity.goodsmanager.request;

/**
 * 商品编辑
 * Created by Administrator on 2017/5/4.
 */

public class GoodsEditRequestEntity {
    public String sys_token;    //	令牌号
    public String sys_uuid;//操作唯一编码（防重复提交）
    public String sys_func;//功能编码（用于授权检查）
    public String sys_user;//用户id
    public String sys_member;//	;会员id




    public String goodsId;//     商品Id
    public String categoryId;//   商品分类Id
    public String createAddress;//      生产地址
    public String factory;//              生产厂家
    public String productNum;//           生产编号
    public String brand;//           品牌
    public String stockNum;//           库存
    public String productTime;//             保质期
    public String specification;//                    规格
    public String unitSpecification;//              规格单位
    public Double minPrice;//       最小价格
    public Double maxPrice;//              最高价格
    public String unitPrice;//         价格单位
    public String described;//               商品描述
    public String imgId;//          商品标题图片Id
    public String imgPath;//          商品标题图片路径
    public String imageIds;//       轮播图片Id的集合，逗号分隔字串






}
