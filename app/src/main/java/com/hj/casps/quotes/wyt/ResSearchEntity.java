package com.hj.casps.quotes.wyt;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ResSearchEntity {


    /**
     * list : []
     * pagecount : 0
     * return_code : 0
     * return_message : 成功!
     */

    private int pagecount;
    private int return_code;
    private String return_message;
    private List<String> list;

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
