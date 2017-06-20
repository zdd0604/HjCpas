package com.hj.casps.commodity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.goodsmanager.goodsmanagerCallBack.GoodToUpdateCallBack;
import com.hj.casps.entity.goodsmanager.request.RequestToUpdateGood;
import com.hj.casps.entity.goodsmanager.response.GoodInfoEntity;
import com.hj.casps.entity.goodsmanager.response.GoodtoUpdateGain;
import com.hj.casps.entity.paymentmanager.response.WytUtils;
import com.hj.casps.util.GsonTools;
import com.lzy.okgo.OkGo;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen on 2017/4/13.
 * 商品详情页
 */

public class ActivityGoodDetail extends ActivityBaseHeader2 implements View.OnClickListener {
    @BindView(R.id.goodsdetail_banner)
    Banner banner;
    @BindView(R.id.goodsdetail_name)
    TextView detail_name;
    @BindView(R.id.goodsdetail_class)
    TextView detail_class;
    @BindView(R.id.goodsdetail_time)
    TextView detail_time;
    @BindView(R.id.goodsdetail_address)
    TextView detail_addr;
    @BindView(R.id.goodsdetail_pro)
    TextView detail_product;
    @BindView(R.id.goodsdetail_num)
    TextView detail_num;
    @BindView(R.id.goodsdetail_brands)
    TextView detail_brands;
    @BindView(R.id.goodsdetail_shelf_life)
    TextView shelf_life;
    @BindView(R.id.goodsdetail_Specificationss)
    TextView Specificationss;
    @BindView(R.id.goodsdetail_price)
    TextView price;
    @BindView(R.id.goodsdetail_desc)
    TextView desc;
    @BindView(R.id.goodsdetail_true_Btn)
    FancyButton submit;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    //库存
    @BindView(R.id.good_detail_stock)
    TextView tvStock;
    private String goodsId;

