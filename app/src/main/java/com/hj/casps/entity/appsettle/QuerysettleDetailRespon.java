package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/15.
 * 获取结款单详情
 */

public class QuerysettleDetailRespon<T> implements Serializable {
    private static final long serialVersionUID = 5526087257523342582L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public T data;
}
