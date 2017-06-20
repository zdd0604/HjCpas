package com.hj.casps.entity.picturemanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ShowDivEntity implements Parcelable{


    private static final long serialVersionUID = 6428654033362714816L;
    /**
     * dataList : [{"divId":"101","divName":"小图","parentdivId":"0"},{"divId":"102","divName":"大图","parentdivId":"0"},{"divId":"1013207","divName":"蔬菜","parentdivId":"101"},{"divId":"101535c","divName":"水果","parentdivId":"101"},{"divId":"10170a2","divName":"肉类","parentdivId":"101"},{"divId":"10163bf","divName":"水产","parentdivId":"101"},{"divId":"101c627","divName":"米类","parentdivId":"101"},{"divId":"1016edb","divName":"面类","parentdivId":"101"},{"divId":"101b590","divName":"杂粮","parentdivId":"101"},{"divId":"101b4bb","divName":"食用油","parentdivId":"101"},{"divId":"101d906","divName":"调料","parentdivId":"101"},{"divId":"101abc5","divName":"蔬菜大图","parentdivId":"101"},{"divId":"1028b83","divName":"水果大图","parentdivId":"102"},{"divId":"102e44b","divName":"肉类大图","parentdivId":"102"},{"divId":"102705a","divName":"水产大图","parentdivId":"102"},{"divId":"10297a6","divName":"米类大图","parentdivId":"102"},{"divId":"102c479","divName":"面类大图","parentdivId":"102"},{"divId":"1025979","divName":"杂粮大图","parentdivId":"102"},{"divId":"1029149","divName":"食用油大图","parentdivId":"102"},{"divId":"102ee80","divName":"调料大图","parentdivId":"102"},{"divId":"103","divName":"其他图片","parentdivId":"0"},{"divId":"1033fbb","divName":"背景图片","parentdivId":"103"},{"divId":"103d8ec","divName":"证书、奖章","parentdivId":"103"}]
     * return_code : 0
     * return_message : 成功
     */

    private int return_code;
    private String return_message;
    private List<DataListBean> dataList;

    protected ShowDivEntity(Parcel in) {
        return_code = in.readInt();
        return_message = in.readString();
    }

    public static final Creator<ShowDivEntity> CREATOR = new Creator<ShowDivEntity>() {
        @Override
        public ShowDivEntity createFromParcel(Parcel in) {
            return new ShowDivEntity(in);
        }

        @Override
        public ShowDivEntity[] newArray(int size) {
            return new ShowDivEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(return_code);
        dest.writeString(return_message);
    }


    public static class DataListBean  implements Parcelable{
        /**
         * divId : 101
         * divName : 小图
         * parentdivId : 0
         */

        private String divId;
        private String divName;
        private String parentdivId;

        protected DataListBean(Parcel in) {
            divId = in.readString();
            divName = in.readString();
            parentdivId = in.readString();
        }

        public static final Creator<DataListBean> CREATOR = new Creator<DataListBean>() {
            @Override
            public DataListBean createFromParcel(Parcel in) {
                return new DataListBean(in);
            }

            @Override
            public DataListBean[] newArray(int size) {
                return new DataListBean[size];
            }
        };

        public String getDivId() {
            return divId;
        }

        public void setDivId(String divId) {
            this.divId = divId;
        }

        public String getDivName() {
            return divName;
        }

        public void setDivName(String divName) {
            this.divName = divName;
        }

        public String getParentdivId() {
            return parentdivId;
        }

        public void setParentdivId(String parentdivId) {
            this.parentdivId = parentdivId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(divId);
            dest.writeString(divName);
            dest.writeString(parentdivId);
        }
    }
}
