package com.hj.casps.adapter.payadapter;

import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hj.casps.R;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.paymentmanager.response.ResqueryGetRefundMoneyEntity;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 * 收款列表adapter
 */

public class ReceiveRefundAdapter extends CommonAdapter<ResqueryGetRefundMoneyEntity> {
    private int index1 = -1;
    private int index2 = -1;
    public static onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(ReceiveRefundAdapter.onCheckedkType onCheckedkType) {
        ReceiveRefundAdapter.onCheckedkType = onCheckedkType;
    }

    public ReceiveRefundAdapter(Context context, List<ResqueryGetRefundMoneyEntity> datas) {
        super(context, datas, R.layout.receipt_item);
        this.mContext = context;
    }

    @Override
    public void concert(ViewHolder hooder, final ResqueryGetRefundMoneyEntity info, final int indexPos) {
        final EditText remarkEd = hooder.getView(R.id.ed_receipt_remark_content);
        remarkEd.setText(info.getRemark());
        //设置备注不可编辑
        remarkEd.setFocusable(false);
        remarkEd.setEnabled(false);

       /* if (remarkEd.getTag() instanceof TextWatcher) {
            remarkEd.removeTextChangedListener((TextWatcher) remarkEd.getTag());
            remarkEd.clearFocus();
        }*/
        hooder.getView(R.id.tv_receipt_bills_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCheckedkType != null) {
                    onCheckedkType.onBillsIDItemCilckListener(indexPos);
                }
            }
        });

        final CheckBox checkBox = hooder.getView(R.id.ck_receipt_bills);
        checkBox.setChecked(info.isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setChecked(checkBox.isChecked());
                if (checkBox.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
            }
        });

        hooder.setText(R.id.tv_receipt_bills_id, info.getOrdertitleNumber() + "");
        hooder.setText(R.id.tv_receipt_title_one, info.getGoodsName());
        //退款方
        hooder.setTextInt(R.id.tv_receipt_payee_title, R.string.head_receive_refund_title);
        hooder.setText(R.id.tv_receipt_payee_content, info.getPaymoneyName());
        //退款金额
        hooder.setTextInt(R.id.tv_receipt_payee_money_title, R.string.head_receive_refund_money_title);
       /* TextView tv_payee_money_content= hooder.getView();
        //退款金额
        tv_payee_money_content.setText(info.getMoney());*/
        hooder.setText(R.id.tv_receipt_payee_money_content,Constant.getNum(info.getMoney()));
        String time = Constant.stmpToDate(info.getPaymoneyTime());
        //付款时间
        hooder.setText(R.id.tv_receipt_time_content, time);

    }

    public interface onCheckedkType {
        void onCheckedY(int pos);
        void onCheckedN(int pos);

        void onBillsIDItemCilckListener(int pos);
    }
}
