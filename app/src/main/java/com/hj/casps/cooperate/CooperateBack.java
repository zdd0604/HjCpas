package com.hj.casps.cooperate;

import java.util.List;

/**
 * Created by zy on 2017/5/13.
 * 关注会员目录解析类
 */

public class CooperateBack {

    private int return_code;//返回的code
    private String return_message;//返回的消息
    private List<ListBean> list;//返回的list

    public class ListBean {
        private List<CooperateModel> 卖;//销售列表
        private List<CooperateModel> 买;//采购列表

        public List<CooperateModel> get买() {
            return 买;
        }

        public void set买(List<CooperateModel> 买) {
            this.买 = 买;
        }

        public List<CooperateModel> get卖() {
            return 卖;
        }

        public void set卖(List<CooperateModel> 卖) {
            this.卖 = 卖;
        }
    }

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

}

