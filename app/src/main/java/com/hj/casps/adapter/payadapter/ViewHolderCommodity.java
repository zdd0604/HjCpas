package com.hj.casps.adapter.payadapter;

import android.view.View;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.entity.appordergoods.OrdertitleData;

import java.text.DecimalFormat;

/**
 * Created by Admin on 2017/4/19.
 * 商品清单
 */

public class ViewHolderCommodity extends TypeAbstractViewHolder<OrdertitleData> {
    private TextView rl_city_commodity_name, rl_city_commodity_money,
            rl_city_commodity_one_money, rl_city_commodity_count,
            rl_city_await_count, rl_city_reality_count,
            rl_city_await_money, rl_reality_money;


    public ViewHolderCommodity(View itemView) {
        super(itemView);

        rl_city_commodity_name = (TextView) itemView.findViewById(R.id.rl_city_commodity_name);
        rl_city_commodity_money = (TextView) itemView.findViewById(R.id.rl_city_commodity_money);
        rl_city_commodity_one_money = (TextView) itemView.findViewById(R.id.rl_city_commodity_one_money);
        rl_city_commodity_count = (TextView) itemView.findViewById(R.id.rl_city_commodity_count);
        rl_city_await_count = (TextView) itemView.findViewById(R.id.rl_city_await_count);
        rl_city_reality_count = (TextView) itemView.findViewById(R.id.rl_city_reality_count);
        rl_city_await_money = (TextView) itemView.findViewById(R.id.rl_city_await_money);
        rl_reality_money = (TextView) itemView.findViewById(R.id.rl_reality_money);

    }

    @Override
    public void bindViewHolder(OrdertitleData cargoMessage, int postion) {
        DecimalFormat df = new DecimalFormat("#0.00");
        rl_city_commodity_name.setText(cargoMessage.goodsName);
        rl_city_commodity_money.setText(df.format(cargoMessage.money));
        rl_city_commodity_one_money.setText(df.format(cargoMessage.price));
        rl_city_commodity_count.setText(df.format(cargoMessage.goodsNum));

        rl_city_await_count.setText(df.format(cargoMessage.exeSendgoodsNum));
        rl_city_reality_count.setText(df.format(cargoMessage.exeReturngoodsNum));
        rl_city_await_money.setText(df.format(cargoMessage.exePaymoneyNum));
        rl_reality_money.setText(df.format(cargoMessage.exeRefundNum));
    }


}
