package com.hj.casps.entity.picturemanager.request;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ResMmb {


    /**
     * data : {"mmbAddress":"北京市怀柔区渤海镇","mmbFName":"长城商行","mmbId":"testshop001"}
     * return_code : 0
     * return_message : 成功
     */

    private DataBean data;
    private int return_code;
    private String return_message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * mmbAddress : 北京市怀柔区渤海镇
         * mmbFName : 长城商行
         * mmbId : testshop001
         */

        private String mmbAddress;
        private String mmbFName;
        private String mmbId;

        public String getMmbAddress() {
            return mmbAddress;
        }

        public void setMmbAddress(String mmbAddress) {
            this.mmbAddress = mmbAddress;
        }

        public String getMmbFName() {
            return mmbFName;
        }

        public void setMmbFName(String mmbFName) {
            this.mmbFName = mmbFName;
        }

        public String getMmbId() {
            return mmbId;
        }

        public void setMmbId(String mmbId) {
            this.mmbId = mmbId;
        }
    }
}
