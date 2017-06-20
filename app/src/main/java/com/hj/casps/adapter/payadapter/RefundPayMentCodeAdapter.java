package com.hj.casps.adapter.payadapter;

import android.content.Context;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.entity.paymentmanager.response.ResRefundMoneyOfflineEntity;
import com.hj.casps.entity.paymentmanager.response.ResponseQueryPayEntity;

import java.util.List;

//在付款界面弹出的popup的条目的adapter
public class RefundPayMentCodeAdapter extends CommonAdapter<ResRefundMoneyOfflineEntity.AccountlistBean> {


    public RefundPayMentCodeAdapter(Context context, List<ResRefundMoneyOfflineEntity.AccountlistBean> datas) {
        super(context, datas, R.layout.layout_item_popup);
    }

    @Override
    public void concert(ViewHolder hooder, ResRefundMoneyOfflineEntity.AccountlistBean accountlistBeen, int indexPos) {
        TextView textView = hooder.getView(R.id.layout_item_tv);
        textView.setText(accountlistBeen.getBankname()+accountlistBeen.getAccountno());
    }
}


