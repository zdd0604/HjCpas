package com.hj.casps.entity.goodsmanager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/8.
 */

public class PublicDatas02 implements Serializable {


    private int stylePic;
    private  String imgId;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public int getStylePic() {
        return stylePic;
    }

    public void setStylePic(int stylePic) {
        this.stylePic = stylePic;
    }

    private static PublicDatas02 data = null;
    //多选图片
    private ArrayList<SelectPicture02ListEntity> multCheckList;
    //单选图片
    private ArrayList<SelectPicture02ListEntity> singleCheckList;

    private PublicDatas02() {

    }

    public ArrayList<SelectPicture02ListEntity> getMultCheckList() {
        return multCheckList;
    }

    public void setMultCheckList(ArrayList<SelectPicture02ListEntity> multCheckList) {
        this.multCheckList = multCheckList;
    }

    public ArrayList<SelectPicture02ListEntity> getSingleCheckList() {
        return singleCheckList;
    }

    public void setSingleCheckList(ArrayList<SelectPicture02ListEntity> singleCheckList) {
        this.singleCheckList = singleCheckList;
    }

    public static PublicDatas02 getInstance() {

        if (data == null)
            synchronized (PublicDatas02.class) {
                data = new PublicDatas02();
            }
        return data;
    }


    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
