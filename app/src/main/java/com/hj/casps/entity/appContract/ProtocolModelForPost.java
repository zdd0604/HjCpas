package com.hj.casps.entity.appContract;

import java.util.List;

//去post时提交的参数
public class ProtocolModelForPost {
    private String sys_token;
    private String sys_uuid;
    private String sys_func;
    private String sys_user;
    private String sys_name;
    private String sys_member;
    private String pageno;
    private String pagesize;
    private String id;
    private String stopStatus;
    private String contract_id;
    private String categoryId;//分类
    private String orderId;//订单头ID
    private String status;//订单类型（1全部2采购3销售）
    private String pagetype;//协议状态（pending:待审批 submit:已提交 running：执行中）
    private String contract_type;//协议类型（采购 1，销售 2）
    private String name;
    private String executeStatus;//订单执行状态0:全部  1：执行中 2：已完成
    private String operate_type;
    private String startTime;
    private String endTime;
    private String contract_status;//协议状态（执行中3，已终止7） 对应x
    private List<ID> mtOrder;

    public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, List<ID> mtOrder) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.mtOrder = mtOrder;
    }

    public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String id, String stopStatus) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.id = id;
        this.stopStatus = stopStatus;
    }

    public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String pageno, String pagesize, String orderId, String status, String name, String executeStatus, String startTime, String endTime) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.orderId = orderId;
        this.status = status;
        this.name = name;
        this.executeStatus = executeStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String pageno, String pagesize, String categoryId, String orderId, String status, String name) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.categoryId = categoryId;
        this.orderId = orderId;
        this.status = status;
        this.name = name;
    }

    public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String contract_id, String operate_type, String contract_status, String contract_type) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.contract_id = contract_id;
        this.operate_type = operate_type;
        this.contract_status = contract_status;
        this.contract_type = contract_type;
    }

    public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String pageno, String pagesize, String pagetype, String contract_type, String name, String contract_status) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.pagetype = pagetype;
        this.contract_type = contract_type;
        this.name = name;
        this.contract_status = contract_status;
    }

    public static class ID {
        private String id;

        public ID(String id) {
            this.id = id;
        }
    }
}