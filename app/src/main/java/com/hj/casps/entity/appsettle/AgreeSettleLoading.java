package com.hj.casps.entity.appsettle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/5.
 * 待审批结款单-同意
 * appsettle / refuseSettle
 * 待审批结款单-拒绝
 */

public class AgreeSettleLoading implements Serializable {
    private static final long serialVersionUID = -1605522946146559361L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//	string	会员id
    private List<IDEntity> list; //结款单ID

    public AgreeSettleLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, List<IDEntity> mList) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.list = mList;
    }

    public AgreeSettleLoading() {
    }

    public static class IDEntity implements Serializable {

        private static final long serialVersionUID = 3062211965125234289L;

        private String id;//	string	结款单id

        public IDEntity() {
        }

        public IDEntity(String id) {
            this.id = id;
        }
    }

}
