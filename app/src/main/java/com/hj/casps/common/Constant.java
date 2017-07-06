package com.hj.casps.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.cooperate.ProtocalProductItem;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.appordermoney.MmbBankAccountEntity;
import com.hj.casps.entity.appordermoney.QueryPayMoneyOrderForSettleEntity;
import com.hj.casps.entity.appsettle.QuerySttleManageGain;
import com.hj.casps.entity.goodsmanager.response.NoteEntity;
import com.hj.casps.ordermanager.OrderShellAdapter;
import com.hj.casps.ordermanager.OrderShellModel;
import com.hj.casps.util.MenuUtils;
import com.hj.casps.util.ToastUtils;
import com.hj.casps.util.XmlUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zdd on 2017/4/17.
 */

public class Constant {
    //总HTTPURL，修改方便
    //内网测试 http://192.168.1.120:8081
    //内网正式 http://192.168.0.135:8081
//    http://192.168.0.250:8080
//
//    public static final String HTTPURLALL = "http://192.168.0.120:8081";
    //    public static final String HTTPURLALL = "http://192.168.0.250:8080";
    public static final String HTTPURLALL = "http://192.168.1.120:8081";
//    public static final String HTTPURLALL = "http://123.126.109.166:2000";
    //公共的请求头
    public static final String HTTPURL = HTTPURLALL + "/v2/";
    //短url
    public static final String SHORTHTTPURL = HTTPURLALL;
    //长url
    public static final String LONGTHTTPURL = HTTPURLALL + "/v2content/upload";
    //主页
    public static final String HOME_HTTP_URL = HTTPURLALL + "/v2/app/index.html";
    //图片的url
    public static final String HTTPURLIMAGE = HTTPURLALL + "/v2content/upload/";
    //当前会员地址列表
    public static final String QueryMmbWareHouseUrl = HTTPURL + "appOrderGoods/queryMmbWareHouse.app";
    //编辑查询
    public static final String ToEditMmbWarehousetUrl = HTTPURL + "appOrderGoods/toEditMmbWarehouse.app";
    //编辑确认
    public static final String UpdateMmbWarehouseUrl = HTTPURL + "appOrderGoods/updateMmbWarehouse.app";
    //获取地域树
    public static final String GetTreeModalUrl = HTTPURL + "appOrderGoods/getTreeModal.app";
    //删除地址
    public static final String DeleteMmbWarehouseUrl = HTTPURL + "appOrderGoods/deleteMmbWarehouse.app";
    //添加确认
    public static final String CreateMmbWarehousetUrl = HTTPURL + "appOrderGoods/createMmbWarehouse.app";
    //查询收货列表
    public static final String QueryGetGoodsUrl = HTTPURL + "appOrderGoods/queryGetGoods.app";
    //收货操作
    public static final String GetGoodsUrl = HTTPURL + "appOrderGoods/getGoods.app";
    //查询发货列表
    public static final String QuerySendGoodsUrl = HTTPURL + "appOrderGoods/querySendGoods.app";
    //发货
    public static final String SendGoodsUrl = HTTPURL + "appOrderGoods/sendGoods.app";
    //退货列表展示
    public static final String QueryReturnGoodsUrl = HTTPURL + "appOrderGoods/queryReturnGoods.app";
    //退货操作
    public static final String ReturnGoodsUrl = HTTPURL + "appOrderGoods/returnGoods.app";
    //收退货列表展示
    public static final String QueryGetReturnGoodsUrl = HTTPURL + "appOrderGoods/queryGetReturnGoods.app";
    //签收退货操作
    public static final String GetReturnGoodsUrl = HTTPURL + "appOrderGoods/getReturnGoods.app";
    //银行账号管理
    public static final String QueryMmbBankAccountUrl = HTTPURL + "appOrderMoney/queryMmbBankAccount.app";
    //操作员列表页
    public static final String QueryUserUrl = HTTPURL + "appUser/queryUser.app";
    //获得添加操作员页面所需数据
    public static final String ToAddUserPageUrl = HTTPURL + "appUser/toAddUserPage.app";
    //编辑操作员页面（获得启动编辑操作员页面所需数据）
    public static final String ToEditUserPageUrl = HTTPURL + "appUser/toEditUserPage.app";
    //编辑操作员（编辑页面的确认提交动作）
    public static final String EditUserUrl = HTTPURL + "appUser/editUser.app";
    //添加操作员（添加操作员页面提交时）
    public static final String CreateUserUrl = HTTPURL + "appUser/createUser.app";
    //修改操作员状态（停用、启用操作动作）
    public static final String EditStateOfUserUrl = HTTPURL + "appUser/editStateOfUser.app";
    //验证账号是否注册（创建操作员时会用到）
    public static final String CheckUserUrl = HTTPURL + "appUser/checkUser.app";
    //获取我的提交的待对方审批结款单列表
    public static final String QueryMyPendingSttleUrl = HTTPURL + "appSettle/queryMyPendingSttle.app";
    //获取结款单详情
    public static final String QuerysettleDetailUrl = HTTPURL + "appSettle/querysettleDetail.app";
    //订单查询详情（所有出订单详情和订单编辑页面都用这个）
    public static final String AppOrderCheckOrderUrl = HTTPURL + "appOrder/appOrderCheckOrder.app";
    //获取收/付款方列表
    public static final String QueryOppositeListUrl = HTTPURL + "appSettle/queryOppositeList.app";
    //获取收/付款方银行账号
    public static final String QueryAddressAccountUrl = HTTPURL + "appOrderMoney/queryAddressAccount.app";
    //获取代付款订单列表
    public static final String QueryPayMoneyOrderForSettleUrl = HTTPURL + "appOrderMoney/queryPayMoneyOrderForSettle.app";
    //获取待审批结款单列表
    public static final String QueryPendingSttleUrl = HTTPURL + "appSettle/queryPendingSttle.app";
    //新建结款单
    public static final String CreateSettleUrl = HTTPURL + "appSettle/createSettle.app";
    //待审批结款单-编辑提交
    public static final String ModifySettleUrl = HTTPURL + "appSettle/modifySettle.app";
    //待审批结款单-同意
    public static final String AgreeSettleUrl = HTTPURL + "appSettle/agreeSettle.app";
    //待审批结款单-拒绝
    public static final String RefuseSettleUrl = HTTPURL + "appSettle/refuseSettle.app";
    //执行中的结款单列表
    public static final String QuerySttleManageUrl = HTTPURL + "appSettle/querySttleManage.app";
    //执行中的结款单-请求终止
    public static final String StopSettleUrl = HTTPURL + "appSettle/stopSettle.app";
    //行中的结款单-撤回终止请求
    public static final String RevokeToStopUrl = HTTPURL + "appSettle/revokeToStop.app";
    //执行中的结款单-同意终止请求
    public static final String AllowToStopUrl = HTTPURL + "appSettle/allowToStop.app";
    //结款单登记担保列表
    public static final String QuerySttleRegistUrl = HTTPURL + "appSettle/querySttleRegist.app";
    //结款单登记担保-申请登记
    public static final String RegistUrl = HTTPURL + "appSettle/regist.app";
    //以下WYT的地盘
    //登录
    public static final String LoginUrl = HTTPURL + "appSecurity/login.app";
    // 超时登录
    public static final String ReLoginUrl = HTTPURL + "appSecurity/reLogin.app";
    //获取运行环境
    public static final String GetContextUrl = HTTPURL + "appSecurity/getContext.app";
    //用户退出
    public static final String LoginOutUrl = HTTPURL + "appSecurity/logout.app";
    //获取短信验证码
    public static final String InitMessageUrl = HTTPURL + "appSecurity/initMessageLogin.app";
    //获取当前用户会员的所有使用的商品分类（出这个会员的商品目录结构）
    public static final String GetUserCategoryUrl = HTTPURL + "appGoods/getUserCategory.app";
    //添加编辑商品时加载平台所有的商品分类
    public static final String GetAllCategoryUrl = HTTPURL + "appGoods/getAllCategory.app";
    //获取关注会员列表
    public static final String QueryMMBConcernsUrl = HTTPURL + "appMemberRelationship/queryMMBConcerns.app";
    //改变关注等级。
    public static final String ChangeConcernGradeUrl = HTTPURL + "appMemberRelationship/changeConcernGrade.app";
    //取消关注
    public static final String CancelConcernUrl = HTTPURL + "appMemberRelationship/cancelConcern.app";
    //获得当前用户的菜单项列表
    public static final String GetMenuListUrl = HTTPURL + "appSecurity/getMenuList.app";
    //银行账号管理-删除账户
    public static final String DeleteBankAccountUrl = HTTPURL + "appOrderMoney/deleteMmbBankAccount.app";
    //特定商品分类下的商品列表
    public static final String SearchGoodUrl = HTTPURL + "appGoods/searchGood.app";
    //特定商品分类下的商品列表
    public static final String AppOrderSearchGoodUrl = HTTPURL + "appOrder/searchGood.app";
    //商品编辑
    public static final String ToUpdateGoodUrl = HTTPURL + "appGoods/toupdateGood.app";
    //商品详情
    public static final String LookGoodUrl = HTTPURL + "appGoods/lookGood.app";
    //商品启用/停用操作的处理
    public static final String UpdateStatusUrl = HTTPURL + "appGoods/updateStatus.app";
    //检查同一分类下的商品名称
    public static final String CheckGoodName = HTTPURL + "appGoods/checkName.app";
    //获得当前会员可用的资源库列表
    public static final String ShowBaseUrl = HTTPURL + "appMaterial/showBase.app";
    //某个目录节点的图片列表。
    public static final String ShowPicUrl = HTTPURL + "appMaterial/showPic.app";
    //添加个人素材库分类
    public static final String AddDivUrl = HTTPURL + "appMaterial/addDiv.app";
    //修改个人素材库分类
    public static final String UpdateDivUrl = HTTPURL + "appMaterial/updateDiv.app";
    /*删除个人素材库分类(只能删除没有资源，没有子节点的目录)删除个人素材库分类(只能删除没有资源，没有子节点的目录)*/
    public static final String DelDivUrl = HTTPURL + "appMaterial/delDiv.app";
    //上传素材库图片。
    public static final String ImageUploadUrl = HTTPURL + "appMaterial/imgUpload.app";
    //删除素材库图片
    public static final String DelMalUrl = HTTPURL + "appMaterial/delMal.app";
    //资源库图片分类目录树
    public static final String ShowDivUrl = HTTPURL + "appMaterial/showDiv.app";
    //根据url获取用户的mmbid
    public static final String GetMmbByUrl = HTTPURL + "appMemberRelationship/getMmbByUrl.app";
    //关注选中的供应商
    public static final String MarkMemberUrl = HTTPURL + "appMemberRelationship/markMember.app";
    //商品添加页面确认提交操作的处理
    public static final String CreateGoodUrl = HTTPURL + "appGoods/createGood.app";
    //商品详情提交的URL
    public static final String UpdateGoodDetailUrl = HTTPURL + "appGoods/updateGood.app";
    //银行账号管理-新增账户/编辑账户-验证银行账号唯一性
    public static final String CheckAccountNoUrl = HTTPURL + "appOrderMoney/checkAccountNo.app";
    //银行账号管理-新增账户
    public static final String CreateMmbBackAccountUrl = HTTPURL + "appOrderMoney/createMmbBankAccount.app";
    // 编辑银行账户
    public static final String UpdateBankAccountUrl = HTTPURL + "appOrderMoney/updateBankAccount.app";
    //查询待付款订单列表（付款）
    public static final String QueryPayMoneyUrl = HTTPURL + "appOrderMoney/queryPayMoney.app";
    //查询待收款列表
    public static final String QueryGeMoneyUrl = HTTPURL + "appOrderMoney/queryGetMoney.app";
    //查询待确认收退款订单列表
    public static final String QueryGetRefundMoney = HTTPURL + "appOrderMoney/queryGetRefundMoney.app";
    //报价检索
    public static final String SearchQuoteUrl = HTTPURL + "appQuote/searchQuote.app";
    //报价检索-查看报价明细
    public static final String QuoteDetailGoodUrl = HTTPURL + "appQuote/quoteDetail.app";
    //查询可退款订单列表
    public static final String QueryReFundMoneyUrl = HTTPURL + "appOrderMoney/queryRefundMoney.app";
    //报价的添加购物车
    public static final String AddShopCarUrl = HTTPURL + "appQuote/addShpc.app";
    //线下付款操作
    public static final String PayMoneyOfflineUrl = HTTPURL + "appOrderMoney/payMoneyOffline.app";
    //执行收款操作处理
    public static final String GetMoneyUrl = HTTPURL + "appOrderMoney/getMoney.app";
    //收退款
    public static final String GetRefundMoneyUrl = HTTPURL + "appOrderMoney/getRefundMoney.app";
    //执行退款
    public static final String RefundMoneyOffline = HTTPURL + "appOrderMoney/refundMoneyOffline.app";
    //采购捡单车的列表
    public static final String SearchSHPCUrl = HTTPURL + "appOrder/searchSHPC.app";
    //升级到业务合作
    public static final String toUpgradebizOperationUrl = HTTPURL + "appMemberRelationship/toUpgradebizOperation.app";
    //会员详情
    public static final String getMmbbymidUrl = HTTPURL + "appMemberRelationship/getMmbbymid.app";
    //启用/停用的方法
    public static final String openBizRelationshipUrl = HTTPURL + "appMemberRelationship/openBizRelationship.app";
    //启用/停用的方法
    public static final String stopBizRelationshipUrl = HTTPURL + "appMemberRelationship/stopBizRelationship.app";
    //降级为仅关注
    public static final String lowerToConcernOperationUrl = HTTPURL + "appMemberRelationship/lowerToConcernOperation.app";
    //根据协议ID查询商品类别。
    public static final String getUserCategoryByCtrIdUrl = HTTPURL + "appGoods/getUserCategoryByCtrId.app";
    //根据会员Id获取指定会员的所有的商品分类
    public static final String getMemberCategoryUrl = HTTPURL + "appGoods/getMemberCategory.app";
    //到创建协议页面需要的信息（获取相关信息，地址列表，银行列表）
    public static final String toAddContractPageUrl = HTTPURL + "appContract/toAddContractPage.app";
    //启动编辑协议页面所需数据。
    public static final String ToUpdateContractPageUrl = HTTPURL + "appContract/toUpdateContractPage.app";
    //启动编辑协议页面所需数据。
    public static final String CreatCTRUrl = HTTPURL + "appContract/creatCTR.app";
    //启动编辑协议页面所需数据。
    public static final String updateCTRUrl = HTTPURL + "appContract/updateCTR.app";
    //启动编辑协议页面所需数据。
    public static final String ToCreateOrderUrl = HTTPURL + "appContract/toCreateOrder.app";
    //待审批的合作关系建立申请列表。
    public static final String QueryMmbRelationshipsUrl = HTTPURL + "appMemberRelationship/queryMmbRelationships.app";
    //进入当前会员的会员合作目录，获取不同合作业务类型的会员列表
    public static final String GetRelaMmbsUrl = HTTPURL + "appMemberRelationship/getRelaMmbs.app";
    //同意拒绝业务关系申请操作的处理
    public static final String VerifyMmbRelationshipUrl = HTTPURL + "appMemberRelationship/verifyMmbRelationship.app";
    //编辑报价跳转
    public static final String EditQuoteUrl = HTTPURL + "appQuote/editQuote.app";
    //创建报价
    public static final String CreateQuoteUrl = HTTPURL + "appQuote/createQuote.app";
    //编辑报价确认
    public static final String SubEditQuoteUrl = HTTPURL + "appQuote/subEditQuote.app";
    //订单创建提交
    public static final String CreateOrderUrl = HTTPURL + "appOrder/createOrder.app";
    //订单编辑提交
    public static final String AppOrderEditOrderUrl = HTTPURL + "appOrder/appOrderEditOrder.app";
    //获取群组列表
    public static final String QueryGroupManUrl = HTTPURL + "appMemberRelationship/queryGroupMan.app";
    //查询所有供应商（学校专用）
    public static final String GetAllMemberUrl = HTTPURL + "appMemberRelationship/getAllMember.app";
    //查询关注我的会员列表（企业专用）
    public static final String GetAllMarkedMeMembersUrl = HTTPURL + "appMemberRelationship/getAllMarkedMeMembers.app";
    //和选中的供应商建立业务合作关系
    public static final String CreateCooperatorsUrl = HTTPURL + "appMemberRelationship/createCooperators.app";
    //当前会员报价列表（报价管理页面用数据）
    public static final String ShowQuoteListUrl = HTTPURL + "appQuote/showQuoteList.app";
    //获取群组列表
    public static final String AdduserforGroupUrl = HTTPURL + "appMemberRelationship/adduserforGroup.app";
    //删除群组列表
    public static final String DeleteUserforGroupUrl = HTTPURL + "appMemberRelationship/deleteUserforGroup.app";
    //删除采购 (销售)拣单车中商品
    public static final String DeleteQuoteSHPCUrl = HTTPURL + "appOrder/deleteQuoteSHPC.app";
    //批量删除采购 (销售)拣单车中商品
    public static final String DeleteMoreSHPCUrl = HTTPURL + "appOrder/deleteMoreSHPC.app";
    //根据订单号删除商品
    public static final String deleteMoreSHPCByGoodsId = HTTPURL + "appOrder/deleteMoreSHPCByGoodsId.app";
    //点击发布范围查询合作会员
    public static final String GetMmbUrl = HTTPURL + "appQuote/getMmb.app";
    //删除采购 (销售)拣单车中商品
    public static final String GetGroupUrl = HTTPURL + "appQuote/getGroup.app";
    //公开确认按钮(和选择发布范围确认按钮一样)
    public static final String SubgUrl = HTTPURL + "appQuote/subg.app";
    //删除报价范围
    public static final String DeleteScopeIdUrl = HTTPURL + "appQuote/deleteScopeId.app";
    //查询合作会员
    public static final String ShowMmbUrl = HTTPURL + "appQuote/showMmb.app";
    //查询群组
    public static final String ShowGroupUrl = HTTPURL + "appQuote/showGroup.app";
    //会员确定按钮  添加会员关系
    public static final String AddMmbIdsUrl = HTTPURL + "appQuote/addMmbIds.app";
    //群组确定按钮  添加群组关系
    public static final String AddGroupIdsUrl = HTTPURL + "appQuote/addGroupIds.app";
    //查看协议详情
    public static final String ToContractDetailPageUrl = HTTPURL + "appContract/toContractDetailPage.app";
    //跳转同意协议输入界面
    public static final String ShowAgreeModalUrl = HTTPURL + "appContract/showAgreeModal.app";
    //同意协议
    public static final String AgreeContractUrl = HTTPURL + "appContract/agreeContract.app";
    //获取合作协议列表
    public static final String QueryContractByPageTypeUrl = HTTPURL + "appContract/queryContractByPageType.app";
    //取新建订单列表（本方新创建，等对方审批）
    public static final String QueryMyPendingOrderUrl = HTTPURL + "appOrder/queryMyPendingOrder.app";
    //待审批订单列表
    public static final String AppOrderListUrl = HTTPURL + "appOrder/appOrderList.app";
    //取执行中订单列表
    public static final String QueryOrderManageUrl = HTTPURL + "appOrder/queryOrderManage.app";
    //订单锁定操作
    public static final String AppOrderLockOrderUrl = HTTPURL + "appOrder/appOrderLockOrder.app";
    //订单作废操作
    public static final String AppOrderAbolishOrderUrl = HTTPURL + "appOrder/appOrderAbolishOrder.app";
    //请求中止操作
    public static final String StopOrderUrl = HTTPURL + "appOrder/stopOrder.app";
    //协议各种操作后（拒绝，申请终止，同意终止，撤回终止，拒绝终止）的处理
    public static final String OperateContractUrl = HTTPURL + "appContract/operateContract.app";
    //商品信息确定
    public static final String UpdateQuoteSHPCUrl = HTTPURL + "appOrder/updateQuoteSHPC.app";

