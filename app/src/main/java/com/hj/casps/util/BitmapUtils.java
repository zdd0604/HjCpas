package com.hj.casps.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hj.casps.R;
import com.hj.casps.app.HejiaApp;
import com.hj.casps.common.Constant;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/5/7.
 */

public class BitmapUtils {

    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    //centerCrop 区别于LoadImage2就是这个centerCrop
    public static void LoadImage(String url, final ImageView iv, final int width, final int height) {
        if (url == null) {
            Glide.with(HejiaApp.getContext()).load(R.drawable.default_imgs).error(R.mipmap.up_sc).override(width, height).centerCrop().into(iv);
        } else {
            if (url.startsWith("/v2")) {
                Glide.with(HejiaApp.getContext()).load(Constant.SHORTHTTPURL + url).override(width, height).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Glide.with(HejiaApp.getContext()).load(R.drawable.default_imgs).error(R.mipmap.up_sc).override(width, height).centerCrop().into(iv);
                        return true;
                    }

                    //这个用于监听图片是否加载完成
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).into(iv);
            } else {
                Glide.with(HejiaApp.getContext()).load(Constant.LONGTHTTPURL + url).override(width, height).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Glide.with(HejiaApp.getContext()).load(R.drawable.default_imgs).error(R.mipmap.up_sc).override(width, height).centerCrop().into(iv);
                        return true;
                    }

                    //这个用于监听图片是否加载完成
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).into(iv);
            }
        }

    }

    // fitCenter 区别于LoadImage就是这个fitCenter
    public static void LoadImage2(String url, final ImageView iv, final int width, final int height) {
        if (url == null) {
            Glide.with(HejiaApp.getContext()).load(R.drawable.default_imgs).error(R.mipmap.up_sc).override(width, height).fitCenter().into(iv);
        } else {
            if (url.startsWith("/v2")) {
                Glide.with(HejiaApp.getContext()).load(Constant.SHORTHTTPURL + url).override(width, height).fitCenter().listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Glide.with(HejiaApp.getContext()).load(R.drawable.default_imgs).error(R.mipmap.up_sc).override(width, height).fitCenter().into(iv);
                        return true;
                    }

                    //这个用于监听图片是否加载完成
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).into(iv);
            } else {
                Glide.with(HejiaApp.getContext()).load(Constant.LONGTHTTPURL + url).override(width, height).fitCenter().listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Glide.with(HejiaApp.getContext()).load(R.drawable.default_imgs).error(R.mipmap.up_sc).override(width, height).fitCenter().into(iv);
                        return true;
                    }

                    //这个用于监听图片是否加载完成
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).into(iv);
            }
        }

    }


    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


}
