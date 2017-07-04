package com.hj.casps.entity.apporder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

//返回类
public class BuyCartBack {

    /**
     * list : [{"listGoods":[{"categoryId":"1002002001","goodsId":"ae378bc20f3b4f679096a21c1302584e","goodsName":"水果","id":"30038608","maxPrice":300,"minPrice":30,"mmbName":"cyh"}],"mmbId":"da4383de72494f5d98dc7836d25f526f","mmbName":"cyh"}]
     * return_code : 0
     * return_message : success
     */

    private int return_code;
    private String return_message;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * listGoods : [{"categoryId":"1002002001","goodsId":"ae378bc20f3b4f679096a21c1302584e","goodsName":"水果","id":"30038608","maxPrice":300,"minPrice":30,"mmbName":"cyh"}]
         * mmbId : da4383de72494f5d98dc7836d25f526f
         * mmbName : cyh
         */

        private String mmbId;
        private String mmbName;
        private List<ListGoodsBean> listGoods;

        protected ListBean(Parcel in) {
            mmbId = in.readString();
            mmbName = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public String getMmbId() {
            return mmbId;
        }

        public void setMmbId(String mmbId) {
            this.mmbId = mmbId;
        }

        public String getMmbName() {
            return mmbName;
        }

        public void setMmbName(String mmbName) {
            this.mmbName = mmbName;
        }

        public List<ListGoodsBean> getListGoods() {
            return listGoods;
        }

        public void setListGoods(List<ListGoodsBean> listGoods) {
            this.listGoods = listGoods;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(mmbId);
            parcel.writeString(mmbName);
        }

        public static class ListGoodsBean implements Parcelable {//返回网络请求后的购物车商品
            /**
             * categoryId : 1002002001
             * goodsId : ae378bc20f3b4f679096a21c1302584e
             * goodsName : 水果
             * id : 30038608
             * maxPrice : 300
             * minPrice : 30
             * mmbName : cyh
             */

            private String categoryId;//品类id
            private String goodsId;//商品id
            private String goodsName;//商品名
            private String id;//报价id
            private int no;//设置编号
            private double maxPrice;//最高价格
            private double minPrice;//最低价格
            private String mmbName;//供应商名称
            private String mateId;//是否是指定商品

            protected ListGoodsBean(Parcel in) {
                categoryId = in.readString();
                goodsId = in.readString();
                goodsName = in.readString();
                id = in.readString();
                no = in.readInt();
                maxPrice = in.readDouble();
                minPrice = in.readDouble();
                mmbName = in.readString();
                mateId = in.readString();
            }

            public static final Creator<ListGoodsBean> CREATOR = new Creator<ListGoodsBean>() {
                @Override
                public ListGoodsBean createFromParcel(Parcel in) {
                    return new ListGoodsBean(in);
                }

                @Override
                public ListGoodsBean[] newArray(int size) {
                    return new ListGoodsBean[size];
                }
            };

            public int getNo() {
                return no;
            }

            public void setNo(int no) {
                this.no = no;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(double maxPrice) {
                this.maxPrice = maxPrice;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public String getMmbName() {
                return mmbName;
            }

            public void setMmbName(String mmbName) {
                this.mmbName = mmbName;
            }

            public String getMateId() {
                return mateId;
            }

            public void setMateId(String mateId) {
                this.mateId = mateId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(categoryId);
                parcel.writeString(goodsId);
                parcel.writeString(goodsName);
                parcel.writeString(id);
                parcel.writeInt(no);
                parcel.writeDouble(maxPrice);
                parcel.writeDouble(minPrice);
                parcel.writeString(mmbName);
                parcel.writeString(mateId);
            }
        }
    }
}