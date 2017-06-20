package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 编辑操作员页面（获得启动编辑操作员页面所需数据）
 */

public class ToEditUserPageRespon<T> implements Serializable {
    private static final long serialVersionUID = 7488040345891984135L;
    public int return_code;//int	结果码，0 停用成功，101 无权限，201 数据库错误
    public String return_message;//string	结果提示文本
    public String sys_uuid;//string	防止表单提交UUID
    public ToEditUserPageEntity userInfo;
    public T allRoles;
}
