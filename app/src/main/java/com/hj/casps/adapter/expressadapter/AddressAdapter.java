package com.hj.casps.adapter.expressadapter;

import android.content.Context;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.appordergoods.QueryGetGoodsEntity;

import java.util.List;

public class AddressAdapter extends CommonAdapter<QueryGetGoodsEntity.AddressListBean> {

    public AddressAdapter(Context context, List<QueryGetGoodsEntity.AddressListBean> datas) {
        super(context, datas, R.layout.layout_item_popup);
        this.mContext = context;
    }

    @Override
    public void concert(ViewHolder hooder, QueryGetGoodsEntity.AddressListBean addressListBean, int indexPos) {
        TextView textView = hooder.getView(R.id.layout_item_tv);
        textView.setText(addressListBean.getAddress());
    }
}
