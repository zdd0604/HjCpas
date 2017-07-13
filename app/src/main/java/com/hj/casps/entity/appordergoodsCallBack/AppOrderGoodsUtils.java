package com.hj.casps.entity.appordergoodsCallBack;

import android.app.Activity;

import com.hj.casps.app.HejiaApp;
import com.hj.casps.cooperate.DaoSession;
import com.hj.casps.entity.appordergoods.QueryGetGoodsBean;
import com.hj.casps.entity.appordergoods.QueryGetGoodsBeanDao;
import com.hj.casps.entity.appordergoods.QueryGetReturnGoodsEntity;
import com.hj.casps.entity.appordergoods.QueryGetReturnGoodsEntityDao;
import com.hj.casps.entity.appordergoods.QueryReturnGoodsEntity;
import com.hj.casps.entity.appordergoods.QueryReturnGoodsEntityDao;
import com.hj.casps.entity.appordergoods.QuerySendGoodsEntity;
import com.hj.casps.entity.appordergoods.QuerySendGoodsEntityDao;
import com.hj.casps.entity.appordergoods.WarehouseEntity;
import com.hj.casps.entity.appordergoods.WarehouseEntityDao;
import com.hj.casps.entity.appsettle.CheckWaitBillsEntity;
import com.hj.casps.entity.appsettle.CheckWaitBillsEntityDao;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleEntity;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleEntityDao;
import com.hj.casps.entity.appsettle.QueryPendingSttleGain;
import com.hj.casps.entity.appsettle.QueryPendingSttleGainDao;
import com.hj.casps.entity.appsettle.QuerySttleManageGain;
import com.hj.casps.entity.appsettle.QuerySttleManageGainDao;
import com.hj.casps.entity.appsettle.QuerySttleRegistGain;
import com.hj.casps.entity.appsettle.QuerySttleRegistGainDao;

import java.util.List;

/**
 * Created by Admin on 2017/5/11.
 */

public class AppOrderGoodsUtils {
    private DaoSession daoSession;
    private static AppOrderGoodsUtils instance;
    public QuerySendGoodsEntityDao querySendGoodsEntityDao;
    public QueryReturnGoodsEntityDao queryReturnGoodsEntityDao;
    public QueryGetGoodsBeanDao queryGetGoodsBeanDao;
    public QueryMyPendingSttleEntityDao queryMyPendingSttleEntityDao;
    public QueryGetReturnGoodsEntityDao queryGetReturnGoodsEntityDao;
    public WarehouseEntityDao WarehouseEntityDao;
    //登记资源列表
    public QuerySttleRegistGainDao querySttleRegistGain;
    // 获取待审批结款单列表 接口返回参数
    public QueryPendingSttleGainDao queryPendingSttleGainDao;
    //执行中借款单
    public QuerySttleManageGainDao querySttleManageGainDao;
    //结款单详情缓存
    public CheckWaitBillsEntityDao checkWaitBillsEntityDao;

    public AppOrderGoodsUtils(Activity context) {
        daoSession = ((HejiaApp) context.getApplication()).getDaoSession();
        querySendGoodsEntityDao = daoSession.getQuerySendGoodsEntityDao();
        queryReturnGoodsEntityDao = daoSession.getQueryReturnGoodsEntityDao();
        queryGetGoodsBeanDao = daoSession.getQueryGetGoodsBeanDao();
        queryMyPendingSttleEntityDao = daoSession.getQueryMyPendingSttleEntityDao();
        queryGetReturnGoodsEntityDao = daoSession.getQueryGetReturnGoodsEntityDao();
        queryPendingSttleGainDao = daoSession.getQueryPendingSttleGainDao();
        WarehouseEntityDao = daoSession.getWarehouseEntityDao();
        querySttleRegistGain = daoSession.getQuerySttleRegistGainDao();
        querySttleManageGainDao = daoSession.getQuerySttleManageGainDao();
        checkWaitBillsEntityDao = daoSession.getCheckWaitBillsEntityDao();
    }

