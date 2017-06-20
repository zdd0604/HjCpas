package com.hj.casps.bankmanage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.ReceiptAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordermoney.QueryMmbBankAccountRespon;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.paymentmanager.RequestQueryGetRefundMoney;
import com.hj.casps.entity.paymentmanager.response.ResGetMoney;
import com.hj.casps.entity.paymentmanager.response.ResQueryGetMoneyEntity;
import com.hj.casps.entity.paymentmanager.response.WytUtils;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyListView;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.GsonTools;
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
 * 收款列表界面
 */
public class ReceiptActivity extends ActivityBaseHeader implements OnPullListener,
        View.OnClickListener,
        ReceiptAdapter.onCheckedkType {

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
    @BindView(R.id.layout_bottom_check_layout1)
    LinearLayout layout_bottom_check_layout1;
    @BindView(R.id.layout_bottom_check_1)
    CheckBox layout_bottom_check_1;
    @BindView(R.id.layout_head_left_btn)
    FancyButton fb;

    private ReceiptAdapter receiptAdapter;
    private MyDialog myDialog;
    private int goodsCount = 0;
    private double goodsMoeny;
    private int pageno = 0;
    private int pagesize = 10;
    private List<ResQueryGetMoneyEntity> mList = new ArrayList<>();
    private List<ResQueryGetMoneyEntity> dbList = new ArrayList<>();
    //    private boolean isReSave = true;//是否缓存
//    private boolean isSave = true;//是否清除数据
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshUI();
                    break;
            }
        }
    };
    private int pagecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        initView();
        //如果有网就加载数据
        if (hasInternetConnected()) {
            initData(pageno);
        } else {
            getDataForLocal();
        }

    }

    /**
     * 从本地加载数据
     */
    private void getDataForLocal() {
        List<ResQueryGetMoneyEntity> entities = WytUtils.getInstance(ReceiptActivity.this).QuerytQueryGetMoneyInfo();
        if (entities != null && entities.size() > 0) {
            mList.clear();
            mList.addAll(entities);
//            refreshUI();
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }
    }

    //请求数据
    private void initData(final int pageno) {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "QueryMmbBankAccountRespon";
        PublicArg p = Constant.publicArg;
        RequestQueryGetRefundMoney r = new RequestQueryGetRefundMoney(
                p.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100610003,
                p.getSys_user(),
                p.getSys_member(),
                Constant.appOrderMoney_orderId,
                Constant.appOrderMoney_goodsName,
                Constant.appOrderMoney_buyersName,
                String.valueOf(pageno + 1),
                String.valueOf(pagesize));
        String param = mGson.toJson(r);
        System.out.println("r==" + r);
        OkGo.post(Constant.QueryGeMoneyUrl)
                .params("param", param)
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<List<ResQueryGetMoneyEntity>>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<List<ResQueryGetMoneyEntity>> listData,
                                          Call call, Response response) {
                        mList.clear();
                        waitDialogRectangle.dismiss();
                        if (listData != null && listData.return_code == 0 && listData.list != null) {
                            ReceiptActivity.this.mList = listData.list;
                            pagecount = listData.pagecount;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        } else {
                            toastSHORT(listData.return_message);
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

    private void refreshUI() {
        if (isSave) {
            dbList.clear();
        }

        dbList.addAll(mList);
        LogShow(dbList.size() + "---------集合长度");

        if (pageNo == 0) {
            receiptAdapter = new ReceiptAdapter(this, mList);
            payment_list.setAdapter(receiptAdapter);
            receiptAdapter.notifyDataSetChanged();
        } else {
            if (pageNo <= ((total - 1) / pageSize)) {
                receiptAdapter.refreshList(mList);
            } else {
                mLoader.onLoadAll();//加载全部
            }
        }

        receiptAdapter.setOnCheckedkType(this);

        if (mList != null)
            for (int i = 0; i < mList.size(); i++) {
                if (!mList.get(i).getIsChecked()) {
                    layout_bottom_check_1.setChecked(false);
                    return;
                }
            }

        if (isRSave)
            saveLocalData();
//
//        waitDialogRectangle.dismiss();
//        if (isSave) {
//            pubList.clear();
//        }
//
//        dbList.addAll(mList);
//        pubList.addAll(mList);
//
//        if (pageno == 0) {
//            receiptAdapter = new ReceiptAdapter(this, mList);
//            payment_list.setAdapter(receiptAdapter);
//            receiptAdapter.notifyDataSetChanged();
//        } else {
//            if (pageno <= (pagecount - 1) / pagesize) {
//                receiptAdapter.refreshList(mList);
//            } else {
//                mLoader.onLoadAll();
//            }
//        }
//
//        receiptAdapter.setOnCheckedkType(this);
//
//        if (mList != null)
//            for (int i = 0; i < mList.size(); i++) {
//                if (!mList.get(i).getIsChecked()) {
//                    layout_bottom_check_1.setChecked(false);
//                    return;
//                }
//            }
//
//        if (isReSave) {
//            saveLocalData();
//        }
    }

    /**
     * 保存数据到数据库
     */
    private void saveLocalData() {
        //先清除数据
        WytUtils.getInstance(this).DeleteAllQueryGetMoneyInfo();
        for (int i = 0; i < dbList.size(); i++) {
            WytUtils.getInstance(this).insertQueryGetMoneyInfo(dbList.get(i));
        }
    }

    private void initView() {
        setTitle(getString(R.string.head_harvest_money_title));

        fb.setVisibility(View.GONE);
        layout_bottom_tv_2.setVisibility(View.GONE);
        layout_bottom_tv_4.setVisibility(View.GONE);

        layout_bottom_tv_3.setText(getText(R.string.head_harvest_money_title));
        layout_bottom_tv_3.setOnClickListener(this);
        layout_bottom_check_1.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_layout_1.setOnClickListener(this);

        mLoader = new NestRefreshLayout(payment_scroll);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

//        payment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(context, BillsDetailsActivity.class);
//                Bundle bundle = new Bundle();
////                bundle.putSerializable(Constant.BUNDLE_TYPE, mList.get(position));
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_bottom_tv_3:
                buyAll();
                break;
            case R.id.layout_bottom_check_layout1:
                selectLaytou();
                break;
            case R.id.layout_bottom_check_1:
                selectCheck();
                break;
            case R.id.layout_head_right_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_32);
                break;
        }
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

    private void buyAll() {
        if (goodsCount <= 0) {
            toastSHORT("请勾选一条订单");
            return;
        }
        showBillsDialog();
    }

    private void CollectReqParam() {
        List<ResGetMoney.Sub> sList = new ArrayList<>();
        for (int i = 0; i < dbList.size(); i++) {
            ResQueryGetMoneyEntity entity = dbList.get(i);
            if (entity.getIsChecked()) {
                sList.add(new ResGetMoney.Sub(entity.getId()));
            }
        }
        executeGetMoneyForNet(sList);
    }

    //收款操作
    private void executeGetMoneyForNet(List<ResGetMoney.Sub> sList) {
        waitDialogRectangle.show();
        PublicArg p = Constant.publicArg;
        ResGetMoney r = new ResGetMoney(p.getSys_token(), Constant.getUUID(), Constant.SYS_FUNC101100610003, p.getSys_user(), p.getSys_member(), sList);
        String param = mGson.toJson(r);
        OkGo.post(Constant.GetMoneyUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                if (pub != null && pub.getReturn_code() == 0) {
                    new MyToast(ReceiptActivity.this, "执行收款成功");
                    clearDatas(true, true);
                } else {
                    toastSHORT(pub.getReturn_message());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                waitDialogRectangle.dismiss();
            }
        });

    }

    public void showBillsDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_receipe_msg));
        myDialog.setReceiptHint(String.valueOf(goodsCount),
                String.valueOf(goodsMoeny), true);
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                CollectReqParam();
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
//        pageno = 0;
//        goodsCount = 0;
//        goodsMoeny = 0;
//        isEmptyParam();
//        isSave = true;
        pageNo = 0;
        isSave = true;
        goodsCount = 0;
        goodsMoeny = 0;
        if (StringUtils.isStrTrue(Constant.appOrderMoney_orderId) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_goodsName) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_buyersName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }
        initData(pageno);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
