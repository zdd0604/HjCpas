package com.hj.casps.entity.appmmbbanaccountcontroller;

import java.io.Serializable;

public class MmbBankAccountEntity implements Serializable {

    private static final long serialVersionUID = 1156896964301679438L;

    private String id;//	string	账号id
    private String accountno;//		string	账户号码
    private String accountname;//	string		账户全称
    private String bankname;//	string		银行名称
    private String contact;//	string		联系人名称
    private String mobilephone;//			string		手机号码
    private String phone;//		string	电话号码

    public MmbBankAccountEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
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

    public MmbBankAccountEntity(String id, String accountno, String accountname, String bankname, String contact, String mobilephone, String phone) {
        this.id = id;
        this.accountno = accountno;
        this.accountname = accountname;
        this.bankname = bankname;
        this.contact = contact;
        this.mobilephone = mobilephone;
        this.phone = phone;
    }
}