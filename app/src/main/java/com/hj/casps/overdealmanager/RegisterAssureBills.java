package com.hj.casps.overdealmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hj.casps.R;
import com.hj.casps.adapter.overdealadapter.ExamplePagerAdapter;
import com.hj.casps.bankmanage.BillsSearchActivity;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登记担保列表
 */
public class RegisterAssureBills extends ActivityBaseHeader {
    @BindView(R.id.execute_magic)
    MagicIndicator execute_magic;
    @BindView(R.id.execute_viewpager)
    ViewPager execute_viewpager;

    private static final String[] CHANNELS = new String[]{"全部", "未申请登记", "已申请登记"};
    private List<String> mTabList = Arrays.asList(CHANNELS);
    private List<Fragment> mFragment = new ArrayList<>();
    private ExamplePagerAdapter mExamplePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_bills);
        ButterKnife.bind(this);
        initView();
        initMagicIndicator();
    }

    private void initView() {
        setTitle(getString(R.string.hint_tv_register_title));
        mFragment.add(new RegisterAssureFragment(0));
        mFragment.add(new RegisterAssureFragment(1));
        mFragment.add(new RegisterAssureFragment(2));
        mExamplePagerAdapter = new ExamplePagerAdapter(getSupportFragmentManager(), mFragment);
        execute_viewpager.setAdapter(mExamplePagerAdapter);
        execute_viewpager.setOffscreenPageLimit(4);
    }

    private void initMagicIndicator() {
        execute_magic.setBackgroundColor(Color.parseColor("#455a64"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setBackgroundColor(Color.WHITE);
        commonNavigator.setLeftPadding(50);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTabList == null ? 0 : mTabList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTabList.get(index));
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setPadding(20, 0, 20, 0);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        execute_viewpager.setCurrentItem(index);
                        Constant.FRGMENT_TYPE = execute_viewpager.getCurrentItem();
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#3F98E8"));
                return indicator;
            }
        });
        execute_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(execute_magic, execute_viewpager);
        execute_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2)
                    Constant.FRGMENT_TYPE = execute_viewpager.getCurrentItem();
            }
        });
    }

    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.REGISTER_BILLS_ACTIVITY_TYPE);
        intentActivity(BillsSearchActivity.class, Constant.START_ACTIVITY_TYPE, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * @author haijian
         * 收到返回的值判断是否成功，如果同意就将数据移除刷新列表
         */
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == Constant.REGISTER_BILLS_ACTIVITY_TYPE_SEARCH) {
            if (Constant.FRGMENT_TYPE == execute_viewpager.getCurrentItem())
                Constant.isRefurbishUI = true;
            execute_viewpager.setCurrentItem(Constant.FRGMENT_TYPE);
        }
    }
}
