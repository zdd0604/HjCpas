package com.hj.casps.cooperate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.getUUID;
//会员详情列表，可以查看会员名称，地址，采购，销售的申请及协议的创建等等。
public class CooperateDetail extends ActivityBaseHeader2 implements View.OnClickListener {
    private String relation_member;
    private String buztype;
    private TextView cooperate_person_name;
    private TextView cooperate_address;
    private FancyButton cooperate_create_buy;
    private FancyButton cooperate_create_sell;
    private FancyButton cooperate_detail_submit;
    private ToggleButton toggle_coop_buy;
    private ToggleButton toggle_coop_sell;
    private MyDialog myDialog;
    //    private String url = HTTPURL + "appMemberRelationship/getMmbbymid.app";
//    private String url_open = HTTPURL + "appMemberRelationship/openBizRelationship.app";
//    private String url_stop = HTTPURL + "appMemberRelationship/stopBizRelationship.app";
//    private String url_lower = HTTPURL + "appMemberRelationship/lowerToConcernOperation.app";
    private String mmbfname;
    private String mmbaddress;
    private String mmbhomepage;
    private String sellbiz;
    private String buybiz;
    private boolean from_net;
    private int type;
    private TextView member_name_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate_detail);
        initView();
        initData();
    }

    //关系管理详情页面布局
    private void initView() {

//        titleRight.setVisibility(View.GONE);
        cooperate_person_name = (TextView) findViewById(R.id.cooperate_person_name);
        cooperate_address = (TextView) findViewById(R.id.cooperate_address);
        cooperate_create_buy = (FancyButton) findViewById(R.id.cooperate_create_buy);
        cooperate_create_buy.setOnClickListener(this);
        cooperate_create_sell = (FancyButton) findViewById(R.id.cooperate_create_sell);
        cooperate_create_sell.setOnClickListener(this);
        cooperate_detail_submit = (FancyButton) findViewById(R.id.cooperate_detail_submit);
        cooperate_detail_submit.setOnClickListener(this);
        toggle_coop_buy = (ToggleButton) findViewById(R.id.toggle_coop_buy);
        toggle_coop_sell = (ToggleButton) findViewById(R.id.toggle_coop_sell);
        member_name_text = (TextView) findViewById(R.id.member_name_text);
        member_name_text.setOnClickListener(this);
    }

    /**
     * 根据点击的会员id获取会员信息（包括与合作会员的业务关系是否启用）
     */
    private void initData() {
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                setTitle(getString(R.string.cooperate_detail));
                relation_member = getIntent().getStringExtra("relation_member");
                buztype = getIntent().getStringExtra("buztype");
                CDLoading cdLoading = new CDLoading(Constant.publicArg.getSys_token(),
                        Constant.getUUID(),
                        Constant.SYS_FUNC101100310001,
                        Constant.publicArg.getSys_user(),
                        Constant.publicArg.getSys_name(),
                        Constant.publicArg.getSys_member(), relation_member, "1", buztype);
                log(mGson.toJson(cdLoading));
                OkGo.post(Constant.getMmbbymidUrl)
                        .tag(this)
                        .params("param", mGson.toJson(cdLoading))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                BackDetail backDetail = mGson.fromJson(s, BackDetail.class);
                                if (backDetail.getReturn_code() != 0) {
                                    toast(backDetail.getReturn_message());
                                }
                                else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                                    toastSHORT("重复登录或令牌超时");
                                    LogoutUtils.exitUser(CooperateDetail.this);
                                }


                                else {
                                    mmbfname = backDetail.getData().getMmbfname();
                                    cooperate_person_name.setText(mmbfname);
                                    mmbaddress = backDetail.getData().getMmbaddress();
                                    cooperate_address.setText(mmbaddress);
                                    mmbhomepage = backDetail.getData().getMmbhomepage();
                                    sellbiz = backDetail.getData().getSellBiz();
                                    buybiz = backDetail.getData().getBuyBiz();
                                    from_net = true;
                                    switchData();

                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }
                        });

                break;
            case 1:
                setTitle(getString(R.string.cooperate_upgrade));
                member_name_text.setText("会员名称");
                String name = getIntent().getStringExtra("name");
                String address = getIntent().getStringExtra("address");
                relation_member = getIntent().getStringExtra("mark_member");
                cooperate_person_name.setText(name);
                cooperate_address.setText(address);
                CDLoading cdLoading1 = new CDLoading(
                        Constant.publicArg.getSys_token(),
                        Constant.getUUID(),
                        Constant.SYS_FUNC101100310001,
                        Constant.publicArg.getSys_user(),
                        Constant.publicArg.getSys_name(),
                        Constant.publicArg.getSys_member(), relation_member);
                String s = mGson.toJson(cdLoading1);
                s = s.replace("relation_member", "mark_member");
