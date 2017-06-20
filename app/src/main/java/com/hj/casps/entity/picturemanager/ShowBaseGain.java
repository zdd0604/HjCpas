package com.hj.casps.entity.picturemanager;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ShowBaseGain<T> implements Serializable {

    private static final long serialVersionUID = 3097937500749208638L;
    public int return_code;
    public  String return_message;
    public  T dataList;
}
