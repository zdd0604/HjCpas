package com.hj.casps.entity.appmmbbanaccountcontroller;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/8.
 * 功能描述：银行账号管理-列表
 * 返回参数：Map结构的JSON字符串
 */

public class QueryMmbBankAccountGain<T> implements Serializable {
    private static final long serialVersionUID = -6031916444438073924L;

    public int pagecount;//	int	符合条件记录总页数
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;
    public T list;
}
