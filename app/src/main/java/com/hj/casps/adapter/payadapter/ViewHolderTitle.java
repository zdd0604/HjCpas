package com.hj.casps.adapter.payadapter;

import android.view.View;
import android.widget.TextView;

import com.hj.casps.R;

/**
 * Created by Admin on 2017/4/19.
 * 订单详情
 */

public class ViewHolderTitle extends TypeAbstractViewHolder<String> {
    private TextView tl_shopping_title;


    public ViewHolderTitle(View itemView) {
        super(itemView);

        tl_shopping_title = (TextView) itemView.findViewById(R.id.tl_shopping_title);
    }

    @Override
    public void bindViewHolder(String cargoMessage, final int postion) {
        tl_shopping_title.setText(cargoMessage);
    }

}
