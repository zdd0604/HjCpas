package com.hj.casps.entity.appordergoods;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 当前会员地址列表
 * 地址编辑
 * 后台返回的JSON实体
 */
@Entity
public class WarehouseEntity implements Serializable {

    private static final long serialVersionUID = 976644609252164876L;


    /**
     * address : ssdd
     * areaDesc :
     * areaId : 4000
     * contact : 哈哈
     * id : 55c75461839b46f0a690efa2c9a6c6f6
     * mobilephone : aa
     * phone : 110
     * zipcode : jmn,nxn
     */

    private String address;
    private String areaDesc;
    private String areaId;
    private String contact;
    private String id;
    private String mobilephone;
    private String phone;
    private String zipcode;

    @Generated(hash = 354053060)
    public WarehouseEntity(String address, String areaDesc, String areaId,
            String contact, String id, String mobilephone, String phone,
            String zipcode) {
        this.address = address;
        this.areaDesc = areaDesc;
        this.areaId = areaId;
        this.contact = contact;
        this.id = id;
        this.mobilephone = mobilephone;
        this.phone = phone;
        this.zipcode = zipcode;
    }

    @Generated(hash = 1548118279)
    public WarehouseEntity() {
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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}