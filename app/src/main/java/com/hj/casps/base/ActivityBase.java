package com.hj.casps.base;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hj.casps.R;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.http.LoginBean;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.user.ActivityLogin;
import com.hj.casps.user.LoginJsonCallBack;
import com.hj.casps.user.UserBean;
import com.hj.casps.user.UserBeanUtils;
import com.hj.casps.util.ActivityUtils;
import com.hj.casps.util.LogToastUtils;
import com.hj.casps.util.NetUtil;
import com.hj.casps.widget.WaitDialogRectangle;
import com.lzy.okgo.OkGo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.common.base.ActivityCommBase;
import cn.jpush.android.api.JPushInterface;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/29.
 * 常用提示
 */

public class ActivityBase extends ActivityCommBase implements
        NetBroadcastReceiver.NetEvevt {
    private AlertDialog dialog;
    public Bundle bundle;
    public AbsRefreshLayout mLoader;
    public static Context context;
    public static NetBroadcastReceiver.NetEvevt evevt;
    public static Gson mGson;
    public WaitDialogRectangle waitDialogRectangle;
    public PublicArg publicArg = null;
    public View contentView;
    /**
     * 网络类型
     */
    private int netMobile;
    public FancyButton layout_head_left_btn;
    public FancyButton layout_head_right_btn;
    public TextView layout_head_right_tv;
    private Calendar c = Calendar.getInstance();
    public int pageNo = 0;//	int	开始行
    public int pageSize = 10;//	int	页条数
    public int total; //返回的总条数
    public int goodsCount = 0; //选择商品的总条数
    public MyDialog myDialog;

    protected void log(String log) {
        LogToastUtils.log(getClass().getSimpleName(), log);
    }

    public void toast(String toast) {
        LogToastUtils.toast(this, toast);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);
        initViewBaseView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        inspectNet();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    private void initViewBaseView() {
        bundle = new Bundle();
        mGson = new Gson();
        context = this;
        evevt = this;
        if (waitDialogRectangle == null)
            waitDialogRectangle = new WaitDialogRectangle(context);
        if (Constant.publicArg != null)
            publicArg = Constant.publicArg;
        inspectNet();
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(ActivityBase.this);
        return isNetConnect();

        // if (netMobile == 1) {
        // System.out.println("inspectNet：连接wifi");
        // } else if (netMobile == 0) {
        // System.out.println("inspectNet:连接移动数据");
        // } else if (netMobile == -1) {
        // System.out.println("inspectNet:当前没有网络");
        //
        // }
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
        isNetConnect();

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;

        }
        return false;
    }

    public void addContentView(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (findViewById(R.id.base_tool_bar) != null) {
            int marginTop = (int) getResources().getDimension(R.dimen.title_height);
            params.setMargins(0, marginTop, 0, 0);
        }
        super.addContentView(view, params);
    }

    public void removeContentView() {
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        if (content.getChildCount() > 1) {
            content.removeViews(1, content.getChildCount() - 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeInput();
        dialogDismiss();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        //取消所有请求
        OkGo.getInstance().cancelAll();
    }


    public void toastDialog(String str) {
        dialogDismiss();
        dialog = new AlertDialog.Builder(this).setMessage(str).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        dialog.show();
    }

    private void dialogDismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 判断是否位数字
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 长toast
     *
     * @param content
     */
    public void toastLONG(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    /**
     * 短toast
     *
     * @param content
     */
    public void toastSHORT(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短toast
     *
     * @param content
     */
    public void LogShow(String content) {
        Log.e("casps", content);
    }

    public void intentActivity(Class to) {
        Intent intent = new Intent(context, to);
        startActivity(intent);
    }

    public void intentActivity(Context from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }


    public void intentActivity(Class to, Bundle bundle) {
        Intent intent = new Intent(context, to);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void intentActivity(Class toClass, int ac_type) {
        Intent intent = new Intent(this, toClass);
        startActivityForResult(intent, ac_type);
    }

    public void intentActivity(Class toClass, int ac_type, Bundle bundle) {
        Intent intent = new Intent(this, toClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, ac_type);
    }

    /**
     * bundle
     *
     * @param from
     * @param to
     */
    public void intentActivity(Context from, Class to, Bundle bundle) {
        Intent intent = new Intent(from, to);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void showCalendar(final TextView editText) {
//        c = Calendar.getInstance();
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        if (month < 10 && dayOfMonth < 10) {
                            editText.setText(year + "-0" + month
                                    + "-0" + dayOfMonth);
                        } else if (month < 10 && dayOfMonth >= 10) {
                            editText.setText(year + "-0" + month
                                    + "-" + dayOfMonth);
                        } else if (month >= 10 && dayOfMonth < 10) {
                            editText.setText(year + "-" + month
                                    + "-0" + dayOfMonth);
                        } else {
                            editText.setText(year + "-" + month
                                    + "-" + dayOfMonth);
                        }

                    }
                }
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH)).show();
        editText.setCompoundDrawables(null, null, null, null);
    }


    public boolean hasInternetConnected() {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo network = manager.getActiveNetworkInfo();
            if (network != null && network.isConnectedOrConnecting()) {
                return true;
            }
        }
        toastSHORT("");
        return false;
    }

    /**
     * 关闭键盘事件
     */
    public void closeInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            // 启动activity时不自动弹出软键盘
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }


    /**
     * @author 增加点击edittext区域外，收起软键盘功能
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > right
                    && event.getY() > top && event.getY() < bottom) {//如果是输入框右边的部分就保留
                return false;
            }
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Editext内容
     *
     * @param editText
     * @return
     */
    public static String getEdVaule(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 获取Editext内容
     *
     * @param textView
     * @return
     */
    public static String getTvVaule(TextView textView) {
        return textView.getText().toString().trim();
    }


    //用户退出
    public void showExitDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("您确定要退出吗？");
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                exitUser();
            }
        });
        myDialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void exitUser() {
        UserBean currentUser = UserBeanUtils.getInstance(this).getCurrentUser();
        currentUser.setTokenIsActive(false);
        UserBeanUtils.getInstance(this).updateToken(currentUser);
        if (isNetConnect()) {
            httpExitApp();
        }
        intentActivity(this, ActivityLogin.class);
        finish();
    }

    //用户退出
    private void httpExitApp() {
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
                                toastSHORT("用户已退出");
                            } else if (loginBean.getReturn_code() == 101) {
                                toastSHORT(loginBean.getReturn_message());
                            } else if (loginBean.getReturn_code() == 102) {
                                toastSHORT(loginBean.getReturn_message());
                            } else if (loginBean.getReturn_code() == 103) {
                                toastSHORT(loginBean.getReturn_message());
                            } else if (loginBean.getReturn_code() == 999) {
                                toastSHORT(loginBean.getReturn_message());
                            } else {
                                toastSHORT("其他异常");
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * 弹框
     */
    public void CreateDialog(String content) {
        // 动态加载一个listview的布局文件进来
        LayoutInflater inflater = LayoutInflater.from(this);
        contentView = inflater.inflate(R.layout.dialog_textview_layout, null);
        TextView dialog_tv_content = (TextView) contentView.findViewById(R.id.dialog_tv_content);
        dialog_tv_content.setText(content);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(contentView)//在这里把写好的这个listview的布局加载dialog中
//                .setTitle("操作说明")
//                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogDismiss();
                    }
                })
                .create();
        dialog.show();
    }

    //判断手机格式是否正确
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|" +
                "(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //判断email格式是否正确
    public boolean isString(String content) {
        String str = "[a-zA-Z]";
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(content);
        return m.matches();
    }

    /**
     * 小数点后两位
     *
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
    }

    /**
     * 提供精确乘法运算的mul方法
     * @return 两个参数的积
     */
    public static double doubleValue(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }
}
