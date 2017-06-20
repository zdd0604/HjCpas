package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 */

public class CheckUserRespon<T> implements Serializable {
    private static final long serialVersionUID = 8705484894481100992L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误 1 失败
    public String return_message;//string	结果提示文本
    public boolean isExist;//	boolean	true:已注册 false:未注册
}
