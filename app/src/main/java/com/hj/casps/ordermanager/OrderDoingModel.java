package com.hj.casps.ordermanager;

/**
 * Created by zy on 2017/5/2.
 * 订单管理页面的提交类
 */

public class OrderDoingModel {
    private String ordertitleCode;
    private String id;
    private int status;
    private int status2;
    private String object;
    private String create_time;
    private String totalMoney;
    private boolean choice;

    public int getStatus2() {
        return status2;
    }

    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    public String getOrdertitleCode() {
        return ordertitleCode;
    }

    public void setOrdertitleCode(String ordertitleCode) {
        this.ordertitleCode = ordertitleCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }
}
