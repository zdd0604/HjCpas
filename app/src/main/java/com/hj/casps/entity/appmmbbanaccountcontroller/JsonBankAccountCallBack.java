package com.hj.casps.entity.appmmbbanaccountcontroller;


import com.google.gson.stream.JsonReader;
import com.hj.casps.util.Convert;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by Admin on 2017/5/8.
 */

public abstract class JsonBankAccountCallBack<T> extends AbsCallback<T> {

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        Type rawType = ((ParameterizedType) type).getRawType();
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        JsonReader jsonReader = new JsonReader(response.body().charStream());

        if (typeArgument == Void.class) {
            QueryMmBankAccountResponse simpleResponse = Convert.fromJson(jsonReader, QueryMmBankAccountResponse.class);
            response.close();
            return (T) simpleResponse.toQueryMmbBankAccountGain();
        } else if (rawType == QueryMmbBankAccountGain.class) {
            QueryMmbBankAccountGain queryMmbBankAccountGain = Convert.fromJson(jsonReader, type);
            response.close();
            int code = queryMmbBankAccountGain.return_code;
            //这里的0是以下意思
            //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
            if (code == 0) {
                return (T) queryMmbBankAccountGain;
            } else if (code == 101) {
                throw new IllegalStateException("无权限");
            } else if (code == 201) {
                throw new IllegalStateException("数据库错误");
            } else {
                throw new IllegalStateException("错误代码：" + code);
            }
        } else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }
}
