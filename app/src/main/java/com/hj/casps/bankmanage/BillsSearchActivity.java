package com.hj.casps.bankmanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * 查询公用的页面
 */
public class BillsSearchActivity extends ActivityBaseHeader2 implements View.OnClickListener {
    private int activity_type;

    @BindView(R.id.layout_payment_bank_1)     //父控件名称 用于隐藏显示整个View
            LinearLayout layout_payment_bank_1;
    @BindView(R.id.search_tv_bills_title_1)
    TextView search_tv_bills_title_1;        //子控件title
    @BindView(R.id.search_ed_bills_content_1)
    EditText search_ed_bills_content_1;       //子控件的输入框

    @BindView(R.id.layout_payment_bank_2)
    LinearLayout layout_payment_bank_2;
    @BindView(R.id.search_tv_bills_title_2)
    TextView search_tv_bills_title_2;
    @BindView(R.id.search_ed_bills_content_2)
    EditText search_ed_bills_content_2;

    //主要用于选择
    @BindView(R.id.layout_payment_bank_3)
    LinearLayout layout_payment_bank_3;
    @BindView(R.id.search_tv_bills_title_3)
    TextView search_tv_bills_title_3;
    @BindView(R.id.search_tv_bills_content_3)
    Spinner search_tv_bills_content_3;

    @BindView(R.id.layout_payment_bank_4)
    LinearLayout layout_payment_bank_4;
    @BindView(R.id.search_tv_bills_title_4)
    TextView search_tv_bills_title_4;
    @BindView(R.id.search_ed_bills_content_4)
    EditText search_ed_bills_content_4;

    @BindView(R.id.layout_payment_bank_5)
    LinearLayout layout_payment_bank_5;
    @BindView(R.id.search_tv_bills_title_5)
    TextView search_tv_bills_title_5;
    @BindView(R.id.search_ed_bills_content_5)
    EditText search_ed_bills_content_5;

    @BindView(R.id.search_payment_btn)
    FancyButton search_payment_btn;

    public String[] settlestatus = new String[]{"全部", "执行中", "本方请求终止", "对方请求终止"};
    public String[] settlestatusID = new String[]{"1", "2", "3", "4"};
    public String[] registerStatus = new String[]{"全部", "未申请登记", "已申请登记"};
    public String[] registerStatusID = new String[]{"0", "1", "2"};
    private TestArrayAdapter querySttleManageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_search);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.title_activity_card__search));
        titleRight.setVisibility(View.GONE);
        activity_type = getIntent().getIntExtra(Constant.BUNDLE_TYPE, 0);
        search_payment_btn.setOnClickListener(this);

        //根据类型设置不同的布局
        switch (activity_type) {
            case Constant.PAYMENT_SEARCH_TYPE:
//                toastSHORT("付款查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                layout_payment_bank_4.setVisibility(View.VISIBLE);
                search_tv_bills_title_4.setText(getText(R.string.tv_search_payment_buy_name));
                search_ed_bills_content_4.setHint(getText(R.string.ed_search_hint_buy_name));
                break;
            case Constant.RECEIPT_SEARCH_TYPE:
//                toastSHORT("收款查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                layout_payment_bank_4.setVisibility(View.VISIBLE);
                search_tv_bills_title_4.setText(getText(R.string.tv_search_payment_buy_name));
                search_ed_bills_content_4.setHint(getText(R.string.ed_search_hint_buy_name));
                break;
            case Constant.REFUND_SEARCH_TYPE:
//                toastSHORT("退款查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                layout_payment_bank_4.setVisibility(View.VISIBLE);
                search_tv_bills_title_4.setText(getText(R.string.tv_search_payment_buy_name));
                search_ed_bills_content_4.setHint(getText(R.string.ed_search_hint_buy_name));
                break;
            case Constant.RECEIVE_REFUND_SEARCH_TYPE:
//                toastSHORT("收退款查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                layout_payment_bank_4.setVisibility(View.VISIBLE);
                search_tv_bills_title_4.setText(getText(R.string.tv_receive_refund_title));
                search_ed_bills_content_4.setHint(getText(R.string.hint_receive_refund_title));
                break;
            case Constant.BANK_BILLS_ACTIVITY_TYPE:
//                toastSHORT("银行账户查询界面");
                search_tv_bills_title_1.setText(getText(R.string.item_account_name2));
                search_ed_bills_content_1.setHint(getText(R.string.edhint_account_name));
                break;
            case Constant.MMBWAREHOUSE_BILLS_ACTIVITY_TYPE:
//                toastSHORT("地址列表查询界面");
                search_tv_bills_title_1.setText(getText(R.string.hint_tv_express_address_title));
                search_ed_bills_content_1.setHint(getText(R.string.hint_ed_express_address_title));
                break;
            case Constant.ECPRESS_ADDRESS_ACTIVITY_TYPE:
//                toastSHORT("收货查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                break;
            case Constant.ECPRESS_SEND_ACTIVITY_TYPE:
//                toastSHORT("发货查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                break;
            case Constant.ECPRESS_QUIT_ACTIVITY_TYPE:
//                toastSHORT("退货查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                break;
            case Constant.ECPRESS_QUIT_HARVEST_ACTIVITY_TYPE:
//                toastSHORT("退货签收查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                break;
            case Constant.CREATE_SECTION_BILLS_ACTIVITY_TYPE:
//                toastSHORT("创建结款单查询界面");
                search_tv_bills_title_1.setText(getText(R.string.hint_search_execyte_title_1));
                search_ed_bills_content_1.setHint(getText(R.string.hint_ed_over_bills_id_title));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.hint_search_execyte_title_3));
                search_ed_bills_content_2.setHint(getText(R.string.hint_ed_over_opposite_title1));
                break;
            case Constant.ADD_SECTION_BILLS_ACTIVITY_TYPE:
