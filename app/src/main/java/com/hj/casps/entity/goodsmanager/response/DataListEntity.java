package com.hj.casps.entity.goodsmanager.response;

import java.io.Serializable;

/**图片库列表的实体
 * Created by Administrator on 2017/5/12.
 */

public class DataListEntity  implements  Serializable {


    private static final long serialVersionUID = -1964013042002389260L;

    /**
     * goodsId : 487fdc3f516344f3ac21a0da749b84d5
     * imgPath : /v2content/upload/img/newImg/UM/min/81b4107e65304290a361e907c51d9f41.jpg
     * name : 小米
     * status : 1
     */

    private String goodsId;
    private String imgPath;
    private String name;
    private int status;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
