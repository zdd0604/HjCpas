package com.hj.casps.entity.appgoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhonglian on 2017/7/15.
 * 功能描述：查询所有单位列表list
 */

public class SelectAllunitNameJson implements Serializable {
    private static final long serialVersionUID = 263369006136346025L;

    /**
     * allunitList : [{"id":"通用:箱","unitAbbr":"box","unitIndex":1001,"unitName":"箱","unitRate":1,"unitType":"通用"},{"id":"通用:袋","unitAbbr":"package","unitIndex":1002,"unitName":"袋","unitRate":1,"unitType":"通用"},{"id":"通用:次","unitAbbr":"time","unitIndex":1003,"unitName":"次","unitRate":1,"unitType":"通用"},{"id":"重量:克","unitAbbr":"g","unitIndex":1101,"unitName":"克","unitRate":1,"unitType":"重量"},{"id":"重量:千克","unitAbbr":"kg","unitIndex":1102,"unitName":"千克","unitRate":1000,"unitType":"重量"},{"id":"重量:市斤","unitAbbr":"500g","unitIndex":1103,"unitName":"市斤","unitRate":500,"unitType":"重量"},{"id":"容积:毫升","unitAbbr":"ml","unitIndex":1201,"unitName":"毫升","unitRate":1,"unitType":"容积"},{"id":"容积:升","unitAbbr":"l","unitIndex":1202,"unitName":"升","unitRate":1000,"unitType":"容积"},{"id":"容积:立方米","unitAbbr":"m3","unitIndex":1203,"unitName":"立方米","unitRate":1000000,"unitType":"容积"},{"id":"长度:毫米","unitAbbr":"mm","unitIndex":1301,"unitName":"毫米","unitRate":1,"unitType":"长度"},{"id":"长度:米","unitAbbr":"m","unitIndex":1302,"unitName":"米","unitRate":1000,"unitType":"长度"},{"id":"长度:寸","unitAbbr":"cun","unitIndex":1303,"unitName":"寸","unitRate":33,"unitType":"长度"},{"id":"长度:尺","unitAbbr":"chi","unitIndex":1304,"unitName":"尺","unitRate":333,"unitType":"长度"},{"id":"时间:秒","unitAbbr":"sec","unitIndex":1501,"unitName":"秒","unitRate":1,"unitType":"时间"},{"id":"时间:分","unitAbbr":"min","unitIndex":1502,"unitName":"分","unitRate":60,"unitType":"时间"},{"id":"时间:小时","unitAbbr":"hour","unitIndex":1503,"unitName":"小时","unitRate":3600,"unitType":"时间"},{"id":"时间:日","unitAbbr":"day","unitIndex":1504,"unitName":"日","unitRate":1,"unitType":"日期"},{"id":"时间:月","unitAbbr":"month","unitIndex":1505,"unitName":"月","unitRate":30,"unitType":"日期"},{"id":"时间:年","unitAbbr":"year","unitIndex":1506,"unitName":"年","unitRate":365,"unitType":"日期"}]
     * return_code : 0
     * return_message : 成功
     */

    private int return_code;
    private String return_message;
    private List<AllunitListBean> allunitList;

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

    public List<AllunitListBean> getAllunitList() {
        return allunitList;
    }

    public void setAllunitList(List<AllunitListBean> allunitList) {
        this.allunitList = allunitList;
    }

    public static class AllunitListBean {
        /**
         * id : 通用:箱
         * unitAbbr : box
         * unitIndex : 1001
         * unitName : 箱
         * unitRate : 1
         * unitType : 通用
         */

        private String id;
        private String unitAbbr;
        private int unitIndex;
        private String unitName;
        private int unitRate;
        private String unitType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnitAbbr() {
            return unitAbbr;
        }

        public void setUnitAbbr(String unitAbbr) {
            this.unitAbbr = unitAbbr;
        }

        public int getUnitIndex() {
            return unitIndex;
        }

        public void setUnitIndex(int unitIndex) {
            this.unitIndex = unitIndex;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public int getUnitRate() {
            return unitRate;
        }

        public void setUnitRate(int unitRate) {
            this.unitRate = unitRate;
        }

        public String getUnitType() {
            return unitType;
        }

        public void setUnitType(String unitType) {
            this.unitType = unitType;
        }
    }
}
