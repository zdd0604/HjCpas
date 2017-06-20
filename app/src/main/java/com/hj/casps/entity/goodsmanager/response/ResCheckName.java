package com.hj.casps.entity.goodsmanager.response;

/**
 * Created by Administrator on 2017/5/29.
 */

public class ResCheckName {
    private int return_code;//     int	结果码，0 成功，101 无权限，201 数据库错误 1 失败
    private String return_message;//	                        string	结果提示文本
    private boolean isRepeat;//   boolean	true:重复不可用，false:不重复可用


    public String getReturn_message() {
        return return_message;
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }
}
