package com.hj.casps.overdealmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.overdealadapter.CreateSectionAdapter;
import com.hj.casps.bankmanage.BillsSearchActivity;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleEntity;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleLoading;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleRespon;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;

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
 * 新创建结款单列表
 */
public class CreateSectionBills extends ActivityBaseHeader implements View.OnClickListener,
        CreateSectionAdapter.OnClickSectionListener,
        OnPullListener {
    @BindView(R.id.create_bills_list)
    ListView create_bills_list;
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    private CreateSectionAdapter createSectionAdapter;
    private List<QueryMyPendingSttleEntity> mList;
    private List<QueryMyPendingSttleEntity> dbList;
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据

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
        setContentView(R.layout.activity_create_section_bills);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.activity_create_section_bills_title));
        layout_head_left_btn.setText(getString(R.string.hint_tv_create_section_bills_title));
        layout_head_left_btn.setOnClickListener(this);
        layout_head_right_tv.setOnClickListener(this);
        mLoader = new NestRefreshLayout(create_bills_list);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        dbList = new ArrayList<>();
        if (hasInternetConnected()) {
            getNetWorkSendExpress(pageNo);
        } else {
            addLocality();
        }
    }


    /**
     * 刷新数据
     */
    private void refreshSendUI() {
        if (isSave) {
            dbList.clear();
        }

        dbList.addAll(mList);

        if (pageNo == 0) {
            createSectionAdapter = new CreateSectionAdapter(this, dbList);
            create_bills_list.setAdapter(createSectionAdapter);
            createSectionAdapter.notifyDataSetChanged();
        } else {
            if (pageNo < (total / pageSize)) {
                createSectionAdapter.refreshList(mList);
            } else {
                mLoader.onLoadAll();//加载全部
            }
        }

        //结款单的单号点击回调事件
        CreateSectionAdapter.setOnClickSectionListener(this);

        if (isRSave)
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);

        waitDialogRectangle.dismiss();
    }

    /**
     * 保存数据
     */
    private void saveSendData() {
        if (dbList.size() > 0) {
            AppOrderGoodsUtils.getInstance(this).deleteMyPendingSttleAll();
            for (int i = 0; i < mList.size(); i++) {
                AppOrderGoodsUtils.getInstance(this).insertMyPendingSttleInfo(dbList.get(i));
            }
        }
    }

    /**
     * 添加本地数据
     */
    private void addLocality() {
        mList = AppOrderGoodsUtils.getInstance(this).queryMyPendingSttleInfo();
        if (mList.size() > 0)
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_head_left_btn:
                intentActivity(this, NewCreateSettlement.class);
                break;
            case R.id.layout_head_right_tv:
                CreateDialog(Constant.DIALOG_CONTENT_6);
                break;
        }
    }

    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.CREATE_SECTION_BILLS_ACTIVITY_TYPE);
        intentActivity(BillsSearchActivity.class, Constant.START_ACTIVITY_TYPE, bundle);
    }

    @Override
    public void onClickBillsId(int pos) {
        //单据号点击
        bundle.putSerializable(Constant.BUNDLE_TYPE, mList.get(pos).getId());
        intentActivity(BillsSectionDetailsActivity.class, bundle);
    }


    /**
     * 网络请求获取发货列表
     */
    private void getNetWorkSendExpress(int pageNo) {
        waitDialogRectangle.show();
        QueryMyPendingSttleLoading qEntity = new QueryMyPendingSttleLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                Constant.SEARCH_sendgoods_OrdertitleCode,
                Constant.SEARCH_sendgoods_OrderGoodName,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        LogShow(mGson.toJson(qEntity));
        Constant.JSONFATHERRESPON = "QueryMyPendingSttleRespon";
        OkGo.post(Constant.QueryMyPendingSttleUrl)
                .tag(this)
                .params("param", mGson.toJson(qEntity))
                .execute(new JsonCallBack<QueryMyPendingSttleRespon<List<QueryMyPendingSttleEntity>>>() {
                    @Override
                    public void onSuccess(QueryMyPendingSttleRespon<List<QueryMyPendingSttleEntity>> listSendExpressRespon, Call call, Response response) {
                        if (listSendExpressRespon.list != null) {
                            mList = listSendExpressRespon.list;
                            total = listSendExpressRespon.pagecount;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                        if (Constant.public_code){
                            //退出操作
                            LogoutUtils.exitUser(CreateSectionBills.this);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE &&
                resultCode == Constant.CREATE_MYPENDINGSTTLE) {
            pageNo = 0;
            isSave = true;
            isRSave = false;
            getNetWorkSendExpress(pageNo);
        }
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageNo = 0;
        isSave = true;
        if (StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrdertitleCode) ||
                StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrderGoodName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }
        getNetWorkSendExpress(pageNo);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageNo++;
        isSave = false;
        if (StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrdertitleCode) ||
                StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrderGoodName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }

        if (dbList.size() < total)
            getNetWorkSendExpress(pageNo);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }
}
