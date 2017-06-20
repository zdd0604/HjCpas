package com.hj.casps.entity.appUser;

import android.app.Activity;

import com.hj.casps.app.HejiaApp;
import com.hj.casps.cooperate.DaoSession;

import java.util.List;

/**
 * Created by Admin on 2017/5/12.
 */

public class AppUserUtils {
    private static AppUserUtils instance;
    public QueryUserEntityDao queryUserEntityDao;

    public AppUserUtils(Activity context) {
        DaoSession daoSession = ((HejiaApp) context.getApplication()).getDaoSession();
        queryUserEntityDao = daoSession.getQueryUserEntityDao();
    }

    public static AppUserUtils getInstance(Activity context) {
        if (instance == null) {
            instance = new AppUserUtils(context);
        }
        return instance;
    }

    /**
     * 查询所有
     */
    public List<QueryUserEntity> queryInfo() {
        return queryUserEntityDao.queryBuilder().build().list();
    }

    /**
     * 插入数据库
     *
     * @param qEntity
     */
    public void insertInfo(QueryUserEntity qEntity) {
        queryUserEntityDao.insert(qEntity);
    }

    /**
     * 删除数据
     */
    public void deleteAll() {
        queryUserEntityDao.deleteAll();
    }
}
