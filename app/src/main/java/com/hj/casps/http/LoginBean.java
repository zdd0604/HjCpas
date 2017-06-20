package com.hj.casps.http;

import java.io.Serializable;

/**
 * Created by 鑫 Administrator on 2017/5/5.
 */

public class LoginBean<T> implements Serializable{


    /**
     return_code	int	结果码，0 成功，101 用户不存在，102 密码错误，103 短信码错误，202 未知错误
     return_message	string	结果提示文本
     token string	成功后会有一个定长字串的令牌
     */

    private int return_code;
    private String return_message;
    private String token;
    private String sys_user;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "return_code=" + return_code +
                ", return_message='" + return_message + '\'' +
                ", token='" + token + '\'' +
                ", sys_user='" + sys_user + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
