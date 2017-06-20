package com.hj.casps.reception;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader3;
import com.hj.casps.commodity.SelectPicture02;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.paymentmanager.response.WytUtils;
import com.hj.casps.ordermanager.BuyCart;
import com.hj.casps.quotes.MyViewHolder;
import com.hj.casps.quotes.wyt.NewQtListEntity;
import com.hj.casps.quotes.wyt.PriceQuoteAdapter;
import com.hj.casps.quotes.wyt.PubMap;
import com.hj.casps.quotes.wyt.QtListEntity;
import com.hj.casps.quotes.wyt.ReqQuoteDetail;
import com.hj.casps.quotes.wyt.RequestSearchQuote;
import com.hj.casps.quotes.wyt.SearchQuoteGain;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC101100410002;


/**
 * Created by YaoChen on 2017/4/14.
 */

public class ActivityPriceSearch extends ActivityBaseHeader3 implements View.OnClickListener,
        OnPullListener, MyViewHolder.onClickMyLienter {
    private ImageView totop;
    AbsRefreshLayout mLoader;
    private TextView styleTv;
    private List<QtListEntity> mList = new ArrayList<>();
    private List<QtListEntity> dbList = new ArrayList<>();
    private List<QtListEntity> pubList = new ArrayList<>();
    private RecyclerView rv;
    private PriceQuoteAdapter adapter;
    private int pageno = 0;
    private int pagesize = 10;
    private int type;//类型   0 采购  1 销售
    private boolean isReSave = true;//是否缓存
    private boolean isSave = true;//是否清除数据
    private String categoryId = "";


    //刷新购物车数据
    private void refreshShopCar() {
        count = 0;
        initShopCarData();
    }

    private int qtCount;
    private int count = 0;
    private TextView shopNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.prices_search));
        setContentView(R.layout.activity_price_search);
        initView();
        if (hasInternetConnected()) {
            initData(pageno);
            initShopCarData();
        } else {
            getDataForLocal();
        }

    }

    //初始化购物车数据
    private void initShopCarData() {
        BuyCart.BuyCartPost post = new BuyCart.BuyCartPost(publicArg.getSys_token(), Constant.getUUID(), SYS_FUNC101100410002, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), String.valueOf(type));
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
                        } else {
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

                    }
                });
    }

    //保存数据到sqlite
    private void saveLocalData() {
        //  0 采购  1 销售
        if (type == 0) {
            WytUtils.getInstance(this).DeleteAllqtListEntityInfo();
            for (int i = 0; i < pubList.size(); i++) {
                WytUtils.getInstance(this).insertqtListEntityInfo(pubList.get(i));
            }
        } else {
            WytUtils.getInstance(this).DeleteAllnewQtListInfo();
            for (int i = 0; i < pubList.size(); i++) {
                QtListEntity entity = pubList.get(i);
                NewQtListEntity listEntity = new NewQtListEntity();
                listEntity.setGoodname(entity.getGoodname());
                listEntity.setGoodsid(entity.getGoodsid());
                listEntity.setId(entity.getId());
                listEntity.setImgPath(entity.getImgPath());
                listEntity.setMaxPrice(entity.getMaxPrice());
                listEntity.setMinPrice(entity.getMinPrice());
                listEntity.setMmbId(entity.getMmbId());
                listEntity.setMmbName(entity.getMmbName());
                listEntity.setNum(entity.getNum());
                listEntity.setStartEnd(entity.getStartEnd());
                listEntity.setStartTime(entity.getStartTime());
                WytUtils.getInstance(this).insertnewQtListInfo(listEntity);
            }
        }

    }

    private void getDataForLocal() {
        if (type == 0) {
            List<QtListEntity> entityList = WytUtils.getInstance(this).QuerytqtListEntityInfo();
            mList.clear();
            mList.addAll(entityList);
        } else {
            List<NewQtListEntity> baseList = WytUtils.getInstance(this).QuerytnewQtListInfo();
            for (int i = 0; i < baseList.size(); i++) {
                NewQtListEntity entity = baseList.get(i);
                QtListEntity entity1 = new QtListEntity();
                entity1.setGoodname(entity.getGoodname());
                entity1.setGoodsid(entity.getGoodsid());
                entity1.setId(entity.getId());
                entity1.setImgPath(entity.getImgPath());
                entity1.setMaxPrice(entity.getMaxPrice());
                entity1.setMinPrice(entity.getMinPrice());
                entity1.setMmbId(entity.getMmbId());
                entity1.setMmbName(entity.getMmbName());
                entity1.setNum(entity.getNum());
                entity1.setStartEnd(entity.getStartEnd());
                entity1.setStartTime(entity.getStartTime());
                mList.add(entity1);
            }
        }
        refreshUI();
    }

    private void refreshUI() {
        if (isSave) {
            pubList.clear();
        }
        for (int i = 0; i < mList.size(); i++) {
            dbList.add(mList.get(i));
            pubList.add(mList.get(i));
        }
        if (pageno == 0) {
            adapter = new PriceQuoteAdapter(context, mList);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            if (pageno <= (qtCount - 1) / pagesize) {
                adapter.addRes(mList);
            } else {
                mLoader.onLoadAll();
            }
        }
        if (isReSave) {
            saveLocalData();
        }

    }


    protected void initData(final int pageno) {

        RequestSearchQuote r = new RequestSearchQuote(publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC10110028,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                Constant.SEARCH_Price_quote_checkboxId,
                String.valueOf(pageno + 1),
                String.valueOf(pagesize),
                String.valueOf(type),
                Constant.SEARCH_Price_quote_goodName,
                categoryId
                , "");
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "SearchQuoteGain";
        System.out.println("r=SearchQuoteUrl" + r.toString());
        OkGo.post(Constant.SearchQuoteUrl).params("param", param).execute(new JsonCallBack<SearchQuoteGain<List<QtListEntity>>>() {
            @Override
            public void onSuccess(SearchQuoteGain<List<QtListEntity>> data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                if (data != null && data.return_code == 0 && data.qtList != null) {
                    ActivityPriceSearch.this.qtCount = data.qtCount;
                    ActivityPriceSearch.this.mList = data.qtList;
                    refreshUI();
                } else {
                    toastSHORT(data.return_message);
                    return;
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
               /* if (!(e instanceof ConnectException))
                    toastSHORT(e.getMessage());*/
                waitDialogRectangle.dismiss();
            }
        });
    }


    @Override
    public void onItemMyLienter(int pos) {
        QtListEntity qtListEntity = dbList.get(pos);
        String QuoteId = qtListEntity.getId();
        Intent intent = new Intent(this, ActivityGoodsDetailed.class);
        intent.putExtra(Constant.INTENT_QUOTE_ID, QuoteId);
        intent.putExtra(Constant.INTENT_TYPE, type);
        startActivity(intent);
    }

    //点击购物车的回调
    @Override
    public void onAddShopCarLisenter(int postion) {
        QtListEntity entity = dbList.get(postion);
        String id = entity.getId();
        addShopCarForNet(id);
    }

    private void addShopCarForNet(String id) {
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ReqQuoteDetail r = new ReqQuoteDetail(publicArg.getSys_token(), timeUUID, Constant.SYS_FUNC10110028, publicArg.getSys_user(), publicArg.getSys_name(), publicArg.getSys_member(), id);
        final String param = mGson.toJson(r);
        System.out.println("r=AddShopCarUrl" + r.toString());
        OkGo.post(Constant.AddShopCarUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                PubMap pubMap = GsonTools.changeGsonToBean(data, PubMap.class);
                if (pubMap.getReturn_code() == 0) {
                    new MyToast(ActivityPriceSearch.this, "添加购物车成功");
                    refreshShopCar();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }
        });
    }


    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);
        shopNum = (TextView) findViewById(R.id.price_search_shopcart_num);
        findViewById(R.id.manager_back);
        final View viewById = findViewById(R.id.price_search_shopcar);
        viewById.setOnClickListener(this);
        styleTv = (TextView) findViewById(R.id.price_search_class_tv);
        int styleType = getIntent().getIntExtra("Style", 0);
        // 如果是销售进来的，就是进入可采购的页面   如果是采购进来的就加载销售的数据
        if (styleType == 0) {
            type = 1;
        } else if (styleType == 1) {
            type = 0;
        }
        if (type == 0) {
            styleTv.setText(getString(R.string.sale));
        } else {
            styleTv.setText(getString(R.string.buy));
        }
        totop = (ImageView) findViewById(R.id.price_search_totop);
        totop.setOnClickListener(this);
        MyViewHolder.setOnClickMyLienter(this);

        GridLayoutManager gl = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(gl);
        rv.addItemDecoration(new SelectPicture02.SelectPicture02ItemDecoration(this));
        mLoader = new NestRefreshLayout(rv);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

    }


    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        Intent intent = new Intent(ActivityPriceSearch.this, ActivityPriceSearchPage.class);
        startActivityForResult(intent, Constant.REQUEST_QUOTE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回顶部
            case R.id.price_search_totop:
                rv.smoothScrollToPosition(0);
                break;

            //购物车
            case R.id.price_search_shopcar:
                // 0 采购  1 销售
                if (type == 0) {
                    bundle.putInt(Constant.ORDER_TYPE, Constant.order_type_sell);
                    context.startActivity(new Intent(this, BuyCart.class).putExtras(bundle));
                }
                if (type == 1) {
                    bundle.putInt(Constant.ORDER_TYPE, Constant.order_type_buy);
                    context.startActivity(new Intent(this, BuyCart.class).putExtras(bundle));
                }
                break;
        }
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageno = 0;
        isEmptyParam();
        isSave = true;
        initData(pageno);
        mLoader.onLoadFinished();
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageno++;
        isEmptyParam();
        initData(pageno);
        mLoader.onLoadFinished();//加载结束
    }

    public void isEmptyParam() {
        if (StringUtils.isStrTrue(Constant.SEARCH_Price_quote_goodName) || "3,2,1".equals(Constant.SEARCH_Price_quote_checkboxId) || (categoryId != null && !categoryId.equals(""))) {
            isSave = true;
            isReSave = false;
        } else {
            isSave = false;
            isReSave = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoryId = "";
        Constant.SEARCH_Price_quote_goodName = "";//商品名
        Constant.SEARCH_Price_quote_checkboxId = "3,2,1";//选项 默认选中123
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshShopCar();
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        pageno = 0;
        initData(pageno);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&requestCode == Constant.REQUEST_QUOTE){
            pageno = 0;
            isEmptyParam();
            isReSave = false;
            initData(pageno);
        }
    }
}








