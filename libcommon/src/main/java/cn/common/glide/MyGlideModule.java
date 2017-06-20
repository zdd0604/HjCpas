package cn.common.glide;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import cn.common.glide.okhttp.OkHttpUrlLoader;

/**
 * Created by Administrator on 2016/10/21.
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) { // Apply options to the builder here.
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String packageName = context.getPackageName();
        builder.setDiskCache(new DiskLruCacheFactory(absolutePath, packageName + "/glide", 250 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(context));
    }
}