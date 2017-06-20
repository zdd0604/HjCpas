package com.hj.casps.entity.goodsmanager.response;

import java.io.Serializable;
import java.util.List;

/**
 * 添加编辑商品时加载平台所有的商品分类
 * 获取当前用户会员的所有使用的商品分类
 * Created by Administrator on 2017/5/4.
 */

public class GoodsCategoryEntity<T> implements Serializable {

    private static final long serialVersionUID = 8572297404899675349L;
    public int return_code;
    public String return_message;
    public T categoryList;
}

