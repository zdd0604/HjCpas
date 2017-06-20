package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/15.
 * 获取收/付款方列表
 */

public class QueryOppositeListRespon<T> implements Serializable {
    private static final long serialVersionUID = 2150681301334654772L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public T list;
}
