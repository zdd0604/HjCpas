package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 获取当前会员的操作员列表（获取 操作员管理 页面所需数据）
 * 返回参数：map结构的json字串
 */

public class QueryUserRespon<T> implements Serializable {
    private static final long serialVersionUID = 2194249285412009534L;
    public int return_code;//int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//	string	结果提示文本
    public int pageNo;//	 int   	当前页码
    public int pageSize;//	 int   		每页数量
    public int totalCount;//	 int  	总记录数
    public int totalPage;//	 int   	总页数
    public T dataList;
}
