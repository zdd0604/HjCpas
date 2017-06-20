package com.hj.casps.util;

import java.text.DecimalFormat;

/**
 * Created by YaoChen on 2017/4/25.
 * 数据格式的处理
 */

public class DecimalUtils {
    public static DecimalFormat getDecimalFormat() {
        DecimalFormat df = new DecimalFormat(",##0.00");
        return df;
    }
    public static DecimalFormat getSizeFormat() {
        DecimalFormat df = new DecimalFormat("##0");
        return df;
    }

    /**
     * 对整数形格式化
     * @return
     */
    public static DecimalFormat getIntFormat() {
        DecimalFormat df = new DecimalFormat(",###");
        return df;
    }

    /**
     * 将秒值格式化成分钟 秒的形式
     *
     * @param secondLong
     * @return  12:23 12分23秒的格式
     */
    public static String getTimeByMillisSecond(long secondLong) {
        if (secondLong <= 0) {
            return "00:00";
        }
        long i = secondLong / 1000;
        int minute = (int) (i / 60);
        int second = (int) i % 60;
        String _str = unitFormat(minute) + ":" + unitFormat(second);
        return _str;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
