package com.hj.casps.bankmanage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.BillsDetailsAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.cooperate.CooperateDirUtils;
import com.hj.casps.entity.appordercheckorder.BuyersAccountListBean;
import com.hj.casps.entity.appordercheckorder.BuyersAddressListBean;
import com.hj.casps.entity.appordercheckorder.DataBean;
import com.hj.casps.entity.appordercheckorder.SellersAccountListBean;
import com.hj.casps.entity.appordercheckorder.SellersAddressListBean;
import com.hj.casps.entity.appordergoods.AppOrderCheckOrderLoading;
import com.hj.casps.entity.appordergoods.AppOrderCheckOrderOrdertitle;
import com.hj.casps.entity.appordergoods.AppOrderCheckOrderRespon;
import com.hj.casps.entity.appordergoods.OrdertitleData;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.protocolmanager.FragmentDao;
import com.hj.casps.protocolmanager.ProtocolDetail;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.GetFlowType;
import static com.hj.casps.common.Constant.GetPayType;
import static com.hj.casps.common.Constant.GetSendsGoodsType;

/**
 * 订单详情页
 */
public class BillsDetailsActivity extends ActivityBaseHeader2 {
    @BindView(R.id.bills_details_rlview)
    RecyclerView bills_details_rlview;

    private BillsDetailsAdapter mRecyclerAdapter;
    private List<AppOrderCheckOrderOrdertitle<List<OrdertitleData>>> dataList;
    private List<AppOrderCheckOrderOrdertitle> listOne;
    private List<OrdertitleData> listTwo;
    private List<String> listTitle;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    setInitView();
                    break;

            }
        }
    };


    /**
     * 刷新获取的界面
     */
    private void setInitView() {
//        listOne.add(dataList.get(0));
//
//        for (int i = 0; i < dataList.get(0).data.size(); i++) {
//            listTwo = dataList.get(0).data;
//        }
        mRecyclerAdapter = new BillsDetailsAdapter(this, listOne, listTwo, listTitle);
        bills_details_rlview.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * 保存数据库
     */
    private void saveDaoData(String s) {
        Log.e("json", String.valueOf(s));
        CooperateDirUtils.getInstance(this).deleteFragmentDaoAll("100", Constant.SEARCH_sendgoods_orderId, "102");//101协议
        FragmentDao fragmentDao = new FragmentDao();
        fragmentDao.setJson(s);
        fragmentDao.setType_i(String.valueOf(100));
        fragmentDao.setType_j(String.valueOf(Constant.SEARCH_sendgoods_orderId));
        fragmentDao.setType_k(String.valueOf(102));
        CooperateDirUtils.getInstance(this).insertInfo(fragmentDao);
    }


    /**
     * 加载本地数据
     */
    private void addLocality() {
//        List<FragmentDao> fragmentDaos = CooperateDirUtils.getInstance(this).queryFragmentDaoInfo();
//        for (int i = 0; i < fragmentDaos.size(); i++) {
//            Log.e("all", fragmentDaos.get(i).toString());
//        }
        String json = CooperateDirUtils.getInstance(this).queryFragmentDaoInfo("100",Constant.SEARCH_sendgoods_orderId, "102");
        Log.e("json", String.valueOf(json));
        if (!TextUtils.isEmpty(json)) {
            AppOrderCheckOrderRespon listListListListListListAppOrderCheckOrderRespon=mGson.fromJson(json, AppOrderCheckOrderRespon.class);
            if (listListListListListListAppOrderCheckOrderRespon.ordertitle != null) {
//                            dataList.add(listListListListListListAppOrderCheckOrderRespon.ordertitle);
                listOne.add(listListListListListListAppOrderCheckOrderRespon.ordertitle);
                for (int i = 0; i < listListListListListListAppOrderCheckOrderRespon.ordertitle.orderList.size(); i++) {
                    listTwo.add(listListListListListListAppOrderCheckOrderRespon.ordertitle.orderList.get(i));
//                            toastSHORT(listListListListListListAppOrderCheckOrderRespon.ordertitle.toString());
                }
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_aetails);
        ButterKnife.bind(this);
        getBundleDatas();
        initView();

    }

    private void initView() {
        setTitle(getString(R.string.activity_bills_details_title));
        titleRight.setVisibility(View.GONE);
        bills_details_rlview.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        if (hasInternetConnected()) {
            waitDialogRectangle.show();
            getNetWotk();
        }else {
            addLocality();
        }
    }

    public void getBundleDatas() {
        dataList = new ArrayList<>();
        listOne = new ArrayList<>();
        listTwo = new ArrayList<>();
        listTitle = new ArrayList<>();
        listTitle.add("商品清单");
    }


//    /**
//     * 请求网络数据
//     */
//    public void getNetWotk() {
//        AppOrderCheckOrderLoading getGoods = new AppOrderCheckOrderLoading(
//                Constant.publicArg.getSys_token(),
//                "00005",
//                Constant.SYS_FUNC,
//                Constant.publicArg.getSys_user(),
//                Constant.publicArg.getSys_name(),
//                Constant.publicArg.getSys_member(),
//                Constant.SEARCH_sendgoods_orderId);
//        log(mGson.toJson(getGoods));
//        Constant.JSONFATHERRESPON = "AppOrderCheckOrderRespon";
//        OkGo.post(Constant.AppOrderCheckOrderUrl)
//                .tag(this)
//                .params("param", mGson.toJson(getGoods))
//                .execute(new JsonCallBack<AppOrderCheckOrderRespon<List<AppOrderCheckOrderOrdertitle<List<OrdertitleData>>>>>() {
//
//                    @Override
//                    public void onSuccess(AppOrderCheckOrderRespon<List<AppOrderCheckOrderOrdertitle<List<OrdertitleData>>>> listAppOrderCheckOrderRespon, Call call, Response response) {
//                        toastSHORT(listAppOrderCheckOrderRespon.return_message);
//                        if (listAppOrderCheckOrderRespon.Ordertitle != null) {
//                            dataList = listAppOrderCheckOrderRespon.Ordertitle;
//                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
//                        }
//                        waitDialogRectangle.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        toastSHORT(e.getMessage());
//                        waitDialogRectangle.dismiss();
//                    }
//                });
//}

    /**
     * 请求网络数据
     */
    public void getNetWotk() {
        AppOrderCheckOrderLoading getGoods = new AppOrderCheckOrderLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                Constant.SEARCH_sendgoods_orderId);
        log(mGson.toJson(getGoods));
        Constant.JSONFATHERRESPON = "AppOrderCheckOrderRespon";

//        OkGo.post(Constant.AppOrderCheckOrderUrl)
//                .tag(this)
//                .params("param", mGson.toJson(getGoods))
//                .execute(new JsonCallBack<AppOrderCheckOrderRespon<
//                        List<BuyersAccountListBean>,
//                        List<BuyersAddressListBean>,
//                        List<DataBean>,
//                        AppOrderCheckOrderOrdertitle<List<OrdertitleData>>,
//                        List<SellersAccountListBean>,
//                        List<SellersAddressListBean>
//                        >>() {
//                    @Override
//                    public void onSuccess(AppOrderCheckOrderRespon<List<BuyersAccountListBean>,
//                            List<BuyersAddressListBean>, List<DataBean>,
//                            AppOrderCheckOrderOrdertitle<List<OrdertitleData>>,
//                            List<SellersAccountListBean>,
//                            List<SellersAddressListBean>>
//                                                  listListListListListListAppOrderCheckOrderRespon,
//                                          Call call, Response response) {
//                        if (listListListListListListAppOrderCheckOrderRespon.ordertitle != null) {
////                            dataList.add(listListListListListListAppOrderCheckOrderRespon.ordertitle);
//                            listOne.add(listListListListListListAppOrderCheckOrderRespon.ordertitle);
//                            for (int i = 0; i < listListListListListListAppOrderCheckOrderRespon.ordertitle.orderList.size(); i++) {
//                                listTwo.add(listListListListListListAppOrderCheckOrderRespon.ordertitle.orderList.get(i));
////                            toastSHORT(listListListListListListAppOrderCheckOrderRespon.ordertitle.toString());
//
//                            }
//                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
//                        }
//                        waitDialogRectangle.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        toastSHORT(e.getMessage());
//                        waitDialogRectangle.dismiss();
//                    }
//
//                });
        OkGo.post(Constant.AppOrderCheckOrderUrl)
                .tag(this)
                .params("param", mGson.toJson(getGoods))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (TextUtils.isEmpty(s))
                            return;
                        AppOrderCheckOrderRespon listListListListListListAppOrderCheckOrderRespon=mGson.fromJson(s, AppOrderCheckOrderRespon.class);
                        if (listListListListListListAppOrderCheckOrderRespon.ordertitle != null) {
//                            dataList.add(listListListListListListAppOrderCheckOrderRespon.ordertitle);
                            listOne.add(listListListListListListAppOrderCheckOrderRespon.ordertitle);
                            for (int i = 0; i < listListListListListListAppOrderCheckOrderRespon.ordertitle.orderList.size(); i++) {
                                listTwo.add(listListListListListListAppOrderCheckOrderRespon.ordertitle.orderList.get(i));
//                            toastSHORT(listListListListListListAppOrderCheckOrderRespon.ordertitle.toString());

                            }
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }

                        saveDaoData(s);
                        waitDialogRectangle.dismiss();

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });


    }
}
