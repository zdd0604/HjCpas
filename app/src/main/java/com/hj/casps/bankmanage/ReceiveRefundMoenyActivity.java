package com.hj.casps.bankmanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.ReceiveRefundAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordermoney.QueryMmbBankAccountRespon;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.paymentmanager.RequestQueryGetRefundMoney;
import com.hj.casps.entity.paymentmanager.response.ResGetMoney;
import com.hj.casps.entity.paymentmanager.response.ResqueryGetRefundMoneyEntity;
import com.hj.casps.entity.paymentmanager.response.WytUtils;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyListView;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 收退款
 */
public class ReceiveRefundMoenyActivity extends ActivityBaseHeader implements OnPullListener,
        View.OnClickListener,
        ReceiveRefundAdapter.onCheckedkType {
    @BindView(R.id.payment_scroll)
    ScrollView payment_scroll;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    @BindView(R.id.payment_list)
    MyListView payment_list;

    @BindView(R.id.layout_bottom_layout_1)
    LinearLayout layout_bottom_layout_1;
    @BindView(R.id.layout_bottom_tv_2)
    TextView layout_bottom_tv_2;
    @BindView(R.id.layout_bottom_tv_3)
    TextView layout_bottom_tv_3;
    @BindView(R.id.layout_bottom_tv_4)
    TextView layout_bottom_tv_4;
    @BindView(R.id.layout_bottom_check_1)
    CheckBox layout_bottom_check_1;
    @BindView(R.id.layout_bottom_check_layout1)
    LinearLayout layout_bottom_check_layout1;
    @BindView(R.id.layout_head_left_btn)
    FancyButton fb;

    private ReceiveRefundAdapter adapter;

    private List<ResqueryGetRefundMoneyEntity> mList = new ArrayList<>();
    private List<ResqueryGetRefundMoneyEntity> dbList = new ArrayList<>();
    private List<ResqueryGetRefundMoneyEntity> pubList = new ArrayList<>();
    //    List<Map<String,String>> orderList = new ArrayList<>();
    List<ResGetMoney.Sub> orderList = new ArrayList<>();
    private MyDialog myDialog;
    private int goodsCount = 0;
    private double goodsMoeny;
    private int pageno = 0;
    private int pagesize = 5;

    private int pageCount;
    private boolean isReSave = true;//是否缓存
    private boolean isSave = true;//是否清除数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        initView();
        if (hasInternetConnected()) {
            initData(pageno);
        } else {
            getDataForLocal();
        }
    }

    //从本地数据库加载数据
    private void getDataForLocal() {
        List<ResqueryGetRefundMoneyEntity> entityList = WytUtils.getInstance(this).QuerytgetRefundMoneyInfo();
        if (entityList != null && entityList.size() > 0) {
            mList.clear();
            mList.addAll(entityList);
            refreshUI();
        }
    }

    private void refreshUI() {
        if (isSave) {
            pubList.clear();
        }
        for (int i = 0; i < mList.size(); i++) {
            dbList.add(mList.get(i));
            pubList.add(mList.get(i));
        }

        if (pageno == 0) {
            adapter = new ReceiveRefundAdapter(this, mList);
            payment_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            if (pageno <= ((pageCount - 1) / pagesize)) {
                adapter.refreshList(mList);
            } else {
                mLoader.onLoadAll();
            }
        }
        if (isReSave) {
            saveLocalData();
        }

        if (dbList != null && dbList.size() > 0)
            for (int i = 0; i < dbList.size(); i++) {
                if (!dbList.get(i).isChecked()) {
                    layout_bottom_check_1.setChecked(false);
                    return;
                }
            }
    }

    //插入数据到本地数据库
    private void saveLocalData() {
        WytUtils.getInstance(this).DeleteAllgetRefundMoneyInfo();
        for (int i = 0; i < pubList.size(); i++) {
            WytUtils.getInstance(this).insertgetRefundMoneyInfo(pubList.get(i));
        }
    }

    //收款操作
    private void executeGetMoneyForNet() {
        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ResGetMoney r = new ResGetMoney(p.getSys_token(), timeUUID, Constant.SYS_FUNC, p.getSys_user(), p.getSys_member(), orderList);
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.GetRefundMoneyUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                if (pub.getReturn_code() == 0) {
                    clearDatas();
                    new MyToast(ReceiveRefundMoenyActivity.this, pub.getReturn_message());
                } else if (pub.getReturn_code() == 1101 || pub.getReturn_code() == 1102) {
                    toastSHORT("重复登录或令牌超时");
                    LogoutUtils.exitUser(ReceiveRefundMoenyActivity.this);
                } else {
                    toastSHORT(pub.getReturn_message());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                orderList.clear();
                waitDialogRectangle.dismiss();
            }
        });

    }

    //请求数据
    private void initData(final int pageno) {
        Constant.JSONFATHERRESPON = "QueryMmbBankAccountRespon";
        RequestQueryGetRefundMoney r = new RequestQueryGetRefundMoney(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                Constant.appOrderMoney_orderId,
                Constant.appOrderMoney_goodsName,
                Constant.appOrderMoney_buyersName,
                String.valueOf(pageno + 1),
                String.valueOf(pagesize));
        waitDialogRectangle.show();
        OkGo.post(Constant.QueryGetRefundMoney)
                .params("param", mGson.toJson(r))
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<List<ResqueryGetRefundMoneyEntity>>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<List<ResqueryGetRefundMoneyEntity>> listData, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        mList.clear();
                        if (listData != null && listData.return_code == 0 && listData.list != null) {
                            mList = listData.list;
                            pageCount = listData.pagecount;
                            refreshUI();
                        } else {
                            toastSHORT(listData.return_message);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                        if (Constant.public_code) {
                            LogoutUtils.exitUser(ReceiveRefundMoenyActivity.this);
                        }
                    }
                });
    }

    private void initView() {
        setTitle(getString(R.string.activity_receiv_refund_money_title));

        adapter.setOnCheckedkType(this);
        fb.setVisibility(View.GONE);
        layout_bottom_tv_2.setVisibility(View.GONE);
        layout_bottom_tv_4.setVisibility(View.GONE);
        layout_bottom_tv_3.setText(getText(R.string.activity_receiv_refund_money_title));
        layout_bottom_tv_3.setOnClickListener(this);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_layout_1.setOnClickListener(this);
        layout_bottom_check_1.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);

        mLoader = new NestRefreshLayout(payment_scroll);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_bottom_check_layout1:
                selectLaytou();
                break;
            case R.id.layout_bottom_check_1:
                selectCheck();
                break;
            case R.id.layout_bottom_tv_3:
                buyAll();
                break;
            case R.id.layout_head_right_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_33);
                break;
        }
    }


    private void clearDatas() {
        selectAll(false);
        goodsCount = 0;
        pageno = 0;
        dbList.clear();
        orderList.clear();
        isReSave = false;
        layout_bottom_check_1.setChecked(false);
        initData(pageno);

    }

    /**
     * 全局选择
     */
    private void selectLaytou() {
        if (dbList == null || dbList.size() == 0) {
            toastSHORT("数据为空");
            return;
        }
        if (layout_bottom_check_1.isChecked()) {
            layout_bottom_check_1.setChecked(false);
            selectAll(false);
        } else {
            layout_bottom_check_1.setChecked(true);
            selectAll(true);
        }
    }
