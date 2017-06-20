package com.hj.casps.adapter.goodmanager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.TypeAbstractViewHolder;
import com.hj.casps.app.HejiaApp;
import com.hj.casps.commodity.ActivityGoodsClass;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.goodsmanager.response.DataListBean;
import com.hj.casps.entity.goodsmanager.response.DataListEntity;
import com.hj.casps.entity.goodsmanager.response.ResSearchGoodEntity;
import com.hj.casps.quotes.MyViewHolder;
import com.hj.casps.util.BitmapUtils;

/**
 * Created by Administrator on 2017/5/21.
 */

public class GoodViewHolder extends TypeAbstractViewHolder<DataListBean> {


    private final ImageView iv_image;
    private final TextView tv_name;
    private final RelativeLayout ll;
    private View mV;
    public static GoodViewHolder.SetClickListener lienter;
    public static void setClickListener(GoodViewHolder.SetClickListener lienter) {
        GoodViewHolder.lienter = lienter;
    }
    public GoodViewHolder(View v) {
        super(v);
        int width = HejiaApp.getContext().getResources().getDisplayMetrics().widthPixels;
        width = (width - 25) / 3;
        ViewGroup.LayoutParams layoutParams =  v.getLayoutParams();;
        layoutParams.height = (int) (width * 1.1f);
        layoutParams.width = width;

        iv_image = (ImageView) v.findViewById(R.id.selectpic_pic_img);
        tv_name = (TextView) v.findViewById(R.id.selectpic_tv);
        ll = (RelativeLayout) v.findViewById(R.id.ll_status);
        mV = v;
    }
    @Override
    public void bindViewHolder(DataListBean data, final int postion) {
//            MySearchGoodEntity.DataListBean data = dataListEntities.get(position);
        //TODO

        ll.setVisibility(data.getStatus()==1?View.VISIBLE:View.GONE);

//        Glide.with(HejiaApp.getContext()).load(Constant.SHORTHTTPURL + data.getImgPath()).override(400,300).centerCrop().error(R.mipmap.c_shop).into(iv_image);
        BitmapUtils.LoadImage2(data.getImgPath(),iv_image,400,350);
        tv_name.setText(data.getName());
        mV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienter.onClickPicture(postion);
            }
        });
    }
    public interface SetClickListener {
        void onClickPicture(int pos);
        ;
    }
}
