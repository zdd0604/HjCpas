package com.hj.casps.entity.appordergoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/4.
 * 退货操作提交
 * 入口参数
 */

public class ReturnGoodsLoading implements Serializable {
    private static final long serialVersionUID = 2275721101414706560L;


    /**
     * sys_user : 0236e1f6e6474da88ff1388e8ae2d46d
     * sys_member : testschool001
     * sys_func : 1212
     * sys_token : 1212
     * sys_uuid : 00033
     * sys_name : as02
     * orderList : [{"order_id":"b7a77e840037449e8dd644c27e311005","num":"120"},{"order_id":"2f364bdfb99746eabed89f6a4715a394","num":"10"}]
     */

    private String sys_user;
    private String sys_member;
    private String sys_func;
    private String sys_token;
    private String sys_uuid;
    private String sys_name;
    private List<OrderListBean> orderList;

    public ReturnGoodsLoading(String sys_user, String sys_member, String sys_func, String sys_token,
                              String sys_uuid, String sys_name, List<OrderListBean> orderList) {
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.sys_func = sys_func;
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_name = sys_name;
        this.orderList = orderList;
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

    public String getSys_func() {
        return sys_func;
    }

    public void setSys_func(String sys_func) {
        this.sys_func = sys_func;
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

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * order_id : b7a77e840037449e8dd644c27e311005
         * num : 120
         */

        private String orderId;
        private String num;

        public OrderListBean(String order_id, String num) {
            this.orderId = order_id;
            this.num = num;
        }

        public String getOrder_id() {
            return orderId;
        }

        public void setOrder_id(String order_id) {
            this.orderId = order_id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
