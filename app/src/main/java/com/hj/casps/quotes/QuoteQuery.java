package com.hj.casps.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.cooperate.CooperateDirUtils;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;

/**
 * 报价管理的当前会员报价列表，即报价管理页面
 */
public class QuoteQuery extends ActivityBaseHeader implements View.OnClickListener, OnPullListener {
    private FancyButton quote_create_Btn;//提交按钮
    private TextView quote_do_desc_tv;
    private ListView quotes_list;//报价列表
    private List<QuoteModel> quoteModels;//报价数据
    private QuotesAdapter adapter;//报价适配器
    private String url;
    private String intentString;
    private int type;
    private int i = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initData(intentString, i);
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.quotes_manage));
        setContentView(R.layout.activity_quote_query);
        initView();
        if (hasInternetConnected()) {
            initData(intentString, i);
        } else {
            addLocality();
        }
    }

    //加载报价管理列表数据
    private void initData(String s, final int i) {
        if (s == null || s.isEmpty()) {
            ModeltoJson modeltoJson = new ModeltoJson();
//        modeltoJson.goodname = "";
            modeltoJson.createTime1 = "";
            modeltoJson.sys_token = publicArg.getSys_token();
            modeltoJson.sys_user = publicArg.getSys_user();
            modeltoJson.sys_func = Constant.SYS_FUNC;
            modeltoJson.startTime = "";
            modeltoJson.sys_uuid = Constant.getUUID();
            modeltoJson.sys_member = publicArg.getSys_member();
            modeltoJson.endTime = "";
            modeltoJson.createTime2 = "";
            modeltoJson.pageno = String.valueOf(i + 1);
            modeltoJson.pagesize = "10";
            modeltoJson.status = "0";
            modeltoJson.type = "0";
            type = 0;
//            Gson gson = new Gson();
//            String s1 = gson.toJson(modeltoJson);
//            url = Constant.ShowQuoteListUrl + "?param=" + s1;
            getDatas(Constant.ShowQuoteListUrl, mGson.toJson(modeltoJson));
        } else {
            s = s.replace("\"pageNo\":\"1\"", "\"pageNo\":\"" + String.valueOf(i + 1) + "\"");
//            url = Constant.ShowQuoteListUrl + "?param=" + s;
            getDatas(Constant.ShowQuoteListUrl, s);
        }
    }

    /**
     * 加载报价管理列表数据
     *
     * @param url   网址
     * @param param 请求参数
     */
    private void getDatas(String url, String param) {
        OkGo.get(url)
                .params("param", param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        initGson(s, i);
                    }
                });
    }

    /**
     * 保存数据库
     */
    private void saveDaoData() {
        CooperateDirUtils.getInstance(this).deleteAll();
        for (int i = 0; i < quoteModels.size(); i++) {
            CooperateDirUtils.getInstance(this).insertInfo(quoteModels.get(i));
        }
    }

    /**
     * 加载本地数据
     */
    private void addLocality() {
        List<QuoteModel> usrList = CooperateDirUtils.getInstance(this).queryQuoteModelInfo();

        if (usrList.size() > 0) {
            quoteModels = usrList;
        }
        adapter.updateRes(quoteModels);

    }

    //解析报价管理
    private void initGson(String response, int i) {
        Log.d("response", response);
        quoteModels = new ArrayList<>();
        Gson gson = new Gson();
        QuotesBack quotesBack = gson.fromJson(response, QuotesBack.class);
        if (quotesBack.getReturn_code() != 0) {
            toast(quotesBack.getReturn_message());
        } else if (quotesBack.getReturn_code() == 1101 || quotesBack.getReturn_code() == 1102) {
            toastSHORT("重复登录或令牌超时");
            LogoutUtils.exitUser(QuoteQuery.this);
        } else {
            quoteModels = quotesBack.getQtList();
        }
        if (quoteModels == null || quoteModels.isEmpty()) {
            toastSHORT("尚未创建报价");
            adapter.removeAll();
            return;
        }
        for (int j = 0; j < quoteModels.size(); j++) {
            quoteModels.get(j).setType(type);
        }
        if (i != 0) {
            if (i <= (quotesBack.getQtCount() - 1) / 10) {
                adapter.addRes(quoteModels);

            } else {
                mLoader.onLoadAll();
            }
        } else {
            adapter.updateRes(quoteModels);

        }
        saveDaoData();
    }

    //加载布局
    private void initView() {

        quote_create_Btn = (FancyButton) findViewById(R.id.quote_create_Btn);
        quote_do_desc_tv = (TextView) findViewById(R.id.quote_do_desc_tv);
        quotes_list = (ListView) findViewById(R.id.quotes_list);
        mLoader = new NestRefreshLayout(quotes_list);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        quote_create_Btn.setOnClickListener(this);
        quote_do_desc_tv.setOnClickListener(this);
        adapter = new QuotesAdapter(quoteModels, QuoteQuery.this, R.layout.quotes_items, this);
        quotes_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quote_create_Btn:
                intentActivity(CreateQuotes.class, 11);
                break;
            case R.id.quote_do_desc_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_9);
                break;
        }
    }

    //右上方搜索按钮
    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        intentActivity(QuotesSearch.class, 11);
    }

    //搜索后的结果返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 22) {
            intentString = data.getExtras().getString("searchjson");
            type = data.getExtras().getInt("type");
            handler.sendEmptyMessage(0);
        } else if (requestCode == 11 && resultCode == 33) {
            handler.sendEmptyMessage(0);
        }
    }

    //下拉刷新
    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
//        toast("刷新了");
        i = 0;
        initData(intentString, i);
        mLoader.onLoadFinished();//加载结束
    }

    //上拉加载
    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
//        toast("加载了");
        i++;
        initData(intentString, i);
        mLoader.onLoadFinished();//加载结束
    }


    /**
     * 网络请求实体类，用来生成json串的请求参数
     */
    private class ModeltoJson {
        private String sys_token;//令牌号
        private String sys_uuid;//操作唯一编码
        private String sys_func;//功能编码
        private String sys_user;//用户ID
        private String sys_name;//用户名
        private String sys_member;//会员id
        private String goodname;//商品名称
        private String startTime;//有效期（开始）
        private String endTime;//有效期（结束）
        private String createTime1;//发布时间（开始）
        private String createTime2;//发布时间（结束）
        private String pageno;//页号
        private String pagesize;//页行数
        private String status;//状态0受用1禁用2过期
        private String type;//报价类型0采购1销售

    }
//    @Override
//    public void onRefresh(AbsRefreshLayout listLoader) {
//        toast("刷新了");
//        mLoader.onLoadAll();//加载全部
////        getQueryMmbWareHouseGainDatas();
//    }
//
//    @Override
//    public void onLoading(AbsRefreshLayout listLoader) {
//        toast("加载了");
//        mLoader.onLoadFinished();//加载结束
//    }

}
