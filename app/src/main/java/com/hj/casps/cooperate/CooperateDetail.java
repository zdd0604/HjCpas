package com.hj.casps.cooperate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appnemberrelationship.BackDetail;
import com.hj.casps.entity.appnemberrelationship.CDLoading;
import com.hj.casps.ui.MyDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.getUUID;

//会员详情列表，可以查看会员名称，地址，采购，销售的申请及协议的创建等等。
public class CooperateDetail extends ActivityBaseHeader2 implements View.OnClickListener {
    private String relation_member;
    private String buztype;
    //会员名称
    @BindView(R.id.cooperate_person_name)
    TextView cooperate_person_name;
    //注册地址
    @BindView(R.id.cooperate_address)
    TextView cooperate_address;
    //创建采购协议
    @BindView(R.id.cooperate_create_buy)
    FancyButton cooperate_create_buy;
    //创建销售协议
    @BindView(R.id.cooperate_create_sell)
    FancyButton cooperate_create_sell;
    //确定按钮数据提交操作
    @BindView(R.id.submit_fbtn)
    FancyButton submit_fbtn;
    //降级为仅关注
    @BindView(R.id.relation_change)
    ToggleButton relation_change;
    //按钮的状态
    private boolean relation_change_blean;
    //我能采购
    @BindView(R.id.toggle_coop_buy)
    ToggleButton toggle_coop_buy;
    //按钮的状态
    private boolean toggle_coop_buy_blean;
    //我能销售
    @BindView(R.id.toggle_coop_sell)
    ToggleButton toggle_coop_sell;
    //按钮的状态
    private boolean isUpLoad = false;
    private boolean isFinish = false;
    private int buyNum = 0;
    private int sellNum = 0;

    private MyDialog myDialog;
    private String sellbiz;
    private String buybiz;
    private int type;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    isUpLoad = false;
                    isFinish = true;
                    opencloseMMB(toggle_coop_buy.isChecked(), "1");
                    break;
                case Constant.HANDLERTYPE_1:
                    isUpLoad = false;
                    opencloseMMB(toggle_coop_sell.isChecked(), "2");
                    break;
                case Constant.HANDLERTYPE_2:
                    isUpLoad = true;
                    opencloseMMB(toggle_coop_buy.isChecked(), "1");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    //关系管理详情页面布局
    private void initView() {
        cooperate_create_buy.setOnClickListener(this);
        cooperate_create_sell.setOnClickListener(this);
        relation_change.setOnClickListener(this);
        submit_fbtn.setOnClickListener(this);
        toggle_coop_buy.setOnClickListener(this);
        toggle_coop_sell.setOnClickListener(this);
    }

