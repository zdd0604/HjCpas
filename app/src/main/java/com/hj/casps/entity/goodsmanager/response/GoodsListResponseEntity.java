package com.hj.casps.entity.goodsmanager.response;

import java.util.List;
import java.util.Map;

/**商品列表
 * Created by Administrator on 2017/5/4.
 */

public class GoodsListResponseEntity {

    public int pageNo;// 当前页码
    public int pageSize;// 每页数量
    public int totalCount;// 总记录数
    public int totalPage;// 总页数
    public List<Map> dataList;// 图片列表:
    public String goodsId;//  商品ID
    public String imgPath;//  商品图片路径
    public String name;//   商品名称
    public int status;//商品状态


}
