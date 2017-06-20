package com.hj.casps.entity.appordermoney;

import com.hj.casps.entity.appUser.CheckUserRespon;
import com.hj.casps.entity.appUser.QueryUserRespon;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appUser.ToEditUserPageRespon;
import com.hj.casps.entity.appordergoods.AppOrderCheckOrderRespon;
import com.hj.casps.entity.appordergoods.GetTreeModalRespon;
import com.hj.casps.entity.appordergoods.SendGoodsRespon;
import com.hj.casps.entity.appordergoodsCallBack.AddressEditRespon;
import com.hj.casps.entity.appordergoodsCallBack.HarvestExpressRespon;
import com.hj.casps.entity.appordergoodsCallBack.SendExpressRespon;
import com.hj.casps.entity.appordergoodsCallBack.SimpleResponse;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleRespon;
import com.hj.casps.entity.appsettle.QuerySttleManageRespon;
import com.hj.casps.entity.goodsmanager.response.GoodsCategoryEntity;
import com.hj.casps.entity.goodsmanager.response.GoodtoUpdateGain;
import com.hj.casps.entity.goodsmanager.response.SearchGoodGain;
import com.hj.casps.quotes.wyt.SearchQuoteGain;
import com.hj.casps.user.LoginContextBean;

import java.io.Serializable;
import java.util.List;

/**
 * 解析尸体类
 * 解析没有泛型
 * return_code	int	结果码，0 成功，101 无权限，201 数据库错误
 * return_message	string	结果提示文本
 */
public class JsonResponse implements Serializable {

    private static final long serialVersionUID = -2744555924619527730L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public int pagecount;//	int	符合条件记录总页数
    public String return_message;
    public String successMsg;
    public String errorMsg;
    public String sys_uuid;
    public int total;
    public int pageno;//	 int   	当前页码
    public int pagesize;//	 int   		每页数量
    public int totalCount;//	 int  	总记录数
    public int success_num;// int 收货成功个数
    public int fail_num;// int 收货失败个数
    public int totalPage;//	 int   	总页数
    public String msg;//string	成功或失败条数
    public String Result;//string	结果提示文本
    public List<String> goodsImages;
    public boolean isExist;//		true:已注册 false:未注册
    public int length;
    public int qtCount;

    /**
     * 银行卡的尸体
     *
     * @return
     */
    public QueryMmbBankAccountRespon toQueryMmbBankAccountRespon() {
        QueryMmbBankAccountRespon queryMmbBankAccountRespon = new QueryMmbBankAccountRespon();
        queryMmbBankAccountRespon.return_code = return_code;
        queryMmbBankAccountRespon.pagecount = pagecount;
        queryMmbBankAccountRespon.return_message = return_message;
        return queryMmbBankAccountRespon;
    }


    /**
     * 收货地址尸体
     *
     * @return
     */
    public SimpleResponse toQueryMmbWareHouseGain() {
        SimpleResponse queryMmbWareHouseGain = new SimpleResponse();
        queryMmbWareHouseGain.return_code = return_code;
        queryMmbWareHouseGain.return_message = return_message;
        queryMmbWareHouseGain.successMsg = successMsg;
        queryMmbWareHouseGain.errorMsg = errorMsg;
        queryMmbWareHouseGain.total = total;
        return queryMmbWareHouseGain;
    }

    /**
     * 地址编辑
     *
     * @return
     */
    public AddressEditRespon toAddressEditRespon() {
        AddressEditRespon addressEditRespon = new AddressEditRespon();
        addressEditRespon.return_code = return_code;
        addressEditRespon.return_message = return_message;
        addressEditRespon.successMsg = successMsg;
        addressEditRespon.errorMsg = errorMsg;
        return addressEditRespon;
    }

    /**
     * 获取地域树
     *
     * @return
     */
    public GetTreeModalRespon toGetTreeModalRespon() {
        GetTreeModalRespon getTreeModalRespon = new GetTreeModalRespon();
        getTreeModalRespon.return_code = return_code;
        getTreeModalRespon.return_message = return_message;
        return getTreeModalRespon;
    }

    /**
     * 查询收货列表
     *
     * @return
     */
    public HarvestExpressRespon toHarvestExpressRespon() {
        HarvestExpressRespon harvestExpressRespon = new HarvestExpressRespon();
        harvestExpressRespon.return_code = return_code;
        harvestExpressRespon.return_message = return_message;
        harvestExpressRespon.total = total;
        return harvestExpressRespon;
    }

    /**
     * 查询发货列表
     *
     * @return
     */
    public SendExpressRespon toSendExpressRespon() {
        SendExpressRespon sendExpressRespon = new SendExpressRespon();
        sendExpressRespon.return_code = return_code;
        sendExpressRespon.return_message = return_message;
        sendExpressRespon.pagecount = pagecount;
        return sendExpressRespon;
    }
    /**
     * 发货操作
     *
     * @return
     */
    public SendGoodsRespon toSendGoodsRespon() {
        SendGoodsRespon sendGoodsRespon = new SendGoodsRespon();
        sendGoodsRespon.return_code = return_code;
        sendGoodsRespon.return_message = return_message;
        sendGoodsRespon.msg = msg;
        return sendGoodsRespon;
    }

