package com.hj.casps.entity.picturemanager.request;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ResMark {
    private int return_code;//  int	    结果码，0 成功， 1 失败
    private String return_message;// string	结果提示文本（2已关注，1关注成功）

    private int success_num;// int  关注成功数量
    private int failed_num;// int  关注失败数量
    private int marked_num;// int  已关注数量

    public int getReturn_code() {
        return return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public int getSuccess_num() {
        return success_num;
    }

    public int getFailed_num() {
        return failed_num;
    }

    public int getMarked_num() {
        return marked_num;
    }
}
