package com.hj.casps.cooperate;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zy on 2017/4/21.
 * 关注会员目录的返回类，用来做缓存
 */
@Entity
public class CooperateModel {
    private String fname;
    private String address;
    private String memberId;
    private int grade;
    private int type;
    @Generated(hash = 307681191)
    public CooperateModel(String fname, String address, String memberId, int grade,
            int type) {
        this.fname = fname;
        this.address = address;
        this.memberId = memberId;
        this.grade = grade;
        this.type = type;
    }
    @Generated(hash = 889027169)
    public CooperateModel() {
    }
    public String getFname() {
        return this.fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getMemberId() {
        return this.memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public int getGrade() {
        return this.grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }



   
}
