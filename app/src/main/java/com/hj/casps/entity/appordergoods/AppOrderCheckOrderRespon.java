package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/17.
 * 订单查询详情（所有出订单详情和订单编辑页面都用这个）
 */

public class AppOrderCheckOrderRespon<A, B, C, D, T, F> implements Serializable {
    private static final long serialVersionUID = 7330327622097896429L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public String Result;//string	结果提示文本
    public int total;
    public A buyersAccountList;
    public B buyersAddressList;
    public C data;
    public D ordertitle;
    public T sellersAccountList;
    public F sellersAddressList;
}
