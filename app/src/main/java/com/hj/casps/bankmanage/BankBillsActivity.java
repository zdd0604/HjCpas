package com.hj.casps.bankmanage;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.expressadapter.ExpressAdapte;
import com.hj.casps.adapter.payadapter.AddCardAdapte;
import com.hj.casps.app.HejiaApp;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.appordergoods.QueryMmbWareHouseLoading;
import com.hj.casps.entity.appordergoods.ToEditMmbWarehouseLoading;
import com.hj.casps.entity.appordergoods.WarehouseEntity;
import com.hj.casps.entity.appordergoods.WarehouseEntityDao;
import com.hj.casps.entity.appordergoodsCallBack.AddressEditRespon;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordergoodsCallBack.SimpleResponse;
import com.hj.casps.entity.appordermoney.MmbBankAccountEntity;
import com.hj.casps.entity.appordermoney.QueryMmbBankAccountRespon;
import com.hj.casps.entity.paymentmanager.RequestBackAccount;
import com.hj.casps.entity.paymentmanager.response.WytUtils;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.exception.OkGoException;

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
 * 账户管理列表
 */
public class BankBillsActivity extends ActivityBaseHeader implements View.OnClickListener,
        AddCardAdapte.onClickItem,
        ExpressAdapte.onClickItem,
        OnPullListener {
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.add_card_btn)
    TextView add_card_btn;
    @BindView(R.id.bank_card_list)
    ListView bank_card_list;

    private AddCardAdapte addCardAdapte;
    //银行账户列表
    private List<MmbBankAccountEntity> mBankList = new ArrayList<>();
    private List<MmbBankAccountEntity> dbBankList = new ArrayList<>();
    private List<MmbBankAccountEntity> pubList = new ArrayList<>();

    private ExpressAdapte expressAdapter;
    //收发货地址列表
    private List<WarehouseEntity> mAddressList;
    private List<WarehouseEntity> dbList;
    private boolean isExpressSave = true;//是否保存数据
    private boolean isRExpressSave = true;//重置时是否保存数据
    private int activityType;
    private WarehouseEntityDao warehouseEntityDao;


    private boolean isReSave = true;//是否缓存
    private boolean isSave = true;//是否清除数据


    //银行账号返回的总数


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    //设置银行账户列表
                    refreshCardUI();
                    break;
                case Constant.HANDLERTYPE_1:
                    //设置收货地址列表数据
                    refreshExpressUI();
                    break;
                case Constant.HANDLERTYPE_2:
                    //缓存数据
                    saveMmbWareHouse();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_bills);
        ButterKnife.bind(this);
        warehouseEntityDao = ((HejiaApp) getApplication()).getWarehouseEntityDao();
        initView();
        getBundle();
    }

    private void initView() {
        add_card_btn.setOnClickListener(this);
        mLoader = new NestRefreshLayout(bank_card_list);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        layout_head_left_btn.setVisibility(View.GONE);
        layout_head_right_tv.setOnClickListener(this);
    }


    /**
     * 判断模块功能
     */
    public void getBundle() {
        activityType = getIntent().getIntExtra(Constant.BUNDLE_TYPE, 0);
        switch (activityType) {
            case Constant.BANK_BILLS_ACTIVITY_TYPE:
                setBnakBills();
                break;
            case Constant.ECPRESS_ADDRESS_ACTIVITY_TYPE:
                setEcpressAddress();
                break;
        }
    }

    /**
     * 银行账户管理
     */
    private void setBnakBills() {
        setTitle(getString(R.string.activity_title_bank_bills_tv));
        if (hasInternetConnected()) {
            getQueryMmbBankAccountGainDatas();
        } else {
            getBankDataForLocal();
        }


    }

    //从数据库加载银行缓存数据
    private void getBankDataForLocal() {
        List<MmbBankAccountEntity> entities = WytUtils.getInstance(this).QuerytBankAccountInfo();
        if (entities != null && entities.size() > 0) {
            for (int i = 0; i < entities.size(); i++) {
                mBankList.add(entities.get(i));
            }
        }
        refreshCardUI();
    }

    /**
     * 获取银行账户列表数据
     * 同时可将数据进行缓存
     */
    private void refreshCardUI() {
        if (isSave) {
            pubList.clear();
        }
        for (int i = 0; i < mBankList.size(); i++) {
            dbBankList.add(mBankList.get(i));
            pubList.add(mBankList.get(i));

        }
        if (pageNo == 0) {
            addCardAdapte = new AddCardAdapte(mBankList, this);
            bank_card_list.setAdapter(addCardAdapte);
            addCardAdapte.notifyDataSetChanged();
        } else {
            if (pageNo <= (total - 1) / pageSize) {
                addCardAdapte.refreshList(mBankList);
            } else {
                mLoader.onLoadAll();
            }
        }
        addCardAdapte.setOnClickItem(this);
     /*   if(isBankSave){
            dbBankList.clear();
        }*/

        if (isReSave) {
            saveBankLocalData();
        }
    }

    //向数据库插入银行缓存信息
    private void saveBankLocalData() {
        WytUtils.getInstance(this).DeleteAllBankAccountInfo();
        if (pubList != null && pubList.size() > 0) {
            for (int i = 0; i < pubList.size(); i++) {
                WytUtils.getInstance(this).insertBankAccountInfo(pubList.get(i));
            }
        }
    }


    /**
     * 收发货地址
     */
    private void setEcpressAddress() {
        setTitle(getString(R.string.hint_tv_express_activity_title));
        add_card_btn.setText(getText(R.string.hint_ed_express_add_title));
        dbList = new ArrayList<>();
        if (hasInternetConnected()) {
            getQueryMmbWareHouseGainDatas(pageNo);
        } else {
            addLocality();
        }
    }

    /**
     * 加载本地数据
     */
    private void addLocality() {
        mAddressList = AppOrderGoodsUtils.getInstance(this).queryWarehouseEntityDaoInfo();
        if (dbList.size() > 0) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
        }
    }

    /**
     * 网数据库插入数据
     */
    private void saveMmbWareHouse() {
        warehouseEntityDao.deleteAll();
        if (dbList != null && dbList.size() > 0) {
            for (int i = 0; i < dbList.size(); i++) {
                warehouseEntityDao.insert(dbList.get(i));
            }
        }
    }


    /**
     * 获取收发货地址列表数据刷新界面
     * 同时可将数据进行缓存
     */
    private void refreshExpressUI() {
        if (isExpressSave) {
            dbList.clear();
        }

        for (int i = 0; i < mAddressList.size(); i++) {
            dbList.add(mAddressList.get(i));
        }

        if (pageNo == 0) {
            expressAdapter = new ExpressAdapte(dbList, this);
            expressAdapter.notifyDataSetChanged();
            bank_card_list.setAdapter(expressAdapter);
        } else {
            if (pageNo < (total / pageSize)) {
                expressAdapter.refreshList(mAddressList);
            } else {
                mLoader.onLoadAll();//加载全部
            }
        }

        ExpressAdapte.setOnClickItem(this);

        if (isRExpressSave)
            mHandler.sendEmptyMessage(Constant.STATUS_TYPE_2);
        waitDialogRectangle.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_card_btn:
                if (activityType == Constant.BANK_BILLS_ACTIVITY_TYPE) {
                    bundle.putInt(Constant.CARD_TYPE, Constant.CARD_ADD);
                } else {
                    bundle.putInt(Constant.CARD_TYPE, Constant.ADDRESS_ADD);
                }
                intentActivity(EditCardActivity.class, Constant.START_ACTIVITY_TYPE, bundle);
                break;
            case R.id.layout_head_right_tv:
                //操作说明
                if (activityType == Constant.BANK_BILLS_ACTIVITY_TYPE) {
                    //银行的
                    CreateDialog(Constant.DIALOG_CONTENT_35);
                } else {
                    //收货的
                    CreateDialog(Constant.DIALOG_CONTENT_2);
                }
                break;
        }
    }


    @Override
    public void onCilckItemEdit(int pos) {
        switch (activityType) {
            case Constant.BANK_BILLS_ACTIVITY_TYPE:
                MmbBankAccountEntity cardInfoEntity = dbBankList.get(pos);
                bundle.putSerializable(Constant.BUNDLE_TYPE, cardInfoEntity);
                bundle.putInt(Constant.CARD_TYPE, Constant.CARD_EDIT);
                break;
            case Constant.ECPRESS_ADDRESS_ACTIVITY_TYPE:
                //收发货地址里列表
                WarehouseEntity expressInfoEntity = mAddressList.get(pos);
                bundle.putString(Constant.BUNDLE_TYPE_0, expressInfoEntity.getId());
                bundle.putInt(Constant.CARD_TYPE, Constant.ADDRESS_EDIT);
                break;
        }
        intentActivity(EditCardActivity.class, Constant.START_ACTIVITY_TYPE, bundle);
    }

    /**
     * 搜索
     */
    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        if (activityType == Constant.BANK_BILLS_ACTIVITY_TYPE) {
            bundle.putInt(Constant.BUNDLE_TYPE, Constant.BANK_BILLS_ACTIVITY_TYPE);
        } else {
            bundle.putInt(Constant.BUNDLE_TYPE, Constant.MMBWAREHOUSE_BILLS_ACTIVITY_TYPE);
        }
        intentActivity(BillsSearchActivity.class, Constant.START_ACTIVITY_TYPE, bundle);
    }

    @Override
    public void onClickItemDelete(final int pos) {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_card_delete_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                if (activityType == Constant.BANK_BILLS_ACTIVITY_TYPE) {
                    deleteBankAccount(pos);

                } else {
                    deleteAddress(pos);
                }
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
        //银行列表的刷新
        if (activityType == Constant.BANK_BILLS_ACTIVITY_TYPE) {
            pageNo = 0;
            isEmptyParam();
            isSave = true;
            getQueryMmbBankAccountGainDatas();
            mLoader.onLoadFinished();
        }

        //收货列表的刷新
        else {
            pageNo = 0;
            setSave();
//            Constant.clearDatas();
            getQueryMmbWareHouseGainDatas(pageNo);
            mLoader.onLoadFinished();//加载结束
        }
        //地址的刷新
    }


    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        //银行列表的刷新
        if (activityType == Constant.BANK_BILLS_ACTIVITY_TYPE) {
            pageNo++;
            isEmptyParam();
            getQueryMmbBankAccountGainDatas();
            mLoader.onLoadFinished();
        } else {
            pageNo++;
//            Constant.clearDatas();
            setSave();
            getQueryMmbWareHouseGainDatas(pageNo);
            mLoader.onLoadFinished();//加载结束
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * @author haijian
         * 收到返回的值判断是否成功，如果同意就将数据移除刷新列表
         */
        if (requestCode == Constant.START_ACTIVITY_TYPE &&
                resultCode == Constant.CARD_EDIT) {
            pageNo = 0;
            dbBankList.clear();
            getQueryMmbBankAccountGainDatas();
        } else if (requestCode == Constant.START_ACTIVITY_TYPE &&
                resultCode == Constant.CARD_ADD) {
            pageNo = 0;
            dbBankList.clear();
            getQueryMmbBankAccountGainDatas();
        } else if (requestCode == Constant.START_ACTIVITY_TYPE &&
                resultCode == Constant.CARD_QUERY) {
            isEmptyParam();
            pageNo = 0;
            dbBankList.clear();
            getQueryMmbBankAccountGainDatas();
        } else if (requestCode == Constant.START_ACTIVITY_TYPE &&
                resultCode == Constant.ADDRESS_ADD
                || requestCode == Constant.START_ACTIVITY_TYPE &&
                resultCode == Constant.ADDRESS_EDIT) {
            pageNo = 0;
            setSave();
            //添加地址 请求网络数据
            getQueryMmbWareHouseGainDatas(pageNo);
        } else if (requestCode == Constant.START_ACTIVITY_TYPE &&
                resultCode == Constant.ADDRESS_SEARCH) {
            pageNo = 0;
            setSave();
            //添加地址 请求网络数据
            getQueryMmbWareHouseGainDatas(pageNo);
        }
    }


    private void deleteBankAccount(int index) {
        String timeUUID = Constant.getTimeUUID();
        if(timeUUID.equals("")){
            toastSHORT(getString(R.string.time_out));
            return;
        }

        final ToEditMmbWarehouseLoading tEdit = new ToEditMmbWarehouseLoading(
                Constant.publicArg.getSys_token(),
                timeUUID,
                Constant.SYS_FUNC101100510003,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                dbBankList.get(index).getId()
        );
        log(mGson.toJson(tEdit));
        Constant.JSONFATHERRESPON = "QueryMmbBankAccountRespon";
        OkGo.post(Constant.DeleteBankAccountUrl)
                .tag(this)
                .params("param", mGson.toJson(tEdit))
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<Void>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<Void> voidQueryMmbBankAccountGain, Call call, Response response) {
                        if (voidQueryMmbBankAccountGain != null) {
                            new MyToast(BankBillsActivity.this, "删除成功");
                            pageNo = 0;
                            dbBankList.clear();
                            getQueryMmbBankAccountGainDatas();
                        }
                    }
                });
    }

    /**
     * 获取后货地址
     */
    private void getQueryMmbWareHouseGainDatas(int pageNo) {
        QueryMmbWareHouseLoading queryMmbWareHouseLoading = new QueryMmbWareHouseLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100510001,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize),
                Constant.SEARCH_sendgoods_OrdertitleCode);
        LogShow(mGson.toJson(queryMmbWareHouseLoading));
        OkGo.post(Constant.QueryMmbWareHouseUrl)
                .tag(this)
                .params("param", mGson.toJson(queryMmbWareHouseLoading))
                .execute(new JsonCallBack<SimpleResponse<List<WarehouseEntity>>>() {
                    @Override
                    public void onSuccess(SimpleResponse<List<WarehouseEntity>> data, Call call, Response response) {
                        total = data.total;
                        //获取数据成功后更新UI 界面
                        if (data.return_code==0&&data.rows != null) {
                            mAddressList = data.rows;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                    }
                });
    }


    /**
     * 删除地址列表接口
     *
     * @param index
     */
    private void deleteAddress(int index) {
        Constant.JSONFATHERRESPON = "AddressEditRespon";
        ToEditMmbWarehouseLoading tEdit = new ToEditMmbWarehouseLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100510001,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                mAddressList.get(index).getId()
        );
        log(mGson.toJson(tEdit));
        OkGo.post(Constant.DeleteMmbWarehouseUrl)
                .tag(this)
                .params("param", mGson.toJson(tEdit))
                .execute(new JsonCallBack<AddressEditRespon<Void>>() {

                    @Override
                    public void onSuccess(AddressEditRespon<Void> voidAddressEditRespon, Call call, Response response) {
                        new MyToast(BankBillsActivity.this, "删除成功");
                        getQueryMmbWareHouseGainDatas(pageNo);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                    }
                });
    }

    /**
     * 获取银行账户列表
     */
    private void getQueryMmbBankAccountGainDatas() {
        PublicArg p = Constant.publicArg;
        RequestBackAccount r = new RequestBackAccount(
                p.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100610001,
                p.getSys_user(),
                p.getSys_member(),
                Constant.SEARCH_Account_Name,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        String param = mGson.toJson(r);
        Constant.JSONFATHERRESPON = "QueryMmbBankAccountRespon";
        System.out.println("r==QueryMmbBankAccountUrl" + r);
        OkGo.post(Constant.QueryMmbBankAccountUrl)
                .tag(this)
                .params("param", param)
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<List<MmbBankAccountEntity>>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<List<MmbBankAccountEntity>> listdata, Call call, Response response) {
                        //获取数据成功后更新UI 界面
                        if (listdata != null && listdata.return_code == 0 && listdata.list != null) {
                            total = listdata.pagecount;
                            mBankList = listdata.list;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        } else {
                            toastSHORT("数据为空");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                            toastSHORT(e.getMessage());
                       if(Constant.public_code){
                           LogoutUtils.exitUser(BankBillsActivity.this);
                       }
                    }
                });
    }

    /**
     * 判断加载数据是dblist是否清空
     * 判断是否缓存数据
     */
    private void setSave() {
        if (StringUtils.isStrTrue(Constant.SEARCH_sendgoods_orderId)) {
            isExpressSave = true;
            isRExpressSave = false;
        } else {
            isExpressSave = true;
            isRExpressSave = true;
        }
    }

    private void isEmptyParam() {
        if (StringUtils.isStrTrue(Constant.SEARCH_Account_Name)) {
            isSave = true;
            isReSave = false;
        } else {
            isSave = false;
            isReSave = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.SEARCH_Account_Name = "";
        Constant.clearDatas();
    }
}
