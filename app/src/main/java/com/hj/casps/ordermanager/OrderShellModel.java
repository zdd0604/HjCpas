package com.hj.casps.ordermanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zy on 2017/4/27.
 * 订单的返回类，继承了Parcelable，便于传递
 */

public class OrderShellModel implements Parcelable {
    private String name;//商品名
    private String goodsId;//商品ID
    private String quoteId;//报价ID
    private String price;//商品价格
    private String finalprice;//该商品的合计价格
    private String allprice;//最后的总价
    private String size;//商品型号，后来取消了这个参数
    private String categoryId;//商品品类ID
    private String id;//商品品类ID
    private int num;//商品数量
    private int no;//商品的no，为了删除商品使用
    private double minPrice = 0.0;//最小单价
    private double maxPrice = 0.0;//最大单价
    private boolean checked;//是否选中该商品
    private boolean status;//是否是指定商品
    private boolean delete;//是否删除该商品


    protected OrderShellModel(Parcel in) {
        name = in.readString();
        goodsId = in.readString();
        quoteId = in.readString();
        price = in.readString();
        finalprice = in.readString();
        allprice = in.readString();
        size = in.readString();
        categoryId = in.readString();
        id = in.readString();
        num = in.readInt();
        no = in.readInt();
        minPrice = in.readDouble();
        maxPrice = in.readDouble();
        checked = in.readByte() != 0;
        status = in.readByte() != 0;
        delete = in.readByte() != 0;
    }

    public static final Creator<OrderShellModel> CREATOR = new Creator<OrderShellModel>() {
        @Override
        public OrderShellModel createFromParcel(Parcel in) {
            return new OrderShellModel(in);
        }

        @Override
        public OrderShellModel[] newArray(int size) {
            return new OrderShellModel[size];
        }
    };

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getAllprice() {
        return allprice;
    }

    public void setAllprice(String allprice) {
        this.allprice = allprice;
    }

    public String getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(String finalprice) {
        this.finalprice = finalprice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public OrderShellModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



    @Override
    public String toString() {
        return "OrderShellModel{" +
                "name='" + name + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", quoteId='" + quoteId + '\'' +
                ", price='" + price + '\'' +
                ", finalprice='" + finalprice + '\'' +
                ", allprice='" + allprice + '\'' +
                ", size='" + size + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", num=" + num +
                ", no=" + no +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", checked=" + checked +
                ", status=" + status +
                ", delete=" + delete +
                '}';
    }

    public  void clearDatas(){
        finalprice = "";
        allprice = "";
        minPrice = 0.0;
        maxPrice = 0.0;
        num = 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(goodsId);
        parcel.writeString(quoteId);
        parcel.writeString(price);
        parcel.writeString(finalprice);
        parcel.writeString(allprice);
        parcel.writeString(size);
        parcel.writeString(categoryId);
        parcel.writeString(id);
        parcel.writeInt(num);
        parcel.writeInt(no);
        parcel.writeDouble(minPrice);
        parcel.writeDouble(maxPrice);
        parcel.writeByte((byte) (checked ? 1 : 0));
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeByte((byte) (delete ? 1 : 0));
    }
}
