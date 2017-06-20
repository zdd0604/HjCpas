package com.hj.casps.reception;

import android.os.Bundle;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;

/**
 * Created by YaoChen on 2017/4/18.
 */

public class ActivityPriceGoodsDetail extends ActivityBaseHeader2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_gooddetail);
        setTitleRight(null, null);
        setTitle(getString(R.string.detail));
        initView();
    }

    private void initView() {

    }
}
