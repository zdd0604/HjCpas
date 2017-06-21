package com.hj.casps.entity.appnemberrelationship;

import com.hj.casps.cooperate.GroupManagerListBean;

import java.util.List;

//群组管理返回类
public class GroupBack {

    /**
     * list : [{"groupName":"广东高校群","groupStatus":1,"id":"gd001","remark":"1"},{"groupName":"广东教育厅认证供应商","groupStatus":1,"id":"gd002","remark":"1"},{"groupName":"广东教育厅认证生产商","groupStatus":1,"id":"gd003","remark":"1"},{"groupName":"广东中学群","groupStatus":1,"id":"gd004","remark":"1"},{"groupName":"测试群","groupStatus":1,"id":"gd005","remark":"1"},{"groupName":"云南高校","groupStatus":1,"id":"yn010","remark":"1"},{"groupName":"云南教育厅认证供应商","groupStatus":1,"id":"yn030","remark":"1"},{"groupName":"云南教育厅帮扶合作社","groupStatus":1,"id":"yn050","remark":"1"},{"groupName":"云南教育厅直供基地","groupStatus":1,"id":"yn031","remark":"1"}]
     * return_code : 0
     * return_message : 成功!
     */

    private int return_code;
    private String return_message;
    private List<GroupManagerListBean> list;

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public List<GroupManagerListBean> getList() {
        return list;
    }

    public void setList(List<GroupManagerListBean> list) {
        this.list = list;
    }
}