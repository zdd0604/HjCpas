package com.hj.casps.commodity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hj.casps.R;
import com.hj.casps.adapter.goodmanager.GoodClassAdapter;
import com.hj.casps.adapter.goodmanager.GoodViewHolder;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.goodsmanager.goodsmanagerCallBack.GoodToUpdateCallBack;
import com.hj.casps.entity.goodsmanager.request.RequestSearchGood;
import com.hj.casps.entity.goodsmanager.request.RequestUpdateStatus;
import com.hj.casps.entity.goodsmanager.response.DataListBean;
import com.hj.casps.entity.goodsmanager.response.GoodInfoEntity;
import com.hj.casps.entity.goodsmanager.response.GoodtoUpdateGain;
import com.hj.casps.entity.goodsmanager.response.ResSearchGoodEntity;
import com.hj.casps.entity.paymentmanager.response.WytUtils;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 商品列表页
 * Created by YaoChen on 2017/4/13.
 */

public class ActivityGoodsClass extends ActivityBaseHeader2 implements View.OnClickListener, GoodViewHolder.SetClickListener, OnPullListener {
    private static List<DataListBean> mList = new ArrayList<>();
    private static int mPostion = -1;
    @BindView(R.id.Goodsclass_grid)
    GridView gridView;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.Goodsclass_add_Btn)
    FancyButton top_add_btn;
    @BindView(R.id.Goodsclass_do_desc_tv)
    TextView top_desc_Tv;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.Goodsclass_top_Re)
    RelativeLayout Goodsclass_top_Re;
    private PopupWindow sharepopupWindow;
    private View contentView;
    private int pageno = 0;
    private int pagesize = 10;
    private int fra_type;
    private boolean isReSave = true;//是否缓存
    private String categoryId;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
            }
        }
    };
    private GoodClassAdapter adapter;
    private int totalCount;
