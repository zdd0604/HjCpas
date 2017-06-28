package com.hj.casps.protocolmanager;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.bankmanage.BillsDetailsActivity;
import com.hj.casps.common.Constant;
import com.hj.casps.cooperate.CooperateCreate;
import com.hj.casps.cooperate.CooperateDirUtils;
import com.hj.casps.entity.appContract.BackBean;
import com.hj.casps.entity.appContract.OrderBackList;
import com.hj.casps.entity.appContract.ProtocolModelForPost;
import com.hj.casps.ordermanager.OrderDetail;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.base.ActivityBase.context;
import static com.hj.casps.base.ActivityBase.mGson;
import static com.hj.casps.common.Constant.END_TIME;
import static com.hj.casps.common.Constant.ORDER_NAME;
import static com.hj.casps.common.Constant.ORDER_ORDER_ID;
import static com.hj.casps.common.Constant.ORDER_STATUS;
import static com.hj.casps.common.Constant.PROTOCOL_SEARCH;
import static com.hj.casps.common.Constant.PROTOCOL_STATUS;
import static com.hj.casps.common.Constant.PROTOCOL_TITLE;
import static com.hj.casps.common.Constant.START_TIME;
import static com.hj.casps.common.Constant.getUUID;
import static com.hj.casps.common.Constant.publicArg;

/**
 * 协议列表和订单列表都放在了这里。为了是软件不冗余，减少设计的界面，以用此法
 */
public class ProtocolFragment extends ViewPagerFragment1 implements View.OnClickListener, OnPullListener {

    private int protocol_type;
    private ListView protocol_list;
    private List<ProtocolListBean> protocolModels = new ArrayList<>();
    private List<OrderRowBean> orderDoingModels;
    private ProtocolAdapter adapter;
    private OrderDoingAdapter adapter_order;
    private int protocol_type_j;
    private int type_k;
    private View order_divider;
    private CheckBox select_all_order;
    private TextView order_lock;
    private TextView order_abandon;
    private LinearLayout order_check_function;
    private MyDialog myDialog;
    private int page = 0;
    //    public static ProtocolFragment protocolFragment = null;
    private AbsRefreshLayout mLoader;
    LinearLayout layout_fragment_button_check1;
//    private String name = "";
//    private String status = "3";


    /**
     * @param i: 是合作协议布局类型
     * @param j: 0代表全部，1代表采购，2代表销售
     * @param k: 1代表协议管理，2代表订单管理
     */
    //ORDER_ORDER_ID, String.valueOf(protocol_type_j + 1), ORDER_NAME, ORDER_STATUS, START_TIME, END_TIME);
    public ProtocolFragment(int i, int j, int k) {
        protocol_type = i;
        protocol_type_j = j;
        type_k = k;
        PROTOCOL_TITLE = "";
        ORDER_ORDER_ID = "";
        ORDER_NAME = "";
        ORDER_STATUS = "0";
        START_TIME = "";
        END_TIME = "";
        PROTOCOL_STATUS = "3";
    }

    /**
     * 保存数据库
     */
    private void saveDaoData() {
        CooperateDirUtils.getInstance(getActivity()).deleteAll();
        switch (type_k) {
            case 1:
                //代表协议管理
                for (int i = 0; i < protocolModels.size(); i++) {
                    CooperateDirUtils.getInstance(getActivity()).insertInfo(protocolModels.get(i));
                }
                break;
            case 2:
                //代表订单管理
                for (int i = 0; i < orderDoingModels.size(); i++) {
                    CooperateDirUtils.getInstance(getActivity()).insertInfo(orderDoingModels.get(i));
                }
                break;
        }
    }


    /**
     * 加载本地数据
     */
    private void addLocality() {
        switch (type_k) {
            case 1:
                List<ProtocolListBean> usrList = CooperateDirUtils.getInstance(getActivity()).queryProtocolListBeanInfo();
                if (usrList.size() > 0) {
                    protocolModels = usrList;
                }
                adapter.updateRes(protocolModels);
                break;
            case 2://代表订单管理
                List<OrderRowBean> usrList1 = CooperateDirUtils.getInstance(getActivity()).queryOrderRowBeanDaoInfo();
                if (usrList1.size() > 0) {
                    orderDoingModels = usrList1;
                }
                adapter_order.updateRes(orderDoingModels);
                break;
        }


    }