//    01058730263

    /**
     * 选择框
     */
    private void selectCheck() {
        if (dbList == null || dbList.size() == 0) {
            toastSHORT("数据为空");
            layout_bottom_check_1.setChecked(false);
            return;
        }
        if (layout_bottom_check_1.isChecked()) {
            selectAll(true);
        } else {
            selectAll(false);
        }
    }

    //执行操作
    private void buyAll() {
        if (goodsCount <= 0) {
            toastSHORT("请勾选一条订单");
            return;
        }
        for (int i = 0; i < dbList.size(); i++) {
            ResqueryGetRefundMoneyEntity entity = dbList.get(i);
            if (entity.isChecked()) {
              /*  HashMap<String, String> map = new HashMap<>();
                map.put("id",entity.getId());*/
                orderList.add(new ResGetMoney.Sub(entity.getId()));
            }
        }
        showBillsDialog();
    }

    public void showBillsDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_refund_money_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
//                new MyToast(context, "收款成功" + bills + "条");
                executeGetMoneyForNet();
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

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageno = 0;
        layout_bottom_check_1.setChecked(false);

     /*   for (int i = 0; i < dbList.size(); i++) {
            dbList.get(i).setIsChecked(false);

        }*/
        isEmptyParam();
        isSave = true;
        initData(pageno);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageno++;
        layout_bottom_check_1.setChecked(false);
        isEmptyParam();
        initData(pageno);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PAYMENT_REQUEST_CODE && resultCode == Constant.PAYMENTRESULTOK) {
            isEmptyParam();
            pageno = 0;
            dbList.clear();
            initData(pageno);
        }
    }

    //搜查查询方法
    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.RECEIVE_REFUND_SEARCH_TYPE);
        intentActivity(BillsSearchActivity.class, Constant.PAYMENT_REQUEST_CODE, bundle);
    }

    @Override
    public void onCheckedY(int pos) {
        goodsCount++;
    }

    @Override
    public void onCheckedN(int pos) {

        if (goodsCount > 0)
            goodsCount--;
    }

    //跳转到详情页面
    @Override
    public void onBillsIDItemCilckListener(int pos) {
        Intent intent = new Intent(context, BillsDetailsActivity.class);
        Constant.SEARCH_sendgoods_orderId = dbList.get(pos).getOrdertitleId();
        startActivity(intent);
    }

    private void selectAll(boolean isck) {
        for (int i = 0; i < dbList.size(); i++) {
            // 改变boolean
            dbList.get(i).setChecked(isck);
            // 如果为选中
            if (dbList.get(i).isChecked()) {
                goodsCount++;
//                goodsMoeny += Double.valueOf(mList.get(i).getAwait_money());
            } else {
                goodsCount = 0;
                goodsMoeny = 0;
            }
            adapter.notifyDataSetChanged();
        }

    }

    //判断是否有参数来确定 是否缓存和清空集合数据
    public void isEmptyParam() {
        if (StringUtils.isStrTrue(Constant.appOrderMoney_orderId) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_goodsName)
                || StringUtils.isStrTrue(Constant.appOrderMoney_buyersName)) {
            isSave = true;
            isReSave = false;
        } else {
            isSave = false;
            isReSave = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }
}