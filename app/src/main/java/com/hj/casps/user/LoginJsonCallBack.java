package com.hj.casps.user;

import com.google.gson.Gson;
import com.hj.casps.entity.appordergoods.UpdateMmbWarehouseGain;
import com.hj.casps.http.LoginBean;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/11.
 */

public class LoginJsonCallBack<T> extends AbsCallback<T> {


    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {

    }

    @Override
    public T convertSuccess(Response response) throws Exception {

        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Gson gson = new Gson();
        String string = response.body().string();
        LoginBean result = gson.fromJson(string, type);
            return (T) result;

    }

}
