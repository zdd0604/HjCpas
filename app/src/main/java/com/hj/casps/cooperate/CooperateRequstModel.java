package com.hj.casps.cooperate;

/**
 * Created by zy on 2017/4/21.
 * 关系管理-待审批申请返回类
 */

public class CooperateRequstModel {
    private String name;
    private String relation;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
