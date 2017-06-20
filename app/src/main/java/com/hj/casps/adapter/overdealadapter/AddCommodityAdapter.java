package com.hj.casps.adapter.overdealadapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.appordermoney.QueryPayMoneyOrderForSettleEntity;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 * 添加结款单订单李诶包
 */

public class AddCommodityAdapter extends CommonAdapter<QueryPayMoneyOrderForSettleEntity> {

    public static onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(AddCommodityAdapter.onCheckedkType onCheckedkType) {
        AddCommodityAdapter.onCheckedkType = onCheckedkType;
    }

    public AddCommodityAdapter(Context context, List<QueryPayMoneyOrderForSettleEntity> datas) {
        super(context, datas, R.layout.create_settl_vholder_two);
        this.mContext = context;
    }

    @Override
    public void concert(final ViewHolder hooder, final QueryPayMoneyOrderForSettleEntity queryPayMoneyOrderForSettleEntity, final int indexPos) {
        hooder.setText(R.id.layout_tv_order_3, queryPayMoneyOrderForSettleEntity.getOrdertitleNumber() + "");
        hooder.setText(R.id.layout_left_tv_content_1, queryPayMoneyOrderForSettleEntity.getGoodsName());
        hooder.setText(R.id.layout_left_tv_content_2, queryPayMoneyOrderForSettleEntity.getMoney() + "");

        hooder.setTextInt(R.id.layout_right_tv_title_1, R.string.hint_settl_paymoneyNum_title);
        hooder.setText(R.id.layout_right_tv_content_1, queryPayMoneyOrderForSettleEntity.getPaymoneyNum() + "");
        hooder.setTextInt(R.id.layout_right_tv_title_2, R.string.hint_settl_await_money_title);
        hooder.setText(R.id.layout_right_tv_content_2, queryPayMoneyOrderForSettleEntity.getExePaymoneyNum() + "");
        final CheckBox tCheck = hooder.getView(R.id.layout_check_order_1);
        tCheck.setChecked(queryPayMoneyOrderForSettleEntity.isCheck());
        tCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryPayMoneyOrderForSettleEntity.setCheck(tCheck.isChecked());
                if (tCheck.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
            }
        });

        hooder.getView(R.id.layout_tv_order_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedkType.onClickBillsId(indexPos);
            }
        });
    }

    public interface onCheckedkType {
        void onCheckedY(int pos);
        void onCheckedN(int pos);
        void onClickBillsId(int pos);
    }
}
