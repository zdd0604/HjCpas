package com.hj.casps.entity.appquote;

import java.io.Serializable;

/**
 * Created by Admin on 2017/6/30.
 * 接口url：appQuote/addMmbIds
 * 功能描述：会员确定按钮  添加会员关系
 */

public class AddMmbIdsEntity implements Serializable {

    private static final long serialVersionUID = 757937897758062533L;
    private String sys_token;
    private String sys_func;
    private String sys_uuid;
    private String sys_member;
    private String sys_name;
    private String sys_user;
    private String mmbIds;
    private String quoteId;
    private String rangType;

    public AddMmbIdsEntity() {
    }

    public AddMmbIdsEntity(String sys_token, String sys_func, String sys_uuid, String sys_member,
                           String sys_name, String sys_user, String mmbIds, String quoteId, String rangType) {
        this.sys_token = sys_token;
        this.sys_func = sys_func;
        this.sys_uuid = sys_uuid;
        this.sys_member = sys_member;
        this.sys_name = sys_name;
        this.sys_user = sys_user;
        this.mmbIds = mmbIds;
        this.quoteId = quoteId;
        this.rangType = rangType;
    }
}
