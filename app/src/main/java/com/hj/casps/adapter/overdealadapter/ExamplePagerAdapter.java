package com.hj.casps.adapter.overdealadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 * Fragment Adapter
 */

public class ExamplePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragment;

    public ExamplePagerAdapter(FragmentManager fm, List<Fragment> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}