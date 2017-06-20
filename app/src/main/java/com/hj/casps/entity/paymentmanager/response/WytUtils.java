package com.hj.casps.entity.paymentmanager.response;

import android.app.Activity;

import com.hj.casps.app.HejiaApp;
import com.hj.casps.cooperate.DaoSession;
import com.hj.casps.entity.appordermoney.MmbBankAccountEntity;
import com.hj.casps.entity.appordermoney.MmbBankAccountEntityDao;
import com.hj.casps.entity.goodsmanager.response.DataListBean;
import com.hj.casps.entity.goodsmanager.response.DataListBeanDao;
import com.hj.casps.entity.goodsmanager.response.GoodInfoEntity;
import com.hj.casps.entity.goodsmanager.response.GoodInfoEntityDao;
import com.hj.casps.quotes.wyt.NewQtListEntity;
import com.hj.casps.quotes.wyt.NewQtListEntityDao;
import com.hj.casps.quotes.wyt.QtListEntity;
import com.hj.casps.quotes.wyt.QtListEntityDao;
import com.hj.casps.quotes.wyt.ResQuoteDetailBean;
import com.hj.casps.quotes.wyt.ResQuoteDetailBeanDao;

import java.util.List;

/**
 * 收付款  报价检索（非报价） 商品 操作数据库的工具
 * Created by Administrator on 2017/5/31.
 */

public class WytUtils {
    private DaoSession daoSession;
    private static WytUtils instance;
    //操作付款的dao
    private final ResponseQueryPayBeanDao queryPayBeanDao;
    //收款
    private final ResQueryGetMoneyEntityDao getMoneyEntityDao;
    //银行账号
    private final MmbBankAccountEntityDao accountEntityDao;
    //退款
    private final RefundMoneyOfflineBeanDao refundMoneyOfflineBeanDao;
    //收退款
    private final ResqueryGetRefundMoneyEntityDao getRefundMoneyEntityDao;
    //报价检索
    private final QtListEntityDao qtListEntityDao;
    private final NewQtListEntityDao newQtListEntityDao;
    //订单详情
    private final ResQuoteDetailBeanDao resQuoteDetailBeanDao;
    //商品列表
    private final DataListBeanDao dataListBeanDao;
    private final GoodInfoEntityDao goodInfoEntityDao;

    public WytUtils(Activity context) {
        daoSession = ((HejiaApp) context.getApplication()).getDaoSession();
        queryPayBeanDao = daoSession.getResponseQueryPayBeanDao();
        getMoneyEntityDao = daoSession.getResQueryGetMoneyEntityDao();
        accountEntityDao = daoSession.getMmbBankAccountEntityDao();
        refundMoneyOfflineBeanDao = daoSession.getRefundMoneyOfflineBeanDao();
        getRefundMoneyEntityDao = daoSession.getResqueryGetRefundMoneyEntityDao();
        qtListEntityDao = daoSession.getQtListEntityDao();
        newQtListEntityDao = daoSession.getNewQtListEntityDao();
        resQuoteDetailBeanDao = daoSession.getResQuoteDetailBeanDao();
        dataListBeanDao = daoSession.getDataListBeanDao();
        goodInfoEntityDao = daoSession.getGoodInfoEntityDao();
    }

    public static WytUtils getInstance(Activity context) {
        if (instance == null) {
            instance = new WytUtils(context);
        }
        return instance;
    }

    /**
     * 插入付款数据列表
     *
     * @param entity
     */
    public void insertQueryPayEntityInfo(ResponseQueryPayBean entity) {
        queryPayBeanDao.insert(entity);
    }

    /**
     * 查询付款数据列表
     *
     * @return
     */
    public List<ResponseQueryPayBean> QuerytQueryPayEntityInfo() {
        List<ResponseQueryPayBean> list = queryPayBeanDao.queryBuilder().build().list();
        return list;
    }

    /**
     * 删除所有付款数据
     */
    public void DeleteAllQueryPayEntityInfo() {
        queryPayBeanDao.deleteAll();
    }


    /**
     * 插入收款数据
     *
     * @param entity
     */
    public void insertQueryGetMoneyInfo(ResQueryGetMoneyEntity entity) {
        getMoneyEntityDao.insert(entity);
    }

    /**
     * 查询收款数据列表
     *
     * @return
     */
    public List<ResQueryGetMoneyEntity> QuerytQueryGetMoneyInfo() {
        List<ResQueryGetMoneyEntity> list = getMoneyEntityDao.queryBuilder().build().list();
        return list;
    }

    /**
     * 删除所有收款数据
     */
    public void DeleteAllQueryGetMoneyInfo() {
        getMoneyEntityDao.deleteAll();
    }


    /**
     * 插入银行账号数据
     *
     * @param entity
     */
    public void insertBankAccountInfo(MmbBankAccountEntity entity) {
        accountEntityDao.insert(entity);
    }

    /**
     * 查询银行账号数据列表
     *
     * @return
     */
    public List<MmbBankAccountEntity> QuerytBankAccountInfo() {
        List<MmbBankAccountEntity> list = accountEntityDao.queryBuilder().build().list();
        return list;
    }

    /**
     * 删除所有银行账号数据
     */
    public void DeleteAllBankAccountInfo() {
        accountEntityDao.deleteAll();
    }










