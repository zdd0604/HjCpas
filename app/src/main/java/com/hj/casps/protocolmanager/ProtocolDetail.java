package com.hj.casps.protocolmanager;

import android.os.Bundle;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.GetFlowType;
import static com.hj.casps.common.Constant.GetPayType;
import static com.hj.casps.common.Constant.GetSendsGoodsType;
import static com.hj.casps.common.Constant.SYS_FUNC;
import static com.hj.casps.common.Constant.getUUID;

//协议详情页面
public class ProtocolDetail extends ActivityBaseHeader2 {

    private TextView protocol_contract_title;
    private TextView protocol_contract_type;
    private TextView protocol_buy_membername;
    private TextView protocol_sell_membername;
    private TextView protocol_operate_user;
    private TextView protocol_operate_time;
    private TextView protocol_user_time;
    private TextView protocol_pay_type;
    private TextView protocol_flow_type;
    private TextView protocol_sendgoods_type;
    private TextView protocol_payer_code;
    private TextView protocol_getgoods_address;
    private TextView protocol_goods;
    private TextView protocol_note;
    private String contract_id;
    private String contract_type;
    private TextView bank_account_name;
    private TextView address_name;

    //每次进入安卓页面都会先进入这里，安卓开发都明白，不需要一一注释了吧
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_detail);
        initData();
        initView();
    }

    //加载数据，从协议列表中点击的id 和type 提交到这里，通过接口请求，返回协议详情
    private void initData() {
        contract_id = getIntent().getStringExtra("contract_id");
        contract_type = getIntent().getStringExtra("contract_type");
        DetailPost detailPost = new DetailPost(publicArg.getSys_token(), getUUID(), SYS_FUNC, publicArg.getSys_user(), publicArg.getSys_member(), contract_id, contract_type);
        OkGo.post(Constant.ToContractDetailPageUrl)
                .tag(this)
                .params("param", mGson.toJson(detailPost))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DetailBack backDetail = mGson.fromJson(s, DetailBack.class);
                        if (backDetail.getReturn_code() != 0) {
                            toast(backDetail.getReturn_message());
                        } else if (backDetail.getReturn_code() == 1101 || backDetail.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(ProtocolDetail.this);
                        } else {
                            protocol_contract_title.setText(backDetail.getData().getContract_title());
                            protocol_contract_type.setText(backDetail.getData().getContract_type().equalsIgnoreCase("1") ? "采购" : "销售");
                            protocol_buy_membername.setText(backDetail.getData().getBuy_membername());
                            protocol_sell_membername.setText(backDetail.getData().getSell_membername());
                            String others_name = backDetail.getData().getBuy_membername().equalsIgnoreCase(publicArg.getSys_mmbname()) ? backDetail.getData().getSell_membername() : backDetail.getData().getBuy_membername();
                            protocol_operate_user.setText(backDetail.getData().getOperate_user().equalsIgnoreCase(publicArg.getSys_user()) ? publicArg.getSys_mmbname() : others_name);
                            protocol_operate_time.setText(Constant.stmpToTime(backDetail.getData().getOperate_time()));
                            protocol_user_time.setText(backDetail.getData().getUser_time());
                            protocol_pay_type.setText(GetPayType(backDetail.getData().getPay_type()));
                            protocol_flow_type.setText(GetSendsGoodsType(backDetail.getData().getFlow_type()));
                            protocol_sendgoods_type.setText(GetFlowType(backDetail.getData().getSendgoods_type()));
                            protocol_payer_code.setText(backDetail.getData().getPayer_name() + "\n" + (backDetail.getData().getPayer_code().equalsIgnoreCase("undefined") ? "" : backDetail.getData().getPayer_code()));
                            protocol_getgoods_address.setText(backDetail.getData().getGetgoods_address());
//                                protocol_payer_code.setText(backDetail.getData().getGetmoney_name() + "\n" + backDetail.getData().getGetmoney_code());
//                                protocol_getgoods_address.setText(backDetail.getData().getSendgoods_address());
                            String goodsName = null;
                            for (int i = 0; i < backDetail.getData().getGoods().size(); i++) {
                                if (i == 0) {
                                    goodsName = backDetail.getData().getGoods().get(i).getCategoryName();
                                } else {
                                    goodsName = goodsName + ";" + backDetail.getData().getGoods().get(i).getCategoryName();
                                }
                            }
                            protocol_goods.setText(goodsName);
                            protocol_note.setText(backDetail.getData().getNote());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toast(e.getMessage());
                    }
                });

    }

    //页面布局拿过来，就是让代码能利用界面，一般都写到initView中用来规范代码
    private void initView() {

        setTitle(getString(R.string.protocol_detail));
        protocol_contract_title = (TextView) findViewById(R.id.protocol_contract_title);
        protocol_contract_type = (TextView) findViewById(R.id.protocol_contract_type);
        protocol_buy_membername = (TextView) findViewById(R.id.protocol_buy_membername);
        protocol_sell_membername = (TextView) findViewById(R.id.protocol_sell_membername);
        protocol_operate_user = (TextView) findViewById(R.id.protocol_operate_user);
        protocol_operate_time = (TextView) findViewById(R.id.protocol_operate_time);
        protocol_user_time = (TextView) findViewById(R.id.protocol_user_time);
        protocol_pay_type = (TextView) findViewById(R.id.protocol_pay_type);
        protocol_flow_type = (TextView) findViewById(R.id.protocol_flow_type);
        protocol_sendgoods_type = (TextView) findViewById(R.id.protocol_sendgoods_type);
        protocol_payer_code = (TextView) findViewById(R.id.protocol_payer_code);
        protocol_getgoods_address = (TextView) findViewById(R.id.protocol_getgoods_address);
        protocol_goods = (TextView) findViewById(R.id.protocol_goods);
        protocol_note = (TextView) findViewById(R.id.protocol_note);
        bank_account_name = (TextView) findViewById(R.id.bank_account_name);
        address_name = (TextView) findViewById(R.id.address_name);
//        if (contract_type.equalsIgnoreCase("2")) {
//            bank_account_name.setText("收款账号");
//            address_name.setText("发货地址");
//        } else {
//            bank_account_name.setText("付款账号");
//            address_name.setText("收货地址");
//        }
    }

    /**
     * 提交类，这些就根据提交的参数进行配置，get set方法来公开，以便于使用，提交返回类下同。
     */

    private static class DetailPost {
        private String sys_token;
        private String sys_uuid;
        private String sys_func;
        private String sys_user;
        private String sys_member;
        private String contract_id;
        private String contract_type;

        public DetailPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String contract_id, String contract_type) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.contract_id = contract_id;
            this.contract_type = contract_type;
        }
    }

    /**
     * 返回类
     */
    private static class DetailBack {

        /**
         * data : {"buy_membername":"奥森学校","contract_title":"销售大米","contract_type":"2","getgoods_address":"奥森学校教工食堂","getmoney_code":"63980011596","getmoney_name":"中信银行","goods":[{"categoryName":"散装大米","ctgId":"1001002002","ctrId":"3add492288584c57901d60ad022c1965","id":"04cfb390de93438f82f1094e1e050eaa"}],"note":"","operate_time":1489041712000,"operate_user":"1b64b63c75ca428eb9ca77999f1e895e","payer_code":"9953001798001","payer_name":"建设银行","sell_membername":"长城商行","sendgoods_address":"北京京东大市场15号库","user_time":"2017-03-09 - 2017-07-20"}
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
             * contract_title : 销售大米
             * contract_type : 2
             * getgoods_address : 奥森学校教工食堂
             * getmoney_code : 63980011596
             * getmoney_name : 中信银行
             * goods : [{"categoryName":"散装大米","ctgId":"1001002002","ctrId":"3add492288584c57901d60ad022c1965","id":"04cfb390de93438f82f1094e1e050eaa"}]
             * note :
             * operate_time : 1489041712000
             * operate_user : 1b64b63c75ca428eb9ca77999f1e895e
             * payer_code : 9953001798001
             * payer_name : 建设银行
             * sell_membername : 长城商行
             * sendgoods_address : 北京京东大市场15号库
             * user_time : 2017-03-09 - 2017-07-20
             */

            private String buy_membername;
            private String contract_title;
            private String contract_type;
            private String getgoods_address;
            private String getmoney_code;
            private String getmoney_name;
            private String note;
            private long operate_time;
            private int flow_type;
            private int pay_type;
            private int sendgoods_type;
            private String operate_user;
            private String payer_code;
            private String payer_name;
            private String sell_membername;
            private String sendgoods_address;
            private String user_time;
            private List<GoodsBean> goods;

            public int getFlow_type() {
                return flow_type;
            }

            public void setFlow_type(int flow_type) {
                this.flow_type = flow_type;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public int getSendgoods_type() {
                return sendgoods_type;
            }

            public void setSendgoods_type(int sendgoods_type) {
                this.sendgoods_type = sendgoods_type;
            }

            public String getBuy_membername() {
                return buy_membername;
            }

            public void setBuy_membername(String buy_membername) {
                this.buy_membername = buy_membername;
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

            public String getSendgoods_address() {
                return sendgoods_address;
            }

            public void setSendgoods_address(String sendgoods_address) {
                this.sendgoods_address = sendgoods_address;
            }

            public String getUser_time() {
                return user_time;
            }

            public void setUser_time(String user_time) {
                this.user_time = user_time;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * categoryName : 散装大米
                 * ctgId : 1001002002
                 * ctrId : 3add492288584c57901d60ad022c1965
                 * id : 04cfb390de93438f82f1094e1e050eaa
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
        }
    }
}
