package com.hj.casps.quotes;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zy on 2017/4/20.
 * 报价管理的返回类，用于缓存
 */
@Entity
public class QuoteModel implements Parcelable {
    private String id;
    private String goodsName;
    private int rangType = 2;
    private String publishName;
    private String num;
    private String minPrice;
    private String maxPrice;
    private String startTime;
    private String endTime;
    private int status;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static Creator<QuoteModel> getCREATOR() {
        return CREATOR;
    }

    protected QuoteModel(Parcel in) {
        id = in.readString();
        goodsName = in.readString();
        rangType = in.readInt();
        publishName = in.readString();
        num = in.readString();
        minPrice = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        maxPrice = in.readString();
        status = in.readInt();
    }

    @Generated(hash = 1927804045)
    public QuoteModel(String id, String goodsName, int rangType, String publishName,
                      String num, String minPrice, String maxPrice, String startTime,
                      String endTime, int status, int type) {
        this.id = id;
        this.goodsName = goodsName;
        this.rangType = rangType;
        this.publishName = publishName;
        this.num = num;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.type = type;
    }

    @Generated(hash = 1016441114)
    public QuoteModel() {
    }

    public static final Creator<QuoteModel> CREATOR = new Creator<QuoteModel>() {
        @Override
        public QuoteModel createFromParcel(Parcel in) {
            return new QuoteModel(in);
        }

        @Override
        public QuoteModel[] newArray(int size) {
            return new QuoteModel[size];
        }
    };


    @Override
    public String toString() {
        return "QuoteModel{" +
                "id='" + id + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", rangType='" + rangType + '\'' +
                ", publishName='" + publishName + '\'' +
                ", num='" + num + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", startTime='" + startTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(goodsName);
        parcel.writeInt(rangType);
        parcel.writeString(publishName);
        parcel.writeString(num);
        parcel.writeString(minPrice);
        parcel.writeString(maxPrice);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeInt(status);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getRangType() {
        return this.rangType;
    }

    public void setRangType(int rangType) {
        this.rangType = rangType;
    }

    public String getPublishName() {
        return this.publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
