package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/4.
 * 查询待收货列表 后台返回JSON
 */

public class QueryGetGoodsAddressGain<T> implements Serializable {
    private static final long serialVersionUID = -8189057271750345730L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public T rows;
}
