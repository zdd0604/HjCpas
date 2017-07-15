package com.hj.casps.entity.appgoods;

import java.io.Serializable;

/**
 * Created by zhonglian on 2017/7/15.
 * 接口url：appGoods/selectAllunitName
 * 功能描述：查询所有单位列表list
 */

public class SelectAllunitName implements Serializable {
    private static final long serialVersionUID = -7601775284307279354L;
    private String sys_token;//	string	令牌号
    private String sys_uuid;    //	string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;    //	string	用户id
    private String sys_name;    //string	用户名
    private String sys_member;    //	string	会员id

    public SelectAllunitName() {
    }

    public SelectAllunitName(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
    }
}
