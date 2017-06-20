package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 获得添加操作员页面所需数据
 */

public class ToAddUserPageRespon <T> implements Serializable {
    private static final long serialVersionUID = 2194249285412009534L;
    public int return_code;//int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public T dataList;
}
