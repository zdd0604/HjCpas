package com.hj.casps.entity.paymentmanager;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ReqQueryRefundMoney {
    private String sys_token;//string	令牌号
    private String sys_uuid;//        string	操作唯一编码（防重复提交）
    private String sys_func;//               string	功能编码（用于授权检查）
    private String sys_user;//                 string	用户id
    private String sys_member;//                         string	会员id
    private String pageno;//                       int	页号
    private String pagesize;//             int	页行数
    private String ordertitleNumber;//              string	订单号
    private String goodsName;//                 string	商品名
    private String buyersName;//                      string	付款方


    public ReqQueryRefundMoney(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String pageno, String pagesize, String ordertitleNumber, String goodsName, String buyersName) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.ordertitleNumber = ordertitleNumber;
        this.goodsName = goodsName;
        this.buyersName = buyersName;
    }
}
