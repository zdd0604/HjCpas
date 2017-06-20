package com.hj.casps.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.util.NetUtil;


/**
 * Created by Administrator on 2017/04/11.
 * 头部 无侧滑 简单返回 或 侧按纽
 */

public class ActivityBaseHeader2 extends ActivityBase {

    protected TextView titleTv;
    protected TextView titleRight;
    protected TextView titleLeft;
    private TextView netTv;
    private RelativeLayout emptyRe;
    private ImageView emptyImg;
    private TextView emptyTv;
    int netStyle = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_basehead_layout2);
        initBaseView();
        netShow();
    }

    @Override
    public boolean inspectNet() {
        int netMobile = NetUtil.getNetWorkState(this);
        if (netMobile == -1) {
            netStyle = 1;
            return true;
        } else {
            netStyle = 0;
            return false;
        }
    }

    public void netShow() {
        if (netStyle == 0) {
            netTv.setVisibility(View.GONE);
        } else {
            netTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        netShow();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View.inflate(this, layoutResID, (ViewGroup) findViewById(R.id.base_content));
    }

    /**
     * 调用后 才能得到titleTv否则为空
     */
    private void initBaseView() {
        setSupportActionBar((Toolbar) findViewById(R.id.base_tool_bar));
        titleTv = (TextView) findViewById(R.id.base_toolbar_title);
        titleLeft = (TextView) findViewById(R.id.base_nav_back);
        titleRight = (TextView) findViewById(R.id.base_nav_right);
        netTv = (TextView) findViewById(R.id.base_net_errors);
        emptyRe = (RelativeLayout) findViewById(R.id.base_empty_Res);
        emptyImg = (ImageView) findViewById(R.id.base_empty_Re_pics);
        emptyTv = (TextView) findViewById(R.id.base_empty_Re_txs);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        titleRight.setEnabled(true);
        BaseTitleClick baseTitleClick = new BaseTitleClick();
        titleLeft.setOnClickListener(baseTitleClick);
        titleRight.setOnClickListener(baseTitleClick);
    }

    public void setEmptyPage(int imgid, String s) {
        emptyRe.setVisibility(View.VISIBLE);
        emptyImg.setImageResource(imgid);
        emptyTv.setText(s);
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == -1) {
            netTv.setVisibility(View.VISIBLE);
        } else {
            netTv.setVisibility(View.GONE);
        }

        // if (netMobile == 1) {
        // System.out.println("inspectNet：连接wifi");
        // } else if (netMobile == 0) {
        // System.out.println("inspectNet:连接移动数据");
        // } else if (netMobile == -1) {
        // System.out.println("inspectNet:当前没有网络");
        //
        // }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void back() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置中间标题
     *
     * @param titleText
     */
    public void setTitle(String titleText) {
        if (titleText != null) {
            if (titleTv != null) {
                titleTv.setText(titleText);
            }
        }
    }


    /**
     * @param text
     * @param drawableRes
     */
    public void setTitleRight(String text, Drawable drawableRes) {
        if (titleRight == null) {
            return;
        }
        if (text == null && drawableRes == null) {
            titleRight.setVisibility(View.GONE);
        } else {
            titleRight.setVisibility(View.VISIBLE);
        }
        if (text != null) {
            titleRight.setText(text);
            titleRight.setBackgroundResource(R.color.transParent);
        }
        if (drawableRes != null) {
            titleRight.setBackgroundDrawable(drawableRes);
            titleRight.setText("");
        }


    }

    private class BaseTitleClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.base_nav_back:
                    onBackClick();
                    break;
                case R.id.base_nav_right:
                    onRightClick();
                    break;
            }

        }
    }

    /**
     * 标题中右边的部分，
     */
    protected void onRightClick() {

    }

    protected void onBackClick() {
        back();
    }

}