    public static boolean isFreshGood = false;
    //s商品管理的页面跳转到编辑页面的intent 值
    public static final String INTENTISADDGOODS = "INTENTISADDGOODS";
    public static final String INTENT_GOODCATEGORYID = "INTENT_GOODCATEGORYID";
    //商品三级目录跳转到商品列表页面的intent传值
    public static final String CATEGORY_ID = "CATEGORY_ID";
    //商品列表传到编辑商品的goodsid
    public static final String INTENT_GOODSID = "INTENT_GOODSID";
    public static final String INTENT_QUOTE_ID = "INTENT_QUOTE_ID";
    //登出的intent
    public static final String INTENT_IS_LOGINOUT = "INTENT_IS_LOGINOUT";
    public static final String INTENT_GOODSNAME = "INTENT_GOODSNAME";
    public static final String INTENT_SHOW_DIV = "INTENT_SHOW_DIV";
    //从图片库的树形结构传递到图片列表请求所用的divId
    public static final String INTENT_DIV_ID = "INTENT_DIV_ID";
    public static List<QueryPayMoneyOrderForSettleEntity> OrderForSettleEntityList = new ArrayList<>();
    //  商品分类的
    public static List<NoteEntity> goodCategoryList = null;
    public static final String INTENT_DIV_NAME = "INTENT_DIV_NAME";
    public static final String INTENT_TYPE = "INTENT_TYPE";
    public static final String PIC_ADD = "PIC_ADD";
    public static final String PIC_DEL = "PIC_DEL";
    public static final String PIC_EDIT = "PIC_EDIT";
    public static final String INTENT_PARENTID = "INTENT_PARENTID";
    public static final String INTENT_BASEID = "INTENT_BASEID";

