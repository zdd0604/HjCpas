package com.hj.casps.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.hj.casps.app.HejiaApp;
import com.hj.casps.common.Constant;
import com.hj.casps.cooperate.DaoSession;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.http.LoginBean;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.user.ActivityLogin;
import com.hj.casps.user.LoginJsonCallBack;
import com.hj.casps.user.UserBean;
import com.hj.casps.user.UserBeanDao;
import com.hj.casps.user.UserBeanUtils;
import com.lzy.okgo.OkGo;
import okhttp3.Call;
import okhttp3.Response;

/**退出登录的工具类
 * Created by Administrator on 2017/6/20.
 */

public class LogoutUtils {

    private static MyDialog dialog;

    public static void exitUser(Activity context) {
        Constant.public_code=false;
        showDialog(context);
    }
    public static void exitUser(Fragment fragment) {
        Constant.public_code=false;
        showDialog(fragment.getActivity());
    }
    private static void showDialog(final Activity context) {
        dialog = new MyDialog(context);
        dialog.setMessage("此账号被别人登录或登录超时");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialog.dismiss();
                UserBean currentUser = UserBeanUtils.getInstance(context).getCurrentUser();
                currentUser.setTokenIsActive(false);
                UserBeanUtils.getInstance(context).updateToken(currentUser);
                httpExitApp(context);
            }
        });
        dialog.show();
    }

    //用户退出
    private static void httpExitApp(final Activity context) {
        String url = Constant.LoginOutUrl;
        PublicArg p = Constant.publicArg;
        String pa = "{\"sys_user\":\"" + p.getSys_user() + "\"" +
                ",\"sys_token \":\"" + p.getSys_token() + "\"}";
        OkGo.post(url).params("param", pa).
                execute(new LoginJsonCallBack<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean loginBean, Call call, Response response) {
                        super.onSuccess(loginBean, call, response);
                        if (loginBean != null) {
                            if (loginBean.getReturn_code() == 0) {
                                ToastUtils.showToast(context, "用户已退出");
                                context.startActivity(new Intent(context,ActivityLogin.class));
                                context.finish();
                            } else if (loginBean.getReturn_code() == 101) {
                                ToastUtils.showToast(context, loginBean.getReturn_message());
                            } else if (loginBean.getReturn_code() == 102) {
                                ToastUtils.showToast(context, loginBean.getReturn_message());
                            } else if (loginBean.getReturn_code() == 103) {
                                ToastUtils.showToast(context, loginBean.getReturn_message());
                            } else if (loginBean.getReturn_code() == 999) {
                                ToastUtils.showToast(context, loginBean.getReturn_message());
                            } else {
                                ToastUtils.showToast(context, "其他异常");
                                return;
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public static void jumpLogin(){
        Context context = HejiaApp.getContext();
        DaoSession daoSession = HejiaApp.daoSession;
        if(daoSession!=null){
            UserBeanDao userBeanDao = daoSession.getUserBeanDao();
            UserBean userBean = userBeanDao.queryBuilder()
                    .where(UserBeanDao.Properties.TokenIsActive.eq(true))
                    .build().unique();
            userBean.setTokenIsActive(false);
            userBeanDao.update(userBean);
            Intent intent = new Intent();
            intent.setAction("com.hj.casps.user.ActivityLogin");
            context.startActivity(intent);
        }
    }

}
