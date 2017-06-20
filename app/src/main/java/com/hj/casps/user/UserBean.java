package com.hj.casps.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by 鑫 Administrator on 2017/5/5.
 */
@Entity
public class UserBean {

    private Long id;
    private String sys_account;
    private String sys_pwd;
    @Id
    private String sys_user; /*用户id*/
    private String token; /*token*/
    private String sys_mmb; /*当前会员id*/
    private String sys_username; /*操作员名称*/
    private String sys_mmbname; /*当前会员名称*/
    private boolean isActive; /*当前用户*/
    private boolean tokenIsActive; /*token是否有效*/
    @Generated(hash = 43828908)
    public UserBean(Long id, String sys_account, String sys_pwd, String sys_user,
            String token, String sys_mmb, String sys_username, String sys_mmbname,
            boolean isActive, boolean tokenIsActive) {
        this.id = id;
        this.sys_account = sys_account;
        this.sys_pwd = sys_pwd;
        this.sys_user = sys_user;
        this.token = token;
        this.sys_mmb = sys_mmb;
        this.sys_username = sys_username;
        this.sys_mmbname = sys_mmbname;
        this.isActive = isActive;
        this.tokenIsActive = tokenIsActive;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSys_account() {
        return this.sys_account;
    }
    public void setSys_account(String sys_account) {
        this.sys_account = sys_account;
    }
    public String getSys_pwd() {
        return this.sys_pwd;
    }
    public void setSys_pwd(String sys_pwd) {
        this.sys_pwd = sys_pwd;
    }
    public String getSys_user() {
        return this.sys_user;
    }
    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getSys_mmb() {
        return this.sys_mmb;
    }
    public void setSys_mmb(String sys_mmb) {
        this.sys_mmb = sys_mmb;
    }
    public String getSys_username() {
        return this.sys_username;
    }
    public void setSys_username(String sys_username) {
        this.sys_username = sys_username;
    }
    public String getSys_mmbname() {
        return this.sys_mmbname;
    }
    public void setSys_mmbname(String sys_mmbname) {
        this.sys_mmbname = sys_mmbname;
    }
    public boolean getIsActive() {
        return this.isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean getTokenIsActive() {
        return this.tokenIsActive;
    }
    public void setTokenIsActive(boolean tokenIsActive) {
        this.tokenIsActive = tokenIsActive;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", sys_account='" + sys_account + '\'' +
                ", sys_pwd='" + sys_pwd + '\'' +
                ", sys_user='" + sys_user + '\'' +
                ", token='" + token + '\'' +
                ", sys_mmb='" + sys_mmb + '\'' +
                ", sys_username='" + sys_username + '\'' +
                ", sys_mmbname='" + sys_mmbname + '\'' +
                ", isActive=" + isActive +
                ", tokenIsActive=" + tokenIsActive +
                '}';
    }
}