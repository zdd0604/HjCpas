package com.hj.casps.ordermanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
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
import com.hj.casps.entity.appordermoney.QueryMmbBankAccountRespon;
import com.hj.casps.entity.paymentmanager.RequestBackAccount;
import com.hj.casps.entity.protocalproductentity.CreateOrder;
import com.hj.casps.entity.protocalproductentity.OrderBack;
import com.hj.casps.ui.MyListView;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC101100410002;
import static com.hj.casps.common.Constant.getUUID;

//下定单的页面
public class OrderDetail extends ActivityBaseHeader2 implements View.OnClickListener {

    private EditText order_detail_time_pay;
    private Spinner order_detail_process;
    private EditText order_detail_time_start;
    private EditText order_detail_time_end;
    private Spinner order_detail_pay_account;
    private Spinner order_detail_pay_address;
    private Spinner order_detail_get_account;
    private Spinner order_detail_get_address;
    private MyListView order_detail_add_layout;
    private TextView order_detail_num;
    private TextView order_detail_product_pay;
    private List<OrderShellModel> orders;
    private OrderShellDetailAdapter adapter;
    private double allPrice;
    public static OrderDetail orderDetail = null;
    private Button order_detail_submit;
    //    private String url = HTTPURL + "appOrder/createOrder.app";
    private List<MmbBankAccountEntity> mList;//银行账户
    private List<WarehouseEntity> mAddressList;//地址
    private String[] addressLists;
    private String[] bankLists;
    private String[] statusItems;
    private int state;
    private int type;
    private TestArrayAdapter stringArrayAdapter1;
    private TestArrayAdapter stringArrayAdapter2;
    private TestArrayAdapter stringArrayAdapter3;
    private String buy_name;
    private String sell_name;
    private String buy_id;
    private String sell_id;
    private String id;
    private boolean orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderDetail = this;
        initData();
        initView();

    }

    //加载下订单的参数
    private void initData() {
        type = getIntent().getIntExtra("type", 0);

        if (type == 1) {
            id = getIntent().getStringExtra("id");
            state = getIntent().getIntExtra("state", 0);

            getData();

        } else {
            orders = getIntent().getParcelableArrayListExtra("orders");
            orderList = getIntent().getBooleanExtra("OrderList", false);
            buy_id = getIntent().getStringExtra("buy_id");
            buy_name = getIntent().getStringExtra("buy_name");
            state = getIntent().getIntExtra("state", 0);

            if (orderList) {
                for (int i = 0; i < orders.size(); i++) {
                    searchPrice(orders.get(i));
                }
            }
        }


        getQueryMmbBankAccountGainDatas();
        getQueryMmbWareHouseGainDatas();
        statusItems = new String[]{"货款两清", "先货后款", "先货后款已交货", "先款后货", "先款后货已收款"};
        stringArrayAdapter3 = new TestArrayAdapter(getApplicationContext(), statusItems);
    }

    //根据id和类型进行订单加载操作
    private void getData() {
        AppOrderCheckOrderLoading getGoods = new AppOrderCheckOrderLoading(
                Constant.publicArg.getSys_token(),
                getUUID(),
                Constant.SYS_FUNC101100510002,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                id);
        log(mGson.toJson(getGoods));
        Constant.JSONFATHERRESPON = "AppOrderCheckOrderRespon";

        OkGo.post(Constant.AppOrderCheckOrderUrl)
                .tag(this)
                .params("param", mGson.toJson(getGoods))
                .execute(new JsonCallBack<AppOrderCheckOrderRespon<
                        List<BuyersAccountListBean>,
                        List<BuyersAddressListBean>,
                        List<DataBean>,
                        AppOrderCheckOrderOrdertitle<List<OrdertitleData>>,
                        List<SellersAccountListBean>,
                        List<SellersAddressListBean>
                        >>() {
                    @Override
                    public void onSuccess(AppOrderCheckOrderRespon<List<BuyersAccountListBean>,
                            List<BuyersAddressListBean>, List<DataBean>,
                            AppOrderCheckOrderOrdertitle<List<OrdertitleData>>,
                            List<SellersAccountListBean>,
                            List<SellersAddressListBean>>
                                                  listListListListListListAppOrderCheckOrderRespon,
                                          Call call, Response response) {
                        if (listListListListListListAppOrderCheckOrderRespon.ordertitle != null) {
                            AppOrderCheckOrderOrdertitle<List<OrdertitleData>> appOrderCheckOrderOrdertitle = listListListListListListAppOrderCheckOrderRespon.ordertitle;
                            order_detail_time_pay.setText(Constant.stmpToDate(appOrderCheckOrderOrdertitle.payTime));
                            order_detail_process.setSelection(appOrderCheckOrderOrdertitle.workflowTypeId - 1);
                            order_detail_time_start.setText(Constant.stmpToDate(appOrderCheckOrderOrdertitle.executeStartTime));
                            order_detail_time_end.setText(Constant.stmpToDate(appOrderCheckOrderOrdertitle.executeEndTime));
                            order_detail_product_pay.setText(appOrderCheckOrderOrdertitle.totalMoney + "");
                            buy_id = appOrderCheckOrderOrdertitle.buyersId;
                            if (state == 2) {
                                if (buy_id.equalsIgnoreCase(publicArg.getSys_member())) {
                                    state = 1;
                                } else {
                                    state = 0;
                                }
                                getQueryMmbBankAccountGainDatas();
                                getQueryMmbWareHouseGainDatas();
                            }

                            buy_name = appOrderCheckOrderOrdertitle.buyersName;
                            sell_id = appOrderCheckOrderOrdertitle.sellersId;
                            sell_name = appOrderCheckOrderOrdertitle.sellersName;
                            orders = new ArrayList<OrderShellModel>();
                            if (state == 1) {
                                //采购
                                String[] get_accounts = new String[]{appOrderCheckOrderOrdertitle.getBank + "\n" + appOrderCheckOrderOrdertitle.getAccount};
                                String[] get_address = new String[]{appOrderCheckOrderOrdertitle.sellersAddressName};
                                order_detail_get_account.setAdapter(new TestArrayAdapter(getApplicationContext(), get_accounts));
                                order_detail_get_address.setAdapter(new TestArrayAdapter(getApplicationContext(), get_address));
                                order_detail_get_account.setEnabled(false);
                                order_detail_get_address.setEnabled(false);
                            } else {
                                //销售
                                String[] pay_accounts = new String[]{appOrderCheckOrderOrdertitle.payBank + "\n" + appOrderCheckOrderOrdertitle.payAccount};
                                String[] pay_address = new String[]{appOrderCheckOrderOrdertitle.buyersAddressName};
                                order_detail_pay_account.setAdapter(new TestArrayAdapter(getApplicationContext(), pay_accounts));
                                order_detail_pay_address.setAdapter(new TestArrayAdapter(getApplicationContext(), pay_address));
                                order_detail_pay_account.setEnabled(false);
                                order_detail_pay_address.setEnabled(false);
                            }
                            for (int i = 0; i < listListListListListListAppOrderCheckOrderRespon.ordertitle.orderList.size(); i++) {
                                OrdertitleData ordertitleData = appOrderCheckOrderOrdertitle.orderList.get(i);
                                OrderShellModel orderShellModel = new OrderShellModel();
                                orderShellModel.setGoodsId(ordertitleData.goodsId);
                                orderShellModel.setCategoryId(ordertitleData.categoryId);
                                orderShellModel.setName(ordertitleData.goodsName);
                                orderShellModel.setQuoteId(ordertitleData.quoteId);
                                orderShellModel.setPrice("0-1000000000000000000");
                                orderShellModel.setFinalprice(String.valueOf(ordertitleData.price));
                                orderShellModel.setAllprice(String.valueOf(ordertitleData.money));
                                orderShellModel.setNum((int) ordertitleData.goodsNum);
                                orders.add(orderShellModel);

                            }
                            order_detail_num.setText(String.valueOf(orders.size()));
                            adapter.updateRes(orders);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    //搜索订单报价，最高价和最低价的区间
    private void searchPrice(final OrderShellModel orderShellModel) {
        final String[] result = {""};
        PublicArg p = Constant.publicArg;
        RequestProtocal r = new RequestProtocal(p.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC10110041,
                p.getSys_user(),
                p.getSys_member(),
                orderShellModel.getGoodsId());
        String param = mGson.toJson(r);
        OkGo.post(Constant.LookGoodUrl)
                .params("param", param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogShow(s);
                        GoodsBack databack = mGson.fromJson(s, GoodsBack.class);
                        if (databack == null) {
                            return;
                        }

                        LogShow(databack.toString());

                        if (databack.getReturn_code() != 0) {
                            Toast.makeText(context, databack.getReturn_message(), Toast.LENGTH_SHORT).show();
                        } else {
                            result[0] = databack.getGoodsInfo().getMinPrice() + "-" + databack.getGoodsInfo().getMaxPrice();
                            orderShellModel.setPrice(result[0]);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    private void initView() {
        setTitle(getIntent().getStringExtra("title"));
        order_detail_time_pay = (EditText) findViewById(R.id.order_detail_time_pay);
        order_detail_time_pay.setOnClickListener(this);
        order_detail_process = (Spinner) findViewById(R.id.order_detail_process);
        order_detail_time_start = (EditText) findViewById(R.id.order_detail_time_start);
        order_detail_time_start.setOnClickListener(this);
        order_detail_time_end = (EditText) findViewById(R.id.order_detail_time_end);
        order_detail_time_end.setOnClickListener(this);
        order_detail_pay_account = (Spinner) findViewById(R.id.order_detail_pay_account);
        order_detail_pay_address = (Spinner) findViewById(R.id.order_detail_pay_address);
        order_detail_get_account = (Spinner) findViewById(R.id.order_detail_get_account);
        order_detail_get_address = (Spinner) findViewById(R.id.order_detail_get_address);
        order_detail_add_layout = (MyListView) findViewById(R.id.order_detail_add_layout);
        order_detail_num = (TextView) findViewById(R.id.order_detail_num);
        if (type == 0) {
            order_detail_num.setText(String.valueOf(orders.size()));
        }
        order_detail_product_pay = (TextView) findViewById(R.id.order_detail_product_pay);
        adapter = new OrderShellDetailAdapter(orders, this, R.layout.item_order_detail);
        order_detail_add_layout.setAdapter(adapter);
        order_detail_submit = (Button) findViewById(R.id.order_detail_submit);
        order_detail_submit.setOnClickListener(this);
        order_detail_process.setAdapter(stringArrayAdapter3);

    }

    //刷新报价结果
    public void refreshAllPrice() {
        allPrice = 0.0;
        for (OrderShellModel order : orders) {
            allPrice += Double.parseDouble(order.getAllprice());
        }
        order_detail_product_pay.setText(allPrice + "");
    }

    //已经订单
    private void submit() {
        refreshAllPrice();
        if (allPrice <= 0) {
            Toast.makeText(this, "总价为零，不能下单", Toast.LENGTH_SHORT).show();
            return;
        }
        // validate
        String pay = order_detail_time_pay.getText().toString().trim();
        if (TextUtils.isEmpty(pay)) {
            Toast.makeText(this, "付款时间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String start = order_detail_time_start.getText().toString().trim();
        if (TextUtils.isEmpty(start)) {
            Toast.makeText(this, "开始时间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String end = order_detail_time_end.getText().toString().trim();
        if (TextUtils.isEmpty(end)) {
            Toast.makeText(this, "结束时间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

        CreateOrder post = null;
        List<CreateOrder.OrderListBean> listBeen = new ArrayList<>();
        String url_get = "";
        if (type == 1) {
            url_get = Constant.AppOrderEditOrderUrl;
            for (int i = 0; i < orders.size(); i++) {
                CreateOrder.OrderListBean orderListBean = new CreateOrder.OrderListBean(
                        buy_id, buy_name, sell_id,
                        sell_name,
                        orders.get(i).getCategoryId(),
                        orders.get(i).getGoodsId(),
                        orders.get(i).getName(),
                        String.valueOf(orders.get(i).getNum()),
                        orders.get(i).getAllprice(),
                        orders.get(i).getFinalprice(),
                        orders.get(i).getQuoteId(), id);
                listBeen.add(orderListBean);
            }
            post = new CreateOrder(publicArg.getSys_token(),
                    getUUID(), SYS_FUNC101100410002, publicArg.getSys_user(),
                    publicArg.getSys_name(), publicArg.getSys_member(), "", "",
                    buy_id, buy_name, end, start,
//                    mList.get(order_detail_get_account.getSelectedItemPosition()).getAccountno(),
//                    mList.get(order_detail_get_account.getSelectedItemPosition()).getBankname(),
                    StringUtils.isStrTrue(mList.get(order_detail_get_account.getSelectedItemPosition()).getAccountno())
                            ? "" : mList.get(order_detail_get_account.getSelectedItemPosition()).getAccountno(),

                    StringUtils.isStrTrue(mList.get(order_detail_get_account.getSelectedItemPosition()).getBankname())
                            ? "" : mList.get(order_detail_get_account.getSelectedItemPosition()).getBankname(),
                    "", "", pay,
                    StringUtils.isStrTrue(mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getId())
                            ? "" : mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getId(),
                    StringUtils.isStrTrue(mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getAddress())
                            ? "" : mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getAddress(),
                    sell_id, sell_name, String.valueOf(allPrice),
                    String.valueOf(order_detail_process.getSelectedItemPosition() + 1), listBeen, id);
        } else {
            url_get = Constant.CreateOrderUrl;
            switch (state) {
                case 0://销售拣单车
                    for (int i = 0; i < orders.size(); i++) {
                        CreateOrder.OrderListBean orderListBean = new CreateOrder.OrderListBean(buy_id, buy_name,
                                publicArg.getSys_member(),
                                publicArg.getSys_mmbname(),
                                orders.get(i).getCategoryId(),
                                orders.get(i).getGoodsId(),
                                orders.get(i).getName(),
                                String.valueOf(orders.get(i).getNum()),
                                orders.get(i).getAllprice(),
                                orders.get(i).getFinalprice(),
                                orders.get(i).getQuoteId());
                        listBeen.add(orderListBean);
                    }
                    post = new CreateOrder(publicArg.getSys_token(),
                            getUUID(),
                            SYS_FUNC101100410002,
                            publicArg.getSys_user(),
                            publicArg.getSys_name(),
                            publicArg.getSys_member(),
                            "", "", buy_id, buy_name,
                            end, start,
                            StringUtils.isStrTrue(mList.get(order_detail_get_account.getSelectedItemPosition()).getAccountno())
                                    ? "" : mList.get(order_detail_get_account.getSelectedItemPosition()).getAccountno(),

                            StringUtils.isStrTrue(mList.get(order_detail_get_account.getSelectedItemPosition()).getBankname())
                                    ? "" : mList.get(order_detail_get_account.getSelectedItemPosition()).getBankname(),
                            "", "", pay,
//                            mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getId(),
//                            mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getAddress(),
                            StringUtils.isStrTrue(mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getId())
                                    ? "" : mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getId(),
                            StringUtils.isStrTrue(mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getAddress())
                                    ? "" : mAddressList.get(order_detail_get_address.getSelectedItemPosition()).getAddress(),
                            publicArg.getSys_member(),
                            publicArg.getSys_mmbname(),
                            String.valueOf(allPrice),
                            String.valueOf(order_detail_process.getSelectedItemPosition() + 1),
                            listBeen);
                    break;
                case 1://采购拣单车
                    for (int i = 0; i < orders.size(); i++) {
                        CreateOrder.OrderListBean orderListBean = new CreateOrder.OrderListBean(
                                publicArg.getSys_member(),
                                publicArg.getSys_mmbname(),
                                buy_id, buy_name,
                                orders.get(i).getCategoryId(),
                                orders.get(i).getGoodsId(),
                                orders.get(i).getName(),
                                String.valueOf(orders.get(i).getNum()),
                                orders.get(i).getAllprice(),
                                orders.get(i).getFinalprice(),
                                orders.get(i).getQuoteId());
                        listBeen.add(orderListBean);
                    }
                    post = new CreateOrder(publicArg.getSys_token(),
                            getUUID(),
                            SYS_FUNC101100410002,
                            publicArg.getSys_user(),
                            publicArg.getSys_name(),
                            publicArg.getSys_member(),
//                            mAddressList.get(order_detail_pay_address.getSelectedItemPosition()).getId(),
//                            mAddressList.get(order_detail_pay_address.getSelectedItemPosition()).getAddress(),
                            StringUtils.isStrTrue(mAddressList.get(order_detail_pay_address.getSelectedItemPosition()).getId())
                                    ? "" : mAddressList.get(order_detail_pay_address.getSelectedItemPosition()).getId(),
                            StringUtils.isStrTrue(mAddressList.get(order_detail_pay_address.getSelectedItemPosition()).getAddress())
                                    ? "" : mAddressList.get(order_detail_pay_address.getSelectedItemPosition()).getAddress(),
                            publicArg.getSys_member(),
                            publicArg.getSys_mmbname(),
                            end, start, "", "",
//                            mList.get(order_detail_pay_account.getSelectedItemPosition()).getAccountno(),
//                            mList.get(order_detail_pay_account.getSelectedItemPosition()).getBankname(),
                            mList.get(order_detail_pay_account.getSelectedItemPosition()).getAccountno() == null
                                    ? "" : mList.get(order_detail_pay_account.getSelectedItemPosition()).getAccountno(),

                            mList.get(order_detail_pay_account.getSelectedItemPosition()).getBankname() == null
                                    ? "" : mList.get(order_detail_pay_account.getSelectedItemPosition()).getBankname(),
                            pay, "", "",
                            buy_id, buy_name, String.valueOf(allPrice),
                            String.valueOf(order_detail_process.getSelectedItemPosition() + 1),
                            listBeen);
                    break;
            }

        }

        LogShow(mGson.toJson(post));
        OkGo.post(url_get)
                .params("param", mGson.toJson(post))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("back", s);
                        OrderBack orderBack = mGson.fromJson(s, OrderBack.class);
                        if (orderBack == null) {
                            return;
                        }
                        if (orderBack.getReturn_code() != 0) {
                            toast(orderBack.getReturn_message());
                        } else {
                            toast("订单提交成功");
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_detail_time_pay:
                showCalendar(order_detail_time_pay);
                break;
            case R.id.order_detail_time_start:
                showCalendar(order_detail_time_start);
                break;
            case R.id.order_detail_time_end:
                showCalendar(order_detail_time_end);
                break;
            case R.id.order_detail_submit:
                submit();
                break;
        }
    }

    /**
     * 获取银行账户列表
     */
    private void getQueryMmbBankAccountGainDatas() {
        PublicArg p = Constant.publicArg;
        RequestBackAccount r = new RequestBackAccount(p.getSys_token(), Constant.getUUID(), Constant.SYS_FUNC10110041, p.getSys_user(), p.getSys_member(), "1", "30");
        String param = mGson.toJson(r);
        Constant.JSONFATHERRESPON = "QueryMmbBankAccountRespon";
        OkGo.post(Constant.QueryMmbBankAccountUrl)
                .tag(this)
                .params("param", param)
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<List<MmbBankAccountEntity>>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<List<MmbBankAccountEntity>> listQueryMmbBankAccountGain, Call call, Response response) {
                        if (listQueryMmbBankAccountGain.list.size() > 0) {
                            mList = listQueryMmbBankAccountGain.list;
                            bankLists = new String[mList.size()];
                            for (int i = 0; i < mList.size(); i++) {
                                bankLists[i] = mList.get(i).getBankname() + "\n" + mList.get(i).getAccountno();
                            }
                            stringArrayAdapter2 = new TestArrayAdapter(getApplicationContext(), bankLists);
                            switch (state) {
                                case 0://销售拣单车
                                    order_detail_get_account.setAdapter(stringArrayAdapter2);
                                    break;
                                case 1://采购拣单车
                                    order_detail_pay_account.setAdapter(stringArrayAdapter2);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        if (Constant.public_code) {
                            LogoutUtils.exitUser(OrderDetail.this);
                        }

                    }
                });
    }

    /**
     * 获取收发货地址
     */
    private void getQueryMmbWareHouseGainDatas() {
        QueryMmbWareHouseLoading queryMmbWareHouseLoading = new QueryMmbWareHouseLoading(
                publicArg.getSys_token(),
                "00520",
                Constant.SYS_FUNC101100510001,
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
                            addressLists = new String[mAddressList.size()];
                            for (int i = 0; i < mAddressList.size(); i++) {
                                addressLists[i] = mAddressList.get(i).getAddress();
                            }
                            stringArrayAdapter1 = new TestArrayAdapter(getApplicationContext(), addressLists);
                            switch (state) {
                                case 0://销售拣单车
                                    order_detail_get_address.setAdapter(stringArrayAdapter1);
                                    break;
                                case 1://采购拣单车
                                    order_detail_pay_address.setAdapter(stringArrayAdapter1);
                                    break;
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                    }
                });
    }


    private class RequestProtocal {
        private String sys_token;
        private String sys_uuid;
        private String sys_func;
        private String sys_user;
        private String sys_member;
        private String goodsId;

        public RequestProtocal(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String goodsId) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.goodsId = goodsId;
        }
    }

    /**
     * 商品信息解析
     */
    private static class GoodsBack {

        /**
         * goodsImages : ["/v2content/upload/img/newImg/XY/8517870bb05f49debd04c44f1ddb7f97.jpg","/v2content/upload/img/newImg/AH/81277dffeef94b4a96cd925cb8b95144.jpg"]
         * goodsInfo : {"brand":"绿地","categoryId":"1002005001","categoryName":"水产鲜品","createAddress":"广东","createTime":1467216000000,"described":"绿色健康，无污染","factory":"绿的食品有限公司","imgPath":"/v2content/upload/img/newImg/GY/min/0ceb874c454c444cbc756a4939e824eb.jpg","maxPrice":25,"minPrice":20,"name":"罗非鱼","productNum":"","productTime":"7","specification":"1","status":0,"stockNum":2500,"unitPrice":"￥","unitSpecification":"1"}
         * return_code : 0
         * return_message : 成功
         */

        private GoodsInfoBean goodsInfo;
        private int return_code;
        private String return_message;
        private List<String> goodsImages;

        public GoodsInfoBean getGoodsInfo() {
            return goodsInfo;
        }

        public void setGoodsInfo(GoodsInfoBean goodsInfo) {
            this.goodsInfo = goodsInfo;
        }

        public int getReturn_code() {
            return return_code;
        }

        public void setReturn_code(int return_code) {
            this.return_code = return_code;
        }

        public String getReturn_message() {
            return return_message;
        }

        public void setReturn_message(String return_message) {
            this.return_message = return_message;
        }

        public List<String> getGoodsImages() {
            return goodsImages;
        }

        public void setGoodsImages(List<String> goodsImages) {
            this.goodsImages = goodsImages;
        }

        public static class GoodsInfoBean {
            /**
             * brand : 绿地
             * categoryId : 1002005001
             * categoryName : 水产鲜品
             * createAddress : 广东
             * createTime : 1467216000000
             * described : 绿色健康，无污染
             * factory : 绿的食品有限公司
             * imgPath : /v2content/upload/img/newImg/GY/min/0ceb874c454c444cbc756a4939e824eb.jpg
             * maxPrice : 25
             * minPrice : 20
             * name : 罗非鱼
             * productNum :
             * productTime : 7
             * specification : 1
             * status : 0
             * stockNum : 2500
             * unitPrice : ￥
             * unitSpecification : 1
             */

            private double maxPrice;
            private double minPrice;

            public double getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(int maxPrice) {
                this.maxPrice = maxPrice;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(int minPrice) {
                this.minPrice = minPrice;
            }

            @Override
            public String toString() {
                return "GoodsInfoBean{" +
                        "maxPrice=" + maxPrice +
                        ", minPrice=" + minPrice +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "GoodsBack{" +
                    "goodsInfo=" + goodsInfo +
                    ", return_code=" + return_code +
                    ", return_message='" + return_message + '\'' +
                    ", goodsImages=" + goodsImages +
                    '}';
        }
    }
}
