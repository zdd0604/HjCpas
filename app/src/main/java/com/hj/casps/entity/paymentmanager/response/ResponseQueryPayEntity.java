package com.hj.casps.entity.paymentmanager.response;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class ResponseQueryPayEntity  {

    //本次付款金额
    private String payNum;
    //本次付款账号
    private String payMentCode;
    //备注信息
    private String reMark;

    /**
     * accountlist : [{"accountno":"12312","bankname":"123123","id":"4d4c7214e64a4dc696de9d5edc168dfd"},{"accountno":"622156561677611","bankname":"农业银行","id":"e2084e95335243c29f5903565d379610"},{"accountno":"6222189929039864012","bankname":"中国建设银行","id":"fc992fffb07442339239a25c521b7ed5"}]
     * exePaymoneyNum : 15995
     * goodsName : 油菜
     * id : 7c63a11218124fecb5fef58e2ea3b922
     * money : 15996
     * ordertitleId : e5805c08f2d047569f307549dc7783ae
     * ordertitleNumber : 10036100
     * paymoneyNum : 1
     * sellersName : cyh
     */
    //是否选中
    private boolean isChecked;
    private double exePaymoneyNum;
    private String goodsName;
    private String id;
    private int money;
    private String ordertitleId;
    private int ordertitleNumber;
    private double paymoneyNum;
    private String sellersName;
    private List<AccountlistBean> accountlist;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public double getExePaymoneyNum() {
        return exePaymoneyNum;
    }

    public void setExePaymoneyNum(double exePaymoneyNum) {
        this.exePaymoneyNum = exePaymoneyNum;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getOrdertitleId() {
        return ordertitleId;
    }

    public void setOrdertitleId(String ordertitleId) {
        this.ordertitleId = ordertitleId;
    }

    public int getOrdertitleNumber() {
        return ordertitleNumber;
    }

    public void setOrdertitleNumber(int ordertitleNumber) {
        this.ordertitleNumber = ordertitleNumber;
    }

    public double getPaymoneyNum() {
        return paymoneyNum;
    }

    public void setPaymoneyNum(double paymoneyNum) {
        this.paymoneyNum = paymoneyNum;
    }

    public String getSellersName() {
        return sellersName;
    }

    public void setSellersName(String sellersName) {
        this.sellersName = sellersName;
    }

    public List<AccountlistBean> getAccountlist() {
        return accountlist;
    }

    public void setAccountlist(List<AccountlistBean> accountlist) {
        this.accountlist = accountlist;
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

    public void clearData() {
        this.payNum = "";
        this.payMentCode = "";
        this.reMark = "";
    }
}
