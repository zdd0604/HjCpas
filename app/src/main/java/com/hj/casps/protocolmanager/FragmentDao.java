package com.hj.casps.protocolmanager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhonglian on 2017/7/3.
 * 用来保存关系协议管理，订单管理页面的数据库
 */
@Entity
public class FragmentDao {
    private String type_i;
    private String type_j;
    private String type_k;
    private String json;

    @Generated(hash = 375823287)
    public FragmentDao(String type_i, String type_j, String type_k, String json) {
        this.type_i = type_i;
        this.type_j = type_j;
        this.type_k = type_k;
        this.json = json;
    }

    @Generated(hash = 1382220505)
    public FragmentDao() {
    }

    public String getType_i() {
        return type_i;
    }

    public void setType_i(String type_i) {
        this.type_i = type_i;
    }

    public String getType_j() {
        return type_j;
    }

    public void setType_j(String type_j) {
        this.type_j = type_j;
    }

    public String getType_k() {
        return type_k;
    }

    public void setType_k(String type_k) {
        this.type_k = type_k;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "FragmentDao{" +
                "type_i='" + type_i + '\'' +
                ", type_j='" + type_j + '\'' +
                ", type_k='" + type_k + '\'' +
                ", json='" + json + '\'' +
                '}';
    }
}
