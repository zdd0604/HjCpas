package com.hj.casps;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lzy.okgo.OkGo;

import org.junit.Test;
import org.junit.runner.RunWith;

import cn.common.http2.callback.SimpleCommonCallback;

@RunWith(AndroidJUnit4.class)
public class HttpTest {
    @Test
    public void callBackTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String jsonUrl="http://apis.baidu.com/bdyunfenxi/intelligence/ip";
        String htmlUrl = "http://www.jianshu.com/p/252555228b72";
        OkGo.get(htmlUrl)
                .execute(new SimpleCommonCallback<Object>(appContext) {
                    @Override
                    public void onSuccess(Object reLoginBean) {
                        if (reLoginBean != null) {
                        }
                    }
                });
        Thread.sleep(1000000);
    }
}
