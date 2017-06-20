package com.hj.casps.entity.appordergoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/15.
 * 签收退货操作
 */

public class GetReturnGoodsOperation implements Serializable {
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_name;//string	用户名
    private String sys_member;//	string	会员id
    private List<GetReturnGoodsList> orderList;

    public GetReturnGoodsOperation() {
    }

    public GetReturnGoodsOperation(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, List<GetReturnGoodsList> orderList) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.orderList = orderList;
    }

    public static class GetReturnGoodsList implements Serializable {

        private static final long serialVersionUID = 8690197762080466614L;
        private String orderId;//	string	订单号
        private String num;//	int	本次收货数量
        private String address_id;//string	收货地址

        public GetReturnGoodsList() {
        }

        public GetReturnGoodsList(String orderId, String num, String address) {
            this.orderId = orderId;
            this.num = num;
            this.address_id = address;
        }
    }

}
