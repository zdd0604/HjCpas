package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/15.
 * 获取待审批结款单列表
 */

public class QueryPendingSttleRespon<T> implements Serializable {
    private static final long serialVersionUID = 3701968714596377300L;
    public int return_code;//int	结果码，0 成功，101 无权限，201 数据库错误
    public int pagecount;//int	符合条件记录总页数
    public String return_message;//
    public int total;//
    public T list;
}
