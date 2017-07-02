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
import com.hj.casps.adapter.overdealadapter.AddCommodityAdapter;
import com.hj.casps.bankmanage.BillsSearchActivity;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordermoney.QueryPayMoneyOrderForSettleEntity;
import com.hj.casps.entity.appordermoney.QueryPayMoneyOrderForSettleLoading;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleRespon;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * zdd
 * 添加订单行
 */
public class AddCommodityInfo extends ActivityBaseHeader2 implements View.OnClickListener,
        AddCommodityAdapter.onCheckedkType {
    @BindView(R.id.add_commodity_info)
    ListView add_commodity_info;

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
    private AddCommodityAdapter addAdapter;
    private List<QueryPayMoneyOrderForSettleEntity> mList;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    setInitView();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commodity_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.hint_tv_add_bills_title));
        AddCommodityAdapter.setOnCheckedkType(this);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setEnabled(true);
        layout_bottom_check_1.setOnClickListener(this);
        layout_bottom_tv_2.setVisibility(View.GONE);
        layout_bottom_tv_3.setVisibility(View.GONE);
        layout_bottom_tv_4.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);

        if (hasInternetConnected()) {
            getNetWorkDatas();
        }
    }

    /**
     * 刷新界面布局
     */
    private void setInitView() {
        addAdapter = new AddCommodityAdapter(this, mList);
        add_commodity_info.setAdapter(addAdapter);
        addAdapter.notifyDataSetChanged();
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
            case R.id.layout_bottom_tv_4:
                setDatas();
                break;

        }
    }

    /**
     * 获取选中的数据
     */
    private void setDatas() {
        Constant.OrderForSettleEntityList.clear();
        if (mList != null)
            for (int i = 0; i < mList.size(); i++) {
                QueryPayMoneyOrderForSettleEntity entity = mList.get(i);
                if (entity.isCheck()) {
                    mList.get(i).setCheck(false);
                    Constant.OrderForSettleEntityList.add(mList.get(i));
                }
            }

        if (Constant.OrderForSettleEntityList.size() == 0) {
            toastSHORT("至少选择一条数据");
            return;
        }
        setResult(Constant.ADDCOMMDITY);
        finish();
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
        if (mList != null && mList.size() > 0)
            if (layout_bottom_check_1.isChecked()) {
                selectAll(true);
            } else {
                selectAll(false);
            }
    }


    @Override
    public void onCheckedY(int pos) {

    }

    @Override
    public void onCheckedN(int pos) {

    }

    @Override
    public void onClickBillsId(int pos) {
        //单据号点击
        bundle.putSerializable(Constant.BUNDLE_TYPE, mList.get(pos).getId());
        intentActivity(BillsSectionDetailsActivity.class, bundle);
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.ADD_SECTION_BILLS_ACTIVITY_TYPE);
        intentActivity(BillsSearchActivity.class, Constant.START_ACTIVITY_TYPE, bundle);
    }

    /**
     * 全选以及取消
     *
     * @param isck
     */
    private void selectAll(boolean isck) {
        for (int i = 0; i < mList.size(); i++) {
            // 改变boolean
            mList.get(i).setCheck(isck);
        }
        addAdapter.notifyDataSetChanged();
    }

    /**
     * 获取代付款订单列表
     */
    private void getNetWorkDatas() {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "QueryMyPendingSttleRespon";
        QueryPayMoneyOrderForSettleLoading tEdit = new QueryPayMoneyOrderForSettleLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                Constant.appOrderMoney_buyersId,
                Constant.appOrderMoney_sellersId,
                Constant.SEARCH_sendgoods_OrderGoodName,
                Constant.SEARCH_sendgoods_OrdertitleCode);
        LogShow(mGson.toJson(tEdit));
        OkGo.post(Constant.QueryPayMoneyOrderForSettleUrl)
                .tag(this)
                .params("param", mGson.toJson(tEdit))
                .execute(new JsonCallBack<QueryMyPendingSttleRespon<List<QueryPayMoneyOrderForSettleEntity>>>() {
                    @Override
                    public void onSuccess(QueryMyPendingSttleRespon<List<QueryPayMoneyOrderForSettleEntity>> listQueryMyPendingSttleRespon, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        if (listQueryMyPendingSttleRespon.list != null) {
                            mList = listQueryMyPendingSttleRespon.list;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == Constant.ADD_SECTION_BILLS_ACTIVITY_TYPE_SEARCH) {
            getNetWorkDatas();
        }
    }

}
