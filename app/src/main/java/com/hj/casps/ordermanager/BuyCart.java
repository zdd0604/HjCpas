package com.hj.casps.ordermanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.hj.casps.entity.appOrder.BuyCartBack;
import com.hj.casps.entity.appOrder.BuyCartPost;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC;
import static com.hj.casps.common.Constant.getUUID;

//采购拣单车 销售拣单车
public class BuyCart extends ActivityBaseHeader implements View.OnClickListener {

    private TextView buy_cart_info;//操作说明
    private ListView buy_cart_list;//购物车list
    private int type;//采购是0，销售是1
    private String type_name;//设置文字，采购方，销售方
    private List<BuyCartBack.ListBean> orderBuyModels;//从网络上获取购物车后的返回list
    private OrderAdapter adapter;//购物车adapter

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
        type = getIntent().getIntExtra(Constant.ORDER_TYPE, Constant.order_type_buy);//从外面传递过来是采购还是销售
        switch (type) {
            case Constant.order_type_buy://如果是采购
                setTitle(getString(R.string.order_buy_cart));//设置标题
                type_name = getString(R.string.cooperate_buy_part);//设置采购方还是销售方文字
                post = new BuyCartPost(publicArg.getSys_token(),//请求，注意这里采购提交1，销售提交0
                        getUUID(),
                        SYS_FUNC,
                        publicArg.getSys_user(),
                        publicArg.getSys_name(),
                        publicArg.getSys_member(),
                        "1");

                break;
            case Constant.order_type_sell://销售拣单车请求，同采购
                setTitle(getString(R.string.order_sell_cart));
                type_name = getString(R.string.cooperate_buy_role);
                post = new BuyCartPost(publicArg.getSys_token(),
                        getUUID(),
                        SYS_FUNC,
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
                        if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(BuyCart.this);
                        }
                        else if (backDetail.getReturn_code() != 0) {
                            Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            orderBuyModels = backDetail.getList();
                            if (orderBuyModels.isEmpty()) {
//                                Toast.makeText(context, "没有订单", Toast.LENGTH_SHORT).show();
                                adapter.removeAll();//订单为空的时候把adapter的数据清空
                            }
                            int no = 0;//指定一个no，为所有商品排个序
                            for (int i = 0; i < orderBuyModels.size(); i++) {
                                for (int i1 = 0; i1 < orderBuyModels.get(i).getListGoods().size(); i1++) {
                                    orderBuyModels.get(i).getListGoods().get(i1).setNo(no);
                                    no++;
                                }
                            }
                            adapter.updateRes(orderBuyModels);//刷新adapter
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

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
            case R.id.buy_cart_info://操作说明
                if (type == 0) {
                    CreateDialog(Constant.DIALOG_CONTENT_23);

                } else {
                    CreateDialog(Constant.DIALOG_CONTENT_25);

                }
                break;
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
            TextView type = (TextView) holder.getView(R.id.text_name_buy_cart);//采购方还是销售方
            type.setText(type_name);
            TextView name = (TextView) holder.getView(R.id.order_buy_name);//供应商名称
            name.setText(orderBuyModel.getMmbName());
            final TextView contents = (TextView) holder.getView(R.id.buy_cart_info_contents);//商品列表，空格隔开
            String contents_cart = "";
            for (int i = 0; i < orderBuyModel.getListGoods().size(); i++) {
                contents_cart = contents_cart + orderBuyModel.getListGoods().get(i).getGoodsName() + "        ";//遍历该供应商下的商品列表
            }
            contents.setText(contents_cart);
            TextView num = (TextView) holder.getView(R.id.order_buy_num);//商品数量（共有多少类商品）
            num.setText(String.valueOf(orderBuyModel.getListGoods().size()));
            RelativeLayout buy_cart_contents_rl = (RelativeLayout) holder.getView(R.id.buy_cart_contents_rl);//加载商品列表的layout
            buy_cart_contents_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//点击layout的事件，传递采购销售类型，供应商id，名称，商品种类数量，商品列表
                    Intent intent = new Intent(context, BuyShell.class);
                    intent.putExtra("buy_type", type_name);
                    intent.putExtra("buy_name", orderBuyModel.getMmbName());
                    intent.putExtra("buy_id", orderBuyModel.getMmbId());
                    intent.putParcelableArrayListExtra("buy_list", (ArrayList<? extends Parcelable>) orderBuyModel.getListGoods());
                    intent.putExtra("buy_num", orderBuyModel.getListGoods().size());
                    startActivityForResult(intent, Constant.START_ACTIVITY_TYPE);//10
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
