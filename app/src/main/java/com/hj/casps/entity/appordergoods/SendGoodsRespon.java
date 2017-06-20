package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/15.
 * 发货操作
 */

public class SendGoodsRespon<T> implements Serializable {
    private static final long serialVersionUID = -959191088837652451L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public String msg;//	string	成功或失败条数
}
