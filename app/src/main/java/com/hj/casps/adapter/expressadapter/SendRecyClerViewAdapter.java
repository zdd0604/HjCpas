package com.hj.casps.adapter.expressadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.entity.appordergoods.QuerySendGoodsEntity;
import com.hj.casps.util.StringUtils;

import java.util.List;

/**
 * Created by Admin on 2017/6/2.
 * 试用
 */

public class SendRecyClerViewAdapter extends RecyclerView.Adapter<SendRecyClerViewAdapter.MyViewHolder>
        implements View.OnClickListener {
    private int pos;
    private List<QuerySendGoodsEntity> datas;
    private QuerySendGoodsEntity querySendGoodsEntity;
    private Context context;
    public static onSendOnCilck onSendOnCilck;

    public static void setOnCSendOnCilck(SendRecyClerViewAdapter.onSendOnCilck onSendOnCilck) {
        SendRecyClerViewAdapter.onSendOnCilck = onSendOnCilck;
    }

    public SendRecyClerViewAdapter(List<QuerySendGoodsEntity> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.harvest_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        pos = position;
        querySendGoodsEntity = datas.get(position);

        holder.harvest_tv_title_2.setText(R.string.hint_tv_deliver_title);
        holder.harvest_tv_title_3.setText(R.string.hint_tv_sum_deliver_title);
        holder.harvest_tv_title_4.setText(R.string.hint_tv_send_already_deliver_title);
        holder.harvest_tv_title_5.setText(R.string.hint_tv_send_await_deliver_title);
        holder.harvest_tv_title_6.setText(R.string.hint_tv_send_new_express_title);
        holder.layout_harvest_3.setVisibility(View.GONE);
        holder.layout_tv_order_3.setOnClickListener(this);
        //赋值
        holder.layout_tv_order_3.setText(querySendGoodsEntity.getOrdertitleNumber() + "");
        holder.harvest_tv_title_1.setText(querySendGoodsEntity.getGoodsName());
        holder.harvest_tv_content_2.setText(querySendGoodsEntity.getBuyers_name());
        holder.harvest_tv_content_3.setText(querySendGoodsEntity.getGoods_num() + "");
        holder.harvest_tv_content_4.setText(querySendGoodsEntity.getSendgoods_num() + "");
        holder.harvest_tv_content_5.setText(querySendGoodsEntity.getExe_sendgoods_num() + "");

        //check复选框点击事件
        holder.layout_check_order_1.setChecked(querySendGoodsEntity.isCheck());
        holder.layout_check_order_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                querySendGoodsEntity.setCheck(holder.layout_check_order_1.isChecked());
                if (onSendOnCilck != null)
                    if (holder.layout_check_order_1.isChecked()) {
                        onSendOnCilck.onCheckedY(position);
                    } else {
                        onSendOnCilck.onCheckedN(position);
                    }
            }
        });

        holder.harvest_ed_content_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    querySendGoodsEntity.setNum("");
                } else if (StringUtils.isStrTrue(holder.harvest_ed_content_6.getText().toString().trim())) {
                    querySendGoodsEntity.setNum(holder.harvest_ed_content_6.getText().toString().trim());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_tv_order_3:
                //单据号点击
                if (onSendOnCilck != null)
                    onSendOnCilck.onBillsIDItemCilckListener(pos);
                break;
        }
    }

    public void refreshList(List<QuerySendGoodsEntity> datas) {
        for (int i=0;i<datas.size();i++){
            this.datas.add(datas.get(i));
        }
        this.notifyDataSetChanged();
    }


    public interface onSendOnCilck {
        void onCheckedY(int position);

        void onCheckedN(int position);

        void onBillsIDItemCilckListener(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox layout_check_order_1;
        TextView layout_tv_order_2;
        TextView layout_tv_order_3;
        TextView harvest_tv_title_1;
        TextView harvest_tv_title_2;
        TextView harvest_tv_content_2;
        TextView harvest_tv_title_3;
        TextView harvest_tv_content_3;
        TextView harvest_tv_title_4;
        TextView harvest_tv_content_4;
        TextView harvest_tv_title_5;
        TextView harvest_tv_content_5;
        TextView harvest_tv_title_6;
        EditText harvest_ed_content_6;
        TextView harvest_tv_title_7;
        TextView harvest_tv_content_7;
        ImageView harvest_img_title_7;
        EditText harvest_ed_content_8;
        LinearLayout layout_harvest_2;
        LinearLayout harvest_left_layout;
        LinearLayout harvest_left_layout_2;
        LinearLayout harvest_left_layout_3;
        LinearLayout harvest_left_layout_4;
        LinearLayout harvest_left_layout_5;
        RelativeLayout layout_harvest_right_2;
        LinearLayout layout_harvest_3;

        public MyViewHolder(View itemView) {
            super(itemView);
            layout_check_order_1 = (CheckBox) itemView.findViewById(R.id.layout_check_order_1);
            layout_tv_order_2 = (TextView) itemView.findViewById(R.id.layout_tv_order_2);
            layout_tv_order_3 = (TextView) itemView.findViewById(R.id.layout_tv_order_3);
            harvest_tv_title_1 = (TextView) itemView.findViewById(R.id.harvest_tv_title_1);
            harvest_tv_title_2 = (TextView) itemView.findViewById(R.id.harvest_tv_title_2);
            harvest_tv_content_2 = (TextView) itemView.findViewById(R.id.harvest_tv_content_2);
            harvest_tv_title_3 = (TextView) itemView.findViewById(R.id.harvest_tv_title_3);
            harvest_tv_content_3 = (TextView) itemView.findViewById(R.id.harvest_tv_content_3);
            harvest_tv_title_4 = (TextView) itemView.findViewById(R.id.harvest_tv_title_4);
            harvest_tv_content_4 = (TextView) itemView.findViewById(R.id.harvest_tv_content_4);
            harvest_tv_title_5 = (TextView) itemView.findViewById(R.id.harvest_tv_title_5);
            harvest_tv_content_5 = (TextView) itemView.findViewById(R.id.harvest_tv_content_5);
            harvest_tv_title_6 = (TextView) itemView.findViewById(R.id.harvest_tv_title_6);
            harvest_ed_content_6 = (EditText) itemView.findViewById(R.id.harvest_ed_content_6);
            harvest_tv_title_7 = (TextView) itemView.findViewById(R.id.harvest_tv_title_7);
            harvest_tv_content_7 = (TextView) itemView.findViewById(R.id.harvest_tv_content_7);
            harvest_img_title_7 = (ImageView) itemView.findViewById(R.id.harvest_img_title_7);
            harvest_ed_content_8 = (EditText) itemView.findViewById(R.id.harvest_ed_content_8);
            layout_harvest_2 = (LinearLayout) itemView.findViewById(R.id.layout_harvest_2);
            harvest_left_layout = (LinearLayout) itemView.findViewById(R.id.harvest_left_layout);
            harvest_left_layout_2 = (LinearLayout) itemView.findViewById(R.id.harvest_left_layout_2);
            harvest_left_layout_3 = (LinearLayout) itemView.findViewById(R.id.harvest_left_layout_3);
            harvest_left_layout_4 = (LinearLayout) itemView.findViewById(R.id.harvest_left_layout_4);
            harvest_left_layout_5 = (LinearLayout) itemView.findViewById(R.id.harvest_left_layout_5);
            layout_harvest_right_2 = (RelativeLayout) itemView.findViewById(R.id.layout_harvest_right_2);
            layout_harvest_3 = (LinearLayout) itemView.findViewById(R.id.layout_harvest_3);
        }
    }
}
