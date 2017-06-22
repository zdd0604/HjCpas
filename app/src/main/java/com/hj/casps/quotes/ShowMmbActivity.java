package com.hj.casps.quotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.cooperate.CooperateGroupSearch;
import com.hj.casps.entity.appQuote.MmbBack;
import com.hj.casps.entity.appQuote.ShowMmbModel;
import com.hj.casps.ui.MyListView;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC;

//报价检索的会员列表，用来选择会员，选择群组
public class ShowMmbActivity extends ActivityBaseHeader2 implements View.OnClickListener {
    private int type;
    private int group;
    private String url_show;
    private String quoteId;
    private List<ShowMmbModel> showMmbModels;
    private ShowMmbAdapter adapter;
    private String intentString;
    @BindView(R.id.layout_button_check1)
    LinearLayout layout_button_check1;
    @BindView(R.id.title_mmb)
    TextView title_mmb;
    @BindView(R.id.show_mmb_list)
    MyListView show_mmb_list;
    @BindView(R.id.show_mmb_check)
    CheckBox show_mmb_check;
    @BindView(R.id.layout_bottom_mmb_reset)
    TextView layout_bottom_mmb_reset;
    @BindView(R.id.layout_bottom_mmb_true)
    TextView layout_bottom_mmb_true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initData(intentString);
                    break;

            }
        }
    };

    //搜索报价会员后返回的结果显示
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 22) {
            intentString = data.getExtras().getString("searchjson");
            handler.sendEmptyMessage(0);
        }
    }

    //安卓基本方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mmb);
        ButterKnife.bind(this);
        initData(intentString);
        initView();
    }

    //检索所有会员列表
    private void initData(String intentString) {
        type = getIntent().getIntExtra("type", 1);
//        type = 1 - type1;
        group = getIntent().getIntExtra("group", 0);
        quoteId = getIntent().getStringExtra("quoteId");
        switch (group) {
            case 0:
                if (intentString == null || intentString.isEmpty()) {
                    url_show = Constant.ShowMmbUrl +
                            "?param={\"sys_func\":\"" + SYS_FUNC + "\"," +
                            "\"sys_member\":\"" + publicArg.getSys_member() + "\"," +
                            "\"mmbId\":\"" + publicArg.getSys_member() + "\"," +
                            "\"sys_name\":\"" + publicArg.getSys_name() + "\"," +
                            "\"sys_token\":\"" + publicArg.getSys_token() + "\"," +
                            "\"sys_user\":\"" + publicArg.getSys_user() + "\"," +
                            "\"sys_uuid\":\"" + Constant.getUUID() + "\"," +
                            "\"type\":\"" + String.valueOf(type) + "\"," +
                            "\"pageno\":\"1\",\"pagesize\":\"5\"}";
                } else {
                    url_show = Constant.ShowMmbUrl + "?param=" + intentString;
                }
                break;
            case 1:
                if (intentString == null || intentString.isEmpty()) {
                    url_show = Constant.ShowGroupUrl +
                            "?param={\"sys_func\":\"" + Constant.SYS_FUNC + "\"," +
                            "\"sys_member\":\"" + publicArg.getSys_member() + "\"," +
                            "\"mmbId\":\"" + publicArg.getSys_member() + "\"," +
                            "\"sys_name\":\"" + publicArg.getSys_name() + "\"," +
                            "\"sys_token\":\"" + publicArg.getSys_token() + "\"," +
                            "\"sys_user\":\"" + publicArg.getSys_user() + "\"," +
                            "\"sys_uuid\":\"" + Constant.getUUID() + "\"," +
                            "\"type\":\"" + String.valueOf(type) + "\"," +
                            "\"pageno\":\"1\"," +
                            "\"pagesize\":\"5\"}";

                } else {
                    url_show = Constant.ShowGroupUrl + "?param=" + intentString;
                }
                break;
        }
        OkGo.get(url_show).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                MmbBack mmbBack = gson.fromJson(s, MmbBack.class);
                if (mmbBack.getReturn_code() != 0) {
                    toast(mmbBack.getReturn_message());
                    return;
                }else if(mmbBack.getReturn_code()==1101||mmbBack.getReturn_code()==1102){
                    toastSHORT("重复登录或令牌超时");
                    LogoutUtils.exitUser(ShowMmbActivity.this);
                }
//                showMmbModels = new ArrayList<>();
                switch (group) {
                    case 0:
                        showMmbModels = mmbBack.getList();
                        break;
                    case 1:
                        showMmbModels = mmbBack.getGroupList();
                        break;
                }

                if (showMmbModels == null || showMmbModels.isEmpty()) {
                    adapter.removeAll();
                }
                adapter.updateRes(showMmbModels);
                show_mmb_check.setChecked(false);
            }
        });

    }

    //加载此页面
    private void initView() {
        setTitle(getString(R.string.quotes_assign_mmb));
        setTitleRight(null, ResourcesCompat.getDrawable(getResources(), R.mipmap.nav_ser, null));
        titleRight.setEnabled(true);
        adapter = new ShowMmbAdapter(showMmbModels, this, R.layout.item_show_mmb, group);
        layout_bottom_mmb_true.setOnClickListener(this);
        layout_button_check1.setOnClickListener(this);
        show_mmb_check.setOnClickListener(this);
        layout_bottom_mmb_reset.setOnClickListener(this);
        show_mmb_list.setAdapter(adapter);
        switch (group) {
            case 0:
                title_mmb.setText("合作会员");
                break;
            case 1:
                title_mmb.setText("群组");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_button_check1:
                if (showMmbModels != null && showMmbModels.size() > 0)
                    if (show_mmb_check.isChecked()) {
                        show_mmb_check.setChecked(false);
                        selectAll(false);
                    } else {
                        show_mmb_check.setChecked(true);
                        selectAll(true);
                    }
                break;
            case R.id.show_mmb_check:
                if (showMmbModels != null && showMmbModels.size() > 0)
                    if (show_mmb_check.isChecked()) {
                        selectAll(true);
                    } else {
                        selectAll(false);
                    }
                break;
            case R.id.layout_bottom_mmb_reset:
                show_mmb_check.setChecked(false);
                if (showMmbModels != null && showMmbModels.size() > 0) {
                    for (int i = 0; i < showMmbModels.size(); i++) {
                        showMmbModels.get(i).setChoose(false);
                        adapter.updateRes(showMmbModels);
                    }
                }
                break;
            case R.id.layout_bottom_mmb_true:
                submit();
                break;
        }
    }

    /**
     * 先择
     *
     * @param b
     */
    private void selectAll(boolean b) {
        for (int i = 0; i < showMmbModels.size(); i++) {
            showMmbModels.get(i).setChoose(b);
            adapter.updateRes(showMmbModels);
        }
    }

    //点击搜索
    @Override
    protected void onRightClick() {
        super.onRightClick();
        Intent intent = new Intent(this, CooperateGroupSearch.class);
        intent.putExtra("type", group);
        intent.putExtra("typeQ", type);
        startActivityForResult(intent, 11);
    }

    //提交选择的合作会员已经群组
    private void submit() {
        String ids = "";
        switch (group) {
            case 0:
                if (showMmbModels != null && showMmbModels.size() > 0) {
                    for (int i = 0; i < showMmbModels.size(); i++) {
                        if (showMmbModels.get(i).isChoose()) {
                            if (ids == null || ids.isEmpty()) {
                                ids = showMmbModels.get(i).getMmbId();
                            } else {
                                ids = ids + "," + showMmbModels.get(i).getMmbId();
                            }
                        }
                    }

                    OkGo.post(Constant.AddMmbIdsUrl).params("param",
                            "{\"sys_func\":\"" + SYS_FUNC + "\"," +
                                    "\"sys_member\":\"" + publicArg.getSys_member() + "\"," +
                                    "\"sys_name\":\"" + publicArg.getSys_name() + "\"," +
                                    "\"sys_token\":\"" + publicArg.getSys_token() + "\"," +
                                    "\"sys_user\":\"" + publicArg.getSys_user() + "\"," +
                                    "\"sys_uuid\":\" " + Constant.SYS_FUNC + " \"," +
                                    "\"mmbIds\":\"" + ids + "\"," +
                                    "\"quoteId\":\"" + quoteId + "\"," +
                                    "\"rangType\":\"1\"}")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    MmbBack mmbBack = gson.fromJson(s, MmbBack.class);
                                    if (mmbBack.getReturn_code() != 0) {
                                        toast(mmbBack.getReturn_message());
                                    }else if(mmbBack.getReturn_code()==1101||mmbBack.getReturn_code()==1102){
                                        toastSHORT("重复登录或令牌超时");
                                        LogoutUtils.exitUser(ShowMmbActivity.this);
                                    }


                                    else {
                                        toast("添加成功");
                                        setResult(22);
                                        finish();
                                    }
                                }
                            });
                } else {
                    finish();
                }
                break;
            case 1:
                if (showMmbModels != null && showMmbModels.size() > 0) {
                    for (int i = 0; i < showMmbModels.size(); i++) {
                        if (showMmbModels.get(i).isChoose()) {
                            if (ids == null || ids.isEmpty()) {
                                ids = showMmbModels.get(i).getId();
                            } else {
                                ids = ids + "," + showMmbModels.get(i).getId();
                            }
                        }
                    }

                    OkGo.post(Constant.AddGroupIdsUrl).params("param",
                            "{\"sys_func\":\"" + SYS_FUNC + "\"," +
                                    "\"sys_member\":\"" + publicArg.getSys_member() + "\"," +
                                    "\"sys_name\":\"" + publicArg.getSys_name() + "\"," +
                                    "\"sys_token\":\"" + publicArg.getSys_token() + "\"," +
                                    "\"sys_user\":\"" + publicArg.getSys_user() + "\"," +
                                    "\"sys_uuid\":\"" + Constant.getUUID() + "\"," +
                                    "\"groupIds\":\"" + ids + "\"," +
                                    "\"quoteId\":\"" + quoteId + "\"," +
                                    "\"rangType\":\"1\"}")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {

                                    Gson gson = new Gson();
                                    MmbBack mmbBack = gson.fromJson(s, MmbBack.class);
                                    if (mmbBack.getReturn_code() != 0) {
                                        toast(mmbBack.getSuccessMsg());
                                    }else if(mmbBack.getReturn_code()==1101||mmbBack.getReturn_code()==1102){
                                        toastSHORT("重复登录或令牌超时");
                                        LogoutUtils.exitUser(ShowMmbActivity.this);
                                    }

                                    else {
                                        toast("添加成功");
                                        setResult(22);
                                        finish();
                                    }
                                }
                            });
                } else {
                    finish();
                }
                break;
        }
    }


    //会员列表的适配器
    private class ShowMmbAdapter extends WZYBaseAdapter<ShowMmbModel> {

        private int type;

        public ShowMmbAdapter(List<ShowMmbModel> data, Context context, int layoutRes, int type) {
            super(data, context, layoutRes);
            this.type = type;
        }

        @Override
        public void bindData(ViewHolder holder, final ShowMmbModel showMmbModel, final int indexPos) {
            TextView show_mmb_name = (TextView) holder.getView(R.id.show_mmb_name);
            TextView show_mmb_relation = (TextView) holder.getView(R.id.show_mmb_relation);
            CheckBox show_mmb_check = (CheckBox) holder.getView(R.id.show_mmb_check_item);
            show_mmb_check.setChecked(showMmbModel.isChoose());
            show_mmb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    showMmbModel.setChoose(b);
                }
            });
            switch (type) {
                case 0:
                    show_mmb_name.setText(showMmbModel.getMmbSname());
                    switch (showMmbModel.getMmbStatus()) {
                        case 0:
                            show_mmb_relation.setText("卖家");
                            break;
                        case 1:
                            show_mmb_relation.setText("买家");
                            break;
                    }
                    break;
                case 1:
                    show_mmb_name.setText(showMmbModel.getGroupName());
                    switch (showMmbModel.getGroupStatus()) {
                        case 0:
                            show_mmb_relation.setText("卖家");
                            break;
                        case 1:
                            show_mmb_relation.setText("买家");
                            break;
                    }
                    break;
            }

        }
    }
}
