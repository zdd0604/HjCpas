package com.hj.casps.entity.appquote;

import java.io.Serializable;

/**
 * Created by Admin on 2017/6/30.
 * 接口url：appQuote/addGroupIds
 * 功能描述：群组确定按钮  添加群组关系
 */

public class AddGroupIdsEntity implements Serializable {
    private static final long serialVersionUID = 6029220724084086824L;
    private String sys_token;
    private String sys_func;
    private String sys_uuid;
    private String sys_member;
    private String sys_name;
    private String sys_user;
    private String groupIds;
    private String quoteId;
    private String rangType;

    public AddGroupIdsEntity() {
    }

    public AddGroupIdsEntity(String sys_token, String sys_func, String sys_uuid, String sys_member, String sys_name, String sys_user, String groupIds, String quoteId, String rangType) {
        this.sys_token = sys_token;
        this.sys_func = sys_func;
        this.sys_uuid = sys_uuid;
        this.sys_member = sys_member;
        this.sys_name = sys_name;
        this.sys_user = sys_user;
        this.groupIds = groupIds;
        this.quoteId = quoteId;
        this.rangType = rangType;
    }
}
