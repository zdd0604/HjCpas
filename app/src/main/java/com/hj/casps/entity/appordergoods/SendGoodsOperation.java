package com.hj.casps.entity.appordergoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/15.
 * 发货操作
 */

public class SendGoodsOperation implements Serializable {
    private static final long serialVersionUID = -4456236075173917285L;

    /**
     * sys_user : e9498a4106af4ba29d32bf4f6b42b500
     * sys_member : testshop001
     * sys_func : 1212
     * sys_token : 1212
     * sys_uuid : 00033
     * sys_name : zdd
     * orderList : [{"order_id":"4988ea3623bd4ddd933ecde753201818","sendgoods_num":"120"},{"order_id":"2f364bdfb99746eabed89f6a4715a394","sendgoods_num":"10"}]
     */

    private String sys_user;
    private String sys_member;
    private String sys_func;
    private String sys_token;
    private String sys_uuid;
    private String sys_name;
    private List<OrderListBean> orderList;

    public SendGoodsOperation() {
    }

    public SendGoodsOperation(String sys_user, String sys_member, String sys_func,
                              String sys_token, String sys_uuid,
                              String sys_name, List<OrderListBean> orderList) {
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
         * order_id : 4988ea3623bd4ddd933ecde753201818
         * sendgoods_num : 120
         */

        private String orderId;
        private String sendgoods_num;

        public OrderListBean(String order_id, String sendgoods_num) {
            this.orderId = order_id;
            this.sendgoods_num = sendgoods_num;
        }

        public OrderListBean() {
        }

        public String getOrder_id() {
            return orderId;
        }

        public void setOrder_id(String order_id) {
            this.orderId = order_id;
        }

        public String getSendgoods_num() {
            return sendgoods_num;
        }

        public void setSendgoods_num(String sendgoods_num) {
            this.sendgoods_num = sendgoods_num;
        }
    }
}
