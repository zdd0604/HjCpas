package com.hj.casps.protocolmanager;

import java.util.List;

/**
 * Created by zy on 2017/4/26.
 */
//协议详情的Model,就是协议详情的返回类，为了利用数据，先根据这个格式进行解析，然后使用这些数据
public class ProtocolDetailModel {
    private String contract_type;
    private String buy_membername;
    private String sell_membername;
    private String contract_title;
    private String operate_user;
    private String operate_time;
    private String user_time;
    private String pay_type;
    private String flow_type;
    private String sendgoods_type;
    private String payer_code;
    private String payer_name;
    private String getmoney_code;
    private String getmoney_name;
    private String sendgoods_address;
    private String getgoods_address;
    private String goods;
    private String note;
    private List<String> addresslist;
    private List<String> banklist;

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getBuy_membername() {
        return buy_membername;
    }

    public void setBuy_membername(String buy_membername) {
        this.buy_membername = buy_membername;
    }

    public String getSell_membername() {
        return sell_membername;
    }

    public void setSell_membername(String sell_membername) {
        this.sell_membername = sell_membername;
    }

    public String getContract_title() {
        return contract_title;
    }

    public void setContract_title(String contract_title) {
        this.contract_title = contract_title;
    }

    public String getOperate_user() {
        return operate_user;
    }

    public void setOperate_user(String operate_user) {
        this.operate_user = operate_user;
    }

    public String getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(String operate_time) {
        this.operate_time = operate_time;
    }

    public String getUser_time() {
        return user_time;
    }

    public void setUser_time(String user_time) {
        this.user_time = user_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getFlow_type() {
        return flow_type;
    }

    public void setFlow_type(String flow_type) {
        this.flow_type = flow_type;
    }

    public String getSendgoods_type() {
        return sendgoods_type;
    }

    public void setSendgoods_type(String sendgoods_type) {
        this.sendgoods_type = sendgoods_type;
    }

    public String getPayer_code() {
        return payer_code;
    }

    public void setPayer_code(String payer_code) {
        this.payer_code = payer_code;
    }

    public String getPayer_name() {
        return payer_name;
    }

    public void setPayer_name(String payer_name) {
        this.payer_name = payer_name;
    }

    public String getGetmoney_code() {
        return getmoney_code;
    }

    public void setGetmoney_code(String getmoney_code) {
        this.getmoney_code = getmoney_code;
    }

    public String getGetmoney_name() {
        return getmoney_name;
    }

    public void setGetmoney_name(String getmoney_name) {
        this.getmoney_name = getmoney_name;
    }

    public String getSendgoods_address() {
        return sendgoods_address;
    }

    public void setSendgoods_address(String sendgoods_address) {
        this.sendgoods_address = sendgoods_address;
    }

    public String getGetgoods_address() {
        return getgoods_address;
    }

    public void setGetgoods_address(String getgoods_address) {
        this.getgoods_address = getgoods_address;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getAddresslist() {
        return addresslist;
    }

    public void setAddresslist(List<String> addresslist) {
        this.addresslist = addresslist;
    }

    public List<String> getBanklist() {
        return banklist;
    }

    public void setBanklist(List<String> banklist) {
        this.banklist = banklist;
    }
}
