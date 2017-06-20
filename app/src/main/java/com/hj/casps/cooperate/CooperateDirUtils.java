package com.hj.casps.cooperate;

import android.app.Activity;

import com.hj.casps.app.HejiaApp;
import com.hj.casps.protocolmanager.OrderRowBean;
import com.hj.casps.protocolmanager.OrderRowBeanDao;
import com.hj.casps.protocolmanager.ProtocolListBean;
import com.hj.casps.protocolmanager.ProtocolListBeanDao;
import com.hj.casps.quotes.QuoteModel;
import com.hj.casps.quotes.QuoteModelDao;

import java.util.List;

/**
 * Created by zy on 2017/6/7.
 * 数据库管理页面
 */

public class CooperateDirUtils {
    private static CooperateDirUtils instance;
    public CooperateModelDao cooperateModelDao;//关注会员目录的数据库
    public QuoteModelDao quoteModelDao;//报价管理列表数据库
    public CooperateContentsBeanDao cooperateContentsBeanDao;//关系管理会员目录的数据库
    public GroupManagerListBeanDao groupManagerListBeanDao;//群组管理的数据库
    public WhoCareListBeanDao whoCareListBeanDao;//供应商关注的数据库
    public ProtocolListBeanDao protocolListBeanDao;//协议管理的数据库
    public OrderRowBeanDao orderRowBeanDao;//订单管理的数据库

    public CooperateDirUtils(Activity context) {
        DaoSession daoSession = ((HejiaApp) context.getApplication()).getDaoSession();
        cooperateModelDao = daoSession.getCooperateModelDao();
        quoteModelDao = daoSession.getQuoteModelDao();
        cooperateContentsBeanDao = daoSession.getCooperateContentsBeanDao();
        groupManagerListBeanDao = daoSession.getGroupManagerListBeanDao();
        whoCareListBeanDao = daoSession.getWhoCareListBeanDao();
        protocolListBeanDao = daoSession.getProtocolListBeanDao();
        orderRowBeanDao = daoSession.getOrderRowBeanDao();
    }

    public static CooperateDirUtils getInstance(Activity context) {
        if (instance == null) {
            instance = new CooperateDirUtils(context);
        }
        return instance;
    }

    /**
     * 查询所有
     */
    public List<CooperateModel> queryInfo() {
        return cooperateModelDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertInfo(CooperateModel qEntity) {
        cooperateModelDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteAll() {
        cooperateModelDao.deleteAll();
    }

    /**
     * 查询所有
     */
    public List<QuoteModel> queryQuoteModelInfo() {
        return quoteModelDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertInfo(QuoteModel qEntity) {
        quoteModelDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteQuoteModelAll() {
        quoteModelDao.deleteAll();
    }

    /**
     * 查询所有
     */
    public List<CooperateContentsBean> queryCooperateContentsBeanInfo() {
        return cooperateContentsBeanDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertInfo(CooperateContentsBean qEntity) {
        cooperateContentsBeanDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteCooperateContentsBeanAll() {
        cooperateContentsBeanDao.deleteAll();
    }

    /**
     * 查询所有
     */
    public List<GroupManagerListBean> queryGroupManagerListBeanInfo() {
        return groupManagerListBeanDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertInfo(GroupManagerListBean qEntity) {
        groupManagerListBeanDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteGroupManagerListBeanAll() {
        groupManagerListBeanDao.deleteAll();
    }

    /**
     * 查询所有
     */
    public List<WhoCareListBean> queryWhoCareListBeanInfo() {
        return whoCareListBeanDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertInfo(WhoCareListBean qEntity) {
        whoCareListBeanDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteWhoCareListBeanAll() {
        whoCareListBeanDao.deleteAll();
    }

    /**
     * 查询所有
     */
    public List<ProtocolListBean> queryProtocolListBeanInfo() {
        return protocolListBeanDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertInfo(ProtocolListBean qEntity) {
        protocolListBeanDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteProtocolListBeanAll() {
        protocolListBeanDao.deleteAll();
    }

    /**
     * 查询所有
     */
    public List<OrderRowBean> queryOrderRowBeanDaoInfo() {
        return orderRowBeanDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertInfo(OrderRowBean qEntity) {
        orderRowBeanDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteOrderRowBeanAll() {
        orderRowBeanDao.deleteAll();
    }


}
