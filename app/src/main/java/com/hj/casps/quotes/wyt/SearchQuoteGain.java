package com.hj.casps.quotes.wyt;

import com.hj.casps.common.Constant;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/17.
 */

public class SearchQuoteGain<T> implements Serializable {
    private static final long serialVersionUID = 8717492228326744142L;
    public int length;
    public int pageNo;
    public int  qtCount;
    public T qtList;
    public int return_code;
    public String return_message;


}
