package com.hj.casps.entity.appsettle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/5.
 * 待审批结款单-编辑提交 入口
 */

public class ModifySettleLoading implements Serializable {
    private static final long serialVersionUID = -8559595903996683269L;
    private String sys_token;//	string	令牌号
    private String sys_uuid;//	string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//	string	会员id
    private List<ModifySettleEntity> list;

    public ModifySettleLoading() {
    }

    public ModifySettleLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, List<ModifySettleEntity> mList) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.list = mList;
    }

    public static class ModifySettleEntity implements Serializable {

        private static final long serialVersionUID = -3549582358148625666L;

        private String id;//string	结款单id
        private String settleCode;//int		结款单号
        private String myTime;//datetime		我的提议时间
        private String myMoney;//double	我的提议金额

        public ModifySettleEntity() {
        }

        public ModifySettleEntity(String id, String settleCode, String myTime, String myMoney) {
            this.id = id;
            this.settleCode = settleCode;
            this.myTime = myTime;
            this.myMoney = myMoney;
        }
    }
}
