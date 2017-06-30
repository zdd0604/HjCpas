package com.hj.casps.entity.appQuote;

import java.io.Serializable;

/**
 * Created by Admin on 2017/6/30.
 */

public class AddMmbIdsRetutnEntity implements Serializable {
    private static final long serialVersionUID = 240014128704286835L;
    /**
     * map : {"successMsg":"添加会员成功！"}
     * return_code : 0
     * return_message : success
     */

    private MapBean map;
    private int return_code;
    private String return_message;

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

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

    public static class MapBean {
        /**
         * successMsg : 添加会员成功！
         */

        private String successMsg;

        public String getSuccessMsg() {
            return successMsg;
        }

        public void setSuccessMsg(String successMsg) {
            this.successMsg = successMsg;
        }
    }
}
