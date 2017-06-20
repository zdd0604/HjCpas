package cn.common.http;

/**
 * Created by Administrator on 2016/8/13.<br>
 * retrofit2.Retrofit中调试 查看网络请求的详细参数  Response<T> response = call.execute();
 */

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

class RetrofitFactory {
    private static HttpServiceApi httpServiceApi;

    static HttpServiceApi getServiceApi() {
        if (httpServiceApi == null) {
            Converter.Factory fastJsonConverterFactory = FastJsonConverterFactory.create();
            CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OkHttpFactory.getInstance())
                    .baseUrl(HttpRxUtils.BaseUrl) //实际不用,不指定会报错
                    .addConverterFactory(fastJsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            httpServiceApi = retrofit.create(HttpServiceApi.class);
        }
        return httpServiceApi;
    }

}
