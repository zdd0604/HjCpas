package com.hj.casps.entity.appsettle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/5.
 * 获取我的提交的待对方审批结款单列表 返回参数
 * 获取收/付款方银行账号
 */

public class QueryMyPendingSttleRespon<T> implements Serializable {
    private static final long serialVersionUID = -3828414138703901908L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public String return_message;//string	结果提示文本
    public int pagecount;//	int	符合条件记录总页数
    public T list;
}
