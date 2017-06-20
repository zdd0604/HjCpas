package com.hj.casps.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.http.LoginBean;
import com.hj.casps.user.ActivityLogin;
import com.hj.casps.user.LoginJsonCallBack;
import com.hj.casps.user.UserBean;
import com.hj.casps.user.UserBeanUtils;
import com.lzy.okgo.OkGo;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/20.
 */

public class LogoutUtils {

    public static boolean handle(Activity context, int returnCode) {
        if (returnCode == 1101) {
            ToastUtils.showToast(context, "被别的设备登录了");
            exitUser(context);
            return true;
        } else if (returnCode == 1102) {
            ToastUtils.showToast(context, "令牌超时或者退出登录失效了");
            exitUser(context);
            return true;
        } else if (returnCode == 2101) {
            ToastUtils.showToast(context, "没当前功能的授权");
            return true;
        } else if (returnCode == 3101) {
            ToastUtils.showToast(context, "重复提交");
            return true;
        }

        return false;
    }

    public static boolean handle(Fragment fragment, int returnCode) {
        FragmentActivity context = fragment.getActivity();
        if (returnCode == 1101) {
            ToastUtils.showToast(context, "被别的设备登录了");
            exitUser(context);
            return true;
        } else if (returnCode == 1102) {
            ToastUtils.showToast(context, "令牌超时或者退出登录失效了");
            exitUser(context);
            return true;
        } else if (returnCode == 2101) {
            ToastUtils.showToast(context, "没当前功能的授权");
            return true;
        } else if (returnCode == 3101) {
            ToastUtils.showToast(context, "重复提交");
            return true;
        }
        return false;
    }

    private static void exitUser(Activity context) {
        UserBean currentUser = UserBeanUtils.getInstance(context).getCurrentUser();
        currentUser.setTokenIsActive(false);
        UserBeanUtils.getInstance(context).updateToken(currentUser);
        httpExitApp(context);
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
}
