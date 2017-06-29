package com.hj.casps.adapter.payadapter;

import android.view.View;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appordergoods.AppOrderCheckOrderOrdertitle;
import com.hj.casps.util.StringUtils;

import java.text.DecimalFormat;

import static com.hj.casps.common.Constant.GetWorkFlowType;

/**
 * Created by Admin on 2017/4/19.
 * 订单详情
 */

public class ViewHolderFather extends TypeAbstractViewHolder<AppOrderCheckOrderOrdertitle> {
    private TextView rl_one_tv_bills_id,
            rl_ft_tv_bills_type,
            rl_ft_tv_flow_id,
            rl_ft_tv_buy_name,
            rl_ft_tv_contract_time,
            rl_ft_tv_payment_time,
            rl_ft_tv_start_delivery,
            rl_ft_tv_end_delivery,
            rl_ft_tv_sum_money,
            rl_ft_tv_payment_card_id,
            rl_ft_tv_gathering_card,
            rl_ft_tv_payment_address,
            rl_ft_tv_payment_address_f,
            layout_tv_order_2;


    public ViewHolderFather(View itemView) {
        super(itemView);
        itemView.findViewById(R.id.layout_check_order_1).setVisibility(View.GONE);
        layout_tv_order_2 = (TextView) itemView.findViewById(R.id.layout_tv_order_2);
        layout_tv_order_2.setText("订单号：");
        rl_one_tv_bills_id = (TextView) itemView.findViewById(R.id.layout_tv_order_3);
        rl_ft_tv_bills_type = (TextView) itemView.findViewById(R.id.rl_ft_tv_bills_type);
        rl_ft_tv_flow_id = (TextView) itemView.findViewById(R.id.rl_ft_tv_flow_id);
        rl_ft_tv_buy_name = (TextView) itemView.findViewById(R.id.rl_ft_tv_buy_name);
        rl_ft_tv_contract_time = (TextView) itemView.findViewById(R.id.rl_ft_tv_contract_time);
        rl_ft_tv_payment_time = (TextView) itemView.findViewById(R.id.rl_ft_tv_payment_time);
        rl_ft_tv_start_delivery = (TextView) itemView.findViewById(R.id.rl_ft_tv_start_delivery);
        rl_ft_tv_end_delivery = (TextView) itemView.findViewById(R.id.rl_ft_tv_end_delivery);
        rl_ft_tv_sum_money = (TextView) itemView.findViewById(R.id.rl_ft_tv_sum_money);
        rl_ft_tv_payment_card_id = (TextView) itemView.findViewById(R.id.rl_ft_tv_payment_card_id);
        rl_ft_tv_gathering_card = (TextView) itemView.findViewById(R.id.rl_ft_tv_gathering_card);
        rl_ft_tv_payment_address = (TextView) itemView.findViewById(R.id.rl_ft_tv_payment_address);
        rl_ft_tv_payment_address_f = (TextView) itemView.findViewById(R.id.rl_ft_tv_payment_address_f);
    }

    @Override
    public void bindViewHolder(AppOrderCheckOrderOrdertitle cargoMessage, int postion) {
        DecimalFormat df = new DecimalFormat("#0.00");
        rl_one_tv_bills_id.setText(cargoMessage.ordertitleCode);
        if (StringUtils.isStrTrue(cargoMessage.buyersId))
            rl_ft_tv_bills_type.setText(cargoMessage.buyersId.equalsIgnoreCase(Constant.publicArg.getSys_member()) ? "采购" : "销售");
        rl_ft_tv_flow_id.setText(GetWorkFlowType(cargoMessage.workflowTypeId));
        rl_ft_tv_buy_name.setText(cargoMessage.buyersName);
        rl_ft_tv_contract_time.setText(Constant.stmpToDate(cargoMessage.lockTime));
        rl_ft_tv_payment_time.setText(Constant.stmpToDate(cargoMessage.payTime));
        rl_ft_tv_start_delivery.setText(Constant.stmpToDate(cargoMessage.executeStartTime));
        rl_ft_tv_end_delivery.setText(Constant.stmpToDate(cargoMessage.executeEndTime));
        rl_ft_tv_sum_money.setText(df.format(cargoMessage.totalMoney));
        if (StringUtils.isStrTrue(cargoMessage.payBank) ||
                StringUtils.isStrTrue(cargoMessage.payAccount))
            rl_ft_tv_payment_card_id.setText(cargoMessage.payBank + "\n" + cargoMessage.payAccount);
        if (StringUtils.isStrTrue(cargoMessage.getBank) ||
                StringUtils.isStrTrue(cargoMessage.getAccount))
            rl_ft_tv_gathering_card.setText(cargoMessage.getBank + "\n" + cargoMessage.getAccount);
        rl_ft_tv_payment_address.setText(cargoMessage.buyersAddressName);
        rl_ft_tv_payment_address_f.setText(cargoMessage.sellersAddressName);
    }
}
