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
import com.hj.casps.adapter.expressadapter.QuitAdapter;
import com.hj.casps.bankmanage.BillsDetailsActivity;
import com.hj.casps.bankmanage.BillsSearchActivity;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoods.QueryGoodsListLoading;
import com.hj.casps.entity.appordergoods.QueryReturnGoodsEntity;
import com.hj.casps.entity.appordergoods.ReturnGoodsLoading;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.HarvestExpressRespon;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyListView;
import com.hj.casps.ui.MyToast;
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
 * 退货
 */
public class QuitExpress extends ActivityBaseHeader implements OnPullListener,
        View.OnClickListener,
        QuitAdapter.onCheckedkType {
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    @BindView(R.id.payment_list)
    MyListView payment_list;
    @BindView(R.id.payment_scroll)
    ScrollView payment_scroll;
    @BindView(R.id.layout_bottom_layout_1)
    LinearLayout layout_bottom_layout_1;
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
    private QuitAdapter quitAdapter;
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据
    private boolean isSetAdapter = false;//重置时是否保存数据
    private List<QueryReturnGoodsEntity> mList;
    private List<QueryReturnGoodsEntity> dbList;
    private List<ReturnGoodsLoading.OrderListBean> orderList = new ArrayList<>();

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
        setTitle(getString(R.string.activity_quit_express_harvest_title));
        layout_bottom_layout_1.setOnClickListener(this);
        layout_bottom_tv_2.setVisibility(View.GONE);
        layout_bottom_tv_3.setBackgroundResource(R.color.reset_bg);
        layout_bottom_tv_3.setText(getText(R.string.hint_reset_title));
        layout_bottom_tv_3.setOnClickListener(this);
        layout_bottom_tv_4.setBackgroundResource(R.color.text_color_blue);
        layout_bottom_tv_4.setText(getText(R.string.activity_quit_express_harvest_title));
        layout_bottom_tv_4.setOnClickListener(this);
        layout_bottom_check_1.setOnClickListener(this);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);
        dbList = new ArrayList<>();
        layout_head_left_btn.setVisibility(View.GONE);
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
     * 添加本地数据库
     */
    private void addLocality() {
        mList = AppOrderGoodsUtils.getInstance(this).queryReturnGoodsInfo();
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
                waitDialogRectangle.show();
                //重置
                selectAll(false);
                deleteBills(false);
                isSave = false;
                isSetAdapter = false;
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                break;
            case R.id.layout_bottom_tv_4:
                //发货
                buyAll();
                break;
            case R.id.layout_head_right_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_4);
                break;
        }
    }

    /**
     * 获取数据完成后加载数据
     */
    private void refreshSendUI() {
        if (isSave) {
            dbList.clear();
        }
        dbList.addAll(mList);

        if (isSetAdapter) {
            if (pageNo == 0) {
                quitAdapter = new QuitAdapter(this, mList);
                quitAdapter.notifyDataSetChanged();
                payment_list.setAdapter(quitAdapter);
            } else {
                if (pageNo <= ((total - 1) / pageSize)) {
                    quitAdapter.refreshList(mList);
                } else {
                    mLoader.onLoadAll();//加载全部
                }
            }
        } else {
            for (int i = 0; i < dbList.size(); i++) {
                dbList.get(i).clearData();
            }
            quitAdapter = new QuitAdapter(this, dbList);
            quitAdapter.notifyDataSetChanged();
            payment_list.setAdapter(quitAdapter);
        }

        quitAdapter.setOnCheckedkType(this);

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
     * 往数据库保存数据
     */
    private void saveSendData() {
        AppOrderGoodsUtils.getInstance(this).deleteReturnGoodsAll();
        if (dbList.size() > 0) {
            for (int i = 0; i < dbList.size(); i++) {
                AppOrderGoodsUtils.getInstance(this).insertReturnGoodsInfo(dbList.get(i));
            }
        }
    }
    /**
     * 全局选择全选事件
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

    private void buyAll() {
        if (goodsCount > 0) {
            setLisData();
        } else {
            toastSHORT("至少选择一条数据");
        }
    }

    public void showBillsDialog(final List<ReturnGoodsLoading.OrderListBean> orderList) {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_quit_express_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                SubmitReturnExpress(orderList);
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
        getNetWorkSendExpress(pageNo);
        mLoader.onLoadFinished();//加载全部
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
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.ECPRESS_QUIT_ACTIVITY_TYPE);
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
     * 获取保存的数据
     */
    private void setLisData() {
        if (dbList.size() > 0) {
            for (int i = 0; i < dbList.size(); i++) {
                if (dbList.get(i).isCheck()) {
                    if (dbList.get(i).getNum() == null &&
                            !StringUtils.isStrTrue(dbList.get(i).getNum())) {
                        toastSHORT("请填写数量");
                        return;
                    }
                    if (dbList.get(i).getExe_returngoods_num() < Integer.valueOf(dbList.get(i).getNum())) {
                        toastSHORT("超出代收数量");
                        return;
                    }
                    orderList.add(new ReturnGoodsLoading.OrderListBean(
                            dbList.get(i).getOrderId(),
                            dbList.get(i).getNum()));
                }
            }
        }
        showBillsDialog(orderList);
    }


    /**
     * 网络请求获取退货列表
     */
    private void getNetWorkSendExpress(int pageNo) {
        waitDialogRectangle.show();
        QueryGoodsListLoading querySendGoodsLoading = new QueryGoodsListLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100510004,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                Constant.SEARCH_sendgoods_OrdertitleCode,
                Constant.SEARCH_sendgoods_OrderGoodName,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        LogShow(mGson.toJson(querySendGoodsLoading));
        Constant.JSONFATHERRESPON = "HarvestExpressRespon";
        OkGo.post(Constant.QueryReturnGoodsUrl)
                .tag(this)
                .params("param", mGson.toJson(querySendGoodsLoading))
                .execute(new JsonCallBack<HarvestExpressRespon<List<QueryReturnGoodsEntity>>>() {

                    @Override
                    public void onSuccess(HarvestExpressRespon<List<QueryReturnGoodsEntity>> listHarvestExpressRespon, Call call, Response response) {
                        if (listHarvestExpressRespon.rows != null) {
                            total = listHarvestExpressRespon.total;
                            mList = listHarvestExpressRespon.rows;
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
     * 退货操作
     */
    private void SubmitReturnExpress(List<ReturnGoodsLoading.OrderListBean> orderList) {
        waitDialogRectangle.show();
        ReturnGoodsLoading returnGoods = new ReturnGoodsLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC10110051,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                orderList);
        LogShow(mGson.toJson(returnGoods));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.ReturnGoodsUrl)
                .tag(this)
                .params("param", mGson.toJson(returnGoods))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidReturnMessageRespon, Call call, Response response) {
                        if (voidReturnMessageRespon.return_code == 0) {
                            new MyToast(context, "退货成功" + voidReturnMessageRespon.success_num + "条");
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
                        } else {
                            new MyToast(context, "退货失败" + voidReturnMessageRespon.fail_num + "条");
                            toastSHORT(voidReturnMessageRespon.return_message);
                        }
                        deleteBills(true);
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
     * 全选以及取消全选
     *
     * @param isck
     */
    private void selectAll(boolean isck) {
        waitDialogRectangle.show();
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
        quitAdapter.notifyDataSetChanged();
        waitDialogRectangle.dismiss();
    }

    /**
     * 还原参数
     */
    private void deleteBills(boolean isDelete) {
        // 刷新
        goodsCount = 0;
        pageNo = 0;
        //判断要不要清空数据
        if (isDelete)
            dbList.clear();
        mList.clear();
        orderList.clear();
        waitDialogRectangle.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == Constant.QUIT_SEARCH) {
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