package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/5.
 * 执行中的结款单列表 入口参数
 */

public class QuerySttleManageLoading implements Serializable {
    private static final long serialVersionUID = -8994726057947734306L;
    private String sys_token;//	string	令牌号
    private String sys_uuid;//	string	操作唯一编码（防重复提交）
    private String sys_func;//	string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//	string	会员id
    private String settleCode;//string	结款单号
    private String oppositeName;//	string	结款对方
    private String settlestatus;//	string	状态（1全部、2执行中、3本方请求终止、4对方请求终止）
    private String executeStartTime;//	datetime		开始时间
    private String executeEndTime;//datetime		结束时间
    private String pageno;//int	页号
    private String pagesize;//	int	页行数

    public QuerySttleManageLoading() {
    }

    public QuerySttleManageLoading(String sys_token, String sys_uuid, String sys_func, String sys_user,
                                   String sys_member, String settleCode, String oppositeName,
                                   String settlestatus, String executeStartTime,
                                   String executeEndTime, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.settleCode = settleCode;
        this.oppositeName = oppositeName;
        this.settlestatus = settlestatus;
        this.executeStartTime = executeStartTime;
        this.executeEndTime = executeEndTime;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }
}
