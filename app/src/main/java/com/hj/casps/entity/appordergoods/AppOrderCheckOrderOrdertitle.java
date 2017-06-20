package com.hj.casps.entity.appordergoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/17.
 * 订单查询详情（所有出订单详情和订单编辑页面都用这个）
 */

public class AppOrderCheckOrderOrdertitle<T> implements Serializable {
    private static final long serialVersionUID = -2256008767949494361L;
    public String buyersName;//	string	买方名字
    public String buyersId;//	string	买方id
    public String sellersName;//string 卖方名字
    public String sellersId;//string 卖方id
    public int workflowTypeId;//string	流程状态	1货款两清，2先货后款，3先货后款已交货，4先款后货，5先款后货已交款
    public int orderStatus;//int	类型	buyersId== memberId orderstatus就是采购 卖方 否则相反
    public String ordertitleCode;//string	订单号
    public String lockTime;//string	签约时间
    public double totalMoney;//string	总金额
    public String payTime;//	string	付款时间
    public String executeStartTime;//string	送货开始时间
    public String executeEndTime;//string	送货结束时间
    public String payBank;//	string	银行名称（支付）
    public String payAccount;//string	银行账号（支付）
    public String getBank;//string	银行名称（收款）
    public String getAccount;//string	银行账号（收款）
    public String sellersAddressName;//string	收货地址
    public String buyersAddressName;//string	发货地址
    public List<OrdertitleData> orderList;//商品list

    @Override
    public String toString() {
        return "AppOrderCheckOrderOrdertitle{" +
                "buyersName='" + buyersName + '\'' +
                ", buyersId='" + buyersId + '\'' +
                ", sellersName='" + sellersName + '\'' +
                ", workflowTypeId='" + workflowTypeId + '\'' +
                ", orderStatus=" + orderStatus +
                ", ordertitleCode='" + ordertitleCode + '\'' +
                ", lockTime='" + lockTime + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", payTime='" + payTime + '\'' +
                ", executeStartTime='" + executeStartTime + '\'' +
                ", payBank='" + payBank + '\'' +
                ", payAccount='" + payAccount + '\'' +
                ", getBank='" + getBank + '\'' +
                ", getAccount='" + getAccount + '\'' +
                ", sellersAddressName='" + sellersAddressName + '\'' +
                ", buyersAddressName='" + buyersAddressName + '\'' +
                ", data=" + orderList +
                '}';
    }
}
