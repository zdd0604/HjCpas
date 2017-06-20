package com.hj.casps.adapter.overdealadapter;

import android.content.Context;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.appsettle.QueryOppositeListEntity;

import java.util.List;

/**
 * Created by Admin on 2017/5/15.
 */

public class QueryOppositeAdapter extends CommonAdapter<QueryOppositeListEntity> {

    public QueryOppositeAdapter(Context context, List<QueryOppositeListEntity> datas) {
        super(context, datas, R.layout.layout_item_popup);
        this.mContext = context;
    }

    @Override
    public void concert(ViewHolder hooder, QueryOppositeListEntity queryOppositeListEntity, int indexPos) {
        TextView textView = hooder.getView(R.id.layout_item_tv);
        textView.setText(queryOppositeListEntity.getSellersName());
    }
}
