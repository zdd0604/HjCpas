package com.hj.casps.adapter.overdealadapter;

import android.content.Context;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.appsettle.QueryOppositeListEntity;

import java.util.List;

public class SellAdapter extends CommonAdapter<QueryOppositeListEntity> {

        public SellAdapter(Context context, List<QueryOppositeListEntity> datas) {
            super(context, datas, R.layout.layout_item_popup);
            this.mContext = context;
        }

        @Override
        public void concert(ViewHolder hooder, QueryOppositeListEntity queryOppositeListEntity, int indexPos) {
            TextView textView = hooder.getView(R.id.layout_item_tv);
            textView.setText(queryOppositeListEntity.getBuyersName());
        }
    }
