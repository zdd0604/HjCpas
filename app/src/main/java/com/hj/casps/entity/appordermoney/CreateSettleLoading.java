package com.hj.casps.entity.appordermoney;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/16.
 * 新建结款单
 */

public class CreateSettleLoading implements Serializable {
    private static final long serialVersionUID = 4997003984351166327L;
    private String mmbgetAccount;//string	收款账号（对方付款）
    private String mmbpayAccount;//string	付款账号（本方付款）
    private String ctrTime;//datetime		借款时间

    public CreateSettleLoading() {
    }

    public String getMmbgetAccount() {
        return mmbgetAccount;
    }

    public void setMmbgetAccount(String mmbgetAccount) {
        this.mmbgetAccount = mmbgetAccount;
    }

    public String getMmbpayAccount() {
        return mmbpayAccount;
    }

    public void setMmbpayAccount(String mmbpayAccount) {
        this.mmbpayAccount = mmbpayAccount;
    }

    public String getCtrTime() {
        return ctrTime;
    }

    public void setCtrTime(String ctrTime) {
        this.ctrTime = ctrTime;
    }

    public CreateSettleLoading(String mmbgetAccount, String mmbpayAccount, String ctrTime) {
        this.mmbgetAccount = mmbgetAccount;
        this.mmbpayAccount = mmbpayAccount;
        this.ctrTime = ctrTime;
    }
}
