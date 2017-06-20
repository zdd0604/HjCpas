package com.hj.casps.entity.appordergoods;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/4.
 * 编辑地址确认提交 后台返回的JSON实体
 * <p>
 * 共用的返回接口 ：
 * 编辑地址确认提交、
 * 删除地址、
 * 添加新地址确认提交、
 * 收货操作提交、
 * 发货操作提交、
 * 退货操作提交、
 * 签收退货操作、
 * 待审批结款单-编辑提交、
 * 待审批结款单-同意、
 * 待审批结款单-拒绝、
 * 执行中的结款单-请求终止、
 * 行中的结款单-撤回终止请求、
 * 执行中的结款单-同意终止请求、
 * 结款单登记为担保资源操作、
 * 新建结款单
 */

public class UpdateMmbWarehouseGain implements Serializable {
    private static final long serialVersionUID = -6713205843293109081L;
    private int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    private String return_message;//string	结果提示文本
    private String msg;//	string	成功或失败条数

    public UpdateMmbWarehouseGain(int return_code, String return_message, String msg) {
        this.return_code = return_code;
        this.return_message = return_message;
        this.msg = msg;
    }

    public UpdateMmbWarehouseGain() {
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
