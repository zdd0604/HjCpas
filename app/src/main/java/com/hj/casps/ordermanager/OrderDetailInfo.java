package com.hj.casps.ordermanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.adapter.ordermanageradapter.OrderDetailsAdapter;
import com.hj.casps.adapter.ordermanageradapter.ViewHolderOrderlOne;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appordercheckorder.BuyersAccountListBean;
import com.hj.casps.entity.appordercheckorder.BuyersAddressListBean;
import com.hj.casps.entity.appordercheckorder.DataBean;
import com.hj.casps.entity.appordercheckorder.SellersAccountListBean;
import com.hj.casps.entity.appordercheckorder.SellersAddressListBean;
import com.hj.casps.entity.appordergoods.AppOrderCheckOrderLoading;
import com.hj.casps.entity.appordergoods.AppOrderCheckOrderOrdertitle;
import com.hj.casps.entity.appordergoods.AppOrderCheckOrderRespon;
import com.hj.casps.entity.appordergoods.OrdertitleData;
import com.hj.casps.entity.appordergoods.QueryMmbWareHouseLoading;
import com.hj.casps.entity.appordergoods.WarehouseEntity;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordergoodsCallBack.SimpleResponse;
import com.hj.casps.entity.appordermoney.MmbBankAccountEntity;
import com.hj.casps.entity.appordermoney.QueryAddressAccountEntity;
import com.hj.casps.entity.appordermoney.QueryMmbBankAccountRespon;
import com.hj.casps.entity.paymentmanager.RequestBackAccount;
import com.hj.casps.overdealmanager.NewCreateSettlDetails;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.widget.WrapContentLinearLayoutManager;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.getUUID;

/**
 * Created by Admin on 2017/7/4.
 */