//                url = HTTPURL + "appMemberRelationship/toUpgradebizOperation.app";
                LogShow(s);
                OkGo.post(Constant.toUpgradebizOperationUrl)
                        .tag(this)
                        .params("param", s)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                BackDetail backDetail = mGson.fromJson(s, BackDetail.class);
                                if (backDetail.getReturn_code() != 0) {
                                    toast(backDetail.getReturn_message());
                                }
                                else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                                    toastSHORT("重复登录或令牌超时");
                                    LogoutUtils.exitUser(CooperateDetail.this);
                                }

                                else {
                                    sellbiz = backDetail.getData().getSellBiz();
                                    buybiz = backDetail.getData().getBuyBiz();
                                    from_net = true;
                                    switchData();

                                }
                            }
                        });
                break;
        }

    }

    //切换可采购还是可销售
    private void switchData() {
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
        toggle_coop_buy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (from_net) {
                    from_net = false;
                }
                opencloseMMB(b, "1");
            }
        });
        toggle_coop_sell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (from_net) {
                    from_net = false;
                }
                opencloseMMB(b, "2");
            }
        });
    }

    /**
     * 启用/停用的方法
     *
     * @param b
     * @param type
     */
    private void opencloseMMB(boolean b, final String type) {
        CDLoading cdLoading = new CDLoading(Constant.publicArg.getSys_token(),
                getUUID(),
                Constant.SYS_FUNC101100310001,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(), relation_member, type);
        log(mGson.toJson(cdLoading));
        if (b) {
            if (this.type == 0) {
                switch (type) {
                    case "1":
                        cooperate_create_buy.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        cooperate_create_sell.setVisibility(View.VISIBLE);
                        break;
                }
            }

            OkGo.post(Constant.openBizRelationshipUrl)
                    .tag(this)
                    .params("param", mGson.toJson(cdLoading))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            BackDetail backDetail = mGson.fromJson(s, BackDetail.class);
                            if (backDetail.getReturn_code() != 0) {
                                toast(backDetail.getReturn_message());
                            } else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                                toastSHORT("重复登录或令牌超时");
                                LogoutUtils.exitUser(CooperateDetail.this);
                            }
                        }
                    });

        } else {
            if (this.type == 0) {
                switch (type) {
                    case "1":
                        cooperate_create_buy.setVisibility(View.INVISIBLE);
                        break;
                    case "2":
                        cooperate_create_sell.setVisibility(View.INVISIBLE);
                        break;
                }
            }
            LogShow(mGson.toJson(cdLoading));
            OkGo.post(Constant.stopBizRelationshipUrl)
                    .tag(this)
                    .params("param", mGson.toJson(cdLoading))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            BackDetail backDetail = mGson.fromJson(s, BackDetail.class);
                            if (backDetail.getReturn_code() != 0) {
                                toast(backDetail.getReturn_message());
                            } else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                                toastSHORT("重复登录或令牌超时");
                                LogoutUtils.exitUser(CooperateDetail.this);
                            }
                        }
                    });

        }
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
            case R.id.cooperate_detail_submit:
                showDialog();
                break;

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
                        Constant.SYS_FUNC101100310001,
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
                                }else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                                    toastSHORT("重复登录或令牌超时");
                                    LogoutUtils.exitUser(CooperateDetail.this);
                                }

                                else {
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
//                showOKDialog();
                setResult(22);
                finish();
            }
        });
        myDialog.show();
        myDialog.setButtonVisible(true, false);
        myDialog.setTextLeftImg(R.mipmap.uc_prompt2, this);

    }

    /**
     * 详单中需要提交的数据
     */
    private class CDLoading {
        private String sys_token;//	string	令牌号
        private String sys_uuid;//string	操作唯一编码（防重复提交）
        private String sys_func;//string	功能编码（用于授权检查）
        private String sys_user;//string	用户id
        private String sys_name;//string	用户名
        private String sys_member;//	string	会员id
        private String relation_member;//	string	点击的会员id
        private String type;//	string	传固定值"1"
        private String buztype;//	string	是买还是卖的关系 0：买 1：卖 2：借 3：贷

        public CDLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String relation_member, String type, String buztype) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.relation_member = relation_member;
            this.type = type;
            this.buztype = buztype;
        }

        public CDLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String relation_member, String type) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.relation_member = relation_member;
            this.type = type;
        }

        public CDLoading(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String relation_member) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.relation_member = relation_member;
        }


        public String getSys_token() {
            return sys_token;
        }

        public void setSys_token(String sys_token) {
            this.sys_token = sys_token;
        }

        public String getSys_uuid() {
            return sys_uuid;
        }

        public void setSys_uuid(String sys_uuid) {
            this.sys_uuid = sys_uuid;
        }

        public String getSys_func() {
            return sys_func;
        }

        public void setSys_func(String sys_func) {
            this.sys_func = sys_func;
        }

        public String getSys_user() {
            return sys_user;
        }

        public void setSys_user(String sys_user) {
            this.sys_user = sys_user;
        }

        public String getSys_name() {
            return sys_name;
        }

        public void setSys_name(String sys_name) {
            this.sys_name = sys_name;
        }

        public String getSys_member() {
            return sys_member;
        }

        public void setSys_member(String sys_member) {
            this.sys_member = sys_member;
        }

        public String getRelation_member() {
            return relation_member;
        }

        public void setRelation_member(String relation_member) {
            this.relation_member = relation_member;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBuztype() {
            return buztype;
        }

        public void setBuztype(String buztype) {
            this.buztype = buztype;
        }
    }

    /**
     * 详单返回的参数
     */
    private class BackDetail {

        /**
         * data : {"buyBiz":"1","mmbaddress":"北京市朝阳区亚运村路18号","mmbfname":"APP测试用企业","mmbhomepage":"","sellBiz":"1"}
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

        public class DataBean {
            /**
             * buyBiz : 1
             * mmbaddress : 北京市朝阳区亚运村路18号
             * mmbfname : APP测试用企业
             * mmbhomepage :
             * sellBiz : 1
             */

            private String buyBiz;
            private String mmbaddress;
            private String mmbfname;
            private String mmbhomepage;
            private String sellBiz;

            public String getBuyBiz() {
                return buyBiz;
            }

            public void setBuyBiz(String buyBiz) {
                this.buyBiz = buyBiz;
            }

            public String getMmbaddress() {
                return mmbaddress;
            }

            public void setMmbaddress(String mmbaddress) {
                this.mmbaddress = mmbaddress;
            }

            public String getMmbfname() {
                return mmbfname;
            }

            public void setMmbfname(String mmbfname) {
                this.mmbfname = mmbfname;
            }

            public String getMmbhomepage() {
                return mmbhomepage;
            }

            public void setMmbhomepage(String mmbhomepage) {
                this.mmbhomepage = mmbhomepage;
            }

            public String getSellBiz() {
                return sellBiz;
            }

            public void setSellBiz(String sellBiz) {
                this.sellBiz = sellBiz;
            }
        }
    }
}
