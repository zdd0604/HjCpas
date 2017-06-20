package com.hj.casps.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 鑫 Administrator on 2017/5/5.
 */

public class ActivityLoginBefore extends ActivityBaseHeader2 {
    @BindView(R.id.user_btn)
    Button mUserBtn;
    @BindView(R.id.user_tag_layout)
    LinearLayout mUserTagLayout;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_before);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleLeft.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        titleTv.setText(R.string.app_name_big);
        // TODO: 2017/5/5  
      /*  if(true){
            mUserTagLayout.setVisibility(View.VISIBLE);
        }*/
        initWebView(mWebView);
        mWebView.loadUrl(Constant.HOME_HTTP_URL, null);

    }

    public static void initWebView(WebView webview) {
        WebSettings setting = webview.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setDatabaseEnabled(true);
        setting.setUseWideViewPort(true); /*加载手机版的 */
        setting.setLoadWithOverviewMode(true);
        setting.setSupportZoom(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick(R.id.user_btn)
    public void onLoginClicked() {
        startActivity(new Intent(this, ActivityLogin.class));
    }
}