    public static final int CARD_QUERY = 89;
    public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
    //公共的点击大图URL
    public static final String INTAENT_PUBLIC_URL = "INTAENT_PUBLIC_URL";
    public static final String QUERY_QUOTE_WHERE = "QUERY_QUOTE_WHERE";
    public static final int REQUEST_QUOTE = 99;
    //从付款列表到付款查询的请求码
    public static final int PAYMENT_REQUEST_CODE = 100;
    public static final String ORDERTITLENUMBER = "ORDERTITLENUMBER";
    public static final String GOODSNAME = "GOODSNAME";
    public static final String SELLERSNAME = "SELLERSNAME";

    public static final int PAYMENTRESULTOK = 101;
    //获取的菜单
    public static List<MenuUtils.MenusEntityExtra> MenuList = null;
    //菜单的初始数据
    public static List<XmlUtils.MenuBean> MenuBean;

    public static PublicArg publicArg = null;
    public static List<String> numbers;

    /*公共的功能编码*/
    public static String SYS_FUNC = "";


    //                      funcCode	菜单项	           说明
    public static String SYS_FUNC1011 = "1011";//	业务平台	各类业务活动的工作平台
    public static String SYS_FUNC10110021 = "10110021";//基础配置	商品目录的管理、会员主页的配置
    public static String SYS_FUNC101100210001 = "101100210001";//商品管理	商品目录的维护
    public static String SYS_FUNC10110028 = "10110028";// 	报价管理	报价的创建和发布
    public static String SYS_FUNC10110031 = "10110031";//	关系管理	管理各类业务关系
    public static String SYS_FUNC101100310001 = "101100310001";// 	业务合作会员目录	有业务合作的会员目录，可设置业务合作类型
    public static String SYS_FUNC101100310002 = "101100310002";//	关注会员目录	关注的会员目录，分为五级关注度管理，可调整关注等级
    public static String SYS_FUNC101100310003 = "101100310003";//	待审批申请	对方希望和你建立某种业务关系的申请，同意后即此业务关系建立
    public static String SYS_FUNC101100310004 = "101100310004";//	群组管理	选择加入平台中那些群组，加入群组可分享此群组的业务关系
    public static String SYS_FUNC10110035 = "10110035";//	协议管理	合作协议是会员间框架性的合同，用以约束双方交易
    public static String SYS_FUNC101100350001 = "101100350001";//	执行中合作协议	双方确认后进入执行状态的合作协议，可在此依据协议下订单
    public static String SYS_FUNC101100350002 = "101100350002";//	待审批合作协议	对方提交等待确认的协议，此协议尚未执行
    public static String SYS_FUNC101100350003 = "101100350003";//	已提交合作协议	提交待对方确认的协议，此协议尚未执行
    public static String SYS_FUNC101100350004 = "101100350004";//	待审批合作协议	对方提交等待确认的协议，此协议尚未执行
    public static String SYS_FUNC101100350005 = "101100350005";//	执行中合作协议	双方确认后进入执行状态的合作协议，可在此依据协议下订单
    public static String SYS_FUNC10110041 = "10110041";//订单管理	拣单车、下订单、双方议价、签单的工作平台
    public static String SYS_FUNC101100410001 = "101100410001";//	合作协议	执行中合作协议列表，可依据协议直接下订单
    public static String SYS_FUNC101100410002 = "101100410002";//	采购拣单车	收集的销售报价的拣单车，可下采购订单
    public static String SYS_FUNC101100410003 = "101100410003";//	销售拣单车	收集的采购报价的拣单车，可销售订单
    public static String SYS_FUNC101100410004 = "101100410004";//	新建订单	对方还没有确认的订单，可以撤回
    public static String SYS_FUNC101100410005 = "101100410005";//	待审批订单	对方提议的订单，确认后即签定，也可以修改内容交由对方审批确认
    public static String SYS_FUNC101100410006 = "101100410006";//	执行中订单	双方确认后的订单，可以执行收发货和收付款
    public static String SYS_FUNC10110051 = "10110051";//	收发货管理	订单发货、收货、退货、收退货操作
    public static String SYS_FUNC101100510001 = "101100510001";//	地址管理	收发货的仓库地址配置
    public static String SYS_FUNC101100510002 = "101100510002";//	收货	采购的货物送到时执行的签收操作
    public static String SYS_FUNC101100510003 = "101100510003";//	发货	根据销售订单向买方发出货品执行的操作
    public static String SYS_FUNC101100510004 = "101100510004";//	退货	对收到货物不满意时退回货品执行的操作
    public static String SYS_FUNC101100510005 = "101100510005";//	收退货	对方退货送到时执行的签收操作
    public static String SYS_FUNC10110061 = "10110061";//收付款管理	订单付款、收款、退款、收退款操作
    public static String SYS_FUNC101100610001 = "101100610001";//	银行账号管理	收付款所用银行账号配置
    public static String SYS_FUNC101100610002 = "101100610002";//	付款	根据采购订单向卖方支付货款执行的操作
    public static String SYS_FUNC101100610003 = "101100610003";//	收款	收到销售货款时执行的签收操作
    public static String SYS_FUNC101100610004 = "101100610004";//	退款	退回货款时执行的操作
    public static String SYS_FUNC101100610005 = "101100610005";//	收退款	收到退款时执行的签收操作
    public static String SYS_FUNC10110081 = "10110081";//	结款单管理	结款单为买卖双方签署的一种合同，用法律文件形式确认一笔卖方的应收账款
    public static String SYS_FUNC101100810001 = "101100810001";//	新创建的结款单	等待对方确认的结款单
    public static String SYS_FUNC101100810002 = "101100810002";//	待审批结款单	对方创建的结款单，审查内容，如果确认即签定，也可修改内容由对方审批确认
    public static String SYS_FUNC101100810003 = "101100810003";//	执行中结款单	双方对缔结条款内容都确认后的结款单
    public static String SYS_FUNC101100810005 = "101100810005";//	登记担保资源	按照规定提交结款单有关资料，由平台审核登记为担保资源后以在融资中使用
    public static String SYS_FUNC1021 = "1021";//操作员管理	操作员的创建和分配可执行功能

