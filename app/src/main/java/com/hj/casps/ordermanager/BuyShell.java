package com.hj.casps.ordermanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.cooperate.CooperateGroupSearch;
import com.hj.casps.entity.appQuote.ChooseGoodsBack;
import com.hj.casps.entity.appQuote.ChooseGoodsPost;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.HTTPURLIMAGE;
import static com.hj.casps.common.Constant.SHORTHTTPURL;
import static com.hj.casps.common.Constant.SYS_FUNC;
import static com.hj.casps.common.Constant.getUUID;

//采购销售拣单车点击进入下单界面
public class BuyShell extends ActivityBaseHeader2 implements View.OnClickListener {

    private TextView buy_shell_info;//操作说明
    private TextView text_name_buy_shell;//采购方还是销售方
    private TextView order_buy_name_shell;//供应商名称
    private TextView order_buy_num_shell;//有多少种商品
    private ListView buy_cart_list_shell;//购物车选择后的list
    private CheckBox select_all_shell;//全选商品按钮
    private TextView offline_shell_reset;//重置按钮
    private TextView order_buy_btn;//下单按钮
    private String buy_type;//传递过来是采购还是销售
    private String buy_name;//供应商名称
    private String categoryId;//商品品类id
    private int buy_num;//商品品类包含的商品数量
    private List<OrderShellModel> orderShellModels;
    private List<ChooseGoodsBack.MtListBean> mtlistbeans;
    private OrderShellAdapter adapter;
    private ChooseGoodsAdapter adapter_choose;
    private int state;
    private String buy_id;
    private int no;
    public static BuyShell buyShell = null;
    private ArrayList<BuyCart.BuyCartBack.ListBean.ListGoodsBean> buy_list;
    private boolean choose;
    private RelativeLayout info_relative;
    private RelativeLayout relation_all;
    private LinearLayout choose_all_layout;
    private String groupName = "";
    private String goodsName;
    private String goodsId;
    //    private String quoteId;
    private String minPrice;
    private String maxPrice;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initData();
                    break;
                case 1:
                    setNo();
                    break;

            }
        }
    };


    //制定商品后，显示的内容
    private void setNo() {
        if (no != -1) {
            orderShellModels.get(no).setStatus(true);
            orderShellModels.get(no).setName(goodsName);
            orderShellModels.get(no).setGoodsId(goodsId);
//            orderShellModels.get(no).setQuoteId(quoteId);
            orderShellModels.get(no).setPrice(minPrice + "-" + maxPrice);
            adapter.notifyDataSetChanged();
        }
    }

    //点击搜索
    @Override
    protected void onRightClick() {
        super.onRightClick();
        bundle.putInt("type", 2);
        intentActivity(CooperateGroupSearch.class, 11, bundle);

    }

    //返回的搜索结果以及指定商品后返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == 22) {
            select_all_shell.setChecked(false);
            groupName = data.getExtras().getString("groupName");
            handler.sendEmptyMessage(0);
        } else if (requestCode == 11 && resultCode == 33) {
            no = data.getExtras().getInt("no", -1);
            goodsName = data.getExtras().getString("goodsName");
            goodsId = data.getExtras().getString("goodsId");
//            quoteId = data.getExtras().getString("quoteId");
            minPrice = data.getExtras().getString("minPrice");
            maxPrice = data.getExtras().getString("maxPrice");
            //bundle.putString("goodsId", finalChooseGood.getGoodsId());
//            bundle.putString("goodsName", finalChooseGood.getName());
//            bundle.putString("minPrice", String.valueOf(finalChooseGood.getMinPrice()));
//            bundle.putString("maxPrice", String.valueOf(finalChooseGood.getMaxPrice()));
            handler.sendEmptyMessage(1);

        }

    }

    //安卓基本方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_shell);
        buyShell = this;
        initData();
        initView();
    }

    //加载拣单车数据，或者指定商品数据
    private void initData() {
        choose = getIntent().getBooleanExtra("choose", false);//是选择指定商品还是拣单车
        if (choose) {//如果是选择指定商品，就从网络加载数据
            no = getIntent().getIntExtra("no", -1);
            categoryId = getIntent().getStringExtra("categoryId");
//            toast(no + "," + categoryId);
            ChooseGoodsPost post = new ChooseGoodsPost(
                    publicArg.getSys_token(),
                    getUUID(),
                    SYS_FUNC,
                    publicArg.getSys_user(),
                    publicArg.getSys_mmbname(),
                    publicArg.getSys_member(),
                    categoryId,
                    "1",
                    "20",
                    "0",
                    groupName);
            LogShow(mGson.toJson(post));
            OkGo.post(Constant.AppOrderSearchGoodUrl)
                    .params("param", mGson.toJson(post))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ChooseGoodsBack backDetail = mGson.fromJson(s, ChooseGoodsBack.class);
                            if (backDetail == null) {
                                return;
                            }
                            if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                                toastSHORT(backDetail.getReturn_message());
                                LogoutUtils.exitUser(BuyShell.this);
                            }
                            else if (backDetail.getReturn_code() != 0) {
                                Toast.makeText(getApplicationContext(), backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mtlistbeans = backDetail.getMtList();
                                if (mtlistbeans == null || mtlistbeans.isEmpty()) {
//                                Toast.makeText(context, "没有订单", Toast.LENGTH_SHORT).show();
                                    adapter_choose.removeAll();
                                }
//                                for (int i = 0; i < mtlistbeans.size(); i++) {
//                                    mtlistbeans.get(i).setChoose(false);
//                                }
                                adapter_choose.updateRes(mtlistbeans);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        } else {

            buy_type = getIntent().getStringExtra("buy_type");
            buy_name = getIntent().getStringExtra("buy_name");
            buy_id = getIntent().getStringExtra("buy_id");
            buy_list = getIntent().getParcelableArrayListExtra("buy_list");
            buy_num = getIntent().getIntExtra("buy_num", 0);
            switch (buy_type) {
                case "采购方：":
                    setTitle(getString(R.string.order_sell_cart));
                    state = 0;
                    break;
                case "销售方：":
                    setTitle(getString(R.string.order_buy_cart));
                    state = 1;
                    break;
            }
            orderShellModels = new ArrayList<>();
            for (int i = 0; i < buy_list.size(); i++) {
                OrderShellModel orderShellModel = new OrderShellModel();
                orderShellModel.setNum(0);
                orderShellModel.setNo(buy_list.get(i).getNo());
                orderShellModel.setCategoryId(buy_list.get(i).getCategoryId());
                orderShellModel.setChecked(false);
                orderShellModel.setStatus(false);
                orderShellModel.setDelete(false);
                orderShellModel.setName(buy_list.get(i).getGoodsName());
                orderShellModel.setGoodsId(buy_list.get(i).getGoodsId());
                orderShellModel.setQuoteId(buy_list.get(i).getId());
                orderShellModel.setPrice(buy_list.get(i).getMinPrice() + "-" + buy_list.get(i).getMaxPrice());
                orderShellModels.add(orderShellModel);
            }
        }

    }

    //刷新页面
    public void refresh() {
        List<OrderShellModel> orderShellModels1 = new ArrayList<>();
        for (int i = 0; i < orderShellModels.size(); i++) {
            if (!orderShellModels.get(i).isDelete()) {
                orderShellModels1.add(orderShellModels.get(i));
            }
        }
        if (orderShellModels1.size() == 0) {
            adapter.removeAll();
        } else {
            adapter.updateRes(orderShellModels1);
        }
        setResult(RESULT_OK);
    }

    //加载布局
    private void initView() {

        buy_shell_info = (TextView) findViewById(R.id.buy_shell_info);
        text_name_buy_shell = (TextView) findViewById(R.id.text_name_buy_shell);
        order_buy_name_shell = (TextView) findViewById(R.id.order_buy_name_shell);
        order_buy_num_shell = (TextView) findViewById(R.id.order_buy_num_shell);
        buy_cart_list_shell = (ListView) findViewById(R.id.buy_cart_list_shell);
        select_all_shell = (CheckBox) findViewById(R.id.select_all_shell);
        offline_shell_reset = (TextView) findViewById(R.id.offline_shell_reset);
        order_buy_btn = (TextView) findViewById(R.id.order_buy_btn);
        choose_all_layout = (LinearLayout) findViewById(R.id.choose_all_layout);

        buy_shell_info.setOnClickListener(this);
        offline_shell_reset.setOnClickListener(this);
        order_buy_btn.setOnClickListener(this);

        info_relative = (RelativeLayout) findViewById(R.id.info_relative);
        relation_all = (RelativeLayout) findViewById(R.id.relation_all);
        if (choose) {
            info_relative.setVisibility(View.GONE);
            relation_all.setVisibility(View.GONE);
            choose_all_layout.setVisibility(View.GONE);
            offline_shell_reset.setVisibility(View.GONE);
            setTitle(getString(R.string.quotes_status_product));
            setTitleRight(null, ResourcesCompat.getDrawable(getResources(), R.mipmap.nav_ser, null));
            order_buy_btn.setText(getString(R.string.True));
            order_buy_btn.setBackgroundColor(getResources().getColor(R.color.title_bg));
//            offline_shell_reset.setBackgroundColor(getResources().getColor(R.color.reset_bg));
            adapter_choose = new ChooseGoodsAdapter(mtlistbeans, this, R.layout.choose_goods_item);
            buy_cart_list_shell.setAdapter(adapter_choose);
            buy_cart_list_shell.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    for (int j = 0; j < mtlistbeans.size(); j++) {
                        if (i == j) {
                            mtlistbeans.get(j).setChoose(true);
                        } else {
                            mtlistbeans.get(j).setChoose(false);

                        }
                    }
                    adapter_choose.notifyDataSetChanged();
                }
            });
        } else {
            text_name_buy_shell.setText(buy_type);
            order_buy_name_shell.setText(buy_name);
            order_buy_num_shell.setText(String.valueOf(buy_num));
            adapter = new OrderShellAdapter(orderShellModels, this, R.layout.item_buy_shell, state, this);
            buy_cart_list_shell.setAdapter(adapter);
            select_all_shell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    selectAll(b);
                }
            });
            choose_all_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (select_all_shell.isChecked()) {
                        select_all_shell.setChecked(false);
                        selectAll(false);
                    } else {
                        select_all_shell.setChecked(true);
                        selectAll(true);
                    }
                }
            });
        }


    }

    private void selectAll(boolean b) {
        for (int i = 0; i < orderShellModels.size(); i++) {
            orderShellModels.get(i).setChecked(b);
        }
        adapter.notifyDataSetChanged();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy_shell_info:
                if (state == 0) {
                    CreateDialog(Constant.DIALOG_CONTENT_26);
                } else {
                    CreateDialog(Constant.DIALOG_CONTENT_24);
                }
                break;
            case R.id.offline_shell_reset:
                for (int i = 0; i < orderShellModels.size(); i++) {
                    orderShellModels.get(i).setChecked(false);
                    orderShellModels.get(i).setNum(0);
                }
                adapter.notifyDataSetChanged();
                select_all_shell.setChecked(false);
                break;
            case R.id.order_buy_btn:
                if (choose) {
                    gotoSubmit();

                } else {
                    List<OrderShellModel> orderShellModels2 = new ArrayList<>();
                    for (int i = 0; i < orderShellModels.size(); i++) {
                        if (orderShellModels.get(i).isChecked()) {
                            orderShellModels2.add(orderShellModels.get(i));
                        }
                    }
                    if (orderShellModels2.size() == 0) {
                        toast("请先勾选报价");
                        return;
                    }
                    bundle.putString("title", getString(R.string.order_detail_product_grid));
                    bundle.putInt("state", state);
                    bundle.putString("buy_name", buy_name);
                    bundle.putString("buy_id", buy_id);
                    bundle.putParcelableArrayList("orders", (ArrayList<? extends Parcelable>) orderShellModels2);
                    intentActivity(OrderDetail.class, bundle);
                }
                break;
        }
    }

    //提交商品的方法
    private void gotoSubmit() {
        if (mtlistbeans == null)
            return;
        String goods_id = null;
        String goods_name = null;
        ChooseGoodsBack.MtListBean chooseGood = new ChooseGoodsBack.MtListBean();
        for (int j = 0; j < mtlistbeans.size(); j++) {
            if (mtlistbeans.get(j).isChoose()) {
                goods_id = mtlistbeans.get(j).getGoodsId();
                goods_name = mtlistbeans.get(j).getName();
                chooseGood = mtlistbeans.get(j);

            }
        }
        if (goods_id == null || goods_id.isEmpty()) {
            toast("请选择商品");
        } else {
            ChooseGoodsPost post = new ChooseGoodsPost(publicArg.getSys_token(), getUUID(), SYS_FUNC, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), goods_id + "," + goods_name, "0", "0");
            final ChooseGoodsBack.MtListBean finalChooseGood = chooseGood;
            OkGo.post(Constant.UpdateQuoteSHPCUrl)
                    .params("param", mGson.toJson(post))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            ChooseGoodsBack backDetail = mGson.fromJson(s, ChooseGoodsBack.class);
                            if (backDetail == null) {
                                return;
                            }if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                                toastSHORT("重复登录或令牌超时");
                                LogoutUtils.exitUser(BuyShell.this);
                            }
                            else if (backDetail.getReturn_code() != 0) {
                                Toast.makeText(getApplicationContext(), backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                            }
                            else {
//                                Toast.makeText(getApplicationContext(), backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                                bundle.putInt("no", no);
                                bundle.putString("goodsId", finalChooseGood.getGoodsId());
                                bundle.putString("goodsName", finalChooseGood.getName());
                                bundle.putString("minPrice", String.valueOf(finalChooseGood.getMinPrice()));
                                bundle.putString("maxPrice", String.valueOf(finalChooseGood.getMaxPrice()));
                                setResult(33, getIntent().putExtras(bundle));
                                finish();

                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    /**
     * 适配器
     */
    private class ChooseGoodsAdapter extends WZYBaseAdapter<ChooseGoodsBack.MtListBean> {
        private Context context;

        public ChooseGoodsAdapter(List<ChooseGoodsBack.MtListBean> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
            this.context = context;
        }

        @Override
        public void bindData(ViewHolder holder, ChooseGoodsBack.MtListBean mtListBean, final int indexPos) {
            CheckBox goods_check_order = (CheckBox) holder.getView(R.id.goods_check_order);
            goods_check_order.setChecked(mtListBean.isChoose());
            ImageView imgOrder = (ImageView) holder.getView(R.id.imgOrder);
            if (mtListBean.getImgPath() != null) {
                Glide.with(getApplicationContext()).load(mtListBean.getImgPath().startsWith("/v2content") ? SHORTHTTPURL + mtListBean.getImgPath() : HTTPURLIMAGE + mtListBean.getImgPath()).into(imgOrder);

            }

            TextView order_name = (TextView) holder.getView(R.id.order_name);
            order_name.setText(mtListBean.getName());
            TextView price_period = (TextView) holder.getView(R.id.price_period);
            price_period.setText(mtListBean.getMinPrice() + "--" + mtListBean.getMaxPrice());
            TextView product_num = (TextView) holder.getView(R.id.product_num);
            product_num.setText(mtListBean.getProductNum());
            TextView factory = (TextView) holder.getView(R.id.factory);
            factory.setText(mtListBean.getFactory());
        }
    }
}
