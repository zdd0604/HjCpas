package com.hj.casps.entity.jsoncallback;


import com.google.gson.stream.JsonReader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.QueryUserRespon;
import com.hj.casps.entity.appUser.ToAddUserPageRespon;
import com.hj.casps.entity.appUser.ToEditUserPageRespon;
import com.hj.casps.entity.appordergoodsCallBack.AddressEditRespon;
import com.hj.casps.entity.appordergoodsCallBack.HarvestExpressRespon;
import com.hj.casps.entity.appordergoodsCallBack.SimpleResponse;
import com.hj.casps.entity.appordermoney.JsonResponse;
import com.hj.casps.entity.appordermoney.QueryMmbBankAccountRespon;
import com.hj.casps.util.Convert;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by Admin on 2017/5/8.
 * 主要用于解析实体返回的数据
 */

public abstract class JsonCallBack<T> extends AbsCallback<T> {

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
            //无数据类型,表示没有data数据的情况（以  new DialogCallback<LzyResponse<Void>>(this)  以这种形式传递的泛型)
            JsonResponse simpleResponse = Convert.fromJson(jsonReader, JsonResponse.class);
            response.close();

            //编辑操作员页面（获得启动编辑操作员页面所需数据）
            if (Constant.JSONFATHERRESPON.equals("ToEditUserPageRespon")) {
                return (T) simpleResponse.toToEditUserPageRespon();
            }
            //列表地址编辑
            else if (Constant.JSONFATHERRESPON.equals("AddressEditRespon")) {
                return (T) simpleResponse.toAddressEditRespon();
            }
            //收货列表地址
            else if (Constant.JSONFATHERRESPON.equals("HarvestExpressRespon")) {
                return (T) simpleResponse.toHarvestExpressRespon();
            }
            //收货列表地址
            else if (Constant.JSONFATHERRESPON.equals("SendExpressRespon")) {
                return (T) simpleResponse.toSendExpressRespon();
            }
            //银行账户列表地址
            else if (Constant.JSONFATHERRESPON.equals("QueryMmbBankAccountRespon")) {
                return (T) simpleResponse.toQueryMmbBankAccountRespon();
            }
            //获得操作员列表
            else if (Constant.JSONFATHERRESPON.equals("QueryUserRespon")) {
                return (T) simpleResponse.toQueryUserRespon();
            }
            //获得添加操作员页面所需数据
            else if (Constant.JSONFATHERRESPON.equals("ToAddUserPageRespon")) {
                return (T) simpleResponse.toReturnMessageRespon();
            }
            //添加操作员（添加操作员页面提交时）
            else if (Constant.JSONFATHERRESPON.equals("CreateUserRespon")) {
                return (T) simpleResponse.toReturnMessageRespon();
            }
            //验证账号是否注册（创建操作员时会用到）
            else if (Constant.JSONFATHERRESPON.equals("CheckUserRespon")) {
                return (T) simpleResponse.toCheckUserRespon();
            }
            //只返回  return_code   return_message
            else if (Constant.JSONFATHERRESPON.equals("ReturnMessageRespon")) {
                return (T) simpleResponse.toReturnMessageRespon();
            }
            //收货地址尸体
            else {
                return (T) simpleResponse.toQueryMmbWareHouseGain();
            }
        }
        /**
         *  部分公共的实体类解析
         */
        else if (rawType == SimpleResponse.class) {
            SimpleResponse queryMmbWareHouseGain = Convert.fromJson(jsonReader, type);
            response.close();
            int code = queryMmbWareHouseGain.return_code;
            if (code == 0) {
                return (T) queryMmbWareHouseGain;
            } else if (code == 101) {
                throw new IllegalStateException(queryMmbWareHouseGain.return_message);
            } else if (code == 201) {
                throw new IllegalStateException(queryMmbWareHouseGain.return_message);
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + queryMmbWareHouseGain.return_message);
            }
        }
        /**
         *  地址修改
         */
        else if (rawType == AddressEditRespon.class) {
            AddressEditRespon addressEditRespon = Convert.fromJson(jsonReader, type);
            response.close();
            int code = addressEditRespon.return_code;
            if (code == 0) {
                return (T) addressEditRespon;
            } else if (code == 101) {
                throw new IllegalStateException(addressEditRespon.return_message);
            } else if (code == 201) {
                throw new IllegalStateException(addressEditRespon.return_message);
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + addressEditRespon.return_message);
            }
        }
        /**
         *  收货列表
         */
        else if (rawType == HarvestExpressRespon.class) {
            //有数据类型，表示有data
            HarvestExpressRespon harvestExpressRespon = Convert.fromJson(jsonReader, type);
            response.close();
            int code = harvestExpressRespon.return_code;
            //这里的0是以下意思
            //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
            if (code == 0) {
                return (T) harvestExpressRespon;
            } else if (code == 101) {
                throw new IllegalStateException(harvestExpressRespon.return_message);
            } else if (code == 201) {
                throw new IllegalStateException(harvestExpressRespon.return_message);
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + harvestExpressRespon.return_message);
            }
        }

        /**
         *  银行卡列表
         */
        else if (rawType == QueryMmbBankAccountRespon.class) {
            QueryMmbBankAccountRespon queryMmbBankAccountGain = Convert.fromJson(jsonReader, type);
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
        }
        /**
         *  获取编辑操作员页面（获得启动编辑操作员页面所需数据）
         */
        else if (rawType == ToEditUserPageRespon.class) {
            ToEditUserPageRespon toEditUserPageRespon = Convert.fromJson(jsonReader, type);
            response.close();
            int code = toEditUserPageRespon.return_code;
            if (code == 0) {
                return (T) toEditUserPageRespon;
            } else if (code == 101) {
                throw new IllegalStateException(toEditUserPageRespon.return_message);
            } else if (code == 201) {
                throw new IllegalStateException(toEditUserPageRespon.return_message);
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + toEditUserPageRespon.return_message);
            }
        }
        /**
         *  获得操作员列表
         */
        else if (rawType == QueryUserRespon.class) {
            //有数据类型，表示有data
            QueryUserRespon QueryUserRespon = Convert.fromJson(jsonReader, type);
            response.close();
            int code = QueryUserRespon.return_code;
            //这里的0是以下意思
            //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
            if (code == 0) {
                return (T) QueryUserRespon;
            } else if (code == 101) {
                throw new IllegalStateException(QueryUserRespon.return_message);
            } else if (code == 201) {
                throw new IllegalStateException(QueryUserRespon.return_message);
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + QueryUserRespon.return_message);
            }
        }
        /**
         *  获得添加操作员页面所需数据
         */
        else if (rawType == ToAddUserPageRespon.class) {
            ToAddUserPageRespon toAddUserPageRespon = Convert.fromJson(jsonReader, type);
            response.close();
            int code = toAddUserPageRespon.return_code;
            if (code == 0) {
                return (T) toAddUserPageRespon;
            } else if (code == 101) {
                throw new IllegalStateException(toAddUserPageRespon.return_message);
            } else if (code == 201) {
                throw new IllegalStateException(toAddUserPageRespon.return_message);
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + toAddUserPageRespon.return_message);
            }
        }
        /**
         * 类无法解析
         */
        else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }
}