    /**
     * 获取当前会员的操作员列表（获取 操作员管理 页面所需数据）
     *
     * @return
     */
    public QueryUserRespon toQueryUserRespon() {
        QueryUserRespon queryUserRespon = new QueryUserRespon();
        queryUserRespon.return_code = return_code;
        queryUserRespon.return_message = return_message;
        queryUserRespon.pageNo = pageno;
        queryUserRespon.pageSize = pagesize;
        queryUserRespon.totalCount = totalCount;
        queryUserRespon.totalPage = totalPage;
        return queryUserRespon;
    }

    /**
     * 获取当前会员的操作员列表（获取 操作员管理 页面所需数据）
     *
     * @return
     */
    public ToEditUserPageRespon toToEditUserPageRespon() {
        ToEditUserPageRespon toEditUserPageRespon = new ToEditUserPageRespon();
        toEditUserPageRespon.return_code = return_code;
        toEditUserPageRespon.return_message = return_message;
        toEditUserPageRespon.sys_uuid = sys_uuid;
        return toEditUserPageRespon;
    }

    /**
     * 获取当前会员的操作员列表（获取 操作员管理 页面所需数据）
     *
     * @return
     */
    public CheckUserRespon toCheckUserRespon() {
        CheckUserRespon checkUserRespon = new CheckUserRespon();
        checkUserRespon.return_code = return_code;
        checkUserRespon.return_message = return_message;
        checkUserRespon.isExist = isExist;
        return checkUserRespon;
    }

    /**
     * 获取我的提交的待对方审批结款单列表
     *
     * @return
     */
    public QueryMyPendingSttleRespon toQueryMyPendingSttleRespon() {
        QueryMyPendingSttleRespon queryMyPendingSttleRespon = new QueryMyPendingSttleRespon();
        queryMyPendingSttleRespon.return_code = return_code;
        queryMyPendingSttleRespon.return_message = return_message;
        queryMyPendingSttleRespon.pagecount = pagecount;
        return queryMyPendingSttleRespon;
    }
    /**
     * string	结果提示文本
     *
     * @return
     */
    public AppOrderCheckOrderRespon toAppOrderCheckOrderRespon() {
        AppOrderCheckOrderRespon appOrderCheckOrderRespon = new AppOrderCheckOrderRespon();
        appOrderCheckOrderRespon.return_code = return_code;
        appOrderCheckOrderRespon.return_message = return_message;
        appOrderCheckOrderRespon.Result = Result;
        return appOrderCheckOrderRespon;
    }
    /**
     * string	：执行中的结款单列表
     *
     * @return
     */
    public QuerySttleManageRespon toQuerySttleManageRespon() {
        QuerySttleManageRespon querySttleManageRespon = new QuerySttleManageRespon();
        querySttleManageRespon.return_code = return_code;
        querySttleManageRespon.return_message = return_message;
        querySttleManageRespon.pagecount = pagecount;
        return querySttleManageRespon;
    }

    /**
     * 适用于只有两个参数的返回接口
     *
     * @return
     */
    public ReturnMessageRespon toReturnMessageRespon() {
        ReturnMessageRespon returnMessageRespon = new ReturnMessageRespon();
        returnMessageRespon.return_code = return_code;
        returnMessageRespon.return_message = return_message;
        returnMessageRespon.success_num = success_num;
        returnMessageRespon.fail_num = fail_num;
        returnMessageRespon.isExist = isExist;
        return returnMessageRespon;
    }

    /**
     * 登录获取用户信息
     *
     * @return
     */

    public LoginContextBean toLoginContext() {
        LoginContextBean loginContextBean = new LoginContextBean();
        loginContextBean.return_code = return_code;
        loginContextBean.return_message = return_message;
        return loginContextBean;

    }

    public GoodsCategoryEntity toGoodsCategoryEntity() {
        GoodsCategoryEntity goodsCategoryEntity = new GoodsCategoryEntity();
        goodsCategoryEntity.return_code = return_code;
        goodsCategoryEntity.return_message = return_message;
        return goodsCategoryEntity;

    }

    public SearchGoodGain toSearchGoodGain() {
        SearchGoodGain searchGoodGain = new SearchGoodGain();
        searchGoodGain.return_code = return_code;
        searchGoodGain.return_message = return_message;
        searchGoodGain.pageno = pageno;
        searchGoodGain.pagesize = pagesize;
        searchGoodGain.totalCount = totalCount;
        searchGoodGain.totalPage = totalPage;
        return searchGoodGain;

    }


    public GoodtoUpdateGain toGoodtoUpdateGain(){
        GoodtoUpdateGain goodtoUpdateGain=new GoodtoUpdateGain();
        goodtoUpdateGain.return_code=return_code;
        goodtoUpdateGain.return_message=return_message;
        goodtoUpdateGain.goodsImages=goodsImages;
        return goodtoUpdateGain;
    }
    public SearchQuoteGain toSearchQuoteGain (){
        SearchQuoteGain quoteGain=new SearchQuoteGain();
        quoteGain.return_code=return_code;
        quoteGain.return_message=return_message;
        quoteGain.pageNo=pageno;
        quoteGain.qtCount=qtCount;
        quoteGain.length=length;
        return quoteGain;
    }

}