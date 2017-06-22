package com.hj.casps.reception;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.commodity.ActivityGoodDetail;
import com.hj.casps.commodity.BannerImageLoader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.paymentmanager.response.WytUtils;
import com.hj.casps.ordermanager.BuyCart;
import com.hj.casps.quotes.wyt.PubMap;
import com.hj.casps.quotes.wyt.ReqQuoteDetail;
import com.hj.casps.quotes.wyt.ResQuoteDetailBean;
import com.hj.casps.quotes.wyt.ResQuoteDetailEntity;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.BitmapUtils;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC;

/**
 * Created by YaoChen on 2017/4/18.
 */

public class ActivityGoodsDetailed extends ActivityBaseHeader2 implements View.OnClickListener, OnBannerClickListener {

    @BindView(R.id.goodsdetailed_name)
    TextView tv_detailedName;
    @BindView(R.id.goodsdetailed_price)
    TextView tv_detailedPrice;
    @BindView(R.id.goodsdetailed_stock)
    TextView tv_detailedStock;
    @BindView(R.id.goodsdetailed_publisher)
    TextView tv_detailedPublisher;
    @BindView(R.id.goodsdetailed_time)
    TextView tv_detailedTime;
    @BindView(R.id.goodsdetailed_location)
    TextView tv_detailedLocation;
    @BindView(R.id.gooddetailed_shopcart)
    ImageView tv_detailedShopcart;
    @BindView(R.id.gooddetailed_shopcart_num)
    TextView tv_detailedShopcartNum;
    @BindView(R.id.goodsdetailed_shopcart)
    RelativeLayout ll_detailedShopcart;
    @BindView(R.id.goodsdetailed_addshopcart)
    TextView tv_detailedAddshopcart;
    @BindView(R.id.explan)
    TextView explan;
    @BindView(R.id.do_time)
    TextView doTime;
    private Banner banner;
    private String quoteId;
    private ResQuoteDetailEntity mEntity=new ResQuoteDetailEntity();

    private int count = 0;


    //数据保存到本地数据库
    private void saveLocalData(ResQuoteDetailEntity entity) {
        ResQuoteDetailBean bean = WytUtils.getInstance(this).QuerytResQuoteDetailInfo(quoteId);
        if(bean!=null){
            bean.setAreaName(entity.getAreaName());
            bean.setExplan(entity.getExplan());
            bean.setGoodsId(entity.getGoodsId());
            bean.setMaxPrice(entity.getMaxPrice());
            bean.setMinPrice(entity.getMinPrice());
            bean.setMmbName(entity.getMmbName());
            bean.setGoodsName(entity.getGoodsName());
            bean.setNum(entity.getNum());
            bean.setPathlist(GsonTools.createGsonString(entity.getPathlist()));
            bean.setQuoteId(entity.getQuoteId());
            bean.setStartEnd(entity.getStartEnd());
            bean.setStartTime(entity.getStartTime());
            WytUtils.getInstance(this).updateResQuoteDetailInfo(bean);
        }else{
            ResQuoteDetailBean bean2=new ResQuoteDetailBean();
            bean2.setAreaName(entity.getAreaName());
            bean2.setExplan(entity.getExplan());
            bean2.setGoodsId(entity.getGoodsId());
            bean2.setMaxPrice(entity.getMaxPrice());
            bean2.setMinPrice(entity.getMinPrice());
            bean2.setMmbName(entity.getMmbName());
            bean2.setGoodsName(entity.getGoodsName());
            bean2.setNum(entity.getNum());
            bean2.setPathlist(GsonTools.createGsonString(entity.getPathlist()));
            bean2.setQuoteId(entity.getQuoteId());
            bean2.setStartEnd(entity.getStartEnd());
            bean2.setStartTime(entity.getStartTime());
            WytUtils.getInstance(this).insertResQuoteDetailInfo(bean2);
        }

    }

