package com.hj.casps.entity.goodsmanager.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */

public class GoodtoUpdateGain<T> implements Serializable {

    private static final long serialVersionUID = -8941299580353977259L;
    public int return_code;
    public String return_message;
    public T goodsInfo;
    public List<String> goodsImages;

}
