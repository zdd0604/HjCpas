package com.hj.casps.util;

import android.support.v4.util.ArrayMap;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by 鑫 Administrator on 2017/5/4.
 */

public class NetUtil2 {
    private static Map<WeakReference<Object>, Long> map = new ArrayMap<>();

    /**
     * @param object 可以为Activity fragment view 等
     * @return true, 可以进行事件处理，false不可以，时间未到
     */
    public static boolean checkEnableDo(Object object) {
        Set<WeakReference<Object>> keySet = map.keySet();
        /*使用倒序性能会好些*/
        Iterator<WeakReference<Object>> iterator = keySet.iterator();
        for (; iterator.hasNext(); ) {
            WeakReference<Object> item = iterator.next();
            if (item != null && item.get() == object) {
                Long lastDoTime = map.get(item);
                long nowTime = new Date().getTime();
                long timeInterval = nowTime - lastDoTime;
                if (timeInterval < SubmitClickUtils.MinDoInterval) {
                    return false;
                } else {
                    map.put(item, nowTime);
                    return true;
                }
            }
        }
        /*map中不存在，*/
        map.put(new WeakReference<Object>(object), new Date().getTime());
        return true;
    }
}
