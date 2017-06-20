package com.hj.casps.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */

public class SubLoginBean  implements Serializable{

    private static final long serialVersionUID = -5698145458425881835L;
    /**
     * mmbid : testshop001
     * mmbname : 长城商行
     * userid : e6ae4ad55d5b44769d2a54a0fedbfff7
     * username : 长城财务
     */
    private String sys_mmb;
    private String sys_mmbname;
    private String sys_user;
    private String sys_username;

    public String getSys_mmb() {
        return sys_mmb;
    }

    public void setSys_mmb(String sys_mmb) {
        this.sys_mmb = sys_mmb;
    }

    public String getSys_mmbname() {
        return sys_mmbname;
    }

    public void setSys_mmbname(String sys_mmbname) {
        this.sys_mmbname = sys_mmbname;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    public String getSys_username() {
        return sys_username;
    }

    public void setSys_username(String sys_username) {
        this.sys_username = sys_username;
    }
}
