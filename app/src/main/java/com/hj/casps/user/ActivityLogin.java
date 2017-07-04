package com.hj.casps.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBase;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.appsecurity.LoginEntity;
import com.hj.casps.entity.appsecurity.ReLoginEntity;
import com.hj.casps.http.LoginBean;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.NetUtil;
import com.hj.casps.util.SubmitClickUtils;
import com.hj.casps.util.ToastUtils;
import com.hj.casps.widget.ListPopupWindow;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen on 2017/4/10.
 * 登录页面
 */

public class ActivityLogin extends ActivityBase {
    @BindView((R.id.login_toast))
    TextView toast;
    @BindView(R.id.login_inputname_Et)
    EditText user_name_Et;
    @BindView(R.id.login_inputps_Et)
    EditText password_Et;
    @BindView(R.id.login_true_Btn)
    FancyButton submit;
    @BindView(R.id.login_inputps_verfy_Tv)
    TextView verfyBv;
    @BindView(R.id.login_inputps_verfy_layout)
    RelativeLayout verfyLayout;
    @BindView(R.id.login_inputps_verfy_Et)
    EditText verfyEt;
    @BindView(R.id.login_inputname_iv)
    ImageView showNameSelectBt;
    private UserBean userBean;
    private ListPopupWindow listPopupWindow;
    private String et_password;//密码
    private String et_verfy;//

