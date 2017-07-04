package com.hj.casps.ordermanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hj.casps.R;
import com.hj.casps.adapter.ordermanageradapter.OrderDetailsAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2017/7/4.
 */

public class OrderDetailInfo extends ActivityBaseHeader2 implements View.OnClickListener {
    @BindView(R.id.order_recycler)
    RecyclerView order_recycler;

    OrderDetailsAdapter OrderDetailsAdapter;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    //请求数据
                    break;
                case Constant.HANDLERTYPE_1:
                    //刷新数据
                    break;
                case Constant.HANDLERTYPE_2:
                    //请求数据
                    break;
                case Constant.HANDLERTYPE_3:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_info);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

    }

    @Override
    public void onClick(View v) {

    }
}
