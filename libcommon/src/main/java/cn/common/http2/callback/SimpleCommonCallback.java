package cn.common.http2.callback;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

import cn.common.app.BuildConfig;
import cn.common.http2.HttpX;
import okhttp3.Call;
import okhttp3.Response;

/**
 */
public abstract class SimpleCommonCallback<T> extends AbsCallback<T> {

    private KProgressHUD progressHUD;
    private boolean showProgress;
    private Context activity;
    private String requestTag;

    public SimpleCommonCallback(Context activity) {
        super();
        this.activity = activity;
    }

    public SimpleCommonCallback(Fragment fragment) {
        super();
        this.activity = fragment.getActivity();
    }

    public SimpleCommonCallback setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
        return this;
    }

    public void onSuccess(T t) {
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {
        log("onSuccess() called with: " + "t = [" + t + "], call = [" + call + "], response = [" + response + "]");
        try {
            String string = response.body().string();
            log(string);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(string);
            int return_code = jsonObject.getInteger("return_code");
            if (return_code == 0) {
                onSuccess(t);
            } else {
                toast(jsonObject.getString("return_message"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void log(String string) {
        if (BuildConfig.DEBUG) {
            System.out.println(string);
        }
    }


    private void toast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    private String getTag(BaseRequest request) {
        this.requestTag = request.getUrl() + new Date().getTime();
        return this.requestTag;
    }


    /**
     * 对话框取消的时候取消订阅
     *
     * @param progressHUD
     */
    private void checkDialog(final KProgressHUD progressHUD) {
        if (progressHUD == null || !progressHUD.isShowing()) {
            return;
        }
        try {
            Field field = progressHUD.getClass().getDeclaredField("mProgressDialog");
            field.setAccessible(true);
            Dialog dialog2 = (Dialog) field.get(progressHUD);
            dialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    OkGo.getInstance().cancelTag(requestTag);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkLife() {
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            String fragmentTag = "life";
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            if (fragmentManager.findFragmentByTag(fragmentTag) == null) {
                HttpFragment httpFragment = new HttpFragment();
                fragmentManager.beginTransaction().add(httpFragment, fragmentTag).commit();
                httpFragment.addSubscriber(progressHUD, requestTag);
            } else {
                HttpFragment httpFragment = (HttpFragment) fragmentManager.findFragmentByTag(fragmentTag);
                httpFragment.addSubscriber(progressHUD, requestTag);
            }

        }
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        request.tag(getTag(request));
        checkLife();
        if (showProgress) {
            progressHUD = HttpX.showProgressDialog(activity);
            checkDialog(progressHUD);
        }
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        //*泛型 反射 com.hj.tl.driver.TestCall2<com.hj.tl.driver.MyClass, java.lang.String>*//*
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Type typeT = params[0];
            return JSONObject.parseObject(response.body().string(), typeT);
        }
        return null;
    }


    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        if (progressHUD != null) {
            progressHUD.dismiss();
        }
    }
}