    public static AppOrderGoodsUtils getInstance(Activity context) {
        if (instance == null) {
            instance = new AppOrderGoodsUtils(context);
        }
        return instance;
    }

    /**
     * 查询所有
     */
    public List<WarehouseEntity> queryWarehouseEntityDaoInfo() {
        return WarehouseEntityDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     */
    public void insertWarehouseEntityDaoInfo(WarehouseEntity warehouseEntity) {
        WarehouseEntityDao.insert(warehouseEntity);
    }

    /**
     * 删除数据
     */
    public void deleteWarehouseEntityDaoAll() {
        WarehouseEntityDao.deleteAll();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertSendGoodsInfo(QuerySendGoodsEntity qEntity) {
        querySendGoodsEntityDao.insert(qEntity);
    }

    /**
     * 查询所有
     */
    public List<QuerySendGoodsEntity> querySendGoodsInfo() {
        return querySendGoodsEntityDao.queryBuilder().build().list();
    }

    /**
     * 删除数据
     */
    public void deleteSendGoodsAll() {
        querySendGoodsEntityDao.deleteAll();
    }


    /**
     * 退货列表
     * 插入数据
     *
     * @param qEntity
     */
    public void insertReturnGoodsInfo(QueryReturnGoodsEntity qEntity) {
        queryReturnGoodsEntityDao.insert(qEntity);
    }

    /**
     * 退货列表
     * 查询所有
     */
    public List<QueryReturnGoodsEntity> queryReturnGoodsInfo() {
        return queryReturnGoodsEntityDao.queryBuilder().build().list();
    }

    /**
     * 退货列表
     * 删除数据
     */
    public void deleteReturnGoodsAll() {
        queryReturnGoodsEntityDao.deleteAll();
    }

    /**
     * 手退货
     * 查询所有
     */
    public List<QueryGetGoodsBean> queryHarvestExpressInfo() {
        return queryGetGoodsBeanDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertHarvestExpressInfo(QueryGetGoodsBean qEntity) {
        queryGetGoodsBeanDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteHarvestExpressAll() {
        queryGetGoodsBeanDao.deleteAll();
    }

    /**
     * 手退货
     * 插入数据
     *
     * @param qEntity
     */
    public void insertHarvestGoodsInfo(QueryGetReturnGoodsEntity qEntity) {
        queryGetReturnGoodsEntityDao.insert(qEntity);
    }

    /**
     * 手退货
     * 查询所有
     */
    public List<QueryGetReturnGoodsEntity> queryHarvestGoodsInfo() {
        return queryGetReturnGoodsEntityDao.queryBuilder().build().list();
    }

    /**
     * 手退货
     * 删除数据
     */
    public void deleteHarvestGoodsAll() {
        queryGetReturnGoodsEntityDao.deleteAll();
    }

    /**
     * 获取我的提交的待对方审批结款单列表
     * 插入数据
     *
     * @param qEntity
     */
    public void insertMyPendingSttleInfo(QueryMyPendingSttleEntity qEntity) {
        queryMyPendingSttleEntityDao.insert(qEntity);
    }

    /**
     * 获取我的提交的待对方审批结款单列表
     * 查询所有
     */
    public List<QueryMyPendingSttleEntity> queryMyPendingSttleInfo() {
        return queryMyPendingSttleEntityDao.queryBuilder().build().list();
    }

    /**
     * 获取我的提交的待对方审批结款单列表
     * 删除数据
     */
    public void deleteMyPendingSttleAll() {
        queryMyPendingSttleEntityDao.deleteAll();
    }

    /**
     * 执行中的结款单
     * 插入数据
     *
     * @param qEntity
     */
    public void insertQueryPendingSttleGainInfo(QueryPendingSttleGain qEntity) {
        queryPendingSttleGainDao.insert(qEntity);
    }

    /**
     * 执行中的结款单
     * 查询所有
     */
    public List<QueryPendingSttleGain> queryQueryPendingSttleGainInfo() {
        return queryPendingSttleGainDao.queryBuilder().build().list();
    }

    /**
     * 执行中的结款单
     * 删除数据
     */
    public void deleteQueryPendingSttleGainAll() {
        queryPendingSttleGainDao.deleteAll();
    }

    /**
     * 登记资源列表
     * 插入数据
     *
     * @param qEntity
     */
    public void insertQuerySttleRegistGainInfo(QuerySttleRegistGain qEntity) {
        querySttleRegistGain.insert(qEntity);
    }

    /**
     * 登记资源列表
     * 查询所有
     */
    public List<QuerySttleRegistGain> queryQuerySttleRegistGainInfo(int Register_id) {
        return querySttleRegistGain.queryBuilder().
                where(QuerySttleRegistGainDao.Properties.Register_id.eq(Register_id)).list();
    }

    /**
     * 登记资源列表
     * 删除数据
     */
    public void deleteQuerySttleRegist(int Register_id) {
        List<QuerySttleRegistGain> dList = querySttleRegistGain.queryBuilder().
                where(QuerySttleRegistGainDao.Properties.Register_id.eq(Register_id)).list();

        if (dList.size() <= 0)
            return;

        for (int i = 0; i < dList.size(); i++) {
            querySttleRegistGain.delete(dList.get(i));
        }
    }

    /**
     * 登记资源列表
     * 删除所有数据
     */
    public void deleteAllQuerySttleRegist() {
        querySttleRegistGain.deleteAll();
    }

    /**
     * 结款单详情界面
     * 插入数据
     * @param checkWaitBillsEntity
     */
    public void insertCheckWaitBillsEntity(CheckWaitBillsEntity checkWaitBillsEntity) {
        checkWaitBillsEntityDao.insert(checkWaitBillsEntity);
    }

    /**
     * 结款单详情界面
     * 查询所有
     */
    public List<CheckWaitBillsEntity> queryCheckWaitBillsEntity(String Register_id) {
        return checkWaitBillsEntityDao.queryBuilder().
                where(CheckWaitBillsEntityDao.Properties.AppSettle.eq(Register_id)).list();
    }

    /**
     * 登记资源列表
     * 删除数据
     */
    public void deleteCheckWaitBillsEntity(String Register_id) {
        List<CheckWaitBillsEntity> dList = checkWaitBillsEntityDao.queryBuilder().
                where(CheckWaitBillsEntityDao.Properties.AppSettle.eq(Register_id)).list();

        if (dList.size() <= 0)
            return;

        for (int i = 0; i < dList.size(); i++) {
            checkWaitBillsEntityDao.delete(dList.get(i));
        }
    }

    /**
     * 结款单详情界面
     * 删除所有数据
     */
    public void deleteAllCheckWaitBillsEntity() {
        checkWaitBillsEntityDao.deleteAll();
    }



    /**
     * 执行中里欸包
     * 插入数据
     *
     * @param qEntity
     */
    public void insertQuerySttleManageGainInfo(QuerySttleManageGain qEntity) {
        querySttleManageGainDao.insert(qEntity);
    }

    /**
     * 执行中里欸包
     * 查询所有
     */
    public List<QuerySttleManageGain> queryQuerySttleManageGainInfo(int Register_id) {
        return querySttleManageGainDao.queryBuilder().
                where(QuerySttleManageGainDao.Properties.Register_id.eq(Register_id)).list();
    }

    /**
     *执行中里欸包
     * 删除数据
     */
    public void deleteQuerySttleManageGain(int Register_id) {
        List<QuerySttleManageGain> dList = querySttleManageGainDao.queryBuilder().
                where(QuerySttleManageGainDao.Properties.Register_id.eq(Register_id)).list();

        if (dList.size() <= 0)
            return;

        for (int i = 0; i < dList.size(); i++) {
            querySttleManageGainDao.delete(dList.get(i));
        }
    }
    /**
     *执行中里欸包
     * 删除所有数据
     */
    public void deleteAllQuerySttleManageGain() {
        querySttleManageGainDao.deleteAll();
    }

}
