package com.hj.casps.entity.appsettle;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Admin on 2017/7/13.
 * 结款单界面的缓存
 */
@Entity
public class CheckWaitBillsEntity implements Serializable {
    private static final long serialVersionUID = -6951760522185483372L;
    @Id
    private String uuid;
    private String appSettle;//订单号
    private String billsJson;//单据JSON
    @Generated(hash = 1340693928)
    public CheckWaitBillsEntity(String uuid, String appSettle, String billsJson) {
        this.uuid = uuid;
        this.appSettle = appSettle;
        this.billsJson = billsJson;
    }
    @Generated(hash = 457716601)
    public CheckWaitBillsEntity() {
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getAppSettle() {
        return this.appSettle;
    }
    public void setAppSettle(String appSettle) {
        this.appSettle = appSettle;
    }
    public String getBillsJson() {
        return this.billsJson;
    }
    public void setBillsJson(String billsJson) {
        this.billsJson = billsJson;
    }
  
}
