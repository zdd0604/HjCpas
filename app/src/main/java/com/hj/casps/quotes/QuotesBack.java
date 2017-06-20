package com.hj.casps.quotes;

import java.util.List;

/**
 * Created by zy on 2017/5/7.
 * 报价管理的返回参数
 */

public class QuotesBack {
    private int return_code;
    private String return_message;
    private int qtCount;
    private List<QuoteModel> qtList;

    public int getQtCount() {
        return qtCount;
    }

    public void setQtCount(int qtCount) {
        this.qtCount = qtCount;
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

    public List<QuoteModel> getQtList() {
        return qtList;
    }

    public void setQtList(List<QuoteModel> qtList) {
        this.qtList = qtList;
    }
}
