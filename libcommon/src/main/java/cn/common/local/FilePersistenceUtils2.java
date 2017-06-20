package cn.common.local;

import android.content.Context;
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
 * Created by xin on 2017/6/20.
 */

public class FilePersistenceUtils2 {
    private String path;

    private FilePersistenceUtils2(Context context, String path1) {
        path = XUtils.getDir(context, path1);
    }

    /**
     * @param context     null 存在sd下，否则存在Android data下
     * @param defaultPath 存储路径
     * @return
     */
    public static FilePersistenceUtils2 getInstance(Context context, String defaultPath) {
        return new FilePersistenceUtils2(context, defaultPath == null ? "persistence" : defaultPath);
    }

    public static FilePersistenceUtils2 getInstance(Context context) {
        return getInstance(context, null);
    }

    public String getPath() {
        return path;
    }

    /**
     * 递归删除文件或文件夹
     *
     * @param f
     */
    public static void delFile(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files.length > 0) {
                for (File item : files) {
                    delFile(item);
                }
            }
            delete(f);
        } else {
            delete(f);
        }
    }

    /**
     * 否则会出现open failed: EBUSY (Device or resource busy)
     * http://stackoverflow.com/questions/11539657/open-failed-ebusy-device-or-resource-busy
     */
    private static void delete(File file) {
        final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
        file.renameTo(to);
        to.delete();
    }

    /**
     * 向文件写入数据
     *
     * @param fileName 相对sd 卡com/hj/artbean/data的路径
     */
    public void write(String fileName, String txt) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());
        File f = getFileForWrite(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(txt);
            bw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StrictMode.setThreadPolicy(old);
    }

    /**
     * 向文件写入数据 追加到尾部
     *
     * @param fileName 相对sd 卡com/hj/artbean/data的路径
     */
    public void append(String fileName, String txt) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());
        File f = getFileForWrite(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(f, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(txt);
            bw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StrictMode.setThreadPolicy(old);
    }

    /**
     * @param fileName
     */
    public String read(String fileName) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskReads()
                .build());
        File f = getFileForRead(fileName);
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
            e.printStackTrace();
        }
        StrictMode.setThreadPolicy(old);
        return null;
    }

    public String read(InputStream inputStream) {
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

    private File getFileForRead(String fileName) {
        File f = new File(path, fileName);
        return f;
    }

    public void delFile(String fileName) {
        File f = getFileForRead(fileName);
        delFile(f);
    }

    public void write(String fileName, Serializable obj) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());
        File f = getFileForRead(fileName);
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

    public boolean checkExit(String fileName) {
        File f = getFileForRead(fileName);
        if (f.exists()) {
            return true;
        }
        return false;
    }

    public Object readObj(String fileName) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskReads()
                .build());
        File f = getFileForRead(fileName);
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

    /**
     * @param fileName 文件名 不是目录名
     * @return
     */
    public File getFileForWrite(String fileName) {
        File f = new File(path, fileName);
        f.getParentFile().mkdirs();
        return f;
    }

}

