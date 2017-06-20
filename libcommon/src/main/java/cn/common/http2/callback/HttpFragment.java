package cn.common.http2.callback;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过fragment的生命周期，控制网络请求，onDestroy时，取消订阅
 */
@SuppressLint("ValidFragment")
public class HttpFragment extends Fragment {

    List<WeakReference<KProgressHUD>> dialog;
    List<Object> subscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public HttpFragment() {
        dialog = new ArrayList<>();
        subscriber = new ArrayList<>();
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void stop() {
        for (WeakReference<KProgressHUD> item : dialog) {
            if (item != null && item.get() != null && item.get().isShowing()) {
                item.get().dismiss();
            }
        }
        for (Object item : subscriber) {
            OkGo.getInstance().cancelTag(item);
        }
    }


    public void addSubscriber(KProgressHUD dialog, Object subscriber) {
        this.dialog.add(new WeakReference<KProgressHUD>(dialog));
        this.subscriber.add(subscriber);
    }
}