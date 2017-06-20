package com.hj.casps.entity.appordergoodsCallBack;

        import java.io.Serializable;

/**
 * Created by Admin on 2017/5/11.
 */

public class AddressEditRespon<T> implements Serializable {
    private static final long serialVersionUID = -4056288355667132049L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public String successMsg;
    public String errorMsg;
    public T row;
}