    //发货操作
    public static String DIALOG_CONTENT_1 = "1.\t此界面为“发货”列表界面；\n" +
            "2.\t列表展示的为所有发货订单；\n" +
            "3.\t点击订单号蓝色订单编号可进入该订单详情页面；\n" +
            "4.\t在“本次发货数量”文本框中可输入具体商品的发货数量；\n" +
            "5.\t点击订单号前面的单选框可选择该订单；\n" +
            "6.\t点击界面底端的“全选”按钮可以选中列表中全部订单；\n" +
            "7.\t点击界面底端的“重置”按钮可清空列表中已输入的信息；\n" +
            "8.\t点击界面底端的“发货”按钮可针对列表中已选订单进行发货；\n" +
            "9.\t点击标题栏左侧的“菜单”按钮可展开左侧的侧边栏直接切换到其他模块菜单；\n" +
            "10.\t点击标题栏右侧的“放大镜”图标页面跳转到查询页面，根据查询条件查询列表中的订单数据；\n";

    //收发货地址列表
    public static String DIALOG_CONTENT_2 = "1.\t此界面为“收发货地址”列表页面；\n" +
            "2.\t列表展示为所有的收发货地址；\n" +
            "3.\t点击“编辑”按钮页面跳转到收发货地址编辑页面；\n" +
            "4.\t点击“删除”按钮，可删除当前的收发货地址；\n" +
            "5.\t点击界面底端的“新增地址”按钮页面跳转到地址新增页面，可以新增收发货地址；\n" +
            "6.\t点击标题栏左侧的“菜单”按钮展开左侧的侧边栏直接切换其他菜单模块；\n" +
            "7.\t点击标题栏右侧的“放大镜”图标页面跳转到查询页面根据查询条件搜索列表中的地址；\n";

    //收发货管理-收货列表
    public static String DIALOG_CONTENT_3 = "1.\t此界面为“收货”订单列表界面；\n" +
            "2.\t界面列表展示了所有的收货订单；\n" +
            "3.\t点击订单号蓝色编号页面跳转到订单详情页面；\n" +
            "4.\t点击“本次收货数量”文本框可直接输入收货数量；\n" +
            "5.\t点击“订单号”前面的单选框可选择当前收货订单；\n" +
            "6.\t点击界面底端的“全选”按钮，可选中列表中所有的收货订单；\n" +
            "7.\t点击界面底端的“重置”按钮，可清空列表中所有已输入的信息；\n" +
            "8.\t点击界面底端的“收货”按钮确认收货；\n" +
            "9.\t点击标题栏左侧的“菜单”按钮展开左侧的侧边栏可直接切换到其他菜单模块；\n" +
            "10.\t点击标题栏右侧的“放大镜”图片页面跳转到查询页面根据查询条件搜索收货订单\n";

    //退货订单列表
    public static String DIALOG_CONTENT_4 = "1.\t此界面为“退货”订单列表界面；\n" +
            "2.\t界面列表展示了所有的退货订单；\n" +
            "3.\t点击订单号蓝色编号页面跳转到订单详情页面；\n" +
            "4.\t点击“本次退货数量”文本框可直接输入退货数量；\n" +
            "5.\t点击“订单号”前面的单选框可选择当前退货订单；\n" +
            "6.\t点击界面底端的“全选”按钮，可选中列表中所有的退货订单；\n" +
            "7.\t点击界面底端的“重置”按钮，可清空列表中所有已输入的信息；\n" +
            "8.\t点击界面底端的“退货”按钮确认退货；\n" +
            "9.\t点击标题栏左侧的“菜单”按钮展开左侧的侧边栏可直接切换到其他菜单模块；\n" +
            "10.\t点击标题栏右侧的“放大镜”图标页面跳转到查询页面根据查询条件搜索退货订单\n";

    // 收退货列表
    public static String DIALOG_CONTENT_5 = "1.\t此界面为“收退货”订单列表界面；\n" +
            "2.\t界面列表展示了所有的收退货订单；\n" +
            "3.\t点击订单号蓝色编号页面跳转到订单详情页面；\n" +
            "4.\t点击“本次收货数量”文本框可直接输入收货数量；\n" +
            "5.\t点击“订单号”前面的单选框可选择当前收退货订单；\n" +
            "6.\t点击界面底端的“全选”按钮，可选中列表中所有的收退货订单；\n" +
            "7.\t点击界面底端的“重置”按钮，可清空列表中所有已输入的信息；\n" +
            "8.\t点击界面底端的“签收退货”按钮确认签收退货；\n" +
            "9.\t点击标题栏左侧的“菜单”按钮展开左侧的侧边栏可直接切换到其他菜单模块；\n" +
            "10.\t点击标题栏右侧的“放大镜”图标页面跳转到查询页面根据查询条件搜索收退货订单\n";

    //    新建结款单列表
    public static String DIALOG_CONTENT_6 = "1.\t此界面为“新建结款单”列表界面；\n" +
            "2.\t列表展示不同的结款单据；\n" +
            "3.\t点击左上方的“创建结款单”按钮，页面跳转到创建结款单界面；\n" +
            "4.\t点击“结款单号”蓝色编号进入结款单详情界面；\n" +
            "5.\t点击顶端标题栏栏左侧的“菜单”按钮展开左侧侧边框菜单；\n" +
            "6.\t点击标题栏右侧“放大镜”图标，页面跳转到查询界面，根据查询条件查询结款单数据；\n";

    // 创建结款单（添加、删除订单）
    public static String DIALOG_CONTENT_7 = "1.\t此界面为“创建结款单”界面；\n" +
            "2.\t付款方和收款方数据自动获取；\n" +
            "3.\t点击付款账号下拉选择付款账号；\n" +
            "4.\t点击收款账号下拉选择收款账号；\n" +
            "5.\t结款时间，点击选择具体的时间；\n" +
            "6.\t点击“添加订单行”界面跳转到订单列表页面，添加后的订单展示在“订单清单”下方；\n" +
            "7.\t点击“删除订单行”，可选择订单清单下方的订单进行删除；\n" +
            "8.\t点击“订单号”后面的蓝色编号进入订单详情界面；\n" +
            "9.\t点击界面最下方的“全选”按钮全部选中订单清单中订单；\n" +
            "10.\t点击“确认结款”完成结款；\n" +
            "11.\t点击顶端的标题栏左侧的图标返回上一级界面；\n";

    //  待审批结款单列表
    public static String DIALOG_CONTENT_8 = "1.\t此界面为“待审批结款单”界面；\n" +
            "2.\t列表展示每一个结款单数据；\n" +
            "3.\t点击“结款单号”蓝色编号页面跳转到结款单详情界面；\n" +
            "4.\t点击“我提倡的时间”后面文本框可直接输入相应的时间；\n" +
            "5.\t点击“我提议的金额”后面文本框可直接输入相应金额；\n" +
            "6.\t点击每一行的结款单号前面的选框可选中；\n" +
            "7.\t点击页面底端的“全选”按钮可选中列表中所有结款单；\n" +
            "8.\t页面底端有“同意”和“拒绝”两个操作按钮；\n" +
            "9.\t点击顶端标题栏左侧的“菜单”按钮展开左侧的侧边栏菜单；\n" +
            "10.\t点击顶端标题栏右侧的“放大镜”图标页面跳转到查询界面，根据查询条件搜索结款单；\n";

