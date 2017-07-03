package com.hj.casps.cooperate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBase;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.ordermanager.OrderDetail;
import com.hj.casps.ordermanager.OrderShellModel;
import com.hj.casps.ui.MyGridView;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.GetFlowType;
import static com.hj.casps.common.Constant.GetPayType;
import static com.hj.casps.common.Constant.GetSendsGoodsType;
import static com.hj.casps.common.Constant.PROTOCOL_TYPE_NUM;
import static com.hj.casps.common.Constant.finalGoodLevelEntities;
import static com.hj.casps.common.Constant.getNow;
import static com.hj.casps.common.Constant.getUUID;
import static com.hj.casps.common.Constant.oldGoodLevelEntities;

//创建关系协议
public class CooperateCreate extends ActivityBaseHeader2 implements View.OnClickListener {
    private boolean buy_type;
    private TextView cooperate_create_info;
    private TextView cooperate_type;
    private TextView cooperate_buy_role;
    private TextView cooperate_sell_role;
    private FancyButton cooperate_products_list;
    private MyGridView cooperate_create_gv;
    private TextView cooperate_protocol_object;
    private TextView cooperate_protocol_time;
    private EditText cooperate_protocol_title;
    private TextView cooperate_protocol_valid_time;
    private Spinner cooperate_protocol_settle_rule;
    private Spinner cooperate_protocol_ship_rule;
    private Spinner cooperate_products_transport_mode;
    private Spinner cooperate_products_buy_account;
    private Spinner cooperate_products_ship_address;
    private Spinner cooperate_products_receipt_account;
    private Spinner cooperate_products_delivery_address;
    private EditText cooperate_products_remarks;
    private FancyButton cooperate_create_submit;
    private String relation_member;
    private String buztype;
    private String buy_mmb_id;
    private String contract_id;
    private String sell_mmb_id;
    private List<EditBack.DataBean.GetmoneyBanklistBean> mGetBankList;//银行账户
    private List<EditBack.DataBean.PaymoneyBanklistBean> mPayBankList;//银行账户
    private List<DataBack.BanklistBean> mBankList;//银行账户
    private List<EditBack.DataBean.GetgoodsAddresslistBean> mGetAddressList;//地址
    private List<EditBack.DataBean.SendgoodsAddresslistBean> mSendAddressList;//地址
    private List<DataBack.AddresslistBean> mAddressList;//地址
    private String[] addressLists;
    private String[] addressLists2;
    private String[] bankLists;
    private String[] bankLists2;
    private String bankListsAccount2;
    private String bankListsAccount;
    private String[] paytypes;
    private String[] flowtypes;
    private String[] sendgoodstypes;
    private TestArrayAdapter stringArrayAdapter1;
    private TestArrayAdapter stringArrayAdapter2;
    private TestArrayAdapter stringArrayAdapter3;
    private TestArrayAdapter stringArrayAdapter4;
    private TestArrayAdapter stringArrayAdapter5;
    private TestArrayAdapter stringArrayAdapter6;
    private TestArrayAdapter stringArrayAdapter7;
    private ProductsAdapter adapter;
    private TextView cooperate_protocol_valid_time_to;
    private LinearLayout products_buy_account;
    private LinearLayout products_ship_address;
    private LinearLayout products_receipt_account;
    private LinearLayout products_delivery_address;
    private int type;
    private boolean isFirst;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isFirst = false;
//                    initData(grade);
                    if (finalGoodLevelEntities.size() == 0) {
                        adapter.removeAll();
                    } else {
                        adapter.updateRes(finalGoodLevelEntities);
                    }

