package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/8.
 * 功能描述：收货地址添加确认
 * 返回参数：Map结构的JSON字串
 */

public class CreateMmbWarehouseGain implements Serializable {
    private static final long serialVersionUID = 3856273205318329126L;

    /**
     * return_code : 0
     * return_message : success
     * successMsg : 创建会员仓库成功!
     * errorMsg :
     */

    private int return_code;
    private String return_message;
    private String successMsg;
    private String errorMsg;

    public CreateMmbWarehouseGain(int return_code, String return_message, String successMsg, String errorMsg) {
        this.return_code = return_code;
        this.return_message = return_message;
        this.successMsg = successMsg;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    @Override
    public String toString() {
        return "CreateMmbWarehouseGain{" +
                "return_code=" + return_code +
                ", return_message='" + return_message + '\'' +
                ", successMsg='" + successMsg + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
