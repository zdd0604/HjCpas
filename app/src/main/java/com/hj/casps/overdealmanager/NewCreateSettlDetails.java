package com.hj.casps.overdealmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.overdealadapter.CreateSettlDetailsAdapter;
import com.hj.casps.adapter.overdealadapter.ViewHolderCreaSettlOne;
import com.hj.casps.adapter.overdealadapter.ViewHolderCreaSettlTwo;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordermoney.OverBillsDtailsEntity;
import com.hj.casps.entity.appordermoney.QueryAddressAccountEntity;
import com.hj.casps.entity.appordermoney.QueryAddressAccountLoading;
import com.hj.casps.entity.appordermoney.QueryPayMoneyOrderForSettleEntity;
import com.hj.casps.entity.appsettle.CreateSettleLoading;
import com.hj.casps.entity.appsettle.QueryMyPendingSttleRespon;
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
 * 创建结款单详情
 */
public class NewCreateSettlDetails extends ActivityBaseHeader2 implements View.OnClickListener,
        OnPullListener,
        ViewHolderCreaSettlTwo.onCheckedkType,
        ViewHolderCreaSettlOne.onClickBankNameListener {
    @BindView(R.id.settl_recyclerview)
    RecyclerView settl_recyclerview;
    @BindView(R.id.layout_bottom_check_1)
    CheckBox layout_bottom_check_1;
    @BindView(R.id.layout_bottom_tv_2)
    TextView layout_bottom_tv_2;
    @BindView(R.id.layout_bottom_tv_3)
    TextView layout_bottom_tv_3;
    @BindView(R.id.layout_bottom_tv_4)
    TextView layout_bottom_tv_4;
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;

    private CreateSettlDetailsAdapter createSettlDetailsAdapter;
    private List<OverBillsDtailsEntity> overList;
    private List<QueryPayMoneyOrderForSettleEntity> commList;
    private List<CreateSettleLoading.ListBean> goodsList;
    private List<String> title;
    private List<QueryAddressAccountEntity> buyList;
    private List<QueryAddressAccountEntity> sellList;
    private View contentView;
    private ListView listView;
    private AlertDialog dialog;
    private EditText ctrTimeEditText = null;
    private int goodsCount = 0;
    private MyDialog myDialog;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    getNetWorkSellDatas();
                    break;
                case Constant.HANDLERTYPE_1:
                    refreshLayoutOne();
                    break;
                case Constant.HANDLERTYPE_2:
                    createSettlDetailsAdapter.refreshList(commList);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create_settl_details);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setTitle(getString(R.string.hint_tv_create_section_bills_title));
        layout_bottom_check_1.setOnClickListener(this);
        layout_bottom_tv_2.setOnClickListener(this);
        layout_bottom_tv_3.setOnClickListener(this);
        layout_bottom_tv_4.setOnClickListener(this);
        ViewHolderCreaSettlOne.setOnClickBankNameListener(this);
        ViewHolderCreaSettlTwo.setOnCheckedkType(this);
        findViewById(R.id.layout_head_left_btn).setVisibility(View.GONE);
        layout_head_left_btn.setVisibility(View.GONE);
        layout_head_right_tv.setOnClickListener(this);

        mLoader = new NestRefreshLayout(settl_recyclerview);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

        overList = new ArrayList<>();
        commList = new ArrayList<>();
        title = new ArrayList<>();
        title.add("订单清单");

        if (hasInternetConnected())
            getNetWorkbuyerDatas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_bottom_check_1:
                //全选按钮点击事件
                selectCheck();
                break;
            case R.id.layout_bottom_tv_2:
                //删除订单行
                showDeleteDialog();
                break;
            case R.id.layout_bottom_tv_3:
                intentActivity(AddCommodityInfo.class, Constant.START_ACTIVITY_TYPE);
                break;
            case R.id.layout_bottom_tv_4:
                //确认结款单
                buyAll();
                break;
            case R.id.layout_head_right_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_7);
                break;
        }
    }

    /**
     * 确认收货
     */
    private void buyAll() {
        if (ctrTimeEditText == null) {
            toastSHORT("请选择结款日期");
            return;
        }

        if (goodsCount <= 0) {
            toastSHORT("至少选择一条数据");
            return;
        }
        setDatas();
    }

    /**
     * 获取选中订单
     */
    private void setDatas() {
        goodsList = new ArrayList<>();
        for (int i = 0; i < commList.size(); i++) {
            QueryPayMoneyOrderForSettleEntity entity = commList.get(i);
            if (entity.isCheck()) {
                if (entity.getEndPaymoneyNum() == null ||
                        !StringUtils.isStrTrue(entity.getEndPaymoneyNum()) ||
                        Integer.valueOf(entity.getEndPaymoneyNum()) == 0) {
                    toastSHORT("请填写实付金额");
                    return;
                }
                if (entity.getExePaymoneyNum() < Integer.valueOf(entity.getEndPaymoneyNum())) {
                    toastSHORT("超出待付金额");
                    return;
                }

                goodsList.add(new CreateSettleLoading.ListBean(
                        entity.getId(),
                        entity.getOrdertitleCode(),
                        String.valueOf(entity.getOrdertitleNumber()),
                        entity.getGoodsName(),
                        String.valueOf(entity.getMoney()),
                        String.valueOf(entity.getPaymoneyNum()),
                        String.valueOf(entity.getExePaymoneyNum()),
                        entity.getBuyersId(),
                        entity.getBuyersName(),
                        entity.getSellersId(),
                        entity.getSellersName(),
                        entity.getEndPaymoneyNum()
                ));
            }
        }
        showBillsDialog();
    }


    /**
     * 创建结款单操作
     */
    public void showBillsDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("确定要创建结款单吗？");
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                submitDatas(goodsList);
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
     * 删除操作
     */
    public void showDeleteDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("确定要删除订单吗");
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                deleteDatas();
                myDialog.dismiss();
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
     * 删除选中的订单
     */
    private void deleteDatas() {
        for (int i = 0; i < commList.size(); i++) {
            if (commList.get(i).isCheck()) {
                commList.remove(commList.get(i));
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
            }
        }
    }

    /**
     * 全选以及取消全选
     */
    private void selectCheck() {
        if (layout_bottom_check_1.isChecked()) {
            selectAll(true);
        } else {
            selectAll(false);
        }
    }

    /**
     * 加载网络返回的数据
     */
    private void refreshLayoutOne() {
        OverBillsDtailsEntity oEntity = new OverBillsDtailsEntity();
        oEntity.setBuyersName(Constant.appOrderMoney_buyersName);
        oEntity.setSellersName(Constant.appOrderMoney_sellersName);
        if (buyList != null && buyList.size() > 0)
            oEntity.setBuyersId(buyList.get(0).getBankname());
        if (sellList != null && sellList.size() > 0)
            oEntity.setSellersId(sellList.get(0).getBankname());
        overList.add(oEntity);

        createSettlDetailsAdapter = new CreateSettlDetailsAdapter(this, overList, commList, title);
        settl_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        settl_recyclerview.setAdapter(createSettlDetailsAdapter);
        createSettlDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBuyersName(final TextView textView) {
        if (buyList.size() <= 0)
            return;
        CreateDialog();
        BuyersNameAdapter qadapter = new BuyersNameAdapter(context, buyList);
        listView.setAdapter(qadapter);
        qadapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(buyList.get(position).getBankname() + "\n"
                        + buyList.get(position).getAccountno());
                Constant.appOrderMoney_mmbpayAccount = buyList.get(position).getAccountno();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onSellersName(final TextView textView) {
        if (sellList.size() <= 0)
            return;
        CreateDialog();
        BuyersNameAdapter qadapter = new BuyersNameAdapter(context, sellList);
        listView.setAdapter(qadapter);
        qadapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(sellList.get(position).getBankname() + "\n"
                        + sellList.get(position).getAccountno());
                Constant.appOrderMoney_mmbgetAccount = sellList.get(position).getAccountno();
                dialog.dismiss();
            }
        });
    }


    public class BuyersNameAdapter extends CommonAdapter<QueryAddressAccountEntity> {
        public BuyersNameAdapter(Context context, List<QueryAddressAccountEntity> datas) {
            super(context, datas, R.layout.layout_item_popup);
            this.mContext = context;
        }

        @Override
        public void concert(ViewHolder hooder, QueryAddressAccountEntity queryAddressAccountEntity, int indexPos) {
            TextView textView = hooder.getView(R.id.layout_item_tv);
            textView.setText(queryAddressAccountEntity.getBankname());
        }
    }


    /**
     * 弹框
     */
    public void CreateDialog() {
        // 动态加载一个listview的布局文件进来
        LayoutInflater inflater = LayoutInflater.from(this);
        contentView = inflater.inflate(R.layout.layout_list_popup, null);
        // 给ListView绑定内容
        listView = (ListView) contentView.findViewById(R.id.layout_list_popup);
        dialog = new AlertDialog.Builder(this)
                .setView(contentView)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.show();

    }

    @Override
    public void onSTime(EditText editText) {
        showCalendar(editText);
        ctrTimeEditText = editText;
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        mLoader.onLoadFinished();//加载全部
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == Constant.ADDCOMMDITY) {
            if (Constant.OrderForSettleEntityList.size() > 0) {
                commList = Constant.OrderForSettleEntityList;
                if (commList.size() > 0)
                    selectAll(false);
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
            }
        }
    }

    @Override
    public void onCheckedY(int pos) {
        goodsCount++;
//        toastSHORT("" + pos);
        commList.get(pos - 2).setCheck(true);
    }

    @Override
    public void onCheckedN(int pos) {
        if (goodsCount > 0)
            goodsCount--;
        commList.get(pos - 2).setCheck(false);
    }

    /**
     * 全选以及取消全选
     *
     * @param isck
     */
    private void selectAll(boolean isck) {
        for (int i = 0; i < commList.size(); i++) {
            // 改变boolean
            commList.get(i).setCheck(isck);
            // 如果为选中
            if (commList.get(i).isCheck()) {
                goodsCount++;
            } else {
                goodsCount = 0;
            }
        }
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
    }

    /**
     * 获取收/付款方银行账号
     */
    private void getNetWorkbuyerDatas() {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "QueryMyPendingSttleRespon";
        QueryAddressAccountLoading tEdit = new QueryAddressAccountLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                Constant.appOrderMoney_buyersId);
        LogShow("获取付款方银行账号:" + mGson.toJson(tEdit));
        OkGo.post(Constant.QueryAddressAccountUrl)
                .tag(this)
                .params("param", mGson.toJson(tEdit))
                .execute(new JsonCallBack<QueryMyPendingSttleRespon<List<QueryAddressAccountEntity>>>() {
                    @Override
                    public void onSuccess(QueryMyPendingSttleRespon<List<QueryAddressAccountEntity>> listQueryMyPendingSttleRespon, Call call, Response response) {
                        if (listQueryMyPendingSttleRespon.list != null) {
                            buyList = listQueryMyPendingSttleRespon.list;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        } else {
                            toastSHORT(listQueryMyPendingSttleRespon.return_message);
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
     * 获取收/付款方银行账号
     */
    private void getNetWorkSellDatas() {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "QueryMyPendingSttleRespon";
        QueryAddressAccountLoading tEdit = new QueryAddressAccountLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                Constant.appOrderMoney_sellersId);
        LogShow(" 获取收款方银行账号:" + mGson.toJson(tEdit));
        OkGo.post(Constant.QueryAddressAccountUrl)
                .tag(this)
                .params("param", mGson.toJson(tEdit))
                .execute(new JsonCallBack<QueryMyPendingSttleRespon<List<QueryAddressAccountEntity>>>() {
                    @Override
                    public void onSuccess(QueryMyPendingSttleRespon<List<QueryAddressAccountEntity>> listQueryMyPendingSttleRespon, Call call, Response response) {
                        if (listQueryMyPendingSttleRespon.list != null) {
                            sellList = listQueryMyPendingSttleRespon.list;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
                        } else {
                            toastSHORT(listQueryMyPendingSttleRespon.return_message);
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
     * 新建结款单
     */
    private void submitDatas(List<CreateSettleLoading.ListBean> mList) {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        CreateSettleLoading settleLoading = new CreateSettleLoading(
                publicArg.getSys_member(),
                Constant.getUUID(),
                publicArg.getSys_token(),
                publicArg.getSys_user(),
                Constant.SYS_FUNC,
                Constant.appOrderMoney_mmbgetAccount,
                Constant.appOrderMoney_mmbpayAccount,
                Constant.appOrderMoney_buyersId,
                Constant.appOrderMoney_sellersId,
                Constant.appOrderMoney_buyersName,
                Constant.appOrderMoney_sellersName,
                getEdVaule(ctrTimeEditText),
                mList);
        LogShow("新建结款单：" + mGson.toJson(settleLoading));
        OkGo.post(Constant.CreateSettleUrl)
                .tag(this)
                .params("param", mGson.toJson(settleLoading))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidReturnMessageRespon, Call call, Response response) {
                        toastSHORT(voidReturnMessageRespon.return_message);
                        finish();
                        deleteBills();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        deleteBills();
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    /**
     * 清除数据
     */
    private void deleteBills() {
        goodsCount = 0;
        goodsList.clear();
        waitDialogRectangle.dismiss();
        layout_bottom_check_1.setChecked(false);
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
    }
}
