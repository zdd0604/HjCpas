package com.hj.casps.cooperate;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zy on 2017/6/8.
 * 选择供应商，谁关注我的数据返回，用来进行数据库的管理
 */
@Entity
public class WhoCareListBean {
    /**
     * member_id : testschool001
     * member_name : 奥森学校
     * mmbhomepage : http://members.nxdj.org.cn/奥森学校.html
     */

    private String member_id;
    private String member_name;
    private String mmbhomepage;
    private boolean choice;

    @Generated(hash = 1904263458)
    public WhoCareListBean(String member_id, String member_name, String mmbhomepage,
            boolean choice) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.mmbhomepage = mmbhomepage;
        this.choice = choice;
    }

    @Generated(hash = 1754835681)
    public WhoCareListBean() {
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMmbhomepage() {
        return mmbhomepage;
    }

    public void setMmbhomepage(String mmbhomepage) {
        this.mmbhomepage = mmbhomepage;
    }

    public boolean getChoice() {
        return this.choice;
    }
}
