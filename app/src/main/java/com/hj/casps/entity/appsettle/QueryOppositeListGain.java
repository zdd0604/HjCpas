package com.hj.casps.entity.appsettle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/5.
 * 获取收/付款方列表 返回参数
 */

public class QueryOppositeListGain implements Serializable {
    private static final long serialVersionUID = -7333718127990316013L;
    private int return_code;//	int	结果码，0 成功，101 无权限，201 数据库错误
    private String return_message;//string	结果提示文本
    private List<OppositeListEntity> mList;

    public QueryOppositeListGain() {
    }

    public QueryOppositeListGain(int return_code, String return_message, List<OppositeListEntity> mList) {
        this.return_code = return_code;
        this.return_message = return_message;
        this.mList = mList;
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

    public List<OppositeListEntity> getmList() {
        return mList;
    }

    public void setmList(List<OppositeListEntity> mList) {
        this.mList = mList;
    }

    public static class OppositeListEntity implements Serializable {

        private static final long serialVersionUID = 915837105683659626L;
        private String sellersId;//	string		收款方id(本方付款时采用)
        private String sellersName;//string	收款方名称(本方付款时采用)
        private String buyersId;//	string	付款方id(对方付款时采用)
        private String buyersName;//string	付款方名称(对方付款时采用)

        public OppositeListEntity(String sellersId, String sellersName, String buyersId, String buyersName) {
            this.sellersId = sellersId;
            this.sellersName = sellersName;
            this.buyersId = buyersId;
            this.buyersName = buyersName;
        }

        public OppositeListEntity() {
        }

        public String getSellersId() {
            return sellersId;
        }

        public void setSellersId(String sellersId) {
            this.sellersId = sellersId;
        }

        public String getSellersName() {
            return sellersName;
        }

        public void setSellersName(String sellersName) {
            this.sellersName = sellersName;
        }

        public String getBuyersId() {
            return buyersId;
        }

        public void setBuyersId(String buyersId) {
            this.buyersId = buyersId;
        }

        public String getBuyersName() {
            return buyersName;
        }

        public void setBuyersName(String buyersName) {
            this.buyersName = buyersName;
        }
    }
}
