package com.hj.casps.protocolmanager;

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

//搜索协议页面
public class ProtocolSearch extends ActivityBaseHeader2 implements View.OnClickListener {

    private Spinner protocol_type;
    private EditText protocol_object_text;
    private Spinner protocol_state;
    private LinearLayout protocol_state_ll;
    private FancyButton protocol_search;
    private int type;
    private LinearLayout type_pro;
    private String[] statusItems;
    private String[] typeItems;
    private TestArrayAdapter stringArrayAdapter;
    private TestArrayAdapter stringArrayAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_search);
        initData();
        initView();
    }

    private void initData() {
        type = getIntent().getIntExtra(Constant.PROTOCOL_TYPE, Constant.protocol_0);
        statusItems = new String[]{"执行中", "已终止"};
        typeItems = new String[]{"采购协议", "销售协议"};

    }

    private void initView() {
        setTitle(getString(R.string.search));
        protocol_type = (Spinner) findViewById(R.id.protocol_type);
        protocol_object_text = (EditText) findViewById(R.id.protocol_object_text);
        protocol_state = (Spinner) findViewById(R.id.protocol_state);
        protocol_state_ll = (LinearLayout) findViewById(R.id.protocol_state_ll);
        protocol_search = (FancyButton) findViewById(R.id.protocol_search);
        stringArrayAdapter = new TestArrayAdapter(getApplicationContext(), statusItems);
        stringArrayAdapter2 = new TestArrayAdapter(getApplicationContext(), typeItems);
        protocol_state.setAdapter(stringArrayAdapter);
        protocol_type.setAdapter(stringArrayAdapter2);
        protocol_search.setOnClickListener(this);
        if (type != 2) {
            protocol_state_ll.setVisibility(View.GONE);
        }
//        type_pro = (LinearLayout) findViewById(R.id.type_pro);
//        type_pro.setVisibility(View.GONE);
//        type_pro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.protocol_search:
                submit();
                break;
        }
    }

    //搜索后的提交
    private void submit() {
        // validate
        String text = protocol_object_text.getText().toString().trim();
        // TODO validate success, do something
//        ProtocolFragment.protocolFragment.refresh(text, protocol_state.getSelectedItemPosition() == 0 ? "3" : "7");
        Constant.PROTOCOL_TITLE = text;
        Constant.PROTOCOL_STATUS = protocol_state.getSelectedItemPosition() == 0 ? "3" : "7";
        Constant.PROTOCOL_TYPE_NUM = protocol_type.getSelectedItemPosition();
//        ProtocolFragment.protocolFragment.refresh();
        setResult(22);
        finish();

    }
}
