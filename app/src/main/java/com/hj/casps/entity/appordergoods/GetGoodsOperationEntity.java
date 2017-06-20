package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/12.
 * 收货操作提交时的实体
 */

public class GetGoodsOperationEntity implements Serializable {
    private static final long serialVersionUID = 2682255657734816614L;
    private String oredertitleCode;//	string	订单id
    private int num;//	int	本次收货数量
    private String id;//	string	订单号

    public GetGoodsOperationEntity() {
    }

    public String getOredertitleCode() {
        return oredertitleCode;
    }

    public void setOredertitleCode(String oredertitleCode) {
        this.oredertitleCode = oredertitleCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetGoodsOperationEntity(String oredertitleCode, int num, String id) {
        this.oredertitleCode = oredertitleCode;
        this.num = num;
        this.id = id;
    }
}
