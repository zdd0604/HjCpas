package com.hj.casps.cooperate;

import java.io.Serializable;

/**
 * Created by zy on 2017/6/3.
 * 关系管理-选择供应商的省份加载数据
 */

public class Dict implements Serializable {
    private Integer id;
    private String text;

    public Dict() {
    }

    public Dict(Integer id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 为什么要重写toString()呢？
     *
     * 因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
     */
    @Override
    public String toString() {
        return text;
    }

}