    //报价管理列表
    public static String DIALOG_CONTENT_9 = "1.\t操作说明：报价管理界面以列表形式呈现，       \n" +
            "2.\t显示更多数据可直接向上滑动手机屏幕加载，\n" +
            "3.\t点击界面最上方左侧菜单按钮，可以展开\n" +
            "4.\t侧边栏菜单项，可直接切换菜单模块；点击\n" +
            "5.\t标题栏右侧放大镜按钮进行查询页面可直接\n" +
            "6.\t进行条件查询；点击标题栏下方的“创建报价”\n" +
            "7.\t按钮可直接创建报价；每个列表下方都会\n" +
            "8.\t单独呈现两个按钮“指定发布范围”和\n" +
            "9.\t“编辑”，点击“指定发布范围”直接选定\n" +
            "10.\t发布范围，点击“编辑”打开当前列表数据\n" +
            "11.\t维护修改；\n";

    //创建报价页面
    public static String DIALOG_CONTENT_10 = "1.\t此界面为“创建报价”界面               \n" +
            "2.\t点击商品名称一栏页面跳转选择商品分类页面，通过选择分类来选择相应的商品，重新选择的商品，会在“商品名称”一栏中显示出来；\n" +
            "3.\t点击“报价类型”一栏选择“报价类型”；\n" +
            "4.\t标题图片可直接点击图片上传替换，上传图片页面\n" +
            "5.\t跳转到图片库管理页面选择图片，选择的图片，会直接将此页面的图片替换掉；\n" +
            "6.\t数量可直接在文本框中输入，单价也可直接在文本框中输入一个价格区间，后面价格固定为“¥”，\n" +
            "7.\t选择有效期时间范围，在下方的备注栏中输入文字备注；\n" +
            "8.\t点击页面下方的“确定”按钮保存，创建报价成功；\n" +
            "9.\t点击标题栏左侧的图标返回上一级页面；\n" +
            "i.\t\n";

    //初次创建报价面
    public static String DIALOG_CONTENT_11 = "1.\t此界面为“初次创建报价”界面；\n" +
            "2.\t点击商品名称一栏页面跳转选择商品分类页面，通过选择分类来选择相应的商品，重新选择的商品，会在“商品名称”一栏中显示出来；\n" +
            "3.\t点击“报价类型”一栏选择“报价类型”；\n" +
            "4.\t标题图片：点击上传“选择”按钮，页面切换至图片库管理界面，选择相应的标题图片，选择后图片会显示在“标题图片”一栏；\n" +
            "5.\t数量可直接在文本框中输入，单价也可直接在文本框中输入一个价格区间，后面价格固定为“¥”，\n" +
            "6.\t选择有效期时间范围，在下方的备注栏中输入文字备注；\n" +
            "7.\t点击页面下方的“确定”按钮保存，创建报价成功；\n" +
            "8.\t点击标题栏左侧的图标返回上一级页面；\n" +
            "\n";

    //选择指定范围
    public static String DIALOG_CONTENT_12 = "1.\t此界面为“指定发布范围”界面；\n" +
            "2.\t选择发布范围分为“公开”和“选择指定发布范围”两种；\n" +
            "3.\t两种发布形式为单选，勾选“公开”或者“选择发布范围”；\n" +
            "4.\t勾选“选择发布范围”，下方“合作会员”和“群组”可添加相应的会员，点击后面的“+”号，页面跳转到选择合作会员和群组的界面，选择会员；\n" +
            "5.\t选择合作合作会员或者群组后，相应的会员会在下方展示出来；\n" +
            "6.\t点击标题栏左侧图标返回上一级页面\n" +
            "7.\t点击标题栏右侧的“确认”按钮保存；\n\n\n";

    //会员、群组展开页面
    public static String DIALOG_CONTENT_13 = "1.\t选择相应的“合作会员”或者“群组”后，会员信息在下方展示出来，可对应进行删除操作；\n" +
            "2.\t对应一行会员信息，手指向左滑动，右侧会出现红色“删除”图标，可直接删除此行的会员信息；\n";

    //关注会员目录
    public static String DIALOG_CONTENT_14 = "1.\t此界面为“关注会员目录”列表界面；\n" +
            "2.\t列表展示“会员名称”、“注册地址”、“关注等级”相关信息；\n" +
            "3.\t列表项中有“升级到业务合作”和“取消关注”两个操作项；\n" +
            "4.\t点击“关注等级”加减号可直接增加或者减少关注等级；\n" +
            "5.\t点击“升级到业务合作”界面出现弹窗：我能采购和我能销售以及降级为仅关注；\n" +
            "6.\t点击“取消关注”按钮，取消会员关注；\n" +
            "7.\t点击标题栏左侧“菜单”按钮展开左侧侧边栏菜单；\n" +
            "8.\t点击标题栏右侧的“放大镜”按钮页面跳转到查询界面，根据查询条件查询关注会员数据；\n" +
            "\n";

    //升级业务提醒
    public static String DIALOG_CONTENT_15 = "1.\t此界面为“关注会员目录”弹窗界面；\n" +
            "2.\t点击“升级到业务合作”按钮，界面出现弹窗提醒；\n" +
            "3.\t点击“我能采购”将业务合作升级到我能采购关系；\n" +
            "4.\t点击“我能销售”将业务合作升级到我能销售关系；\n" +
            "5.\t点击“降为仅关注”将业务合作关系进行降级；\n" +
            "6.\t点击下方的“确定”按钮进行合作关系保存；\n" +
            "7.\t点击下方的“取消”按钮，关闭弹窗；\n";

    //待审批申请
    public static String DIALOG_CONTENT_16 = "1.\t此界面为“待审批申请”列表界面；\n" +
            "2.\t列表展示“会员名称”、“业务关系”相关信息；\n" +
            "3.\t在审批列表中有“同意”和“拒绝”两个操作按钮；\n" +
            "4.\t点击“同意”按钮同意申请；\n" +
            "5.\t点击“拒绝”按钮拒绝申请\n" +
            "6.\t列表界面显示更多数据，手指向上滑动界面加载更多数据；\n" +
            "7.\t点击标题栏左侧的“菜单”按钮，展开左侧侧边栏，可直接切换到其他菜单模块；\n";

    //群组管理列表
    public static String DIALOG_CONTENT_17 = "1.\t此界面为“群组管理”列表界面；\n" +
            "2.\t列表展示了所有的群组信息；\n" +
            "3.\t点击“申请加群”按钮申请加入此群；\n" +
            "4.\t点击“申请退群”按钮申请退出此群；\n" +
            "5.\t点击“申请加群”后状态变更成“审核中”等待审核通过后加入；\n" +
            "6.\t点击标题栏左侧的“菜单”按钮展开左侧的侧边栏，可直接在侧边栏切换到其他菜单模块；\n" +
            "7.\t点击标题栏右侧的“放大镜”图标，页面跳转到查询页面，根据查询条件搜索群组数据；\n" +
            "\n";

    //创建合作协议-销售
    public static String DIALOG_CONTENT_18 = "1.\t此界面为“创建合作协议”销售界面；\n" +
            "2.\t业务类型、采购方、供货方数据为自动获取；\n" +
            "3.\t点击“选择协议商品品类”页面跳转到选择商品品类界面，选择商品品类后会在“已选商品列表”下方展示出来，可点击删除商品品类；\n" +
            "4.\t点击界面最下方的“保存”按钮创建合作协议成功；\n";

    //创建合作协议-采购
    public static String DIALOG_CONTENT_19 = "1.\t此界面为“创建合作协议”采购界面；\n" +
            "2.\t业务类型、采购方、供货方数据为自动获取；\n" +
            "3.\t点击“选择协议商品品类”页面跳转到选择商品品类界面，选择商品品类后会在“已选商品列表”下方展示出来，可点击删除商品品类；\n" +
            "4.\t点击界面最下方的“保存”按钮创建合作协议成功；\n";

    //选择商品品类
    public static String DIALOG_CONTENT_20 = "1.\t此界面为“协议商品品类”分类目录界面；\n" +
            "2.\t下方的默认的商品品类展示商品一级品类，可点击一级品类展示二级品类，点击二级品类可展开三级品类；\n" +
            "3.\t选择品类后，已选中的品类会在上方的“已选商品列表”下方展示出来，可直接删除商品；\n" +
            "4.\t点击上方的标题栏左侧的图标返回上一级界面；\n" +
            "5.\t点击上方的标题栏右侧的“保存”按钮，保存已选择的商品，页面返回上一级界面；\n";

