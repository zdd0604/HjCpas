package com.hj.casps.entity.goodsmanager.request;

/**功能描述：检查同一会员同一分类下的商品名称不可以重复，
 * Created by Administrator on 2017/5/4.
 */

public class GoodsCheckNameRequestEntity {
    public String sys_token;    //	令牌号
    public String sys_uuid;//操作唯一编码（防重复提交）
    public String sys_func;//功能编码（用于授权检查）
    public String sys_user;//用户id
    public String sys_member;//	;会员id


   public String  categoryId;//	商品分类Id
    public String goodsName;//商品名称

}
