package cn.common.http;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/13.
 * // @GET("index") //访问地址baseurl+index
 * // @GET("/index") //访问地址 baseurl中的域名+index
 * //@POST("http://api.nuuneoi.com/special/user/list") 忽略baseurl
 */
 interface HttpServiceApi {

    @GET
    Observable<Result<String>> get(@Url String httpUrl);
    /**
     * post提交无参数的json格式
     *
     * @param httpUrl
     * @param jsonObject
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST
    Observable<Result<String>> postJson(@Url String httpUrl, @Body JSONObject jsonObject);

    /**
     * post提交无参数的json格式 带头部的
     *
     * @param httpUrl
     * @param jsonObject
     * @param headers
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST
    Observable<Result<String>> postJson(@Url String httpUrl, @Body JSONObject jsonObject, @HeaderMap Map<String,String> headers);

    @POST
    Observable<Result<String>> post(@Url String httpUrl);

    /**
     * form表单的形式提交数据
     * http://stackoverflow.com/questions/30777419/getting-header-from-response-retrofit-okhttp-client
     * @param httpUrl
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<Result<String>> postParam(@Url String httpUrl, @FieldMap Map<String, String> params);
}