    //编辑合作协议_下订单
    public static String DIALOG_CONTENT_21 = "1.\t此界面为“编辑合作协议”界面；\n" +
            "2.\t业务类型、采购方、供货方数据为自动获取；\n" +
            "3.\t点击“选择协议商品品类”页面跳转到选择商品品类界面，选择商品后会在“已选商品列表”下方展示出来，可点击删除商品品类；\n" +
            "4.\t下方的信息可根据情况实际的选择及录入；\n" +
            "5.\t点击界面最下方的“下订单”按钮页面跳转到下单页面；\n";

    //编辑合作协议_修改
    public static String DIALOG_CONTENT_22 = "1.\t此界面为“编辑合作协议”界面；\n" +
            "2.\t业务类型、采购方、供货方数据为自动获取；\n" +
            "3.\t点击“选择协议商品品类”页面跳转到选择商品品类界面，选择商品品类后会在“已选商品列表”下方展示出来，可点击删除商品品类；\n" +
            "4.\t下方的信息可根据情况实际的选择及录入；\n" +
            "5.\t点击界面最下方的“确定”按钮合作协议信息编辑成功；\n";

    //采购拣单车列表
    public static String DIALOG_CONTENT_23 = "1.\t此界面为“采购拣单车”列表界面；\n" +
            "2.\t列表中展示销售方名称、商品品类数量、相关商品名称；\n" +
            "3.\t列表显示更多数据可通过手指向上滑动加载显示更多数据；\n" +
            "4.\t界面的右下方返回顶部按钮，点击此按钮界面返回最顶部；\n" +
            "5.\t点击列表中一销售方的数据，页面跳转到此销售方商品详细界面；\n" +
            "6.\t点击标题栏左侧的“菜单”按钮，左侧展开侧边框，可直接切换其他菜单模块；\n" +
            "\n";

    //采购商品列表
    public static String DIALOG_CONTENT_24 = "1.\t此界面为“采购拣单车”销售方的商品列表页；\n" +
            "2.\t显示当前销售方的名称及商品数据；\n" +
            "3.\t列表展示具体商品，显示商品名称、价格区间、数量\n" +
            "4.\t每种商品可直接在数量后面的文本框输入商品数量，也可点击后面的“删除”按钮删除此件商品；\n" +
            "5.\t商品可勾选多个，点击界面最低端的“全选”按钮选中界面中全部商品；\n" +
            "6.\t点击“重置”按钮，清空列表中已经输入的数据；\n" +
            "7.\t点击底端的“下单”按钮，页面跳转到下单页面；\n" +
            "8.\t点击标题栏左侧的图标返回上一级界面；\n";

    //销售拣单车
    public static String DIALOG_CONTENT_25 = "此界面为“销售拣单车”列表界面；\n" +
            "列表中展示采购方名称、商品数量、相关商品名称；\n" +
            "列表显示更多数据可通过手指向上滑动加载显示更多数据；\n" +
            "界面的右下方返回顶部按钮，点击此按钮界面返回最顶部；\n" +
            "点击列表中一采购方的数据，页面跳转到此采购方商品详细界面；\n" +
            "点击标题栏左侧的“菜单”按钮，左侧展开侧边框，可直接切换其他菜单模块；\n";

    //销售拣单车商品列表
    public static String DIALOG_CONTENT_26 = "1.\t此界面为“销售拣单车”采购方的商品列表页；\n" +
            "2.\t显示当前采购方的名称及商品数量；\n" +
            "3.\t列表展示具体商品，显示商品名称、价格区间、数量及指定商品、删除两个操作按钮；\n" +
            "4.\t每种商品可直接在数量后面的文本框输入商品数量；\n" +
            "5.\t点击“指定商品”按钮，页面跳转到指定商品列表；\n" +
            "6.\t点击“删除”按钮，删除此件商品；\n" +
            "7.\t商品可勾选多个，点击界面最低端的“全选”按钮选中界面中全部商品；\n" +
            "8.\t点击“重置”按钮，清空列表中已经输入的数据；\n" +
            "9.\t点击底端的“下单”按钮，页面跳转到下单页面；\n" +
            "10.\t点击标题栏左侧的图标返回上一级界面；\n";

    //操作员管理列表
    public static String DIALOG_CONTENT_27 = "1.\t此界面为“业务操作员管理”列表界面；\n" +
            "2.\t通过列表形式展示操作员的信息；\n" +
            "3.\t点击右上方的“添加”按钮页面跳转到新增操作员界面；\n" +
            "4.\t点击已经添加的操作员列表，页面跳转到此操作员详细页面；\n" +
            "5.\t点击标题栏左侧的“菜单”按钮，打开左侧的侧边栏，可直接切换到其他模块菜单；\n" +
            "6.\t点击标题栏右侧的“放大镜”图标，界面跳转到搜索页面，搜索列表操作员信息；\n";


    //商品管理分类
    public static String DIALOG_CONTENT_28 = "1.此界面为“商品分类”界面；\n" +
            "2.页面展示了所有商品分类目录；\n" +
            "3.分类目录默认展示商品的一级目录，点击每一个商品分类会展开此分类的下级分类，如果是末级分类，点击进入该分类下的全部商品列表；\n" +
            "4.点击“添加”按钮可添加新的商品分类；\n" +
            "5.点击顶端的“菜单”展开左侧的侧边框，可直接切换其他的菜单模块；\n";

    //商品管理列表
    public static String DIALOG_CONTENT_29 = "1.此界面为分类下面的商品列表页面；\n" +
            "2.点击左上方的“添加”按钮可以添加该分类下面的新的商品；\n" +
            "3.手指选择一件商品，界面底端出现“编辑”“停用”“详情”“取消”操作按钮；\n" +
            "4.编辑：编辑修改商品信息；\n" +
            "5.停用：停用此件商品；\n" +
            "6.详情：查看商品详细信息；\n" +
            "7.点击具体一件商品进入商品明细页面；\n" +
            "8点击右下方的“向上箭头”界面返回页面顶端；\n" +
            "9.点击左上方的图标页面返回上一级页面；\n";
    //商品信息编辑
    public static String DIALOG_CONTENT_30 = "1.此界面为编辑商品信息页面；\n" +
            "2.点击“选择分类”页面跳转到选择商品分类界面；\n" +
            "3.点击轮播图片下面的“选择”上传按钮可以上传多张商品轮播图片，上传后的图片可以" +
            "4.点击图片右上方的“x”按钮删除图片；\n" +
            "5.点击标题图片右侧已上传的图片，页面跳转可以重新选择标题图片；\n" +
            "6.下方为商品详情可对应修改商品的基本信息；\n" +
            "7.点击界面标题栏左侧的图标返回上一级页面；\n" +
            "8.点击界面最下方的“保存”按钮保存编辑后商品信息；\n";
    //收付款管理-付款
    public static String DIALOG_CONTENT_31 = "1.此界面为“付款”订单列表界面；\n" +
            "2.界面列表展示了所有的付款订单；\n" +
            "3.点击订单号蓝色编号页面跳转到订单详情页面；\n" +
            "4.点击“本次付款金额”文本框可直接输入金额；\n" +
            "5.点击每一个付款订单下面的备注文本框，可输入相关备注信息；\n" +
            "6.点击“订单号”前面的单选框可选择当前付款订单；\n" +
            "7.点击界面底端的“全选”按钮，可选中列表中所有的付款订单；\n" +
            "8.点击界面底端的“重置”按钮，可清空列表中所有已输入的信息；\n" +
            "9.点击界面底端的“线下付款”按钮，付款为线下付款，线上不涉及支付；\n" +
            "10.点击标题栏左侧的“菜单”按钮展开左侧的侧边栏可直接切换到其他菜单模块；\n" +
            "11.点击标题栏右侧的“放大镜”图标页面跳转到查询页面根据查询条件搜索付款订单\n";
    //收付款管理-收款列表
    public static String DIALOG_CONTENT_32 = "1.此界面为“收款”订单列表界面；\n" +
            "2.界面列表展示了所有的收款订单；\n" +
            "3.点击订单号蓝色编号页面跳转到订单详情页面；\n" +
            "4.点击“本次付款金额”文本框可直接输入金额；\n" +
            "5.点击“订单号”前面的单选框可选择当前收款订单；\n" +
            "6.点击界面底端的“全选”按钮，可选中列表中所有的收款订单；\n" +
            "7.点击界面底端的“收款”按钮确认收款；\n" +
            "8.点击标题栏左侧的“菜单”按钮展开左侧的侧边栏可直接切换到其他菜单模块；\n" +
            "9点击标题栏右侧的“放大镜”图标页面跳转到查询页面根据查询条件搜索收款订单\n";
    //收退款管理列表
    public static String DIALOG_CONTENT_33 = "1.此界面为“收退款”订单列表界面；\n" +
            "2.界面列表展示了所有的收退款订单；\n" +
            "3.点击订单号蓝色编号页面跳转到订单详情页面；\n" +
            "4.点击“订单号”前面的单选框可选择当前收退款订单；\n" +
            "5.点击界面底端的“全选”按钮，可选中列表中所有的收退款订单；\n" +
            "6.点击界面底端的“收款”按钮确认收款；\n" +
            "7.点击标题栏左侧的“菜单”按钮展开左侧的侧边栏可直接切换到其他菜单模块；\n" +
            "8点击标题栏右侧的“放大镜”图标页面跳转到查询页面根据查询条件搜索收退款订单\n";
    //收付款管理-退款管理列表
    public static String DIALOG_CONTENT_34 = "1.此界面为“退款”订单列表界面；\n" +
            "2.界面列表展示了所有的退款订单；\n" +
            "3.点击订单号蓝色编号页面跳转到订单详情页面；\n" +
            "4.点击“订单号”前面的单选框可选择当前退款订单；\n" +
            "5.点击每一行的退款订单下方的备注文本框可直接输入相关的备注信息；\n" +
            "6.点击“本次付款金额”后面的文本框可直接输入相应金额；\n" +
            "7.点击界面底端的“全选”按钮，可选中列表中所有的退款订单；\n" +
            "8.点击界面底端的“重置”按钮可清空已经输入的数据信息；\n" +
            "9.点击界面底端的“退款确认”按钮确认退款；\n" +
            "10.点击标题栏左侧的“菜单”按钮展开左侧的侧边栏可直接切换到其他菜单模块；\n" +
            "11.点击标题栏右侧的“放大镜”图标页面跳转到查询页面根据查询条件搜索退款订单\n";
    //收付款管理-银行账户管理
    public static String DIALOG_CONTENT_35 = "1.此界面为“银行账户管理”列表界面；\n" +
            "2.界面列表展示了所有的银行账户；\n" +
            "3.点击“编辑”按钮页面跳转到编辑页面，可直接编辑银行账户信息；\n" +
            "4.点击“删除”按钮，直接删除当前银行账户；\n" +
            "5.点击界面底端的“新增账户”页面跳转到账户新增页面；\n" +
            "6.点击标题栏左侧的“菜单”按钮展开左侧的侧边栏可直接切换到其他菜单模块；\n" +
            "7.点击标题栏右侧的“放大镜”图标页面跳转到查询页面根据查询条件搜索银行账户信息；\n";
    //选择图片
    public static String DIALOG_CONTENT_36 = "1.此界面为“选择图片”界面\n" +
            "2.点击某个图片即为选中，再次点击即为取消选中，选择图片之后，点击确定按钮，可以将选择的图片传回到需要图片的页面；\n" +
            "3.点击取消按钮，页面将取消已选择的图片，并返回上一级页面；\n";

