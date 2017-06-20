package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/4.
 * 编辑地址确认提交 JSON实体类
 */

public class UpdateMmbWarehouseLoading implements Serializable {
    private static final long serialVersionUID = 7394882180318375799L;

    private String sys_token;//	string	令牌号
    private String sys_uuid;//	string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//		string	用户id
    private String sys_name;//		string	用户名
    private String sys_member;//	string	会员id
    private String address;//	string	地址
    private String areaDesc;//	string	所属地域
    private String zipcode;//	string	邮编
    private String contact;//	string	联系人名称
    private String mobilephone;//	string	手机号
    private String phone;//		string	电话号码
    private String id;//	string	地址id
    private String mmbId;//	string	登录人id

    public UpdateMmbWarehouseLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String address, String areaDesc, String zipcode, String contact, String mobilephone, String phone, String id, String mmbId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.address = address;
        this.areaDesc = areaDesc;
        this.zipcode = zipcode;
        this.contact = contact;
        this.mobilephone = mobilephone;
        this.phone = phone;
        this.id = id;
        this.mmbId = mmbId;
    }

    public UpdateMmbWarehouseLoading() {
    }

    public String getSys_token() {
        return sys_token;
    }

    public void setSys_token(String sys_token) {
        this.sys_token = sys_token;
    }

    public String getSys_uuid() {
        return sys_uuid;
    }

    public void setSys_uuid(String sys_uuid) {
        this.sys_uuid = sys_uuid;
    }

    public String getSys_func() {
        return sys_func;
    }

    public void setSys_func(String sys_func) {
        this.sys_func = sys_func;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public String getSys_member() {
        return sys_member;
    }

    public void setSys_member(String sys_member) {
        this.sys_member = sys_member;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaDesc() {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMmbId() {
        return mmbId;
    }

    public void setMmbId(String mmbId) {
        this.mmbId = mmbId;
    }
}