    //网络设置数据
    private void setDataForNet(GoodtoUpdateGain<GoodInfoEntity> goodInfo) {
        //设置商品信息
        initBar(goodInfo.goodsImages);
        setGoodInfo(goodInfo.goodsInfo);
        //保存到本地数据库
        saveLocalData(goodInfo);
    }
    //保存到本地数据库
    private void saveLocalData(GoodtoUpdateGain<GoodInfoEntity> entity) {
        GoodInfoEntity bean = WytUtils.getInstance(this).QuerytGoodInfoEntityInfo(goodsId);
        if (bean != null) {
            GoodInfoEntity e = entity.goodsInfo;
            bean.setGoodsId(goodsId);
            bean.setBrand(e.getBrand());
            bean.setCategoryId(e.getCategoryId());
            bean.setCategoryName(e.getCategoryName());
            bean.setCreateAddress(e.getCreateAddress());
            bean.setCreateTime(e.getCreateTime());
            bean.setDescribed(e.getDescribed());
            bean.setFactory(e.getFactory());
            bean.setImgPath(e.getImgPath());
            bean.setMaxPrice(e.getMaxPrice());
            bean.setMinPrice(e.getMinPrice());
            bean.setName(e.getName());
            bean.setProductNum(e.getProductNum());
            bean.setProductTime(e.getProductTime());
            bean.setSpecification(e.getSpecification());
            bean.setStatus(e.getStatus());
            bean.setStockNum(e.getStockNum());
            bean.setUnitPrice(e.getUnitPrice());
            bean.setUnitSpecification(e.getUnitSpecification());
            if(entity.goodsImages!=null&&entity.goodsImages.size()>0){
            bean.setGoodsImages(GsonTools.createGsonString(entity.goodsImages));
            }
            WytUtils.getInstance(this).updateGoodInfoEntityInfo(bean);
        }else{
            GoodInfoEntity bean2=new GoodInfoEntity();
            GoodInfoEntity e = entity.goodsInfo;
            bean2.setGoodsId(goodsId);
            bean2.setBrand(e.getBrand());
            bean2.setCategoryId(e.getCategoryId());
            bean2.setCategoryName(e.getCategoryName());
            bean2.setCreateAddress(e.getCreateAddress());
            bean2.setCreateTime(e.getCreateTime());
            bean2.setDescribed(e.getDescribed());
            bean2.setFactory(e.getFactory());
            bean2.setImgPath(e.getImgPath());
            bean2.setMaxPrice(e.getMaxPrice());
            bean2.setMinPrice(e.getMinPrice());
            bean2.setName(e.getName());
            bean2.setProductNum(e.getProductNum());
            bean2.setProductTime(e.getProductTime());
            bean2.setSpecification(e.getSpecification());
            bean2.setStatus(e.getStatus());
            bean2.setStockNum(e.getStockNum());
            bean2.setUnitPrice(e.getUnitPrice());
            bean2.setUnitSpecification(e.getUnitSpecification());
            if(entity.goodsImages!=null&&entity.goodsImages.size()>0){
                bean2.setGoodsImages(GsonTools.createGsonString(entity.goodsImages));
            }
            WytUtils.getInstance(this).insertGoodInfoEntityInfo(bean2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gooddetail);
        setTitle(getString(R.string.detail));
        setTitleRight(null, null);
        ButterKnife.bind(this);
        goodsId = getIntent().getStringExtra(Constant.INTENT_GOODSID);


        initView();
        if (hasInternetConnected()) {
            initData();
        } else {
            getLocalData();
        }
    }

    //从本地数据库加载数据
    private void getLocalData() {
        GoodInfoEntity entity = WytUtils.getInstance(this).QuerytGoodInfoEntityInfo(goodsId);
        if(entity!=null){
            initBar(GsonTools.changeGsonToList(entity.getGoodsImages(),String.class));
            setGoodInfo(entity);
        }
    }

    private void initData() {
        PublicArg publicArg = Constant.publicArg;
        RequestToUpdateGood requestToUpdateGood = new RequestToUpdateGood(publicArg.getSys_token(), Constant.getUUID(), Constant.SYS_FUNC101100210001, publicArg.getSys_user(), publicArg.getSys_member(), goodsId);
        String param = mGson.toJson(requestToUpdateGood);
        OkGo.post(Constant.LookGoodUrl).params("param", param).execute(new GoodToUpdateCallBack<GoodtoUpdateGain<GoodInfoEntity>>() {
            @Override
            public void onSuccess(GoodtoUpdateGain<GoodInfoEntity> goodInfo, Call call, Response response) {
                super.onSuccess(goodInfo, call, response);
                if(goodInfo.return_code==0&&goodInfo!=null){
                setDataForNet(goodInfo);

                }
            }
        });

    }

    private void initView() {
        findViewById(R.id.goodsdetail_true_Btn).setOnClickListener(this);
        submit.setOnClickListener(this);

    }


    private void initBar(final List<String> datas) {

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < datas.size(); i++) {
            System.out.println("g======" + datas.get(i));
            list.add(i, Constant.SHORTHTTPURL + datas.get(i));
        }
        //设置图片集合
        ImageLoader imageLoader = new BannerImageLoader();
        banner.setImageLoader(imageLoader);
        banner.setOffscreenPageLimit(Integer.MAX_VALUE);
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(ActivityGoodDetail.this, ShowPhotoActivity.class);
                intent.putExtra(Constant.INTAENT_PUBLIC_URL, list.get(position - 1));
                startActivity(intent);
            }
        });
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        banner.setImages(list);
//        banner.setOnBannerClickListener(new HomeOnBannerClickListener(data));
        banner.start();
    }

    private void setGoodInfo(GoodInfoEntity g) {
        Glide.with(this).load(Constant.SHORTHTTPURL + g.getImgPath()).into(ivTitle);
        detail_name.setText(g.getName());
        detail_class.setText(g.getCategoryName());
        //生产日期
        detail_time.setText(Constant.stmpToDate(g.getCreateTime()));
        detail_addr.setText(g.getCreateAddress());
        detail_num.setText(g.getProductNum());
        detail_brands.setText(g.getBrand());
        //库存
        tvStock.setText(g.getStockNum());
        shelf_life.setText(g.getProductTime());
//        if (g.equals(g))
        //规格  规格+规格单位
        if (isNumeric(g.getUnitSpecification())){
            if (g.getSpecification().equals("1")){
                Specificationss.setText(g.getSpecification() + ",单位：千克");
            }else{
                Specificationss.setText(g.getSpecification() + ",单位：克");
            }
        }else {
            Specificationss.setText(g.getSpecification() + ",单位：" + g.getUnitSpecification());
        }

        //生产产家
        detail_product.setText(g.getFactory());
        price.setText("￥" + g.getMinPrice() + "--￥" + g.getMaxPrice());
        desc.setText(g.getDescribed());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goodsdetail_true_Btn:
//                Intent intent = new Intent(ActivityGoodDetail.this, ActivityEditGoods.class);
//                intent.putExtra(Constant.INTENT_GOODSID,goodsId);
//                intent.putExtra(Constant.INTENTISADDGOODS,false);
//                startActivity(intent);
                finish();
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

}
