package com.hj.casps.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by xx on 2016/5/16.
 */
public class SharedPreferenceTools {
    private static final String SP_NAME = "config";
    private static SharedPreferences mSp;

    //保存布尔值
    public static void saveBoolean(Context context, String key, boolean value) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context
                    .MODE_PRIVATE);
        }
        mSp.edit().putBoolean(key, value).commit();
    }
    //获取布尔值
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        boolean result = mSp.getBoolean(key, defValue);
        return result;
    }


    //保存字符串
    public static void saveString(Context context, String key, String value) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context
                    .MODE_PRIVATE);
        }
        mSp.edit().putString(key, value).commit();

    }
    //获取字符串
    public static String getString(Context context, String key, String defValue) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        String result = mSp.getString(key, defValue);
        return result;
    }

    /*清除sp某个内容*/
    public static void removeString(Context context, String key) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context
                    .MODE_PRIVATE);
        }
        mSp.edit().remove(key).commit();

    }


    //保存字符串
    public static void saveInt(Context context, String key, int value) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context
                    .MODE_PRIVATE);
        }
        mSp.edit().putInt(key, value).commit();

    }
    //获取字符串
    public static int getInt(Context context, String key, int defValue) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        int result = mSp.getInt(key, defValue);
        return result;
    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
    public static void setObject(Context context,String key, Object object) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static  <T> T getObject(Context context,String key, Class<T> clazz) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        if (mSp.contains(key)) {
            String objectVal = mSp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }





}
