package com.hj.casps.adapter.overdealadapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.TypeAbstractViewHolder;
import com.hj.casps.entity.appordermoney.QueryPayMoneyOrderForSettleEntity;
import com.hj.casps.util.StringUtils;

/**
 * Created by Admin on 2017/4/19.
 * 订单详情
 */

public class ViewHolderCreaSettlTwo extends TypeAbstractViewHolder<QueryPayMoneyOrderForSettleEntity> {

    private TextView vholder_two_tv_bills_id_content,
            vholder_two_tv_commodity_name_content,
            vholder_two_tv_bills_money_content,
            vholder_two_tv_await_money_content;

    EditText vholder_two_tv_bills_reality_money_content;
    private CheckBox vholder_two_payment_ck_bills;

    public static onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(ViewHolderCreaSettlTwo.onCheckedkType onCheckedkType) {
        ViewHolderCreaSettlTwo.onCheckedkType = onCheckedkType;
    }


    public ViewHolderCreaSettlTwo(View itemView) {
        super(itemView);
        itemView.findViewById(R.id.layout_right_layout_2).setVisibility(View.GONE);
        itemView.findViewById(R.id.layout_right_layout_3).setVisibility(View.VISIBLE);
        vholder_two_payment_ck_bills = (CheckBox) itemView.findViewById(R.id.layout_check_order_1);
        vholder_two_tv_bills_id_content = (TextView) itemView.findViewById(R.id.layout_tv_order_3);
        vholder_two_tv_commodity_name_content = (TextView) itemView.findViewById(R.id.layout_left_tv_content_1);
        vholder_two_tv_bills_money_content = (TextView) itemView.findViewById(R.id.layout_left_tv_content_2);
        vholder_two_tv_await_money_content = (TextView) itemView.findViewById(R.id.layout_right_tv_content_1);
        vholder_two_tv_bills_reality_money_content = (EditText) itemView.findViewById(R.id.layout_right_tv_content_3);
    }

    @Override
    public void bindViewHolder(final QueryPayMoneyOrderForSettleEntity commEntity, final int postion) {
        vholder_two_tv_bills_id_content.setText(commEntity.getOrdertitleNumber() + "");
        vholder_two_tv_commodity_name_content.setText(commEntity.getGoodsName());
        vholder_two_tv_bills_money_content.setText(commEntity.getMoney() + "");
        vholder_two_tv_await_money_content.setText(commEntity.getExePaymoneyNum() + "");
        vholder_two_payment_ck_bills.setChecked(commEntity.isCheck());
        vholder_two_payment_ck_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vholder_two_payment_ck_bills.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(postion);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(postion);
                }
                commEntity.setCheck(vholder_two_payment_ck_bills.isChecked());
            }
        });
        vholder_two_tv_bills_reality_money_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    commEntity.setEndPaymoneyNum("");
                } else if (StringUtils.isStrTrue(vholder_two_tv_bills_reality_money_content.getText().toString().trim())) {
                    commEntity.setEndPaymoneyNum(vholder_two_tv_bills_reality_money_content.getText().toString().trim());
                }
            }
        });
    }


    public interface onCheckedkType {
        void onCheckedY(int pos);
        void onCheckedN(int pos);
    }

}
