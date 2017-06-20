package com.hj.casps.quotes;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.TypeAbstractViewHolder;
import com.hj.casps.app.HejiaApp;
import com.hj.casps.common.Constant;
import com.hj.casps.quotes.wyt.QtListEntity;
import com.hj.casps.util.BitmapUtils;

//报价检索的View,使用ViewHolder集成
public class MyViewHolder extends TypeAbstractViewHolder<QtListEntity> {
    public ImageView priceSearchGridImg;
    public TextView priceSearchGridName;
    public TextView priceSearchGridPrice;
    public TextView priceSearchGridNum;
    public TextView priceSearchGridTime;
    public TextView tv_publishName;
    private View mV;
    public static onClickMyLienter onClickMyLienter;
    public final TextView tv_add_shopcar;
    //

    public static void setOnClickMyLienter(MyViewHolder.onClickMyLienter onClickMyLienter) {
        MyViewHolder.onClickMyLienter = onClickMyLienter;
    }


    public MyViewHolder(View v) {
        super(v);
        int width = HejiaApp.getContext().getResources().getDisplayMetrics().widthPixels;
        width = (width - 25) / 2;
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        ;
//        layoutParams.height = (int) (width * 1.1f);
        layoutParams.width = width;

        priceSearchGridImg = (ImageView) v.findViewById(R.id.price_search_grid_img);
        priceSearchGridName = (TextView) v.findViewById(R.id.price_search_grid_name);
        priceSearchGridPrice = (TextView) v.findViewById(R.id.price_search_grid_price);
        priceSearchGridNum = (TextView) v.findViewById(R.id.price_search_grid_num);
        priceSearchGridTime = (TextView) v.findViewById(R.id.price_search_grid_time);
        tv_add_shopcar = (TextView) v.findViewById(R.id.price_search_grid_Btn);
        tv_publishName = (TextView) v.findViewById(R.id.tv_publishName);
        mV = v;

    }

    @Override
    public void bindViewHolder(final QtListEntity item, final int postion) {
        BitmapUtils.LoadImage(item.getImgPath(), priceSearchGridImg, 600, 350);
        priceSearchGridName.setText(item.getGoodname());
        priceSearchGridNum.setText("存货量:" + String.valueOf(item.getNum()));
        priceSearchGridPrice.setText("￥" + item.getMinPrice() + "—￥" + item.getMaxPrice());
        String start = Constant.stmpToDate(item.getStartTime());
        String end = Constant.stmpToDate(item.getStartEnd());
        priceSearchGridTime.setText(start + "至" + end);
        tv_publishName.setText(item.getMmbName());


        mV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMyLienter.onItemMyLienter(postion);
            }
        });
        tv_add_shopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMyLienter.onAddShopCarLisenter(postion);
            }
        });

    }

    public interface onClickMyLienter {
        void onItemMyLienter(int pos);

        void onAddShopCarLisenter(int pos);
    }
}