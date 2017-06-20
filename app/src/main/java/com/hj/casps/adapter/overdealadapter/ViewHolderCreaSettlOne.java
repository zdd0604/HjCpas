package com.hj.casps.adapter.overdealadapter;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.TypeAbstractViewHolder;
import com.hj.casps.entity.appordermoney.OverBillsDtailsEntity;

/**
 * Created by Admin on 2017/4/19.
 * 订单详情
 */

public class ViewHolderCreaSettlOne extends TypeAbstractViewHolder<OverBillsDtailsEntity> implements View.OnClickListener{
    private TextView vholder_buyersName_tv,
            vholder_sellersName_tv,
            vholder_buyersId_tv,
            vholder_sellersId_tv;
    EditText vholder_buyerTime_tv;

   public static onClickBankNameListener onClickBankNameListener;

    public static void setOnClickBankNameListener(ViewHolderCreaSettlOne.onClickBankNameListener onClickBankNameListener) {
        ViewHolderCreaSettlOne.onClickBankNameListener = onClickBankNameListener;
    }

    public ViewHolderCreaSettlOne(View itemView) {
        super(itemView);

        vholder_buyersName_tv = (TextView) itemView.findViewById(R.id.vholder_buyersName_tv);
        vholder_sellersName_tv = (TextView) itemView.findViewById(R.id.vholder_sellersName_tv);
        vholder_buyersId_tv = (TextView) itemView.findViewById(R.id.vholder_buyersId_tv);
        vholder_sellersId_tv = (TextView) itemView.findViewById(R.id.vholder_sellersId_tv);
        vholder_buyerTime_tv = (EditText) itemView.findViewById(R.id.vholder_buyerTime_tv);
        vholder_buyersId_tv.setOnClickListener(this);
        vholder_sellersId_tv.setOnClickListener(this);
        vholder_buyerTime_tv.setOnClickListener(this);
    }

    @Override
    public void bindViewHolder(OverBillsDtailsEntity oEntity, final int postion) {
        vholder_buyersName_tv.setText(oEntity.getBuyersName());
        vholder_sellersName_tv.setText(oEntity.getSellersName());
        vholder_buyersId_tv.setText(oEntity.getBuyersId());
        vholder_sellersId_tv.setText(oEntity.getSellersId());
        vholder_buyerTime_tv.setText(oEntity.getCtrTime());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vholder_buyersId_tv:
                onClickBankNameListener.onBuyersName(vholder_buyersId_tv);
                break;
            case R.id.vholder_sellersId_tv:
                onClickBankNameListener.onSellersName(vholder_sellersId_tv);
                break;
            case R.id.vholder_buyerTime_tv:
                onClickBankNameListener.onSTime(vholder_buyerTime_tv);
                break;
        }
    }


    public interface onClickBankNameListener {
        void onBuyersName(TextView textView);
        void onSellersName(TextView textView);
        void onSTime(EditText editText);
    }
}
