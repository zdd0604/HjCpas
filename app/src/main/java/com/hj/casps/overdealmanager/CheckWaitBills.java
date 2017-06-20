package com.hj.casps.overdealmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.overdealadapter.CheckWaitAdapter;
import com.hj.casps.bankmanage.BillsSearchActivity;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appsettle.AgreeSettleLoading;
import com.hj.casps.entity.appsettle.ModifySettleLoading;
import com.hj.casps.entity.appsettle.QueryPendingSttleGain;
import com.hj.casps.entity.appsettle.QueryPendingSttleLoading;
import com.hj.casps.entity.appsettle.QueryPendingSttleRespon;
import com.hj.casps.ui.MyDialog;
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
 * 待审批结款单
 */
public class CheckWaitBills extends ActivityBaseHeader implements View.OnClickListener,
        CheckWaitAdapter.onCheckedkType,
        OnPullListener {
    @BindView(R.id.check_bills_list)
    ListView check_bills_list;
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    @BindView(R.id.layout_bottom_layout_1)
    LinearLayout layout_bottom_layout_1;
    @BindView(R.id.layout_bottom_check_1)
    CheckBox layout_bottom_check_1;
    @BindView(R.id.layout_bottom_tv_2)
    TextView layout_bottom_tv_2;
    @BindView(R.id.layout_bottom_tv_3)
    TextView layout_bottom_tv_3;
    @BindView(R.id.layout_bottom_tv_4)
    TextView layout_bottom_tv_4;
    @BindView(R.id.layout_bottom_check_layout1)
    LinearLayout layout_bottom_check_layout1;

    private CheckWaitAdapter cAdapter;
    private List<QueryPendingSttleGain> mList;
    private List<QueryPendingSttleGain> dbList;
    private MyDialog myDialog;
    private int pageNo = 0;//	int	开始行
    private int pageSize = 10;//	int	页条数
    private int total;
    private int goodsCount = 0;
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据
    private List<ModifySettleLoading.ModifySettleEntity> modifySettleList = new ArrayList<>();
    private List<AgreeSettleLoading.IDEntity> iDEntityList = new ArrayList<>();

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    refreshUI();
                    break;
                case Constant.HANDLERTYPE_1:
                    saveData();
                    break;
                case Constant.HANDLERTYPE_2:
                    pageNo = 0;
                    if (hasInternetConnected())
                        getQueryMmbBankAccountGainDatas(pageNo);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_wait_bills);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        layout_head_left_btn.setVisibility(View.GONE);
        setTitle(getString(R.string.activity_wiat_check_bills_title));
        layout_bottom_check_1.setOnClickListener(this);
        layout_bottom_tv_2.setOnClickListener(this);
        layout_bottom_tv_3.setOnClickListener(this);
        layout_bottom_tv_4.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_tv_2.setText(R.string.hint_wait_layout_title_3);
        layout_bottom_tv_3.setText(R.string.cooperate_agree);
        layout_bottom_tv_4.setText(R.string.cooperate_deny);

        dbList = new ArrayList<>();
        mLoader = new NestRefreshLayout(check_bills_list);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        if (hasInternetConnected()) {
            getQueryMmbBankAccountGainDatas(pageNo);
        } else {
            addLocality();
        }
    }

    /**
     * 同时可将数据进行缓存
     */
    private void refreshUI() {
        if (isSave) {
            dbList.clear();
        }

        dbList.addAll(mList);

        if (pageNo == 0) {
            cAdapter = new CheckWaitAdapter(this, dbList);
            check_bills_list.setAdapter(cAdapter);
            cAdapter.notifyDataSetChanged();
        } else {
            if (pageNo <= ((total - 1) / pageSize)) {
                cAdapter.refreshList(mList);
            } else {
                mLoader.onLoadAll();//加载全部
            }
        }
        CheckWaitAdapter.setOnCheckedkType(this);
        if (isRSave)
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);

        waitDialogRectangle.dismiss();

        for (int i = 0; i < dbList.size(); i++) {
            if (!dbList.get(i).isCheck()) {
                layout_bottom_check_1.setChecked(false);
                return;
            }
        }
    }

    /**
     * 保存数据
     */
    private void saveData() {
        AppOrderGoodsUtils.getInstance(this).deleteQueryPendingSttleGainAll();
        if (dbList.size() > 0) {
            for (int i = 0; i < dbList.size(); i++) {
                AppOrderGoodsUtils.getInstance(this).insertQueryPendingSttleGainInfo(dbList.get(i));
            }
        }
    }

    /**
     * 加载本地数据
     */
    private void addLocality() {
        dbList = AppOrderGoodsUtils.getInstance(this).queryQueryPendingSttleGainInfo();
        if (dbList.size() > 0) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_head_right_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_8);
                break;
            case R.id.layout_bottom_check_layout1:
                selectLaytou();
                break;
            case R.id.layout_bottom_check_1:
                selectCheck();
                break;
            case R.id.layout_bottom_tv_2:
                //编辑提交
                commitEdit();
                break;
            case R.id.layout_bottom_tv_3:
                //待审批结款单-同意
                commitAgreeSettle(getString(R.string.dialog_wait_layout_title_5), Constant.AgreeSettleUrl);
                break;
            case R.id.layout_bottom_tv_4:
                //待审批结款单-拒绝
                commitAgreeSettle(getString(R.string.dialog_wait_layout_title_4), Constant.RefuseSettleUrl);
                break;
        }
    }

    /**
     * 全局选择全选事件
     */
    private void selectLaytou() {
        if (mList != null && mList.size() > 0)
            if (layout_bottom_check_1.isChecked()) {
                layout_bottom_check_1.setChecked(false);
                selectAll(false);
            } else {
                layout_bottom_check_1.setChecked(true);
                selectAll(true);
            }
    }

    /**
     * 选择框全选事件
     */
    private void selectCheck() {
        if (dbList != null && dbList.size() > 0)
            if (layout_bottom_check_1.isChecked()) {
                selectAll(true);
            } else {
                selectAll(false);
            }
    }

    /**
     * 获取选择的数据
     *
     * @param msg
     * @param url
     */
    private void commitAgreeSettle(String msg, String url) {
        if (dbList.size() <= 0) {
            toastSHORT("数据为空");
            return;
        }

        for (int i = 0; i < dbList.size(); i++) {
            if (dbList.get(i).isCheck()) {
                iDEntityList.add(new AgreeSettleLoading.IDEntity(dbList.get(i).getId()));
            }
        }

        if (iDEntityList.size() <= 0) {
            toastSHORT("请选择数据");
            return;
        }
        showBillsDialog(msg, url);
    }

    /**
     * 编辑提交
     */
    private void commitEdit() {
        if (dbList.size() <= 0) {
            toastSHORT("没有数据");
            return;
        }

        for (int i = 0; i < dbList.size(); i++) {
            if (dbList.get(i).isCheck()) {
                modifySettleList.add(new ModifySettleLoading.ModifySettleEntity(
                        dbList.get(i).getId(),
                        String.valueOf(dbList.get(i).getSettleCode()),
                        dbList.get(i).getMyTime(),
                        String.valueOf(dbList.get(i).getMyMoney())));
            }
        }
        if (modifySettleList.size() <= 0) {
            toastSHORT("请选择数据");
            return;
        }
        LogShow("=============" + modifySettleList.toString());
        commitModifySettle();
    }


    /**
     * 获取待审批的类表
     */
    private void getQueryMmbBankAccountGainDatas(int pageNo) {
        waitDialogRectangle.show();
        QueryPendingSttleLoading param = new QueryPendingSttleLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100810002,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                Constant.SEARCH_sendgoods_OrdertitleCode,
                Constant.SEARCH_sendgoods_OrderGoodName,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        LogShow(mGson.toJson(param));
        OkGo.post(Constant.QueryPendingSttleUrl)
                .tag(this)
                .params("param", mGson.toJson(param))
                .execute(new JsonCallBack<QueryPendingSttleRespon<List<QueryPendingSttleGain>>>() {

                    @Override
                    public void onSuccess(QueryPendingSttleRespon<List<QueryPendingSttleGain>>
                                                  listQueryOppositeListRespon, Call call, Response response) {
                        if (listQueryOppositeListRespon.list != null) {
                            total = listQueryOppositeListRespon.total;
                            mList = listQueryOppositeListRespon.list;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });
    }


    /**
     * 待审批结款单-编辑提交
     */
    private void commitModifySettle() {
        ModifySettleLoading param = new ModifySettleLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100810002,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                modifySettleList);
        LogShow(mGson.toJson(param));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.ModifySettleUrl)
                .tag(this)
                .params("param", mGson.toJson(param))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidReturnMessageRespon, Call call, Response response) {
                        toastSHORT(voidReturnMessageRespon.return_message);
                        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    /**
     * 待审批结款单-同意
     * 待审批结款单-拒绝
     */
    private void commitAgreeSettleRefuseSettle(String url) {
        waitDialogRectangle.show();
        AgreeSettleLoading param = new AgreeSettleLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100810002,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                iDEntityList);
        LogShow(mGson.toJson(param));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(url)
                .tag(this)
                .params("param", mGson.toJson(param))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {

                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidReturnMessageRespon, Call call, Response response) {
                        toastSHORT(voidReturnMessageRespon.return_message);
                        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.WAIT_CHECK_BILLS_ACTIVITY_TYPE);
        intentActivity(BillsSearchActivity.class, Constant.START_ACTIVITY_TYPE, bundle);
    }

    @Override
    public void onCheckedY(int pos) {
        goodsCount++;
    }

    @Override
    public void onCheckedN(int pos) {
        if (goodsCount > 0)
            goodsCount--;
    }

    @Override
    public void onBillsIDItemCilckListener(int pos) {
        bundle.putString(Constant.BUNDLE_TYPE, String.valueOf(dbList.get(pos).getId()));
        intentActivity(BillsSectionDetailsActivity.class, bundle);
    }


    public void showBillsDialog(String msg, final String url) {
        myDialog = new MyDialog(this);
        myDialog.setMessage(msg);
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                commitAgreeSettleRefuseSettle(url);
            }
        });
        myDialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }


    private void selectAll(boolean isck) {
        for (int i = 0; i < dbList.size(); i++) {
            // 改变boolean
            dbList.get(i).setCheck(isck);
        }
        cAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageNo = 0;
//        Constant.clearDatas();
        isSave = true;
        if (StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrdertitleCode) ||
                StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrderGoodName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }
        getQueryMmbBankAccountGainDatas(pageNo);
        mLoader.onLoadFinished();//加载全部
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageNo++;
//        Constant.clearDatas();
//        isSave = true; //上啦缓存数据
        isSave = false;
        if (StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrdertitleCode) ||
                StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrderGoodName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }

        if (dbList.size() < total)
            getQueryMmbBankAccountGainDatas(pageNo);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE &&
                resultCode == Constant.WAIT_CHECK_BILLS_ACTIVITY_TYPE_SEARCH) {
            pageNo = 0;
            isSave = true;
            isRSave = false;
            getQueryMmbBankAccountGainDatas(pageNo);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }
}
