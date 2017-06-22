package com.hj.casps.cooperate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.commodity.SelectClass;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.hj.casps.common.Constant.GOODS_NAME;
import static com.hj.casps.common.Constant.SYS_FUNC;
//群组管理的搜索页面
public class CooperateGroupSearch extends ActivityBaseHeader2 implements View.OnClickListener {

    private EditText cooperate_group_names;
    private FancyButton group_search;
    private int type;
    private int typeQ;
    private TextView name;
    private EditText cooperate_care_names;
    private LinearLayout care_name_layout;
    private LinearLayout province_layout;
    private Spinner cooperate_province_names;
    List<Dict> dicts = new ArrayList<Dict>();
    private LinearLayout names_layout;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == 22) {
            cooperate_group_names.setText(GOODS_NAME);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate_group_search);
        initData();
        initView();
    }

    private void initData() {
        type = getIntent().getIntExtra("type", 3);
        typeQ = getIntent().getIntExtra("typeQ", 0);

    }

    //群组管理搜索布局
    private void initView() {

        cooperate_group_names = (EditText) findViewById(R.id.cooperate_group_names);
        group_search = (FancyButton) findViewById(R.id.group_search);
        group_search.setOnClickListener(this);
        name = (TextView) findViewById(R.id.name);

        cooperate_care_names = (EditText) findViewById(R.id.cooperate_care_names);
        cooperate_care_names.setOnClickListener(this);
        care_name_layout = (LinearLayout) findViewById(R.id.care_name_layout);
        province_layout = (LinearLayout) findViewById(R.id.province_layout);
        cooperate_province_names = (Spinner) findViewById(R.id.cooperate_province_names);
        dicts.add(new Dict(110002, "北京市"));
        dicts.add(new Dict(120000, "天津市"));
        dicts.add(new Dict(130000, "河北省"));
        dicts.add(new Dict(140000, "山西省"));
        dicts.add(new Dict(150000, "内蒙古自治区"));
        dicts.add(new Dict(210000, "辽宁省"));
        dicts.add(new Dict(220000, "吉林省"));
        dicts.add(new Dict(230000, "黑龙江省"));
        dicts.add(new Dict(310000, "上海市"));
        dicts.add(new Dict(320000, "江苏省"));
        dicts.add(new Dict(330000, "浙江省"));
        dicts.add(new Dict(340000, "安徽省"));
        dicts.add(new Dict(350000, "福建省"));
        dicts.add(new Dict(360000, "江西省"));
        dicts.add(new Dict(410000, "河南省"));
        dicts.add(new Dict(420000, "湖北省"));
        dicts.add(new Dict(430000, "湖南省"));
        dicts.add(new Dict(440000, "广东省"));
        dicts.add(new Dict(450000, "广西壮族自治区"));
        dicts.add(new Dict(460000, "海南省"));
        dicts.add(new Dict(500000, "重庆市"));
        dicts.add(new Dict(510000, "四川省"));
        dicts.add(new Dict(520000, "贵州省"));
        dicts.add(new Dict(530000, "云南省"));
        dicts.add(new Dict(540000, "西藏自治区"));
        dicts.add(new Dict(610000, "陕西省"));
        dicts.add(new Dict(620000, "甘肃省"));
        dicts.add(new Dict(630000, "宁夏回族自治区"));
        dicts.add(new Dict(640000, "青海省"));
        dicts.add(new Dict(650000, "新疆维吾尔自治区"));
        ArrayAdapter<Dict> adapter = new ArrayAdapter<Dict>(this,
                android.R.layout.simple_spinner_item, dicts);
        cooperate_province_names.setAdapter(adapter);

        names_layout = (LinearLayout) findViewById(R.id.names_layout);
        switch (type) {
            case 0://报价管理搜索会员
                setTitle(getString(R.string.search_quote_mmb));
                name.setText(getString(R.string.cooperate_name_text));
                break;
            case 1://报价管理搜索群组
                setTitle(getString(R.string.search_quote_group));
                name.setText(getString(R.string.cooperate_group_name_text));
                break;
            case 3://关系管理群组管理的搜索
                setTitle(getString(R.string.cooperate_group));
                name.setText(getString(R.string.cooperate_group_names));
                break;
            case 5://订单管理商品分类选择的搜索
                setTitle(getString(R.string.search));
                name.setText(getString(R.string.order_choose));
                cooperate_group_names.setText("请选择商品分类");
                cooperate_group_names.setFocusable(false);
                cooperate_group_names.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectClass.class);
                        intent.putExtra("fra", 1);
                        startActivityForResult(intent, 11);
                    }
                });
                care_name_layout.setVisibility(View.VISIBLE);
                province_layout.setVisibility(View.VISIBLE);
                break;
            case 4://关注供应商的搜索
                setTitle(getString(R.string.search));
                names_layout.setVisibility(View.GONE);
                care_name_layout.setVisibility(View.VISIBLE);
                province_layout.setVisibility(View.VISIBLE);
                break;
            case 2://谁关注我的搜索
                setTitle(getString(R.string.search));
                name.setText(getString(R.string.quotes_search_name));
                group_search.setText(getString(R.string.True));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_search:
                submit();
                break;
        }
    }

    //搜索后的提交
    private void submit() {
        // validate
        String names = cooperate_group_names.getText().toString().trim();
        String cares = cooperate_care_names.getText().toString().trim();
        String province = String.valueOf(cooperate_province_names.getSelectedItemPosition() + 1);
//        String province = String.valueOf(((Dict) cooperate_province_names.getSelectedItem()).getId());//因为这么传ID不行，所以去掉，等以后接口可以了再释放

        // TODO validate success, do something
        Bundle bundle = new Bundle();
        switch (type) {
            case 0:

                bundle.putString("searchjson", "{\"sys_func\":\"" + SYS_FUNC + "\",\"sys_member\":\"" + publicArg.getSys_member() + "\",\"mmbId\":\"" + publicArg.getSys_member() + "\",\"sys_name\":\"" + publicArg.getSys_name() + "\",\"sys_token\":\"" + publicArg.getSys_token() + "\",\"sys_user\":\"" + publicArg.getSys_user() + "\",\"sys_uuid\":\"\",\"mmbSname\":\"" + names + "\"," +
                        "\"pageno\":\"1\"," + "\"pagesize\":\"20\"," +
                        "\"type\":\"" + String.valueOf(typeQ) + "\"}");

                break;
            case 1:
                bundle.putString("searchjson", "{\"sys_func\":\"" + SYS_FUNC + "\",\"sys_member\":\"" + publicArg.getSys_member() + "\",\"mmbId\":\"" + publicArg.getSys_member() + "\",\"sys_name\":\"" + publicArg.getSys_name() + "\",\"sys_token\":\"" + publicArg.getSys_token() + "\",\"sys_user\":\"" + publicArg.getSys_user() + "\",\"sys_uuid\":\"\",\"groupName\":\"" + names + "\"," +
                        "\"pageno\":\"1\"," + "\"pagesize\":\"20\"," +
                        "\"type\":\"" + String.valueOf(typeQ) + "\"}");
                break;
            case 2:
            case 3:
                bundle.putString("groupName", names);
                break;
            case 4:
            case 5:
                bundle.putString("cares", cares);
                bundle.putString("province", province);
                break;
        }
        setResult(22, getIntent().putExtras(bundle));
        finish();

    }


}
