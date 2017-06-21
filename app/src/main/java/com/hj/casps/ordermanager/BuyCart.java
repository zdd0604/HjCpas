package com.hj.casps.ordermanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC101100410002;
import static com.hj.casps.common.Constant.SYS_FUNC101100410003;
import static com.hj.casps.common.Constant.getUUID;

//采购拣单车 销售拣单车
public class BuyCart extends ActivityBaseHeader implements View.OnClickListener {

    private TextView buy_cart_info;
    private ListView buy_cart_list;
    private int type;
    private String type_name;
    private List<BuyCartBack.ListBean> orderBuyModels;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_cart);
        initData();
        initView();
    }

    //加载拣单车的数据
    private void initData() {
        BuyCartPost post = null;
        type = getIntent().getIntExtra(Constant.ORDER_TYPE, Constant.order_type_buy);
        switch (type) {
            case Constant.order_type_buy:
                setTitle(getString(R.string.order_buy_cart));
                type_name = getString(R.string.cooperate_buy_part);
                post = new BuyCartPost(publicArg.getSys_token(),
                        getUUID(),
                        SYS_FUNC101100410002,
                        publicArg.getSys_user(),
                        publicArg.getSys_name(),
                        publicArg.getSys_member(),
                        "1");

                break;
            case Constant.order_type_sell:
                setTitle(getString(R.string.order_sell_cart));
                type_name = getString(R.string.cooperate_buy_role);
                post = new BuyCartPost(publicArg.getSys_token(),
                        getUUID(),
                        SYS_FUNC101100410003,
                        publicArg.getSys_user(),
                        publicArg.getSys_name(),
                        publicArg.getSys_member(),
                        "0");

                break;

        }
        OkGo.post(Constant.SearchSHPCUrl)
                .params("param", mGson.toJson(post))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        BuyCartBack backDetail = mGson.fromJson(s, BuyCartBack.class);
                        if (backDetail == null) {
                            return;
                        }
                        if (backDetail.getReturn_code() != 0) {
                            Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                        }
                        else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(BuyCart.this);
                        }

                        else {
                            orderBuyModels = backDetail.getList();
                            if (orderBuyModels.isEmpty()) {
//                                Toast.makeText(context, "没有订单", Toast.LENGTH_SHORT).show();
                                adapter.removeAll();
                            }
                            int no = 0;
                            for (int i = 0; i < orderBuyModels.size(); i++) {
                                for (int i1 = 0; i1 < orderBuyModels.get(i).getListGoods().size(); i1++) {
                                    orderBuyModels.get(i).getListGoods().get(i1).setNo(no);
                                    no++;
                                }
                            }
                            adapter.updateRes(orderBuyModels);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//        orderBuyModels = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            OrderBuyModel orderBuyModel = new OrderBuyModel();
//            orderBuyModel.setName("长城商行" + String.valueOf(i + 1));
//            orderBuyModel.setType(type_name);
//            orderBuyModel.setContents("散装农场青菜        冷冻大肉        葵花油       五常大米       中国");
//            orderBuyModel.setNum(i + 20);
//            orderBuyModels.add(orderBuyModel);
//
//        }

    }

    //加载页面
    private void initView() {
        setTitleRight(null, null);
        buy_cart_info = (TextView) findViewById(R.id.buy_cart_info);
        buy_cart_list = (ListView) findViewById(R.id.buy_cart_list);
        adapter = new OrderAdapter(orderBuyModels, this, R.layout.item_buy_cart);
        buy_cart_list.setAdapter(adapter);
        buy_cart_info.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy_cart_info:
                if (type == 0) {
                    CreateDialog(Constant.DIALOG_CONTENT_23);

                } else {
                    CreateDialog(Constant.DIALOG_CONTENT_25);

                }
                break;
        }
    }

    //请求类
    public static class BuyCartPost {
        private String sys_token;
        private String sys_uuid;
        private String sys_func;
        private String sys_user;
        private String sys_name;
        private String sys_member;
        private String type;
        private String index;

        public BuyCartPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String type) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.type = type;
        }

        public BuyCartPost(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String type, String index) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.type = type;
            this.index = index;
        }
    }

    //返回类
    public static class BuyCartBack {

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

            public static class ListGoodsBean implements Parcelable {
                /**
                 * categoryId : 1002002001
                 * goodsId : ae378bc20f3b4f679096a21c1302584e
                 * goodsName : 水果
                 * id : 30038608
                 * maxPrice : 300
                 * minPrice : 30
                 * mmbName : cyh
                 */

                private String categoryId;
                private String goodsId;
                private String goodsName;
                private String id;
                private int no;
                private double maxPrice;
                private double minPrice;
                private String mmbName;

                protected ListGoodsBean(Parcel in) {
                    categoryId = in.readString();
                    goodsId = in.readString();
                    goodsName = in.readString();
                    id = in.readString();
                    no = in.readInt();
                    maxPrice = in.readDouble();
                    minPrice = in.readDouble();
                    mmbName = in.readString();
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
                }
            }
        }
    }

    //适配器
    public class OrderAdapter extends WZYBaseAdapter<BuyCartBack.ListBean> {
        private Context context;

        public OrderAdapter(List<BuyCartBack.ListBean> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
            this.context = context;
        }

        @Override
        public void bindData(WZYBaseAdapter.ViewHolder holder, final BuyCartBack.ListBean orderBuyModel, final int indexPos) {
            TextView type = (TextView) holder.getView(R.id.text_name_buy_cart);
            type.setText(type_name);
            TextView name = (TextView) holder.getView(R.id.order_buy_name);
            name.setText(orderBuyModel.getMmbName());
            final TextView contents = (TextView) holder.getView(R.id.buy_cart_info_contents);
            String contents_cart = "";
            for (int i = 0; i < orderBuyModel.getListGoods().size(); i++) {
                contents_cart = contents_cart + orderBuyModel.getListGoods().get(i).getGoodsName() + "        ";
            }
            contents.setText(contents_cart);
            TextView num = (TextView) holder.getView(R.id.order_buy_num);
            num.setText(String.valueOf(orderBuyModel.getListGoods().size()));
            RelativeLayout buy_cart_contents_rl = (RelativeLayout) holder.getView(R.id.buy_cart_contents_rl);
            buy_cart_contents_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BuyShell.class);
                    intent.putExtra("buy_type", type_name);
                    intent.putExtra("buy_name", orderBuyModel.getMmbName());
                    intent.putExtra("buy_id", orderBuyModel.getMmbId());
                    intent.putParcelableArrayListExtra("buy_list", (ArrayList<? extends Parcelable>) orderBuyModel.getListGoods());
                    intent.putExtra("buy_num", orderBuyModel.getListGoods().size());
                    startActivityForResult(intent, Constant.START_ACTIVITY_TYPE);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == RESULT_OK) {
            initData();
        }
    }
}
