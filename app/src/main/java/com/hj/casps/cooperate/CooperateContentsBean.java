package com.hj.casps.cooperate;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zy on 2017/6/8.
 * 关注会员目录的返回结果，用来做缓存
 */
@Entity
public class CooperateContentsBean {
    /**
     * mark_member : c1559f7f457c44d0a6a72b9e0137fe55
     * mmbaddress : 北京市海淀区中关村路155号
     * mmbfName : APP测试学校
     * mmbhomepage :
     * relagrade : 2
     * relationship_id : fb40e46a6c0c4f559877b4d49570afaf
     */

    private String mark_member;
    private String mmbaddress;
    private String mmbfName;
    private String mmbhomepage;
    private int relagrade;
    private String relationship_id;

    @Generated(hash = 2139845930)
    public CooperateContentsBean(String mark_member, String mmbaddress,
            String mmbfName, String mmbhomepage, int relagrade,
            String relationship_id) {
        this.mark_member = mark_member;
        this.mmbaddress = mmbaddress;
        this.mmbfName = mmbfName;
        this.mmbhomepage = mmbhomepage;
        this.relagrade = relagrade;
        this.relationship_id = relationship_id;
    }

    @Generated(hash = 141106917)
    public CooperateContentsBean() {
    }

    public String getMark_member() {
        return mark_member;
    }

    public void setMark_member(String mark_member) {
        this.mark_member = mark_member;
    }

    public String getMmbaddress() {
        return mmbaddress;
    }

    public void setMmbaddress(String mmbaddress) {
        this.mmbaddress = mmbaddress;
    }

    public String getMmbfName() {
        return mmbfName;
    }

    public void setMmbfName(String mmbfName) {
        this.mmbfName = mmbfName;
    }

    public String getMmbhomepage() {
        return mmbhomepage;
    }

    public void setMmbhomepage(String mmbhomepage) {
        this.mmbhomepage = mmbhomepage;
    }

    public int getRelagrade() {
        return relagrade;
    }

    public void setRelagrade(int relagrade) {
        this.relagrade = relagrade;
    }

    public String getRelationship_id() {
        return relationship_id;
    }

    public void setRelationship_id(String relationship_id) {
        this.relationship_id = relationship_id;
    }
}
