package com.hj.casps.adapter.overdealadapter;

import android.view.View;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.TypeAbstractViewHolder;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appsettle.QuerysettleDetailOneGain;

/**
 * Created by Admin on 2017/4/19.
 * 订单详情
 */

public class ViewHolderTypeOne extends TypeAbstractViewHolder<QuerysettleDetailOneGain> {
    private TextView layout_0_tv_content, layout_1_tv_content, layout_2_tv_content,
            layout_3_tv_content, layout_4_tv_content,
            layout_5_tv_content, layout_6_tv_content,
            layout_7_tv_content, layout_8_tv_content;


    public ViewHolderTypeOne(View itemView) {
        super(itemView);

        //设置标题
        TextView layout_1_tv_tltle = (TextView) itemView.findViewById(R.id.layout_1_tv_tltle);
        layout_1_tv_tltle.setText(R.string.tv_bills_buy_name_title);
        TextView layout_2_tv_tltle = (TextView) itemView.findViewById(R.id.layout_2_tv_tltle);
        layout_2_tv_tltle.setText(R.string.hint_tv_sell_name_title);
        TextView layout_3_tv_tltle = (TextView) itemView.findViewById(R.id.layout_3_tv_tltle);
        layout_3_tv_tltle.setText(R.string.hint_tv_over_bills_money_title);
        TextView layout_4_tv_tltle = (TextView) itemView.findViewById(R.id.layout_4_tv_tltle);
        layout_4_tv_tltle.setText(R.string.hint_tv_appoint_over_bills_time_title);
        TextView layout_5_tv_tltle = (TextView) itemView.findViewById(R.id.layout_5_tv_tltle);
        layout_5_tv_tltle.setText(R.string.hint_tv_appoint_over_bills_money_title);
        TextView layout_6_tv_tltle = (TextView) itemView.findViewById(R.id.layout_6_tv_tltle);
        layout_6_tv_tltle.setText(R.string.hint_tv_bills_affirm_type_title);
        TextView layout_7_tv_tltle = (TextView) itemView.findViewById(R.id.layout_7_tv_tltle);
        layout_7_tv_tltle.setText(R.string.hint_tv_bills_signature_type_title);
        TextView layout_8_tv_tltle = (TextView) itemView.findViewById(R.id.layout_8_tv_tltle);
        layout_8_tv_tltle.setText(R.string.hint_tv_guarantee_register_type_title);

        layout_0_tv_content = (TextView) itemView.findViewById(R.id.rl_one_tv_bills_id);
        layout_1_tv_content = (TextView) itemView.findViewById(R.id.rl_ft_tv_bills_type);
        layout_2_tv_content = (TextView) itemView.findViewById(R.id.rl_ft_tv_flow_id);
        layout_3_tv_content = (TextView) itemView.findViewById(R.id.rl_ft_tv_buy_name);
        layout_4_tv_content = (TextView) itemView.findViewById(R.id.rl_ft_tv_contract_time);
        layout_5_tv_content = (TextView) itemView.findViewById(R.id.rl_ft_tv_payment_time);
        layout_6_tv_content = (TextView) itemView.findViewById(R.id.rl_ft_tv_start_delivery);
        layout_7_tv_content = (TextView) itemView.findViewById(R.id.rl_ft_tv_end_delivery);
        layout_8_tv_content = (TextView) itemView.findViewById(R.id.rl_ft_tv_sum_money);

        itemView.findViewById(R.id.layout_bills_9).setVisibility(View.GONE);
        itemView.findViewById(R.id.layout_bills_10).setVisibility(View.GONE);
        itemView.findViewById(R.id.layout_bills_11).setVisibility(View.GONE);
    }

    @Override
    public void bindViewHolder(QuerysettleDetailOneGain querysettleDetailOneGain, final int postion) {
        layout_0_tv_content.setText(querysettleDetailOneGain.getSettleCode()+"");
        layout_1_tv_content.setText(querysettleDetailOneGain.getMmbpayName());
        layout_2_tv_content.setText(querysettleDetailOneGain.getMmbgetName());
        layout_3_tv_content.setText(querysettleDetailOneGain.getSettleMoney() + "");
        layout_4_tv_content.setText(Constant.stmpToDate(querysettleDetailOneGain.getCtrTime()));
        layout_5_tv_content.setText(querysettleDetailOneGain.getCtrMoney() + "");
        setStatusValue(querysettleDetailOneGain.getStatus());
        setStatusSingnValue(querysettleDetailOneGain.getStatusSingn());
        setStatusRegistValue(querysettleDetailOneGain.getStatusRegist());
    }

    private void setStatusValue(int type) {
        switch (type) {
            case Constant.STATUS_TYPE_1:
                layout_6_tv_content.setText(R.string.hint_status_type_title_1);
                break;
            case Constant.STATUS_TYPE_2:
                layout_6_tv_content.setText(R.string.hint_status_type_title_2);
                break;
            case Constant.STATUS_TYPE_3:
                layout_6_tv_content.setText(R.string.hint_status_type_title_3);
                break;
            case Constant.STATUS_TYPE_4:
                layout_6_tv_content.setText(R.string.hint_status_type_title_4);
                break;
            case Constant.STATUS_TYPE_5:
                layout_6_tv_content.setText(R.string.hint_status_type_title_5);
                break;
            case Constant.STATUS_TYPE_6:
                layout_6_tv_content.setText(R.string.hint_status_type_title_6);
                break;
            case Constant.STATUS_TYPE_7:
                layout_6_tv_content.setText(R.string.hint_status_type_title_7);
                break;
        }
    }

    private void setStatusSingnValue(int type) {
        switch (type) {
            case Constant.STATUS_TYPE_1:
                layout_7_tv_content.setText(R.string.hint_status_singn_title_1);
                break;
            case Constant.STATUS_TYPE_2:
                layout_7_tv_content.setText(R.string.hint_status_singn_title_2);
                break;
            case Constant.STATUS_TYPE_3:
                layout_7_tv_content.setText(R.string.hint_status_singn_title_3);
                break;
            case Constant.STATUS_TYPE_4:
                layout_7_tv_content.setText(R.string.hint_status_singn_title_4);
                break;
        }
    }

    private void setStatusRegistValue(int type) {
        switch (type) {
            case Constant.STATUS_TYPE_1:
                layout_8_tv_content.setText(R.string.hint_status_regist_title_1);
                break;
            case Constant.STATUS_TYPE_2:
                layout_8_tv_content.setText(R.string.hint_status_regist_title_2);
                break;
            case Constant.STATUS_TYPE_3:
                layout_8_tv_content.setText(R.string.hint_status_regist_title_3);
                break;
            case Constant.STATUS_TYPE_4:
                layout_8_tv_content.setText(R.string.hint_status_regist_title_4);
                break;
        }
    }
}
