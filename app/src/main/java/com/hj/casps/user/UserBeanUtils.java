package com.hj.casps.user;

import android.app.Activity;

import com.hj.casps.app.HejiaApp;
import com.hj.casps.cooperate.DaoSession;

import java.util.List;

//import com.hj.casps.entity.appordergoods.DaoSession;

//import com.hj.casps.entity.appordergoods.DaoSession;

/**
 * Created by 鑫 Administrator on 2017/5/5.
 */

public class UserBeanUtils {
    private static UserBeanUtils instance;
    UserBeanDao userBeanDao;
    private UserBean currentUserBean;

    private UserBeanUtils(Activity context) {
        DaoSession daoSession = ((HejiaApp) context.getApplication()).getDaoSession();
        this.userBeanDao = daoSession.getUserBeanDao();
    }

    public static UserBeanUtils getInstance(Activity context) {
        if (instance == null) {
            instance = new UserBeanUtils(context);
        }
        return instance;
    }

    /**
     * 退出登录
     * 将当前用户的token设为无效状态
     */
    public void loginOut() {
        UserBean _currentUserBean = getCurrentUser();
        if (_currentUserBean != null) {
            _currentUserBean.setIsActive(false);
            _currentUserBean.setTokenIsActive(false);
            userBeanDao.update(_currentUserBean);
        }
    }

    /**
     * 按用户名查找用户
     *
     * @param name
     * @return
     */
    public UserBean getUserBean(String name) {
        UserBean userBean = userBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Sys_account.eq(name)).unique();
        return userBean;
    }

    /**
     * 当前登录的用户
     *
     * @return
     */
    public UserBean getCurrentUser() {
        if (currentUserBean == null) {
            UserBean userBean = userBeanDao.queryBuilder()
                    .where(UserBeanDao.Properties.TokenIsActive.eq(true))
                    .build().unique();
            currentUserBean = userBean;
        }
        return currentUserBean;
    }


    /**
     * 应该只有登录页面才会调用此方法
     *
     * @param bean
     */
    public void setCurrentUserBean(UserBean bean) {
        this.currentUserBean = bean;
        this.currentUserBean.setIsActive(true);
        this.currentUserBean.setTokenIsActive(true);
        String userId = getUserId (bean.getSys_user());
        if (userId.equals("")) {
            long id = userBeanDao.insert(bean);
            this.currentUserBean.setId(id);
        } else {
            userBeanDao.update(bean);
        }
//        long id = userBeanDao.insert(bean);
//        this.currentUserBean.setId(id);


    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<UserBean> getList() {

        List<UserBean> userList = userBeanDao.queryBuilder()
                .build().list();
        return userList;
    }
//    public UserBean getUserForId(String s){
//        UserBean userBean=userBeanDao.queryBuilder().where(UserBeanDao.Properties.Sys_user.eq(s)).build().unique();
//        return  userBean;
//
//    }
    public String getUserId(String s){
        UserBean unique = userBeanDao.queryBuilder().where(UserBeanDao.Properties.Sys_user.eq(s)).build().unique();
        if(unique!=null){
            return s;
        }
        return "";

    }
    public void updateToken(UserBean ub){
        userBeanDao.update(ub);
    }


}
