package com.hj.casps.entity.appsettle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/5.
 * 新建结款单 入口参数
 */

public class CreateSettleLoading implements Serializable {
    private static final long serialVersionUID = 1882042134267791534L;
    /**
     * sys_member : testshop001
     * sys_uuid : 00002
     * sys_token : di13bjrewz9f15ck
     * sys_user : 475814ffe832455dba6ecdf4306b3642
     * sys_func : 1212
     * mmbgetAccount : 9953001798001
     * mmbpayAccount :
     * ctrTime : 2017-04-05
     * mmbpayId : testshop001
     * mmbgetId : testshop001
     * mmbpayName : 奥森学校
     * mmbgetName : 长城商行
     * list : [{"buyersId":"testschool001","buyersName":"奥森学校","exePaymoneyNum":"60","goodsName":"油菜","id":"e3c95684deea4044ab98f5222056bb09","money":"480","ordertitleCode":"e7aeb59469674b70aa0b9047252b97d9","ordertitleNumber":"10038408","paymoneyNum":"420","sellersId":"testshop001","sellersName":"长城商行","num":"20"}]
     */

    private String sys_member;
    private String sys_uuid;
    private String sys_token;
    private String sys_user;
    private String sys_func;
    private String mmbgetAccount;
    private String mmbpayAccount;
    private String mmbpayId;
    private String mmbgetId;
    private String mmbpayName;
    private String mmbgetName;
    private String ctrTime;
    private List<ListBean> list;

    public CreateSettleLoading() {
    }

    public CreateSettleLoading(String sys_member, String sys_uuid, String sys_token, String sys_user,
                               String sys_func, String mmbgetAccount, String mmbpayAccount, String mmbpayId,
                               String mmbgetId, String mmbpayName, String mmbgetName,
                               String ctrTime, List<ListBean> list) {
        this.sys_member = sys_member;
        this.sys_uuid = sys_uuid;
        this.sys_token = sys_token;
        this.sys_user = sys_user;
        this.sys_func = sys_func;
        this.mmbgetAccount = mmbgetAccount;
        this.mmbpayAccount = mmbpayAccount;
        this.mmbpayId = mmbpayId;
        this.mmbgetId = mmbgetId;
        this.mmbpayName = mmbpayName;
        this.mmbgetName = mmbgetName;
        this.ctrTime = ctrTime;
        this.list = list;
    }

    public static class ListBean {
        /**
         * buyersId : testschool001
         * buyersName : 奥森学校
         * exePaymoneyNum : 60
         * goodsName : 油菜
         * id : e3c95684deea4044ab98f5222056bb09
         * money : 480
         * ordertitleCode : e7aeb59469674b70aa0b9047252b97d9
         * ordertitleNumber : 10038408
         * paymoneyNum : 420
         * sellersId : testshop001
         * sellersName : 长城商行
         * num : 20
         */

        private String buyersId;
        private String buyersName;
        private String exePaymoneyNum;
        private String goodsName;
        private String id;
        private String money;
        private String ordertitleCode;
        private String ordertitleNumber;
        private String paymoneyNum;
        private String sellersId;
        private String sellersName;
        private String num;

        public ListBean() {
        }

        public ListBean(String id, String ordertitleCode, String ordertitleNumber,
                                String goodsName, String money, String paymoneyNum,
                                String exePaymoneyNum, String buyersId, String buyersName,
                                String sellersId, String sellersName, String num) {
            this.id = id;
            this.ordertitleCode = ordertitleCode;
            this.ordertitleNumber = ordertitleNumber;
            this.goodsName = goodsName;
            this.money = money;
            this.paymoneyNum = paymoneyNum;
            this.exePaymoneyNum = exePaymoneyNum;
            this.buyersId = buyersId;
            this.buyersName = buyersName;
            this.sellersId = sellersId;
            this.sellersName = sellersName;
            this.num = num;
        }
    }

//    private String sys_token;//	string	令牌号
//    private String sys_uuid;//string	操作唯一编码（防重复提交）
//    private String sys_func;//	string	功能编码（用于授权检查）
//    private String sys_user;//	string	用户id
//    private String sys_member;//	string	会员id
//    private String mmbgetAccount;//	string	收款账号（对方付款）
//    private String mmbpayAccount;//	string	付款账号（本方付款）
//    private String ctrTime;//		datetime		借款时间
//    private List<SettleListEntity> list;
//
//    public CreateSettleLoading() {
//    }
//
//    public CreateSettleLoading(String sys_token, String sys_uuid, String sys_func,
//                               String sys_user, String sys_member,
//                               String mmbgetAccount, String mmbpayAccount,
//                               String ctrTime, List<SettleListEntity> mList) {
//        this.sys_token = sys_token;
//        this.sys_uuid = sys_uuid;
//        this.sys_func = sys_func;
//        this.sys_user = sys_user;
//        this.sys_member = sys_member;
//        this.mmbgetAccount = mmbgetAccount;
//        this.mmbpayAccount = mmbpayAccount;
//        this.ctrTime = ctrTime;
//        this.list = mList;
//    }
//
//    public static class SettleListEntity implements Serializable {
//
//        private static final long serialVersionUID = 6916624699716320116L;
//        private String id;//string	订单id（order）
//        private String ordertitleCode;//	string		订单头id（order）
//        private String ordertitleNumber;//	int		订单号（order）
//        private String goodsName;//string	商品名（order）
//        private String money;//double	订单金额（order）
//        private String paymoneyNum;//double	已付金额（order）
//        private String exePaymoneyNum;//	double	待付金额（order）
//        private String buyersId;//	string	付款方id（order）
//        private String buyersName;//strubg	付款方名称（order）
//        private String sellersId;//	string		收款方id（order）
//        private String sellersName;//string	收款方名称（order）
//        private String num;//double	结款实付金额（order）
//
//        public SettleListEntity() {
//        }
//
//        public SettleListEntity(String id, String ordertitleCode, String ordertitleNumber,
//                                String goodsName, String money, String paymoneyNum,
//                                String exePaymoneyNum, String buyersId, String buyersName,
//                                String sellersId, String sellersName, String num) {
//            this.id = id;
//            this.ordertitleCode = ordertitleCode;
//            this.ordertitleNumber = ordertitleNumber;
//            this.goodsName = goodsName;
//            this.money = money;
//            this.paymoneyNum = paymoneyNum;
//            this.exePaymoneyNum = exePaymoneyNum;
//            this.buyersId = buyersId;
//            this.buyersName = buyersName;
//            this.sellersId = sellersId;
//            this.sellersName = sellersName;
//            this.num = num;
//        }
//    }
}
