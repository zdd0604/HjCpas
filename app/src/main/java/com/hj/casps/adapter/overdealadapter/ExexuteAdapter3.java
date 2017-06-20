package com.hj.casps.adapter.overdealadapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appsettle.QuerySttleManageGain;

import java.util.List;

/**
 * Created by Admin on 2017/5/2.
 * 执行中结款单列表详情
 */

public class ExexuteAdapter3 extends CommonAdapter<QuerySttleManageGain> {
    private String name;
    private String content;
    public static onClickBillsListener onClickBillsListener;

    public static void setOnClickBillsListener(onClickBillsListener onClickBillsListener) {
        ExexuteAdapter3.onClickBillsListener = onClickBillsListener;
    }

    public ExexuteAdapter3(Context context, List<QuerySttleManageGain> datas) {
        super(context, datas, R.layout.section_item);
        this.mContext = context;
    }

    @Override
    public void concert(ViewHolder hooder, final QuerySttleManageGain executeGain, final int indexPos) {
        /**
         * ctrMoney : 3600
         * ctrTime : 1475164800000
         * id : cda0e2807ba746cd90682a9746f94f0d
         * mmbgetName : 长城商行
         * mmbpayName : 奥森学校
         * settleCode : 40022003
         * settleMoney : 3600
         * status : 4
         */
        hooder.setTextInt(R.id.layout_1_tv_title, R.string.hint_status_regist_assure_title_1);
        hooder.setTextInt(R.id.layout_2_tv_title, R.string.hint_status_regist_assure_title_2);
        hooder.setTextInt(R.id.layout_3_tv_title, R.string.hint_status_regist_assure_title_3);
        hooder.setTextInt(R.id.layout_4_tv_title, R.string.hint_status_regist_assure_title_4);
        hooder.setTextInt(R.id.layout_5_tv_title, R.string.hint_status_regist_assure_title_5);
        hooder.setText(R.id.tv_section_bills_id_content, executeGain.getSettleCode() + "");
        name = executeGain.getMmbpayId().equals(Constant.publicArg.getSys_member()) ?
                executeGain.getMmbpayName() : executeGain.getMmbgetName();
        hooder.setText(R.id.layout_1_tv_content, name);
        hooder.setText(R.id.layout_2_tv_content, Constant.stmpToDate(executeGain.getCtrTime()));
        hooder.setText(R.id.layout_3_tv_content, executeGain.getSettleMoney() + "");
        hooder.setText(R.id.layout_4_tv_content, executeGain.getCtrMoney() + "");
        hooder.setText(R.id.layout_5_tv_content, executeGain.getSettleMoney() + "");

        hooder.getView(R.id.relative_layout_1).setVisibility(View.VISIBLE);
        TextView tv_request_stop_title = hooder.getView(R.id.tv_request_stop_title);

        tv_request_stop_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBillsListener.onBtnClickListener(indexPos);
            }
        });
        hooder.getView(R.id.tv_section_bills_id_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBillsListener.onBillsIDItemCilckListener(indexPos);
            }
        });


        //status	string	状态（
        // status=4请求终止、
        // status=5 && memberid=mmbpayid ? 撤回终止请求：同意终止、
        // status=6 && memberid=mmbpayid ? 同意终止：撤回终止请求）
        switch (executeGain.getStatus()) {
            case 4:
                content = "请求终止";
                break;
            case 5:
                content = executeGain.getStatus() == 5 &&
                        Constant.publicArg.getSys_member().equals(executeGain.getMmbpayId()) ?
                        "撤回终止请求" : "同意终止";
                break;
            case 6:
                content = executeGain.getStatus() == 6 &&
                        Constant.publicArg.getSys_member().equals(executeGain.getMmbpayId()) ?
                        "同意终止" : "撤回终止请求";
                break;
        }
        tv_request_stop_title.setText(content);
    }


    public interface onClickBillsListener {
        void onBillsIDItemCilckListener(int pos);

        void onBtnClickListener(int pos);
    }

}