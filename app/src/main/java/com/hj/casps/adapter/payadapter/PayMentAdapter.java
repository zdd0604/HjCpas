package com.hj.casps.adapter.payadapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBase;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.paymentmanager.response.ResponseQueryPayEntity;
import com.hj.casps.util.StringUtils;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 * 付款列表adapter
 */

public class PayMentAdapter extends CommonAdapter<ResponseQueryPayEntity> {
    public static onCheckedkType onCheckedkType;
    private int index1 = -1;
    private int index2 = -1;


    public static void setOnCheckedkType(PayMentAdapter.onCheckedkType onCheckedkType) {
        PayMentAdapter.onCheckedkType = onCheckedkType;
    }

    public PayMentAdapter(Context context, List<ResponseQueryPayEntity> datas) {
        super(context, datas, R.layout.commodity_item);
        this.mContext = context;
    }

    @Override
    public void concert(final ViewHolder hooder, final ResponseQueryPayEntity payMnetInfo, final int indexPos) {
        final EditText et_now_money = hooder.getView(R.id.ed_now_money);
        final EditText et_payment_remark = hooder.getView(R.id.ed_payment_remark);
        //付款方
        hooder.setTextInt(R.id.tv_bank_name_content_title, R.string.head_payee_title);
        //总金额
        hooder.setTextInt(R.id.tv_sum_money_title, R.string.pay_sum_money);
        //已付金额
        hooder.setTextInt(R.id.tv_already_money_title, R.string.pay_already_money);
        //待付金额
        hooder.setTextInt(R.id.tv_await_money_title, R.string.pay_await_money);
        //本次付款金额
        hooder.setTextInt(R.id.ed_now_money_title, R.string.pay_now_money);
        //本次付款账号
        hooder.setTextInt(R.id.tv_payment_id_title, R.string.pay_ment_id);

        //设置金额为保留两位小数
        ActivityBase.setPricePoint(et_now_money);

        if (et_payment_remark.getTag() instanceof TextWatcher) {
            et_payment_remark.removeTextChangedListener((TextWatcher) et_payment_remark.getTag());
            et_payment_remark.clearFocus();
        }
        TextWatcher moneyWatcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    payMnetInfo.setReMark("");
                } else if (StringUtils.isStrTrue(ActivityBase.getEdVaule(et_payment_remark))) {
                    payMnetInfo.setReMark(ActivityBase.getEdVaule(et_payment_remark));
                }
            }
        };
        et_payment_remark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index2 = indexPos;
                }
                return false;
            }
        });

        et_payment_remark.addTextChangedListener(moneyWatcher2);
        et_payment_remark.setTag(moneyWatcher2);


        //付款的
        if (et_now_money.getTag() instanceof TextWatcher) {
            et_now_money.removeTextChangedListener((TextWatcher) et_now_money.getTag());
            et_now_money.clearFocus();
        }
        TextWatcher moneyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    payMnetInfo.setPayNum("");
                } else if (StringUtils.isStrTrue(ActivityBase.getEdVaule(et_now_money))) {
                    payMnetInfo.setPayNum(ActivityBase.getEdVaule(et_now_money));
                }
            }
        };
        et_now_money.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index1 = indexPos;
                }
                return false;
            }
        });
        et_now_money.addTextChangedListener(moneyWatcher);
        et_now_money.setTag(moneyWatcher);


        hooder.setText(R.id.layout_tv_order_3, payMnetInfo.getOrdertitleNumber() + "");
        hooder.setText(R.id.tv_goodname, payMnetInfo.getGoodsName());
        hooder.setText(R.id.tv_bank_name_content, payMnetInfo.getSellersName());
        hooder.setText(R.id.tv_sum_money, payMnetInfo.getMoney() + "");
        hooder.setCheckBox(R.id.layout_check_order_1, payMnetInfo.isChecked());
        hooder.setText(R.id.tv_already_money, Constant.getNum(payMnetInfo.getPaymoneyNum()));
        hooder.setText(R.id.tv_await_money, Constant.getNum(payMnetInfo.getExePaymoneyNum()));


        if (payMnetInfo.getReMark() != null) {
            hooder.setEdiTextView(R.id.ed_payment_remark, payMnetInfo.getReMark());
        }
        //付款账号
        final TextView tv_payMengCode = hooder.getView(R.id.tv_payment_id);

        if (payMnetInfo.getAccountlist().size() > 0) {
            hooder.setText(R.id.tv_payment_id, payMnetInfo.getAccountlist().get(0).getBankname() +
                    payMnetInfo.getAccountlist().get(0).getAccountno());
            payMnetInfo.setPayMentCode(payMnetInfo.getAccountlist().get(0).getAccountno());
        }
        hooder.getView(R.id.tv_payment_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedkType.onPayMentCodeItemClickListener(tv_payMengCode, indexPos);
            }
        });

        hooder.getView(R.id.layout_tv_order_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCheckedkType != null) {
                    onCheckedkType.onBillsIDItemCilckListener(indexPos);
                }
            }
        });

        final CheckBox checkBox = hooder.getView(R.id.layout_check_order_1);
        checkBox.setChecked(payMnetInfo.isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payMnetInfo.setChecked(checkBox.isChecked());
                if (checkBox.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
            }
        });

        if (index1 != -1 && index1 == indexPos) {
            //强制加上焦点
            et_now_money.requestFocus();
            //设置光标显示到编辑框尾部
            et_now_money.setSelection(et_now_money.getText().length());
            //重置
            index1 = -1;
        }
        if (index2 != -1 && index2 == indexPos) {
            //强制加上焦点
            et_payment_remark.requestFocus();
            //设置光标显示到编辑框尾部
            et_payment_remark.setSelection(et_payment_remark.getText().length());
            //重置
            index2 = -1;
        }
    }

    public interface onCheckedkType {

        void onCheckedY(int pos);

        void onCheckedN(int pos);

        //订单的回调
        void onBillsIDItemCilckListener(int pos);

        //选择银行账号的回调
        void onPayMentCodeItemClickListener(TextView v, int pos);

    }


}
