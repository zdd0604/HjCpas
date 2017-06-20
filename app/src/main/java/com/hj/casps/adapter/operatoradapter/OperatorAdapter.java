package com.hj.casps.adapter.operatoradapter;

import android.content.Context;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.appUser.QueryUserEntity;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 * 操作员管理adapter
 */

public class OperatorAdapter extends CommonAdapter<QueryUserEntity> {

    public OperatorAdapter(Context context, List<QueryUserEntity> datas) {
        super(context, datas, R.layout.operator_item);
        this.mContext = context;
    }

    @Override
    public void concert(ViewHolder hooder, final QueryUserEntity queryUserEntity, final int indexPos) {
        hooder.setText(R.id.operator_tv_tlile, queryUserEntity.getName());
        hooder.setText(R.id.operator_account_tlile, queryUserEntity.getAccount());
    }
}
