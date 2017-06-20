package cn.common.http;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/13.
 */
public class HttpRxUtils {
    public static final String NiceHttpException = "网络异常";
    public static String BaseUrl = "http://www.126.com"; //Retrofit 设置的BaseUrl实际不用
    private static boolean debug = false;
    public static final String KeyHeaderToken = "TOKENS";

    public static void setDebugMode(boolean debug) {
        HttpRxUtils.debug = debug;
    }

    /**
     * 最终网络请求
     *
     * @param <T>
     */
    public static abstract class NextSubscriber<T> extends Subscriber<T> {
        Context context;


        public NextSubscriber(Context context) {
            this.context = context;
        }

        @Override
        public void onCompleted() {
            unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            toast(context, NiceHttpException);
            e.printStackTrace();
            unsubscribe();
        }

        @Override
        public void onNext(T t) {
            if (t == null) {
                return;
            }
            onSuccessNext(t);
        }

        protected abstract void onSuccessNext(T baseBean);

    }

    public static <T> Observable<T> next(Context context, String httpUrl, JSONObject jsonObject, Class<T> tClass) {
        return nextPostJson(context, httpUrl, jsonObject, tClass, null, true);
    }

    public static <T> Observable<T> next(Context context, String httpUrl, JSONObject jsonObject, String tokenHeader, Class<T> tClass) {
        java.util.Map<String, String> headers = new HashMap<>();
        headers.put(KeyHeaderToken, tokenHeader);
        return nextPostJson(context, httpUrl, jsonObject, tClass, headers, true);
    }

    /**
     * @param context
     * @param httpUrl    请求地址
     * @param jsonObject json参数
     * @param tClass     要转换的类
     * @param showDialog 是否显示进度条
     * @param <T>        转换类的泛型
     * @return
     */
    public static <T> Observable<T> nextPostMap(Context context, String httpUrl, JSONObject jsonObject, Class<T> tClass, boolean showDialog) {
        /*进度条显示*/
        KProgressHUD dialog = null;
        if (showDialog) {
            dialog = showProgressDialog(context);
        }
        /*网络请求调用*/
        java.util.Map<String, String> map = new HashMap<>();
        String value = jsonObject.toJSONString();
        map.put("json", value);
        Observable observable = RetrofitFactory.getServiceApi().postParam(httpUrl, map)
                .subscribeOn(Schedulers.newThread())
//                                .debounce(400, TimeUnit.MILLISECONDS)//防止多次点击处理
                .doOnNext(new NextActionResLog(context))
                .onErrorReturn(new ErrorFun(dialog))
                .map(new Map(tClass))
//                .doOnNext(new NextActionDialog(dialog))
                //观察者运行在主线程
                .observeOn(AndroidSchedulers.mainThread());
        Observable<T> cancelObservable = getDialogCancelObservable(dialog, observable);
        return cancelObservable;
    }