//                toastSHORT("添加订单查询界面");
                search_tv_bills_title_1.setText(getText(R.string.tv_search_payment_id));
                search_ed_bills_content_1.setHint(getText(R.string.ed_search_hint_id));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.tv_search_payment_goods_name));
                search_ed_bills_content_2.setHint(getText(R.string.ed_search_hint_goods_name));
                break;
            case Constant.WAIT_CHECK_BILLS_ACTIVITY_TYPE:
//                toastSHORT("待审批结款单查询界面");
                search_tv_bills_title_1.setText(getText(R.string.hint_search_execyte_title_1));
                search_ed_bills_content_1.setHint(getText(R.string.hint_ed_over_bills_id_title));
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                search_tv_bills_title_2.setText(getText(R.string.hint_search_execyte_title_3));
                search_ed_bills_content_2.setHint(getText(R.string.hint_ed_over_opposite_title1));
                break;
            case Constant.EXECUTE_BILLS_ACTIVITY_TYPE:
//                toastSHORT("执行中结款单");
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                layout_payment_bank_3.setVisibility(View.VISIBLE);
                layout_payment_bank_4.setVisibility(View.VISIBLE);
                layout_payment_bank_5.setVisibility(View.VISIBLE);
                search_tv_bills_title_1.setText(getText(R.string.hint_search_execyte_title_1));
                search_ed_bills_content_1.setHint(getText(R.string.hint_ed_over_bills_id_title));

                search_tv_bills_title_2.setText(getText(R.string.hint_search_execyte_title_3));
                search_ed_bills_content_2.setHint(getText(R.string.hint_ed_over_opposite_title1));

                search_tv_bills_title_3.setText(getText(R.string.hint_search_execyte_title_5));
                querySttleManageAdapter = new TestArrayAdapter(this, settlestatus);
                search_tv_bills_content_3.setAdapter(querySttleManageAdapter);

                search_tv_bills_title_4.setText(getText(R.string.hint_search_execyte_title_6));
                search_ed_bills_content_4.setFocusable(false);
                search_ed_bills_content_4.setHint(getText(R.string.hint_search_execyte_title_7));
                search_ed_bills_content_4.setOnClickListener(this);

                search_tv_bills_title_5.setText(getText(R.string.hint_search_execyte_title_8));
                search_ed_bills_content_5.setFocusable(false);
                search_ed_bills_content_5.setHint(getText(R.string.hint_search_execyte_title_9));
                search_ed_bills_content_5.setOnClickListener(this);

                break;
            case Constant.REGISTER_BILLS_ACTIVITY_TYPE:
