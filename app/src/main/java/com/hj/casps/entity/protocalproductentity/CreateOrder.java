package com.hj.casps.entity.protocalproductentity;

import java.util.List;

/**
 * 提交类
 */
public class CreateOrder {
    private String sys_token;
    private String sys_uuid;
    private String sys_func;
    private String sys_user;
    private String sys_name;
    private String sys_member;
    private String buyersAddressId;
    private String buyersAddressName;
    private String buyerId;
    private String buyerName;
    private String executeEndTime;
    private String executeStartTime;
    private String getAccount;
    private String getBank;
    private String payAccount;
    private String payBank;
    private String payTime;
    private String sellerAddressId;
    private String sellerAddressName;
    private String sellerId;
    private String sellerName;
    private String totalMoney;
    private String workflowTypeId;
    private List<OrderListBean> orderList;
    private String id;

    public CreateOrder(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String buyersAddressId, String buyersAddressName, String buyerId, String buyerName, String executeEndTime, String executeStartTime, String getAccount, String getBank, String payAccount, String payBank, String payTime, String sellerAddressId, String sellerAddressName, String sellerId, String sellerName, String totalMoney, String workflowTypeId, List<OrderListBean> orderList, String id) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.buyersAddressId = buyersAddressId;
        this.buyersAddressName = buyersAddressName;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.executeEndTime = executeEndTime;
        this.executeStartTime = executeStartTime;
        this.getAccount = getAccount;
        this.getBank = getBank;
        this.payAccount = payAccount;
        this.payBank = payBank;
        this.payTime = payTime;
        this.sellerAddressId = sellerAddressId;
        this.sellerAddressName = sellerAddressName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.totalMoney = totalMoney;
        this.workflowTypeId = workflowTypeId;
        this.orderList = orderList;
        this.id = id;
    }

    public CreateOrder(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String buyersAddressId, String buyersAddressName, String buyerId, String buyerName, String executeEndTime, String executeStartTime, String getAccount, String getBank, String payAccount, String payBank, String payTime, String sellerAddressId, String sellerAddressName, String sellerId, String sellerName, String totalMoney, String workflowTypeId, List<OrderListBean> orderList) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.buyersAddressId = buyersAddressId;
        this.buyersAddressName = buyersAddressName;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.executeEndTime = executeEndTime;
        this.executeStartTime = executeStartTime;
        this.getAccount = getAccount;
        this.getBank = getBank;
        this.payAccount = payAccount;
        this.payBank = payBank;
        this.payTime = payTime;
        this.sellerAddressId = sellerAddressId;
        this.sellerAddressName = sellerAddressName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.totalMoney = totalMoney;
        this.workflowTypeId = workflowTypeId;
        this.orderList = orderList;
    }

    public static class OrderListBean {
        private String buyersId;
        private String buyersName;
        private String sellersId;
        private String sellerName;
        private String categoryId;
        private String goodsId;
        private String goodsName;
        private String goodsNum;
        private String money;
        private String price;
        private String quoteId;
        private String id;

        public OrderListBean(String buyersId, String buyerName, String sellersId, String sellerName, String categoryId, String goodsId, String goodsName, String goodsNum, String money, String price, String quoteId, String id) {
            this.buyersId = buyersId;
            this.buyersName = buyerName;
            this.sellersId = sellersId;
            this.sellerName = sellerName;
            this.categoryId = categoryId;
            this.goodsId = goodsId;
            this.goodsName = goodsName;
            this.goodsNum = goodsNum;
            this.money = money;
            this.price = price;
            this.quoteId = quoteId;
            this.id = id;
        }

        public OrderListBean(String buyersId, String buyerName, String sellersId, String sellerName, String categoryId, String goodsId, String goodsName, String goodsNum, String money, String price, String quoteId) {
            this.buyersId = buyersId;
            this.buyersName = buyerName;
            this.sellersId = sellersId;
            this.sellerName = sellerName;
            this.categoryId = categoryId;
            this.goodsId = goodsId;
            this.goodsName = goodsName;
            this.goodsNum = goodsNum;
            this.money = money;
            this.price = price;
            this.quoteId = quoteId;
        }
    }

}
