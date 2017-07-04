package com.hj.casps.entity.appquote;

import java.util.List;

//合作范围返回值，解析时使用
public class MmbReturnBack {
    private int return_code;
    private String return_message;
    private int json;
    private List<MmbModelPerson> list;

    public int getJson() {
        return json;
    }

    public void setJson(int json) {
        this.json = json;
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

    public List<MmbModelPerson> getList() {
        return list;
    }

    public void setList(List<MmbModelPerson> list) {
        this.list = list;
    }
}