package com.hj.casps.entity.goodsmanager.request;

/**
 * 商品列表请求参数实体
 * Created by Administrator on 2017/5/4.
 */

public class GoodsListRequestEntity {

    public String sys_token;    //	令牌号
    public String sys_uuid;//操作唯一编码（防重复提交）
    public String sys_func;//功能编码（用于授权检查）
    public String sys_user;//用户id
    public String sys_member;//	;会员id


    public String categoryI;    //	商品分类ID
    public String pageNo;    // 页码
    public String pageSize;    // 每页数量

}
