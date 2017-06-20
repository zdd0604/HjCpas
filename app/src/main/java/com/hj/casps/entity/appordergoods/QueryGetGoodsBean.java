package com.hj.casps.entity.appordergoods;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Admin on 2017/5/12.
 * 保存收货列表数据库接口
 */
@Entity
public class QueryGetGoodsBean implements Serializable {
    private static final long serialVersionUID = -8661174973521275801L;


    /**
     * address_list : [{"address":"ndnxmckckkc","id":"4497b5b187ee4ce5ab689e4a5e9632aa"},{"address":"jjxjx","id":"4f005444a10b42c694d795f0e716eea4"},{"address":"ssdd","id":"55c75461839b46f0a690efa2c9a6c6f6"},{"address":"app test 2","id":"7fda1ceb40874b9aa7f8008487ac354e"},{"address":"njzxjxjjc","id":"824dbad1dcd6489585233afe82145dd9"},{"address":"XP二敏玩LOL","id":"85f08801413d4210a1d2841990435366"},{"address":"nnnkzkxk","id":"97bfac6c26254e61ae4136661bc765fa"},{"address":"bxjxjxjxn","id":"ae6876e8dae14bb7b3c073a079ea0258"},{"address":"jxjkxxkkckc","id":"b1c29640f8c74582a6b24b1f93dccaed"},{"address":"来咯弄12","id":"d37145830b9546b3b5cb792226c3c0d2"},{"address":"合肥","id":"f231868a67b3466e931cc68ef820e3d2"}]
     * exe_getgoods_num : 1975
     * getgoods_num : 1025
     * good_sname : 散装农场青菜
     * goods_num : 3000
     * orderId : 2f364bdfb99746eabed89f6a4715a394
     * ordertitleCode : ea9cdbd8c4bf40bea46794b1627ab351
     * ordertitleNumber : 10038443
     * sellers_name : 长城商行
     */

    private int exe_getgoods_num;
    private int getgoods_num;
    private String good_sname;
    private int goods_num;
    private String orderId;
    private String ordertitleCode;
    private int ordertitleNumber;
    private String sellers_name;
    private String address_list;
    @Generated(hash = 527976027)
    public QueryGetGoodsBean(int exe_getgoods_num, int getgoods_num, String good_sname, int goods_num, String orderId, String ordertitleCode, int ordertitleNumber, String sellers_name, String address_list) {
        this.exe_getgoods_num = exe_getgoods_num;
        this.getgoods_num = getgoods_num;
        this.good_sname = good_sname;
        this.goods_num = goods_num;
        this.orderId = orderId;
        this.ordertitleCode = ordertitleCode;
        this.ordertitleNumber = ordertitleNumber;
        this.sellers_name = sellers_name;
        this.address_list = address_list;
    }
    @Generated(hash = 1301866548)
    public QueryGetGoodsBean() {
    }
    public int getExe_getgoods_num() {
        return this.exe_getgoods_num;
    }
    public void setExe_getgoods_num(int exe_getgoods_num) {
        this.exe_getgoods_num = exe_getgoods_num;
    }
    public int getGetgoods_num() {
        return this.getgoods_num;
    }
    public void setGetgoods_num(int getgoods_num) {
        this.getgoods_num = getgoods_num;
    }
    public String getGood_sname() {
        return this.good_sname;
    }
    public void setGood_sname(String good_sname) {
        this.good_sname = good_sname;
    }
    public int getGoods_num() {
        return this.goods_num;
    }
    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }
    public String getOrderId() {
        return this.orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrdertitleCode() {
        return this.ordertitleCode;
    }
    public void setOrdertitleCode(String ordertitleCode) {
        this.ordertitleCode = ordertitleCode;
    }
    public int getOrdertitleNumber() {
        return this.ordertitleNumber;
    }
    public void setOrdertitleNumber(int ordertitleNumber) {
        this.ordertitleNumber = ordertitleNumber;
    }
    public String getSellers_name() {
        return this.sellers_name;
    }
    public void setSellers_name(String sellers_name) {
        this.sellers_name = sellers_name;
    }
    public String getAddress_list() {
        return this.address_list;
    }
    public void setAddress_list(String address_list) {
        this.address_list = address_list;
    }

}