    //谁在关注我
    public static String DIALOG_CONTENT_37 = "1.此界面为“谁在关注我”列表界面；\n" +
            "2.列表展示已关注我的会员名称列表；";
    //搜索供应商
    public static String DIALOG_CONTENT_38 = "1.此界面为“搜索供应商”列表界面；\n" +
            "2.列表展示供应商相关信息；\n" +
            "3.列表项中有“加关注”、“建立采购关系”和“建立销售关系”三个操作项；\n" +
            "4.点击“建立采购关系”可申请与供应商建立采购关系；\n" +
            "5.点击“建立销售关系”可申请与供应商建立销售关系；\n";

    //bundle的key
    public static final String BUNDLE_TYPE = "BUNDLE_TYPE";
    public static final String BUNDLE_TYPE_0 = "0";
    public static final String BUNDLE_TYPE_1 = "1";
    public static final String BUNDLE_TYPE_2 = "2";
    public static final String BUNDLE_TYPE_3 = "3";
    //Handler传递
    public static final int HANDLERTYPE_0 = 0;
    public static final int HANDLERTYPE_1 = 1;
    public static final int HANDLERTYPE_2 = 2;
    public static final int HANDLERTYPE_3 = 3;
    public static final int HANDLERTYPE_4 = 4;
    public static final int HANDLERTYPE_5 = 5;
    public static final int HANDLERTYPE_6 = 6;


    //订单列表布局
    public static final int type_one = 1;
    public static final int type_two = 2;
    public static final int type_three = 3;

    public static final int protocol_0 = 0;
    public static final int protocol_1 = 1;
    public static final int protocol_2 = 2;
    public static final int fra_1 = 1;
    public static final int fra_2 = 2;
    //订单管理布局
    public static final String ORDER_TYPE = "ORDER_TYPE";
    public static final int order_type_buy = 0;
    public static final int order_type_sell = 1;
    public static int order_type_dao = 0;
    //协议商品品类全局变量
    public static List<ProtocalProductItem.GoodLevelEntity> finalGoodLevelEntities;
    public static List<ProtocalProductItem.GoodLevelEntity> oldGoodLevelEntities;

    /**
     * 结款单详情
     * 确认状态（1没有确认、
     * 2付款方确认、
     * 3收款方确认、
     * 4双方确认、
     * 5付款方确认终止、
     * 6收款方确认终止、
     * 7双方确认终止）
     */

    public static final int STATUS_TYPE_0 = 0;
    public static final int STATUS_TYPE_1 = 1;
    public static final int STATUS_TYPE_2 = 2;
    public static final int STATUS_TYPE_3 = 3;
    public static final int STATUS_TYPE_4 = 4;
    public static final int STATUS_TYPE_5 = 5;
    public static final int STATUS_TYPE_6 = 6;
    public static final int STATUS_TYPE_7 = 7;


    //判断由那个界面跳转搜索
    public static final int PAYMENT_SEARCH_TYPE = 0; //付款界面
    public static final int RECEIPT_SEARCH_TYPE = 1; //收款界面
    public static final int REFUND_SEARCH_TYPE = 2; //退款界面
    public static final int RECEIVE_REFUND_SEARCH_TYPE = 3; //收退款界面
    public static final int BANK_BILLS_ACTIVITY_TYPE = 4; //银行账户管理
    public static final int ECPRESS_ADDRESS_ACTIVITY_TYPE = 5; //收货地址管理
    public static final int ECPRESS_SEND_ACTIVITY_TYPE = 6; //发货
    public static final int ECPRESS_QUIT_ACTIVITY_TYPE = 7; //退货
    public static final int ECPRESS_QUIT_HARVEST_ACTIVITY_TYPE = 8; //退货
    public static final int CREATE_SECTION_BILLS_ACTIVITY_TYPE = 9; //创建结款单
    public static final int ADD_SECTION_BILLS_ACTIVITY_TYPE = 10; //添加订单
    public static final int WAIT_CHECK_BILLS_ACTIVITY_TYPE = 11; //待审批结款单
    public static final int EXECUTE_BILLS_ACTIVITY_TYPE = 12; //执行中结款单
    public static final int REGISTER_BILLS_ACTIVITY_TYPE = 13; //结款单登记担保列表
    public static final int MMBWAREHOUSE_BILLS_ACTIVITY_TYPE = 14; //地址管理列表查询


    //操作银行卡的类型
    public static final String CARD_TYPE = "CARD_TYPE";
    public static final int START_ACTIVITY_TYPE = 10;//acticity跳转初始值
    public static final int CARD_EDIT = 20;//银行卡编辑
    public static final int CARD_ADD = 21;//添加银行卡
    public static final int ADDRESS_EDIT = 22;//收获地址编辑
    public static final int ADDRESS_ADD = 23;//添加收获地址
    public static final int ADDCOMMODITY_ADD = 24;//创建结款但
    public static final int ADDRESS_SEARCH = 25;//收货地址列表查询
    public static final int SENDGOODS_SEARCH = 26;//发货列表查询
    public static final int APPUSER_EDITUSER = 27;//编辑操作员
    public static final int APPUSER_ADDUSER = 28;//添加操作员
    public static final int CREATE_USER = 29;//添加或者修改操作员
    public static final int CREATE_MYPENDINGSTTLE = 30;//获取我的提交的待对方审批结款单列表
    public static final int ADDCOMMDITY = 31;//：获取代付款订单列表
    public static final int appSettle_querySttleManage = 32;//执行中借款单查询
    public static final int QUIT_SEARCH = 33;//退货查询界面
    public static final int QUIT_GET_SEARCH = 34;//退货签收查询界面
    public static final int QUIT_RETURN_SEARCH = 35;//退货签收查询界面
    public static final int WAIT_CHECK_BILLS_ACTIVITY_TYPE_SEARCH = 36;//待审批结款单查询界面
    public static final int REGISTER_BILLS_ACTIVITY_TYPE_SEARCH = 37;//登记担保资源查询界面
    public static final int ADD_SECTION_BILLS_ACTIVITY_TYPE_SEARCH = 38;//获取代付款订单列表

