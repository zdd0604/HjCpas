package com.hj.casps.protocolmanager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zy on 2017/6/8.
 * 订单管理的返回数据，用来做数据库缓存
 */
@Entity
public class ProtocolListBean {
    /**
     * buy_mmb_id : testschool001
     * contract_id : 66457abab66947149194c05c6092eb16
     * contract_status : 1待确认，4已拒绝，5中止申请，3已完成，7已中止
     * contract_title : 1234567
     * end_time : 1494864000000
     * flow_type : 自取
     * mmbname : 长城商行
     * pay_type : 每月
     * start_time : 1494864000000
     */

    private String buy_mmb_id;
    private String contract_id;
    private int contract_status;
    private String contract_title;
    private long end_time;
    private String flow_type;
    private String mmbname;
    private String pay_type;
    private long start_time;

    @Generated(hash = 1708763398)
    public ProtocolListBean(String buy_mmb_id, String contract_id,
            int contract_status, String contract_title, long end_time,
            String flow_type, String mmbname, String pay_type, long start_time) {
        this.buy_mmb_id = buy_mmb_id;
        this.contract_id = contract_id;
        this.contract_status = contract_status;
        this.contract_title = contract_title;
        this.end_time = end_time;
        this.flow_type = flow_type;
        this.mmbname = mmbname;
        this.pay_type = pay_type;
        this.start_time = start_time;
    }

    @Generated(hash = 1992150762)
    public ProtocolListBean() {
    }

    public String getBuy_mmb_id() {
        return buy_mmb_id;
    }

    public void setBuy_mmb_id(String buy_mmb_id) {
        this.buy_mmb_id = buy_mmb_id;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public int getContract_status() {
        return contract_status;
    }

    public void setContract_status(int contract_status) {
        this.contract_status = contract_status;
    }

    public String getContract_title() {
        return contract_title;
    }

    public void setContract_title(String contract_title) {
        this.contract_title = contract_title;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getFlow_type() {
        return flow_type;
    }

    public void setFlow_type(String flow_type) {
        this.flow_type = flow_type;
    }

    public String getMmbname() {
        return mmbname;
    }

    public void setMmbname(String mmbname) {
        this.mmbname = mmbname;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }
}
