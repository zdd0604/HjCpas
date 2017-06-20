package com.hj.casps.entity.goodsmanager.request;

/**
 * Created by Administrator on 2017/5/4.
 */

public class GoodsAllCategoryRequestEntity {


    public String sys_token;    //	令牌号
    public String sys_uuid;//操作唯一编码（防重复提交）
    public String sys_func;//功能编码（用于授权检查）
    public String sys_user;//用户id
    public String sys_member;//	;会员id
}
