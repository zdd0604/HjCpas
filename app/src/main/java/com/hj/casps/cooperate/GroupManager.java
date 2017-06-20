package com.hj.casps.cooperate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyListView;
import com.hj.casps.ui.MyToast;
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

import static com.hj.casps.common.Constant.SYS_FUNC101100310004;
import static com.hj.casps.common.Constant.SYS_FUNC101100410004;
import static com.hj.casps.common.Constant.getUUID;

//群组管理，选择供应商，谁关注我
public class GroupManager extends ActivityBaseHeader implements View.OnClickListener, OnPullListener {

    private TextView cooperate_group_info;
    private MyListView group_list;
    private List<GroupManagerListBean> cooperateGroupModels;
    private List<WhoCareListBean> whocaremes;
    private ListView who_care_me;
    private CooperateGroupAdapter adapter;
    private WhoCareMeAdapter whoCareMeAdapter;
    private String groupName = "";
    private ScrollView scroll_list;
    private int type;//0群组管理，1选择供应商，2谁关注我
    @BindView(R.id.layout_group_button_check1)
    LinearLayout layout_group_button_check1;
//    private String url = HTTPURL + "appMemberRelationship/queryGroupMan.app";
//    private String get_all_url = HTTPURL + "appMemberRelationship/getAllMember.app";
//    private String get_url = HTTPURL + "appMemberRelationship/getAllMarkedMeMembers.app";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initData();
                    break;

            }
        }
    };
    private String goods_catergory = "";
    private String member_name = "";
    private String province = "";
    private int pageno = 1;
    private int pagesize = 20;
    private Button add_care_me;
    private CheckBox select_all_order;
    private TextView order_lock;
    private TextView order_abandon;
    private LinearLayout order_check_function;
    private MyDialog myDialog;
    private AbsRefreshLayout mLoader;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == 22) {
            groupName = data.getExtras().getString("groupName");
            member_name = data.getExtras().getString("cares");
            province = data.getExtras().getString("province");
            handler.sendEmptyMessage(0);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manager);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra(Constant.PROTOCOL_TYPE, Constant.protocol_0);
        initView();
        if (hasInternetConnected()) {
            initData();
        } else {
            addLocality();
        }
    }

    /**
     * 保存数据库
     */
    private void saveDaoData() {
        CooperateDirUtils.getInstance(this).deleteAll();
        switch (type) {
            case 0:
                for (int i = 0; i < cooperateGroupModels.size(); i++) {
                    CooperateDirUtils.getInstance(this).insertInfo(cooperateGroupModels.get(i));
                }
                break;
            case 1:
                for (int i = 0; i < whocaremes.size(); i++) {
                    CooperateDirUtils.getInstance(this).insertInfo(whocaremes.get(i));
                }
                break;
            case 2:
                for (int i = 0; i < whocaremes.size(); i++) {
                    CooperateDirUtils.getInstance(this).insertInfo(whocaremes.get(i));
                }
                break;
        }


    }


    /**
     * 加载本地数据
     */
    private void addLocality() {
        switch (type) {
            case 0:
                List<GroupManagerListBean> usrList = CooperateDirUtils.getInstance(this).queryGroupManagerListBeanInfo();
                if (usrList.size() > 0) {
                    cooperateGroupModels = usrList;
                }
                adapter.updateRes(cooperateGroupModels);
                break;
            case 1:
            case 2:
                List<WhoCareListBean> usrList1 = CooperateDirUtils.getInstance(this).queryWhoCareListBeanInfo();
                if (usrList1.size() > 0) {
                    whocaremes = usrList1;
                }
                whoCareMeAdapter.updateRes(whocaremes);
                break;
        }


    }

    //加载网络数据，列举群组管理，选择供应商，谁关注我列表
    private void initData() {
        if (type == 0) {
            cooperateGroupModels = new ArrayList<>();
            QueryMMBConcerns concerns = new QueryMMBConcerns(
                    publicArg.getSys_token(),
                    Constant.getUUID(),
                    SYS_FUNC101100310004,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    groupName);

            OkGo.post(Constant.QueryGroupManUrl)
                    .tag(this)
                    .params("param", mGson.toJson(concerns))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            GroupBack backDetail = mGson.fromJson(s, GroupBack.class);
                            if (backDetail.getReturn_code() != 0) {
                                toast(backDetail.getReturn_message());
                            } else {
                                cooperateGroupModels = backDetail.getList();
                                if (cooperateGroupModels.isEmpty()) {
                                    toast("没有群组");
                                    adapter.removeAll();
                                }
                                adapter.updateRes(cooperateGroupModels);
                                saveDaoData();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            toast(e.getMessage());
                        }
                    });
        } else {
            whocaremes = new ArrayList<>();
            QueryMMBConcerns concerns = new QueryMMBConcerns(
                    publicArg.getSys_token(),
                    getUUID(), SYS_FUNC101100310004,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    member_name,
                    province,
                    String.valueOf(pageno),
                    String.valueOf(pagesize));
            OkGo.post(type == 1 ? Constant.GetAllMarkedMeMembersUrl : Constant.GetAllMemberUrl)
                    .tag(this)
                    .params("param", mGson.toJson(concerns))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            WhoCareMe whoCareMe = mGson.fromJson(s, WhoCareMe.class);
                            if (whoCareMe.getReturn_code() != 0) {
                                toast(whoCareMe.getReturn_message());
                            } else {
                                whocaremes = whoCareMe.getList();
                                if (whocaremes.isEmpty()) {
                                    toast("没有结果");
                                    whoCareMeAdapter.removeAll();
                                }
                                if (pageno != 1) {
                                    if (pageno - 1 <= (whoCareMe.getPagecount() - 1) / 20) {
                                        whoCareMeAdapter.addRes(whocaremes);

                                    } else {
                                        mLoader.onLoadAll();
                                    }
                                } else {
                                    whoCareMeAdapter.updateRes(whocaremes);
                                    saveDaoData();
                                }

                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            toast(e.getMessage());
                        }
                    });

        }

    }

    //加载布局
    private void initView() {
        cooperate_group_info = (TextView) findViewById(R.id.cooperate_group_info);
        group_list = (MyListView) findViewById(R.id.group_list);
        cooperate_group_info.setOnClickListener(this);
        adapter = new CooperateGroupAdapter(cooperateGroupModels, this, R.layout.item_group_manager);
        whoCareMeAdapter = new WhoCareMeAdapter(whocaremes, this, R.layout.cooperate_item_only, type);
        who_care_me = (ListView) findViewById(R.id.who_care_me);
        group_list.setAdapter(adapter);
        who_care_me.setAdapter(whoCareMeAdapter);
        scroll_list = (ScrollView) findViewById(R.id.scroll_list);
        add_care_me = (Button) findViewById(R.id.add_care_me);
        add_care_me.setOnClickListener(this);
        select_all_order = (CheckBox) findViewById(R.id.select_all_order);
        order_lock = (TextView) findViewById(R.id.order_lock);
        order_lock.setOnClickListener(this);
        order_abandon = (TextView) findViewById(R.id.order_abandon);
        order_abandon.setOnClickListener(this);
        order_check_function = (LinearLayout) findViewById(R.id.order_check_function);

        switch (type) {
            case 0:
                setTitle(getString(R.string.cooperate_group));
                who_care_me.setVisibility(View.GONE);
                scroll_list.setVisibility(View.VISIBLE);
                break;
            case 1:
                setTitle(getString(R.string.cooperate_mmb_care));
                who_care_me.setVisibility(View.VISIBLE);
                scroll_list.setVisibility(View.GONE);
                mLoader = new NestRefreshLayout(who_care_me);
                mLoader.setOnLoadingListener(this);
                mLoader.setPullLoadEnable(true);
                mLoader.setPullRefreshEnable(true);
                break;
            case 2:
                setTitle(getString(R.string.cooperate_mmb_search));
                who_care_me.setVisibility(View.VISIBLE);
                scroll_list.setVisibility(View.GONE);
                add_care_me.setVisibility(View.VISIBLE);
                order_check_function.setVisibility(View.VISIBLE);
                mLoader = new NestRefreshLayout(who_care_me);
                mLoader.setOnLoadingListener(this);
                mLoader.setPullLoadEnable(true);
                mLoader.setPullRefreshEnable(true);
                break;
        }

        select_all_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                selectAll(b);
            }
        });
        layout_group_button_check1.setOnClickListener(new View.OnClickListener() {
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
        for (int i = 0; i < whocaremes.size(); i++) {
            whocaremes.get(i).setChoice(b);
        }
        whoCareMeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cooperate_group_info:
                switch (type) {
                    case 0:
                        CreateDialog(Constant.DIALOG_CONTENT_17);

                        break;
                    case 1:
                        CreateDialog(Constant.DIALOG_CONTENT_37);
                        break;
                    case 2:
                        CreateDialog(Constant.DIALOG_CONTENT_38);
                        break;
                }

                break;
            case R.id.add_care_me:
                showDia(0);
                break;
            case R.id.order_lock:
                showDia(1);
                break;
            case R.id.order_abandon:
                showDia(2);
                break;
        }
    }

    //弹出对话框
    private void showDia(final int type) {
        int a = 0;
        for (int i = 0; i < whocaremes.size(); i++) {
            if (whocaremes.get(i).isChoice()) {
                a++;
            }
        }
        if (a == 0) {
            toast("请先选择企业");
            return;
        }
        myDialog = new MyDialog(this);
        switch (type) {
            case 0:
                myDialog.setMessage(getString(R.string.cooperate_add_dialog));
                break;
            case 1:
                myDialog.setMessage(getString(R.string.cooperate_buy_dialog));
                break;
            case 2:
                myDialog.setMessage(getString(R.string.cooperate_sell_dialog));
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

    //对供应商加关注，建立采购或者销售关系
    private void lockorab(final int type) {
        String url = "";
        switch (type) {
            case 0:
                //加关注
                url = Constant.MarkMemberUrl;
                break;
            case 1:
            case 2:
                //建立销售，采购
                url = Constant.CreateCooperatorsUrl;
                break;
        }
        String ids = "";
        for (int i = 0; i < whocaremes.size(); i++) {
            if (whocaremes.get(i).isChoice()) {
                ids = ids.isEmpty() ? whocaremes.get(i).getMember_id() : ids + "," + whocaremes.get(i).getMember_id();
            }
        }
        QueryMMBConcerns post = new QueryMMBConcerns(publicArg.getSys_token(), getUUID(), SYS_FUNC101100410004, publicArg.getSys_user(), publicArg.getSys_member(), ids, String.valueOf(type));
        OkGo.post(url)
                .params("param", mGson.toJson(post))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        WhoCareMe backDetail = mGson.fromJson(s, WhoCareMe.class);
                        if (backDetail == null) {
                            return;
                        }
                        if (backDetail.getReturn_code() != 0) {
                            Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                        } else {
                            if (type == 0) {
                                new MyToast(context, "已关注");
                            } else {
                                new MyToast(context, "操作成功，待审核");

                            }
                            handler.sendEmptyMessage(0);
                            select_all_order.setChecked(false);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //搜索
    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt("type", type + 3);
        intentActivity(CooperateGroupSearch.class, 11, bundle);
    }

    //下拉刷新
    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageno = 1;
        initData();
        mLoader.onLoadFinished();//加载结束
    }

    //上拉加载
    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageno++;
        initData();
        mLoader.onLoadFinished();//加载结束
    }

    /**
     * 搜索群组提交类，搜索供应商，谁关注我，通用
     */
    public static class QueryMMBConcerns {
        private String sys_token;
        private String sys_uuid;
        private String sys_func;
        private String sys_user;
        private String sys_member;
        private String group_name;
        private String goods_catergory;
        private String member_name;
        private String province;
        private String pageno;
        private String pagesize;
        private String member_ids;
        private String biz_type;

        public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String member_ids, String biz_type) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.member_ids = member_ids;
            this.biz_type = biz_type;
        }

        //查询关注我的会员列表（企业专用）
        public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String member_name, String province, String pageno, String pagesize) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.member_name = member_name;
            this.province = province;
            this.pageno = pageno;
            this.pagesize = pagesize;
        }

        //查询所有供应商（学校专用）
        public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String goods_catergory, String member_name, String province, String pageno, String pagesize) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.goods_catergory = goods_catergory;
            this.member_name = member_name;
            this.province = province;
            this.pageno = pageno;
            this.pagesize = pagesize;
        }

        public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String group_name) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.group_name = group_name;
        }
    }

    //群组管理返回类
    private static class GroupBack {

        /**
         * list : [{"groupName":"广东高校群","groupStatus":1,"id":"gd001","remark":"1"},{"groupName":"广东教育厅认证供应商","groupStatus":1,"id":"gd002","remark":"1"},{"groupName":"广东教育厅认证生产商","groupStatus":1,"id":"gd003","remark":"1"},{"groupName":"广东中学群","groupStatus":1,"id":"gd004","remark":"1"},{"groupName":"测试群","groupStatus":1,"id":"gd005","remark":"1"},{"groupName":"云南高校","groupStatus":1,"id":"yn010","remark":"1"},{"groupName":"云南教育厅认证供应商","groupStatus":1,"id":"yn030","remark":"1"},{"groupName":"云南教育厅帮扶合作社","groupStatus":1,"id":"yn050","remark":"1"},{"groupName":"云南教育厅直供基地","groupStatus":1,"id":"yn031","remark":"1"}]
         * return_code : 0
         * return_message : 成功!
         */

        private int return_code;
        private String return_message;
        private List<GroupManagerListBean> list;

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

        public List<GroupManagerListBean> getList() {
            return list;
        }

        public void setList(List<GroupManagerListBean> list) {
            this.list = list;
        }

//        public static class ListBean {
//            /**
//             * groupName : 广东高校群
//             * groupStatus : 1
//             * id : gd001
//             * remark : 1
//             */
//
//            private String groupName;
//            private int groupStatus;
//            private String id;
//            private String remark;
//
//            public String getGroupName() {
//                return groupName;
//            }
//
//            public void setGroupName(String groupName) {
//                this.groupName = groupName;
//            }
//
//            public int getGroupStatus() {
//                return groupStatus;
//            }
//
//            public void setGroupStatus(int groupStatus) {
//                this.groupStatus = groupStatus;
//            }
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getRemark() {
//                return remark;
//            }
//
//            public void setRemark(String remark) {
//                this.remark = remark;
//            }
//        }
    }

    //群组管理适配器
    private class CooperateGroupAdapter extends WZYBaseAdapter<GroupManagerListBean> {
        private Context context;

        public CooperateGroupAdapter(List<GroupManagerListBean> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
            this.context = context;

        }

        @Override
        public void bindData(ViewHolder holder, final GroupManagerListBean cooperateModel, final int indexPos) {

            TextView name = (TextView) holder.getView(R.id.cooperate_group_name);
            name.setText(cooperateModel.getGroupName());
            FancyButton state = (FancyButton) holder.getView(R.id.cooperate_group_state);
            switch (cooperateModel.getGroupStatus()) {
                case 0:
                    state.setText(context.getString(R.string.cooperate_group_add));
                    state.setBackgroundColor(context.getResources().getColor(R.color.title_bg));
                    state.setTextColor(context.getResources().getColor(R.color.white));
                    state.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            postAddGroup(true, cooperateModel.getId());

                        }
                    });
                    break;
                case 1:
                    break;
                case 2:
                    state.setText(context.getString(R.string.cooperate_group_gone));
                    state.setBackgroundColor(context.getResources().getColor(R.color.white));
                    state.setTextColor(context.getResources().getColor(R.color.grey));
                    state.setBorderColor(context.getResources().getColor(R.color.hint_color));
                    state.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final MyDialog myDialog = new MyDialog(context);
                            myDialog.setMessage(context.getString(R.string.cooperate_dialog_gone));
                            myDialog.setYesOnclickListener(context.getString(R.string.True), new MyDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {
                                    myDialog.dismiss();
                                    postAddGroup(false, cooperateModel.getId());

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
                    });

                    break;
            }


        }

        private void postAddGroup(final boolean b, String id) {
            String url;
            if (b) {
                url = Constant.AdduserforGroupUrl;
            } else {
                url = Constant.DeleteUserforGroupUrl;

            }
            QueryMMBConcerns concerns = new QueryMMBConcerns(
                    publicArg.getSys_token(),
                    Constant.getUUID(),
                    SYS_FUNC101100310004,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    id);
            String s = mGson.toJson(concerns);
            s = s.replace("group_name", "group_id");
            OkGo.post(url)
                    .tag(this)
                    .params("param", s)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            GroupBack backDetail = mGson.fromJson(s, GroupBack.class);
                            if (backDetail.getReturn_code() != 0) {
                                toast(backDetail.getReturn_message());
                            } else {
                                if (b) {
                                    new MyToast(context, context.getString(R.string.cooperate_group_grant));

                                } else {
                                    new MyToast(context, context.getString(R.string.cooperate_dialog_gone_ok));

                                }
                                initData();
                            }
                        }
                    });

        }
    }

    /**
     * 搜索供应商，谁关注我，返回参数
     */
    private static class WhoCareMe {

        /**
         * list : [{"member_id":"testschool001","member_name":"奥森学校","mmbhomepage":"http://members.nxdj.org.cn/奥森学校.html"},{"member_id":"c35e4315f7804f52b27e403a812deec6","member_name":"新美农业合作社","mmbhomepage":"http://members.nxdj.org.cn/新美农场.html"},{"member_id":"459f3498633d4952a293dc360e2b7c97","member_name":"天坛学院","mmbhomepage":"http://106.2.221.174:2000/v2content/mmbhtml/天坛学院.html"},{"member_id":"55da4721cd7049e186a17745a795a6cd","member_name":"天美贸易公司","mmbhomepage":""},{"member_id":"d5d1244c85d5478ca88a253b509f8bed","member_name":"北京交通大学","mmbhomepage":""},{"member_id":"19dae428c9ad44ce86011d786ead53f1","member_name":"北京冯氏商贸有限公司","mmbhomepage":"http://members.nxdj.org.cn/北京冯氏商贸.html"},{"member_id":"a9c1512052164391ab1fcc2ffa67b0ec","member_name":"北京试用的学校","mmbhomepage":""},{"member_id":"da4383de72494f5d98dc7836d25f526f","member_name":"cyh","mmbhomepage":"http://members.nxdj.org.cn/cyh.html"}]
         * pagecount : 8
         * return_code : 0
         * return_message : 成功!
         */

        private int pagecount;
        private int return_code;
        private String return_message;
        private List<WhoCareListBean> list;

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

        public List<WhoCareListBean> getList() {
            return list;
        }

        public void setList(List<WhoCareListBean> list) {
            this.list = list;
        }

//        public static class ListBean {
//            /**
//             * member_id : testschool001
//             * member_name : 奥森学校
//             * mmbhomepage : http://members.nxdj.org.cn/奥森学校.html
//             */
//
//            private String member_id;
//            private String member_name;
//            private String mmbhomepage;
//            private boolean choice;
//
//            public boolean isChoice() {
//                return choice;
//            }
//
//            public void setChoice(boolean choice) {
//                this.choice = choice;
//            }
//
//            public String getMember_id() {
//                return member_id;
//            }
//
//            public void setMember_id(String member_id) {
//                this.member_id = member_id;
//            }
//
//            public String getMember_name() {
//                return member_name;
//            }
//
//            public void setMember_name(String member_name) {
//                this.member_name = member_name;
//            }
//
//            public String getMmbhomepage() {
//                return mmbhomepage;
//            }
//
//            public void setMmbhomepage(String mmbhomepage) {
//                this.mmbhomepage = mmbhomepage;
//            }
//        }
    }

    //选择供应商 谁关注我的适配器
    private class WhoCareMeAdapter extends WZYBaseAdapter<WhoCareListBean> {
        private int type;

        public WhoCareMeAdapter(List<WhoCareListBean> data, Context context, int layoutRes, int type) {
            super(data, context, layoutRes);
            this.type = type;
        }

        @Override
        public void bindData(ViewHolder holder, final WhoCareListBean listBean, final int indexPos) {
            TextView name = (TextView) holder.getView(R.id.cooperate_name);
            name.setText(listBean.getMember_name());
            CheckBox layout_check_order_going = (CheckBox) holder.getView(R.id.layout_check_order_going);
            if (type == 1) {
                layout_check_order_going.setVisibility(View.GONE);
            } else {
                layout_check_order_going.setVisibility(View.VISIBLE);
                layout_check_order_going.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        listBean.setChoice(b);
                    }
                });
                layout_check_order_going.setChecked(listBean.isChoice());
            }


        }
    }


}
