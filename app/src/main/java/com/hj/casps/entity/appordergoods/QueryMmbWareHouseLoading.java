package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * 当前地址会员列表
 * Created by Admin on 2017/5/4.
 * 提交JSON的实体类
 */

public class QueryMmbWareHouseLoading implements Serializable {
    private static final long serialVersionUID = 8785128475206114098L;

    private String sys_token;//	string	令牌号
    private String sys_uuid;//	string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_name;//	string	用户名
    private String sys_member;//	string	会员id
    private String pageno;//	int	开始行
    private String pagesize;//	int	页条数
    private String address2;//	string	地址名称

    public QueryMmbWareHouseLoading(String sys_token, String sys_uuid, String sys_func,
                                    String sys_user, String sys_name,
                                    String sys_member, String pageno,
                                    String pagesize, String address2) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.address2 = address2;
    }

    public QueryMmbWareHouseLoading() {
    }
}
