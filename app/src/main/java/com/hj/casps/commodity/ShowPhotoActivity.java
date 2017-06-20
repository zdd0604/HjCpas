package com.hj.casps.commodity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hj.casps.R;
import com.hj.casps.common.Constant;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ShowPhotoActivity extends FragmentActivity {


    private Bitmap bitmap;
    private String pic_url;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        ImageView photoView= (ImageView) findViewById(R.id.photoView);
        pic_url = getIntent().getStringExtra(Constant.INTAENT_PUBLIC_URL);
        //如果是网络图片
            Glide.with(this).load(pic_url).into(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
