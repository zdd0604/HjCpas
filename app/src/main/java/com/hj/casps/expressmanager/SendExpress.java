package com.hj.casps.expressmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.expressadapter.SendAdapter;
import com.hj.casps.bankmanage.BillsDetailsActivity;
import com.hj.casps.bankmanage.BillsSearchActivity;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoods.QueryGoodsListLoading;
import com.hj.casps.entity.appordergoods.QuerySendGoodsEntity;
import com.hj.casps.entity.appordergoods.SendGoodsOperation;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordergoodsCallBack.SimpleResponse;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyListView;
import com.hj.casps.ui.MyToast;
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
 * 发货
 */
public class SendExpress extends ActivityBaseHeader implements OnPullListener,
        View.OnClickListener,
        SendAdapter.onCheckedkType {
    @BindView(R.id.layout_head_right_btn)
    FancyButton layout_head_right_btn;
    @BindView(R.id.payment_scroll)
    ScrollView payment_scroll;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    @BindView(R.id.payment_list)
    MyListView payment_list;
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.layout_bottom_tv_2)
    TextView layout_bottom_tv_2;
    @BindView(R.id.layout_bottom_tv_3)
    TextView layout_bottom_tv_3;
    @BindView(R.id.layout_bottom_tv_4)
    TextView layout_bottom_tv_4;
    @BindView(R.id.layout_bottom_check_1)
    CheckBox layout_bottom_check_1;
    @BindView(R.id.layout_bottom_check_layout1)
    LinearLayout layout_bottom_check_layout1;
    private SendAdapter sendAdapter;
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据
    private boolean isSetAdapter = true;//重置时是否保存数据
    private List<QuerySendGoodsEntity> mList = new ArrayList<>();
    private List<QuerySendGoodsEntity> dbList = new ArrayList<>();//上啦加载的数据缓存
    private List<SendGoodsOperation.OrderListBean> sendGoodsListList;

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
                case Constant.HANDLERTYPE_2:
                    getNetWorkSendExpress(pageNo);
                    break;
                case Constant.HANDLERTYPE_3:
                    pageNo = 0;
//                    Constant.clearDatas();
                    isSave = true;
                    if (StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrdertitleCode) ||
                            StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrderGoodName)) {
                        isRSave = false;
                    } else {
                        isRSave = true;
                    }
                    getNetWorkSendExpress(pageNo);
                    mLoader.onLoadFinished();//加载全部
                    break;
                case Constant.HANDLERTYPE_4:
                    pageNo++;