    //在fragment中类似于activity的安卓公共方法
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        layout = inflater.inflate(R.layout.fragment_blank, container, false);
//        protocolFragment = this;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        }
        initView(rootView);
        initAdapter();
        return rootView;

    }

    //判断是否有网络
    public boolean hasInternetConnected() {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo network = manager.getActiveNetworkInfo();
            if (network != null && network.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    //查看fragment是否改变
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (hasInternetConnected()) {
                page = 0;
                initData();
            } else {
                addLocality();
            }
        }
    }

    //Adapter的初始化
    private void initAdapter() {
        switch (type_k) {
            case 1:
                adapter = new ProtocolAdapter(protocolModels, getActivity(), R.layout.protocol_item);
                protocol_list.setAdapter(adapter);
                break;
            case 2:
                adapter_order = new OrderDoingAdapter(orderDoingModels, getActivity(), R.layout.item_order_going);
                protocol_list.setAdapter(adapter_order);
                if (protocol_type == 1) {
                    order_divider.setVisibility(View.VISIBLE);
                    order_check_function.setVisibility(View.VISIBLE);
                }
                break;
        }
        select_all_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                selectAll(b);
            }
        });
        layout_fragment_button_check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_all_order.isChecked()) {
                    select_all_order.setChecked(false);
                    selectAll(false);
                } else {
                    select_all_order.setChecked(true);
                    selectAll(true);
                }
            }
        });
    }

    private void selectAll(boolean b) {
        for (int i = 0; i < orderDoingModels.size(); i++) {
            orderDoingModels.get(i).setChoice(b);
        }
        adapter_order.notifyDataSetChanged();
    }

    //用来接收查询后的页面
    @Override
    public void onResume() {
//        Toast.makeText(getContext(), "res", Toast.LENGTH_SHORT).show();
        if (PROTOCOL_SEARCH) {
            page = 0;
            initData();
        }
        super.onResume();

    }

    //初始化数据，根据订单类型的不同和销售类型的不同来加载网络数据
    public void initData() {
        PROTOCOL_SEARCH = false;
        switch (type_k) {
            case 1:
                ProtocolModelForPost post = null;
                switch (protocol_type) {
                    case 0:
                        post = new ProtocolModelForPost(publicArg.getSys_token(),
                                getUUID(),
                                Constant.SYS_FUNC,
                                publicArg.getSys_user(),
                                publicArg.getSys_member(),
                                String.valueOf(page + 1),
                                "10",
                                "submit",
                                protocol_type_j == 0 ? "" : String.valueOf(protocol_type_j),
                                PROTOCOL_TITLE, "");
                        break;
                    case 1:
                        post = new ProtocolModelForPost(
                                publicArg.getSys_token(),
                                getUUID(),
                                Constant.SYS_FUNC,
                                publicArg.getSys_user(),
                                publicArg.getSys_member(),
                                String.valueOf(page + 1), "10", "pending",
                                protocol_type_j == 0 ? "" : String.valueOf(protocol_type_j),
                                PROTOCOL_TITLE, "");
                        break;
                    case 2:
                        post = new ProtocolModelForPost(
                                publicArg.getSys_token(),
                                getUUID(),
                                Constant.SYS_FUNC,
                                publicArg.getSys_user(),
                                publicArg.getSys_member(),
                                String.valueOf(page + 1),
                                "10", "running",
                                protocol_type_j == 0 ? "" : String.valueOf(protocol_type_j),
                                PROTOCOL_TITLE, PROTOCOL_STATUS);
                        break;
                }

                OkGo.post(Constant.QueryContractByPageTypeUrl)
                        .params("param", mGson.toJson(post))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {

                                BackBean backDetail = mGson.fromJson(s, BackBean.class);
                                if (backDetail == null) {
                                    return;
                                }
                                if (backDetail.getReturn_code() != 0) {
                                    Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                                } else if (backDetail.getReturn_code() == 1101 || backDetail.getReturn_code() == 1102) {
                                    LogoutUtils.exitUser(ProtocolFragment.this);
                                } else {
                                    protocolModels = backDetail.getList();
                                    if (protocolModels.isEmpty()) {
//                                        Toast.makeText(context, "无协议", Toast.LENGTH_SHORT).show();
                                        adapter.removeAll();
                                    }
                                    if (page != 0) {
                                        if (page <= (backDetail.getPagecount() - 1) / 10) {
                                            adapter.addRes(protocolModels);

                                        } else {
                                            mLoader.onLoadAll();
                                        }
                                    } else {

                                        adapter.updateRes(protocolModels);

                                    }
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
//                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case 2:
                ProtocolModelForPost post1 = null;
                String url = null;
                switch (protocol_type) {
                    case 0:
                        post1 = new ProtocolModelForPost(
                                publicArg.getSys_token(),
                                getUUID(),
                                Constant.SYS_FUNC,
                                publicArg.getSys_user(),
                                publicArg.getSys_name(),
                                publicArg.getSys_member(),
                                String.valueOf(page + 1),
                                "10",
                                "",
                                ORDER_ORDER_ID,
                                String.valueOf(protocol_type_j + 1),
                                ORDER_NAME);
                        url = Constant.QueryMyPendingOrderUrl;
                        break;
                    case 1:
                        post1 = new ProtocolModelForPost(
                                publicArg.getSys_token(),
                                getUUID(),
                                Constant.SYS_FUNC,
                                publicArg.getSys_user(),
                                publicArg.getSys_name(),
                                publicArg.getSys_member(),
                                String.valueOf(page + 1),
                                "10", null, ORDER_ORDER_ID,
                                String.valueOf(protocol_type_j + 1),
                                ORDER_NAME);
                        url = Constant.AppOrderListUrl;
                        break;
                    case 2:
                        //String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String pageno, String pagesize, String orderId, String status, String name, String executeStatus, String startTime, String endTime
                        post1 = new ProtocolModelForPost(
                                publicArg.getSys_token(),
                                getUUID(),
                                Constant.SYS_FUNC,
                                publicArg.getSys_user(),
                                publicArg.getSys_name(),
                                publicArg.getSys_member(),
                                String.valueOf(page + 1),
                                "10", ORDER_ORDER_ID,
                                String.valueOf(protocol_type_j + 1),
                                ORDER_NAME,
                                ORDER_STATUS,
                                START_TIME,
                                END_TIME);
                        url = Constant.QueryOrderManageUrl;
                        break;
                }
                OkGo.post(url)
                        .params("param", mGson.toJson(post1))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    OrderBackList backDetail = mGson.fromJson(s, OrderBackList.class);
                                    if (backDetail == null) {
                                        return;
                                    }
                                    if (backDetail.getReturn_code() != 0) {
//                                        Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                                    } else if (backDetail.getReturn_code() == 1101 || backDetail.getReturn_code() == 1102) {
                                        LogoutUtils.exitUser(ProtocolFragment.this);
                                    } else {
                                        orderDoingModels = backDetail.getRows();
                                        if (orderDoingModels.isEmpty()) {
//                                            Toast.makeText(context, "无订单", Toast.LENGTH_SHORT).show();
                                            adapter_order.removeAll();
                                        }
                                        if (page != 0) {
                                            if (page <= (backDetail.getTotal() - 1) / 10) {
                                                adapter_order.addRes(orderDoingModels);

                                            } else {
                                                mLoader.onLoadAll();
                                            }
                                        } else {
                                            adapter_order.updateRes(orderDoingModels);
                                        }
                                    }
                                } catch (Exception e) {
//                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
//                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }

    }

    //做初始化View的操作
    protected void initView(View layout) {
        order_divider = (View) layout.findViewById(R.id.order_divider);
        select_all_order = (CheckBox) layout.findViewById(R.id.select_all_order);
        order_lock = (TextView) layout.findViewById(R.id.order_lock);
        order_lock.setOnClickListener(this);
        order_abandon = (TextView) layout.findViewById(R.id.order_abandon);
        order_abandon.setOnClickListener(this);
        order_check_function = (LinearLayout) layout.findViewById(R.id.order_check_function);
        layout_fragment_button_check1 = (LinearLayout) layout.findViewById(R.id.layout_fragment_button_check1);
        protocol_list = (ListView) layout.findViewById(R.id.protocol_list);
        mLoader = new NestRefreshLayout(protocol_list);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);


    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_lock:
                lockOrder(0);
                break;
            case R.id.order_abandon:
                lockOrder(1);
                break;
        }
    }

    /**
     * 订单判断
     * @param type
     */
    private void lockOrder(int type) {
        List<ProtocolModelForPost.ID> ids = new ArrayList<>();
        for (int i = 0; i < orderDoingModels.size(); i++) {
            if (orderDoingModels.get(i).isChoice()) {
                ids.add(new ProtocolModelForPost.ID(orderDoingModels.get(i).getId()));
            }
        }

        if (ids.size() == 0) {
            ToastUtils.showToast(getActivity(), "请选择订单");
            return;
        }
        showDialog(type,ids);
    }



    //弹框
    private void showDialog(final int type,final List<ProtocolModelForPost.ID> ids) {
        if (orderDoingModels.size() == 0) {
            ToastUtils.showToast(getActivity(), "请选择订单");
            return;
        }
        myDialog = new MyDialog(getContext());
        switch (type) {
            case 0:
                myDialog.setMessage(getString(R.string.order_locked_ask));
                break;
            case 1:
                myDialog.setMessage(getString(R.string.order_abandon_ask));
                break;
        }
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                lockorab(type, ids);
                myDialog.dismiss();
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



    //锁定，作废订单操作
    private void lockorab(int type, List<ProtocolModelForPost.ID> ids) {
        String url = "";
        switch (type) {
            case 0:
                //锁定
                url = Constant.AppOrderLockOrderUrl;
                break;
            case 1:
                //作废
                url = Constant.AppOrderAbolishOrderUrl;
                break;
        }

        ProtocolModelForPost post = new ProtocolModelForPost(
                publicArg.getSys_token(),
                getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                ids);
        OkGo.post(url)
                .params("param", mGson.toJson(post))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        BackBean backDetail = mGson.fromJson(s, BackBean.class);
                        if (backDetail == null) {
                            return;
                        }
                        if (backDetail.getReturn_code() != 0) {
                            Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                        } else if (backDetail.getReturn_code() == 1101 || backDetail.getReturn_code() == 1102) {
                            LogoutUtils.exitUser(ProtocolFragment.this);
                        } else {
                            Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                            initData();
                        }
                        select_all_order.setChecked(false);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    //下拉刷新
    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        page = 0;
        initData();
        mLoader.onLoadFinished();//加载结束
    }

    //上拉加载
    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        page++;
        initData();
        mLoader.onLoadFinished();//加载结束
    }

    /**
     * 协议的适配器，用来添加协议管理的数据，制定操作等
     */

    public class ProtocolAdapter extends WZYBaseAdapter<ProtocolListBean> {
        private Context context;

        public ProtocolAdapter(List<ProtocolListBean> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
            this.context = context;
        }

        @Override
        public void bindData(WZYBaseAdapter.ViewHolder holder, final ProtocolListBean protocolModel, final int indexPos) {
            TextView protocol_title = (TextView) holder.getView(R.id.protocol_title);
            protocol_title.setText(protocolModel.getContract_title());
            protocol_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProtocolDetail.class);
                    intent.putExtra("contract_id", protocolModel.getContract_id());
                    intent.putExtra("contract_type", String.valueOf(protocol_type_j));
                    startActivity(intent);
                }
            });
            TextView protocol_object = (TextView) holder.getView(R.id.protocol_object);
            protocol_object.setText(protocolModel.getMmbname());
            TextView protocol_transport_type = (TextView) holder.getView(R.id.protocol_transport_type);
            protocol_transport_type.setText(protocolModel.getFlow_type());
            TextView protocol_buy_time = (TextView) holder.getView(R.id.protocol_buy_time);
            protocol_buy_time.setText(protocolModel.getPay_type());
            TextView protocol_begin_time = (TextView) holder.getView(R.id.protocol_begin_time);
            protocol_begin_time.setText(Constant.stmpToDate(protocolModel.getStart_time()));
            TextView protocol_end_time = (TextView) holder.getView(R.id.protocol_end_time);
            protocol_end_time.setText(Constant.stmpToDate(protocolModel.getEnd_time()));
            TextView protocol_waiting_sure = (TextView) holder.getView(R.id.protocol_waiting_sure);
            FancyButton fancyButton_1 = (FancyButton) holder.getView(R.id.fancyButton_1);
            FancyButton fancyButton_2 = (FancyButton) holder.getView(R.id.fancyButton_2);
            FancyButton fancyButton_3 = (FancyButton) holder.getView(R.id.fancyButton_3);
            RelativeLayout protocol_item_relative = (RelativeLayout) holder.getView(R.id.protocol_item_relative);
            View protocol_view = holder.getView(R.id.protocol_view);
            switch (protocol_type) {
                case 0:
                    protocol_waiting_sure.setVisibility(View.VISIBLE);
                    protocol_item_relative.setVisibility(View.GONE);
                    protocol_view.setVisibility(View.GONE);
                    switch (protocolModel.getContract_status()) {
                        case 1:
                            protocol_waiting_sure.setText(context.getString(R.string.protocol_waiting_sure));
                            break;
                        case 4:
                            protocol_waiting_sure.setText(context.getString(R.string.protocol_waiting_refuse));
                            break;
                    }
                    break;
                case 1:
                    protocol_waiting_sure.setVisibility(View.GONE);
                    fancyButton_1.setVisibility(View.VISIBLE);
                    fancyButton_2.setVisibility(View.VISIBLE);
                    fancyButton_3.setVisibility(View.VISIBLE);
                    fancyButton_1.setBackgroundColor(context.getResources().getColor(R.color.white));
                    fancyButton_1.setBorderColor(context.getResources().getColor(R.color.lineColor));
                    fancyButton_1.setTextColor(context.getResources().getColor(R.color.grey));
                    fancyButton_1.setText(context.getString(R.string.protocol_refuse));
                    fancyButton_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            postOperate(protocolModel.getContract_id(), "refuse", 1, context.getString(R.string.cooperate_deny_done));
                        }
                    });
                    fancyButton_2.setBackgroundColor(context.getResources().getColor(R.color.title_bg));
                    fancyButton_2.setBorderColor(context.getResources().getColor(R.color.title_bg));
                    fancyButton_2.setTextColor(context.getResources().getColor(R.color.white));
                    fancyButton_2.setText(context.getString(R.string.protocol_sure));
                    fancyButton_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, ProtocolAgree.class);
                            intent.putExtra("contract_id", protocolModel.getContract_id());
                            intent.putExtra("contract_status", String.valueOf(protocolModel.getContract_status()));
                            intent.putExtra("contract_type", String.valueOf(protocol_type_j));
                            startActivity(intent);
                        }
                    });
                    fancyButton_3.setBackgroundColor(context.getResources().getColor(R.color.white));
                    fancyButton_3.setBorderColor(context.getResources().getColor(R.color.title_bg));
                    fancyButton_3.setTextColor(context.getResources().getColor(R.color.title_bg));
                    fancyButton_3.setText(context.getString(R.string.protocol_edit));
                    fancyButton_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, CooperateCreate.class);
                            intent.putExtra("type", 1);
                            intent.putExtra("contract_id", protocolModel.getContract_id());
                            intent.putExtra("buy_type", protocol_type_j == 1);
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    switch (protocolModel.getContract_status()) {
                        case 3:
                            protocol_waiting_sure.setVisibility(View.GONE);
                            fancyButton_1.setVisibility(View.VISIBLE);
                            fancyButton_2.setVisibility(View.VISIBLE);
                            fancyButton_3.setVisibility(View.GONE);
                            fancyButton_1.setBackgroundColor(context.getResources().getColor(R.color.title_bg));
                            fancyButton_1.setBorderColor(context.getResources().getColor(R.color.title_bg));
                            fancyButton_1.setTextColor(context.getResources().getColor(R.color.white));
                            fancyButton_1.setText(context.getString(R.string.protocol_get_order));
                            fancyButton_1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(context, CooperateCreate.class);
                                    intent.putExtra("type", 2);
                                    intent.putExtra("contract_id", protocolModel.getContract_id());
                                    intent.putExtra("buy_type", protocol_type_j == 1);
                                    context.startActivity(intent);
                                }
                            });
                            fancyButton_2.setBackgroundColor(context.getResources().getColor(R.color.white));
                            fancyButton_2.setBorderColor(context.getResources().getColor(R.color.lineColor));
                            fancyButton_2.setTextColor(context.getResources().getColor(R.color.grey));
                            fancyButton_2.setText(context.getString(R.string.protocol_request_reject));
                            fancyButton_2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    postOperate(protocolModel.getContract_id(), "terminate", 3, context.getString(R.string.cooperate_request_done));
                                }
                            });
                            break;
                        case 5:

                            protocol_waiting_sure.setVisibility(View.GONE);
                            if (!protocolModel.getBuy_mmb_id().equalsIgnoreCase(publicArg.getSys_member())) {
                                fancyButton_3.setVisibility(View.GONE);
                                fancyButton_2.setVisibility(View.VISIBLE);
                                fancyButton_1.setVisibility(View.VISIBLE);
                                fancyButton_1.setBackgroundColor(context.getResources().getColor(R.color.white));
                                fancyButton_1.setBorderColor(context.getResources().getColor(R.color.red));
                                fancyButton_1.setTextColor(context.getResources().getColor(R.color.red));
                                fancyButton_1.setText(context.getString(R.string.protocol_agree_reject));
                                fancyButton_1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        postOperate(protocolModel.getContract_id(), "agreeTerminate", 6, context.getString(R.string.cooperate_agree_done));
                                    }
                                });
                                fancyButton_2.setBackgroundColor(context.getResources().getColor(R.color.white));
                                fancyButton_2.setBorderColor(context.getResources().getColor(R.color.lineColor));
                                fancyButton_2.setTextColor(context.getResources().getColor(R.color.grey));
                                fancyButton_2.setText(context.getString(R.string.protocol_refuse_re));
                                fancyButton_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        postOperate(protocolModel.getContract_id(), "refuseTerminate", 6, context.getString(R.string.cooperate_deny_done));
                                    }
                                });
                            } else {
                                fancyButton_3.setVisibility(View.GONE);
                                fancyButton_2.setVisibility(View.GONE);
                                fancyButton_1.setVisibility(View.VISIBLE);
                                fancyButton_1.setBackgroundColor(context.getResources().getColor(R.color.white));
                                fancyButton_1.setBorderColor(context.getResources().getColor(R.color.title_bg));
                                fancyButton_1.setTextColor(context.getResources().getColor(R.color.title_bg));
                                fancyButton_1.setText(context.getString(R.string.protocol_refuse_reject));
                                fancyButton_1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        postOperate(protocolModel.getContract_id(), "recallTerminate", 6, context.getString(R.string.cooperate_reject_done));
                                    }
                                });
                            }

                            break;
                        case 6:
                            protocol_waiting_sure.setVisibility(View.GONE);
                            if (protocolModel.getBuy_mmb_id().equalsIgnoreCase(publicArg.getSys_member())) {
                                fancyButton_3.setVisibility(View.GONE);
                                fancyButton_2.setVisibility(View.VISIBLE);
                                fancyButton_1.setVisibility(View.VISIBLE);
                                fancyButton_1.setBackgroundColor(context.getResources().getColor(R.color.white));
                                fancyButton_1.setBorderColor(context.getResources().getColor(R.color.red));
                                fancyButton_1.setTextColor(context.getResources().getColor(R.color.red));
                                fancyButton_1.setText(context.getString(R.string.protocol_agree_reject));
                                fancyButton_1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        postOperate(protocolModel.getContract_id(), "agreeTerminate", 6, context.getString(R.string.cooperate_agree_done));
                                    }
                                });
                                fancyButton_2.setBackgroundColor(context.getResources().getColor(R.color.white));
                                fancyButton_2.setBorderColor(context.getResources().getColor(R.color.lineColor));
                                fancyButton_2.setTextColor(context.getResources().getColor(R.color.grey));
                                fancyButton_2.setText(context.getString(R.string.protocol_refuse_re));
                                fancyButton_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        postOperate(protocolModel.getContract_id(), "refuseTerminate", 6, context.getString(R.string.cooperate_deny_done));
                                    }
                                });
                            } else {
                                fancyButton_3.setVisibility(View.GONE);
                                fancyButton_2.setVisibility(View.GONE);
                                fancyButton_1.setVisibility(View.VISIBLE);
                                fancyButton_1.setBackgroundColor(context.getResources().getColor(R.color.white));
                                fancyButton_1.setBorderColor(context.getResources().getColor(R.color.title_bg));
                                fancyButton_1.setTextColor(context.getResources().getColor(R.color.title_bg));
                                fancyButton_1.setText(context.getString(R.string.protocol_refuse_reject));
                                fancyButton_1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        postOperate(protocolModel.getContract_id(), "recallTerminate", 6, context.getString(R.string.cooperate_reject_done));
                                    }
                                });
                            }
                            break;
                        case 7:
                            protocol_waiting_sure.setVisibility(View.VISIBLE);
                            protocol_item_relative.setVisibility(View.GONE);
                            protocol_view.setVisibility(View.GONE);
                            protocol_waiting_sure.setText(context.getString(R.string.protocol_waiting_stop));
                            break;
                    }
                    break;
            }

        }

        //提交协议各种请求
        private void postOperate(String id, String refuse, int i, final String string) {
            ProtocolModelForPost post = new ProtocolModelForPost(
                    publicArg.getSys_token(),
                    getUUID(),
                    Constant.SYS_FUNC,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    id, refuse, String.valueOf(i),
                    String.valueOf(protocol_type_j));
            OkGo.post(Constant.OperateContractUrl)
                    .params("param", mGson.toJson(post))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            BackBean backDetail = mGson.fromJson(s, BackBean.class);
                            if (backDetail.getReturn_code() != 0) {
                                Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                            } else if (backDetail.getReturn_code() == 1101 || backDetail.getReturn_code() == 1102) {
                                LogoutUtils.exitUser(ProtocolFragment.this);
                            } else {
                                new MyToast(context, string);
                                initData();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });

        }
    }

    //订单管理界面的适配器
    public class OrderDoingAdapter extends WZYBaseAdapter<OrderRowBean> {
        private Context context;

        private void postOperate(String id, String refuse, final String string) {
            ProtocolModelForPost post = new ProtocolModelForPost(
                    publicArg.getSys_token(),
                    getUUID(),
                    Constant.SYS_FUNC,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    id,
                    refuse);
            OkGo.post(Constant.StopOrderUrl)
                    .params("param", mGson.toJson(post))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            BackBean backDetail = mGson.fromJson(s, BackBean.class);
                            if (backDetail == null) {
                                return;
                            }
                            if (backDetail.getReturn_code() == 0) {
                                new MyToast(context, string);
                                page = 0;
                                initData();
                            } else if (backDetail.getReturn_code() == 1101 || backDetail.getReturn_code() == 1102) {
                                LogoutUtils.exitUser(ProtocolFragment.this);
                            } else {
                                Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });

        }

        public OrderDoingAdapter(List<OrderRowBean> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
            this.context = context;
        }

        @Override
        public void bindData(ViewHolder holder, final OrderRowBean orderDoingModel, final int indexPos) {
            DecimalFormat df = new DecimalFormat("#0.00");
            TextView oderId = (TextView) holder.getView(R.id.layout_tv_order_line_no);
            oderId.setText(String.valueOf(orderDoingModel.getOrdertitleCode()));
            oderId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoDetail(orderDoingModel);
                }
            });
            TextView object = (TextView) holder.getView(R.id.order_doing_object);
            if (orderDoingModel.getBuyersId() == null || orderDoingModel.getBuyersId().equalsIgnoreCase(publicArg.getSys_member())) {
                object.setText(orderDoingModel.getSellersName());
            } else {
                object.setText(orderDoingModel.getBuyersName());
            }
            TextView time = (TextView) holder.getView(R.id.order_doing_create_time);
            TextView price = (TextView) holder.getView(R.id.order_doing_all_price);
            price.setText(df.format(orderDoingModel.getTotalMoney()));
            CheckBox checkBox = (CheckBox) holder.getView(R.id.layout_check_order_going);
            TextView order_item_state = (TextView) holder.getView(R.id.order_item_state);
            TextView create_time = (TextView) holder.getView(R.id.create_time);
            FancyButton order_going_btn = (FancyButton) holder.getView(R.id.order_going_btn);
            FancyButton order_going_btn2 = (FancyButton) holder.getView(R.id.order_going_btn2);

            switch (protocol_type) {
                case 0://新建订单
                    checkBox.setVisibility(View.GONE);
                    order_item_state.setVisibility(View.GONE);
                    order_going_btn.setVisibility(View.GONE);
                    time.setText(Constant.stmpToDate(orderDoingModel.getCreateTime()));

                    break;
                case 1://待审批订单
                    time.setText(Constant.stmpToDate(orderDoingModel.getCreateTime()));

                    checkBox.setVisibility(View.VISIBLE);
                    order_item_state.setVisibility(View.GONE);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            orderDoingModel.setChoice(b);
                        }
                    });
                    checkBox.setChecked(orderDoingModel.isChoice());
                    order_going_btn.setVisibility(View.VISIBLE);
                    order_going_btn.setText(context.getString(R.string.quotes_edit));
                    order_going_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(context, OrderDetail.class);
                            intent.putExtra("title", context.getString(R.string.edit));
                            intent.putExtra("type", 1);
                            intent.putExtra("id", orderDoingModel.getId());
                            intent.putExtra("state", 2 - protocol_type_j);
