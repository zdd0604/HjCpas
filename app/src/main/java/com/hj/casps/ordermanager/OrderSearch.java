package com.hj.casps.ordermanager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * 订单搜索的类
 *
 */
public class OrderSearch extends ActivityBaseHeader2 implements View.OnClickListener {

    private EditText order_buy_id;
    private Spinner order_buy_type;
    private Spinner order_buy_state;
    private EditText order_doing_object_text;
    private EditText order_time_from;
    private EditText order_time_to;
    private FancyButton order_search;
    private int type;
    private LinearLayout order_state_layout;
    private LinearLayout order_time_layout;
    private String[] statusItems;
    private String[] typeItems;
    private TestArrayAdapter stringArrayAdapter;
    private TestArrayAdapter stringArrayAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search);
        initData();
        initView();
    }

    //加载可搜索数据
    private void initData() {
        type = getIntent().getIntExtra(Constant.PROTOCOL_TYPE, Constant.protocol_0);
        statusItems = new String[]{"全部", "执行中", "已完成"};
        typeItems = new String[]{"全部", "采购", "销售"};
    }

    //加载搜索界面
    private void initView() {
        setTitle("查询订单");
        order_buy_id = (EditText) findViewById(R.id.order_buy_id);
        order_buy_type = (Spinner) findViewById(R.id.order_buy_type);
        order_buy_state = (Spinner) findViewById(R.id.order_buy_state);
        order_doing_object_text = (EditText) findViewById(R.id.order_doing_object_text);
        order_time_from = (EditText) findViewById(R.id.order_time_from);
        order_time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar(order_time_from);
            }
        });
        order_time_to = (EditText) findViewById(R.id.order_time_to);
        order_time_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar(order_time_to);

            }
        });
        order_search = (FancyButton) findViewById(R.id.order_search);

        order_search.setOnClickListener(this);
        order_state_layout = (LinearLayout) findViewById(R.id.order_state_layout);
        order_time_layout = (LinearLayout) findViewById(R.id.order_time_layout);
        stringArrayAdapter = new TestArrayAdapter(getApplicationContext(),  statusItems);
        stringArrayAdapter2 = new TestArrayAdapter(getApplicationContext(),  typeItems);
        order_buy_state.setAdapter(stringArrayAdapter);
        order_buy_type.setAdapter(stringArrayAdapter2);
        if (type != 2) {
            order_state_layout.setVisibility(View.GONE);
            order_time_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_search:
                submit();
                break;
        }
    }

    //提交搜索结果
    private void submit() {
        // validate
        String id = order_buy_id.getText().toString().trim();

        String text = order_doing_object_text.getText().toString().trim();

        String from = order_time_from.getText().toString().trim();

        String to = order_time_to.getText().toString().trim();

        // TODO validate success, do something

        Constant.ORDER_NAME = text;
        Constant.ORDER_ORDER_ID = id;
        Constant.START_TIME = from;
        Constant.END_TIME = to;
        Constant.ORDER_STATUS = String.valueOf(order_buy_state.getSelectedItemPosition());
        Constant.PROTOCOL_TYPE_NUM = order_buy_type.getSelectedItemPosition();
//        ProtocolFragment.protocolFragment.refresh();
        setResult(22);
        finish();

    }
}