//                    Constant.clearDatas();
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
                    break;
                case Constant.HANDLERTYPE_5:
                    waitDialogRectangle.show();
                    //重置
                    selectAll(false);
                    deleteBills(false);
                    isSave = false;
                    isSetAdapter = false;
                    mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.activity_send_express_harvest_title));
        layout_head_left_btn.setVisibility(View.GONE);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_tv_2.setVisibility(View.GONE);
        layout_bottom_tv_3.setBackgroundResource(R.color.reset_bg);
        layout_bottom_tv_3.setText(getText(R.string.hint_reset_title));
        layout_bottom_tv_3.setOnClickListener(this);
        layout_bottom_tv_4.setBackgroundResource(R.color.text_color_blue);
        layout_bottom_tv_4.setText(getText(R.string.activity_send_express_harvest_title));
        layout_bottom_tv_4.setOnClickListener(this);
        layout_bottom_check_1.setOnClickListener(this);
        layout_head_right_btn.setVisibility(View.GONE);
        layout_bottom_check_layout1.setOnClickListener(this);
        sendGoodsListList = new ArrayList<>();
        mLoader = new NestRefreshLayout(payment_scroll);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

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
        if (isSetAdapter) {
            if (pageNo == 0) {
                sendAdapter = new SendAdapter(this, mList);
                payment_list.setAdapter(sendAdapter);
                sendAdapter.notifyDataSetChanged();
            } else {
                if (pageNo <= ((total - 1) / pageSize)) {
                    sendAdapter.refreshList(mList);
                } else {
                    mLoader.onLoadAll();//加载全部
                }
            }
        } else {
            for (int i = 0; i < dbList.size(); i++) {
                dbList.get(i).clearData();
            }
            sendAdapter = new SendAdapter(this, dbList);
            payment_list.setAdapter(sendAdapter);
            sendAdapter.notifyDataSetChanged();
        }

        SendAdapter.setOnCheckedkType(this);

        if (isRSave)
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);

        waitDialogRectangle.dismiss();
        if (dbList != null && dbList.size() > 0)
            for (int i = 0; i < dbList.size(); i++) {
                if (!dbList.get(i).isCheck()) {
                    layout_bottom_check_1.setChecked(false);
                    return;
                }
            }
    }

    /**
     * 往数据库保存数据
     */
    private void saveSendData() {
        AppOrderGoodsUtils.getInstance(this).deleteSendGoodsAll();
        if (dbList.size() > 0 && dbList != null) {
            for (int i = 0; i < dbList.size(); i++) {
                AppOrderGoodsUtils.getInstance(this).insertSendGoodsInfo(dbList.get(i));
            }
        }
    }

    /**
     * 加载本地数据
     */
    private void addLocality() {
        mList = AppOrderGoodsUtils.getInstance(this).querySendGoodsInfo();
        if (mList.size() > 0) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_bottom_check_layout1:
                selectLaytou();
                break;
            case R.id.layout_bottom_check_1:
                selectCheck();
                break;
            case R.id.layout_bottom_tv_3:
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_5);
                break;
            case R.id.layout_bottom_tv_4:
                //发货
                buyAll();
                break;
            case R.id.layout_head_right_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_1);
                break;
        }
    }

    /**
     * 全局选择
     */
    private void selectLaytou() {
        if (dbList != null && dbList.size() > 0)
            if (layout_bottom_check_1.isChecked()) {
                layout_bottom_check_1.setChecked(false);
                selectAll(false);
            } else {
                layout_bottom_check_1.setChecked(true);
                selectAll(true);
            }
    }

    /**
     * 选择框
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
     * 购买选中
     */
    private void buyAll() {
        if (goodsCount > 0) {
            setLisData();
        } else {
            toastSHORT("至少选择一条数据");
        }
    }

    public void showBillsDialog(final List<SendGoodsOperation.OrderListBean> sendGoodsListList) {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_send_express_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                SubmitSendExpress(sendGoodsListList);
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


    /**
     * 获取保存的数据
     */
    private void setLisData() {
        for (int i = 0; i < dbList.size(); i++) {
            QuerySendGoodsEntity querySendGoodsEntity = dbList.get(i);
            if (querySendGoodsEntity.isCheck()) {
                if (!StringUtils.isStrTrue(querySendGoodsEntity.getNum())
                        || Integer.valueOf(dbList.get(i).getNum()) == 0) {
                    toastSHORT("请输入本次发货数量");
                    return;
                }
                if (Integer.valueOf(querySendGoodsEntity.getNum()) >
                        querySendGoodsEntity.getExe_sendgoods_num()) {
                    toastSHORT("超出代发货数量");
                    return;
                }
                sendGoodsListList.add(new SendGoodsOperation.OrderListBean(
                        querySendGoodsEntity.getOrderId(),
                        querySendGoodsEntity.getNum()));
            }
        }
        showBillsDialog(sendGoodsListList);
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_3);
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_4);
    }

    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.ECPRESS_SEND_ACTIVITY_TYPE);
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
        Constant.SEARCH_sendgoods_orderId = dbList.get(pos).getOrdertitleCode();
        intentActivity(BillsDetailsActivity.class);
    }


    /**
     * 网络请求获取发货列表
     */
    private void getNetWorkSendExpress(int pageNo) {
        waitDialogRectangle.show();
        QueryGoodsListLoading querySendGoodsLoading = new QueryGoodsListLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                Constant.SEARCH_sendgoods_OrdertitleCode,
                Constant.SEARCH_sendgoods_OrderGoodName,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        LogShow(mGson.toJson(querySendGoodsLoading));
        Constant.JSONFATHERRESPON = "SendExpressRespon";
        OkGo.post(Constant.QuerySendGoodsUrl)
                .tag(this)
                .params("param", mGson.toJson(querySendGoodsLoading))
                .execute(new JsonCallBack<SimpleResponse<List<QuerySendGoodsEntity>>>() {
                    @Override
                    public void onSuccess(SimpleResponse<List<QuerySendGoodsEntity>> listSendExpressRespon, Call call, Response response) {
//                        mList.clear();
                        if (listSendExpressRespon.rows != null) {
                            total = listSendExpressRespon.total;
                            mList = listSendExpressRespon.rows;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                        if (Constant.public_code) {
                            //退出操作
                            LogoutUtils.exitUser(SendExpress.this);
                        }
                    }
                });
    }

    /**
     * 发货操作
     */
    private void SubmitSendExpress(List<SendGoodsOperation.OrderListBean> param) {
        waitDialogRectangle.show();
        SendGoodsOperation sendGoodsOperation = new SendGoodsOperation(
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                Constant.SYS_FUNC,
                publicArg.getSys_token(),
                Constant.getUUID(),
                publicArg.getSys_name(),
                param);
        LogShow(mGson.toJson(sendGoodsOperation));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.SendGoodsUrl)
                .tag(this)
                .params("param", mGson.toJson(sendGoodsOperation))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidReturnMessageRespon, Call call, Response response) {
                        if (voidReturnMessageRespon.return_code == 0) {
                            new MyToast(context, "发货成功" + voidReturnMessageRespon.success_num + "条");
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
                        } else {
                            new MyToast(context, "发货失败" + voidReturnMessageRespon.fail_num + "条");
                            toastSHORT(voidReturnMessageRespon.return_message);
                        }
                        deleteBills(true);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                        if (Constant.public_code) {
                            //退出操作
                            LogoutUtils.exitUser(SendExpress.this);
                        }
                    }
                });
    }


    /**
     * 全选
     *
     * @param isck
     */
    private void selectAll(boolean isck) {
        waitDialogRectangle.show();
        if (dbList.size() > 0)
            for (int i = 0; i < dbList.size(); i++) {
                // 改变boolean
                dbList.get(i).setCheck(isck);
                // 如果为选中
                if (dbList.get(i).isCheck()) {
                    goodsCount++;
                } else {
                    goodsCount = 0;
                }
            }
        sendAdapter.notifyDataSetChanged();
        waitDialogRectangle.dismiss();
    }

    /**
     * 清除数据
     */
    private void deleteBills(boolean isDelete) {
        // 刷新
        goodsCount = 0;
        pageNo = 0;
        //判断要不要清空数据
        if (isDelete)
            dbList.clear();
        mList.clear();
        sendGoodsListList.clear();
        waitDialogRectangle.dismiss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == Constant.SENDGOODS_SEARCH) {
            pageNo = 0;
            isSave = true;
            isRSave = false;
            getNetWorkSendExpress(pageNo);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }
}