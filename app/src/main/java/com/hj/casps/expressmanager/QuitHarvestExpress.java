package com.hj.casps.expressmanager;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hj.casps.R;
import com.hj.casps.adapter.expressadapter.QuitHarvestAdapter;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.bankmanage.BillsDetailsActivity;
import com.hj.casps.bankmanage.BillsSearchActivity;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoods.GetReturnGoodsOperation;
import com.hj.casps.entity.appordergoods.QueryGetReturnGoodsEntity;
import com.hj.casps.entity.appordergoods.QueryGetReturnGoodsGain;
import com.hj.casps.entity.appordergoods.QueryGoodsListLoading;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.HarvestExpressRespon;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyListView;
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
 * 退货签收
 */
public class QuitHarvestExpress extends ActivityBaseHeader implements OnPullListener,
        View.OnClickListener,
        QuitHarvestAdapter.onCheckedkType {

    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    @BindView(R.id.payment_list)
    MyListView payment_list;
    @BindView(R.id.payment_scroll)
    ScrollView payment_scroll;
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
    private QuitHarvestAdapter quitAdapter;
    private List<QueryGetReturnGoodsGain> mList;
    private List<QueryGetReturnGoodsGain> dbList;
    private View contentView;
    private ListView listView;
    private boolean isSave = true;//是否保存数据
    private boolean isRSave = true;//重置时是否保存数据
    private boolean isSetAdapter = false;//重置时是否保存数据
    private List<QueryGetReturnGoodsGain.AddressListBean> addressList;
    private List<GetReturnGoodsOperation.GetReturnGoodsList> orderList = new ArrayList<>();

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
                    getNetWorkDatas(pageNo);
                    break;
                case Constant.HANDLERTYPE_3:
                    waitDialogRectangle.show();
                    //重置
                    selectAll(false);
                    deleteBills(false);
                    isSave = false;
                    isSetAdapter = false;
                    mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                    break;
                case Constant.HANDLERTYPE_4:
                    pageNo = 0;
//                    Constant.clearDatas();
                    isSave = true;
                    if (StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrdertitleCode) ||
                            StringUtils.isStrTrue(Constant.SEARCH_sendgoods_OrderGoodName)) {
                        isRSave = false;
                    } else {
                        isRSave = true;
                    }
                    getNetWorkDatas(pageNo);
                    mLoader.onLoadFinished();//加载结束
                    break;
                case Constant.HANDLERTYPE_5:
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
                        getNetWorkDatas(pageNo);
                    mLoader.onLoadFinished();//加载结束7
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

    /**
     * 加载View
     */
    private void initView() {
        setTitle(getString(R.string.activity_quit_harvest_express_title));
        layout_bottom_tv_2.setVisibility(View.GONE);
        layout_bottom_tv_3.setBackgroundResource(R.color.reset_bg);
        layout_bottom_tv_3.setText(getText(R.string.hint_reset_title));
        layout_bottom_tv_3.setOnClickListener(this);
        layout_bottom_tv_4.setBackgroundResource(R.color.text_color_blue);
        layout_bottom_tv_4.setText(getText(R.string.hint_tv_quit_harvest_express_title));
        layout_bottom_tv_4.setOnClickListener(this);
        layout_bottom_check_1.setOnClickListener(this);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);
        layout_head_left_btn.setVisibility(View.GONE);
        dbList = new ArrayList<>();
        mLoader = new NestRefreshLayout(payment_scroll);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

        if (hasInternetConnected()) {
            getNetWorkDatas(pageNo);
        } else {
            addLocality();
        }
    }

    /**
     * 设置界面
     */
    private void setInitView() {
        if (isSave) {
            dbList.clear();
        }

        dbList.addAll(mList);

        if (isSetAdapter) {
            if (pageNo == 0) {
                quitAdapter = new QuitHarvestAdapter(this, mList);
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
            quitAdapter = new QuitHarvestAdapter(this, dbList);
            quitAdapter.notifyDataSetChanged();
            payment_list.setAdapter(quitAdapter);
        }

        QuitHarvestAdapter.setOnCheckedkType(this);
        waitDialogRectangle.dismiss();
        if (isRSave)
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);

        for (int i = 0; i < dbList.size(); i++) {
            if (!dbList.get(i).isCheck()) {
                layout_bottom_check_1.setChecked(false);
                return;
            }
        }
    }

    /**
     * 插入数据库
     */
    private void saveHarvesData() {
        AppOrderGoodsUtils.getInstance(this).deleteHarvestGoodsAll();
        if (dbList.size() > 0 && dbList != null) {
            for (int i = 0; i < dbList.size(); i++) {
                QueryGetReturnGoodsEntity qBean = new QueryGetReturnGoodsEntity();
                qBean.setBuyers_name(dbList.get(i).getBuyers_name());
                qBean.setExeGetreturngoodsNum(dbList.get(i).getExeGetreturngoodsNum());
                qBean.setGetreturngoodsNum(dbList.get(i).getGetreturngoodsNum());
                qBean.setGoodsName(dbList.get(i).getGoodsName());
                qBean.setGoods_num(dbList.get(i).getGoods_num());
                qBean.setOrderId(dbList.get(i).getOrderId());
                qBean.setOrdertitleCode(dbList.get(i).getOrdertitleCode());
                qBean.setOrdertitleNumber(dbList.get(i).getOrdertitleNumber());
                qBean.setAddress_list(GsonTools.createGsonString(dbList.get(i).getAddress_list()));
                AppOrderGoodsUtils.getInstance(this).insertHarvestGoodsInfo(qBean);
            }
        }
    }

    /**
     * 加载本地数据
     */
    private void addLocality() {
        List<QueryGetReturnGoodsEntity> beanList = AppOrderGoodsUtils.getInstance(this).queryHarvestGoodsInfo();
        mList = new ArrayList<>();
        if (beanList.size() > 0 && beanList != null) {
            for (int i = 0; i < beanList.size(); i++) {
                QueryGetReturnGoodsGain qBean = new QueryGetReturnGoodsGain();
                qBean.setBuyers_name(beanList.get(i).getBuyers_name());
                qBean.setExeGetreturngoodsNum(beanList.get(i).getExeGetreturngoodsNum());
                qBean.setGetreturngoodsNum(beanList.get(i).getGetreturngoodsNum());
                qBean.setGoodsName(beanList.get(i).getGoodsName());
                qBean.setGoods_num(beanList.get(i).getGoods_num());
                qBean.setOrderId(beanList.get(i).getOrderId());
                qBean.setOrdertitleCode(beanList.get(i).getOrdertitleCode());
                qBean.setOrdertitleNumber(beanList.get(i).getOrdertitleNumber());
                Type listType = new TypeToken<List<QueryGetReturnGoodsGain.AddressListBean>>() {
                }.getType();
                List<QueryGetReturnGoodsGain.AddressListBean> been = mGson.fromJson(
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
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_3);
                break;
            case R.id.layout_bottom_tv_4:
                buyAll();
                break;
            case R.id.layout_head_right_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_5);
                break;
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

    /**
     * 退货操作判断数量
     */
    private void buyAll() {
        if (goodsCount > 0) {
            setLisData();
        } else {
            toastSHORT("至少选择一条数据");
        }
    }

    /**
     * 退货操作弹框
     *
     * @param orderList
     */
    public void showBillsDialog(final List<GetReturnGoodsOperation.GetReturnGoodsList> orderList) {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_harvest_express_msg));
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


    /**
     * 获取网络数据
     */
    private void getNetWorkDatas(int pageNo) {
        waitDialogRectangle.show();
        QueryGoodsListLoading getGoods = new QueryGoodsListLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                Constant.SEARCH_sendgoods_OrdertitleCode,
                Constant.SEARCH_sendgoods_OrderGoodName,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        Constant.JSONFATHERRESPON = "HarvestExpressRespon";
        LogShow(mGson.toJson(getGoods));
        OkGo.post(Constant.QueryGetReturnGoodsUrl)
                .tag(this)
                .params("param", mGson.toJson(getGoods))
                .execute(new JsonCallBack<HarvestExpressRespon<List<QueryGetReturnGoodsGain>>>() {

                    @Override
                    public void onSuccess(HarvestExpressRespon<List<QueryGetReturnGoodsGain>> listHarvestExpressRespon, Call call, Response response) {
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
                        if (Constant.public_code) {
                            //退出操作
                            LogoutUtils.exitUser(QuitHarvestExpress.this);
                        }
                    }
                });
    }

    /**
     * 退货签收操作
     */
    private void SubmitReturnExpress(List<GetReturnGoodsOperation.GetReturnGoodsList> orderList) {
        waitDialogRectangle.show();
        GetReturnGoodsOperation returnGoods = new GetReturnGoodsOperation(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                orderList);
        LogShow(mGson.toJson(returnGoods));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.GetReturnGoodsUrl)
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
                        if (Constant.public_code) {
                            //退出操作
                            LogoutUtils.exitUser(QuitHarvestExpress.this);
                        }
                    }
                });
    }


    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_4);
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_5);
    }

    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.ECPRESS_QUIT_HARVEST_ACTIVITY_TYPE);
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
     * 地址列表选择器
     */
    public void CreateDialog(final TextView textView,
                             final List<QueryGetReturnGoodsGain.AddressListBean> addressList,
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
     * 地址列表选择器适配器
     */
    public class AddressAdapter extends CommonAdapter<QueryGetReturnGoodsGain.AddressListBean> {
        public AddressAdapter(Context context, List<QueryGetReturnGoodsGain.AddressListBean> datas) {
            super(context, datas, R.layout.layout_item_popup);
            this.mContext = context;
        }

        @Override
        public void concert(ViewHolder hooder, QueryGetReturnGoodsGain.AddressListBean addressListBean, int indexPos) {
            TextView textView = hooder.getView(R.id.layout_item_tv);
            textView.setText(addressListBean.getAddress());
        }
    }

    /**
     * 获取保存的数据
     */
    private void setLisData() {
        if (dbList.size() > 0) {
            for (int i = 0; i < dbList.size(); i++) {
                if (dbList.get(i).isCheck()) {
                    if (!StringUtils.isStrTrue(dbList.get(i).getNum())
                            || Integer.valueOf(dbList.get(i).getNum()) == 0) {
                        toastSHORT("请填写数量");
                        return;
                    }

                    if (dbList.get(i).getExeGetreturngoodsNum() < Integer.valueOf(dbList.get(i).getNum())) {
                        toastSHORT("超出待收数量");
                        return;
                    }

                    if (!StringUtils.isStrTrue(dbList.get(i).getAddressName())) {
                        toastSHORT("尚未创建地址，收退货失败");
                        return;
                    }

                    orderList.add(new GetReturnGoodsOperation.GetReturnGoodsList(
                            dbList.get(i).getOrderId(),
                            String.valueOf(dbList.get(i).getNum()),
                            dbList.get(i).getAddressId()
                    ));
                }
            }
        }
        showBillsDialog(orderList);
    }

    /**
     * 全选以及取消全选的操作
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
     * 提交成功或者失败后清空数据
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
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == Constant.QUIT_RETURN_SEARCH) {
            pageNo = 0;
            isSave = true;
            isRSave = false;
            getNetWorkDatas(pageNo);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }
}