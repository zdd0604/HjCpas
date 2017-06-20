package com.hj.casps.adapter.operatoradapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.TypeAbstractViewHolder;

/**
 * Created by Admin on 2017/4/19.
 * 订单详情
 */

public class ViewHolderRelation extends TypeAbstractViewHolder<String> {
    private CheckBox relation_ck;
    private TextView relation_content;

    public ViewHolderRelation(View itemView) {
        super(itemView);
        relation_ck = (CheckBox) itemView.findViewById(R.id.relation_ck);
        relation_content = (TextView) itemView.findViewById(R.id.relation_content);
    }

    @Override
    public void bindViewHolder(String cargoMessage, final int postion) {
        relation_content.setText(cargoMessage);
    }

}
