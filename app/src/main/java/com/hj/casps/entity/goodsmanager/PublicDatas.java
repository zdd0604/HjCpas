package com.hj.casps.entity.goodsmanager;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/8.
 */

public class PublicDatas implements Serializable {
    private static PublicDatas data = null;

    private int return_code;
    private String return_message;
    private String token;
    private String sys_user;

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    private PublicDatas() {

    }

    public static PublicDatas getInstance() {

        if (data == null)
            synchronized (PublicDatas.class) {
                data = new PublicDatas();
            }
        return data;
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
        return "PublicDatas{" +
                "return_code=" + return_code +
                ", return_message='" + return_message + '\'' +
                ", token='" + token + '\'' +
                ", sys_user='" + sys_user + '\'' +
                '}';
    }
}