public class OrderDetailInfo extends ActivityBaseHeader2 implements View.OnClickListener,
        ViewHolderOrderlOne.onClickOrderListener {
    @BindView(R.id.order_recycler)
    RecyclerView order_recycler;
    @BindView(R.id.order_detail_num)
    TextView order_detail_num;
    @BindView(R.id.order_detail_product_pay)
    TextView order_detail_product_pay;
    @BindView(R.id.order_detail_submit)
    Button order_detail_submit;


    OrderDetailsAdapter orderDetailsAdapter;
    private ListView listView;
    private AlertDialog dialog;
    private int type;
    private String id;
    private int state;
    private List<OrderShellModel> orders;
    private boolean orderList;
    private String buy_name;
    private String sell_name;
    private String buy_id;
    private String sell_id;
    private String account2 = "";
    private String bankname2 = "";
    private String adress2 = "";
    private EditText order_detail_time_pay;
    private EditText order_detail_time_start;
    private TextView order_detail_process;
    private EditText order_detail_time_end;
    private List<String> statusItems = new ArrayList<>();
    private List<MmbBankAccountEntity> mList;//银行账户
    private List<WarehouseEntity> mAddressList;//地址

    private List<String> listOne = new ArrayList<>();
    private List<OrderShellModel> listTwo = new ArrayList<>();
    private List<String> title = new ArrayList<>();

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    //请求数据
                    getQueryMmbBankAccountGainDatas();
                    break;
                case Constant.HANDLERTYPE_1:
                    //刷新数据
                    getQueryMmbWareHouseGainDatas();
                    break;
                case Constant.HANDLERTYPE_2:
                    //请求数据
                    break;
                case Constant.HANDLERTYPE_3:
                    getData();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_info);
        ButterKnife.bind(this);

        initView();
    }

    //
    private void initView() {
        statusItems.add("货款两清");
        statusItems.add("先货后款");
        statusItems.add("先货后款已交货");
        statusItems.add("先款后货");
        statusItems.add("先款后货已收款");
        type = getIntent().getIntExtra("type", 0);
        if (hasInternetConnected())
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        if (type == 1) {
            id = getIntent().getStringExtra("id");
            state = getIntent().getIntExtra("state", 0);
            if (hasInternetConnected())
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_3);
        } else {
            id = "";
            orders = getIntent().getParcelableArrayListExtra("orders");
            orderList = getIntent().getBooleanExtra("OrderList", false);
            buy_name = getIntent().getStringExtra("buy_name");
            buy_id = getIntent().getStringExtra("buy_id");
            account2 = getIntent().getStringExtra("account") == null ? "" : getIntent().getStringExtra("account");
            bankname2 = getIntent().getStringExtra("bankname") == null ? "" : getIntent().getStringExtra("bankname");
            adress2 = getIntent().getStringExtra("adress") == null ? "" : getIntent().getStringExtra("adress");
            state = getIntent().getIntExtra("state", 0);
            if (orderList && orders.size() > 0)
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
        }

        listOne.add("商品清单");
        title.add("商品清单");
        ViewHolderOrderlOne.setOnClickOrderListener(this);
        orderDetailsAdapter = new OrderDetailsAdapter(this, listOne, listTwo, title);
        order_recycler.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        order_recycler.setAdapter(orderDetailsAdapter);
        orderDetailsAdapter.notifyDataSetChanged();
    }

    //根据id和类型进行订单加载操作
    private void getData() {
        AppOrderCheckOrderLoading getGoods = new AppOrderCheckOrderLoading(
                Constant.publicArg.getSys_token(),
                getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                id);
        log(mGson.toJson(getGoods));
        Constant.JSONFATHERRESPON = "AppOrderCheckOrderRespon";

        OkGo.post(Constant.AppOrderCheckOrderUrl)
                .tag(this)
                .params("param", mGson.toJson(getGoods))
                .execute(new JsonCallBack<AppOrderCheckOrderRespon>() {
                    @Override
                    public void onSuccess(AppOrderCheckOrderRespon
                                                  listListListListListListAppOrderCheckOrderRespon,
                                          Call call, Response response) {
                        if (listListListListListListAppOrderCheckOrderRespon.ordertitle != null) {
                            AppOrderCheckOrderOrdertitle<List<OrdertitleData>> appOrderCheckOrderOrdertitle =
                                    listListListListListListAppOrderCheckOrderRespon.ordertitle;
                            toastSHORT(Constant.stmpToDate(appOrderCheckOrderOrdertitle.payTime));
                            order_detail_time_pay.setText(Constant.stmpToDate(appOrderCheckOrderOrdertitle.payTime));
                            order_detail_time_pay.setText(Constant.stmpToDate(appOrderCheckOrderOrdertitle.payTime));
                            order_detail_process.setText(statusItems.get(appOrderCheckOrderOrdertitle.workflowTypeId - 1));
                            order_detail_time_start.setText(Constant.stmpToDate(appOrderCheckOrderOrdertitle.executeStartTime));
                            order_detail_time_end.setText(Constant.stmpToDate(appOrderCheckOrderOrdertitle.executeEndTime));
                            order_detail_product_pay.setText(appOrderCheckOrderOrdertitle.totalMoney + "");
//                            buy_id = appOrderCheckOrderOrdertitle.buyersId;
//                            if (state == 2) {
//                                if (buy_id.equalsIgnoreCase(publicArg.getSys_member())) {
//                                    state = 1;
//                                } else {
//                                    state = 0;
//                                }
////                                getQueryMmbBankAccountGainDatas();
////                                getQueryMmbWareHouseGainDatas();
//                            }
//
//                            buy_name = appOrderCheckOrderOrdertitle.buyersName;
//                            sell_id = appOrderCheckOrderOrdertitle.sellersId;
//                            sell_name = appOrderCheckOrderOrdertitle.sellersName;
//                            orders = new ArrayList<OrderShellModel>();
//                            if (state == 1) {
//                                //采购
//                                account2 = "";
//                                bankname2 = listListListListListListAppOrderCheckOrderRespon.sellersAccountList.size() == 0 ? "" : listListListListListListAppOrderCheckOrderRespon.sellersAccountList.get(0).getBankname();
//                                adress2 = listListListListListListAppOrderCheckOrderRespon.sellersAddressList.size() == 0 ? "" : listListListListListListAppOrderCheckOrderRespon.sellersAddressList.get(0).getAddress();
////                                String[] get_accounts = new String[]{appOrderCheckOrderOrdertitle.getBank + "\n" + appOrderCheckOrderOrdertitle.getAccount};
////                                String[] get_address = new String[]{appOrderCheckOrderOrdertitle.sellersAddressName};
////                                order_detail_get_account.setAdapter(new TestArrayAdapter(getApplicationContext(), get_accounts));
////                                order_detail_get_address.setAdapter(new TestArrayAdapter(getApplicationContext(), get_address));
////                                order_detail_get_account.setEnabled(false);
////                                order_detail_get_address.setEnabled(false);
//                            } else {
//                                //销售
//                                account2 = "";
//                                bankname2 = listListListListListListAppOrderCheckOrderRespon.buyersAccountList.size() == 0 ? "" : listListListListListListAppOrderCheckOrderRespon.buyersAccountList.get(0).getBankname();
//                                adress2 = listListListListListListAppOrderCheckOrderRespon.buyersAddressList.size() == 0 ? "" : listListListListListListAppOrderCheckOrderRespon.buyersAddressList.get(0).getAddress();
////                                String[] pay_accounts = new String[]{appOrderCheckOrderOrdertitle.payBank + "\n" + appOrderCheckOrderOrdertitle.payAccount};
////                                String[] pay_address = new String[]{appOrderCheckOrderOrdertitle.buyersAddressName};
////                                order_detail_pay_account.setAdapter(new TestArrayAdapter(getApplicationContext(), pay_accounts));
////                                order_detail_pay_address.setAdapter(new TestArrayAdapter(getApplicationContext(), pay_address));
////                                order_detail_pay_account.setEnabled(false);
////                                order_detail_pay_address.setEnabled(false);
//                            }
//                            for (int i = 0; i < listListListListListListAppOrderCheckOrderRespon.ordertitle.orderList.size(); i++) {
//                                OrdertitleData ordertitleData = appOrderCheckOrderOrdertitle.orderList.get(i);
//                                OrderShellModel orderShellModel = new OrderShellModel();
//                                orderShellModel.setGoodsId(ordertitleData.goodsId);
//                                orderShellModel.setId(ordertitleData.id);
//                                orderShellModel.setCategoryId(ordertitleData.categoryId);
//                                orderShellModel.setName(ordertitleData.goodsName);
//                                orderShellModel.setQuoteId(ordertitleData.quoteId);
//                                orderShellModel.setPrice("0-1000000000000000000");
//                                orderShellModel.setFinalprice(String.valueOf(ordertitleData.price));
//                                orderShellModel.setAllprice(String.valueOf(ordertitleData.money));
//                                orderShellModel.setNum((int) ordertitleData.goodsNum);
//                                orders.add(orderShellModel);
//
//                            }
//                            order_detail_num.setText(String.valueOf(orders.size()));
////                            adapter.updateRes(orders);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * 获取银行账户列表
     */
    private void getQueryMmbBankAccountGainDatas() {
        waitDialogRectangle.show();
        RequestBackAccount r = new RequestBackAccount(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                "1",
                "30");
        String param = mGson.toJson(r);
        Constant.JSONFATHERRESPON = "QueryMmbBankAccountRespon";
        OkGo.post(Constant.QueryMmbBankAccountUrl)
                .tag(this)
                .params("param", param)
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<List<MmbBankAccountEntity>>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<List<MmbBankAccountEntity>> listQueryMmbBankAccountGain, Call call, Response response) {
                        if (listQueryMmbBankAccountGain != null) {
                            mList = listQueryMmbBankAccountGain.list;
                            if (hasInternetConnected())
                                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
                        } else {
                            toastSHORT(listQueryMmbBankAccountGain.return_message);
                        }
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        toastSHORT(e.getMessage());
                        if (Constant.public_code) {
                            LogoutUtils.exitUser(OrderDetailInfo.this);
                        }
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    /**
     * 获取收发货地址
     */
    private void getQueryMmbWareHouseGainDatas() {
        waitDialogRectangle.show();
        QueryMmbWareHouseLoading queryMmbWareHouseLoading = new QueryMmbWareHouseLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                "1",
                "30",
                Constant.appOrderMoney_orderId);
        log(mGson.toJson(queryMmbWareHouseLoading));
        OkGo.post(Constant.QueryMmbWareHouseUrl)
                .tag(this)
                .params("param", mGson.toJson(queryMmbWareHouseLoading))
                .execute(new JsonCallBack<SimpleResponse<List<WarehouseEntity>>>() {
                    @Override
                    public void onSuccess(SimpleResponse<List<WarehouseEntity>> listQueryMmbWareHouseGain, Call call, Response response) {
                        if (listQueryMmbWareHouseGain.rows != null) {
                            mAddressList = listQueryMmbWareHouseGain.rows;


                        }
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        toastSHORT(e.getMessage());
                        if (Constant.public_code) {
                            LogoutUtils.exitUser(OrderDetailInfo.this);
                        }
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTimePay(EditText textView) {
        showCalendar(textView);
    }

    @Override
    public void onTimeStart(EditText textView) {
        showCalendar(textView);

    }

    @Override
    public void onTimeEnd(EditText editText) {
        showCalendar(editText);

    }

    @Override
    public void onProgress(final TextView textView) {
        if (statusItems.size() <= 0)
            return;
        CreateDialog();
        ProgressAdapter qadapter = new ProgressAdapter(context, statusItems);
        listView.setAdapter(qadapter);
        qadapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(statusItems.get(position));
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onPayaccount(final TextView editText) {
        switch (state) {
            case 0://销售拣单车
                if (mList.size() <= 0)
                    return;
                CreateDialog();
                PayAdapter qadapter = new PayAdapter(context, mList);
                listView.setAdapter(qadapter);
                qadapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        editText.setText(mList.get(position).getBankname() + "\n" + mList.get(position).getAccountno());
                        dialog.dismiss();
                    }
                });

                break;
            case 1://采购拣单车
                editText.setText(adress2);
                break;
        }
    }

    @Override
    public void onPayaddress(final TextView editText) {
        switch (state) {
            case 0://销售拣单车
                if (mAddressList.size() <= 0)
                    return;
                CreateDialog();
                AddressAdapter qadapter = new AddressAdapter(context, mAddressList);
                listView.setAdapter(qadapter);
                qadapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        editText.setText(mAddressList.get(position).getAddress());
                        dialog.dismiss();
                    }
                });

                break;
            case 1://采购拣单车
                editText.setText(adress2);
                break;
        }


    }

    public class AddressAdapter extends CommonAdapter<WarehouseEntity> {
        public AddressAdapter(Context context, List<WarehouseEntity> datas) {
            super(context, datas, R.layout.layout_item_popup);
            this.mContext = context;
        }

        @Override
        public void concert(ViewHolder hooder, WarehouseEntity queryAddressAccountEntity, int indexPos) {
            TextView textView = hooder.getView(R.id.layout_item_tv);
            textView.setText(queryAddressAccountEntity.getAddress());
        }
    }

    public class PayAdapter extends CommonAdapter<MmbBankAccountEntity> {
        public PayAdapter(Context context, List<MmbBankAccountEntity> datas) {
            super(context, datas, R.layout.layout_item_popup);
            this.mContext = context;
        }

        @Override
        public void concert(ViewHolder hooder, MmbBankAccountEntity queryAddressAccountEntity, int indexPos) {
            TextView textView = hooder.getView(R.id.layout_item_tv);
            textView.setText(queryAddressAccountEntity.getBankname() + "\n" + queryAddressAccountEntity.getAccountno());
        }
    }

    public class ProgressAdapter extends CommonAdapter<String> {

        public ProgressAdapter(Context context, List<String> datas) {
            super(context, datas, R.layout.layout_item_popup);
        }

        @Override
        public void concert(ViewHolder hooder, String s, int indexPos) {
            TextView textView = hooder.getView(R.id.layout_item_tv);
            textView.setText(s);
        }
    }

    @Override
    public void onGetaccount(final TextView editText) {
        switch (state) {
            case 0://采购拣单车
                editText.setText(adress2);
                break;
            case 1://销售拣单车
                if (mList.size() <= 0)
                    return;
                CreateDialog();
                PayAdapter qadapter = new PayAdapter(context, mList);
                listView.setAdapter(qadapter);
                qadapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        editText.setText(mList.get(position).getBankname() + "\n" + mList.get(position).getAccountno());
                        dialog.dismiss();
                    }
                });
                break;

        }
    }

    @Override
    public void onGetaddress(final TextView editText) {
        if (mAddressList.size() <= 0)
            return;
        switch (state) {
            case 0://采购拣单车
                editText.setText(adress2);
                break;
            case 1://销售拣单车
                CreateDialog();
                AddressAdapter qadapter = new AddressAdapter(context, mAddressList);
                listView.setAdapter(qadapter);
                qadapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        editText.setText(mAddressList.get(position).getAddress());
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public void setViewId(View order_detail_time_pay,
                          View order_detail_time_start,
                          View order_detail_time_end,
                          View order_detail_process) {
        this.order_detail_process = (TextView) (order_detail_process);
        this.order_detail_time_pay = (EditText) (order_detail_time_pay);
        this.order_detail_time_start = (EditText) (order_detail_time_start);
        this.order_detail_time_end = (EditText) (order_detail_time_end);

    }

    /**
     * 弹框
     */
    public void CreateDialog() {
        // 动态加载一个listview的布局文件进来
        LayoutInflater inflater = LayoutInflater.from(this);
        contentView = inflater.inflate(R.layout.layout_list_popup, null);
        // 给ListView绑定内容
        listView = (ListView) contentView.findViewById(R.id.layout_list_popup);
        dialog = new AlertDialog.Builder(this)
                .setView(contentView)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.show();

    }
}
