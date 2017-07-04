package com.hj.casps.entity.appcontract;

import com.hj.casps.protocolmanager.ProtocolListBean;

import java.util.List;

//返回的参数，此参数是协议管理的参数
public class BackBean {

    /**
     * list : [{"buy_mmb_id":"testschool001","contract_id":"66457abab66947149194c05c6092eb16","contract_status":4,"contract_title":"1234567","end_time":1494864000000,"flow_type":"自取","mmbname":"长城商行","pay_type":"每月","start_time":1494864000000},{"buy_mmb_id":"testschool001","contract_id":"24c81eb3ec4847199ec795087e9aebfc","contract_status":4,"contract_title":"4到7月临时采购","end_time":1498752000000,"flow_type":"自取","mmbname":"长城商行","pay_type":"每月","start_time":1491840000000}]
     * pagecount : 2
     * return_code : 0
     * return_message : 成功!
     */

    private int pagecount;
    private int return_code;
    private String return_message;
    private List<ProtocolListBean> list;

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
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

    public List<ProtocolListBean> getList() {
        return list;
    }

    public void setList(List<ProtocolListBean> list) {
        this.list = list;
    }
}