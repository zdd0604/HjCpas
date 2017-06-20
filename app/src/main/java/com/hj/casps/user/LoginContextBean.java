package com.hj.casps.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/10.
 */

public class LoginContextBean<T> implements Serializable {


    private static final long serialVersionUID = -5192175009027681545L;
    /**
     * Context : {"mmbid":"testshop001","mmbname":"长城商行","userid":"e6ae4ad55d5b44769d2a54a0fedbfff7","username":"长城财务"}
     * return_code : 0
     * return_message : success
     */

    public T Context;
    public int return_code;
    public String return_message;



}
