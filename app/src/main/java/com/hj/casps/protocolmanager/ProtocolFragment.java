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
import com.hj.casps.ordermanager.OrderDetail;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
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
import static com.hj.casps.common.Constant.SYS_FUNC101100350001;
import static com.hj.casps.common.Constant.SYS_FUNC101100350002;
import static com.hj.casps.common.Constant.SYS_FUNC101100350003;
import static com.hj.casps.common.Constant.SYS_FUNC101100410004;
import static com.hj.casps.common.Constant.SYS_FUNC101100410005;
import static com.hj.casps.common.Constant.SYS_FUNC101100410006;
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
                for (int i = 0; i < protocolModels.size(); i++) {
                    CooperateDirUtils.getInstance(getActivity()).insertInfo(protocolModels.get(i));
                }
                break;
            case 2:
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
            case 2:
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
                        post = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100350001, publicArg.getSys_user(), publicArg.getSys_member(), String.valueOf(page + 1), "10", "submit", protocol_type_j == 0 ? "" : String.valueOf(protocol_type_j), PROTOCOL_TITLE, "");
                        break;
                    case 1:
                        post = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100350002, publicArg.getSys_user(), publicArg.getSys_member(), String.valueOf(page + 1), "10", "pending", protocol_type_j == 0 ? "" : String.valueOf(protocol_type_j), PROTOCOL_TITLE, "");
                        break;
                    case 2:
                        post = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100350003, publicArg.getSys_user(), publicArg.getSys_member(), String.valueOf(page + 1), "10", "running", protocol_type_j == 0 ? "" : String.valueOf(protocol_type_j), PROTOCOL_TITLE, PROTOCOL_STATUS);
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
                        post1 = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100410004, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), String.valueOf(page + 1), "10", "", ORDER_ORDER_ID, String.valueOf(protocol_type_j + 1), ORDER_NAME);
                        url = Constant.QueryMyPendingOrderUrl;
                        break;
                    case 1:
                        post1 = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100410005, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), String.valueOf(page + 1), "10", null, ORDER_ORDER_ID, String.valueOf(protocol_type_j + 1), ORDER_NAME);
                        url = Constant.AppOrderListUrl;
                        break;
                    case 2:
                        //String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String pageno, String pagesize, String orderId, String status, String name, String executeStatus, String startTime, String endTime
                        post1 = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100410006, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), String.valueOf(page + 1), "10", ORDER_ORDER_ID, String.valueOf(protocol_type_j + 1), ORDER_NAME, ORDER_STATUS, START_TIME, END_TIME);
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
                showDialog(0);
                break;
            case R.id.order_abandon:
                showDialog(1);
                break;
        }
    }

    //弹框
    private void showDialog(final int type) {
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
                myDialog.dismiss();
                lockorab(type);
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
    private void lockorab(int type) {
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
        List<ProtocolModelForPost.ID> ids = new ArrayList<>();
        for (int i = 0; i < orderDoingModels.size(); i++) {
            if (orderDoingModels.get(i).isChoice()) {
                ids.add(new ProtocolModelForPost.ID(orderDoingModels.get(i).getId()));
            }
        }
        ProtocolModelForPost post = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100410004, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), ids);
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

    //去post时提交的参数
    private static class ProtocolModelForPost {
        private String sys_token;
        private String sys_uuid;
        private String sys_func;
        private String sys_user;
        private String sys_name;
        private String sys_member;
        private String pageno;
        private String pagesize;
        private String id;
        private String stopStatus;
        private String contract_id;
        private String categoryId;//分类
        private String orderId;//订单头ID
        private String status;//订单类型（1全部2采购3销售）
        private String pagetype;//协议状态（pending:待审批 submit:已提交 running：执行中）
        private String contract_type;//协议类型（采购 1，销售 2）
        private String name;
        private String executeStatus;//订单执行状态0:全部  1：执行中 2：已完成
        private String operate_type;
        private String startTime;
        private String endTime;
        private String contract_status;//协议状态（执行中3，已终止7） 对应x
        private List<ID> mtOrder;

        public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, List<ID> mtOrder) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.mtOrder = mtOrder;
        }

        public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String id, String stopStatus) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.id = id;
            this.stopStatus = stopStatus;
        }

        public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String pageno, String pagesize, String orderId, String status, String name, String executeStatus, String startTime, String endTime) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.pageno = pageno;
            this.pagesize = pagesize;
            this.orderId = orderId;
            this.status = status;
            this.name = name;
            this.executeStatus = executeStatus;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String pageno, String pagesize, String categoryId, String orderId, String status, String name) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.pageno = pageno;
            this.pagesize = pagesize;
            this.categoryId = categoryId;
            this.orderId = orderId;
            this.status = status;
            this.name = name;
        }

        public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String contract_id, String operate_type, String contract_status, String contract_type) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.contract_id = contract_id;
            this.operate_type = operate_type;
            this.contract_status = contract_status;
            this.contract_type = contract_type;
        }

        public ProtocolModelForPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String pageno, String pagesize, String pagetype, String contract_type, String name, String contract_status) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.pageno = pageno;
            this.pagesize = pagesize;
            this.pagetype = pagetype;
            this.contract_type = contract_type;
            this.name = name;
            this.contract_status = contract_status;
        }

        private static class ID {
            private String id;

            public ID(String id) {
                this.id = id;
            }
        }
    }

    //返回的参数，此参数是协议管理的参数
    private static class BackBean {

        /**
         * list : [{"buy_mmb_id":"testschool001","contract_id":"66457abab66947149194c05c6092eb16","contract_status":4,"contract_title":"1234567","end_time":1494864000000,"flow_type":"自取","mmbname":"长城商行","pay_type":"每月","start_time":1494864000000},{"buy_mmb_id":"testschool001","contract_id":"24c81eb3ec4847199ec795087e9aebfc","contract_status":4,"contract_title":"4到7月临时采购","end_time":1498752000000,"flow_type":"自取","mmbname":"长城商行","pay_type":"每月","start_time":1491840000000}]
         * pagecount : 2
         * return_code : 0
         * return_message : 成功!
         */

        private int pagecount;
        private int return_code;
        private String return_message;
        private List<ProtocolListBean> list;

        public int getPagecount() {
            return pagecount;
        }

        public void setPagecount(int pagecount) {
            this.pagecount = pagecount;
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

        public List<ProtocolListBean> getList() {
            return list;
        }

        public void setList(List<ProtocolListBean> list) {
            this.list = list;
        }

//        public static class ListBean {
//            /**
//             * buy_mmb_id : testschool001
//             * contract_id : 66457abab66947149194c05c6092eb16
//             * contract_status : 1待确认，4已拒绝，5中止申请，3已完成，7已中止
//             * contract_title : 1234567
//             * end_time : 1494864000000
//             * flow_type : 自取
//             * mmbname : 长城商行
//             * pay_type : 每月
//             * start_time : 1494864000000
//             */
//
//            private String buy_mmb_id;
//            private String contract_id;
//            private int contract_status;
//            private String contract_title;
//            private long end_time;
//            private String flow_type;
//            private String mmbname;
//            private String pay_type;
//            private long start_time;
//
//            public String getBuy_mmb_id() {
//                return buy_mmb_id;
//            }
//
//            public void setBuy_mmb_id(String buy_mmb_id) {
//                this.buy_mmb_id = buy_mmb_id;
//            }
//
//            public String getContract_id() {
//                return contract_id;
//            }
//
//            public void setContract_id(String contract_id) {
//                this.contract_id = contract_id;
//            }
//
//            public int getContract_status() {
//                return contract_status;
//            }
//
//            public void setContract_status(int contract_status) {
//                this.contract_status = contract_status;
//            }
//
//            public String getContract_title() {
//                return contract_title;
//            }
//
//            public void setContract_title(String contract_title) {
//                this.contract_title = contract_title;
//            }
//
//            public long getEnd_time() {
//                return end_time;
//            }
//
//            public void setEnd_time(long end_time) {
//                this.end_time = end_time;
//            }
//
//            public String getFlow_type() {
//                return flow_type;
//            }
//
//            public void setFlow_type(String flow_type) {
//                this.flow_type = flow_type;
//            }
//
//            public String getMmbname() {
//                return mmbname;
//            }
//
//            public void setMmbname(String mmbname) {
//                this.mmbname = mmbname;
//            }
//
//            public String getPay_type() {
//                return pay_type;
//            }
//
//            public void setPay_type(String pay_type) {
//                this.pay_type = pay_type;
//            }
//
//            public long getStart_time() {
//                return start_time;
//            }
//
//            public void setStart_time(long start_time) {
//                this.start_time = start_time;
//            }
//        }
    }

    //返回的参数，此参数是订单管理的参数
    public static class OrderBackList {

        /**
         * return_code : 0
         * return_message : success
         * rows : [{"buyersAddressId":"f9a6aaa8380a4ee9a5ad59fe81868361","buyersAddressName":"奥森学校中心食堂冷库","buyersName":"奥森学校","createTime":1495105852000,"executeEndTime":1495036800000,"executeStartTime":1495036800000,"executeStatus":1,"getAccount":"6222189929039864012","getBank":"中国建设银行","id":"5fb3e707307c4d379319d9c4ff2661ba","operateTime":1495105852000,"ordertitleCode":10000405,"payAccount":"9953001798001","payBank":"建设银行","payTime":1495036800000,"sellersAddressId":"7633cc6dd2334c8fa1090962b26a4624","sellersAddressName":"哦破rosy送","sellersName":"长城商行","status":3,"stopStatus":1,"totalMoney":3246,"userId":"475814ffe832455dba6ecdf4306b3642","userName":"1212","workflowTypeId":3},{"buyersAddressName":"","buyersId":"testshop001","buyersName":"长城商行","createTime":1495094735000,"executeEndTime":1495036800000,"executeStartTime":1495036800000,"executeStatus":1,"getBank":"","id":"6e0cff206b4c4d218b66db96aedc0511","operateTime":1495094735000,"ordertitleCode":10000308,"payBank":"","sellersAddressName":"","sellersId":"da4383de72494f5d98dc7836d25f526f","sellersName":"cyh","status":2,"stopStatus":1,"totalMoney":30000,"userId":"945fa151f0ce4dc6986ddd13728af39d","userName":"全功能","workflowTypeId":4},{"buyersAddressName":"","buyersId":"testschool001","buyersName":"奥森学校","createTime":1495019702000,"executeEndTime":1494950400000,"executeStartTime":1494950400000,"executeStatus":2,"finishTime":1495160276000,"getAccount":"6222189929039864012","getBank":"中国建设银行","id":"1d43817f78044b369987059d5174d143","lockTime":1495019974000,"operateTime":1495160276000,"ordertitleCode":10000302,"payBank":"","payTime":1495036800000,"sellersAddressId":"7833d058979249ce91028fe174af271e","sellersAddressName":"自摸嗖嗖嗖","sellersId":"testshop001","sellersName":"长城商行","status":4,"stopStatus":4,"totalMoney":400,"userId":"ad4410e4864a41ccbd2b6b4b4914b715","userName":"奥森主管","workflowTypeId":2},{"buyersAddressId":"388f41ee44b04aeea3640140f91f380e","buyersAddressName":"Beijing","buyersName":"³¤³ÇÉÌÐÐ","createTime":1495011940000,"executeEndTime":1494864000000,"executeStartTime":1494864000000,"executeStatus":1,"getAccount":"","getBank":"","id":"621d0f2516414bf484ba5d979f40742e","operateTime":1495011940000,"ordertitleCode":10099000,"payAccount":"123214453454","payBank":"ÖÐ¹úÒøÐÐ","payTime":1494864000000,"sellersAddressId":"","sellersAddressName":"","sellersName":"Áõ³¤³Ç","status":2,"stopStatus":1,"totalMoney":15996,"userId":"a29d2326763546a4b0063c202cff08ff","workflowTypeId":3},{"buyersAddressName":"","buyersId":"testschool001","buyersName":"奥森学校","createTime":1494994281000,"executeEndTime":1494950400000,"executeStartTime":1494950400000,"executeStatus":1,"getAccount":"6222189929039864012","getBank":"中国建设银行","id":"12bafd55844a4f82acdd13984c830214","operateTime":1494994281000,"ordertitleCode":10000301,"payBank":"","payTime":1494950400000,"sellersAddressId":"7833d058979249ce91028fe174af271e","sellersAddressName":"自摸嗖嗖嗖","sellersId":"testshop001","sellersName":"长城商行","status":3,"stopStatus":1,"totalMoney":4000,"userId":"94c108267af94624bcf99171690741cf","userName":"全功能测试","workflowTypeId":3},{"buyersAddressName":"","buyersId":"da4383de72494f5d98dc7836d25f526f","buyersName":"cyh","createTime":1494994179000,"executeEndTime":1494950400000,"executeStartTime":1494950400000,"executeStatus":1,"getAccount":"6222189929039864012","getBank":"中国建设银行","id":"805079325749452499d48a22984d7c01","operateTime":1494994179000,"ordertitleCode":10000300,"payBank":"","payTime":1494950400000,"sellersAddressId":"78d5b989f2f3436f9b871a4756ac0657","sellersAddressName":"我可以","sellersId":"testshop001","sellersName":"长城商行","status":3,"stopStatus":1,"totalMoney":10000,"userId":"94c108267af94624bcf99171690741cf","userName":"全功能测试","workflowTypeId":2},{"buyersAddressId":"388f41ee44b04aeea3640140f91f380e","buyersAddressName":"Beijing","buyersName":"³¤³ÇÉÌÐÐ","createTime":1494980437000,"executeEndTime":1494864000000,"executeStartTime":1494864000000,"executeStatus":1,"getAccount":"","getBank":"","id":"9e69d04389354459af1751ce63190b3e","operateTime":1494980437000,"ordertitleCode":10098900,"payAccount":"123214453454","payBank":"ÖÐ¹úÒøÐÐ","payTime":1494864000000,"sellersAddressId":"","sellersAddressName":"","sellersName":"Áõ³¤³Ç","status":2,"stopStatus":1,"totalMoney":15996,"userId":"a29d2326763546a4b0063c202cff08ff","workflowTypeId":3},{"buyersAddressId":"adbc73908b5841e3bc61ff12780e9610","buyersAddressName":"","buyersId":"testschool001","buyersName":"奥森学校","createTime":1491898956000,"executeEndTime":1492185600000,"executeStartTime":1492185600000,"executeStatus":1,"getAccount":"68900055338","getBank":"","id":"514a1de67b104c688c000e9c84a15ec6","operateTime":1491898956000,"ordertitleCode":10000003,"payAccount":"9953001798001","payBank":"","payTime":1492012800000,"sellersAddressId":"d2f7b7029fb341879ba3b009376dd274","sellersAddressName":"","sellersId":"a844cb008f2643a4b5ec7d9fc664f1ce","sellersName":"北京尚德粮油商贸公司","status":2,"stopStatus":1,"totalMoney":130,"userId":"ad4410e4864a41ccbd2b6b4b4914b715","userName":"奥森主管","workflowTypeId":2},{"buyersAddressName":"","buyersId":"da4383de72494f5d98dc7836d25f526f","buyersName":"cyh","createTime":1491887871000,"executeEndTime":1492444800000,"executeStartTime":1492444800000,"executeStatus":1,"getAccount":"63980011596","getBank":"中信银行","id":"e9508ce21602448bab348c7464b6c5d4","operateTime":1491887871000,"ordertitleCode":10000002,"payBank":"","payTime":1492185600000,"sellersAddressId":"ff3bce69cc094a0ca3488e833c40e5bb","sellersAddressName":"北京市朝阳区立水桥5号库","sellersId":"testshop001","sellersName":"长城商行","status":3,"stopStatus":1,"totalMoney":24,"userId":"945fa151f0ce4dc6986ddd13728af39d","userName":"全功能","workflowTypeId":2},{"buyersAddressId":"f418e4c02c824b4b80862b2942bce6a4","buyersAddressName":"","buyersId":"72365baa7dd34594b890045cf41efed3","buyersName":"cyb","createTime":1491465920000,"executeEndTime":1491408000000,"executeStartTime":1491408000000,"executeStatus":2,"finishTime":1491470953000,"getAccount":"61272461165061","getBank":"","id":"f9ba1542f4584148a2cc187d82c292d2","lockTime":1491465987000,"operateTime":1491465987000,"ordertitleCode":10038640,"payAccount":"6154613216584","payBank":"","payTime":1491408000000,"sellersAddressId":"636b30c669cb4a8ba4ba0c8d88cf3224","sellersAddressName":"","sellersId":"da4383de72494f5d98dc7836d25f526f","sellersName":"cyh","status":4,"stopStatus":1,"totalMoney":4300,"userId":"be89786782e049a18f54f5730263f861","userName":"cyh","workflowTypeId":2}]
         * total : 157
         */

        private int return_code;
        private String return_message;
        private int total;
        private List<OrderRowBean> rows;

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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<OrderRowBean> getRows() {
            return rows;
        }

        public void setRows(List<OrderRowBean> rows) {
            this.rows = rows;
        }
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
            ProtocolModelForPost post = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100350001, publicArg.getSys_user(), publicArg.getSys_member(), id, refuse, String.valueOf(i), String.valueOf(protocol_type_j));
            OkGo.post(Constant.OperateContractUrl)
                    .params("param", mGson.toJson(post))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            BackBean backDetail = mGson.fromJson(s, BackBean.class);
                            if (backDetail.getReturn_code() != 0) {
                                Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
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
            ProtocolModelForPost post = new ProtocolModelForPost(publicArg.getSys_token(), getUUID(), SYS_FUNC101100350001, publicArg.getSys_user(), publicArg.getSys_member(), id, refuse);
            OkGo.post(Constant.StopOrderUrl)
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

        /**
         * 加数据
         */
//        public void getDatas(String id) {
//
//            pInfo = new PayMnetInfo();
//            pInfo.setBillsId(id);
//            pInfo.setBillsType("销售");
//            pInfo.setFlowId("先货后款");
//            pInfo.setBuyer("奥森学院");
//            pInfo.setContractTime("2017-04-26");
//            pInfo.setPaymentTime("2017-04-26");
//            pInfo.setDeliveryStarTime("2017-04-26");
//            pInfo.setDeliveryEndTime("2017-04-26");
//            pInfo.setTotalMoney("20000");
//            pInfo.setPayId("暂无");
//            pInfo.setPayId("中信银行");
//            pInfo.setHarvestAddress("北京海淀区");
//            pInfo.setAlready_money("3000");
//            pInfo.setAwait_money("17000");
//            pInfo.setNow_money("1700");
//            pInfo.setPayment_remark("没有备注");
//
//
//        }
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
