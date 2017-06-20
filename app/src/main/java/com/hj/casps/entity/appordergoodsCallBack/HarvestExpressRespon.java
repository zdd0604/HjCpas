package com.hj.casps.entity.appordergoodsCallBack;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/12.
 */

public class HarvestExpressRespon<T> implements Serializable {
    private static final long serialVersionUID = 1665767870815610134L;
    public int total;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public T rows;
}
