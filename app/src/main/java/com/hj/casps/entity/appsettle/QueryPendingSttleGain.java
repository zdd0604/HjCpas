package com.hj.casps.entity.appsettle;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/5.
 * 获取待审批结款单列表 接口返回参数
 */
@Entity
public class QueryPendingSttleGain implements Serializable {

    private static final long serialVersionUID = 4316165052233678936L;

    @Transient
    private String myTime;// datetime		我的提议时间
    @Transient
    private String myMoney;//double	我的提议金额
    @Transient
    private boolean isCheck;
    /**
     * ctrMoney : 2000
     * ctrTime : 1495036800000
     * id : 01761d5eeb7646ae871312afa8bc56ca
     * mmbgetId : testshop001
     * mmbgetName : 长城商行
     * mmbpayId : testschool001
     * mmbpayName : 奥森学校
     * settleCode : 40039118
     * settleMoney : 2000
     */

    private double ctrMoney;
    private long ctrTime;
    private String id;
    private String mmbgetId;
    private String mmbgetName;
    private String mmbpayId;
    private String mmbpayName;
    private int settleCode;
    private double settleMoney;
    @Generated(hash = 1823085708)
    public QueryPendingSttleGain(double ctrMoney, long ctrTime, String id,
            String mmbgetId, String mmbgetName, String mmbpayId, String mmbpayName,
            int settleCode, double settleMoney) {
        this.ctrMoney = ctrMoney;
        this.ctrTime = ctrTime;
        this.id = id;
        this.mmbgetId = mmbgetId;
        this.mmbgetName = mmbgetName;
        this.mmbpayId = mmbpayId;
        this.mmbpayName = mmbpayName;
        this.settleCode = settleCode;
        this.settleMoney = settleMoney;
    }
    @Generated(hash = 888394928)
    public QueryPendingSttleGain() {
    }
    public double getCtrMoney() {
        return this.ctrMoney;
    }
    public void setCtrMoney(double ctrMoney) {
        this.ctrMoney = ctrMoney;
    }
    public long getCtrTime() {
        return this.ctrTime;
    }
    public void setCtrTime(long ctrTime) {
        this.ctrTime = ctrTime;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMmbgetId() {
        return this.mmbgetId;
    }
    public void setMmbgetId(String mmbgetId) {
        this.mmbgetId = mmbgetId;
    }
    public String getMmbgetName() {
        return this.mmbgetName;
    }
    public void setMmbgetName(String mmbgetName) {
        this.mmbgetName = mmbgetName;
    }
    public String getMmbpayId() {
        return this.mmbpayId;
    }
    public void setMmbpayId(String mmbpayId) {
        this.mmbpayId = mmbpayId;
    }
    public String getMmbpayName() {
        return this.mmbpayName;
    }
    public void setMmbpayName(String mmbpayName) {
        this.mmbpayName = mmbpayName;
    }
    public int getSettleCode() {
        return this.settleCode;
    }
    public void setSettleCode(int settleCode) {
        this.settleCode = settleCode;
    }
    public double getSettleMoney() {
        return this.settleMoney;
    }
    public void setSettleMoney(double settleMoney) {
        this.settleMoney = settleMoney;
    }

    public String getMyTime() {
        return myTime;
    }

    public void setMyTime(String myTime) {
        this.myTime = myTime;
    }

    public String getMyMoney() {
        return myMoney;
    }

    public void setMyMoney(String myMoney) {
        this.myMoney = myMoney;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
