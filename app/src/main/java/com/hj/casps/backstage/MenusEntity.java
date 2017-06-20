package com.hj.casps.backstage;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */

public class MenusEntity<T> implements Serializable {

    public T menus;
    public int return_code;
    public String return_message;

}
