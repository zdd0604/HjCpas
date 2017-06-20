package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/4.
 * 添加新地址确认提交 JSON实体
 */

public class CreateMmbWarehouseLoading implements Serializable {
    private static final long serialVersionUID = 286279475332917621L;

    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_name;//string	用户名
    private String sys_member;//	string	会员id

    private String address;//string	地址
    private String areaId;//	string	所属地域ID
    private String zipcode;//string	邮编
    private String contact;//string	联系人名称
    private String mobilephone;//	string	手机号
    private String phone;//string	电话号码
    private String id;//string	地址id

    /**
     * 编辑提交
     *
     * @param sys_token
     * @param sys_uuid
     * @param sys_func
     * @param sys_user
     * @param sys_name
     * @param sys_member
     * @param address
     * @param areaId
     * @param zipcode
     * @param contact
     * @param mobilephone
     * @param phone
     * @param id
     */
    public CreateMmbWarehouseLoading(String sys_token, String sys_uuid, String sys_func, String sys_user,
                                     String sys_name, String sys_member, String address,
                                     String areaId, String zipcode, String contact,
                                     String mobilephone, String phone, String id) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.address = address;
        this.areaId = areaId;
        this.zipcode = zipcode;
        this.contact = contact;
        this.mobilephone = mobilephone;
        this.phone = phone;
        this.id = id;
    }


    /**
     * 添加确认
     *
     * @param sys_token
     * @param sys_uuid
     * @param sys_func
     * @param sys_user
     * @param sys_name
     * @param sys_member
     * @param address
     * @param areaId
     * @param zipcode
     * @param contact
     * @param mobilephone
     * @param phone
     */
    public CreateMmbWarehouseLoading(String sys_token, String sys_uuid, String sys_func,
                                     String sys_user, String sys_name, String sys_member, String address,
                                     String areaId, String zipcode, String contact,
                                     String mobilephone, String phone) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.address = address;
        this.areaId = areaId;
        this.zipcode = zipcode;
        this.contact = contact;
        this.mobilephone = mobilephone;
        this.phone = phone;
    }
}
