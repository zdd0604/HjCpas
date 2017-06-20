package com.hj.casps.entity.goodsmanager.response;

import java.util.List;
import java.util.Map;

/**商品详情/商品编辑返回实体通用
 * Created by Administrator on 2017/5/4.
 */

public class GoodsDetailResponseEntity {
    public Map goodsInfo;//商品信息
    public String goodsId;//商品Id
    public String name;//商品名称
    public String categoryName;//	商品分类
    public String createTime;//生产日期
    public String createAddress;//	生产地址
    public String factory;//生产厂家
    public String productNum;//生产编号
    public String brand;//商品品牌
    public String stockNum;//库存
    public String productTime;//保质期
    public String specification;//	 规格
    public String uit_specification;//    规格单位
    public double maxPrice;//最高价格
    public double minPrice;//	最低价格
    public String unitPrice;// 价格单位
    public String imgPath;//商品图片
    public String status;// 商品状态
    public String described;//   备注
    public List<String> goodsImages;//  轮播图片列表





}
