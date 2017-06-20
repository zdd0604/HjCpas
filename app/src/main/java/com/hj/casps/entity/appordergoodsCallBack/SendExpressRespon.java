package com.hj.casps.entity.appordergoodsCallBack;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/11.
 * 获取我的提交的待对方审批结款单列表
 * 发货 列表
 */

public class SendExpressRespon<T> implements Serializable {

    private static final long serialVersionUID = -6018167825874716116L;

    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public int pagecount;
    public T list;
}