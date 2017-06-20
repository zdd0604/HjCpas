package com.hj.casps.reception;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.hj.casps.R.id.editGoods_name_Et;


/**
 * Created by YaoChen on 2017/4/18.
 */

public class ActivityPriceSearchPage extends ActivityBaseHeader2 implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    @BindView(editGoods_name_Et)
    EditText editGoodsNameEt;
    @BindView(R.id.cb_0)
    CheckBox cb0;
    @BindView(R.id.cb_1)
    CheckBox cb1;
    @BindView(R.id.cb_2)
    CheckBox cb2;
    @BindView(R.id.cb_3)
    CheckBox cb3;
    @BindView(R.id.search_price_Btn)
    FancyButton searchPriceBtn;
    private ListView listView;
    private List<Map<String, Object>> mdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_price);
        ButterKnife.bind(this);
        setTitleRight(null, null);
        setTitle(getString(R.string.search));
        getIntent().getStringExtra(Constant.INTENT_GOODSID);
        initView();

    }

    private void initView() {

        searchPriceBtn.setOnClickListener(this);
        //本省公开报价
        if (Constant.SEARCH_Price_quote_checkboxId.equals("3,2,1")) {
            cb0.setChecked(false);
        } else {
            cb0.setChecked(true);
        }
        cb1.setChecked(true);
        cb2.setChecked(true);
        cb3.setChecked(true);
        cb0.setOnCheckedChangeListener(this);
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_0:
                break;
            case R.id.cb_1:
                break;
            case R.id.cb_2:
                break;
            case R.id.cb_3:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击查询
            case R.id.search_price_Btn:
                Constant.SEARCH_Price_quote_goodName = getEdVaule(editGoodsNameEt);
                if (cb0.isChecked()) {
                    Constant.SEARCH_Price_quote_checkboxId = "3,2,1,0";
                }else{
                    Constant.SEARCH_Price_quote_checkboxId = "3,2,1";
                }
                setResult(RESULT_OK);
                this.finish();
                break;
        }

    }
}

