package com.hj.casps.entity.appmmbbanaccountcontroller;

import java.io.Serializable;

/**
 * 解析数据
 */
public class QueryMmBankAccountResponse implements Serializable {

    private static final long serialVersionUID = -2744555924619527730L;
    public int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    public int pagecount;//	int	符合条件记录总页数
    public String return_message;

    public QueryMmbBankAccountGain toQueryMmbBankAccountGain() {
        QueryMmbBankAccountGain queryMmbBankAccountGain = new QueryMmbBankAccountGain();
        queryMmbBankAccountGain.return_code = return_code;
        queryMmbBankAccountGain.pagecount = pagecount;
        queryMmbBankAccountGain.return_message = return_message;
        return queryMmbBankAccountGain;
    }
}