    private int type;
    private TextView shopNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gooddetailed);
        ButterKnife.bind(this);
        quoteId = getIntent().getStringExtra(Constant.INTENT_QUOTE_ID);
        type = getIntent().getIntExtra(Constant.INTENT_TYPE, -1);
        setTitleRight(null, null);
        setTitle(getString(R.string.detailed));
        initView();
        if (hasInternetConnected()) {
            initData();
            initShopCarData();
        } else {
            getLocalData(quoteId);
        }
    }

    private void getLocalData(String quoteId) {
        ResQuoteDetailBean bean = WytUtils.getInstance(this).QuerytResQuoteDetailInfo(quoteId);
        if(bean!=null){
            mEntity.setAreaName(bean.getAreaName());
            mEntity.setExplan(bean.getExplan());
            mEntity.setGoodsId(bean.getGoodsId());
            mEntity.setMaxPrice(bean.getMaxPrice());
            mEntity.setMinPrice(bean.getMinPrice());
            mEntity.setMmbName(bean.getMmbName());
            mEntity.setGoodsName(bean.getGoodsName());
            mEntity.setNum(bean.getNum());
            mEntity.setPathlist(GsonTools.changeGsonToList(bean.getPathlist(),String.class));
            mEntity.setQuoteId(bean.getQuoteId());
            mEntity.setStartEnd(bean.getStartEnd());
            mEntity.setStartTime(bean.getStartTime());
            setDatas(mEntity);
        }

    }

    private void initData() {
        waitDialogRectangle.show();
        ReqQuoteDetail r = new ReqQuoteDetail(publicArg.getSys_token(), Constant.getUUID(), Constant.SYS_FUNC, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), quoteId);
        String param = mGson.toJson(r);
        OkGo.post(Constant.QuoteDetailGoodUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                ResQuoteDetailEntity entity = GsonTools.changeGsonToBean(data, ResQuoteDetailEntity.class);
                if (entity != null && entity.getReturn_code() == 0) {
                    ActivityGoodsDetailed.this.mEntity=entity;
                    setDatas(entity);
                    saveLocalData(entity);
                }else if(entity.getReturn_code()==1101||entity.getReturn_code()==1102){
                    toastSHORT("重复登录或令牌超时");
                    LogoutUtils.exitUser(ActivityGoodsDetailed.this);
                }else{
                    toastSHORT(entity.getReturn_message());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                toastSHORT(e.getMessage());
                waitDialogRectangle.dismiss();
            }
        });

    }

    private void setDatas(ResQuoteDetailEntity entity) {


        //设置轮播图
        List<String> pathlist = entity.getPathlist();
        List<String> imageList = new ArrayList<>();
        for (int i = 0; i < pathlist.size(); i++) {
            String url = "";
            if (pathlist.get(i).startsWith("/v2")) {
                url = Constant.SHORTHTTPURL + pathlist.get(i);
            } else {
                url = Constant.LONGTHTTPURL + pathlist.get(i);
            }
            imageList.add(i, url);
        }
        banner.setImages(imageList);
        explan.setText(entity.getExplan());
        banner.start();
        tv_detailedName.setText(entity.getGoodsName());
        tv_detailedPrice.setText(Constant.getNum(entity.getMinPrice()) + "-" + Constant.getNum(entity.getMaxPrice()));
//        tv_detailedStock.setText(entity.get "");
        String start = Constant.stmpToDate(entity.getStartTime());
        String end = Constant.stmpToDate(entity.getStartEnd());
        doTime.setText(start + "--" + end);
        tv_detailedPublisher.setText(entity.getMmbName());
    }


    private void initView() {
        banner = (Banner) findViewById(R.id.goodsdetailed_banner);
        shopNum = (TextView) findViewById(R.id.gooddetailed_shopcart_num);
        ImageLoader imageLoader = new BannerImageLoader();
        banner.setImageLoader(imageLoader);
        banner.setOffscreenPageLimit(Integer.MAX_VALUE);
        banner.setOnClickListener(this);
        banner.setOnBannerClickListener(this);
        findViewById(R.id.goodsdetailed_addshopcart).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goodsdetailed_banner:
                startActivity(new Intent(ActivityGoodsDetailed.this, ActivityPriceGoodsDetail.class));
                break;
            case R.id.goodsdetailed_addshopcart:
                addShopCarForNet();
                break;
        }
    }

    /**
     * 添加购物车
     */
    private void addShopCarForNet() {


        String timeUUID = Constant.getTimeUUID();
        if(timeUUID.equals("")){
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ReqQuoteDetail r = new ReqQuoteDetail(publicArg.getSys_token(), timeUUID, Constant.SYS_FUNC, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), quoteId);
        final String param = mGson.toJson(r);

        waitDialogRectangle.show();
        OkGo.post(Constant.AddShopCarUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                PubMap pubMap = GsonTools.changeGsonToBean(data, PubMap.class);
                if (pubMap.getReturn_code() == 0) {
                    new MyToast(ActivityGoodsDetailed.this, pubMap.getMsg());
                    count = 0;
                    initShopCarData();
                }
                else if(pubMap.getReturn_code()==1101||pubMap.getReturn_code()==1102){
                    toastSHORT("重复登录或令牌超时");
                    LogoutUtils.exitUser(ActivityGoodsDetailed.this);
                }
                else{
                    toastSHORT(pubMap.getReturn_message());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                toastSHORT(e.getMessage());
                waitDialogRectangle.dismiss();
            }
        });

    }

    @OnClick({R.id.goodsdetailed_shopcart, R.id.goodsdetailed_addshopcart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goodsdetailed_shopcart:
                if (type == 0) {
                    bundle.putInt(Constant.ORDER_TYPE, Constant.order_type_sell);
                    context.startActivity(new Intent(context, BuyCart.class).putExtras(bundle));
                }
                if (type == 1) {
                    bundle.putInt(Constant.ORDER_TYPE, Constant.order_type_buy);
                    context.startActivity(new Intent(context, BuyCart.class).putExtras(bundle));
                }
                break;
            case R.id.goodsdetailed_addshopcart:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    //轮播条目的点击事件返回轮播post
    @Override
    public void OnBannerClick(int position) {
        /*position是从1开始的  注意*/
//        toastSHORT(position+"");
        String goodsId1 = mEntity.getGoodsId();
//        String goodsId = entity.getGoodsId();
        Intent intent = new Intent(this, ActivityGoodDetail.class);
        intent.putExtra(Constant.INTENT_GOODSID, goodsId1);
        startActivity(intent);

    }


    //初始化购物车数据
    private void initShopCarData() {
        BuyCart.BuyCartPost post = new BuyCart.BuyCartPost(publicArg.getSys_token(), Constant.getUUID(), SYS_FUNC, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), String.valueOf(type));

        OkGo.post(Constant.SearchSHPCUrl)
                .params("param", mGson.toJson(post))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        BuyCart.BuyCartBack backDetail = mGson.fromJson(s, BuyCart.BuyCartBack.class);
                        if (backDetail == null) {
                            return;
                        }
                        if (backDetail.getReturn_code() != 0) {
                            Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                        }
                        else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(ActivityGoodsDetailed.this);
                        }

                        else {

                            List<BuyCart.BuyCartBack.ListBean> list = backDetail.getList();
                            for (int i = 0; i < list.size(); i++) {
                                count += list.get(i).getListGoods().size();
                            }
                            shopNum.setText(String.valueOf(count));
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        count = 0;
        initShopCarData();
    }
}