    private LoginBean loginBean;
    //判断是否需要验证码
    private Boolean isCheck = true;
    private String message;
    private Timer timer;
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        SubmitClickUtils.addView(submit, verfyBv, showNameSelectBt);
        user_name_Et.setOnFocusChangeListener(new NameEtFocusChangeListener());
    }


    /**
     * 错误提示事件
     *
     * @param msg
     */
    private void toastShow(String msg) {
        toast.setVisibility(View.VISIBLE);
        if (toast.getVisibility() == View.VISIBLE) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.setVisibility(View.GONE);
                }
            }, 1000);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!hasInternetConnected()) {
            ToastUtils.showToast(this, "网络连接失败");
            return;
        }
        UserBeanUtils instance = UserBeanUtils.getInstance(ActivityLogin.this);
        List<UserBean> userBeanList = instance.getList();
        for (UserBean u : userBeanList) {
            System.out.println("l=" + u);
        }
        if (userBeanList != null && userBeanList.size() > 0) {
            /*如果存在token有效的，则直接登录了*/
            for (int i = userBeanList.size() - 1; i > -1; i--) {
                UserBean userBean = userBeanList.get(i);
                if (userBean.getTokenIsActive() && userBean.getIsActive()) {
                    instance.setCurrentUserBean(userBean);
                    setPublicArg(userBean);
                    goNext();
                    return;
                }
            }
            /*不存在token有效的*/
            Collections.reverse(userBeanList);
            userBean = userBeanList.get(0);
            user_name_Et.setText(userBean.getSys_account());
            password_Et.setText(userBean.getSys_pwd());
            if (userBeanList.size() > 1) {
                verfyLayout.setVisibility(View.GONE);
                /*显示下拉*/
                showNameSelectBt.setVisibility(View.VISIBLE);
                showNameSelectBt.setTag(userBeanList);
            }
        } else {
            verfyLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 本地的格式判断
     *
     * @return
     */
    private boolean isCheckLogin() {
        if (!isCheckGetVerfy()) return false;
        if (userBean == null) {
            if (verfyEt.getText().toString().length() == 0) {
                toastSHORT(getString(R.string.verfy_password));
                return false;
            }
        }
        return true;
    }

    //验证用户名和密码
    private boolean isCheckGetVerfy() {
        if (user_name_Et.getText().toString().length() == 0) {
            toastSHORT(getString(R.string.error_user_name));
            return false;
        }
        if (password_Et.getText().toString().length() == 0) {
            toastSHORT(getString(R.string.error_password));
            return false;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * /InitMessageLogin 请求
     */
    private void httpInit() {
        if (!hasInternetConnected()) {
            return;
        }
        if (!NetUtil.ping()) {
            toastSHORT("访问服务器失败");
            return;
        }

        String sys_account = user_name_Et.getText().toString().trim();
        String sys_pwd = password_Et.getText().toString().trim();
        String url = Constant.InitMessageUrl;
        OkGo.post(url).params("param", "{\"sys_account\":\"" + sys_account + "\",\"sys_pwd\":\"" + sys_pwd + "\"}")
                .execute(new LoginJsonCallBack<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean initloginBean, Call call, Response response) {
                        //处理短信登录返回结果
                        if (initloginBean != null) {
                            verfyLogin(initloginBean);
                        } else if (initloginBean.getReturn_code() == 1101 || initloginBean.getReturn_code() == 2101) {
                            LogoutUtils.exitUser(ActivityLogin.this);
                        } else {
                            new IllegalStateException("httpInit方法异常，获取短信失败接口失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                    }
                });
    }

    //短信验证接口的状态码判断
    private void verfyLogin(LoginBean loginBean) {
        int return_code = loginBean.getReturn_code();
        if (return_code == 0) {
            this.loginBean = loginBean;
            countDown(verfyBv, 30, "倒计时", "", "获取验证码");
            toastSHORT("短信已发送");
        } else if (return_code == 110) {
            toastSHORT("用户未启用");
        } else if (return_code == 101) {
            toastSHORT("用户名不存在");
        } else if (return_code == 102) {
            toastSHORT("密码错误，无法获取验证码");
        } else if (return_code == 999) {
            toastSHORT("用户名或密码错误，获取验证码失败");
        } else if (return_code == 2101) {
            toastSHORT(getString(R.string.no_authorizations));
        } else if (return_code == 3101) {
            toastSHORT(getString(R.string.repeat_submit));
        } else {
            toastSHORT(loginBean.getReturn_message());
        }
    }

    //登录接口
    private void httpLogin() {
        waitDialogRectangle.show();
        final String username = user_name_Et.getText().toString().trim();
        final String password = password_Et.getText().toString().trim();
        final String userId = loginBean.getUserId();
        String et_verfy = verfyEt.getText().toString().trim();
        if (et_verfy.length() != 6) {
            toastSHORT("验证码长度不正确");
            waitDialogRectangle.dismiss();
            return;
        }
        LoginEntity loginEntity = new LoginEntity(userId, username, password, et_verfy);
        OkGo.post(Constant.LoginUrl)
                .params("param", mGson.toJson(loginEntity))
                .execute(new LoginJsonCallBack<LoginBean>() {
                             @Override
                             public void onSuccess(LoginBean httploginBean, Call call, Response response) {
                                 waitDialogRectangle.dismiss();
                                 if (httploginBean != null) {
                                     if (httploginBean.getReturn_code() == 0) {
                                         //判断数据库有没有该用户数据，有就直接登录
                                         httpGetContext(httploginBean, username, password);
                                     } else if (httploginBean.getReturn_code() == 110) {
                                         toastSHORT("用户未启用");
                                     } else if (httploginBean.getReturn_code() == 101) {
                                         toastSHORT("用户不存在");
                                     } else if (httploginBean.getReturn_code() == 102) {
                                         toastSHORT("密码错误");
                                     } else if (httploginBean.getReturn_code() == 103) {
                                         toastSHORT("短信码错误");
                                     } else if (httploginBean.getReturn_code() == 104) {
                                         toastSHORT("短信未发送");
                                     } else if (httploginBean.getReturn_code() == 999) {
                                         toastSHORT("未知错误");
                                     } else if (httploginBean.getReturn_code() == 2101) {
                                         toastSHORT(getString(R.string.no_authorizations));
                                     } else if (httploginBean.getReturn_code() == 3101) {
                                         toastSHORT(getString(R.string.repeat_submit));
                                     } else if (httploginBean.getReturn_code() == 1101 || httploginBean.getReturn_code() == 2101) {
                                         toastSHORT(httploginBean.getReturn_message());
                                         LogoutUtils.exitUser(ActivityLogin.this);
                                     } else {
                                         toastSHORT(httploginBean.getReturn_message());
                                     }
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 toastSHORT(e.getMessage());
                                 waitDialogRectangle.dismiss();
                             }
                         }
                );

    }


    /**
     * 二次登录接口
     */
    private void httpReLogin() {
        waitDialogRectangle.show();
        final UserBean reUserBean = UserBeanUtils.getInstance(ActivityLogin.this)
                .getUserBean(getEdVaule(user_name_Et));
        if (reUserBean == null) {
            httpLogin();
            return;
        }
        String reToken = reUserBean.getToken();
        String sys_user = reUserBean.getSys_user();
        String et_userName = getEdVaule(user_name_Et);
        String et_password = getEdVaule(password_Et);

        if ("".equals(reToken) || reToken == null) {
            httpLogin();
        } else {
            ReLoginEntity reLoginEntity = new ReLoginEntity(et_userName, et_password, sys_user, reToken);
            OkGo.post(Constant.ReLoginUrl)
                    .params("param", mGson.toJson(reLoginEntity))
                    .execute(new LoginJsonCallBack<LoginBean<Void>>() {
                        @Override
                        public void onSuccess(LoginBean loginBean, Call call, Response response) {
                            super.onSuccess(loginBean, call, response);
                            waitDialogRectangle.dismiss();
                            if (loginBean != null) {
                                if (loginBean.getReturn_code() == 0) {
                                    //设置新token
                                    reUserBean.setToken(loginBean.getToken());
                                    UserBeanUtils.getInstance(ActivityLogin.this).setCurrentUserBean(reUserBean);
                                    setPublicArg(reUserBean);
                                    goNext();
                                } else if (loginBean.getReturn_code() == 101) {
                                    toastSHORT("用户不存在");
                                    return;
                                } else if (loginBean.getReturn_code() == 102) {
                                    toastSHORT("密码错误");
                                    return;
                                } else if (loginBean.getReturn_code() == 105) {
                                    toastSHORT("用户未登陆过");
                                    return;
                                } else if (loginBean.getReturn_code() == 999) {
                                    toastSHORT("未知错误");
                                    return;
                                } else if (loginBean.getReturn_code() == 1101 || loginBean.getReturn_code() == 2101) {
                                    toastSHORT(loginBean.getReturn_message());
                                    LogoutUtils.exitUser(ActivityLogin.this);
                                } else {
                                    toastShow(loginBean.getReturn_message());
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

    //设置上传所需的公共参数
    private void setPublicArg(UserBean reUserBean) {
        Constant.publicArg = new PublicArg(
                reUserBean.getToken(),
                reUserBean.getSys_user(),
                reUserBean.getSys_account(),
                reUserBean.getSys_mmb(),
                "12345",
                "12345",
                reUserBean.getSys_mmbname(),
                reUserBean.getSys_username());

    }

    /**
     * /GetContext 请求
     * sys_user	string	用户id
     * sys_token String	token
     */

    private void httpGetContext(final LoginBean initloginBean, final String et_username, final String et_pwd) {
        final String sys_user = this.loginBean.getUserId();
        final String sys_token = initloginBean.getToken();
        OkGo.post(Constant.GetContextUrl)
                .params("param", "{\"sys_user\":\"" + sys_user + "\",\"sys_token\":\"" + sys_token + "\"}")
                .execute(new LoginCallBack<LoginContextBean<SubLoginBean>>() {

                    @Override
                    public void onSuccess(LoginContextBean<SubLoginBean> subLoginBeanLoginContextBean, Call call, Response response) {
                        super.onSuccess(subLoginBeanLoginContextBean, call, response);
                        waitDialogRectangle.dismiss();
                        SubLoginBean context = subLoginBeanLoginContextBean.Context;
                        UserBean userBean = new UserBean(null, et_username, et_pwd, sys_user, sys_token, context.getSys_mmb(),
                                context.getSys_username(), context.getSys_mmbname(), true, true);
                        UserBeanUtils.getInstance(ActivityLogin.this).setCurrentUserBean(userBean);
                        setPublicArg(userBean);
                        goNext();

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        waitDialogRectangle.dismiss();
                        toastSHORT(e.getMessage());
                        if (Constant.public_code) {
                            LogoutUtils.exitUser(ActivityLogin.this);
                        }
                    }
                });
    }

    private void goNext() {
        startActivity(new Intent(this, ActivityLoginAfter2.class));
    }

    /**
     * 多用户下拉时
     *
     * @param v
     */
    @OnClick(R.id.login_inputname_iv)
    void onShowNameSelectClick(View v) {
        if (v.getTag() != null) {
            if (listPopupWindow != null && listPopupWindow.isShowing()) {
                dismissPop();
                return;
            } else {
                final List<UserBean> userBeanList = (List<UserBean>) v.getTag();
                List<String> sysAccountList = new ArrayList<>();
                for (int i = 0; i < userBeanList.size(); i++) {
                    sysAccountList.add(userBeanList.get(i).getSys_account());
                }
                listPopupWindow = new ListPopupWindow(this);
                View listView = listPopupWindow.getContentView().findViewById(R.id.list);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listView.getLayoutParams();
                layoutParams.setMargins(20, 20, 20, 20);
                layoutParams.gravity = Gravity.CENTER;
                listView.setLayoutParams(layoutParams);
                listPopupWindow.setAdapter(new ArrayAdapter(this, R.layout.simple_item_layout, R.id.item_name, sysAccountList));
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listPopupWindow.dismiss();
                        userBean = userBeanList.get(position);
                        user_name_Et.setText(userBean.getSys_account());
                        password_Et.setText(userBean.getSys_pwd());
                    }
                });
                listPopupWindow.showAtLocation(user_name_Et, Gravity.CENTER, 0, 0);
            }
        }
    }

    /**
     * 获取验证码单击事件
     *
     * @param v
     */
    @OnClick(R.id.login_inputps_verfy_Tv)
    void onGetVerfyClick(View v) {
        if (isCheckGetVerfy()) {
            httpInit();
        }
    }

    /**
     * 登录提交事件
     *
     * @param v
     */
    @OnClick(R.id.login_true_Btn)
    void onLoadClick(View v) {
        switch (v.getId()) {
            case R.id.login_true_Btn:
                if (!hasInternetConnected()) {
                    toastSHORT("网络连接失败，请检查网络");
                    return;
                }
                String userName = user_name_Et.getText().toString().trim();
                et_verfy = (String) verfyEt.getTag();
                message = verfyEt.getText().toString().trim();
                String password = password_Et.getText().toString().trim();
                if (password.length() == 0 || userName.length() == 0) {
                    toastSHORT("用户名或密码为空");
                    return;
                }
                checkVerfyLayoutVisible(userName);

                if (isCheck) {
                    if (loginBean == null) {
                        toastSHORT("首次登陆请获取验证码");
                        return;
                    }
                }
                if (isCheckLogin()) {
                    if (this.userBean == null) {
                        httpLogin();
                    } else {
                        httpReLogin();
                    }
                }
        }
    }

    //判断本地数据库是否有该用户
    private void checkVerfyLayoutVisible(String userName) {
        UserBean _userBean = UserBeanUtils.getInstance(this).getUserBean(userName);
        verfyLayout.setVisibility(_userBean == null ? View.VISIBLE : View.GONE);
        if (_userBean != null) {
            isCheck = false;
        } else {
            isCheck = true;
        }
        userBean = _userBean;
    }

    private void dismissPop() {
        if (listPopupWindow != null && listPopupWindow.isShowing()) {
            listPopupWindow.dismiss();
        }
    }

    private class NameEtFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                /*失去了焦点 检查当前用户是否登录过，未登录显示获取验证码，登录不显示该块*/
                String userName = user_name_Et.getText().toString().trim();
                checkVerfyLayoutVisible(userName);
            } else {
                dismissPop();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginBean = null;
        isCheck = true;

        verfyBv.setText("获取验证码");
        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
        verfyBv.setClickable(true);
        dismissPop();
    }

    /**
     * 倒计时
     *
     * @param view
     * 显示的文本控件
     * @param sumTime
     * 总倒计时
     * @param leftContent
     * 时间前面的内容
     * @param rightContent
     * 时间后面的内容提示
     * @param content
     * 倒计时完成时的显示（刚开始的显示内容）
     * <p>
     * countDown(ceshi, 59, "还有", "秒", "获取验证码")   还有59秒
     */
    private int countTime;
    private TimerTask timerTask;

    //发送短信的倒计时方法
    public void countDown(final TextView view, final int sumTime, final String leftContent,
                          final String rightContent, final String content) {
        timer = new Timer();
        //设置不能点击，防止重复发送数据到主线程
        view.setClickable(false);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        String str = leftContent + (sumTime - countTime) + rightContent;
                        SpannableStringBuilder style = new SpannableStringBuilder(str);
                        //str代表要显示的全部字符串
                        style.setSpan(new ForegroundColorSpan(Color.RED), 3, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        view.setText(style);
                        if (countTime == sumTime) {
                            view.setText(content);
                            countTime = -1;
                            //结束后重新设置可以点击
                            view.setClickable(true);
                            ActivityLogin.this.timerTask.cancel();
                        }
                        countTime++;
                        break;
                }
            }
        };

        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
