package com.hj.casps.protocolmanager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zy on 2017/6/8.
 */
@Entity
public class OrderRowBean {
    /**
     * buyersAddressId : f9a6aaa8380a4ee9a5ad59fe81868361
     * buyersAddressName : 奥森学校中心食堂冷库
     * buyersName : 奥森学校
     * createTime : 1495105852000
     * executeEndTime : 1495036800000
     * executeStartTime : 1495036800000
     * executeStatus : 1
     * getAccount : 6222189929039864012
     * getBank : 中国建设银行
     * id : 5fb3e707307c4d379319d9c4ff2661ba
     * operateTime : 1495105852000
     * ordertitleCode : 10000405
     * payAccount : 9953001798001
     * payBank : 建设银行
     * payTime : 1495036800000
     * sellersAddressId : 7633cc6dd2334c8fa1090962b26a4624
     * sellersAddressName : 哦破rosy送
     * sellersName : 长城商行
     * status : 3
     * stopStatus : 1
     * totalMoney : 3246
     * userId : 475814ffe832455dba6ecdf4306b3642
     * userName : 1212
     * workflowTypeId : 3
     * buyersId : testshop001
     * sellersId : da4383de72494f5d98dc7836d25f526f
     * finishTime : 1495160276000
     * lockTime : 1495019974000
     */

    private String buyersAddressId;
    private String buyersAddressName;
    private String buyersName;
    private long createTime;
    private long executeEndTime;
    private long executeStartTime;
    private int executeStatus;
    private String getAccount;
    private String getBank;
    private String id;
    private long operateTime;
    private int ordertitleCode;
    private String payAccount;
    private String payBank;
    private long payTime;
    private String sellersAddressId;
    private String sellersAddressName;
    private String sellersName;
    private int status;
    private int stopStatus;
    private double totalMoney;
    private String userId;
    private String userName;
    private int workflowTypeId;
    private String buyersId;
    private String sellersId;
    private long finishTime;
    private long lockTime;
    private boolean choice;

    @Generated(hash = 1802923351)
    public OrderRowBean(String buyersAddressId, String buyersAddressName,
            String buyersName, long createTime, long executeEndTime,
            long executeStartTime, int executeStatus, String getAccount,
            String getBank, String id, long operateTime, int ordertitleCode,
            String payAccount, String payBank, long payTime,
            String sellersAddressId, String sellersAddressName, String sellersName,
            int status, int stopStatus, double totalMoney, String userId,
            String userName, int workflowTypeId, String buyersId, String sellersId,
            long finishTime, long lockTime, boolean choice) {
        this.buyersAddressId = buyersAddressId;
        this.buyersAddressName = buyersAddressName;
        this.buyersName = buyersName;
        this.createTime = createTime;
        this.executeEndTime = executeEndTime;
        this.executeStartTime = executeStartTime;
        this.executeStatus = executeStatus;
        this.getAccount = getAccount;
        this.getBank = getBank;
        this.id = id;
        this.operateTime = operateTime;
        this.ordertitleCode = ordertitleCode;
        this.payAccount = payAccount;
        this.payBank = payBank;
        this.payTime = payTime;
        this.sellersAddressId = sellersAddressId;
        this.sellersAddressName = sellersAddressName;
        this.sellersName = sellersName;
        this.status = status;
        this.stopStatus = stopStatus;
        this.totalMoney = totalMoney;
        this.userId = userId;
        this.userName = userName;
        this.workflowTypeId = workflowTypeId;
        this.buyersId = buyersId;
        this.sellersId = sellersId;
        this.finishTime = finishTime;
        this.lockTime = lockTime;
        this.choice = choice;
    }

    @Generated(hash = 935357013)
    public OrderRowBean() {
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

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

    public String getGetAccount() {
        return getAccount;
    }

    public void setGetAccount(String getAccount) {
        this.getAccount = getAccount;
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

    public String getSellersAddressId() {
        return sellersAddressId;
    }

    public void setSellersAddressId(String sellersAddressId) {
        this.sellersAddressId = sellersAddressId;
    }

    public String getSellersAddressName() {
        return sellersAddressName;
    }

    public void setSellersAddressName(String sellersAddressName) {
        this.sellersAddressName = sellersAddressName;
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

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
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

    public String getBuyersId() {
        return buyersId;
    }

    public void setBuyersId(String buyersId) {
        this.buyersId = buyersId;
    }

    public String getSellersId() {
        return sellersId;
    }

    public void setSellersId(String sellersId) {
        this.sellersId = sellersId;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }

    public boolean getChoice() {
        return this.choice;
    }
}