//                            intent.putExtra("buy_id", 1);
//                            intent.putExtra("buy_name", 1);
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 2://执行中订单
                    create_time.setText(context.getString(R.string.tv_bills_contract_time_title));
                    time.setText(Constant.stmpToDate(orderDoingModel.getLockTime()));
                    if (orderDoingModel.getExecuteStatus() == 1) {
                        switch (orderDoingModel.getStopStatus()) {
                            case 1://请求终止
                                checkBox.setVisibility(View.GONE);
                                order_going_btn2.setVisibility(View.GONE);
                                order_item_state.setVisibility(View.VISIBLE);
                                order_going_btn.setVisibility(View.VISIBLE);
                                order_going_btn.setText(context.getString(R.string.order_doing_request_stop));
                                order_going_btn.setPadding(20, 0, 20, 0);
                                order_going_btn.setBorderColor(context.getResources().getColor(R.color.title_bg));
                                order_going_btn.setTextColor(context.getResources().getColor(R.color.title_bg));
                                order_item_state.setText(context.getString(R.string.order_doing_going));
                                order_item_state.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                                break;
                            case 2://撤回请求终止
                                checkBox.setVisibility(View.GONE);
                                order_going_btn2.setVisibility(View.GONE);
                                order_item_state.setVisibility(View.VISIBLE);
                                order_going_btn.setVisibility(View.VISIBLE);
                                order_going_btn.setText(context.getString(R.string.order_doing_reject_request));
                                order_going_btn.setPadding(5, 0, 5, 0);
                                order_going_btn.setBorderColor(context.getResources().getColor(R.color.title_bg));
                                order_going_btn.setTextColor(context.getResources().getColor(R.color.title_bg));
                                order_item_state.setText(context.getString(R.string.order_doing_going));
                                order_item_state.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                                break;
                            case 3://同意终止
                                checkBox.setVisibility(View.GONE);
                                order_item_state.setVisibility(View.VISIBLE);
                                order_going_btn.setVisibility(View.VISIBLE);
                                order_going_btn.setText(context.getString(R.string.order_doing_sure_stop));
                                order_going_btn.setPadding(20, 0, 20, 0);
                                order_going_btn.setBorderColor(context.getResources().getColor(R.color.red));
                                order_going_btn.setTextColor(context.getResources().getColor(R.color.red));
                                order_going_btn2.setVisibility(View.VISIBLE);
                                order_going_btn2.setText(context.getString(R.string.protocol_refuse_re));
                                order_going_btn2.setPadding(20, 0, 20, 0);
                                order_going_btn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showDialog(4, orderDoingModel.getId());

                                    }
                                });
                                order_item_state.setText(context.getString(R.string.order_doing_going));
                                order_item_state.setBackgroundColor(context.getResources().getColor(R.color.light_green));

                                break;
                        }
                    } else {
                        checkBox.setVisibility(View.GONE);
                        order_going_btn2.setVisibility(View.GONE);
                        order_item_state.setVisibility(View.VISIBLE);
                        order_item_state.setText(context.getString(R.string.order_doing_finished));
                        order_item_state.setBackgroundColor(context.getResources().getColor(R.color.text_color_blue2));
                        order_going_btn.setVisibility(View.GONE);
                    }
                    order_going_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog(orderDoingModel.getStopStatus(), orderDoingModel.getId());
                        }
                    });

