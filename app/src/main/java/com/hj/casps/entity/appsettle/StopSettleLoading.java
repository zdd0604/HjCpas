package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/5.
 * 执行中的结款单-请求终止 入口参数
 * appsettle / revokeToStop
 * 行中的结款单-撤回终止请求
 * appsettle / allowToStop
 * 执行中的结款单-同意终止请求
 * appsettle / regist
 * 结款单登记为担保资源操作
 */

public class StopSettleLoading implements Serializable {
    private static final long serialVersionUID = 2279469976929128347L;

    private String sys_token;//	string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//	string	用户id
    private String sys_member;//	string	会员id
    private String id;//string	结款单id

    public StopSettleLoading() {
    }

    public StopSettleLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String id) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.id = id;
    }
}
