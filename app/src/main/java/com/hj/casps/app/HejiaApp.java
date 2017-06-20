package com.hj.casps.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.hj.casps.BuildConfig;
import com.hj.casps.cooperate.DaoMaster;
import com.hj.casps.cooperate.DaoSession;
import com.hj.casps.entity.appordergoods.WarehouseEntityDao;
import com.hj.casps.user.ActivityLogin;
import com.hj.casps.user.UserBean;
import com.hj.casps.user.UserBeanDao;
import com.hj.casps.user.UserBeanUtils;
import com.hj.casps.util.ActivityUtils;
import com.lzy.okgo.OkGo;

import org.greenrobot.greendao.database.Database;

import java.util.List;
import java.util.logging.Level;

import cn.common.CommonAppUtils;
import cn.common.http2.HttpX;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by 鑫 Administrator on 2017/4/17.
 */

public class HejiaApp extends MultiDexApplication {
    private ApiList apilist;
    public  static  Context context;

    public ApiList getApiList() {
        if (apilist == null) {
            apilist = (ApiList) DataFilePersistenceUtils.readObj(Constants.ApiListFileName);
        }
        return apilist;
    }


    public void setApiList(ApiList apiList) {
        this.apilist = apiList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        HttpX.init(this);
        JPushInterface.init(this);
        initGreenDao();
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        CommonAppUtils.onCreate(this, BuildConfig.DEBUG);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                //设置token无效
                setTokenDisable();
                ex.printStackTrace();
                ActivityUtils.exit();
            }
        });

        try {
            //必须调用初始化
            OkGo.init(this);
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("CASPS", Level.INFO, true)
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS) //全局的写入超时时间
                    .setRetryCount(3);    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTokenDisable() {
        System.out.println("setTokenDisable");
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        UserBean userBean = userBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.TokenIsActive.eq(true))
                .build().unique();
        userBean.setTokenIsActive(false);
        userBeanDao.update(userBean);

    }

    private DaoSession daoSession;


    private void initGreenDao() {
        /*A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.*/
        boolean ENCRYPTED = false;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
//        Stetho.initializeWithDefaults(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public WarehouseEntityDao getWarehouseEntityDao() {
        return daoSession.getWarehouseEntityDao();
    }

    public static  Context getContext(){
        return  context;
    }
}
