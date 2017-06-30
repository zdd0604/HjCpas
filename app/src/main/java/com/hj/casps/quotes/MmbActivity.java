package com.hj.casps.quotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appQuote.DeleteScopeIdEntity;
import com.hj.casps.entity.appQuote.GetMmbEntity;
import com.hj.casps.entity.appQuote.MmbModelPerson;
import com.hj.casps.entity.appQuote.MmbReturnBack;
import com.hj.casps.entity.appQuote.SubgEntity;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.DIALOG_CONTENT_13;

/**
 * 选择发布范围
 */
public class MmbActivity extends ActivityBaseHeader2 implements View.OnClickListener {

    private TextView quote_mmb_desc_tv;
    private CheckBox layout_check_mmb_pub;
    private CheckBox layout_check_mmb_choose;
    private ImageView person_cooperate_add;
    private ListView person_list;
    private ImageView group_cooperate_add;
    private ListView group_list;
    private String quoteId;
    private List<MmbModelPerson> mmbModelPersons;
    private List<MmbModelPerson> mmbModelGroup;
    private MmbAdapter adapter_person;
    private MmbAdapter adapter_group;
    private boolean pub = false;
    private boolean choose = false;
    private int type;
    private int rangType;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    initData();
                    break;
                case Constant.HANDLERTYPE_1:
                    refreshData();
                    break;
                case Constant.HANDLERTYPE_2:
                    groupOkGo();
                    break;
                case Constant.HANDLERTYPE_3:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmb);
        initData();
        initView();
    }

    //加载数据进行搜索范围
    private void initData() {
        quoteId = getIntent().getStringExtra("quoteId");
        type = getIntent().getIntExtra("type", 1);
        rangType = getIntent().getIntExtra("RangType", 0);
        if (hasInternetConnected())
            handler.sendEmptyMessage(Constant.HANDLERTYPE_1);
    }

    //指定发布范围的页面初始化
    private void initView() {
        setTitle(getString(R.string.quotes_assign));
        setTitleRight(getString(R.string.True), null);
        titleRight.setEnabled(true);
        quote_mmb_desc_tv = (TextView) findViewById(R.id.quote_mmb_desc_tv);
        layout_check_mmb_pub = (CheckBox) findViewById(R.id.layout_check_mmb_pub);
        layout_check_mmb_choose = (CheckBox) findViewById(R.id.layout_check_mmb_choose);
        person_cooperate_add = (ImageView) findViewById(R.id.person_cooperate_add);
        person_list = (ListView) findViewById(R.id.person_list);
        person_cooperate_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("type", type);
                bundle.putInt("group", 0);
                bundle.putString("quoteId", quoteId);
                intentActivity(ShowMmbActivity.class, 11, bundle);
            }
        });
        group_cooperate_add = (ImageView) findViewById(R.id.group_cooperate_add);
        group_list = (ListView) findViewById(R.id.group_list);
        group_cooperate_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("type", type);
                bundle.putInt("group", 1);
                bundle.putString("quoteId", quoteId);
                intentActivity(ShowMmbActivity.class, 11, bundle);
            }
        });
        quote_mmb_desc_tv.setOnClickListener(this);
        adapter_person = new MmbAdapter(mmbModelPersons, this, R.layout.item_mmb, 1);
        person_list.setAdapter(adapter_person);
        adapter_group = new MmbAdapter(mmbModelGroup, this, R.layout.item_mmb, 2);
        group_list.setAdapter(adapter_group);
        switch (rangType) {
            case 0:
                layout_check_mmb_pub.setChecked(true);
                if (layout_check_mmb_pub.isChecked()) {
                    layout_check_mmb_choose.setChecked(false);
                    pub = true;
                    choose = false;
                } else {
                    pub = false;
                }
                break;
            case 1:
                layout_check_mmb_choose.setChecked(true);
                if (layout_check_mmb_choose.isChecked()) {
                    layout_check_mmb_pub.setChecked(false);
                    choose = true;
                    pub = false;
                } else {
                    choose = false;
                }
                break;
        }
        layout_check_mmb_pub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    layout_check_mmb_choose.setChecked(false);
                    pub = true;
                    choose = false;
                } else {
                    pub = false;
                }
            }
        });
        layout_check_mmb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    layout_check_mmb_pub.setChecked(false);
                    choose = true;
                    pub = false;
                } else {
                    choose = false;
                }
            }
        });
    }

    //点击确定发布范围，选择后点击
    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (pub) {
            subg(0);
        } else if (choose) {
            subg(1);
        } else {
            toast("没有指定任何发布范围");
        }
    }

    //数据更新
    private void refreshData() {
        GetMmbEntity getMmbEntity = new GetMmbEntity(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                quoteId
        );
        OkGo.get(Constant.GetMmbUrl)
                .params("param", mGson.toJson(getMmbEntity))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        MmbReturnBack mmbBack = mGson.fromJson(s, MmbReturnBack.class);
                        if (mmbBack.getReturn_code() != 0) {
                            toast(mmbBack.getReturn_message());
                            return;
                        } else if (mmbBack.getReturn_code() == 1101 ||
                                mmbBack.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(MmbActivity.this);
                        }

                        mmbModelPersons = new ArrayList<>();
                        mmbModelPersons = mmbBack.getList();
                        if (mmbModelPersons.isEmpty()) {
                            adapter_person.removeAll();
                        }
                        adapter_person.updateRes(mmbModelPersons);

                        if (hasInternetConnected())
                            handler.sendEmptyMessage(Constant.HANDLERTYPE_2);
                    }
                });
    }

    //合作范围的群加载
    private void groupOkGo() {
        GetMmbEntity getMmbEntity = new GetMmbEntity(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                quoteId
        );
        OkGo.get(Constant.GetGroupUrl)
                .params("param", mGson.toJson(getMmbEntity))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        MmbReturnBack mmbBack2 = mGson.fromJson(s, MmbReturnBack.class);
                        if (mmbBack2.getReturn_code() != 0) {
                            toast(mmbBack2.getReturn_message());
                            return;
                        } else if (mmbBack2.getReturn_code() == 1101
                                || mmbBack2.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(MmbActivity.this);
                        }

                        mmbModelGroup = new ArrayList<>();
                        mmbModelGroup = mmbBack2.getList();
                        if (mmbModelGroup.isEmpty()) {
                            adapter_group.removeAll();

                        }
                        adapter_group.updateRes(mmbModelGroup);
                    }
                });
    }

    //确定发布范围的类型添加
    private void subg(int i) {
        SubgEntity subgEntity = new SubgEntity(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                String.valueOf(i),
                quoteId
        );
        OkGo.get(Constant.SubgUrl)
                .params("param", mGson.toJson(subgEntity))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        MmbReturnBack mmbBack = mGson.fromJson(s, MmbReturnBack.class);
                        if (mmbBack.getReturn_code() == 0 && mmbBack.getJson() == 0) {
                            toast("发布报价成功");
                            Bundle bundle = new Bundle();
                            bundle.putString("searchjson", "");
                            setResult(22, getIntent().putExtras(bundle));
                            finish();
                        } else if (mmbBack.getReturn_code() == 1101 || mmbBack.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(MmbActivity.this);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quote_mmb_desc_tv:
                CreateDialog(Constant.DIALOG_CONTENT_12 + DIALOG_CONTENT_13);
                break;
        }
    }

    //发布范围适配器
    private class MmbAdapter extends WZYBaseAdapter<MmbModelPerson> {
        private boolean isOpen = false;
        private int i;

        public MmbAdapter(List<MmbModelPerson> data, Context context, int layoutRes, int i) {
            super(data, context, layoutRes);
            this.i = i;
        }

        @Override
        public void bindData(ViewHolder holder, final MmbModelPerson mmbModelPerson, final int indexPos) {
            TextView mmb_name = (TextView) holder.getView(R.id.mmb_name);
            RelativeLayout relative_layout_1_mmb = (RelativeLayout) holder.getView(R.id.relative_layout_1_mmb);

            switch (i) {
                case 1:
                    mmb_name.setText(mmbModelPerson.getMmbSname());
                    break;
                case 2:
                    mmb_name.setText(mmbModelPerson.getGroupName());
                    break;
            }

            final TextView delete_mmb_item = (TextView) holder.getView(R.id.delete_mmb_item);
            relative_layout_1_mmb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isOpen) {
                        delete_mmb_item.setVisibility(View.GONE);
                        isOpen = false;
                    } else {
                        delete_mmb_item.setVisibility(View.VISIBLE);
                        isOpen = true;
                    }
                }
            });
            delete_mmb_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteScopeIdEntity idEntity = new DeleteScopeIdEntity(
                            publicArg.getSys_token(),
                            Constant.getUUID(),
                            Constant.SYS_FUNC,
                            publicArg.getSys_user(),
                            publicArg.getSys_member(),
                            quoteId, mmbModelPerson.getId()
                    );
                    OkGo.post(Constant.DeleteScopeIdUrl)
                            .params("param", mGson.toJson(idEntity))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    MmbReturnBack mmbBack = mGson.fromJson(s, MmbReturnBack.class);
                                    if (mmbBack.getReturn_code() != 0) {
                                        toast(mmbBack.getReturn_message());
                                        return;
                                    } else if (mmbBack.getReturn_code() == 1101
                                            || mmbBack.getReturn_code() == 1102) {
                                        toastSHORT("重复登录或令牌超时");
                                        LogoutUtils.exitUser(MmbActivity.this);
                                    } else {
                                        refreshData();
                                    }
                                }
                            });
                }
            });
        }
    }

    //搜索发布范围后返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 22) {
            handler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }
    }
}
