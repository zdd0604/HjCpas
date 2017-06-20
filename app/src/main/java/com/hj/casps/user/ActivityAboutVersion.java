package com.hj.casps.user;

import android.os.Bundle;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;

/**
 * Created by YaoChen on 2017/4/14.
 */

public class ActivityAboutVersion extends ActivityBaseHeader2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutversion);
        setTitleRight(null,null);
        setTitle(getString(R.string.about_version));
    }
}
