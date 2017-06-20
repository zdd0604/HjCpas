package com.hj.casps.entity.appordergoodsCallBack;

import android.app.Activity;

import com.hj.casps.app.HejiaApp;
import com.hj.casps.cooperate.DaoSession;
import com.hj.casps.entity.appordergoods.QueryGetGoodsBeanDao;

/**
 * Created by Admin on 2017/5/12.
 */

public class HarvestAppGoodUtils {
    private static HarvestAppGoodUtils instance;
    public QueryGetGoodsBeanDao queryGetGoodsBeanDao;

    public HarvestAppGoodUtils(Activity context) {
        DaoSession daoSession = ((HejiaApp) context.getApplication()).getDaoSession();
        queryGetGoodsBeanDao = daoSession.getQueryGetGoodsBeanDao();
    }

}
