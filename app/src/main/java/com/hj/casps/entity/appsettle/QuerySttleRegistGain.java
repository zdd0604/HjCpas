package com.hj.casps.entity.appsettle;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/5.
 * 结款单登记担保列表 返回参数
 */
@Entity
public class QuerySttleRegistGain implements Serializable {
    private static final long serialVersionUID = 710773690658188163L;
    @Id
    private String uuid;
    private int register_id;//登记资源列表；类型
    private String id;//string	结款单id
    private String settleCode;//	string	结款单号
    private String mmbgetId;//	string		收款方id
    private String mmbgpayId;//	string		付款方id
    private String mmbgetName;//	string		收款方名称
    private String mmbpayName;//string		付款方名称（mmbpayId等于当前会员id，显示mmbpayName否则显示mmbgetName）
    private double settleMoney;//Double		结款订单金额
    private long ctrTime;//	Date		预计结款时间
    private double ctrMoney;//	Double	约定金额
    private double gotMoney;//double	已付金额
    private String statusRegist;//	string	担保资源状态（statusRegist =1？申请登记：提交申请待审）


    @Generated(hash = 312179217)
    public QuerySttleRegistGain(String uuid, int register_id, String id, String settleCode,
            String mmbgetId, String mmbgpayId, String mmbgetName, String mmbpayName,
            double settleMoney, long ctrTime, double ctrMoney, double gotMoney,
            String statusRegist) {
        this.uuid = uuid;
        this.register_id = register_id;
        this.id = id;
        this.settleCode = settleCode;
        this.mmbgetId = mmbgetId;
        this.mmbgpayId = mmbgpayId;
        this.mmbgetName = mmbgetName;
        this.mmbpayName = mmbpayName;
        this.settleMoney = settleMoney;
        this.ctrTime = ctrTime;
        this.ctrMoney = ctrMoney;
        this.gotMoney = gotMoney;
        this.statusRegist = statusRegist;
    }


    @Generated(hash = 670431015)
    public QuerySttleRegistGain() {
    }


    @Override
    public String toString() {
        return "QuerySttleRegistGain{" +
                "register_id=" + register_id +
                ", id='" + id + '\'' +
                ", settleCode='" + settleCode + '\'' +
                ", mmbgetId='" + mmbgetId + '\'' +
                ", mmbgpayId='" + mmbgpayId + '\'' +
                ", mmbgetName='" + mmbgetName + '\'' +
                ", mmbpayName='" + mmbpayName + '\'' +
                ", settleMoney=" + settleMoney +
                ", ctrTime=" + ctrTime +
                ", ctrMoney=" + ctrMoney +
                ", gotMoney=" + gotMoney +
                ", statusRegist='" + statusRegist + '\'' +
                '}';
    }


    public int getRegister_id() {
        return this.register_id;
    }


    public void setRegister_id(int register_id) {
        this.register_id = register_id;
    }


    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getSettleCode() {
        return this.settleCode;
    }


    public void setSettleCode(String settleCode) {
        this.settleCode = settleCode;
    }


    public String getMmbgetId() {
        return this.mmbgetId;
    }


    public void setMmbgetId(String mmbgetId) {
        this.mmbgetId = mmbgetId;
    }


    public String getMmbgpayId() {
        return this.mmbgpayId;
    }


    public void setMmbgpayId(String mmbgpayId) {
        this.mmbgpayId = mmbgpayId;
    }


    public String getMmbgetName() {
        return this.mmbgetName;
    }


    public void setMmbgetName(String mmbgetName) {
        this.mmbgetName = mmbgetName;
    }


    public String getMmbpayName() {
        return this.mmbpayName;
    }


    public void setMmbpayName(String mmbpayName) {
        this.mmbpayName = mmbpayName;
    }


    public double getSettleMoney() {
        return this.settleMoney;
    }


    public void setSettleMoney(double settleMoney) {
        this.settleMoney = settleMoney;
    }


    public long getCtrTime() {
        return this.ctrTime;
    }


    public void setCtrTime(long ctrTime) {
        this.ctrTime = ctrTime;
    }


    public double getCtrMoney() {
        return this.ctrMoney;
    }


    public void setCtrMoney(double ctrMoney) {
        this.ctrMoney = ctrMoney;
    }


    public double getGotMoney() {
        return this.gotMoney;
    }


    public void setGotMoney(double gotMoney) {
        this.gotMoney = gotMoney;
    }


    public String getStatusRegist() {
        return this.statusRegist;
    }


    public void setStatusRegist(String statusRegist) {
        this.statusRegist = statusRegist;
    }


    public String getUuid() {
        return this.uuid;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


}