    //点击事件，用来新建合作协议或者点击关注
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CooperateCreate.class);
        switch (v.getId()) {
            case R.id.cooperate_create_buy:
                intent.putExtra("buy_type", true);
                intent.putExtra("relation_member", relation_member);
                startActivity(intent);
                break;
            case R.id.cooperate_create_sell:
                intent.putExtra("buy_type", false);
                intent.putExtra("relation_member", relation_member);
                startActivity(intent);
                break;
            case R.id.toggle_coop_buy:
                //我能采购
                relation_change.setChecked(false);
                if (toggle_coop_buy.isChecked()) {
                    if (this.type == 0 && buybiz.equalsIgnoreCase("0"))
                        cooperate_create_buy.setVisibility(View.VISIBLE);
                } else {
                    if (this.type == 0)
                        cooperate_create_buy.setVisibility(View.GONE);
                }
                buyNum++;
                break;
            case R.id.toggle_coop_sell:
                //我能销售
                relation_change.setChecked(false);
                if (toggle_coop_sell.isChecked()) {
                    if (this.type == 0 && sellbiz.equalsIgnoreCase("0"))
                        cooperate_create_sell.setVisibility(View.VISIBLE);
                } else {
                    if (this.type == 0)
                        cooperate_create_sell.setVisibility(View.GONE);
                }
                sellNum++;
                break;
            case R.id.relation_change:
                //降级为仅关注
                toggle_coop_buy.setChecked(false);
                toggle_coop_sell.setChecked(false);
                break;
            case R.id.submit_fbtn:
                //提交数据操作
                submitDatas();
                break;
        }
    }

    /**
     * 提交数据的操作
     */
    private void submitDatas() {
        if (relation_change.isChecked()) {
            showDialog();
        } else if (buyNum > 0 && sellNum == 0) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        } else if (sellNum > 0 && buyNum == 0) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
        } else if (sellNum > 0 && buyNum > 0) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
        }
    }

    /**
     * 根据点击的会员id获取会员信息（包括与合作会员的业务关系是否启用）
     */
    private void initData() {
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                //会员详情
                vipDetail();
                break;
            case 1:
                //升级到业务合作
                relationChange();
                break;
        }
    }

    //切换可采购还是可销售
    private void switchData() {
        //sellbiz是销售 1是让开关关闭 0是打开
        switch (sellbiz) {
            case "1":
                toggle_coop_sell.setChecked(false);
                break;
            case "0":
                toggle_coop_sell.setChecked(true);
                if (type == 0) {
                    cooperate_create_sell.setVisibility(View.VISIBLE);
                }
                break;
        }
        //buybiz是采购
        switch (buybiz) {
            case "1":
                toggle_coop_buy.setChecked(false);
                break;
            case "0":
                toggle_coop_buy.setChecked(true);
                if (type == 0) {
                    cooperate_create_buy.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    /**
     * 启用/停用的方法
     *
     * @param isOpen
     * @param cooperatetype
     */
    private void opencloseMMB(boolean isOpen, final String cooperatetype) {
        CDLoading cdLoading = new CDLoading(
                publicArg.getSys_token(),
                getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                relation_member,
                cooperatetype);
        log(mGson.toJson(cdLoading));
        if (isOpen) {
            upDatas(Constant.openBizRelationshipUrl, mGson.toJson(cdLoading));
        } else {
            upDatas(Constant.stopBizRelationshipUrl, mGson.toJson(cdLoading));
        }
    }

    //降级仅关注的弹框
    private void showDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.cooperate_downgrade_dialog));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                CDLoading cdLoading = new CDLoading(
                        Constant.publicArg.getSys_token(),
                        Constant.getUUID(),
                        Constant.SYS_FUNC,
                        Constant.publicArg.getSys_user(),
                        Constant.publicArg.getSys_name(),
                        Constant.publicArg.getSys_member(), relation_member);
                log(mGson.toJson(cdLoading));
                OkGo.post(Constant.lowerToConcernOperationUrl)
                        .tag(this)
                        .params("param", mGson.toJson(cdLoading))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                BackDetail backDetail = mGson.fromJson(s, BackDetail.class);
                                if (backDetail.getReturn_code() != 0) {
                                    toast(backDetail.getReturn_message());
                                } else {
                                    showOKDialog();
                                }
                            }
                        });
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

    //降级成功的弹框
    private void showOKDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.cooperate_downgrade_OK));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                setResult(22);
                finish();
            }
        });
        myDialog.show();
        myDialog.setButtonVisible(true, false);
        myDialog.setTextLeftImg(R.mipmap.uc_prompt2, this);
    }


    /**
     * 上传数据
     *
     * @param Url
     * @param param
     */
    private void upDatas(String Url, String param) {
        OkGo.post(Url)
                .tag(this)
                .params("param", param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        BackDetail backDetail = mGson.fromJson(s, BackDetail.class);
                        if (backDetail == null)
                            return;

                        toastSHORT(backDetail.getReturn_message());

                        if (isUpLoad) {
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
                            if (isFinish)
                                setResult(22);
                            finish();
                        } else {
                            setResult(22);
                            finish();
                        }
                    }
                });
    }

    /**
     * 会员详情
     */
    private void vipDetail() {
        setTitle(getString(R.string.cooperate_detail));
        relation_member = getIntent().getStringExtra("relation_member");
        buztype = getIntent().getStringExtra("buztype");
        CDLoading cdLoading = new CDLoading(Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                relation_member,
                "1",
                buztype);
        LogShow("vipDetail：" + mGson.toJson(cdLoading));
        OkGo.post(Constant.getMmbbymidUrl)
                .tag(this)
                .params("param", mGson.toJson(cdLoading))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        BackDetail backDetail = mGson.fromJson(s, BackDetail.class);
                        if (backDetail.getReturn_code() != 0) {
                            toast(backDetail.getReturn_message());
                        } else {
                            cooperate_person_name.setText(backDetail.getData().getMmbfname());
                            cooperate_address.setText(backDetail.getData().getMmbfname());
                            sellbiz = backDetail.getData().getSellBiz();
                            buybiz = backDetail.getData().getBuyBiz();
                            switchData();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * 上级到业务合作会员
     */
    private void relationChange() {
        setTitle(getString(R.string.cooperate_upgrade));
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        relation_member = getIntent().getStringExtra("mark_member");
        cooperate_person_name.setText(name);
        cooperate_address.setText(address);
        CDLoading cdLoading1 = new CDLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                relation_member);

        LogShow("relationChange:" + mGson.toJson(cdLoading1).replace("relation_member", "mark_member"));
        OkGo.post(Constant.toUpgradebizOperationUrl)
                .tag(this)
                .params("param", mGson.toJson(cdLoading1).replace("relation_member", "mark_member"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        BackDetail backDetail = mGson.fromJson(s, BackDetail.class);
                        if (backDetail.getReturn_code() != 0) {
                            toast(backDetail.getReturn_message());
                        } else {
                            sellbiz = backDetail.getData().getSellBiz();
                            buybiz = backDetail.getData().getBuyBiz();
                            switchData();
                        }
                    }
                });
    }
}
