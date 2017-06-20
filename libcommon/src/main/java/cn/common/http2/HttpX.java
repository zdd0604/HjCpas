package cn.common.http2;

import android.app.Application;
import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.util.logging.Level;

import cn.common.CommonAppUtils;

/**
 * Created by 鑫 Administrator on 2017/4/26.
 */

public class HttpX {
    public OkGo getInstance() {
        return OkGo.getInstance();
    }

    public static void init(Application context) {
        OkGo.init(context);
        OkGo.getInstance()
                .debug("OkGo", Level.INFO, CommonAppUtils.isDebug)
                .setCertificates()
                .setCacheMode(CacheMode.NO_CACHE);
    }

    /**
     * 整个网络加载的进度条
     *
     * @param context
     * @return
     */
    public static KProgressHUD showProgressDialog(Context context) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}
