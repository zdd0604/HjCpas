package com.hj.casps.expressmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hj.casps.R;
import com.hj.casps.adapter.expressadapter.AddressAdapter;
import com.hj.casps.adapter.expressadapter.HarvestAdapter;
import com.hj.casps.bankmanage.BillsDetailsActivity;
import com.hj.casps.bankmanage.BillsSearchActivity;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoods.GetGoodsLoading;
import com.hj.casps.entity.appordergoods.QueryGetGoodsBean;
import com.hj.casps.entity.appordergoods.QueryGetGoodsEntity;
import com.hj.casps.entity.appordergoods.QueryGoodsListLoading;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.HarvestExpressRespon;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.operatormanager.OperatorAdd;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;

import java.lang.reflect.Type;
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
 * 收货
 */
public class HarvestExpress extends ActivityBaseHeader implements OnPullListener,
        View.OnClickListener,
        HarvestAdapter.onCheckedkType {

    private HarvestAdapter harvestAdapter;

    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    @BindView(R.id.payment_list)
    ListView payment_list;
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.layout_head_right_btn)
    FancyButton layout_head_right_btn;
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
    private View contentView;
    private ListView listView;
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据
    private boolean isSetAdapter = false;//重置时是否保存数据
    private List<QueryGetGoodsEntity> mList;//每次请求服务器的数据
    private List<QueryGetGoodsEntity> dbList;//上啦加载的数据缓存
    private List<GetGoodsLoading.NumListBean> goodLsit = new ArrayList<>();
    private List<QueryGetGoodsEntity.AddressListBean> addressList;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    setInitView();
                    break;
                case Constant.HANDLERTYPE_1:
                    saveHarvesData();
                    break;
                case Constant.HANDLERTYPE_2:
                    getNetWotk(pageNo);
                    break;
            }
        }
    };


    /**
     * 插入数据库
     */
    private void saveHarvesData() {
        AppOrderGoodsUtils.getInstance(this).deleteHarvestExpressAll();
        if (dbList.size() > 0 && dbList != null) {
            for (int i = 0; i < dbList.size(); i++) {
                QueryGetGoodsBean qBean = new QueryGetGoodsBean();
                qBean.setExe_getgoods_num(dbList.get(i).getExe_getgoods_num());
                qBean.setGetgoods_num(dbList.get(i).getGetgoods_num());
                qBean.setGood_sname(dbList.get(i).getGood_sname());
                qBean.setGoods_num(dbList.get(i).getGoods_num());
                qBean.setOrderId(dbList.get(i).getOrderId());
                qBean.setOrdertitleCode(dbList.get(i).getOrdertitleCode());
                qBean.setOrdertitleNumber(dbList.get(i).getOrdertitleNumber());
                qBean.setSellers_name(dbList.get(i).getSellers_name());
                qBean.setAddress_list(GsonTools.createGsonString(dbList.get(i).getAddress_list()));
                AppOrderGoodsUtils.getInstance(this).insertHarvestExpressInfo(qBean);
                LogShow("---" + GsonTools.createGsonString(dbList.get(i).getAddress_list()));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.activity_express_harvest_title));
        layout_head_left_btn.setVisibility(View.GONE);
        layout_bottom_tv_2.setVisibility(View.GONE);
        layout_bottom_tv_3.setBackgroundResource(R.color.reset_bg);
        layout_bottom_tv_3.setText(getText(R.string.hint_reset_title));
        layout_bottom_tv_3.setOnClickListener(this);
        layout_bottom_tv_4.setBackgroundResource(R.color.text_color_blue);
        layout_bottom_tv_4.setText(getText(R.string.activity_express_harvest_title));
        layout_bottom_tv_4.setOnClickListener(this);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_layout_1.setOnClickListener(this);
        layout_bottom_check_1.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);
        layout_head_right_btn.setVisibility(View.GONE);
        mLoader = new NestRefreshLayout(payment_list);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

        dbList = new ArrayList<>();
        if (hasInternetConnected()) {
            getNetWotk(pageNo);
        } else {
            addLocality();
        }
    }


    /**
     * 刷新界面加载数据
     */
    private void setInitView() {
        if (isSave) {
            dbList.clear();
        }

        dbList.addAll(mList);

        if (isSetAdapter) {
            if (pageNo == 0) {
                harvestAdapter = new HarvestAdapter(this, mList);
                payment_list.setAdapter(harvestAdapter);
                harvestAdapter.notifyDataSetChanged();
            } else {
                if (pageNo <= ((total - 1) / pageSize)) {
                    harvestAdapter.refreshList(mList);
                } else {
                    mLoader.onLoadAll();//加载全部
                }
            }
        } else {
            for (int i = 0; i < dbList.size(); i++) {
                dbList.get(i).clearData();
            }
            harvestAdapter = new HarvestAdapter(this, dbList);
            payment_list.setAdapter(harvestAdapter);
            harvestAdapter.notifyDataSetChanged();
        }


        harvestAdapter.setOnCheckedkType(this);

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
     * 加载本地数据
     */
    private void addLocality() {
        AppOrderGoodsUtils.getInstance(this).deleteHarvestExpressAll();
        List<QueryGetGoodsBean> beanList = AppOrderGoodsUtils.getInstance(this).queryHarvestExpressInfo();
        if (beanList.size() > 0 && beanList != null) {
            for (int i = 0; i < beanList.size(); i++) {
                QueryGetGoodsEntity qBean = new QueryGetGoodsEntity();
                qBean.setExe_getgoods_num(beanList.get(i).getExe_getgoods_num());
                qBean.setGetgoods_num(beanList.get(i).getGetgoods_num());
                qBean.setGood_sname(beanList.get(i).getGood_sname());
                qBean.setGoods_num(beanList.get(i).getGoods_num());
                qBean.setOrderId(beanList.get(i).getOrderId());
                qBean.setOrdertitleCode(beanList.get(i).getOrdertitleCode());
                qBean.setOrdertitleNumber(beanList.get(i).getOrdertitleNumber());
                qBean.setSellers_name(beanList.get(i).getSellers_name());
                Type listType = new TypeToken<List<QueryGetGoodsEntity.AddressListBean>>() {
                }.getType();
                List<QueryGetGoodsEntity.AddressListBean> been = mGson.fromJson(
                        beanList.get(i).getAddress_list(),
                        listType);
                qBean.setAddress_list(been);
                mList.add(qBean);
            }
        }
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
                buyAll();
                break;
            case R.id.layout_head_right_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_3);
                break;
        }
    }


    /**
     * 确认收货
     */
    private void buyAll() {
        if (goodsCount > 0) {
            setDatas();
        } else {
            toastSHORT("至少选择一条数据");
        }
    }


    private void setDatas() {
        for (int i = 0; i < dbList.size(); i++) {
            QueryGetGoodsEntity queryGetGoodsEntity = dbList.get(i);
            if (queryGetGoodsEntity.isCheck()) {
                if (!StringUtils.isStrTrue(queryGetGoodsEntity.getNum())
                        || Integer.valueOf(queryGetGoodsEntity.getNum()) == 0) {
                    toastSHORT("请填写数量");
                    return;
                }
                if (queryGetGoodsEntity.getExe_getgoods_num() < Integer.valueOf(queryGetGoodsEntity.getNum())) {
                    toastSHORT("超出代收数量");
                    return;
                }
                goodLsit.add(new GetGoodsLoading.NumListBean(
                        queryGetGoodsEntity.getOrderId(),
                        queryGetGoodsEntity.getNum(),
                        queryGetGoodsEntity.getAddressId()));
            }
        }
        showBillsDialog();
    }

    public void showBillsDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_express_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                SubmitReturnExpress(goodLsit);
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
        getNetWotk(pageNo);
        mLoader.onLoadFinished();//加载全部
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageNo++;
//        Constant.clearDatas();
        isSave = false;
        if (StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrdertitleCode) ||
                StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrderGoodName)) {
            isRSave = false;
        } else {
            isRSave = true;
        }
        if (dbList.size() < total)
            getNetWotk(pageNo);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.ECPRESS_ADDRESS_ACTIVITY_TYPE);
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

    @Override
    public void onAddressOnClickListener(TextView view, int pos) {
        addressList = dbList.get(pos).getAddress_list();
        if (addressList != null && dbList.size() > 0) {
            CreateDialog(view, addressList, pos);
        }
    }

    /**
     * 弹框
     */
    public void CreateDialog(final TextView textView,
                             final List<QueryGetGoodsEntity.AddressListBean> addressList,
                             final int listpos) {
        // 动态加载一个listview的布局文件进来
        LayoutInflater inflater = LayoutInflater.from(this);
        contentView = inflater.inflate(R.layout.layout_list_popup, null);
        // 给ListView绑定内容
        listView = (ListView) contentView.findViewById(R.id.layout_list_popup);
        AddressAdapter qadapter = new AddressAdapter(context, addressList);
        listView.setAdapter(qadapter);
        qadapter.notifyDataSetChanged();
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(contentView)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(addressList.get(position).getAddress());
                dbList.get(listpos).setAddressId(addressList.get(position).getId());
                dbList.get(listpos).setAddressName(addressList.get(position).getAddress());
                dialog.dismiss();
            }
        });
    }


    /**
     * 请求网络数据
     */
    public void getNetWotk(int pageNo) {
        waitDialogRectangle.show();
        QueryGoodsListLoading getGoods = new QueryGoodsListLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100510002,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                Constant.SEARCH_sendgoods_OrdertitleCode,
                Constant.SEARCH_sendgoods_OrderGoodName,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        LogShow(mGson.toJson(getGoods));
        Constant.JSONFATHERRESPON = "HarvestExpressRespon";
        OkGo.post(Constant.QueryGetGoodsUrl)
                .tag(this)
                .params("param", mGson.toJson(getGoods))
                .execute(new JsonCallBack<HarvestExpressRespon<List<QueryGetGoodsEntity>>>() {

                    @Override
                    public void onSuccess(HarvestExpressRespon<List<QueryGetGoodsEntity>> listHarvestExpressRespon, Call call, Response response) {
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
                        if (Constant.public_code){
                            //退出操作
                            LogoutUtils.exitUser(HarvestExpress.this);
                        }
                    }
                });
    }

    /**
     * 收货货操作
     */
    private void SubmitReturnExpress(List<GetGoodsLoading.NumListBean> param) {
        waitDialogRectangle.show();
        GetGoodsLoading returnGoods = new GetGoodsLoading(
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                Constant.SYS_FUNC10110051,
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.publicArg.getSys_name(),
                param);
        LogShow(mGson.toJson(returnGoods));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.GetGoodsUrl)
                .tag(this)
                .params("param", mGson.toJson(returnGoods))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidSendGoodsRespon, Call call, Response response) {
                        if (voidSendGoodsRespon.return_code == 0) {
                            new MyToast(context, "收货成功" + voidSendGoodsRespon.success_num + "条");
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
                        } else {
                            new MyToast(context, "收货成功" + voidSendGoodsRespon.fail_num + "条");
                            toastSHORT(voidSendGoodsRespon.return_message);
                        }
                        deleteBills(true);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                        if (Constant.public_code){
                            //退出操作
                            LogoutUtils.exitUser(HarvestExpress.this);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == Constant.QUIT_GET_SEARCH) {
            pageNo = 0;
            isSave = true;
            isRSave = false;
            getNetWotk(pageNo);
        }
    }

    /**
     * 全选以及取消
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
        harvestAdapter.notifyDataSetChanged();
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
        goodLsit.clear();
        waitDialogRectangle.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }
}