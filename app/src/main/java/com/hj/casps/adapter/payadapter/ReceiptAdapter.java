package com.hj.casps.adapter.payadapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    private int index1 = -1;
    private int index2 = -1;
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
        final EditText remarkEd = hooder.getView(R.id.ed_receipt_remark_content);

        //设置备注不可编辑
        remarkEd.setFocusable(false);
        remarkEd.setEnabled(false);
       /* if (remarkEd.getTag() instanceof TextWatcher) {
            remarkEd.removeTextChangedListener((TextWatcher) remarkEd.getTag());
            remarkEd.clearFocus();
        }*/
        TextWatcher remarkWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    info.setId("");
                } else {

                }
            }
        };
//        remarkEd.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    index2 = indexPos + 1;
//                }
//                return false;
//            }
//        });
//        remarkEd.addTextChangedListener(remarkWatcher);
//        remarkEd.setTag(remarkWatcher);

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

        hooder.setText(R.id.tv_receipt_bills_id, info.getOrdertitleNumber() + "");
        hooder.setText(R.id.tv_receipt_title_one, info.getGoodsName());
        hooder.setText(R.id.tv_receipt_payee_content, info.getPaymoneyName());
        hooder.setText(R.id.tv_receipt_payee_money_content, Constant.getNum(info.getMoney()));
        String time = Constant.stmpToDate(info.getPaymoneyTime());
        hooder.setText(R.id.tv_receipt_time_content, time);
        //备注
//        hooder.setEdiTextView(R.id.ed_receipt_remark_content);
//        if (index2 != -1 && index2 == indexPos + 1) {
//            //强制加上焦点
//            remarkEd.requestFocus();
//            //设置光标显示到编辑框尾部
//            remarkEd.setSelection(remarkEd.getText().length());
//            //重置
//            index2 = -1;
//        }
    }

    public interface onCheckedkType {
        void onCheckedY(int pos);

        void onCheckedN(int pos);

        void onBillsIDItemCilckListener(int pos);
    }
}
