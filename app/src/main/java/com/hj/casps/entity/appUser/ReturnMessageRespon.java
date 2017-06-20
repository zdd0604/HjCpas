package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 部分共用的返回参数解析接口
 * return_code
 * return_message
 */

public class ReturnMessageRespon<T> implements Serializable {
    private static final long serialVersionUID = 9083755697298488748L;
    public int return_code;//int	结果码，0 降级成功，101 无权限，201 数据库错误 1降级失败
    public String return_message;//string	结果提示文本
    public int success_num;// int 收货成功个数
    public int fail_num;// int 收货失败个数
    public boolean isExist;//	boolean	true:已注册 false:未注册
}
