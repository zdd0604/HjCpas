package com.hj.casps.bankmanage;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.PayMentAdapter;
import com.hj.casps.adapter.payadapter.ReceiptAdapter;
import com.hj.casps.adapter.payadapter.RefundAdapter;
import com.hj.casps.adapter.payadapter.RefundPayMentCodeAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordermoney.QueryMmbBankAccountRespon;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.paymentmanager.ReqPayMoneyOffine;
import com.hj.casps.entity.paymentmanager.ResPayMoneyOffline;
import com.hj.casps.entity.paymentmanager.response.RefundMoneyOfflineBean;
import com.hj.casps.entity.paymentmanager.response.RequestQueryRefundMoney;
import com.hj.casps.entity.paymentmanager.response.ResQueryGetMoneyEntity;
import com.hj.casps.entity.paymentmanager.response.ResRefundMoneyOfflineEntity;
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
 * 退款列表
 */

public class RefundActivity extends ActivityBaseHeader implements OnPullListener,
        View.OnClickListener,
        RefundAdapter.onCheckedkType {

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

    private RefundAdapter refundAdapter;
    //提交数据的集合
    private List<ReqPayMoneyOffine> orderList = new ArrayList();
    private MyDialog myDialog;
    private int goodsCount = 0;
    private View contentView;
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据
    private boolean isSetAdapter = true;//重置时是否保存数据
    private List<ResRefundMoneyOfflineEntity> mList;
    private List<ResRefundMoneyOfflineEntity> dbList;
    private List<ResRefundMoneyOfflineEntity.AccountlistBean> accountlist;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    initData(pageNo);
                    break;
                case Constant.HANDLERTYPE_1:
                    refreshUI();
                    break;
                case Constant.HANDLERTYPE_2:
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
        setTitle(getString(R.string.head_retreat_money_title));
        refundAdapter.setOnCheckedkType(this);
        layout_bottom_tv_2.setText(getString(R.string.hint_reset_title));
        layout_bottom_tv_2.setOnClickListener(this);
        layout_bottom_tv_2.setBackgroundResource(R.color.light_orange);
        layout_bottom_tv_3.setOnClickListener(this);
        layout_bottom_tv_3.setText(getText(R.string.head_retreat_money_verify_title));
        layout_bottom_tv_4.setVisibility(View.GONE);
        fb.setVisibility(View.GONE);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_check_1.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);

        mList = new ArrayList<>();
        dbList = new ArrayList<>();
        mLoader = new NestRefreshLayout(payment_scroll);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

        if (hasInternetConnected()) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        } else {
            getDataForLocal();
        }

    }

    //从本地加载数据
    private void getDataForLocal() {
        List<RefundMoneyOfflineBean> beanList = WytUtils.getInstance(this).QuerytRefundMoneyInfo();
        for (int i = 0; i < beanList.size(); i++) {
            ResRefundMoneyOfflineEntity entity = new ResRefundMoneyOfflineEntity();
            RefundMoneyOfflineBean bean = beanList.get(i);
            entity.setOrdertitleNumber(bean.getOrdertitleNumber());
            entity.setOrdertitleId(bean.getOrdertitleId());
            entity.setGoodsName(bean.getGoodsName());
            entity.setBuyersName(bean.getBuyersName());
            entity.setExeRefundNum(bean.getExeRefundNum());
            entity.setId(bean.getId());
            entity.setAccountlist(GsonTools.changeGsonToList(bean.getAccountlist(), ResRefundMoneyOfflineEntity.AccountlistBean.class));
            mList.add(entity);
        }
        if (mList != null && mList.size() > 0) {
            refreshUI();
        }

    }

    // 刷新UI
    private void refreshUI() {
        if (isSave) {
            dbList.clear();
        }

        dbList.addAll(mList);
        LogShow("mList:" + mList.toString());
        LogShow("dblist:" + dbList.toString());

        if (isSetAdapter) {
            if (pageNo == 0) {
                refundAdapter = new RefundAdapter(this, mList);
                payment_list.setAdapter(refundAdapter);
                refundAdapter.notifyDataSetChanged();
            } else {
                if (pageNo <= ((total - 1) / pageSize)) {
                    refundAdapter.refreshList(mList);
                } else {
                    mLoader.onLoadAll();//加载全部
                }
            }
        } else {
            for (int i = 0; i < dbList.size(); i++) {
                dbList.get(i).clearData();
            }
            refundAdapter = new RefundAdapter(this, dbList);
            payment_list.setAdapter(refundAdapter);
            refundAdapter.notifyDataSetChanged();
        }
        if (isRSave)
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);

        refundAdapter.setOnCheckedkType(this);

        if (dbList != null && dbList.size() > 0)
            for (int i = 0; i < dbList.size(); i++) {
                if (!dbList.get(i).isChecked()) {
                    layout_bottom_check_1.setChecked(false);
                    return;
                }
            }
        waitDialogRectangle.dismiss();
    }

    //保存数据到本地数据库
    private void saveLocalData() {
        //先删除数据
        WytUtils.getInstance(this).DeleteAllRefundMoneyInfo();
        for (int i = 0; i < dbList.size(); i++) {
            RefundMoneyOfflineBean bean = new RefundMoneyOfflineBean();
            ResRefundMoneyOfflineEntity entity = dbList.get(i);
            bean.setId(entity.getId());
            bean.setBuyersName(entity.getBuyersName());
            bean.setExeRefundNum(entity.getExeRefundNum());
            bean.setGoodsName(entity.getGoodsName());
            bean.setOrdertitleId(entity.getOrdertitleId());
            bean.setOrdertitleNumber(entity.getOrdertitleNumber());
            bean.setAccountlist(GsonTools.createGsonString(entity.getAccountlist()));
            WytUtils.getInstance(this).insertRefundMoneyInfo(bean);
        }

    }

    //请求数据接口
    private void initData(final int pageno) {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "QueryMmbBankAccountRespon";
        RequestQueryRefundMoney r = new RequestQueryRefundMoney(
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
        LogShow("r=QueryReFundMoneyUrl" + mGson.toJson(r));
        OkGo.post(Constant.QueryReFundMoneyUrl)
                .params("param", mGson.toJson(r))
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<List<ResRefundMoneyOfflineEntity>>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<List<ResRefundMoneyOfflineEntity>> listData, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        if (listData != null && listData.return_code == 0 && listData.list != null) {
                            mList = listData.list;
                            total = listData.pagecount;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
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
                            LogoutUtils.exitUser(RefundActivity.this);
                        }
                    }
                });
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
                CreateDialog(Constant.DIALOG_CONTENT_34);
                break;
            case R.id.layout_bottom_tv_2:
                isSave = false;
                isSetAdapter = false;
                selectAll(false);
                deleteBills(false);
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
                break;
        }
    }

    private void clearDatas(boolean isInitData) {
        selectAll(false);
        for (int i = 0; i < dbList.size(); i++) {
            dbList.get(i).clearData();
        }
        goodsCount = 0;
        pageNo = 0;
        orderList.clear();
        layout_bottom_check_1.setChecked(false);
        if (isInitData) {
            dbList.clear();
            initData(pageNo);
        } else {
            refreshUI();
        }
    }


    /**
     * 收集提交所用的参数
     */
    private void buyAll() {
        if (goodsCount <= 0) {
            toastSHORT("请勾选一条订单");
            return;
        }
        for (int i = 0; i < dbList.size(); i++) {
            ResRefundMoneyOfflineEntity entity = dbList.get(i);

            if (entity.isChecked()) {
                System.out.println("entity.getPayNum()" + entity.getPayNum());
                if (entity.getPayNum() == null || !StringUtils.isStrTrue(entity.getPayNum())) {

                    toastSHORT("请输入退款金额");
                    return;
                }
                if (entity.getPayNum().equals("0") || entity.getPayNum().equals("0.0")) {
                    toastSHORT("金额不能为0");
                    return;
                }

                if (entity.getExeRefundNum() < Double.valueOf(entity.getPayNum())) {
                    toastSHORT("超出可退金额");
                    return;

                }
                ReqPayMoneyOffine r = new ReqPayMoneyOffine();
                //订单id
                r.setId(entity.getId());
                //备注
                r.setPayMoneyCode(entity.getPayMentCode());
                //付款金额
                r.setNum(entity.getPayNum());
                //商品名
                r.setGoodName(entity.getGoodsName());
                //备注
                r.setGetPayRemark(entity.getReMark());
                orderList.add(r);
            }
        }
        showBillsDialog();

    }


    @Override
    public void onPayMentCodeItemClickListener(TextView view, int pos) {
        accountlist = dbList.get(pos).getAccountlist();
        if (accountlist != null && dbList.size() > 0) {
            CreateDialog(view, accountlist, pos);
        }
    }

    /**
     * 弹框
     */
    public void CreateDialog(final TextView textView,
                             final List<ResRefundMoneyOfflineEntity.AccountlistBean> accountlist,
                             final int listpos) {
        // 动态加载一个listview的布局文件进来
        LayoutInflater inflater = LayoutInflater.from(this);
        contentView = inflater.inflate(R.layout.layout_list_popup, null);
        // 给ListView绑定内容
        ListView listView = (ListView) contentView.findViewById(R.id.layout_list_popup);
        RefundPayMentCodeAdapter qadapter = new RefundPayMentCodeAdapter(context, accountlist);
        listView.setAdapter(qadapter);
        qadapter.notifyDataSetChanged();
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(contentView)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(accountlist.get(position).getBankname() + accountlist.get(position).getAccountno());
                dbList.get(listpos).setPayMentCode(accountlist.get(position).getAccountno());
                dialog.dismiss();
            }
        });
    }


    //请求网络接口
    private void executePayMoneyForNet() {
        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ResPayMoneyOffline r = new ResPayMoneyOffline(
                p.getSys_token(),
                timeUUID,
                Constant.SYS_FUNC,
                p.getSys_user(),
                p.getSys_member(),
                orderList);
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.RefundMoneyOffline).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                if (pub != null) {
                    if (pub.getReturn_code() == 0) {
                        new MyToast(RefundActivity.this, pub.getReturn_message());
                    } else if (pub.getReturn_code() == 1101 || pub.getReturn_code() == 1102) {
                        toastSHORT("重复登录或令牌超时");
                        LogoutUtils.exitUser(RefundActivity.this);
                    }
                    deleteBills(false);
                } else {
                    toastSHORT(pub.getReturn_message());
                }

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                waitDialogRectangle.dismiss();
                orderList.clear();
            }
        });
    }

    /**
     * 全局选择
     */
    private void selectLaytou() {
        if (dbList != null && dbList.size() > 0)
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
        if (layout_bottom_check_1.isChecked()) {
            selectAll(true);
        } else {
            selectAll(false);
        }
    }


    public void reSetBillsDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_reset_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selectAll(false);
                layout_bottom_check_1.setChecked(false);
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

    public void showBillsDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_refund_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                executePayMoneyForNet();

            }
        });
        myDialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                orderList.clear();
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageNo = 0;
        isSave = true;
        if (StringUtils.isStrTrue(Constant.appOrderMoney_orderId) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_goodsName)
                || StringUtils.isStrTrue(Constant.appOrderMoney_buyersName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }
        if (hasInternetConnected())
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);

        mLoader.onLoadFinished();//加载结束

    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageNo++;
        isSave = false;
        if (StringUtils.isStrTrue(Constant.appOrderMoney_orderId) ||
                StringUtils.isStrTrue(Constant.appOrderMoney_goodsName)
                || StringUtils.isStrTrue(Constant.appOrderMoney_buyersName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }
        if (dbList.size() < total)
            if (hasInternetConnected())
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PAYMENT_REQUEST_CODE && resultCode == Constant.PAYMENTRESULTOK) {
            pageNo = 0;
            isSave = true;
            isRSave = false;
            initData(pageNo);
        }
    }

    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.REFUND_SEARCH_TYPE);
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

    @Override
    public void onBillsIDItemCilckListener(int pos) {
        Intent intent = new Intent(context, BillsDetailsActivity.class);
        Constant.SEARCH_sendgoods_orderId = dbList.get(pos).getOrdertitleId();
        startActivity(intent);
    }


    //全选
    private void selectAll(boolean isck) {
        for (int i = 0; i < dbList.size(); i++) {
            // 改变boolean
            dbList.get(i).setChecked(isck);
            // 如果为选中
            if (dbList.get(i).isChecked()) {
                goodsCount++;
            } else {
                goodsCount = 0;
            }
        }
        refundAdapter.notifyDataSetChanged();
    }


    /**
     * 清除数据
     */
    private void deleteBills(boolean isDelete) {
        // 刷新
        goodsCount = 0;
        pageNo = 0;
        //判断要不要清空数据
        if (isDelete)
            dbList.clear();
        mList.clear();
        orderList.clear();
        waitDialogRectangle.dismiss();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }

}
