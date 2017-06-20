package com.hj.casps.adapter.overdealadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appsettle.QuerySttleRegistGain;

import java.util.List;

/**
 * Created by Admin on 2017/5/2.
 * 执行中结款单列表详情
 */

public class RegisterAssuerAdapter extends CommonAdapter<QuerySttleRegistGain> {

    public static onClickBillsListener onClickBillsListener;
    private String name;
    private String StatusRegistName;

    public static void setOnClickBillsListener(RegisterAssuerAdapter.onClickBillsListener onClickBillsListener) {
        RegisterAssuerAdapter.onClickBillsListener = onClickBillsListener;
    }

    public RegisterAssuerAdapter(Context context, List<QuerySttleRegistGain> datas) {
        super(context, datas, R.layout.section_item);
        this.mContext = context;
    }

    @Override
    public void concert(ViewHolder hooder, final QuerySttleRegistGain assureGain, final int indexPos) {
        hooder.setText(R.id.tv_section_bills_id_content, assureGain.getSettleCode());
        hooder.setTextInt(R.id.layout_1_tv_title, R.string.hint_status_regist_assure_title_1);
        //（mmbpayId等于当前会员id，显示mmbpayName否则显示mmbgetName）
//        name = Constant.publicArg.getSys_member().equals(assureGain.getMmbgpayId()) ?
//                assureGain.getMmbpayName() : assureGain.getMmbgetName();
        name = Constant.publicArg.getSys_member().equals(assureGain.getMmbgpayId()) ?
                assureGain.getMmbgetName() : assureGain.getMmbpayName();
        hooder.setText(R.id.layout_1_tv_content, name);
        hooder.setTextInt(R.id.layout_2_tv_title, R.string.hint_status_regist_assure_title_2);
        hooder.setText(R.id.layout_2_tv_content, Constant.stmpToDate(assureGain.getCtrTime()));
        hooder.setTextInt(R.id.layout_3_tv_title, R.string.hint_status_regist_assure_title_3);
        hooder.setText(R.id.layout_3_tv_content, assureGain.getSettleMoney() + "");
        hooder.setTextInt(R.id.layout_4_tv_title, R.string.hint_status_regist_assure_title_4);
        hooder.setText(R.id.layout_4_tv_content, assureGain.getCtrMoney() + "");
        hooder.setTextInt(R.id.layout_5_tv_title, R.string.hint_status_regist_assure_title_5);
        hooder.setText(R.id.layout_5_tv_content, assureGain.getGotMoney() + "");


        StatusRegistName = Integer.valueOf(assureGain.getStatusRegist()) == 1
                ? "申请登记" : "提交申请待审";

        if (StatusRegistName.equals("申请登记")) {
            hooder.getView(R.id.relative_layout_1).setVisibility(View.VISIBLE);
            hooder.getView(R.id.tv_commit_apply_title).setVisibility(View.GONE);
            TextView tv_request_stop_title = hooder.getView(R.id.tv_request_stop_title);
            tv_request_stop_title.setBackgroundResource(R.drawable.btn_style);
            tv_request_stop_title.setText(StatusRegistName);
            tv_request_stop_title.setTextColor(Color.WHITE);
            tv_request_stop_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBillsListener.onBtnClickListener(indexPos);
                }
            });
        }

        if (StatusRegistName.equals("提交申请待审")) {
            hooder.getView(R.id.relative_layout_1).setVisibility(View.GONE);
            hooder.getView(R.id.tv_commit_apply_title).setVisibility(View.VISIBLE);
        }


        hooder.getView(R.id.tv_section_bills_id_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBillsListener.onBillsIDItemCilckListener(indexPos);
            }
        });

    }

    public interface onClickBillsListener {
        void onBillsIDItemCilckListener(int pos);

        void onBtnClickListener(int pos);
    }

}