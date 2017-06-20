package com.hj.casps.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hj.casps.R;
import com.hj.casps.backstage.ActivityBackStage;
import com.hj.casps.reception.ActivityPriceSearch;
import com.hj.casps.ui.MyDialog;

/**
 * Created by YaoChen on 2017/4/14.
 */

public class ActivityUser extends AppCompatActivity implements View.OnClickListener {
    private MyDialog myDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    private void initView() {
        findViewById(R.id.user_head_cancel).setOnClickListener(this);
        findViewById(R.id.user_head_set).setOnClickListener(this);
        findViewById(R.id.user_buy_tv).setOnClickListener(this);
        findViewById(R.id.user_btn).setOnClickListener(this);
        findViewById(R.id.user_sale_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_head_cancel:
                myDialog = new MyDialog(ActivityUser.this);
                myDialog.setMessage(getString(R.string.loginout_msg));
                myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        myDialog.dismiss();
                    }
                });
                myDialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
                break;
            case R.id.user_head_set:
                startActivity(new Intent(ActivityUser.this, ActivitySetting.class));
                break;
            case R.id.user_buy_tv:
                Intent buy = new Intent(ActivityUser.this, ActivityPriceSearch.class);
                buy.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                buy.putExtra("Style", 0);
                startActivity(buy);
                break;
            case R.id.user_sale_tv:
                Intent sale = new Intent(ActivityUser.this, ActivityPriceSearch.class);
                sale.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                sale.putExtra("Style", 1);
                startActivity(sale);
                break;
            case R.id.user_btn:
                startActivity(new Intent(ActivityUser.this, ActivityBackStage.class));
                break;
        }
    }
}
