package com.hj.casps.entity.appquote;

/**
 * 创建和编辑报价返回值
 */
public class ReturnBean {
    private int return_code;
    private String return_message;

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
}