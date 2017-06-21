package com.hj.casps.entity.appnemberrelationship;

/**
 * 详单返回的参数
 */
public class BackDetail {

    /**
     * data : {"buyBiz":"1","mmbaddress":"北京市朝阳区亚运村路18号","mmbfname":"APP测试用企业","mmbhomepage":"","sellBiz":"1"}
     * return_code : 0
     * return_message : 成功!
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

    public class DataBean {
        /**
         * buyBiz : 1
         * mmbaddress : 北京市朝阳区亚运村路18号
         * mmbfname : APP测试用企业
         * mmbhomepage :
         * sellBiz : 1
         */

        private String buyBiz;
        private String mmbaddress;
        private String mmbfname;
        private String mmbhomepage;
        private String sellBiz;

        public String getBuyBiz() {
            return buyBiz;
        }

        public void setBuyBiz(String buyBiz) {
            this.buyBiz = buyBiz;
        }

        public String getMmbaddress() {
            return mmbaddress;
        }

        public void setMmbaddress(String mmbaddress) {
            this.mmbaddress = mmbaddress;
        }

        public String getMmbfname() {
            return mmbfname;
        }

        public void setMmbfname(String mmbfname) {
            this.mmbfname = mmbfname;
        }

        public String getMmbhomepage() {
            return mmbhomepage;
        }

        public void setMmbhomepage(String mmbhomepage) {
            this.mmbhomepage = mmbhomepage;
        }

        public String getSellBiz() {
            return sellBiz;
        }

        public void setSellBiz(String sellBiz) {
            this.sellBiz = sellBiz;
        }
    }
}