    //判断解析类型
    public static String JSONFATHERRESPON = "";

    public static MmbBankAccountEntity cardInfoEntity = null;

    //合作协议布局
    public static final String PROTOCOL_TYPE = "PROTOCOL_TYPE";
    public static final String FRA_TYPE = "FRA_TYPE";
    public static String PROTOCOL_TITLE = "";
    public static String PROTOCOL_STATUS = "3";
    public static boolean PROTOCOL_SEARCH = false;
    public static int PROTOCOL_TYPE_NUM = 0;
    public static int PROTOCOL_TYPE_BUY = 0;
    //订单管理参数
    public static String ORDER_STATUS = "0";//订单执行状态0:全部  1：执行中 2：已完成
    public static String ORDER_ORDER_ID = "";//订单头ID
    public static String ORDER_NAME = "";//交易对方
    public static String START_TIME = "";//签约开始时间
    public static String END_TIME = "";//签约结束时间
    public static List<OrderShellModel> orderShellModelsNew;//
    public static OrderShellAdapter adapterNew;//
    public static RelativeLayout relation_all1;//
    public static TextView order_buy_num_shell1;//

    //报价管理
    public static String GOODS_ID = "";
    public static String GOODS_NAME = "";

    //报价检索页面
    public static String SEARCH_Price_quote_goodName = "";//商品名
    public static String SEARCH_Price_quote_checkboxId = "3,2,1";//选项 默认选中123

    //银行账号管理查询
    public static String SEARCH_Account_Name = "";

    //图片库管理搜索图片
    public static String MaterialName = "";

    //登记资源列表
    public static boolean isRefurbishUI = false; //是否刷新界面

    //搜索 界面公用值
    //查询发货列表查询
    public static String SEARCH_sendgoods_OrdertitleCode = "";//string	订单号
    public static String SEARCH_sendgoods_OrderGoodName = "";//string	订单号
    public static String SEARCH_sendgoods_orderId = "";//string	订单号
    public static String SEARCH_sendgoods_goodsName = "";//int	商品名
    public static String editUser_password = "";//String	密码
    public static String editUser_password1 = "";//String	确认密码
    //结款单管理settleCode
    public static final String buy = "buy";
    public static final String sell = "sell";
    public static String isbuy = buy;
    public static String appOrderMoney_sellersId;//string		收款方id(本方付款时采用)
    public static String appOrderMoney_sellersName;//string	收款方名称(本方付款时采用)
    public static String appOrderMoney_mmbpayAccount;//	string	付款账号（本方付款）
    public static String appOrderMoney_buyersId;//string	付款方id(对方付款时采用)
    public static String appOrderMoney_buyersName;//string	付款方名称(对方付款时采用)\
    public static String appOrderMoney_mmbgetAccount;// 	string	收款账号（对方付款）
    public static String appOrderMoney_orderId = "";//string	订单号
    public static String appOrderMoney_goodsName = "";//int	商品名
    public static String appOrderMoney_settleCode = "";//	string	结款单号
    public static String appOrderMoney_oppositeName = "";//	string	结款对方
    public static String appOrderMoney_settlestatus = "";//	string	状态（1全部、2执行中、3本方请求终止、4对方请求终止）
    public static String appOrderMoney_executeStartTime = "";//datetime		开始时间
    public static String appOrderMoney_executeEndTime = "";//datetime		结束时间
    public static String appSettle_statusRegist = "";//string	状态（0全部、1未申请登记、2已申请登记）
    public static int FRGMENT_TYPE = 0;//string	状态（0全部、1未申请登记、2已申请登记）
    public static String appOrderGoods_areaCode = "";//区域编码
    public static Map<Integer, QueryPayMoneyOrderForSettleEntity> OrderForSettleEntityMap = new HashMap<>();
    //收发货
    public static int ADDRESSID = 0;//string
    //保存执行中的数据
    public static int Fragment_Postion = 0;
    public static Map<Integer, List<QuerySttleManageGain>> QuerySttleManageMap = new HashMap<>();

    //  判断是否选择图片到商品编辑的标记
    public static boolean isAddPic = false;

    public static  String  sys_actionId="";

    //判断后台返回的值
//    public static int public_code = 0;
    public static boolean public_code = false;

    public static void clearDatas() {
        appOrderMoney_orderId = "";//string	订单号
        appOrderMoney_goodsName = "";//int	商品名
        appOrderMoney_settleCode = "";//	string	结款单号
        appOrderMoney_oppositeName = "";//	string	结款对方
        appOrderMoney_settlestatus = "";//	string	状态（1全部、2执行中、3本方请求终止、4对方请求终止）
        appOrderMoney_executeStartTime = "";//datetime		开始时间
        appOrderMoney_executeEndTime = "";//datetime		结束时间
        appSettle_statusRegist = "";//string	状态（0全部、1未申请登记、2已申请登记）
        FRGMENT_TYPE = 0;//string	状态（0全部、1未申请登记、2已申请登记）
        SEARCH_sendgoods_orderId = "";//string	订单号
        SEARCH_sendgoods_goodsName = "";//int	商品名
        editUser_password = "";//String	密码
        editUser_password1 = "";//String	确认密码
//        appSettle_settleCode = "";//String	结款单号
//        appSettle_oppositeName = "";//String	结款对方
        appOrderMoney_buyersName = "";//string	付款方名称(对方付款时采用)\
        SEARCH_sendgoods_OrdertitleCode = "";
        SEARCH_sendgoods_OrderGoodName = "";
    }

    /**
     * @param timestmp
     * @return
     */
    public static String stmpToDate(String timestmp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long timestmp_long = Long.parseLong(timestmp);
            String time_string = format.format(timestmp_long);
            return time_string;
        } catch (Exception e) {
            return "";
        }

    }

    //获取今天日期
    public static String getToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    //获取当前时间
    public static String getNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public static String stmpToDate(long timestmp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time_st_string = format.format(timestmp);
        return time_st_string;
    }

    public static String stmpToTime(long timestmp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time_st_string = format.format(timestmp);
        return time_st_string;
    }

    //判断两个日期的大小，第一个日期小于第二个日期返回true，否则返回false，传入的格式必须是yyyy-MM-dd
    public static boolean judgeDate(String date1, String date2) {
        if (TextUtils.isEmpty(date1) || TextUtils.isEmpty(date2)) {
            return true;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);
            Long diff = d1.getTime() - d2.getTime();
            return diff <= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("TIME", "judgeDate: " + e.getMessage());
            return false;
        }

    }


    /**
     * 协议管理规则字典
     */
    public static String GetPayType(int i) {
        String title = null;
        switch (i) {
            case 0:
                title = "每月";
                break;
            case 1:
                title = "每季";
                break;
            case 2:
                title = "6个月";
                break;
            case 3:
                title = "每年";
                break;
            case 4:
                title = "其他";
                break;
        }
        return title;
    }

    public static String GetSendsGoodsType(int i) {
        String title = null;
        switch (i) {
            case 0:
                title = "自取";
                break;
            case 1:
                title = "免费配送";
                break;
            case 2:
                title = "有偿配送（1%）";
                break;

        }
        return title;
    }

    public static String GetFlowType(int i) {
        String title = null;
        switch (i) {
            case 0:
                title = "行运";
                break;
            case 1:
                title = "空运";
                break;

        }
        return title;
    }

    public static String GetWorkFlowType(int i) {
        String title = null;
        switch (i) {
            case 1:
                title = "货款两清";
                break;
            case 2:
                title = "先货后款";
                break;
            case 3:
                title = "先货后款已交货";
                break;
            case 4:
                title = "先款后货";
                break;
            case 5:
                title = "先款后货已交款";
                break;

        }
        return title;
    }

    public static String getUUID() {
        int math_num = (int) (Math.random() * 100000);
        String str = String.format("%5d", math_num).replace(" ", "0");
        return str;
    }

    private static long currentTime = -1;


    public static void getTime(Context context) {
        String timeUUID = getTimeUUID();
        if (timeUUID.equals("")) {
            ToastUtils.showToast(context, context.getString(R.string.time_out));
            return;
        }
    }

    //根据时间限时uuid
    public static String getTimeUUID() {
        if (currentTime != -1) {
            if (System.currentTimeMillis() <= currentTime + 2000) {
                return "";
            } else {
                currentTime = System.currentTimeMillis();
                return getUUID();
            }
        } else {
            currentTime = System.currentTimeMillis();
            return getUUID();
        }

    }


    /**
     * 保留两位小数，并且以数字形式显示
     */
    public static String getNum(double d) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(d);
    }


}