//刷新UI
    private void refreshUi() {
        if (pageno == 0) {
            if (adapter == null) {
                adapter = new GoodClassAdapter(context, null);
                rv.setAdapter(adapter);
            }
            adapter.setDatas(mList);
        } else {
            if (pageno <= (totalCount - 1) / pagesize) {
                adapter.addRes(mList);
                mLoader.onLoadFinished();
            } else {
                mLoader.onLoadAll();
            }
        }
        if (isReSave) {
            saveLocalData();
        }


    }

    //保存数据到数据库
    private void saveLocalData() {
        List<DataListBean> listBeen = WytUtils.getInstance(this).QuerytDataListBeanInfo(categoryId);
        for (int i = 0; i < listBeen.size(); i++) {
            WytUtils.getInstance(this).deleteDataListBeanInfo(listBeen.get(i));
        }
        List<DataListBean> datas = adapter.getDatas();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                DataListBean entity = datas.get(i);
                entity.setId(Constant.getUUID());
                entity.setCategoryId(categoryId);
                WytUtils.getInstance(this).insertDataListBeanInfo(entity);
            }
        }
    }
    //从本地数据库加载数据
    private void getLocalData() {
        List<DataListBean> listBeen = WytUtils.getInstance(this).QuerytDataListBeanInfo(categoryId);
        if (listBeen != null && listBeen.size() > 0) {
            mList.clear();
            mList.addAll(listBeen);
            refreshUi();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsclass);
        categoryId = getIntent().getStringExtra(Constant.CATEGORY_ID);
        ButterKnife.bind(this);
        setTitle(getString(R.string.goods_class));
        initView();
        //判断是否有网络
        if (hasInternetConnected()) {
            initData(pageno);
        } else {
            //从本地数据库加载数据
            getLocalData();
        }
        setTitleRight(null, null);
    }


        //请求数据接口
    private void initData(final int pageno) {
        PublicArg p = Constant.publicArg;
        RequestSearchGood r = new RequestSearchGood(p.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                p.getSys_user(),
                p.getSys_member(),
                categoryId,
                String.valueOf(pageno + 1),
                String.valueOf(pagesize));
        String param = mGson.toJson(r);
        Constant.JSONFATHERRESPON = "SearchGoodGain";
        waitDialogRectangle.show();
        OkGo.post(Constant.SearchGoodUrl).params("param", param).execute(new StringCallback() {
                                                                             @Override
              public void onSuccess(String data, Call call, Response response) {
                  waitDialogRectangle.dismiss();
                  ResSearchGoodEntity entity = GsonTools.changeGsonToBean(data, ResSearchGoodEntity.class);
                  if (entity.getReturn_code() == 0 && entity.getDataList() != null && entity.getDataList().size() > 0) {
                      mList = entity.getDataList();
                      totalCount = entity.getTotalCount();
                      refreshUi();
                  }else if(entity.getReturn_code()==1101||entity.getReturn_code()==1102){
                      toastSHORT(entity.getReturn_message());
                      LogoutUtils.exitUser(ActivityGoodsClass.this);
                  }
                     }
              @Override
              public void onError(Call call, Response response, Exception e) {
                  super.onError(call, response, e);
                  waitDialogRectangle.dismiss();
              }
                                                                         }

        );
    }


    private void initView() {

        fra_type = getIntent().getIntExtra("fra", 0);
        if (fra_type == 1) {
            //是否显示上部的   添加头部布局
            Goodsclass_top_Re.setVisibility(View.GONE);
        }
        showSharePopupWindow();
        top_add_btn.setOnClickListener(this);
        top_desc_Tv.setOnClickListener(this);
//        setGridViewWidth(gridView);
        int height = gridView.getHeight();
        mLoader = new NestRefreshLayout(rlContent);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        GoodViewHolder.setClickListener(this);

        GridLayoutManager gl = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(gl);
        //添加分割线
        rv.addItemDecoration(new SelectPicture02.SelectPicture02ItemDecoration(this));
        log("heigth=" + height);

    }
          /*选择图片编辑*/
    private void choosePictureEdit(int position) {
        if (fra_type == 1) {
            DataListBean d = adapter.getDatas().get(position);
            Constant.GOODS_ID = d.getGoodsId();
            Constant.GOODS_NAME = d.getName();
            setResult(RESULT_OK);
            finish();

        } else {
            if (!sharepopupWindow.isShowing()) {
                DataListBean d = adapter.getDatas().get(position);
                ActivityGoodsClass.mPostion = position;
                ImageView addIv = (ImageView) contentView.findViewById(R.id.gooddeclass_pop_goodpic);
                TextView tv_goodname = (TextView) contentView.findViewById(R.id.gooddeclass_pop_goodname);
                tv_goodname.setText(d.getName());
                TextView tv = (TextView) contentView.findViewById(R.id.gooddeclass_do_do);
                tv.setText(d.getStatus() == 0 ? "禁用" : "启用");
                Drawable drawable = getResources().getDrawable(R.mipmap.cz_enable);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv.setCompoundDrawables(null, drawable, null, null);

                Glide.with(ActivityGoodsClass.this).load(Constant.SHORTHTTPURL + d.getImgPath()).into(addIv);
                sharepopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {

        super.onRestart();
        if(Constant.isFreshGood){
            initData(pageno);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Constant.isFreshGood=false;
    }
//        显示底部的编辑 详情  启用的PopupWindow
    private void showSharePopupWindow() {
        contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_goodsclass, null);

        contentView.findViewById(R.id.gooddeclass_pop_do_view).setOnClickListener(this);
        contentView.findViewById(R.id.gooddeclass_pop_do_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.gooddeclass_do_edit).setOnClickListener(this);
        contentView.findViewById(R.id.gooddeclass_do_detail).setOnClickListener(this);
        contentView.findViewById(R.id.gooddeclass_do_do).setOnClickListener(this);

        sharepopupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        sharepopupWindow.setAnimationStyle(R.style.take_photo_anim);
        sharepopupWindow.setTouchable(true);
        sharepopupWindow.setOutsideTouchable(true);
        sharepopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //商品启用停用
            case R.id.gooddeclass_do_do:
                if(!hasInternetConnected()){
                    toastSHORT("没有网络，无法操作");
                    return;
                }
                sharepopupWindow.dismiss();
                updateStatus();
                break;
            case R.id.gooddeclass_pop_do_view:
                sharepopupWindow.dismiss();
                break;
            case R.id.gooddeclass_pop_do_cancel:
                sharepopupWindow.dismiss();
                break;
            case R.id.gooddeclass_do_edit:
                if(!hasInternetConnected()){
                    toastSHORT("没有网络，无法操作");
                    return;
                }
                List<DataListBean> datas = adapter.getDatas();
                sharepopupWindow.dismiss();
                Intent intent = new Intent(ActivityGoodsClass.this, ActivityEditGoods.class);
                if (datas.size() > 0 && mPostion != -1) {
                    String goodsId = datas.get(mPostion).getGoodsId();
                    intent.putExtra(Constant.INTENT_GOODSID, goodsId);
                    intent.putExtra(Constant.INTENTISADDGOODS, false);
                    startActivity(intent);
                }

                break;
            //商品详情
            case R.id.gooddeclass_do_detail:
                sharepopupWindow.dismiss();
                List<DataListBean> datas2 = adapter.getDatas();
                Intent intentDetail = new Intent(ActivityGoodsClass.this, ActivityGoodDetail.class);
                if (datas2.size() > 0 && mPostion != -1) {
                    String goodsId = datas2.get(mPostion).getGoodsId();
                    intentDetail.putExtra(Constant.INTENT_GOODSID, goodsId);
                    startActivity(intentDetail);
                }
                break;
            //商品添加
            case R.id.Goodsclass_add_Btn:
                Intent i = new Intent(ActivityGoodsClass.this, ActivityEditGoods.class);
                i.putExtra(Constant.INTENTISADDGOODS, true);

                startActivity(i);

                break;
            case R.id.Goodsclass_do_desc_tv:
                Log.i("desc", "操作说明");
                CreateDialog(Constant.DIALOG_CONTENT_29);
                break;
        }

    }

    //调用接口 商品启用停用操作
    private void updateStatus() {
        String timeUUID = Constant.getTimeUUID();
        if(timeUUID.equals("")){
            toastSHORT(getString(R.string.time_out));
            return;
        }
        final List<DataListBean> datas = adapter.getDatas();
        final List<DataListBean> newDatas = new ArrayList<>();
        String goodsId = datas.get(mPostion).getGoodsId();
        final int statusInt = datas.get(mPostion).getStatus();
        //状态是可用，就停用      停用就启用
        String status = statusInt == 0 ? "disabled" : "enabled";
        PublicArg publicArg = Constant.publicArg;
        RequestUpdateStatus requestToUpdateGood = new RequestUpdateStatus(publicArg.getSys_token(), timeUUID, Constant.SYS_FUNC, publicArg.getSys_user(), publicArg.getSys_member(), goodsId, status);
        String param = mGson.toJson(requestToUpdateGood);
        OkGo.post(Constant.UpdateStatusUrl).params("param", param).execute(new GoodToUpdateCallBack<GoodtoUpdateGain<GoodInfoEntity>>() {
            @Override
            public void onSuccess(GoodtoUpdateGain<GoodInfoEntity> goodInfo, Call call, Response response) {
                super.onSuccess(goodInfo, call, response);
                log(goodInfo.toString());
                if (goodInfo.return_code == 0) {
                    datas.get(mPostion).setStatus(statusInt == 0 ? 1 : 0);
                    newDatas.addAll(datas);
                    adapter.setDatas(newDatas);
//                    adapter.notifyItemChanged(mPostion);
                    toastSHORT("操作成功");
                }

                else {
                    toastSHORT(goodInfo.return_message);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (Constant.public_code){
                    LogoutUtils.exitUser(ActivityGoodsClass.this);
                }
            }
        });

    }

    //选择图片
    @Override
    public void onClickPicture(int pos) {
        choosePictureEdit(pos);
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageno = 0;
        initData(pageno);
        mLoader.onLoadFinished();
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageno++;
        initData(pageno);
        mLoader.onLoadFinished();
    }

   /* class GoodClassListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mList != null ? mList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
//                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//                convertView=   inflater.inflate(R.layout.selectpic_griditem,parent,false);
                convertView = View.inflate(parent.getContext(), R.layout.selectpic_griditem, null);
                viewHolder.im = (ImageView) convertView.findViewById(R.id.selectpic_pic_img);
                viewHolder.tv = (TextView) convertView.findViewById(R.id.selectpic_tv);
                viewHolder.ll = (RelativeLayout) convertView.findViewById(R.id.selectpic_ischeck);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            DataListEntity data = mList.get(position);
//            MySearchGoodEntity.DataListBean data = mList.get(position);
            //TODO
            Glide.with(ActivityGoodsClass.this).load(Constant.SHORTHTTPURL + data.getImgPath()).error(R.mipmap.c_shop).into(viewHolder.im);
            viewHolder.tv.setText(data.getName());
            if (data.getStatus() == 1) {
                viewHolder.ll.setVisibility(View.VISIBLE);
            }

           int width = getResources().getDisplayMetrics().widthPixels;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                width -= gridView.getHorizontalSpacing() * 2;
            }
            width = width / 3;
            ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
            layoutParams.height = (int) (width * 1.1f);
            convertView.setLayoutParams(layoutParams);
            return convertView;
        }

    }

    class ViewHolder {
//            convertView.setLayoutParams(new ViewGroup.LayoutParams());
//            View view = getView(position, convertView, parent);
        ImageView im;
        TextView tv;
        RelativeLayout ll;

    }*/


}
