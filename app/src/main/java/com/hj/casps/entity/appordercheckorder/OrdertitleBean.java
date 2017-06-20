package com.hj.casps.entity.appordercheckorder;

import java.io.Serializable;

public class OrdertitleBean<T> implements Serializable {
    private static final long serialVersionUID = -994762971189560003L;
    /**
         * buyersAddressId : adbc73908b5841e3bc61ff12780e9610
         * buyersAddressName : 奥森学校教工食堂
         * buyersId : testschool001
         * buyersName : 奥森学校
         * createTime : 1489041803000
         * executeEndTime : 1488988800000
         * executeStartTime : 1488988800000
         * executeStatus : 1
         * getBank :
         * id : ea9cdbd8c4bf40bea46794b1627ab351
         * lockTime : 1489041961000
         * operateTime : 1494385494000
         * orderList : [{"buyersId":"testschool001","buyersName":"奥森学校","categoryId":"1002001001","createTime":1489041803000,"exeGetgoodsNum":215,"exeGetmoneyNum":0,"exeGetrefundNum":0,"exeGetreturngoodsNum":0,"exePaymoneyNum":5175,"exeRefundNum":0,"exeReturngoodsNum":1035,"exeSendgoodsNum":1750,"executeStatus":1,"getgoodsNum":1035,"getmoneyNum":0,"getrefundNum":0,"getreturngoodsNum":0,"goodsId":"355f67ee7b174b269d3c6e8cf30371bc","goodsName":"散装农场青菜","goodsNum":3000,"id":"2f364bdfb99746eabed89f6a4715a394","lockTime":1489041961000,"lockmoneyNum":0,"money":15000,"ordertitleNumber":10038443,"oredertitleCode":"ea9cdbd8c4bf40bea46794b1627ab351","paymoneyNum":0,"price":5,"quoteId":"30037900","refundNum":0,"returngoodsNum":0,"sellersId":"testshop001","sellersName":"长城商行","sendgoodsNum":1250,"status":4,"stopStatus":3,"userId":"1b64b63c75ca428eb9ca77999f1e895e","userName":"长城商行一号经理","workflowType":2}]
         * ordertitleCode : 10038443
         * payAccount : 9953001798001
         * payBank : 建设银行
         * payTime : 1488988800000
         * sellersAddressName :
         * sellersId : testshop001
         * sellersName : 长城商行
         * status : 4
         * stopStatus : 3
         * totalMoney : 15000
         * userId : 945fa151f0ce4dc6986ddd13728af39d
         * userName : 全功能
         * workflowTypeId : 2
         */

        public String buyersAddressId;
        public String buyersAddressName;
        public String buyersId;
        public String buyersName;
        public long createTime;
        public long executeEndTime;
        public long executeStartTime;
        public int executeStatus;
        public String getBank;
        public String id;
        public long lockTime;
        public long operateTime;
        public int ordertitleCode;
        public String payAccount;
        public String payBank;
        public long payTime;
        public String sellersAddressName;
        public String sellersId;
        public String sellersName;
        public int status;
        public int stopStatus;
        public int totalMoney;
        public String userId;
        public String userName;
        public int workflowTypeId;
        public T orderList;

        public String getBuyersAddressId() {
            return buyersAddressId;
        }

        public void setBuyersAddressId(String buyersAddressId) {
            this.buyersAddressId = buyersAddressId;
        }

        public String getBuyersAddressName() {
            return buyersAddressName;
        }

        public void setBuyersAddressName(String buyersAddressName) {
            this.buyersAddressName = buyersAddressName;
        }

        public String getBuyersId() {
            return buyersId;
        }

        public void setBuyersId(String buyersId) {
            this.buyersId = buyersId;
        }

        public String getBuyersName() {
            return buyersName;
        }

        public void setBuyersName(String buyersName) {
            this.buyersName = buyersName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getExecuteEndTime() {
            return executeEndTime;
        }

        public void setExecuteEndTime(long executeEndTime) {
            this.executeEndTime = executeEndTime;
        }

        public long getExecuteStartTime() {
            return executeStartTime;
        }

        public void setExecuteStartTime(long executeStartTime) {
            this.executeStartTime = executeStartTime;
        }

        public int getExecuteStatus() {
            return executeStatus;
        }

        public void setExecuteStatus(int executeStatus) {
            this.executeStatus = executeStatus;
        }

        public String getGetBank() {
            return getBank;
        }

        public void setGetBank(String getBank) {
            this.getBank = getBank;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getLockTime() {
            return lockTime;
        }

        public void setLockTime(long lockTime) {
            this.lockTime = lockTime;
        }

        public long getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(long operateTime) {
            this.operateTime = operateTime;
        }

        public int getOrdertitleCode() {
            return ordertitleCode;
        }

        public void setOrdertitleCode(int ordertitleCode) {
            this.ordertitleCode = ordertitleCode;
        }

        public String getPayAccount() {
            return payAccount;
        }

        public void setPayAccount(String payAccount) {
            this.payAccount = payAccount;
        }

        public String getPayBank() {
            return payBank;
        }

        public void setPayBank(String payBank) {
            this.payBank = payBank;
        }

        public long getPayTime() {
            return payTime;
        }

        public void setPayTime(long payTime) {
            this.payTime = payTime;
        }

        public String getSellersAddressName() {
            return sellersAddressName;
        }

        public void setSellersAddressName(String sellersAddressName) {
            this.sellersAddressName = sellersAddressName;
        }

        public String getSellersId() {
            return sellersId;
        }

        public void setSellersId(String sellersId) {
            this.sellersId = sellersId;
        }

        public String getSellersName() {
            return sellersName;
        }

        public void setSellersName(String sellersName) {
            this.sellersName = sellersName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStopStatus() {
            return stopStatus;
        }

        public void setStopStatus(int stopStatus) {
            this.stopStatus = stopStatus;
        }

        public int getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(int totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getWorkflowTypeId() {
            return workflowTypeId;
        }

        public void setWorkflowTypeId(int workflowTypeId) {
            this.workflowTypeId = workflowTypeId;
        }

    }