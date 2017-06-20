package com.hj.casps.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YaoChen on 2017/4/14.
 * 登录后主页
 */

public class ActivityLoginAfter extends ActivityBaseHeader2 {

    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_after);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ActivityLoginBefore.initWebView(mWebView);
        mWebView.loadUrl("https://www.baidu.com/", null);
    }

    @OnClick({R.id.goBuy, R.id.goSell, R.id.goManager})
    public void onGoClicked(View view) {
        switch (view.getId()) {
            case R.id.goBuy:
                break;
            case R.id.goSell:
                break;
            case R.id.goManager:
                break;
            case R.id.user_btn:
                break;
        }
    }

    @OnClick({R.id.user_btn, R.id.tag1, R.id.tag2, R.id.tag3, R.id.tag4})
    public void onTagClicked(View view) {
        switch (view.getId()) {
            case R.id.user_btn:
                break;
            case R.id.tag1:
                break;
            case R.id.tag2:
                break;
            case R.id.tag3:
                break;
            case R.id.tag4:
                break;
        }
    }
}
