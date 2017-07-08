package com.hj.casps.adapter.ordermanageradapter;

import android.view.View;
import android.webkit.ServiceWorkerClient;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.TypeAbstractViewHolder;
import com.hj.casps.entity.appordermoney.OverBillsDtailsEntity;

/**
 * Created by Admin on 2017/4/19.
 * 订单详情
 */

public class ViewHolderOrderlOne extends TypeAbstractViewHolder<String> implements View.OnClickListener {
    private EditText order_detail_time_pay;
    private TextView order_detail_process;
    private EditText order_detail_time_start;
    private EditText order_detail_time_end;
    private TextView order_detail_pay_account;
    private TextView order_detail_pay_address;
    private TextView order_detail_get_account;
    private TextView order_detail_get_address;
    public static onClickOrderListener onClickOrderListener;

    public static void setOnClickOrderListener(ViewHolderOrderlOne.onClickOrderListener onClickOrderListener) {
        ViewHolderOrderlOne.onClickOrderListener = onClickOrderListener;
    }

    public ViewHolderOrderlOne(View itemView) {
        super(itemView);
        order_detail_time_pay = (EditText) itemView.findViewById(R.id.order_detail_time_pay);
        order_detail_time_pay.setOnClickListener(this);
        order_detail_process = (TextView) itemView.findViewById(R.id.order_detail_process);
        order_detail_process.setOnClickListener(this);
        order_detail_time_start = (EditText) itemView.findViewById(R.id.order_detail_time_start);
        order_detail_time_start.setOnClickListener(this);
        order_detail_time_end = (EditText) itemView.findViewById(R.id.order_detail_time_end);
        order_detail_time_end.setOnClickListener(this);
        order_detail_pay_account = (TextView) itemView.findViewById(R.id.order_detail_pay_account);
        order_detail_pay_address = (TextView) itemView.findViewById(R.id.order_detail_pay_address);
        order_detail_get_account = (TextView) itemView.findViewById(R.id.order_detail_get_account);
        order_detail_get_address = (TextView) itemView.findViewById(R.id.order_detail_get_address);
        order_detail_pay_account.setOnClickListener(this);
        order_detail_pay_address.setOnClickListener(this);
        order_detail_get_account.setOnClickListener(this);
        order_detail_get_address.setOnClickListener(this);
        if (onClickOrderListener != null)
            onClickOrderListener.setViewId(itemView.findViewById(R.id.order_detail_time_pay),
                    itemView.findViewById(R.id.order_detail_time_start),
                    itemView.findViewById(R.id.order_detail_time_end),
                    itemView.findViewById(R.id.order_detail_process));
    }

    @Override
    public void bindViewHolder(String cargoMessage, int postion) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.order_detail_time_pay:
                onClickOrderListener.onTimePay(order_detail_time_pay);
                break;
            case R.id.order_detail_time_start:
                onClickOrderListener.onTimeStart(order_detail_time_start);
                break;
            case R.id.order_detail_time_end:
                onClickOrderListener.onTimeEnd(order_detail_time_end);
                break;
            case R.id.order_detail_process:
                onClickOrderListener.onProgress(order_detail_process);
                break;
            case R.id.order_detail_pay_account:
                onClickOrderListener.onPayaccount(order_detail_pay_account);
                break;
            case R.id.order_detail_pay_address:
                onClickOrderListener.onPayaddress(order_detail_pay_address);
                break;
            case R.id.order_detail_get_account:
                onClickOrderListener.onGetaccount(order_detail_get_account);
                break;
            case R.id.order_detail_get_address:
                onClickOrderListener.onGetaddress(order_detail_get_address);
                break;

        }

    }


    public interface onClickOrderListener {
        void onTimePay(EditText textView);

        void onTimeStart(EditText textView);

        void onTimeEnd(EditText editText);

        void onProgress(TextView editText);

        void onPayaccount(TextView editText);

        void onPayaddress(TextView editText);

        void onGetaccount(TextView editText);

        void onGetaddress(TextView editText);

        void setViewId(View order_detail_time_pay, View order_detail_time_start,
                       View order_detail_time_end, View order_detail_process);
    }
}