//        pageno++;
//        goodsCount = 0;
//        goodsMoeny = 0;
/*
        for (int i = 0; i < dbList.size(); i++) {
            dbList.get(i).setIsChecked(false);
        }*/
//        isEmptyParam();
//        if (dbList.size() < total)
        pageNo++;
//      Constant.clearDatas();
        isSave = false;
        if (StringUtils.isStrTrue(Constant.appOrderMoney_orderId) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_goodsName) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_buyersName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }

        if (dbList.size() < total)
            initData(pageno);
        mLoader.onLoadFinished();//加载结束
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PAYMENT_REQUEST_CODE && resultCode == Constant.PAYMENTRESULTOK) {
            goodsMoeny = 0;
            goodsCount = 0;
            pageno = 0;
            isSave = true;
            isRSave = false;
//            dbList.clear();
//            isEmptyParam();
            initData(pageno);
        }
    }


    /**
     * @param isInitData true 代表请求网络    false    刷新数据
     */
    private void clearDatas(boolean idDelste, boolean isInitData) {
        selectAll(false);
     /*   for (int i = 0; i < dbList.size(); i++) {
//            dbList.get(i).clearData();
        }*/
        goodsMoeny = 0;
        goodsCount = 0;
        pageno = 0;
        if (idDelste)
            dbList.clear();
        layout_bottom_check_1.setChecked(false);
        if (isInitData) {
            initData(pageno);
        } else {
//            refreshUI();
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }
    }

    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.RECEIPT_SEARCH_TYPE);
        intentActivity(BillsSearchActivity.class, Constant.PAYMENT_REQUEST_CODE, bundle);
    }

    @Override
    public void onCheckedY(int pos) {
        goodsCount++;
        goodsMoeny += Double.valueOf(dbList.get(pos).getMoney());
    }

    @Override
    public void onCheckedN(int pos) {
        if (goodsCount > 0)
            goodsCount--;
        if (goodsMoeny > 0) {
            goodsMoeny -= Double.valueOf(dbList.get(pos).getMoney());
        }
    }

    @Override
    public void onBillsIDItemCilckListener(int pos) {
        //TODO
        Constant.SEARCH_sendgoods_orderId = dbList.get(pos).getOrdertitleId();
        intentActivity(BillsDetailsActivity.class);
    }


    private void selectAll(boolean isck) {
        for (int i = 0; i < dbList.size(); i++) {
            // 改变boolean
            dbList.get(i).setIsChecked(isck);
            // 如果为选中
            if (dbList.get(i).getIsChecked()) {
                goodsCount++;
                goodsMoeny += Double.valueOf(dbList.get(i).getMoney());
            } else {
                goodsCount = 0;
                goodsMoeny = 0;
            }
        }
        receiptAdapter.notifyDataSetChanged();
    }

    private void deleteBills(boolean isDelete) {
        pageNo = 0;
        goodsCount = 0;
        goodsMoeny = 0;
        if (isDelete)
            dbList.clear();
        waitDialogRectangle.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }

//    public void isEmptyParam() {
//        if (StringUtils.isStrTrue(Constant.appOrderMoney_orderId) ||
//                StringUtils.isStrTrue(Constant.appOrderMoney_goodsName) ||
//                StringUtils.isStrTrue(Constant.appOrderMoney_buyersName)) {
//            isSave = true;
//            isReSave = false;
//        } else {
//            isSave = false;
//            isReSave = true;
//        }
//    }
}
