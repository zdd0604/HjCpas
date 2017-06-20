package com.hj.casps.entity.paymentmanager.response;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */

public class ResRefundMoneyOfflineEntity {

    //本次付款金额
    private String payNum;
    //本次付款账号
    private String payMentCode;
    //备注信息
    private String reMark;


    /**
     * accountlist : [{"accountno":"12312","bankname":"123123","id":"4d4c7214e64a4dc696de9d5edc168dfd"},{"accountno":"622156561677611","bankname":"农业银行","id":"e2084e95335243c29f5903565d379610"},{"accountno":"6222189929039864012","bankname":"中国建设银行","id":"fc992fffb07442339239a25c521b7ed5"}]
     * buyersName : 奥森学校
     * exeRefundNum : 66
     * goodsName : 散装农场青菜
     * id : 2f364bdfb99746eabed89f6a4715a394
     * ordertitleNumber : 10038443
     */
    private boolean isChecked;
    private String buyersName;
    private double exeRefundNum;
    private String goodsName;
    private String id;
    private String ordertitleId;
    private int ordertitleNumber;
    private List<AccountlistBean> accountlist;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getBuyersName() {
        return buyersName;
    }

    public void setBuyersName(String buyersName) {
        this.buyersName = buyersName;
    }

    public double getExeRefundNum() {
        return exeRefundNum;
    }

    public void setExeRefundNum(double exeRefundNum) {
        this.exeRefundNum = exeRefundNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrdertitleNumber() {
        return ordertitleNumber;
    }

    public void setOrdertitleNumber(int ordertitleNumber) {
        this.ordertitleNumber = ordertitleNumber;
    }

    public List<AccountlistBean> getAccountlist() {
        return accountlist;
    }

    public void setAccountlist(List<AccountlistBean> accountlist) {
        this.accountlist = accountlist;
    }

    public static class AccountlistBean {
        /**
         * accountno : 12312
         * bankname : 123123
         * id : 4d4c7214e64a4dc696de9d5edc168dfd
         */

        private String accountno;
        private String bankname;
        private String id;

        public String getAccountno() {
            return accountno;
        }

        public void setAccountno(String accountno) {
            this.accountno = accountno;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public String getPayMentCode() {
        return payMentCode;
    }

    public void setPayMentCode(String payMentCode) {
        this.payMentCode = payMentCode;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }


    public void clearData() {
        payNum = "";
        payMentCode = "";
        reMark = "";
    }

    public String getOrdertitleId() {
        return ordertitleId;
    }

    public void setOrdertitleId(String ordertitleId) {
        this.ordertitleId = ordertitleId;
    }
}
