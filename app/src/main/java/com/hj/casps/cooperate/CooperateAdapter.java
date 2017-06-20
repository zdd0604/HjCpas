package com.hj.casps.cooperate;

import android.content.Context;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;

import java.util.List;

/**
 * Created by zy on 2017/4/21.
 */
//关注会员目录的适配器
public class CooperateAdapter extends WZYBaseAdapter<CooperateModel> {
    public CooperateAdapter(List<CooperateModel> data, Context context, int layoutRes) {
        super(data, context, layoutRes);
    }

    @Override
    public void bindData(ViewHolder holder, CooperateModel cooperateModel, final int indexPos) {
        TextView name = (TextView) holder.getView(R.id.cooperate_name);
        name.setText(cooperateModel.getFname());
    }
}
