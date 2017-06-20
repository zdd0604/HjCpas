package com.hj.casps.adapter.overdealadapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleEntity;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 */

public class CreateSectionAdapter extends CommonAdapter<QueryMyPendingSttleEntity> {
    public static OnClickSectionListener onClickSectionListener;

    public static void setOnClickSectionListener(OnClickSectionListener onClickSectionListener) {
        CreateSectionAdapter.onClickSectionListener = onClickSectionListener;
    }

    public CreateSectionAdapter(Context context, List<QueryMyPendingSttleEntity> datas) {
        super(context, datas, R.layout.section_item);
        this.mContext = context;
    }

    @Override
    public void concert(final ViewHolder hooder, final QueryMyPendingSttleEntity queryMyPendingSttleEntity, final int indexPos) {
        String name;
        //数据属于五行可将其隐藏
        hooder.getView(R.id.layout_section_5).setVisibility(View.GONE);
        hooder.getView(R.id.relative_layout_1).setVisibility(View.GONE);
        hooder.setTextInt(R.id.tv_section_bills_id_title, R.string.hint_tv_section_bills_id_title);
        hooder.setText(R.id.tv_section_bills_id_content, queryMyPendingSttleEntity.getSettleCode() + "");
        hooder.setTextInt(R.id.layout_1_tv_title, R.string.hint_tv_section_bills_opposite_title);
        //（页面上处理：会员id等于 mmbpayId 结款对方显示mmbgetName，否则显示mmbpayName）
        name = Constant.publicArg.getSys_member().equals(queryMyPendingSttleEntity.getMmbpayId()) ?
                queryMyPendingSttleEntity.getMmbgetName() : queryMyPendingSttleEntity.getMmbpayName();
        hooder.setText(R.id.layout_1_tv_content, name);
        hooder.setTextInt(R.id.layout_2_tv_title, R.string.hint_tv_section_bills_my_propose_time_title);
        hooder.setText(R.id.layout_2_tv_content, Constant.stmpToTime(queryMyPendingSttleEntity.getCtrTime()));
        hooder.setTextInt(R.id.layout_3_tv_title, R.string.hint_tv_section_bills_bills_money_title);
        hooder.setText(R.id.layout_3_tv_content, queryMyPendingSttleEntity.getSettleMoney() + "");
        hooder.setTextInt(R.id.layout_4_tv_title, R.string.hint_tv_section_bills_my_propose_moey_title);
        hooder.setText(R.id.layout_4_tv_content, queryMyPendingSttleEntity.getCtrMoney() + "");

        //订单号
        TextView tv_section_bills_id_content = hooder.getView(R.id.tv_section_bills_id_content);
        tv_section_bills_id_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSectionListener != null) {
                    onClickSectionListener.onClickBillsId(indexPos);
                }
            }
        });

        //请求终止按钮
        TextView tv_request_stop_title = hooder.getView(R.id.tv_request_stop_title);
        tv_request_stop_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSectionListener != null) {

                }
            }
        });

        TextView tv_commit_apply_title = hooder.getView(R.id.tv_commit_apply_title);
        tv_commit_apply_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSectionListener != null) {

                }
            }
        });

    }

    public interface OnClickSectionListener {
        void onClickBillsId(int pos);
    }
}
