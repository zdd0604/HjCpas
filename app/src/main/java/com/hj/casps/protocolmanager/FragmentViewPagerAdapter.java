package com.hj.casps.protocolmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zy on 2017/4/25.
 * fragment 的适配器，订单管理，协议管理通用。
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> data;
    private List<String> dataText;

    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> data, List<String> dataText) {
        super(fm);
        this.data = data;
        this.dataText = dataText;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    public CharSequence getPageTitle(int position) {
        return dataText.get(position);
    }
}
