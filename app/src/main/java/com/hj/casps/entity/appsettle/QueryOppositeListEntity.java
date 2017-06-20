package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/15.
 * 获取收/付款方列表
 */

public class QueryOppositeListEntity implements Serializable {
    private static final long serialVersionUID = -1722352259592143175L;
    private String sellersId;//string		收款方id(本方付款时采用)
    private String sellersName;//string	收款方名称(本方付款时采用)
    private String buyersId;//string	付款方id(对方付款时采用)
    private String buyersName;//string	付款方名称(对方付款时采用)

    public String getSellersId() {
        return sellersId;
    }

    public void setSellersId(String sellersId) {
        this.sellersId = sellersId;
    }

    public String getSellersName() {
        return sellersName;
    }

    public void setSellersName(String sellersName) {
        this.sellersName = sellersName;
    }

    public String getBuyersId() {
        return buyersId;
    }

    public void setBuyersId(String buyersId) {
        this.buyersId = buyersId;
    }

    public String getBuyersName() {
        return buyersName;
    }

    public void setBuyersName(String buyersName) {
        this.buyersName = buyersName;
    }

    public QueryOppositeListEntity() {
    }

    public QueryOppositeListEntity(String sellersId, String sellersName, String buyersId, String buyersName) {
        this.sellersId = sellersId;
        this.sellersName = sellersName;
        this.buyersId = buyersId;
        this.buyersName = buyersName;
    }
}
