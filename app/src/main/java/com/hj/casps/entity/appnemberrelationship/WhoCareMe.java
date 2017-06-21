package com.hj.casps.entity.appnemberrelationship;

import com.hj.casps.cooperate.WhoCareListBean;

import java.util.List;

/**
 * 搜索供应商，谁关注我，返回参数
 */
public class WhoCareMe {

    /**
     * list : [{"member_id":"testschool001","member_name":"奥森学校","mmbhomepage":"http://members.nxdj.org.cn/奥森学校.html"},{"member_id":"c35e4315f7804f52b27e403a812deec6","member_name":"新美农业合作社","mmbhomepage":"http://members.nxdj.org.cn/新美农场.html"},{"member_id":"459f3498633d4952a293dc360e2b7c97","member_name":"天坛学院","mmbhomepage":"http://106.2.221.174:2000/v2content/mmbhtml/天坛学院.html"},{"member_id":"55da4721cd7049e186a17745a795a6cd","member_name":"天美贸易公司","mmbhomepage":""},{"member_id":"d5d1244c85d5478ca88a253b509f8bed","member_name":"北京交通大学","mmbhomepage":""},{"member_id":"19dae428c9ad44ce86011d786ead53f1","member_name":"北京冯氏商贸有限公司","mmbhomepage":"http://members.nxdj.org.cn/北京冯氏商贸.html"},{"member_id":"a9c1512052164391ab1fcc2ffa67b0ec","member_name":"北京试用的学校","mmbhomepage":""},{"member_id":"da4383de72494f5d98dc7836d25f526f","member_name":"cyh","mmbhomepage":"http://members.nxdj.org.cn/cyh.html"}]
     * pagecount : 8
     * return_code : 0
     * return_message : 成功!
     */

    private int pagecount;
    private int return_code;
    private String return_message;
    private List<WhoCareListBean> list;

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

    public List<WhoCareListBean> getList() {
        return list;
    }

    public void setList(List<WhoCareListBean> list) {
        this.list = list;
    }
}
