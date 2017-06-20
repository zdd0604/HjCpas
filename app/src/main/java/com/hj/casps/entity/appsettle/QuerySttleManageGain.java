package com.hj.casps.entity.appsettle;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Admin on 2017/5/5.
 * 执行中的结款单列表 返回参数
 */
@Entity
public class QuerySttleManageGain implements Serializable {

    private static final long serialVersionUID = 7013162270796610716L;

    /**
     * ctrMoney : 785
     * ctrTime : 1501430400000
     * id : e57a94b7713a426fb1c49974bb87053f
     * mmbgetId : testshop001
     * mmbgetName : 长城商行
     * mmbpayId : testschool001
     * mmbpayName : 奥森学校
     * settleCode : 40000901
     * settleMoney : 785
     * status : 4
     */
    @Id
    private String uuid;
    private int register_id;//登记资源列表；类型
    private double ctrMoney;
    private long ctrTime;
    private String id;
    private String mmbgetId;
    private String mmbgetName;
    private String mmbpayId;
    private String mmbpayName;
    private int settleCode;
    private double settleMoney;
    private int status;

    @Generated(hash = 831777361)
    public QuerySttleManageGain(String uuid, int register_id, double ctrMoney,
            long ctrTime, String id, String mmbgetId, String mmbgetName,
            String mmbpayId, String mmbpayName, int settleCode, double settleMoney,
            int status) {
        this.uuid = uuid;
        this.register_id = register_id;
        this.ctrMoney = ctrMoney;
        this.ctrTime = ctrTime;
        this.id = id;
        this.mmbgetId = mmbgetId;
        this.mmbgetName = mmbgetName;
        this.mmbpayId = mmbpayId;
        this.mmbpayName = mmbpayName;
        this.settleCode = settleCode;
        this.settleMoney = settleMoney;
        this.status = status;
    }

    @Generated(hash = 1382100803)
    public QuerySttleManageGain() {
    }

    public double getCtrMoney() {
        return ctrMoney;
    }

    public void setCtrMoney(double ctrMoney) {
        this.ctrMoney = ctrMoney;
    }

    public long getCtrTime() {
        return ctrTime;
    }

    public void setCtrTime(long ctrTime) {
        this.ctrTime = ctrTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMmbgetId() {
        return mmbgetId;
    }

    public void setMmbgetId(String mmbgetId) {
        this.mmbgetId = mmbgetId;
    }

    public String getMmbgetName() {
        return mmbgetName;
    }

    public void setMmbgetName(String mmbgetName) {
        this.mmbgetName = mmbgetName;
    }

    public String getMmbpayId() {
        return mmbpayId;
    }

    public void setMmbpayId(String mmbpayId) {
        this.mmbpayId = mmbpayId;
    }

    public String getMmbpayName() {
        return mmbpayName;
    }

    public void setMmbpayName(String mmbpayName) {
        this.mmbpayName = mmbpayName;
    }

    public int getSettleCode() {
        return settleCode;
    }

    public void setSettleCode(int settleCode) {
        this.settleCode = settleCode;
    }

    public double getSettleMoney() {
        return settleMoney;
    }

    public void setSettleMoney(double settleMoney) {
        this.settleMoney = settleMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getRegister_id() {
        return this.register_id;
    }

    public void setRegister_id(int register_id) {
        this.register_id = register_id;
    }
}