    /**
     * 插入退款数据
     *
     * @param entity
     */
    public void insertRefundMoneyInfo(RefundMoneyOfflineBean entity) {
        refundMoneyOfflineBeanDao.insert(entity);
    }

    /**
     * 查询退款数据列表
     *
     * @return
     */
    public List<RefundMoneyOfflineBean> QuerytRefundMoneyInfo() {
        List<RefundMoneyOfflineBean> list = refundMoneyOfflineBeanDao.queryBuilder().build().list();
        return list;
    }

    /**
     * 删除所有退款数据
     */
    public void DeleteAllRefundMoneyInfo() {
        refundMoneyOfflineBeanDao.deleteAll();
    }



    // 收退款操作
    /**
     * 插入收退款数据
     *
     * @param entity
     */
    public void insertgetRefundMoneyInfo(ResqueryGetRefundMoneyEntity entity) {
        getRefundMoneyEntityDao.insert(entity);
    }

    /**
     * 查询收退款数据列表
     *
     * @return
     */
    public List<ResqueryGetRefundMoneyEntity> QuerytgetRefundMoneyInfo() {
        List<ResqueryGetRefundMoneyEntity> list = getRefundMoneyEntityDao.queryBuilder().build().list();
        return list;
    }

    /**
     * 删除所有收退款数据
     */
    public void DeleteAllgetRefundMoneyInfo() {
        getRefundMoneyEntityDao.deleteAll();
    }



    //报价检索的

    //  插入报价检索
    public void insertqtListEntityInfo(QtListEntity entity) {
        qtListEntityDao.insert(entity);
    }

    /**
     * 查询报价检索列表
     *
     * @return
     */
    public List<QtListEntity> QuerytqtListEntityInfo() {
        List<QtListEntity> list = qtListEntityDao.queryBuilder().build().list();
        return list;
    }

    /**
     * 删除所有报价检索数据
     */
    public void DeleteAllqtListEntityInfo() {
        qtListEntityDao.deleteAll();
    }




    //  插入销售报价检索
    public void insertnewQtListInfo(NewQtListEntity entity) {
        newQtListEntityDao.insert(entity);
    }

    /**
     * 查询销售报价检索列表
     *
     * @return
     */
    public List<NewQtListEntity> QuerytnewQtListInfo() {
        List<NewQtListEntity> list = newQtListEntityDao.queryBuilder().build().list();
        return list;
    }

    /**
     * 删除所有报价检索数据
     */
    public void DeleteAllnewQtListInfo() {
        newQtListEntityDao.deleteAll();
    }




    //  插入报价检索详情
    public void insertResQuoteDetailInfo(ResQuoteDetailBean entity) {
        resQuoteDetailBeanDao.insert(entity);
    }

    //  查询销售报价检索详情
    public ResQuoteDetailBean QuerytResQuoteDetailInfo(String quoteId) {
        ResQuoteDetailBean bean = resQuoteDetailBeanDao.queryBuilder().where(ResQuoteDetailBeanDao.Properties.QuoteId.eq(quoteId)).build().unique();
        return bean;
    }
        //删除报价检索详情
    public void deleteResQuoteDetailInfo( ResQuoteDetailBean bean){
        resQuoteDetailBeanDao.delete(bean);

    }
    //  更改报价检索详情
    public void updateResQuoteDetailInfo(ResQuoteDetailBean bean ){
        resQuoteDetailBeanDao.update(bean);
    }
    //删除所有
    public void DeleteAllResQuoteDetailInfo(){
        resQuoteDetailBeanDao.deleteAll();

    }


    //商品列表
    public void insertDataListBeanInfo(DataListBean entity) {
        dataListBeanDao.insert(entity);
    }

    //  查找所对应categoryId的数据集合
    public  List<DataListBean> QuerytDataListBeanInfo(String categoryId) {
        List<DataListBean >bean = dataListBeanDao.queryBuilder().where(DataListBeanDao.Properties.CategoryId.eq(categoryId)).build().list();
        return bean;
    }

    // 商品列表删除
    public void deleteDataListBeanInfo(DataListBean  entity){
        dataListBeanDao.delete(entity);
    }
    // 商品所有删除
    public void DeleteAllDataListBeanInfo(){
        dataListBeanDao.deleteAll();
    }












    //  插入商品详情
    public void insertGoodInfoEntityInfo(GoodInfoEntity entity) {
        goodInfoEntityDao.insert(entity);
    }

    //  查询商品详情
    public GoodInfoEntity QuerytGoodInfoEntityInfo(String goodsId) {
        GoodInfoEntity bean = goodInfoEntityDao.queryBuilder().where(GoodInfoEntityDao.Properties.GoodsId.eq(goodsId)).build().unique();
        return bean;
    }
    //删除商品详情
    public void deleteGoodInfoEntityInfo( GoodInfoEntity bean){
        goodInfoEntityDao.delete(bean);

    }
    //  更改报商品详情
    public void updateGoodInfoEntityInfo(GoodInfoEntity bean ){
        goodInfoEntityDao.update(bean);
    }
    //删除所有商品详情
    public void DeleteAllGoodInfoEntityInfo(  ){
        goodInfoEntityDao.deleteAll();

    }


















}
