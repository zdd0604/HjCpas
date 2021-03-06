package com.hj.casps.overdealmanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hj.casps.R;
import com.hj.casps.adapter.overdealadapter.SectionDetailsAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appsettle.CheckWaitBillsEntity;
import com.hj.casps.entity.appsettle.QuerysettleDetailLoading;
import com.hj.casps.entity.appsettle.QuerysettleDetailOneGain;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 订单详情页
 * QuerysettleDetailUrl
 */
public class BillsSectionDetailsActivity extends ActivityBaseHeader2 {
    @BindView(R.id.bills_details_rlview)
    RecyclerView bills_details_rlview;
    private SectionDetailsAdapter mRecyclerAdapter;
    private String id;
    private List<QuerysettleDetailOneGain> listOne;
    private List<QuerysettleDetailOneGain.ListBean> listTow;
    private List<String> listTitle;
    private int SettleCode;//订单号
    private String billJson;//字符串
    private boolean isSaveDats = true;//是否保存数据

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    refreshSendUI();
                    break;
                case Constant.HANDLERTYPE_1:
                    saveSendData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_aetails);
        ButterKnife.bind(this);
        getBundleDatas();
    }

    /**
     * 不同的布局设置ITEM
     */
    public void getBundleDatas() {
        id = getIntent().getStringExtra(Constant.BUNDLE_TYPE);
        listOne = new ArrayList<>();
        listTitle = new ArrayList<>();
        listTitle.add("结款单清单");
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.activity_over_bills_details_title));
        titleRight.setVisibility(View.GONE);
        bills_details_rlview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (hasInternetConnected()) {
            isSaveDats = true;
            getNetWorkSendExpress();
        } else {
            isSaveDats = false;
            addLocality();
        }
    }

    /**
     * 刷新数据
     */
    private void refreshSendUI() {
        mRecyclerAdapter = new SectionDetailsAdapter(this, listOne, listTow, listTitle);
        bills_details_rlview.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();
        if (isSaveDats)
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
    }

    /**
     * 保存数据
     */
    private void saveSendData() {
        AppOrderGoodsUtils.getInstance(this).deleteCheckWaitBillsEntity(id);
        CheckWaitBillsEntity checkWaitBillsEntity = new CheckWaitBillsEntity(Constant.getUUID(), id, billJson);
        AppOrderGoodsUtils.getInstance(this).insertCheckWaitBillsEntity(checkWaitBillsEntity);
    }

    /**
     * 添加本地数据
     */
    private void addLocality() {
        List<CheckWaitBillsEntity> list = AppOrderGoodsUtils.getInstance(this).queryCheckWaitBillsEntity(id);

        if (list.size() == 0)
            return;

        for (int i = 0; i < list.size(); i++) {
            CheckWaitBillsEntity entity = list.get(i);
            String json = entity.getBillsJson();
            QuerysettleDetailOneGain oneGain = mGson.fromJson(json, QuerysettleDetailOneGain.class);
            listOne.add(oneGain);
            listTow = oneGain.getList();
        }
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
    }

    /**
     * 获取结款单详情
     */
    private void getNetWorkSendExpress() {
        waitDialogRectangle.show();
        QuerysettleDetailLoading qEntity = new QuerysettleDetailLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                id);
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        LogShow(mGson.toJson(qEntity));
        OkGo.post(Constant.QuerysettleDetailUrl)
                .tag(this)
                .params("param", mGson.toJson(qEntity))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        QuerysettleDetailOneGain oneGain = mGson.fromJson(s, QuerysettleDetailOneGain.class);
                        if (oneGain.getReturn_code() == 0) {
                            SettleCode = oneGain.getSettleCode();
                            billJson = s;
                            listOne.add(oneGain);
                            listTow = oneGain.getList();
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        } else {
                            toastSHORT(oneGain.getReturn_message());
                        }
                        waitDialogRectangle.dismiss();
                    }
                });
    }

}