    /**
     * @param context
     * @param httpUrl    请求地址
     * @param jsonObject json参数
     * @param tClass     要转换的类
     * @param headers    头部token
     * @param showDialog 是否显示进度条
     * @param <T>        转换类的泛型
     * @return
     */
    public static <T> Observable<T> nextPostJson(Context context, String httpUrl, JSONObject jsonObject, Class<T> tClass, java.util.Map<String, String> headers, boolean showDialog) {
        /*进度条显示*/
        KProgressHUD dialog = null;
        if (showDialog) {
//            dialog = showProgressDialog(context);
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
        /*网络请求调用*/

        Observable observable = RetrofitFactory.getServiceApi().postJson(httpUrl, jsonObject, headers)
                .subscribeOn(Schedulers.newThread())
//                .debounce(400, TimeUnit.MILLISECONDS)//防止多次点击处理
                .doOnNext(new NextActionResLog(context))
//                .onErrorReturn(new ErrorFun(dialog))
                .map(new Map(tClass))
                .doOnNext(new NextActionDialog(dialog))
                //观察者运行在主线程
                .observeOn(AndroidSchedulers.mainThread());
        Observable<T> cancelObservable = getDialogCancelObservable(dialog, observable);
        return cancelObservable;

    }

    @NonNull
    private static <T> Observable<T> getDialogCancelObservable(final KProgressHUD dialog, final Observable observable) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                if (dialog != null) {
//                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            log("onDismiss() called with: " + "dialog = [" + dialog + "]");
//                            subscriber.unsubscribe();
//                        }
//                    });
//                    if (dialog.getContext() instanceof AppCompatActivity) {
//                        AppCompatActivity activity = (AppCompatActivity) dialog.getContext();
//                        String fragmentTag = "rxjava";
//                        if (activity.getSupportFragmentManager().findFragmentByTag(fragmentTag) == null) {
//                            Fragment instantiate = Fragment.instantiate(activity, HttpFragment.class.getName());
//                            activity.getSupportFragmentManager()
//                                    .beginTransaction()
//                                    .add(instantiate, fragmentTag)
//                                    .commit();
//                        }
//                        HttpFragment fragment = (HttpFragment) activity.getSupportFragmentManager().findFragmentByTag(fragmentTag);
//                        fragment.addSubscriber(dialog, subscriber);
//                    }
                }
                observable.subscribe(subscriber);
            }

        });
    }

    /***
     * get请求
     *
     * @param context
     * @param httpUrl
     * @param tClass
     * @param showDialog
     * @param <T>
     * @return
     */
    public static <T> Observable<T> nextGet(Context context, String httpUrl, Class<T> tClass, boolean showDialog) {
        /*进度条显示*/
        KProgressHUD dialog = null;
        if (showDialog) {
//            dialog = showProgressDialog(context);
        }
        /*网络请求调用*/
        Observable observable = RetrofitFactory.getServiceApi().get(httpUrl)
                .subscribeOn(Schedulers.newThread())
                .debounce(400, TimeUnit.MILLISECONDS)//防止多次点击处理
                .doOnNext(new NextActionResLog(context))
                .onErrorReturn(new ErrorFun(dialog))
                .map(new Map(tClass))
                .doOnNext(new NextActionDialog(dialog))
                //观察者运行在主线程
                .observeOn(AndroidSchedulers.mainThread());
        Observable<T> cancelObservable = getDialogCancelObservable(dialog, observable);
        return cancelObservable;
    }

    /**
     * 进度条
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

    private static void toast(Context context, String message) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            /*主线程时才执行*/
            Toast.makeText(context, "出错:" + message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 通过fragment的生命周期，控制网络请求，onDestroy时，取消订阅
     */
    @SuppressLint("ValidFragment")
    private class HttpFragment extends Fragment {

        List<WeakReference<Dialog>> dialog;
        List<WeakReference<Subscriber>> subscriber;

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
//            stop();
        }

        private void stop() {
            for (WeakReference<Dialog> item : dialog) {
                if (item != null && item.get() != null && item.get().isShowing()) {
                    item.get().dismiss();
                }
            }
            for (WeakReference<Subscriber> item : subscriber) {
                if (item != null && item.get() != null && !item.get().isUnsubscribed()) {
                    item.get().unsubscribe();
                }
            }
        }


        public void addSubscriber(Dialog dialog, Subscriber subscriber) {
            this.dialog.add(new WeakReference<Dialog>(dialog));
            this.subscriber.add(new WeakReference<Subscriber>(subscriber));
        }
    }

    /**
     * 异常的抛出
     */
    private static class ErrorFun implements Func1 {
        private KProgressHUD dialog;

        public ErrorFun(KProgressHUD dialog) {
            this.dialog = dialog;
        }

        @Override
        public Object call(Object o) {
            log("ErrorFun:" + o.toString());
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (o instanceof Throwable) {
                Throwable throwable = (Throwable) o;
                throwable.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 取消进度条
     */
    private static class NextActionDialog implements Action1 {
        KProgressHUD dialog;

        public NextActionDialog(KProgressHUD dialog) {
            this.dialog = dialog;
        }

        @Override
        public void call(Object o) {
            dismiss();
        }

        private void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    /**
     * String 解析到类
     *
     * @param <R>
     */
    private static class Map<R> implements Func1 {

        private final Class<R> tClass;

        public Map(Class<R> clazz) {
            tClass = clazz;
        }

        @Override
        public R call(Object o) {
            if (o == null) {
                return null;
            }
            if (o instanceof Result) {
                Result result = (Result) o;
                if (result.isError()) {
                    result.error().printStackTrace();
                } else {
                    retrofit2.Response response = result.response();
                    String string = (String) response.body();
                    return jsonParse(string);
                }
            }
            if (o instanceof String) {
                return jsonParse((String) o);
            }
            return null;
        }

        private R jsonParse(String string) {
            try {
                R t = JSONObject.parseObject(string, tClass);
                return t;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }


    /**
     * 响应 写入日志
     */
    private static class NextActionResLog implements Action1 {
        Context context;

        public NextActionResLog(Context context) {
            this.context = context;
        }

        @Override
        public void call(Object o) {
            if (!debug) {
                return;
            }
            if (o instanceof Result) {
                Result result = (Result) o;
                if (!result.isError()) {
                    /*有错误时map转换时会log出的*/
                    retrofit2.Response response = result.response();
                    Response responseOkHttp = response.raw();
                    Request request = responseOkHttp.request();
                    log("NextActionResLog ==========begin ==========");
                    log("NextActionResLog ==========request ==========");
                    log("NextActionResLog url ->" + request.url().toString());
                    log("NextActionResLog header ->" + headerToString(request.headers()));
                    log("NextActionResLog body ->" + bodyToString(request));
                    log("NextActionResLog ==========response ==========");
                    log("NextActionResLog Body ->" + response.body());
                    log("NextActionResLog Error ->" + bodyErrorToString(response.errorBody()));
                    log("NextActionResLog ==========end ==========");
                }
            } else {
                log("NextActionResLog obj->" + o.toString());
            }
        }

        private String bodyErrorToString(ResponseBody responseBody) {
            if (responseBody == null) {
                return "null";
            }
            try {
                return responseBody.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "length:" + responseBody.contentLength();
        }

        public void write(String fileName, String txt) {
            StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                    .permitDiskWrites()
                    .build());
            File f = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
            f = new File(f, fileName);
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

        private String headerToString(Headers headers) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0, size = headers.size(); i < size; i++) {
                sb.append(headers.name(i) + ":" + headers.get(headers.name(i)) + ";");
            }
            String txt = sb.toString();
            return txt;
        }

        /**
         * @param request
         * @return
         */
        private String bodyToString(final Request request) {
            if (request.body() == null) {
                return "null";
            }
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                 /*写入文件*/
                String body = buffer.readUtf8();
                write("http.txt", body);
                return body;
            } catch (final IOException e) {
                return "did not work";
            }
        }


    }

    private static void log(String s) {
        Log.w(HttpRxUtils.class.getName(), s);
    }

    /**
     * 将观察者类型转化
     * json参数比较复杂时先在子线程中转换成json对象再转换Observable
     */
    public static class FlatMap<T> implements Func1<JSONObject, Observable<T>> {
        private String httpUrl;
        private Context context;
        private Class clazz;
        private java.util.Map<String, String> headers;

        public FlatMap(Context context, String httpUrl, java.util.Map<String, String> headers, Class clazz) {
            this.httpUrl = httpUrl;
            this.context = context;
            this.clazz = clazz;
            this.headers = headers;
        }

        @Override
        public Observable<T> call(JSONObject jsonObject) {
            Observable observable = nextPostJson(context, httpUrl, jsonObject, clazz, headers, false);
            return observable;
        }
    }

    private static class ErrorThrowable implements Action1<Throwable> {
        Dialog dialog;

        public ErrorThrowable(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void call(Throwable throwable) {
            log("ErrorThrowable->" + throwable.toString());
            if (dialog != null) {
                toast(dialog.getContext(), throwable.getMessage());
            }
            throwable.printStackTrace();
        }
    }
}
