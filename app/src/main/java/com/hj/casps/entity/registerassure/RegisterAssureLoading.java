package com.hj.casps.entity.registerassure;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/2.
 * 结款单登记列表
 */

public class RegisterAssureLoading implements Serializable {
    private static final long serialVersionUID = -5560312272835364825L;
    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//	string	会员id
    private String settleCode;//	string	结款单号
    private String oppositeName;//		string	结款对方
    private String statusRegist;//	string	状态（0全部、1未申请登记、2已申请登记）
    private String executeStartTime;//	datetime		开始时间
    private String executeEndTime;//	datetime		结束时间
    private int pageno;//	int	页号
    private int pagesize;//	int	页行数

    public RegisterAssureLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String settleCode, String oppositeName, String statusRegist, String executeStartTime, String executeEndTime, int pageno, int pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.settleCode = settleCode;
        this.oppositeName = oppositeName;
        this.statusRegist = statusRegist;
        this.executeStartTime = executeStartTime;
        this.executeEndTime = executeEndTime;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

    public RegisterAssureLoading() {
    }

    public String getSys_token() {
        return sys_token;
    }

    public void setSys_token(String sys_token) {
        this.sys_token = sys_token;
    }

    public String getSys_uuid() {
        return sys_uuid;
    }

    public void setSys_uuid(String sys_uuid) {
        this.sys_uuid = sys_uuid;
    }

    public String getSys_func() {
        return sys_func;
    }

    public void setSys_func(String sys_func) {
        this.sys_func = sys_func;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    public String getSys_member() {
        return sys_member;
    }

    public void setSys_member(String sys_member) {
        this.sys_member = sys_member;
    }

    public String getSettleCode() {
        return settleCode;
    }

    public void setSettleCode(String settleCode) {
        this.settleCode = settleCode;
    }

    public String getOppositeName() {
        return oppositeName;
    }

    public void setOppositeName(String oppositeName) {
        this.oppositeName = oppositeName;
    }

    public String getStatusRegist() {
        return statusRegist;
    }

    public void setStatusRegist(String statusRegist) {
        this.statusRegist = statusRegist;
    }

    public String getExecuteStartTime() {
        return executeStartTime;
    }

    public void setExecuteStartTime(String executeStartTime) {
        this.executeStartTime = executeStartTime;
    }

    public String getExecuteEndTime() {
        return executeEndTime;
    }

    public void setExecuteEndTime(String executeEndTime) {
        this.executeEndTime = executeEndTime;
    }

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
