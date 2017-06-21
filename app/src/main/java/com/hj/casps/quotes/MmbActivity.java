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

import com.google.gson.Gson;
import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.DIALOG_CONTENT_13;
import static com.hj.casps.common.Constant.SYS_FUNC10110028;

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
    private String url_mmb;
    private String url_group;
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
                case 0:
                    initData();
                    break;

            }
        }
    };

    //搜索发布范围后返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 22) {
            handler.sendEmptyMessage(0);
        }
    }

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
        refreshData();
    }

    //数据更新
    private void refreshData() {
        url_mmb = Constant.GetMmbUrl +
                "?param={\"sys_func\":\"" + SYS_FUNC10110028 + "\"," +
                "\"sys_member\":\"" + publicArg.getSys_member() + "\"," +
                "\"sys_name\":\"" + publicArg.getSys_name() + "\"," +
                "\"sys_token\":\"" + publicArg.getSys_token() + "\"," +
                "\"sys_user\":\"" + publicArg.getSys_user() + "\"," +
                "\"sys_uuid\":\" " + Constant.getUUID() + "\"," +
                "\"quoteId\":\"" + quoteId + "\"}";
        OkGo.get(url_mmb).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, okhttp3.Response response) {
                groupOkGo();
                Gson gson = new Gson();
                MmbBack mmbBack = gson.fromJson(s, MmbBack.class);
                if (mmbBack.getReturn_code() != 0) {
                    toast(mmbBack.getReturn_message());
                    return;
                }
                mmbModelPersons = new ArrayList<>();
                mmbModelPersons = mmbBack.getList();
                if (mmbModelPersons.isEmpty()) {
                    adapter_person.removeAll();
                }
                adapter_person.updateRes(mmbModelPersons);
            }
        });
    }

    //合作范围的群加载
    private void groupOkGo() {
        url_group = Constant.GetGroupUrl + "?param={\"sys_func\":\"" + SYS_FUNC10110028 + "\"," +
                "\"sys_member\":\"" + publicArg.getSys_member() + "\"," +
                "\"sys_name\":\"" + publicArg.getSys_name() + "\"," +
                "\"sys_token\":\"" + publicArg.getSys_token() + "\"," +
                "\"sys_user\":\"" + publicArg.getSys_user() + "\"," +
                "\"sys_uuid\":\" " + Constant.getUUID() + " \"," +
                "\"quoteId\":\"" + quoteId + "\"}";
        OkGo.get(url_group).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, okhttp3.Response response) {
                Gson gson2 = new Gson();
                MmbBack mmbBack2 = gson2.fromJson(s, MmbBack.class);
                if (mmbBack2.getReturn_code() != 0) {
                    toast(mmbBack2.getReturn_message());
                    return;
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


    //合作范围返回值，解析时使用
    private static class MmbBack {
        private int return_code;
        private String return_message;
        private int json;
        private List<MmbModelPerson> list;

        public int getJson() {
            return json;
        }

        public void setJson(int json) {
            this.json = json;
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

        public List<MmbModelPerson> getList() {
            return list;
        }

        public void setList(List<MmbModelPerson> list) {
            this.list = list;
        }
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

    //确定发布范围的类型添加
    private void subg(int i) {
        OkGo.get(Constant.SubgUrl + "?param={\"sys_func\":\"" + SYS_FUNC10110028 + "\"," +
                "\"sys_member\":\"" + publicArg.getSys_member() + "\"," +
                "\"sys_name\":\"" + publicArg.getSys_name() + "\"," +
                "\"sys_token\":\"" + publicArg.getSys_token() + "\"," +
                "\"sys_user\":\"" + publicArg.getSys_user() + "\"," +
                "\"sys_uuid\":\" " + Constant.getUUID() + "\"," +
                "\"rangType\":\"" + String.valueOf(i) + "\"," +
                "\"quoteId\":\"" + quoteId + "\"}")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        MmbBack mmbBack = gson.fromJson(s, MmbBack.class);
                        if (mmbBack.getReturn_code() == 0 && mmbBack.getJson() == 0) {
                            toast("发布报价成功");
                            Bundle bundle = new Bundle();
                            bundle.putString("searchjson", "");
                            setResult(22, getIntent().putExtras(bundle));
                            finish();
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

    //会员群组提交类
    private class MmbModelPerson {
        private String id;
        private String mmbSname;
        private String groupName;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getMmbSname() {
            return mmbSname;
        }

        public void setMmbSname(String mmbSname) {
            this.mmbSname = mmbSname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    //发布范围适配器
    private class MmbAdapter extends WZYBaseAdapter<MmbModelPerson> {

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
                    delete_mmb_item.setVisibility(View.VISIBLE);
                }
            });
            delete_mmb_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // param={\"scopeId\":\"testschool001\",\"quoteId\":\"30038601\",\"rangType\":\"1\"}
                    OkGo.post(Constant.DeleteScopeIdUrl)
                            .params("param", "{\"sys_func\":\"" + SYS_FUNC10110028 + "\"," +
                                    "\"sys_member\":\"" + publicArg.getSys_member() + "\"," +
                                    "\"sys_name\":\"" + publicArg.getSys_name() + "\"," +
                                    "\"sys_token\":\"" + publicArg.getSys_token() + "\"," +
                                    "\"sys_user\":\"" + publicArg.getSys_user() + "\"," +
                                    "\"sys_uuid\":\"" + Constant.getUUID() + "\"," +
                                    "\"scopeId\":\"" + mmbModelPerson.getId() + "\"," +
                                    "\"quoteId\":\"" + quoteId + "\"}")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    MmbBack mmbBack = gson.fromJson(s, MmbBack.class);
                                    if (mmbBack.getReturn_code() != 0) {
                                        toast(mmbBack.getReturn_message());
                                        return;
                                    } else {
                                        refreshData();
                                    }
                                }
                            });
                }
            });
        }
    }
}
