package com.hj.casps.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.backstage.ActivityBackStage;
import com.hj.casps.common.Constant;
import com.hj.casps.reception.ActivityPriceSearch;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.user.ActivityLoginAfter;
import com.hj.casps.user.ActivityLoginAfter2;
import com.hj.casps.util.NetUtil;

import java.util.List;


/**
 * Created by Administrator on 2017/04/11.
 * 头部 实现头部侧滑的类
 */

public class ActivityBaseHeader extends ActivityBase {

    private TextView titleTv;
    private ImageView navMenu;
    private ImageView navSearch;
    private DrawerLayout drawLayout;
    private BaseNavLeftFragment leftFragment;
    private int slidestyle;
    private TextView netTv;
    private RelativeLayout emptyRe;
    private ImageView emptyImg;
    private TextView emptyTv;
    private int netStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_basehead_layout);
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

    @Override
    protected void onResume() {
        super.onResume();
        netShow();
    }

    public void netShow() {
        if (netStyle == 0) {
            netTv.setVisibility(View.GONE);
        } else {
            netTv.setVisibility(View.VISIBLE);
        }
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
    public void setContentView(@LayoutRes int layoutResID) {
        View.inflate(this, layoutResID, (ViewGroup) findViewById(R.id.base_content));
    }

    /**
     * 调用后 才能得到titleTv否则为空
     */
    private void initBaseView() {
        leftFragment = new BaseNavLeftFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.navigation, leftFragment).commit();
        navMenu = (ImageView) findViewById(R.id.base_nav_menu);
        drawLayout = (DrawerLayout) findViewById(R.id.base_header_layout);
        navSearch = (ImageView) findViewById(R.id.base_nav_search);
        setSupportActionBar((Toolbar) findViewById(R.id.base_tool_bar));
        titleTv = (TextView) findViewById(R.id.base_toolbar_title);
        netTv = (TextView) findViewById(R.id.base_net_error);
        emptyRe = (RelativeLayout) findViewById(R.id.base_empty_Re);
        emptyImg = (ImageView) findViewById(R.id.base_empty_Re_pic);
        emptyTv = (TextView) findViewById(R.id.base_empty_Re_tx);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        BaseViewClick viewClick = new BaseViewClick();
        navMenu.setOnClickListener(viewClick);
        navSearch.setOnClickListener(viewClick);
        leftFragment.setListener(new BaseSimpleBaseLeftListener());
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
            if (drawLayout.isDrawerOpen(Gravity.LEFT)) {
                onNavMenuClick();
            } else {
                back();
            }

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
        if (titleText != null && titleTv != null) {
            titleTv.setText(titleText);
        }
    }

    public void setEmptyPage(int imgid, String s) {
        emptyRe.setVisibility(View.VISIBLE);
        emptyImg.setImageResource(imgid);
        emptyTv.setText(s);
    }

    /**
     * @param text
     * @param drawableRes
     */
    public void setTitleRight(String text, Drawable drawableRes) {
        if (navSearch == null) {
            return;
        }
        if (text == null && drawableRes == null) {
            navSearch.setVisibility(View.GONE);
        } else {
            navSearch.setVisibility(View.VISIBLE);
        }
//        if (text != null) {
//            navSearch.setText(text);
//        }
        if (drawableRes != null) {
            navSearch.setBackgroundDrawable(drawableRes);
        }

    }

    protected void setData(BaseNavLeftFragment.BaseLeftViewData data, List<BaseNavLeftFragment.LevelEntity> listEntity) {
        leftFragment.setData(data, listEntity);
    }

    protected void setCartNun(int num) {
        leftFragment.setCartNun(num);
    }

    protected void setListener(BaseNavLeftFragment.BaseLeftListener listener) {
        leftFragment.setListener(listener);
    }

    /**
     * fragment中事件处理
     * 可实现公共的事件
     */
    protected class BaseSimpleBaseLeftListener implements BaseNavLeftFragment.BaseLeftListener {

        @Override
        public void onCartClick(View v) {
            CartClick();
        }

        @Override
        public void onExitClick(View v) {
          showExitDialog();
        }

        @Override
        public void onBackSysClick(View v) {
            BackSysClick();
        }

        @Override
        public void onUserClick(View v) {

        }

        @Override
        public void onListItemClick(BaseNavLeftFragment.LevelEntity levelEntity) {
            ListItemClick(levelEntity);
        }

        @Override
        public void onBtClick(View v, int index) {
            BtClick(index);
        }
    }

    private void ListItemClick(BaseNavLeftFragment.LevelEntity levelEntity) {
        if (slidestyle == 0) {
            ActivityBackStage.jumpActivity(levelEntity.getDircode(), this);
        }

    }


    private void BackSysClick() {
        Intent intent = new Intent(this, ActivityLoginAfter2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void CartClick() {
//        Intent intent = new Intent(this, ActivityUser.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        toast("跳到拣单车");
    }

    private void BtClick(int index) {
        switch (index) {
            case 0:
                /*报价检索的sysfunc是随便传的获取菜单的任意一个 不做验证的sysfunc*/
                if(Constant.MenuList!=null&&Constant.MenuList.size()>0){
                    Constant.SYS_FUNC=Constant.MenuList.get(0).getEntity().getDircode();
                }
                Intent intent0 = new Intent(this, ActivityPriceSearch.class);
                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent0.putExtra("Style", 0);
                startActivity(intent0);
                break;
            case 1:
                 /*报价检索的sysfunc是随便传的获取菜单的任意一个 不做验证的sysfunc*/
                if(Constant.MenuList!=null&&Constant.MenuList.size()>0){
                    Constant.SYS_FUNC=Constant.MenuList.get(0).getEntity().getDircode();
                }
                Intent intent1 = new Intent(this, ActivityPriceSearch.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("Style", 1);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(this, ActivityBackStage.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
        }

    }




    public void setTvVisibility(){
        navSearch.setVisibility(View.GONE);
    }
    /**
     * activity中公共部分的事件处理
     */
    private class BaseViewClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.base_nav_menu) {
                onNavMenuClick();
            } else if (id == R.id.base_nav_search) {
                onNavSearchClick();
            }
        }
    }

    /**
     * 导航搜索点击事件
     */
    protected void onNavSearchClick() {

    }

    /**
     * 导航菜单点击事件
     */
    private void onNavMenuClick() {
        if (drawLayout.isDrawerOpen(Gravity.LEFT)) {
            closeNavMenu();
        } else {
            drawLayout.openDrawer(Gravity.LEFT, true);
        }
    }

    protected void closeNavMenu() {
        drawLayout.closeDrawer(Gravity.LEFT, true);
    }

}
