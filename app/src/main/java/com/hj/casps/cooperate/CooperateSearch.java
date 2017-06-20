package com.hj.casps.cooperate;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;

import mehdi.sakout.fancybuttons.FancyButton;
//关注会员目录查询
public class CooperateSearch extends ActivityBaseHeader2 implements View.OnClickListener {

    private Spinner cooperate_grade_spinner;
    private FancyButton cooperate_search;
    private String[] gradeItems;
    private TestArrayAdapter stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate_search);
        initData();
        initView();
    }

    //查询数据加载
    private void initData() {
        gradeItems = new String[]{"全部", "1", "2", "3", "4", "5"};
    }

    //关注会员目录的布局
    private void initView() {

        setTitle(getString(R.string.cooperate_contents));
        cooperate_grade_spinner = (Spinner) findViewById(R.id.cooperate_grade_spinner);
        cooperate_search = (FancyButton) findViewById(R.id.cooperate_search);
        cooperate_search.setOnClickListener(this);
        stringArrayAdapter = new TestArrayAdapter(this,gradeItems);
        cooperate_grade_spinner.setAdapter(stringArrayAdapter);
    }

    //提交查询数据
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cooperate_search:
                bundle.putInt("grade", cooperate_grade_spinner.getSelectedItemPosition());
                setResult(22, getIntent().putExtras(bundle));
                finish();
                break;
        }
    }
}
