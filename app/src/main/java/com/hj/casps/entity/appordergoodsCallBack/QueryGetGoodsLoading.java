package com.hj.casps.entity.appordergoodsCallBack;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/12.
 * 功能描述：收货操作 提交
 * List<GetGoodsOperationEntity>
 */

public class QueryGetGoodsLoading<T> implements Serializable {

    private static final long serialVersionUID = -1494630979952265687L;
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_name;//string	用户名
    private String sys_member;//	string	会员id
    private T param;
}
