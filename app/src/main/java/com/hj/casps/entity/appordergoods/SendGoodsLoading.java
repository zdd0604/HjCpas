package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/4.
 * 发货操作提交 入口参数：
 */

public class SendGoodsLoading implements Serializable {

    private static final long serialVersionUID = -8579061703110828362L;

    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_name;//string	用户名
    private String sys_member;//string	会员id
    private String id;//string	订单id
    private String SendgoodsNum;//	string	本次发货数量
    private String sellersId;//	string	卖方id
    private String sellersName;//string	卖方名字
    private String buyersId;//string	买方id
    private String exeSendgoodsNum;//	double	待发数量
    private String money;//double	钱数
    private String goodsNum;//double	总数

    public SendGoodsLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String id, String sendgoodsNum, String sellersId, String sellersName, String buyersId, String exeSendgoodsNum, String money, String goodsNum) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.id = id;
        SendgoodsNum = sendgoodsNum;
        this.sellersId = sellersId;
        this.sellersName = sellersName;
        this.buyersId = buyersId;
        this.exeSendgoodsNum = exeSendgoodsNum;
        this.money = money;
        this.goodsNum = goodsNum;
    }

    public SendGoodsLoading() {
    }
}
