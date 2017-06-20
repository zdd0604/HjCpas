package com.hj.casps.commodity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

public class ActivityPictureSearch extends ActivityBaseHeader2 {

    @BindView(R.id.search_tv_bills_title_1)
    TextView searchTvBillsTitle1;
    @BindView(R.id.search_ed_bills_content_1)
    EditText searchEdBillsContent1;
    @BindView(R.id.search_payment_btn)
    FancyButton searchPaymentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_search);
        ButterKnife.bind(this);
        setTitle("查询");
        setTitleRight("", null);
    }
    @OnClick(R.id.search_payment_btn)
    public void onViewClicked() {
        String materialName = searchEdBillsContent1.getText().toString().trim();
        Constant.MaterialName=materialName;
        setResult(RESULT_OK);
        finish();

    }
}
