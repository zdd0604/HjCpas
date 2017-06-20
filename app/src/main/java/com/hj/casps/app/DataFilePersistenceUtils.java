package com.hj.casps.app;

import android.os.Environment;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/15.
 * 数据持久化
 * 负责从文件中读取 写入txt文件
 */
public class DataFilePersistenceUtils {
    public static String Data_PATH = "casps";

    /**
     * 向文件写入数据
     *
     * @param fileName 相对sd 卡com/hj/artbean/data的路径
     */
    public static void write(String fileName, String txt) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());
        File f = getFile(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(txt);
            bw.close();
            fos.close();
        } catch (Exception e) {
        }
        StrictMode.setThreadPolicy(old);
    }

    /**
     * @param fileName
     */
    public static String read(String fileName) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskReads()
                .build());
        File f = getFile(fileName);
        if (!f.exists()) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder sb = new StringBuilder();
            while (null != (line = br.readLine())) {
                sb.append(line);
            }
            br.close();
            fis.close();
            return sb.toString();
        } catch (Exception e) {
        }
        StrictMode.setThreadPolicy(old);
        return null;
    }

    public static String read(InputStream inputStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder sb = new StringBuilder();
            while (null != (line = br.readLine())) {
                sb.append(line);
            }
            br.close();
            inputStream.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void delFile(String fileName) {
        File f = getFile(fileName);
        f.delete();
    }

    public static void write(String fileName, Serializable obj) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());
        File f = getFile(fileName);
        ObjectOutputStream oo = null;
        try {
            oo = new ObjectOutputStream(new FileOutputStream(f));
            oo.writeObject(obj);
            oo.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oo != null) {
                try {
                    oo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        StrictMode.setThreadPolicy(old);
    }

    public static boolean checkExit(String fileName) {
        File f = getFile(fileName);
        if (f.exists()) {
            return true;
        }
        return false;
    }

    public static Object readObj(String fileName) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskReads()
                .build());
        File f = getFile(fileName);
        if (!f.exists()) {
            return null;
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(
                    f));
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (Exception e) {
            //有异常直接删除，免得下次再读
            delFile(fileName);
            e.printStackTrace();
        } finally {
            //即使return了该块还是会调用的
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        StrictMode.setThreadPolicy(old);
        return null;
    }

    private static File getFile(String fileName) {
        if (Data_PATH == null) {
            Data_PATH = "artbean02";
        }
        File f = new File(Environment.getExternalStorageDirectory(), Data_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File(f, fileName);
        return f;
    }

}
