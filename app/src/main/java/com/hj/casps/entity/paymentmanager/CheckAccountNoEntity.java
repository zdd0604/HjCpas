package com.hj.casps.entity.paymentmanager;

/**
 * Created by Administrator on 2017/5/16.
 */

public class CheckAccountNoEntity {

    private int return_code;//int	结果码，0 成功，101 无权限，201 数据库错误
    private String return_message;//string	结果提示文本
    private int num;//返回值  num == 0  ? true : false

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
