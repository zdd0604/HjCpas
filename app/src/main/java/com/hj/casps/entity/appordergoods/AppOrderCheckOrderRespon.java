package com.hj.casps.entity.appordergoods;

import com.hj.casps.entity.appordercheckorder.BuyersAccountListBean;
import com.hj.casps.entity.appordercheckorder.BuyersAddressListBean;
import com.hj.casps.entity.appordercheckorder.DataBean;
import com.hj.casps.entity.appordercheckorder.SellersAccountListBean;
import com.hj.casps.entity.appordercheckorder.SellersAddressListBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/17.
 * 订单查询详情（所有出订单详情和订单编辑页面都用这个）
 */

public class AppOrderCheckOrderRespon implements Serializable {
    private static final long serialVersionUID = 7330327622097896429L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public String Result;//string	结果提示文本
    public int total;
    public List<BuyersAccountListBean> buyersAccountList;
    public List<BuyersAddressListBean> buyersAddressList;
    public List<DataBean> data;
    public AppOrderCheckOrderOrdertitle<List<OrdertitleData>> ordertitle;
    public List<SellersAccountListBean> sellersAccountList;
    public List<SellersAddressListBean> sellersAddressList;
}
