package com.hj.casps.entity.appordergoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/15.
 * 收货操作
 */

public class GetGoodsLoading implements Serializable {
    private static final long serialVersionUID = -8034383678972293018L;


    /**
     * sys_user : 0236e1f6e6474da88ff1388e8ae2d46d
     * sys_member : testschool001
     * sys_func : 1212
     * sys_token : 1212
     * sys_uuid : 00033
     * sys_name : as02
     * num_list : [{"order_id":"ea9cdbd8c4bf40bea46794b1627ab351","num":"1000","address_id":"f231868a67b3466e931cc68ef820e3d2"},{"order_id":"913eb5f4a81a42cfaa3308e309defb4f","num":"1","address_id":"f231868a67b3466e931cc68ef820e3d2"}]
     */

    private String sys_user;
    private String sys_member;
    private String sys_func;
    private String sys_token;
    private String sys_uuid;
    private String sys_name;
    private List<NumListBean> num_list;

    public GetGoodsLoading(String sys_user, String sys_member, String sys_func,
                           String sys_token, String sys_uuid,
                           String sys_name, List<NumListBean> num_list) {
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.sys_func = sys_func;
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_name = sys_name;
        this.num_list = num_list;
    }

    public String getSys_user() {
        return sys_user;
    }

    public void setSys_user(String sys_user) {
        this.sys_user = sys_user;
    }

    public String getSys_member() {
        return sys_member;
    }

    public void setSys_member(String sys_member) {
        this.sys_member = sys_member;
    }

    public String getSys_func() {
        return sys_func;
    }

    public void setSys_func(String sys_func) {
        this.sys_func = sys_func;
    }

    public String getSys_token() {
        return sys_token;
    }

    public void setSys_token(String sys_token) {
        this.sys_token = sys_token;
    }

    public String getSys_uuid() {
        return sys_uuid;
    }

    public void setSys_uuid(String sys_uuid) {
        this.sys_uuid = sys_uuid;
    }

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public List<NumListBean> getNum_list() {
        return num_list;
    }

    public void setNum_list(List<NumListBean> num_list) {
        this.num_list = num_list;
    }

    public static class NumListBean {
        /**
         * order_id : ea9cdbd8c4bf40bea46794b1627ab351
         * num : 1000
         * address_id : f231868a67b3466e931cc68ef820e3d2
         */

        private String orderId;
        private String num;
        private String address_id;

        public NumListBean(String order_id, String num, String address_id) {
            this.orderId = order_id;
            this.num = num;
            this.address_id = address_id;
        }

        public String getOrder_id() {
            return orderId;
        }

        public void setOrder_id(String order_id) {
            this.orderId = order_id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }
    }
}
