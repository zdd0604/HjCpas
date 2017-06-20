package com.hj.casps.entity.registerassure;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/2.
 * 结款单登记担保列表
 */

public class RegisterAssureGain implements Serializable {
    private static final long serialVersionUID = 5562598223723920005L;
    private int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    private int pagecount;//	int	符合条件记录总页数
    private List<AssureBills> mList;

    public RegisterAssureGain(int return_code, int pagecount, List<AssureBills> mList) {
        this.return_code = return_code;
        this.pagecount = pagecount;
        this.mList = mList;
    }

    public RegisterAssureGain() {
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public List<AssureBills> getmList() {
        return mList;
    }

    public void setmList(List<AssureBills> mList) {
        this.mList = mList;
    }

    public static class AssureBills implements Serializable {
        private static final long serialVersionUID = -84656756049556740L;
        private String id;//string	结款单id
        private String settleCode;//string	结款单号
        private String mmbgetName;//	string		结款对方
        private String mmbpayName;//	string		结款对方（mmbpayId等于mmbgetName显示mmbpayName否则显示mmbgetName）
        private double settleMoney;//	Double		结款订单金额
        private String ctrTime;//	Date		预计结款时间
        private double ctrMoney;//		Double	约定金额
        private double gotMoney;//double	已付金额
        private String statusRegist;//	string	担保资源状态（statusRegist =1？申请登记：提交申请待审）

        public AssureBills(String id, String settleCode, String mmbgetName, String mmbpayName, double settleMoney, String ctrTime, double ctrMoney, double gotMoney, String statusRegist) {
            this.id = id;
            this.settleCode = settleCode;
            this.mmbgetName = mmbgetName;
            this.mmbpayName = mmbpayName;
            this.settleMoney = settleMoney;
            this.ctrTime = ctrTime;
            this.ctrMoney = ctrMoney;
            this.gotMoney = gotMoney;
            this.statusRegist = statusRegist;
        }

        public AssureBills() {
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSettleCode() {
            return settleCode;
        }

        public void setSettleCode(String settleCode) {
            this.settleCode = settleCode;
        }

        public String getMmbgetName() {
            return mmbgetName;
        }

        public void setMmbgetName(String mmbgetName) {
            this.mmbgetName = mmbgetName;
        }

        public String getMmbpayName() {
            return mmbpayName;
        }

        public void setMmbpayName(String mmbpayName) {
            this.mmbpayName = mmbpayName;
        }

        public double getSettleMoney() {
            return settleMoney;
        }

        public void setSettleMoney(double settleMoney) {
            this.settleMoney = settleMoney;
        }

        public String getCtrTime() {
            return ctrTime;
        }

        public void setCtrTime(String ctrTime) {
            this.ctrTime = ctrTime;
        }

        public double getCtrMoney() {
            return ctrMoney;
        }

        public void setCtrMoney(double ctrMoney) {
            this.ctrMoney = ctrMoney;
        }

        public double getGotMoney() {
            return gotMoney;
        }

        public void setGotMoney(double gotMoney) {
            this.gotMoney = gotMoney;
        }

        public String getStatusRegist() {
            return statusRegist;
        }

        public void setStatusRegist(String statusRegist) {
            this.statusRegist = statusRegist;
        }
    }
}
