package com.hj.casps.app;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/15.
 */
public class ApiList implements Serializable {
    public final static String Index_captcha="Index_captcha";
    public final static String Login_index="Login_index";

    private JSONObject jsonObject;

    public ApiList(String jsonString) {
        try {
            JSONObject jo = JSONObject.parseObject(jsonString);
            Integer error = jo.getInteger("error");
            if (error != null && error == 0) {
                JSONObject data = jo.getJSONObject("data");
                this.jsonObject = data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getItemUrl(String itemKey) {
        if (jsonObject != null) {
            return (String) jsonObject.get(itemKey);
        }
        return null;
    }
}
