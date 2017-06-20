package com.hj.casps.entity.picturemanager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ShowBaseEntity {


    /**
     * dataList : [{"baseId":"4","baseName":"临时素材库"},{"baseId":"19","baseName":"平台素材库"}]
     * return_code : 0
     * return_message : 成功
     */

    private int return_code;
    private String return_message;
    private List<DataListBean> dataList;

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

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * baseId : 4
         * baseName : 临时素材库
         */

        private String baseId;
        private String baseName;

        public String getBaseId() {
            return baseId;
        }

        public void setBaseId(String baseId) {
            this.baseId = baseId;
        }

        public String getBaseName() {
            return baseName;
        }

        public void setBaseName(String baseName) {
            this.baseName = baseName;
        }
    }
}