//                toastSHORT("结款单登记担保列表");
                layout_payment_bank_2.setVisibility(View.VISIBLE);
                layout_payment_bank_3.setVisibility(View.VISIBLE);
                layout_payment_bank_4.setVisibility(View.VISIBLE);
                layout_payment_bank_5.setVisibility(View.VISIBLE);
                search_tv_bills_title_1.setText(getText(R.string.hint_search_execyte_title_1));
                search_ed_bills_content_1.setHint(getText(R.string.hint_ed_over_bills_id_title));

                search_tv_bills_title_2.setText(getText(R.string.hint_search_execyte_title_3));
                search_ed_bills_content_2.setHint(getText(R.string.hint_ed_over_opposite_title1));

                search_tv_bills_title_3.setText(getText(R.string.hint_status_regist_assure_title_7));
                querySttleManageAdapter = new TestArrayAdapter(getApplicationContext(), registerStatus);
                search_tv_bills_content_3.setAdapter(querySttleManageAdapter);

                search_tv_bills_title_4.setText(getText(R.string.hint_search_execyte_title_6));
                search_ed_bills_content_4.setFocusable(false);
                search_ed_bills_content_4.setHint(getText(R.string.hint_search_execyte_title_7));
                search_ed_bills_content_4.setOnClickListener(this);

                search_tv_bills_title_5.setText(getText(R.string.hint_search_execyte_title_8));
                search_ed_bills_content_5.setFocusable(false);
                search_ed_bills_content_5.setHint(getText(R.string.hint_search_execyte_title_9));
                search_ed_bills_content_5.setOnClickListener(this);
                break;
        }
    }

    /**
     * 判断按钮的点击事件
     */
    private void searchBtn() {
        switch (activity_type) {
            case Constant.PAYMENT_SEARCH_TYPE:
                //("付款查询界面");
                //订单号
                Constant.appOrderMoney_orderId = getEdVaule(search_ed_bills_content_1);
                //商品名
                Constant.appOrderMoney_goodsName = getEdVaule(search_ed_bills_content_2);
                //f付款方
                Constant.appOrderMoney_buyersName = getEdVaule(search_ed_bills_content_4);
                setResult(Constant.PAYMENTRESULTOK);
                finish();


                break;
            case Constant.RECEIPT_SEARCH_TYPE:
                //("收款查询界面");
                Constant.appOrderMoney_orderId = getEdVaule(search_ed_bills_content_1);
                //商品名
                Constant.appOrderMoney_goodsName = getEdVaule(search_ed_bills_content_2);
                //f付款方
                Constant.appOrderMoney_buyersName = getEdVaule(search_ed_bills_content_4);
                setResult(Constant.PAYMENTRESULTOK);
                finish();

                break;
            case Constant.REFUND_SEARCH_TYPE:
                //("退款查询界面");
                Constant.appOrderMoney_orderId = getEdVaule(search_ed_bills_content_1);
                //商品名
                Constant.appOrderMoney_goodsName = getEdVaule(search_ed_bills_content_2);
                //f付款方
                Constant.appOrderMoney_buyersName = getEdVaule(search_ed_bills_content_4);
                setResult(Constant.PAYMENTRESULTOK);
                finish();

                break;
            case Constant.RECEIVE_REFUND_SEARCH_TYPE:
                //("收退款查询界面");
                Constant.appOrderMoney_orderId=getEdVaule(search_ed_bills_content_1);
                //商品名
                Constant.appOrderMoney_goodsName=getEdVaule(search_ed_bills_content_2);
                //f付款方
                Constant.appOrderMoney_buyersName=getEdVaule(search_ed_bills_content_4);
                setResult(Constant.PAYMENTRESULTOK);
                finish();


                break;
            case Constant.BANK_BILLS_ACTIVITY_TYPE:
                //("银行账户查询界面");
                Constant.SEARCH_Account_Name = getEdVaule(search_ed_bills_content_1);
                setResult(Constant.CARD_QUERY);
                finish();
                break;
            case Constant.MMBWAREHOUSE_BILLS_ACTIVITY_TYPE:
                //("地址列表查询界面");
                Constant.SEARCH_sendgoods_OrdertitleCode = getEdVaule(search_ed_bills_content_1);
                setResult(Constant.ADDRESS_SEARCH);
                finish();
                break;
            case Constant.ECPRESS_ADDRESS_ACTIVITY_TYPE:
                //("收货查询界面");
                 Constant.SEARCH_sendgoods_OrdertitleCode = getEdVaule(search_ed_bills_content_1);
                 Constant.SEARCH_sendgoods_OrderGoodName  = getEdVaule(search_ed_bills_content_2);
                setResult(Constant.QUIT_GET_SEARCH);
                finish();
                break;
            case Constant.ECPRESS_SEND_ACTIVITY_TYPE:
                //("发货查询界面");
                Constant.SEARCH_sendgoods_OrdertitleCode = getEdVaule(search_ed_bills_content_1);
                Constant.SEARCH_sendgoods_OrderGoodName  = getEdVaule(search_ed_bills_content_2);
                setResult(Constant.SENDGOODS_SEARCH);
                finish();
                break;
            case Constant.ECPRESS_QUIT_ACTIVITY_TYPE:
                //("退货查询界面");
                 Constant.SEARCH_sendgoods_OrdertitleCode = getEdVaule(search_ed_bills_content_1);
                 Constant.SEARCH_sendgoods_OrderGoodName  = getEdVaule(search_ed_bills_content_2);
                setResult(Constant.QUIT_SEARCH);
                finish();
                break;
            case Constant.ECPRESS_QUIT_HARVEST_ACTIVITY_TYPE:
                //("退货签收查询界面");
//                toastSHORT("退货签收");
                 Constant.SEARCH_sendgoods_OrdertitleCode= getEdVaule(search_ed_bills_content_1);
                 Constant.SEARCH_sendgoods_OrderGoodName = getEdVaule(search_ed_bills_content_2);
                setResult(Constant.QUIT_RETURN_SEARCH);
                finish();
                break;
            case Constant.CREATE_SECTION_BILLS_ACTIVITY_TYPE:
                //("创建结款单查询界面");
                Constant.SEARCH_sendgoods_OrdertitleCode   = getEdVaule(search_ed_bills_content_1);
                Constant.SEARCH_sendgoods_OrderGoodName = getEdVaule(search_ed_bills_content_2);
                setResult(Constant.CREATE_MYPENDINGSTTLE);
                finish();
                break;
            case Constant.ADD_SECTION_BILLS_ACTIVITY_TYPE:
                //("添加订单查询界面");
                Constant.SEARCH_sendgoods_OrdertitleCode   = getEdVaule(search_ed_bills_content_1);
                Constant.SEARCH_sendgoods_OrderGoodName = getEdVaule(search_ed_bills_content_2);
                setResult(Constant.ADD_SECTION_BILLS_ACTIVITY_TYPE_SEARCH);
                finish();
                break;
            case Constant.WAIT_CHECK_BILLS_ACTIVITY_TYPE:
                //("待审批结款单查询界面");
                Constant.SEARCH_sendgoods_OrdertitleCode = getEdVaule(search_ed_bills_content_1);
                Constant.SEARCH_sendgoods_OrderGoodName = getEdVaule(search_ed_bills_content_2);
                setResult(Constant.WAIT_CHECK_BILLS_ACTIVITY_TYPE_SEARCH);
                finish();
                break;
            case Constant.EXECUTE_BILLS_ACTIVITY_TYPE:
                //("执行中结款单");
                Constant.appOrderMoney_settleCode = getEdVaule(search_ed_bills_content_1);//	string	结款单号
                Constant.appOrderMoney_oppositeName = getEdVaule(search_ed_bills_content_2);//	string	结款对方
                Constant.appOrderMoney_settlestatus = settlestatusID[search_tv_bills_content_3.getSelectedItemPosition()];
                //	string	状态（1全部、2执行中、3本方请求终止、4对方请求终止）
                Constant.appOrderMoney_executeStartTime = getEdVaule(search_ed_bills_content_4);//datetime		开始时间
                Constant.appOrderMoney_executeEndTime = getEdVaule(search_ed_bills_content_5);//datetime		结束时间
                Constant.FRGMENT_TYPE = search_tv_bills_content_3.getSelectedItemPosition();
                setResult(Constant.appSettle_querySttleManage);
                finish();
                break;
            case Constant.REGISTER_BILLS_ACTIVITY_TYPE:
                //("结款单登记担保列表");
                Constant.SEARCH_sendgoods_OrdertitleCode = getEdVaule(search_ed_bills_content_1);//	string	结款单号
                Constant.SEARCH_sendgoods_OrderGoodName = getEdVaule(search_ed_bills_content_2);//	string	结款对方
                Constant.appOrderMoney_settlestatus = registerStatusID[search_tv_bills_content_3.getSelectedItemPosition()];
                //	string	状态（1全部、2执行中、3本方请求终止、4对方请求终止）
                Constant.appOrderMoney_executeStartTime = getEdVaule(search_ed_bills_content_4);//datetime		开始时间
                Constant.appOrderMoney_executeEndTime = getEdVaule(search_ed_bills_content_5);//datetime		结束时间
                Constant.FRGMENT_TYPE = search_tv_bills_content_3.getSelectedItemPosition();
                setResult(Constant.REGISTER_BILLS_ACTIVITY_TYPE_SEARCH);
                finish();
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_ed_bills_content_4:
                showCalendar(search_ed_bills_content_4);
                break;
            case R.id.search_ed_bills_content_5:
                showCalendar(search_ed_bills_content_5);
                break;
            case R.id.search_payment_btn:
                searchBtn();
                break;
        }
    }
}
