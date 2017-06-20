package com.hj.casps.commodity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.base.FragmentBase;

/**
 * Created by YaoChen on 2017/4/13.
 */

public class SelectPicture01 extends ActivityBaseHeader2 {
    RadioGroup radioGroup;
    ViewPager viewPager;
    private FragmentBase[] fragmentBases;
    private int PageFlag;
    private RelativeLayout rtl;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageFlag = getIntent().getIntExtra("PicStyle", 0);
        if (PageFlag == 1) {
            setTitle(getString(R.string.select_title_pic));
        } else if (PageFlag == 2) {
            setTitle(getString(R.string.select_banner_pic));
        }
        setContentView(R.layout.activity_selectpic01);
        initView();
    }

    private void initView() {
//        Intent intent = new Intent();
        a = getIntent().getIntExtra("fra", 1);
        radioGroup = (RadioGroup) findViewById(R.id.selectpic01_rg);
        viewPager = (ViewPager) findViewById(R.id.selectpic01_vp);
        radioGroup.setOnCheckedChangeListener(new JiapuRgChangeListener());
        rtl = (RelativeLayout) findViewById(R.id.rtl);
        if (a == 0) {
            fragmentBases = new FragmentBase[]{new FragmentPlatformPic()};
            rtl.setVisibility(View.GONE);
            setTitle(getString(R.string.product_order_choose));
        } else {
//            fragmentBases = new FragmentBase[]{new FragementTemporaryPic(), new FragmentPlatformPic(), new FragmentMyPic()};
        }
        viewPager.setAdapter(new JiaPuViewPageAdapter(getSupportFragmentManager()));

    }

    private class JiaPuViewPageAdapter extends FragmentPagerAdapter {
        public JiaPuViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentBases.length;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("PicStyle", PageFlag);
            fragmentBases[position].setArguments(bundle);
            return fragmentBases[position];
        }

    }

    private class JiapuRgChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (checkedId == R.id.selectpic01_temporary_rb) {
                viewPager.setCurrentItem(0);
            } else if (checkedId == R.id.selectpic01_platform_rb) {
                viewPager.setCurrentItem(1);
            } else {
                viewPager.setCurrentItem(2);
            }
        }
    }
}