//                order_going_btn.setPadding(10, 0, 10, 0);

                    break;
            }
        }

        //提交订单管理的各种请求
        private void gotoDetail(OrderRowBean orderDoingModel) {
//            getDatas(orderDoingModel.getId());
            Intent intent = new Intent(context, BillsDetailsActivity.class);
//            Bundle bundle = new Bundle();
            Constant.SEARCH_sendgoods_orderId = orderDoingModel.getId();
//            bundle.putSerializable(Constant.BUNDLE_TYPE, mList.get(pos));
//            intent.putExtras(bundle);
            context.startActivity(intent);
        }

        //弹框
        private void showDialog(final int i, final String id) {
            final MyDialog myDialog = new MyDialog(context);
            switch (i) {
                case 1:
                    myDialog.setMessage(context.getString(R.string.order_stop_ask));
                    break;
                case 2:
                    myDialog.setMessage(context.getString(R.string.order_stop_reject_ask));
                    break;
                case 3:
                    myDialog.setMessage(context.getString(R.string.order_stop_agree_ask));
                    break;
                case 4:
                    myDialog.setMessage(context.getString(R.string.order_stop_refuse_ask));
                    break;
            }
            myDialog.setYesOnclickListener(context.getString(R.string.True), new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    myDialog.dismiss();
                    switch (i) {
                        case 1:
                            postOperate(id, "stop", "已请求终止");
                            break;
                        case 2:
                            postOperate(id, "revoke", "已撤回");
                            break;
                        case 3:
                            postOperate(id, "allow", "已同意");
                            break;
                        case 4:
                            postOperate(id, "refuse", "已拒绝");
                            break;
                    }
                    initData();

                }
            });
            myDialog.setNoOnclickListener(context.getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }
    }
}