                    oldGoodLevelEntities.clear();
                    break;

            }
        }
    };


    //选择商品类型后的返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == 22) {
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate_create);
        initData();
        initView();
    }

    //加载该关系下的协议数据
    private void initData() {
        isFirst = true;
        finalGoodLevelEntities = new ArrayList<>();
        finalGoodLevelEntities.clear();
        Constant.oldGoodLevelEntities = new ArrayList<>();
        oldGoodLevelEntities.clear();
        buy_type = getIntent().getBooleanExtra("buy_type", true);
        type = getIntent().getIntExtra("type", 0);
        relation_member = getIntent().getStringExtra("relation_member");
        contract_id = getIntent().getStringExtra("contract_id");
//        intent.putExtra("contract_id", protocolModel.getContract_id());
//        intent.putExtra("contract_status", String.valueOf(protocolModel.getContract_status()));
//        intent.putExtra("contract_type", String.valueOf(protocol_type_j));

        paytypes = new String[]{GetPayType(0), GetPayType(1), GetPayType(2), GetPayType(3), GetPayType(4)};
        flowtypes = new String[]{GetFlowType(0), GetFlowType(1)};
        sendgoodstypes = new String[]{GetSendsGoodsType(0), GetSendsGoodsType(1), GetSendsGoodsType(2)};
        stringArrayAdapter3 = new TestArrayAdapter(getApplicationContext(), paytypes);
        stringArrayAdapter5 = new TestArrayAdapter(getApplicationContext(), flowtypes);
        stringArrayAdapter4 = new TestArrayAdapter(getApplicationContext(), sendgoodstypes);
        switch (type) {
            case 0:
                createData();
                break;
            case 1:
                editData(Constant.ToUpdateContractPageUrl, true);
                break;
            case 2:
                editData(Constant.ToCreateOrderUrl, false);
                break;
        }

    }

    //销毁协议需要清理商品
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finalGoodLevelEntities.clear();
        oldGoodLevelEntities.clear();
    }

    //编辑协议和提交
    private void editData(String url, final boolean b) {
        CreateData data = new CreateData(
                publicArg.getSys_token(),
                getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                contract_id,
                buy_type ? "1" : "2",
                "");
        OkGo.post(url)
                .params("param", mGson.toJson(data))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        EditBack databack = mGson.fromJson(s, EditBack.class);
                        if (databack.getReturn_code() != 0) {
                            Toast.makeText(context, databack.getReturn_message(), Toast.LENGTH_SHORT).show();
                        } else if (databack.getReturn_code() == 1101 || databack.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(CooperateCreate.this);
                        } else {
                            buy_mmb_id = databack.getData().getBuy_mmb_id();
                            sell_mmb_id = databack.getData().getSell_mmb_id();
                            cooperate_buy_role.setText(databack.getData().getBuy_membername());
                            cooperate_sell_role.setText(databack.getData().getSell_membername());
                            if (b && databack.getData().getGoods() != null) {
                                for (int i = 0; i < databack.getData().getGoods().size(); i++) {
                                    ProtocalProductItem.GoodLevelEntity goodLevelEntity = new ProtocalProductItem.GoodLevelEntity(databack.getData().getGoods().get(i).getCategoryName(), databack.getData().getGoods().get(i).getCtgId());
                                    goodLevelEntity.setChoose(true);
                                    finalGoodLevelEntities.add(goodLevelEntity);

                                }
                                adapter.notifyDataSetChanged();
                            }

                            cooperate_protocol_title.setText(databack.getData().getContract_title());
                            cooperate_protocol_title.setEnabled(b);
                            cooperate_protocol_valid_time.setText(databack.getData().getUser_time().split(" - ")[0]);
                            cooperate_protocol_valid_time.setEnabled(b);
                            cooperate_protocol_valid_time_to.setText(databack.getData().getUser_time().split(" - ")[1]);
                            cooperate_protocol_valid_time_to.setEnabled(b);
                            cooperate_protocol_settle_rule.setSelection(databack.getData().getPay_type());
                            cooperate_protocol_settle_rule.setEnabled(b);
                            cooperate_protocol_ship_rule.setSelection(databack.getData().getFlow_type());
                            cooperate_protocol_ship_rule.setEnabled(b);
                            cooperate_products_transport_mode.setSelection(databack.getData().getSendgoods_type());
                            cooperate_products_transport_mode.setEnabled(b);
                            cooperate_products_remarks.setText(databack.getData().getNote());
                            cooperate_products_remarks.setEnabled(b);
                            if (buy_type) {
                                if (b && databack.getData().getPaymoney_banklist() != null) {
                                    mPayBankList = databack.getData().getPaymoney_banklist();
                                    bankLists = new String[mPayBankList.size()];
                                    for (int i = 0; i < mPayBankList.size(); i++) {
                                        bankLists[i] = mPayBankList.get(i).getBankname();
                                    }

                                } else {
                                    bankLists = new String[]{databack.getData().getPayer_name()};
                                    bankListsAccount = databack.getData().getPayer_code();
                                }
                                stringArrayAdapter2 = new TestArrayAdapter(getApplicationContext(), bankLists);

                                bankLists2 = new String[]{databack.getData().getGetmoney_name()};
                                bankListsAccount2 = databack.getData().getGetmoney_code();
                                stringArrayAdapter6 = new TestArrayAdapter(getApplicationContext(), bankLists2);
                                if (b && databack.getData().getGetgoods_addresslist() != null) {
                                    mGetAddressList = databack.getData().getGetgoods_addresslist();
                                    addressLists = new String[mGetAddressList.size()];
                                    for (int i = 0; i < mGetAddressList.size(); i++) {
                                        addressLists[i] = mGetAddressList.get(i).getAddress();
                                    }
                                } else {
                                    addressLists = new String[]{databack.getData().getGetgoods_address()};
                                }
                                stringArrayAdapter1 = new TestArrayAdapter(getApplicationContext(), addressLists);
                                addressLists2 = new String[]{databack.getData().getSendgoods_address()};
                                stringArrayAdapter7 = new TestArrayAdapter(getApplicationContext(), addressLists2);
                                cooperate_products_buy_account.setAdapter(stringArrayAdapter2);
                                cooperate_products_buy_account.setEnabled(b);
                                cooperate_products_ship_address.setAdapter(stringArrayAdapter1);
                                cooperate_products_ship_address.setEnabled(b);
                                cooperate_products_receipt_account.setAdapter(stringArrayAdapter6);
                                cooperate_products_receipt_account.setEnabled(false);
                                cooperate_products_delivery_address.setAdapter(stringArrayAdapter7);
                                cooperate_products_delivery_address.setEnabled(false);
                            } else {
                                if (databack.getData().getGetmoney_banklist() != null) {
                                    mGetBankList = databack.getData().getGetmoney_banklist();
                                    bankLists = new String[mGetBankList.size()];
                                    for (int i = 0; i < mGetBankList.size(); i++) {
                                        bankLists[i] = mGetBankList.get(i).getBankname();
                                    }

                                } else {
                                    bankLists = new String[]{databack.getData().getGetmoney_name()};
                                    bankListsAccount = databack.getData().getGetmoney_code();
                                }
                                stringArrayAdapter2 = new TestArrayAdapter(getApplicationContext(), bankLists);

                                bankLists2 = new String[]{databack.getData().getPayer_name()};
                                bankListsAccount2 = databack.getData().getPayer_code();
                                stringArrayAdapter6 = new TestArrayAdapter(getApplicationContext(), bankLists2);
                                if (b && databack.getData().getSendgoods_addresslist() != null) {
                                    mSendAddressList = databack.getData().getSendgoods_addresslist();
                                    addressLists = new String[mSendAddressList.size()];
                                    for (int i = 0; i < mSendAddressList.size(); i++) {
                                        addressLists[i] = mSendAddressList.get(i).getAddress();
                                    }

                                } else {
                                    addressLists = new String[]{databack.getData().getSendgoods_address()};
                                }
                                stringArrayAdapter1 = new TestArrayAdapter(getApplicationContext(), addressLists);

                                addressLists2 = new String[]{databack.getData().getGetgoods_address()};
                                stringArrayAdapter7 = new TestArrayAdapter(getApplicationContext(), addressLists2);

                                cooperate_products_receipt_account.setAdapter(stringArrayAdapter2);
                                cooperate_products_receipt_account.setEnabled(b);
                                cooperate_products_delivery_address.setAdapter(stringArrayAdapter1);
                                cooperate_products_delivery_address.setEnabled(b);
                                cooperate_products_buy_account.setAdapter(stringArrayAdapter6);
                                cooperate_products_buy_account.setEnabled(false);
                                cooperate_products_ship_address.setAdapter(stringArrayAdapter7);
                                cooperate_products_ship_address.setEnabled(false);
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    /**
     * 创建合作协议
     */
    private void createData() {
        CreateData data = new CreateData(
                Constant.publicArg.getSys_token(),
                getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                relation_member,
                buy_type ? "1" : "2");
        LogShow("创建合作协议：" + mGson.toJson(data));
        OkGo.post(Constant.toAddContractPageUrl)
                .params("param", mGson.toJson(data))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DataBack databack = mGson.fromJson(s, DataBack.class);
                        if (databack.getReturn_code() != 0) {
                            Toast.makeText(context, databack.getReturn_message(), Toast.LENGTH_SHORT).show();
                        } else if (databack.getReturn_code() == 1101 || databack.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(CooperateCreate.this);
                        } else {
                            buy_mmb_id = databack.getBuy_mmb_id();
                            sell_mmb_id = databack.getSell_mmb_id();
                            setView(databack);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //设置协议布局界面
    private void setView(DataBack databack) {
        cooperate_buy_role.setText(databack.getBuy_membername());
        cooperate_sell_role.setText(databack.getSell_membername());
        if (databack.getBanklist() != null) {
            mBankList = databack.getBanklist();
            bankLists = new String[mBankList.size()];
            for (int i = 0; i < mBankList.size(); i++) {
                bankLists[i] = mBankList.get(i).getBankname();
            }
            stringArrayAdapter2 = new TestArrayAdapter(getApplicationContext(), bankLists);

        }
        if (databack.getAddresslist() != null) {
            mAddressList = databack.getAddresslist();
            addressLists = new String[mAddressList.size()];
            for (int i = 0; i < mAddressList.size(); i++) {
                addressLists[i] = mAddressList.get(i).getAddress();
            }
            stringArrayAdapter1 = new TestArrayAdapter(getApplicationContext(), addressLists);

        }
        if (buy_type) {
            cooperate_products_buy_account.setAdapter(stringArrayAdapter2);
            cooperate_products_ship_address.setAdapter(stringArrayAdapter1);
        } else {
            cooperate_products_receipt_account.setAdapter(stringArrayAdapter2);
            cooperate_products_delivery_address.setAdapter(stringArrayAdapter1);
        }
    }

    //新建协议的布局
    private void initView() {
        cooperate_create_info = (TextView) findViewById(R.id.cooperate_create_info);
        cooperate_create_info.setOnClickListener(this);
        cooperate_type = (TextView) findViewById(R.id.cooperate_type);
        cooperate_buy_role = (TextView) findViewById(R.id.cooperate_buy_role);
        cooperate_sell_role = (TextView) findViewById(R.id.cooperate_sell_role);
        cooperate_products_list = (FancyButton) findViewById(R.id.cooperate_products_list);
        cooperate_products_list.setOnClickListener(this);
        cooperate_create_gv = (MyGridView) findViewById(R.id.cooperate_create_gv);
        adapter = new ProductsAdapter(finalGoodLevelEntities, this, R.layout.goods_item);
        cooperate_create_gv.setAdapter(adapter);
        cooperate_protocol_object = (TextView) findViewById(R.id.cooperate_protocol_object);
        cooperate_protocol_object.setText(publicArg.getSys_username());
        cooperate_protocol_object.setEnabled(false);
        cooperate_protocol_time = (TextView) findViewById(R.id.cooperate_protocol_time);
        cooperate_protocol_time.setText(getNow());
        cooperate_protocol_time.setEnabled(false);
        cooperate_protocol_title = (EditText) findViewById(R.id.cooperate_protocol_title);
        cooperate_protocol_valid_time = (TextView) findViewById(R.id.cooperate_protocol_valid_time);
        cooperate_protocol_valid_time.setOnClickListener(this);
        cooperate_protocol_valid_time_to = (TextView) findViewById(R.id.cooperate_protocol_valid_time_to);
        cooperate_protocol_valid_time_to.setOnClickListener(this);
        cooperate_protocol_settle_rule = (Spinner) findViewById(R.id.cooperate_protocol_settle_rule);
        cooperate_protocol_ship_rule = (Spinner) findViewById(R.id.cooperate_protocol_ship_rule);
        cooperate_products_transport_mode = (Spinner) findViewById(R.id.cooperate_products_transport_mode);
        cooperate_protocol_settle_rule.setAdapter(stringArrayAdapter3);
        cooperate_protocol_ship_rule.setAdapter(stringArrayAdapter4);
        cooperate_products_transport_mode.setAdapter(stringArrayAdapter5);
        cooperate_products_buy_account = (Spinner) findViewById(R.id.cooperate_products_buy_account);
        cooperate_products_ship_address = (Spinner) findViewById(R.id.cooperate_products_ship_address);
        cooperate_products_receipt_account = (Spinner) findViewById(R.id.cooperate_products_receipt_account);
        cooperate_products_delivery_address = (Spinner) findViewById(R.id.cooperate_products_delivery_address);
        cooperate_products_remarks = (EditText) findViewById(R.id.cooperate_products_remarks);
        cooperate_create_submit = (FancyButton) findViewById(R.id.cooperate_create_submit);
        cooperate_create_submit.setOnClickListener(this);
        products_buy_account = (LinearLayout) findViewById(R.id.products_buy_account);
        products_ship_address = (LinearLayout) findViewById(R.id.products_ship_address);
        products_receipt_account = (LinearLayout) findViewById(R.id.products_receipt_account);
        products_delivery_address = (LinearLayout) findViewById(R.id.products_delivery_address);

        switch (type) {
            case 0:
                if (buy_type) {
                    cooperate_type.setText(R.string.cooperate_products_buy);
                    products_receipt_account.setVisibility(View.GONE);
                    products_delivery_address.setVisibility(View.GONE);
                } else {
                    cooperate_type.setText(R.string.cooperate_products_sell);
                    products_buy_account.setVisibility(View.GONE);
                    products_ship_address.setVisibility(View.GONE);
                }
                setTitle(getString(R.string.cooperate_create_protocol));
                cooperate_products_list.setText(getString(R.string.cooperate_products_choose_type1));
                break;
            case 1:
                if (buy_type) {
                    cooperate_type.setText(R.string.cooperate_products_buy);
                } else {
                    cooperate_type.setText(R.string.cooperate_products_sell);
                }
                setTitle(getString(R.string.cooperate_edit_protocol));
                cooperate_products_list.setText(getString(R.string.cooperate_products_choose_type1));
                break;
            case 2:
                if (buy_type) {
                    cooperate_type.setText(R.string.cooperate_products_buy);
                    products_receipt_account.setVisibility(View.GONE);
                    products_delivery_address.setVisibility(View.GONE);
                } else {
                    cooperate_type.setText(R.string.cooperate_products_sell);
                    products_buy_account.setVisibility(View.GONE);
                    products_ship_address.setVisibility(View.GONE);
                }
                setTitle(getString(R.string.protocol_create_order));
                cooperate_create_submit.setText(getString(R.string.protocol_get_order));
                cooperate_products_list.setText(getString(R.string.cooperate_products_choose_type2));
                break;
        }


    }

    //协议商品的适配器
    private class ProductsAdapter extends WZYBaseAdapter<ProtocalProductItem.GoodLevelEntity> {
        private ProductsAdapter(List<ProtocalProductItem.GoodLevelEntity> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
        }

        @Override
        public void bindData(WZYBaseAdapter.ViewHolder holder, final ProtocalProductItem.GoodLevelEntity goodLevelEntity, final int indexPos) {
            TextView name = (TextView) holder.getView(R.id.goods_item_name);
            ImageView close = (ImageView) holder.getView(R.id.close_goods);
            name.setText(goodLevelEntity.getName());
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalGoodLevelEntities.remove(goodLevelEntity);
                    if (finalGoodLevelEntities.size() == 0) {
                        adapter.removeAll();
                    } else {
                        if (isFirst){
                            adapter.notifyDataSetChanged();
                        }else {
                            adapter.updateRes(finalGoodLevelEntities);

                        }


                    }
//                    refreshAdapter();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cooperate_create_info:
                switch (type) {
                    case 0:
                        if (buy_type) {
                            CreateDialog(Constant.DIALOG_CONTENT_19);
                        } else {
                            CreateDialog(Constant.DIALOG_CONTENT_18);
                        }
                        break;
                    case 1:
                        CreateDialog(Constant.DIALOG_CONTENT_22);
                        break;
                    case 2:
                        CreateDialog(Constant.DIALOG_CONTENT_21);
                        break;
                }

                break;
            case R.id.cooperate_products_list:
                oldGoodLevelEntities.addAll(finalGoodLevelEntities);
//                if (type == 2) {
//                    bundle.putBoolean("goods", true);
//                    bundle.putString("ctrId", contract_id);
//                    bundle.putString("sellMmbId", sell_mmb_id);
//                }
                if (type == 0 || type == 1) {
                    bundle.putBoolean("goods", false);
                    bundle.putString("sellMmbId", sell_mmb_id);
                } else {
                    bundle.putBoolean("goods", true);
                    bundle.putString("ctrId", contract_id);
                    bundle.putString("sellMmbId", sell_mmb_id);
                }
                intentActivity(ProtocalProductItem.class, 11, bundle);
                break;
            case R.id.cooperate_create_submit:
                submit();
                break;
            case R.id.cooperate_protocol_valid_time:
                showCalendar(cooperate_protocol_valid_time);
                break;
            case R.id.cooperate_protocol_valid_time_to:
                showCalendar(cooperate_protocol_valid_time_to);
                break;
        }
    }

    //新建协议的提交
    private void submit() {
        // validate
        String title = getEdVaule(cooperate_protocol_title);
        String note = getEdVaule(cooperate_products_remarks);
        String user_time = getTvVaule(cooperate_protocol_valid_time) + " - " +
                getTvVaule(cooperate_protocol_valid_time_to);
        if (!Constant.judgeDate(getTvVaule(cooperate_protocol_valid_time), getTvVaule(cooperate_protocol_valid_time_to))) {
            toast("开始时间不能大于结束时间");
            return;
        }
        if (TextUtils.isEmpty(title)) {
            toastSHORT("标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(getTvVaule(cooperate_protocol_valid_time))
                || TextUtils.isEmpty(getTvVaule(cooperate_protocol_valid_time_to))) {
            toastSHORT("有效时间不能为空");
            return;
        }
        String goods = "";
        for (int i = 0; i < finalGoodLevelEntities.size(); i++) {
            if (i == 0) {
                goods = finalGoodLevelEntities.get(i).getCategoryId();
            } else {
                goods = goods + "," + finalGoodLevelEntities.get(i).getCategoryId();
            }
        }
        if (TextUtils.isEmpty(goods)) {
            toastSHORT("商品品类不能为空");
            return;
        }
        if (buy_type) {
            if (cooperate_products_buy_account.getSelectedItemPosition() == -1) {
                toast("付款账号不能为空");
                return;
            }
            if (cooperate_products_ship_address.getSelectedItemPosition() == -1) {
                toast("收货地址不能为空");
                return;
            }
        } else {
            if (cooperate_products_receipt_account.getSelectedItemPosition() == -1) {
                toast("收款账号不能为空");
                return;
            }
            if (cooperate_products_delivery_address.getSelectedItemPosition() == -1) {
                toast("发货地址不能为空");
                return;
            }
        }

        CreateData data = null;
        switch (type) {
            //创建合作协议
            case 0:
                if (buy_type) {

                    data = new CreateData(
                            Constant.publicArg.getSys_token(),
                            getUUID(),
                            Constant.SYS_FUNC,
                            Constant.publicArg.getSys_user(),
                            Constant.publicArg.getSys_member(),
                            goods,
                            buy_mmb_id, sell_mmb_id,
                            title,
                            user_time,
                            String.valueOf(cooperate_protocol_settle_rule.getSelectedItemPosition()),
                            String.valueOf(cooperate_protocol_ship_rule.getSelectedItemPosition()),
                            String.valueOf(cooperate_products_transport_mode.getSelectedItemPosition()),
                            cooperate_products_buy_account.getSelectedItemPosition() == -1 ? "" : mBankList.get(cooperate_products_buy_account.getSelectedItemPosition()).getAccountno(),
                            cooperate_products_buy_account.getSelectedItemPosition() == -1 ? "" : mBankList.get(cooperate_products_buy_account.getSelectedItemPosition()).getBankname(), "", "", "",
                            cooperate_products_ship_address.getSelectedItemPosition() == -1 ? "" : mAddressList.get(cooperate_products_ship_address.getSelectedItemPosition()).getAddress(), note, "1");
                } else {

                    data = new CreateData(
                            Constant.publicArg.getSys_token(),
                            getUUID(),
                            Constant.SYS_FUNC,
                            Constant.publicArg.getSys_user(),
                            Constant.publicArg.getSys_member(),
                            goods,
                            buy_mmb_id, sell_mmb_id,
                            title,
                            user_time,
                            String.valueOf(cooperate_protocol_settle_rule.getSelectedItemPosition()),
                            String.valueOf(cooperate_protocol_ship_rule.getSelectedItemPosition()),
                            String.valueOf(cooperate_products_transport_mode.getSelectedItemPosition()),
                            "",
                            "",
                            cooperate_products_receipt_account.getSelectedItemPosition() == -1 ? "" : mBankList.get(cooperate_products_receipt_account.getSelectedItemPosition()).getAccountno(),
                            cooperate_products_receipt_account.getSelectedItemPosition() == -1 ? "" : mBankList.get(cooperate_products_receipt_account.getSelectedItemPosition()).getBankname(),
                            cooperate_products_delivery_address.getSelectedItemPosition() == -1 ? "" : mAddressList.get(cooperate_products_delivery_address.getSelectedItemPosition()).getAddress(),
                            "",
                            note,
                            "2");
                }
                gotoNet(Constant.CreatCTRUrl, data);
                break;
            //编辑协议
            case 1:
                if (buy_type) {
                    data = new CreateData(
                            Constant.publicArg.getSys_token(),
                            getUUID(),
                            Constant.SYS_FUNC,
                            Constant.publicArg.getSys_user(),
                            Constant.publicArg.getSys_member(),
                            contract_id,
                            "", goods,
                            buy_mmb_id,
                            sell_mmb_id,
                            title,
                            user_time,
                            String.valueOf(cooperate_protocol_settle_rule.getSelectedItemPosition()),
                            String.valueOf(cooperate_protocol_ship_rule.getSelectedItemPosition()),
                            String.valueOf(cooperate_products_transport_mode.getSelectedItemPosition()),
                            cooperate_products_buy_account.getSelectedItemPosition() == -1 ? "" : mPayBankList.get(cooperate_products_buy_account.getSelectedItemPosition()).getAccountno(),
                            cooperate_products_buy_account.getSelectedItemPosition() == -1 ? "" : mPayBankList.get(cooperate_products_buy_account.getSelectedItemPosition()).getBankname(),
                            bankListsAccount2,
                            bankLists2[0],
                            addressLists2[0],
                            cooperate_products_ship_address.getSelectedItemPosition() == -1 ? "" : mGetAddressList.get(cooperate_products_ship_address.getSelectedItemPosition()).getAddress(),
                            note);
                } else {
                    data = new CreateData(Constant.publicArg.getSys_token(),
                            getUUID(),
                            Constant.SYS_FUNC,
                            Constant.publicArg.getSys_user(),
                            Constant.publicArg.getSys_member(),
                            contract_id,
                            "",
                            goods,
                            buy_mmb_id, sell_mmb_id, title,
                            user_time,
                            String.valueOf(cooperate_protocol_settle_rule.getSelectedItemPosition()),
                            String.valueOf(cooperate_protocol_ship_rule.getSelectedItemPosition()),
                            String.valueOf(cooperate_products_transport_mode.getSelectedItemPosition()),
                            bankListsAccount2,
                            bankLists2[0],
                            cooperate_products_receipt_account.getSelectedItemPosition() == -1 ? "" : mGetBankList.get(cooperate_products_receipt_account.getSelectedItemPosition()).getAccountno(),
                            cooperate_products_receipt_account.getSelectedItemPosition() == -1 ? "" : mGetBankList.get(cooperate_products_receipt_account.getSelectedItemPosition()).getBankname(),
                            cooperate_products_delivery_address.getSelectedItemPosition() == -1 ? "" : mSendAddressList.get(cooperate_products_delivery_address.getSelectedItemPosition()).getAddress(),
                            addressLists2[0],
                            note);
                }
                gotoNet(Constant.updateCTRUrl, data);
                break;
            //下订单
            case 2:
                gotoOrder();
                break;
        }


    }

    /**
     * 下订单
     */
    private void gotoOrder() {
        if (buy_type) {
            bundle.putInt("state", 1);
            bundle.putString("buy_id", sell_mmb_id);
            bundle.putString("buy_name", cooperate_sell_role.getText().toString().trim());
        } else {
            bundle.putInt("state", 0);
            bundle.putString("buy_id", buy_mmb_id);
            bundle.putString("buy_name", cooperate_buy_role.getText().toString().trim());


        }
        List<OrderShellModel> orderShellModels = new ArrayList<>();
        for (int i = 0; i < finalGoodLevelEntities.size(); i++) {
            OrderShellModel orderShellModel = new OrderShellModel();
            orderShellModel.setStatus(true);
//            orderShellModel.setStatus(true);
            orderShellModel.setName(finalGoodLevelEntities.get(i).getName());
            orderShellModel.setGoodsId(finalGoodLevelEntities.get(i).getCategoryId());
//            orderShellModels.get(no).setQuoteId(quoteId);
//            orderShellModel.setPrice(se + "-" + maxPrice);
            orderShellModels.add(orderShellModel);

        }
        bundle.putBoolean("OrderList", true);
        bundle.putString("title", getString(R.string.order_detail_product_grid));
        bundle.putString("account", bankListsAccount2);
        bundle.putString("bankname", bankLists2[0]);
        bundle.putString("adress", addressLists2[0]);
        bundle.putParcelableArrayList("orders", (ArrayList<? extends Parcelable>) orderShellModels);
        intentActivity(OrderDetail.class, bundle);
        finish();
    }

    //提交后的交互
    private void gotoNet(String url, CreateData data) {
        waitDialogRectangle.show();
        waitDialogRectangle.setText("正在提交");
        OkGo.post(url)
                .params("param", mGson.toJson(data))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        DataBack databack = mGson.fromJson(s, DataBack.class);
                        if (databack.getReturn_code() != 0) {
                            Toast.makeText(context, databack.getReturn_message(), Toast.LENGTH_SHORT).show();
                        } else if (databack.getReturn_code() == 1101 || databack.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(CooperateCreate.this);
                        } else {
                            toast("提交完成");
                            finalGoodLevelEntities.clear();
                            oldGoodLevelEntities.clear();
//                            PROTOCOL_TYPE_NUM = buy_type ? 0 : 1;
                            setResult(33);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        waitDialogRectangle.dismiss();
                    }
                });
    }


    /**
     * 提交类
     */
    private static class CreateData {
        private String sys_token;
        private String sys_uuid;
        private String sys_func;
        private String sys_user;
        private String sys_member;
        private String contract_id;
        private String contract_type;
        private String goods;
        private String buy_mmb_id;
        private String sell_mmb_id;
        private String contract_title;
        private String user_time;
        private String pay_type;
        private String flow_type;
        private String sendgoods_type;
        private String payer_code;
        private String payer_name;
        private String getmoney_code;
        private String getmoney_name;
        private String sendgoods_address;
        private String getgoods_address;
        private String note;
        private String relation_member;
        private String buztype;

        public CreateData(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String contract_id, String contract_type, String goods, String buy_mmb_id, String sell_mmb_id, String contract_title, String user_time, String pay_type, String flow_type, String sendgoods_type, String payer_code, String payer_name, String getmoney_code, String getmoney_name, String sendgoods_address, String getgoods_address, String note) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.contract_id = contract_id;
            this.contract_type = contract_type;
            this.goods = goods;
            this.buy_mmb_id = buy_mmb_id;
            this.sell_mmb_id = sell_mmb_id;
            this.contract_title = contract_title;
            this.user_time = user_time;
            this.pay_type = pay_type;
            this.flow_type = flow_type;
            this.sendgoods_type = sendgoods_type;
            this.payer_code = payer_code;
            this.payer_name = payer_name;
            this.getmoney_code = getmoney_code;
            this.getmoney_name = getmoney_name;
            this.sendgoods_address = sendgoods_address;
            this.getgoods_address = getgoods_address;
            this.note = note;
        }

        public CreateData(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String contract_id, String contract_type, String goods) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.contract_id = contract_id;
            this.contract_type = contract_type;
            this.goods = goods;
        }

        public CreateData(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String relation_member, String buztype) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.relation_member = relation_member;
            this.buztype = buztype;
        }

        public CreateData(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String goods, String buy_mmb_id, String sell_mmb_id, String contract_title, String user_time, String pay_type, String flow_type, String sendgoods_type, String payer_code, String payer_name, String getmoney_code, String getmoney_name, String sendgoods_address, String getgoods_address, String note, String buztype) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.goods = goods;
            this.buy_mmb_id = buy_mmb_id;
            this.sell_mmb_id = sell_mmb_id;
            this.contract_title = contract_title;
            this.user_time = user_time;
            this.pay_type = pay_type;
            this.flow_type = flow_type;
            this.sendgoods_type = sendgoods_type;
            this.payer_code = payer_code;
            this.payer_name = payer_name;
            this.getmoney_code = getmoney_code;
            this.getmoney_name = getmoney_name;
            this.sendgoods_address = sendgoods_address;
            this.getgoods_address = getgoods_address;
            this.note = note;
            this.buztype = buztype;
        }
    }

    private static class DataBack {

        /**
         * addresslist : [{"address":"北京市朝阳区立水桥5号库","areaId":"1","contact":"王部长","id":"ff3bce69cc094a0ca3488e833c40e5bb","mmbId":"testshop001","mobilephone":"15555555550","phone":"","zipcode":""}]
         * banklist : [{"accountname":"999","accountno":"1314","bankname":"工商银行","contact":"老司机","id":"34f9841f0564491da4860fd1c3461522","mmbId":"testshop001","mobilephone":"","phone":""},{"accountname":"123","accountno":"123","bankname":"","contact":"","id":"53e998565a584a5094654cd65598b3e6","mmbId":"testshop001","mobilephone":"","phone":""},{"accountname":"北京长城商贸中信分户","accountno":"63980011596","bankname":"中信银行","contact":"牛仁","id":"77d696d228f54d7a929c860e7cd3cd72","mmbId":"testshop001","mobilephone":"13901380137","phone":""},{"accountname":"TEST","accountno":"001","bankname":"中国银行","contact":"徐","id":"dca2f44770dd4cccafad81f295b16bec","mmbId":"testshop001","mobilephone":"15255179870","phone":""}]
         * buy_membername : 长城商行
         * buy_mmb_id : testshop001
         * contract_type : 1
         * return_code : 0
         * return_message : 成功!
         * sell_membername : APP测试用企业
         * sell_mmb_id : 46e7ffa51761499e905a10f1a33ec450
         */

        private String buy_membername;
        private String buy_mmb_id;
        private String contract_type;
        private int return_code;
        private String return_message;
        private String sell_membername;
        private String sell_mmb_id;
        private List<AddresslistBean> addresslist;
        private List<BanklistBean> banklist;

        public String getBuy_membername() {
            return buy_membername;
        }

        public void setBuy_membername(String buy_membername) {
            this.buy_membername = buy_membername;
        }

        public String getBuy_mmb_id() {
            return buy_mmb_id;
        }

        public void setBuy_mmb_id(String buy_mmb_id) {
            this.buy_mmb_id = buy_mmb_id;
        }

        public String getContract_type() {
            return contract_type;
        }

        public void setContract_type(String contract_type) {
            this.contract_type = contract_type;
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

        public String getSell_membername() {
            return sell_membername;
        }

        public void setSell_membername(String sell_membername) {
            this.sell_membername = sell_membername;
        }

        public String getSell_mmb_id() {
            return sell_mmb_id;
        }

        public void setSell_mmb_id(String sell_mmb_id) {
            this.sell_mmb_id = sell_mmb_id;
        }

        public List<AddresslistBean> getAddresslist() {
            return addresslist;
        }

        public void setAddresslist(List<AddresslistBean> addresslist) {
            this.addresslist = addresslist;
        }

        public List<BanklistBean> getBanklist() {
            return banklist;
        }

        public void setBanklist(List<BanklistBean> banklist) {
            this.banklist = banklist;
        }

        public static class AddresslistBean {
            /**
             * address : 北京市朝阳区立水桥5号库
             * areaId : 1
             * contact : 王部长
             * id : ff3bce69cc094a0ca3488e833c40e5bb
             * mmbId : testshop001
             * mobilephone : 15555555550
             * phone :
             * zipcode :
             */

            private String address;
            private String areaId;
            private String contact;
            private String id;
            private String mmbId;
            private String mobilephone;
            private String phone;
            private String zipcode;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMmbId() {
                return mmbId;
            }

            public void setMmbId(String mmbId) {
                this.mmbId = mmbId;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }
        }

        public static class BanklistBean {
            /**
             * accountname : 999
             * accountno : 1314
             * bankname : 工商银行
             * contact : 老司机
             * id : 34f9841f0564491da4860fd1c3461522
             * mmbId : testshop001
             * mobilephone :
             * phone :
             */

            private String accountname;
            private String accountno;
            private String bankname;
            private String contact;
            private String id;
            private String mmbId;
            private String mobilephone;
            private String phone;

            public String getAccountname() {
                return accountname;
            }

            public void setAccountname(String accountname) {
                this.accountname = accountname;
            }

            public String getAccountno() {
                return accountno;
            }

            public void setAccountno(String accountno) {
                this.accountno = accountno;
            }

            public String getBankname() {
                return bankname;
            }

            public void setBankname(String bankname) {
                this.bankname = bankname;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMmbId() {
                return mmbId;
            }

            public void setMmbId(String mmbId) {
                this.mmbId = mmbId;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }

    private static class EditBack {

        /**
         * data : {"buy_membername":"奥森学校","buy_mmb_id":"testschool001","contract_title":"rrrrr","contract_type":"2","flow_type":0,"getgoods_address":"","getgoods_addresslist":[{"address":"被禁了","areaId":"21","contact":"","id":"028cdcb30cca49029875e92d6f3222ef","mmbId":"testschool001","mobilephone":"","phone":"","zipcode":""},{"address":"奥森学校教工食堂","areaId":"3","contact":"","id":"adbc73908b5841e3bc61ff12780e9610","mmbId":"testschool001","mobilephone":"","phone":"","zipcode":"100015"},{"address":"奥森学校中心食堂冷库","areaId":"1","contact":"张三","id":"f9a6aaa8380a4ee9a5ad59fe81868361","mmbId":"testschool001","mobilephone":"","phone":"","zipcode":"100015"}],"getmoney_banklist":[{"accountname":"999","accountno":"1314","bankname":"工商银行","contact":"老司机","id":"34f9841f0564491da4860fd1c3461522","mmbId":"testshop001","mobilephone":"","phone":""},{"accountname":"123","accountno":"123","bankname":"","contact":"","id":"53e998565a584a5094654cd65598b3e6","mmbId":"testshop001","mobilephone":"","phone":""},{"accountname":"北京长城商贸中信分户","accountno":"63980011596","bankname":"中信银行","contact":"牛仁","id":"77d696d228f54d7a929c860e7cd3cd72","mmbId":"testshop001","mobilephone":"13901380137","phone":""},{"accountname":"TEST","accountno":"001","bankname":"中国银行","contact":"徐","id":"dca2f44770dd4cccafad81f295b16bec","mmbId":"testshop001","mobilephone":"15255179870","phone":""}],"getmoney_code":"1314","getmoney_name":"工商银行","goods":[{"categoryName":"水产干品","ctgId":"1002005003","ctrId":"4879644f970e40f79d392a975beb6e35","id":"7db7972d8d134ea99ed761afe75ce8a1"}],"note":"","operate_time":1496224794000,"operate_user":"d5faa6bd39ab45f383c20bad5a71abe3","pay_type":0,"payer_code":"undefined","payer_name":"","paymoney_banklist":[{"accountname":"北京市朝阳区奥森学校","accountno":"9953001798001","bankname":"建设银行","contact":"丁丁","id":"0e4161c278a540328a7def34bf6f7696","mmbId":"testschool001","mobilephone":"13501360137","phone":""}],"sell_membername":"长城商行","sell_mmb_id":"testshop001","sendgoods_address":"北京市朝阳区立水桥5号库","sendgoods_addresslist":[{"address":"北京爱德华边塞诗","areaId":"3","contact":"","id":"cac3b44ed35e4c758a6c6c0dda2c35c4","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"北京市朝阳区立水桥5号库","areaId":"1","contact":"王部长","id":"ff3bce69cc094a0ca3488e833c40e5bb","mmbId":"testshop001","mobilephone":"15555555550","phone":"","zipcode":""}],"sendgoods_type":0,"user_time":"2017-05-31 - 2017-05-31"}
         * return_code : 0
         * return_message : 成功!
         */

        private DataBean data;
        private int return_code;
        private String return_message;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
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

        public static class DataBean {
            /**
             * buy_membername : 奥森学校
             * buy_mmb_id : testschool001
             * contract_title : rrrrr
             * contract_type : 2
             * flow_type : 0
             * getgoods_address :
             * getgoods_addresslist : [{"address":"被禁了","areaId":"21","contact":"","id":"028cdcb30cca49029875e92d6f3222ef","mmbId":"testschool001","mobilephone":"","phone":"","zipcode":""},{"address":"奥森学校教工食堂","areaId":"3","contact":"","id":"adbc73908b5841e3bc61ff12780e9610","mmbId":"testschool001","mobilephone":"","phone":"","zipcode":"100015"},{"address":"奥森学校中心食堂冷库","areaId":"1","contact":"张三","id":"f9a6aaa8380a4ee9a5ad59fe81868361","mmbId":"testschool001","mobilephone":"","phone":"","zipcode":"100015"}]
             * getmoney_banklist : [{"accountname":"999","accountno":"1314","bankname":"工商银行","contact":"老司机","id":"34f9841f0564491da4860fd1c3461522","mmbId":"testshop001","mobilephone":"","phone":""},{"accountname":"123","accountno":"123","bankname":"","contact":"","id":"53e998565a584a5094654cd65598b3e6","mmbId":"testshop001","mobilephone":"","phone":""},{"accountname":"北京长城商贸中信分户","accountno":"63980011596","bankname":"中信银行","contact":"牛仁","id":"77d696d228f54d7a929c860e7cd3cd72","mmbId":"testshop001","mobilephone":"13901380137","phone":""},{"accountname":"TEST","accountno":"001","bankname":"中国银行","contact":"徐","id":"dca2f44770dd4cccafad81f295b16bec","mmbId":"testshop001","mobilephone":"15255179870","phone":""}]
             * getmoney_code : 1314
             * getmoney_name : 工商银行
             * goods : [{"categoryName":"水产干品","ctgId":"1002005003","ctrId":"4879644f970e40f79d392a975beb6e35","id":"7db7972d8d134ea99ed761afe75ce8a1"}]
             * note :
             * operate_time : 1496224794000
             * operate_user : d5faa6bd39ab45f383c20bad5a71abe3
             * pay_type : 0
             * payer_code : undefined
             * payer_name :
             * paymoney_banklist : [{"accountname":"北京市朝阳区奥森学校","accountno":"9953001798001","bankname":"建设银行","contact":"丁丁","id":"0e4161c278a540328a7def34bf6f7696","mmbId":"testschool001","mobilephone":"13501360137","phone":""}]
             * sell_membername : 长城商行
             * sell_mmb_id : testshop001
             * sendgoods_address : 北京市朝阳区立水桥5号库
             * sendgoods_addresslist : [{"address":"北京爱德华边塞诗","areaId":"3","contact":"","id":"cac3b44ed35e4c758a6c6c0dda2c35c4","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"北京市朝阳区立水桥5号库","areaId":"1","contact":"王部长","id":"ff3bce69cc094a0ca3488e833c40e5bb","mmbId":"testshop001","mobilephone":"15555555550","phone":"","zipcode":""}]
             * sendgoods_type : 0
             * user_time : 2017-05-31 - 2017-05-31
             */

            private String buy_membername;
            private String buy_mmb_id;
            private String contract_title;
            private String contract_type;
            private int flow_type;
            private String getgoods_address;
            private String getmoney_code;
            private String getmoney_name;
            private String note;
            private long operate_time;
            private String operate_user;
            private int pay_type;
            private String payer_code;
            private String payer_name;
            private String sell_membername;
            private String sell_mmb_id;
            private String sendgoods_address;
            private int sendgoods_type;
            private String user_time;
            private List<GetgoodsAddresslistBean> getgoods_addresslist;
            private List<GetmoneyBanklistBean> getmoney_banklist;
            private List<GoodsBean> goods;
            private List<PaymoneyBanklistBean> paymoney_banklist;
            private List<SendgoodsAddresslistBean> sendgoods_addresslist;

            public String getBuy_membername() {
                return buy_membername;
            }

            public void setBuy_membername(String buy_membername) {
                this.buy_membername = buy_membername;
            }

            public String getBuy_mmb_id() {
                return buy_mmb_id;
            }

            public void setBuy_mmb_id(String buy_mmb_id) {
                this.buy_mmb_id = buy_mmb_id;
            }

            public String getContract_title() {
                return contract_title;
            }

            public void setContract_title(String contract_title) {
                this.contract_title = contract_title;
            }

            public String getContract_type() {
                return contract_type;
            }

            public void setContract_type(String contract_type) {
                this.contract_type = contract_type;
            }

            public int getFlow_type() {
                return flow_type;
            }

            public void setFlow_type(int flow_type) {
                this.flow_type = flow_type;
            }

            public String getGetgoods_address() {
                return getgoods_address;
            }

            public void setGetgoods_address(String getgoods_address) {
                this.getgoods_address = getgoods_address;
            }

            public String getGetmoney_code() {
                return getmoney_code;
            }

            public void setGetmoney_code(String getmoney_code) {
                this.getmoney_code = getmoney_code;
            }

            public String getGetmoney_name() {
                return getmoney_name;
            }

            public void setGetmoney_name(String getmoney_name) {
                this.getmoney_name = getmoney_name;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public long getOperate_time() {
                return operate_time;
            }

            public void setOperate_time(long operate_time) {
                this.operate_time = operate_time;
            }

            public String getOperate_user() {
                return operate_user;
            }

            public void setOperate_user(String operate_user) {
                this.operate_user = operate_user;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public String getPayer_code() {
                return payer_code;
            }

            public void setPayer_code(String payer_code) {
                this.payer_code = payer_code;
            }

            public String getPayer_name() {
                return payer_name;
            }

            public void setPayer_name(String payer_name) {
                this.payer_name = payer_name;
            }

            public String getSell_membername() {
                return sell_membername;
            }

            public void setSell_membername(String sell_membername) {
                this.sell_membername = sell_membername;
            }

            public String getSell_mmb_id() {
                return sell_mmb_id;
            }

            public void setSell_mmb_id(String sell_mmb_id) {
                this.sell_mmb_id = sell_mmb_id;
            }

            public String getSendgoods_address() {
                return sendgoods_address;
            }

            public void setSendgoods_address(String sendgoods_address) {
                this.sendgoods_address = sendgoods_address;
            }

            public int getSendgoods_type() {
                return sendgoods_type;
            }

            public void setSendgoods_type(int sendgoods_type) {
                this.sendgoods_type = sendgoods_type;
            }

            public String getUser_time() {
                return user_time;
            }

            public void setUser_time(String user_time) {
                this.user_time = user_time;
            }

            public List<GetgoodsAddresslistBean> getGetgoods_addresslist() {
                return getgoods_addresslist;
            }

            public void setGetgoods_addresslist(List<GetgoodsAddresslistBean> getgoods_addresslist) {
                this.getgoods_addresslist = getgoods_addresslist;
            }

            public List<GetmoneyBanklistBean> getGetmoney_banklist() {
                return getmoney_banklist;
            }

            public void setGetmoney_banklist(List<GetmoneyBanklistBean> getmoney_banklist) {
                this.getmoney_banklist = getmoney_banklist;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public List<PaymoneyBanklistBean> getPaymoney_banklist() {
                return paymoney_banklist;
            }

            public void setPaymoney_banklist(List<PaymoneyBanklistBean> paymoney_banklist) {
                this.paymoney_banklist = paymoney_banklist;
            }

            public List<SendgoodsAddresslistBean> getSendgoods_addresslist() {
                return sendgoods_addresslist;
            }

            public void setSendgoods_addresslist(List<SendgoodsAddresslistBean> sendgoods_addresslist) {
                this.sendgoods_addresslist = sendgoods_addresslist;
            }

            public static class GetgoodsAddresslistBean {
                /**
                 * address : 被禁了
                 * areaId : 21
                 * contact :
                 * id : 028cdcb30cca49029875e92d6f3222ef
                 * mmbId : testschool001
                 * mobilephone :
                 * phone :
                 * zipcode :
                 */

                private String address;
                private String areaId;
                private String contact;
                private String id;
                private String mmbId;
                private String mobilephone;
                private String phone;
                private String zipcode;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getAreaId() {
                    return areaId;
                }

                public void setAreaId(String areaId) {
                    this.areaId = areaId;
                }

                public String getContact() {
                    return contact;
                }

                public void setContact(String contact) {
                    this.contact = contact;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getMmbId() {
                    return mmbId;
                }

                public void setMmbId(String mmbId) {
                    this.mmbId = mmbId;
                }

                public String getMobilephone() {
                    return mobilephone;
                }

                public void setMobilephone(String mobilephone) {
                    this.mobilephone = mobilephone;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getZipcode() {
                    return zipcode;
                }

                public void setZipcode(String zipcode) {
                    this.zipcode = zipcode;
                }
            }

            public static class GetmoneyBanklistBean {
                /**
                 * accountname : 999
                 * accountno : 1314
                 * bankname : 工商银行
                 * contact : 老司机
                 * id : 34f9841f0564491da4860fd1c3461522
                 * mmbId : testshop001
                 * mobilephone :
                 * phone :
                 */

                private String accountname;
                private String accountno;
                private String bankname;
                private String contact;
                private String id;
                private String mmbId;
                private String mobilephone;
                private String phone;

                public String getAccountname() {
                    return accountname;
                }

                public void setAccountname(String accountname) {
                    this.accountname = accountname;
                }

                public String getAccountno() {
                    return accountno;
                }

                public void setAccountno(String accountno) {
                    this.accountno = accountno;
                }

                public String getBankname() {
                    return bankname;
                }

                public void setBankname(String bankname) {
                    this.bankname = bankname;
                }

                public String getContact() {
                    return contact;
                }

                public void setContact(String contact) {
                    this.contact = contact;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getMmbId() {
                    return mmbId;
                }

                public void setMmbId(String mmbId) {
                    this.mmbId = mmbId;
                }

                public String getMobilephone() {
                    return mobilephone;
                }

                public void setMobilephone(String mobilephone) {
                    this.mobilephone = mobilephone;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }
            }

            public static class GoodsBean {
                /**
                 * categoryName : 水产干品
                 * ctgId : 1002005003
                 * ctrId : 4879644f970e40f79d392a975beb6e35
                 * id : 7db7972d8d134ea99ed761afe75ce8a1
                 */

                private String categoryName;
                private String ctgId;
                private String ctrId;
                private String id;

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public String getCtgId() {
                    return ctgId;
                }

                public void setCtgId(String ctgId) {
                    this.ctgId = ctgId;
                }

                public String getCtrId() {
                    return ctrId;
                }

                public void setCtrId(String ctrId) {
                    this.ctrId = ctrId;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public static class PaymoneyBanklistBean {
                /**
                 * accountname : 北京市朝阳区奥森学校
                 * accountno : 9953001798001
                 * bankname : 建设银行
                 * contact : 丁丁
                 * id : 0e4161c278a540328a7def34bf6f7696
                 * mmbId : testschool001
                 * mobilephone : 13501360137
                 * phone :
                 */

                private String accountname;
                private String accountno;
                private String bankname;
                private String contact;
                private String id;
                private String mmbId;
                private String mobilephone;
                private String phone;

                public String getAccountname() {
                    return accountname;
                }

                public void setAccountname(String accountname) {
                    this.accountname = accountname;
                }

                public String getAccountno() {
                    return accountno;
                }

                public void setAccountno(String accountno) {
                    this.accountno = accountno;
                }

                public String getBankname() {
                    return bankname;
                }

                public void setBankname(String bankname) {
                    this.bankname = bankname;
                }

                public String getContact() {
                    return contact;
                }

                public void setContact(String contact) {
                    this.contact = contact;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getMmbId() {
                    return mmbId;
                }

                public void setMmbId(String mmbId) {
                    this.mmbId = mmbId;
                }

                public String getMobilephone() {
                    return mobilephone;
                }

                public void setMobilephone(String mobilephone) {
                    this.mobilephone = mobilephone;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }
            }

            public static class SendgoodsAddresslistBean {
                /**
                 * address : 北京爱德华边塞诗
                 * areaId : 3
                 * contact :
                 * id : cac3b44ed35e4c758a6c6c0dda2c35c4
                 * mmbId : testshop001
                 * mobilephone :
                 * phone :
                 * zipcode :
                 */

                private String address;
                private String areaId;
                private String contact;
                private String id;
                private String mmbId;
                private String mobilephone;
                private String phone;
                private String zipcode;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getAreaId() {
                    return areaId;
                }

                public void setAreaId(String areaId) {
                    this.areaId = areaId;
                }

                public String getContact() {
                    return contact;
                }

                public void setContact(String contact) {
                    this.contact = contact;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getMmbId() {
                    return mmbId;
                }

                public void setMmbId(String mmbId) {
                    this.mmbId = mmbId;
                }

                public String getMobilephone() {
                    return mobilephone;
                }

                public void setMobilephone(String mobilephone) {
                    this.mobilephone = mobilephone;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getZipcode() {
                    return zipcode;
                }

                public void setZipcode(String zipcode) {
                    this.zipcode = zipcode;
                }
            }
        }
    }
}
