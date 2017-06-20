package com.hj.casps.entity.goodsmanager;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Administrator on 2017/5/8.
 */

public class SelectPicture02ListEntity implements Parcelable {
    //缩略图
    private String imagePath;
    //素材名称
    private String imageName;
    private boolean isCheck;
    private String imgId;
    //原图
    private String  materialPath;

    public String getMaterialPath() {
        return materialPath;
    }

    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public static final Creator<SelectPicture02ListEntity> CREATOR=new Creator<SelectPicture02ListEntity>() {
        @Override
        public SelectPicture02ListEntity createFromParcel(Parcel source) {
            return new SelectPicture02ListEntity(source);
        }

        @Override
        public SelectPicture02ListEntity[] newArray(int size) {
            return new SelectPicture02ListEntity[0];
        }
    };


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imagePath);
        dest.writeString(this.imageName);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
    }

    public SelectPicture02ListEntity() {
    }

    protected SelectPicture02ListEntity(Parcel in) {
        this.imagePath = in.readString();
        this.imageName = in.readString();
        this.isCheck = in.readByte() != 0;
    }
}
