package com.hj.casps.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import cn.common.local.FilePersistenceUtils2;

/**
 * Created by xin on 2017/6/20.
 */

public class BitmapUtils2 {

    public static final double MaxSize=1024*1024;

    /**
     * 图片压缩
     * @param filePath 原文件路径
     * @return 新的文件路径
     */
    public String getCompressFile(String filePath) throws  Exception{
        /*读取bitmap*/
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, option);
        int max = Math.min(option.outWidth, option.outHeight);
        option.inSampleSize = (int) (max * 1f / MaxSize);
        option.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(filePath, option);
        while (bm.getByteCount() > 1024 * 1024) { /*不大于1M*/
            Matrix var6 = new Matrix();
            var6.postScale(0.8f, 0.8f);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), var6, true);
        }
        /*Bitmap写入文件*/
        File file = FilePersistenceUtils2.getInstance(null,"BitmapUtils2").getFileForWrite(new Date().getTime() + ".jpg");
        FileOutputStream fos = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.close();
        bm.recycle();
        return file.getAbsolutePath();
    }
}
