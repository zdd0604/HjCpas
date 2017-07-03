package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/17.
 * 订单查询详情（所有出订单详情和订单编辑页面都用这个）
 */

public class OrdertitleData implements Serializable {
    private static final long serialVersionUID = -4473579895885613252L;
    public String goodsName;//	string	商品名
    public String goodsId;//	string	商品名id
    public String categoryId;//	string	商品分类id
    public String id;//	string	id
    public String quoteId;//	string	报价id
    public double money;//	string	金额
    public double price;//double	单价
    public double goodsNum;//	double	数量
    public double exeSendgoodsNum;//	double	待发货数量
    public double exeReturngoodsNum;// double	实际发货数量
    public double exePaymoneyNum;//	double	待付款金额
    public double exeRefundNum;//	double	实际付款金额
}
