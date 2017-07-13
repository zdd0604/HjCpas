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
    private List<ResQueryGetMoneyEntity> mList;
    private List<ResQueryGetMoneyEntity> dbList;
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    refreshUI();
                    break;
                case Constant.HANDLERTYPE_1:
                    saveLocalData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        initView();
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
        mList = new ArrayList<>();
        dbList = new ArrayList<>();

        //如果有网就加载数据
        if (hasInternetConnected()) {
            initData(pageNo);
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
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }
    }

    private void refreshUI() {
        if (isSave) {
            dbList.clear();
        }

        dbList.addAll(mList);

        LogShow(dbList.toString());
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

        if (isRSave)
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);

        receiptAdapter.setOnCheckedkType(this);

        if (dbList != null && dbList.size() > 0)
            for (int i = 0; i < dbList.size(); i++) {
                if (!dbList.get(i).getIsChecked()) {
                    layout_bottom_check_1.setChecked(false);
                    return;
                }
            }
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

    //收集请求参数
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
        if (hasInternetConnected())
            initData(pageNo);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageNo++;
        isSave = false;
        if (StringUtils.isStrTrue(Constant.appOrderMoney_orderId) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_goodsName) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_buyersName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }
        if (dbList.size() < total) {
            if (hasInternetConnected())
                initData(pageNo);
        }
        mLoader.onLoadFinished();//加载结束
    }


    //请求数据
    private void initData(final int pageno) {
        waitDialogRectangle.show();
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
                String.valueOf(pageSize));
        LogShow(mGson.toJson(r));
        OkGo.post(Constant.QueryGeMoneyUrl)
                .params("param", mGson.toJson(r))
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<List<ResQueryGetMoneyEntity>>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<List<ResQueryGetMoneyEntity>> listData,
                                          Call call, Response response) {
                        waitDialogRectangle.dismiss();
//                        mList.clear();
                        if (listData != null && listData.return_code == 0 && listData.list != null) {
                            mList = listData.list;
                            total = listData.pagecount;
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
                        if (Constant.public_code) {
                            LogoutUtils.exitUser(ReceiptActivity.this);
                        }
                    }
                });
    }

    //收款操作
    private void executeGetMoneyForNet(List<ResGetMoney.Sub> sList) {
        waitDialogRectangle.show();
        ResGetMoney r = new ResGetMoney(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                sList);
        OkGo.post(Constant.GetMoneyUrl)
                .params("param", mGson.toJson(r))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String data, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                        if (pub != null && pub.getReturn_code() == 0) {
                            new MyToast(ReceiptActivity.this, "执行收款成功");
                            clearDatas(true, true);
                        } else if (pub.getReturn_code() == 1101 || pub.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(ReceiptActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PAYMENT_REQUEST_CODE && resultCode == Constant.PAYMENTRESULTOK) {
            goodsMoeny = 0;
            goodsCount = 0;
            pageNo = 0;
            isSave = true;
            isRSave = false;
            initData(pageNo);
        }
    }


    /**
     * @param isInitData true 代表请求网络    false    刷新数据
     */
    private void clearDatas(boolean idDelste, boolean isInitData) {
        selectAll(false);
        goodsMoeny = 0;
        goodsCount = 0;
        pageNo = 0;
        if (idDelste)
            dbList.clear();
        layout_bottom_check_1.setChecked(false);
        if (isInitData) {
            initData(pageNo);
        } else {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }
    }

    //查询搜索方法
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

    //跳转到详情页
    @Override
    public void onBillsIDItemCilckListener(int pos) {
        //TODO
        Constant.SEARCH_sendgoods_orderId = dbList.get(pos).getOrdertitleId();
        intentActivity(BillsDetailsActivity.class);
    }

    //全选操作
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }
}
