package com.hj.casps.adapter.payadapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hj.casps.R;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.paymentmanager.response.ResQueryGetMoneyEntity;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 * 收款列表adapter
 */

public class ReceiptAdapter extends CommonAdapter<ResQueryGetMoneyEntity> {
    public static onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(ReceiptAdapter.onCheckedkType onCheckedkType) {
        ReceiptAdapter.onCheckedkType = onCheckedkType;
    }

    public ReceiptAdapter(Context context, List<ResQueryGetMoneyEntity> datas) {
        super(context, datas, R.layout.receipt_item);
        this.mContext = context;
    }

    @Override
    public void concert(ViewHolder hooder, final ResQueryGetMoneyEntity info, final int indexPos) {
        hooder.setText(R.id.tv_receipt_bills_id, info.getOrdertitleNumber() + "");
        hooder.setText(R.id.tv_receipt_title_one, info.getGoodsName());
        hooder.setText(R.id.tv_receipt_payee_content, info.getPaymoneyName());
        hooder.setText(R.id.tv_receipt_payee_money_content, Constant.getNum(info.getMoney()));
        String time = Constant.stmpToDate(info.getPaymoneyTime());
        hooder.setText(R.id.tv_receipt_time_content, time);
        final EditText remarkEd = hooder.getView(R.id.ed_receipt_remark_content);
        remarkEd.setText(info.getRemark());
        //设置备注不可编辑
        remarkEd.setFocusable(false);
        remarkEd.setEnabled(false);

        hooder.getView(R.id.tv_receipt_bills_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCheckedkType != null) {
                    onCheckedkType.onBillsIDItemCilckListener(indexPos);
                }
            }
        });

        final CheckBox checkBox = hooder.getView(R.id.ck_receipt_bills);
        checkBox.setChecked(info.getIsChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setIsChecked(checkBox.isChecked());
                if (checkBox.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
            }
        });
    }

    public interface onCheckedkType {
        void onCheckedY(int pos);

        void onCheckedN(int pos);

        void onBillsIDItemCilckListener(int pos);
    }
}
