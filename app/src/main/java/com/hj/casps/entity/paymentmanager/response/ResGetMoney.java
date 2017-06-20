package com.hj.casps.entity.paymentmanager.response;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/20.
 */

public class ResGetMoney {
    private String sys_token;//  string	令牌号
    private String sys_uuid;//             string	操作唯一编码（防重复提交）
    private String sys_func;//        string	功能编码（用于授权检查）
    private String sys_user;//                 string	用户id
    private String sys_member;//                               	string	会员id
    private List<Sub> list;
    //收款id

    public ResGetMoney(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, List<Sub> list) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.list = list;
    }

    public static  class Sub {
        public String id;

        public Sub(String id) {
            this.id = id;
        }

    }

    @Override
    public String toString() {
        return "ResGetMoney{" +
                "sys_token='" + sys_token + '\'' +
                ", sys_uuid='" + sys_uuid + '\'' +
                ", sys_func='" + sys_func + '\'' +
                ", sys_user='" + sys_user + '\'' +
                ", sys_member='" + sys_member + '\'' +
                ", list=" + list +
                '}';
    }
}
