package com.hj.casps.entity.goodsmanager.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */

public class SearchGoodGain<T> implements Serializable {

    private static final long serialVersionUID = -6615497795502250262L;
    public  T dataList;
    public int pageno	;	         //  当前页码
    public int pagesize;	       //每页数量
    public int return_code;
    public String return_message;
    public int totalCount;	     //  总记录数
    public int totalPage	;	 //  总页数

}
