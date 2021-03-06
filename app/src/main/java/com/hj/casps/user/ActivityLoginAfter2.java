package com.hj.casps.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.backstage.ActivityBackStage;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.picturemanager.request.ReqMark;
import com.hj.casps.entity.picturemanager.request.ReqMmb;
import com.hj.casps.entity.picturemanager.request.ResMark;
import com.hj.casps.entity.picturemanager.request.ResMmb;
import com.hj.casps.reception.ActivityPriceSearch;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.MenuUtils;
import com.hj.casps.util.XmlUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen on 2017/4/14.
 * 会员登录后
 */

public class ActivityLoginAfter2 extends ActivityBaseHeader2 {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.save)
    TextView tv_save;
    @BindView(R.id.home)
    TextView home;
    @BindView(R.id.goBuy)
    TextView goBuy;
    @BindView(R.id.goSell)
    TextView goSell;
    @BindView(R.id.manager_back)
    TextView managerBack;
    @BindView(R.id.user_btn)
    LinearLayout userBtn;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_after2);
        ButterKnife.bind(this);
        //TODO test
        //初始化菜单数据
        onStartInitData();
        //初始化菜单的基础数据
        initMenuData();
        initView();
    }

    //初始化菜单的基础数据
    private void initMenuData() {
        /*从assert资源的menu.xml获取基础数据*/
        List<XmlUtils.MenuBean> menuBeen = XmlUtils.dom4jXMLResolve(ActivityLoginAfter2.this);
        Constant.MenuBean = menuBeen;
    }

    private void initView() {
        /*头部的设置*/
        setTitleRight("退出", null);
        titleLeft.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        titleLeft.setText("设置");
        titleTv.setText(R.string.app_name_big);
//        titleTv.setBackgroundResource(R.mipmap.icon_shop);
        ActivityLoginBefore.initWebView(mWebView);
        mWebView.loadUrl(Constant.HOME_HTTP_URL, null);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                tv_save.setVisibility(View.VISIBLE);
                managerBack.setVisibility(View.VISIBLE);
                home.setVisibility(View.VISIBLE);
                try {
                    mUrl = URLDecoder.decode(url, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (mUrl != null && !mUrl.equals("http://192.168.1.120:8081/v2/app/05/01/index.html")) {
                    queryMmbForNet(false);
                }
                LogShow(" url ActivityLoginAfter2 = [" + mUrl + "]");
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    /**
     * 跳到设置
     */
    @Override
    protected void onBackClick() {
        startActivity(new Intent(this, ActivitySetting.class));
    }

    /**
     * 退出
     */
    @Override
    protected void onRightClick() {
        showExitDialog();
    }

    @OnClick({R.id.home, R.id.goBuy, R.id.goSell, R.id.save, R.id.manager_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home:
                Constant.sys_actionId = "";
                mWebView.loadUrl(Constant.HOME_HTTP_URL, null);
                home.setVisibility(View.GONE);
                tv_save.setVisibility(View.GONE);
                managerBack.setVisibility(View.VISIBLE);
                break;
            //去销售
            case R.id.goBuy:
                 /*报价检索的sysfunc是随便传的获取菜单的任意一个 不做验证的sysfunc*/
                if (Constant.MenuList != null && Constant.MenuList.size() > 0) {
                    Constant.SYS_FUNC = Constant.MenuList.get(0).getEntity().getDircode();
                }
                Intent buy = new Intent(this, ActivityPriceSearch.class);
                buy.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                buy.putExtra("Style", 0);
                startActivity(buy);
                break;
            //去采购
            case R.id.goSell:
                 /*报价检索的sysfunc是随便传的获取菜单的任意一个 不做验证的sysfunc*/
                if (Constant.MenuList != null && Constant.MenuList.size() > 0) {
                    Constant.SYS_FUNC = Constant.MenuList.get(0).getEntity().getDircode();
                }
                Intent sale = new Intent(this, ActivityPriceSearch.class);
                sale.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                sale.putExtra("Style", 1);
                startActivity(sale);
                break;
            //收藏店铺
            case R.id.save:
                queryMmbForNet(true);
                break;
            case R.id.manager_back:
                //跳转到后台管理界面
                intentActivity(this, ActivityBackStage.class);
                break;
        }
    }

    //根据会员主页url查询有没有会员信息，如果有就可以收藏店铺
    //如果flag是true就表示是收藏店铺   如果是false就表示去销售或去采购  就不调用收藏店铺的接口了
    private void queryMmbForNet(final boolean flag) {
        if (Constant.MenuList != null && Constant.MenuList.size() > 0) {
            Constant.SYS_FUNC = Constant.MenuList.get(0).getEntity().getDircode();
        }
        waitDialogRectangle.show();
        ReqMmb r = new ReqMmb(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                mUrl);
        LogShow(mGson.toJson(r));
        OkGo.post(Constant.GetMmbByUrl)
                .params("param", mGson.toJson(r))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String data, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        ResMmb resMmb = GsonTools.changeGsonToBean(data, ResMmb.class);
                        if (resMmb.getReturn_code() == 0 && resMmb.getData() != null) {
                            String mmbId = resMmb.getData().getMmbId();
                            if (flag) {
                                markMemberForNet(mmbId);
                            } else {
                                Constant.sys_actionId = mmbId;
                            }
                        } else if (resMmb.getReturn_code() == 105) {
                            toast("不是合法会员");
                            return;

                        } else if (resMmb.getReturn_code() == 1101 || resMmb.getReturn_code() == 1102) {
                            LogoutUtils.exitUser(ActivityLoginAfter2.this);
                        } else {
                            toast("收藏失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    //收藏会员的请求
    private void markMemberForNet(String mmbId) {
        waitDialogRectangle.show();
        if (Constant.MenuList != null && Constant.MenuList.size() > 0) {
            Constant.SYS_FUNC = Constant.MenuList.get(0).getEntity().getDircode();
        }
        ReqMark r = new ReqMark(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(), mmbId);
        LogShow(mGson.toJson(r));
        OkGo.post(Constant.MarkMemberUrl)
                .params("param", mGson.toJson(r))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ResMark resMark = GsonTools.changeGsonToBean(s, ResMark.class);
                        waitDialogRectangle.dismiss();
                        if (resMark.getReturn_code() == 0) {
                            new MyToast(ActivityLoginAfter2.this, "收藏店铺成功");
                        } else if (resMark.getReturn_code() == 1101 || resMark.getReturn_code() == 1102) {
                            LogoutUtils.exitUser(ActivityLoginAfter2.this);
                        } else {
                            waitDialogRectangle.dismiss();
                            toast(resMark.getReturn_message());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            showExitDialog();
        }
        return true;
    }


    //初始化菜单数据
    private void onStartInitData() {
        PublicArg p = Constant.publicArg;
        String param = "{\"sys_token\":\"" + p.getSys_token() + "\",\"sys_user\":\"" + p.getSys_user() + "\"}";
        OkGo.post(Constant.GetMenuListUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                MenuUtils.Bean entity = GsonTools.changeGsonToBean(data, MenuUtils.Bean.class);
                if (entity != null && entity.getReturn_code() == 0 && entity.getMenus() != null) {
                    MenuUtils.Bean.MenusEntity menus = entity.getMenus();
                    handData(menus);
                } else if (entity.getReturn_code() == 1101 || entity.getReturn_code() == 1102) {
                    LogoutUtils.exitUser(ActivityLoginAfter2.this);
                } else {
                    toastSHORT("获取用户菜单失败");
                    return;
                }

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                toastSHORT(e.getMessage());

            }
        });
    }

    //处理菜单数据
    private void handData(MenuUtils.Bean.MenusEntity menus) {
        ArrayList<MenuUtils.MenusEntityExtra> extras = new ArrayList<>();
        if (menus.getMenus() == null) {
            toastSHORT("获取菜单失败");
            return;
        }
        MenuUtils.doBuildData(menus.getMenus(), extras);
        Constant.MenuList = extras;
        System.out.println("e=" + extras);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.sys_actionId = "";
    }
}
