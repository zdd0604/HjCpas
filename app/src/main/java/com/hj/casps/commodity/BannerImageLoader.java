package com.hj.casps.commodity;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hj.casps.R;
import com.hj.casps.util.LogToastUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2016/12/29.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String url;
//        if (path instanceof BeanGoodHome.LunboEntity) {
//            BeanGoodHome.LunboEntity entity = (BeanGoodHome.LunboEntity) path;
//            url = entity.getGoods_img_url();
//        } else {
            url = path.toString();
//        }
        LogToastUtils.log(getClass().getSimpleName(), "url->" + url);
        Glide.with(context).load(url).error(R.drawable.default_imgs).placeholder(R.drawable.default_imgs).into(imageView);
    }
}
