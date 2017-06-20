package com.hj.casps.bankmanage;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.PayMentAdapter;
import com.hj.casps.adapter.payadapter.PayMentCodeAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordermoney.QueryMmbBankAccountRespon;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.paymentmanager.ReqPayMoneyOffine;
import com.hj.casps.entity.paymentmanager.RequestQueryPayMoney;
import com.hj.casps.entity.paymentmanager.ResPayMoneyOffline;
import com.hj.casps.entity.paymentmanager.response.ResponseQueryPayBean;
import com.hj.casps.entity.paymentmanager.response.ResponseQueryPayEntity;
import com.hj.casps.entity.paymentmanager.response.WytUtils;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyListView;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

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
 * 付款
 */
public class PaymentActivity extends ActivityBaseHeader implements View.OnClickListener,
        OnPullListener, PayMentAdapter.onCheckedkType {
    @BindView(R.id.layout_bottom_check_layout1)
    LinearLayout layout_bottom_check_layout1;
    @BindView(R.id.layout_bottom_tv_2)
    TextView layout_bottom_tv_2;
    @BindView(R.id.layout_bottom_tv_3)
    TextView layout_bottom_tv_3;
    @BindView(R.id.layout_bottom_tv_4)
    TextView layout_bottom_tv_4;
    @BindView(R.id.layout_bottom_check_1)
    CheckBox layout_bottom_check_1;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    @BindView(R.id.payment_list)
    MyListView payment_list;
    @BindView(R.id.payment_scroll)
    ScrollView payment_scroll;
    @BindView(R.id.layout_head_left_btn)
    FancyButton fb;


    private MyDialog myDialog;
    private int goodsCount = 0;
    private double goodsMoeny;
    private PayMentAdapter payMentAdapter;
    private int pageno = 0;
    private int pagesize = 5;
    private boolean isReSave = true;//是否缓存
    private boolean isSave = true;//是否清除数据
    private List<ResponseQueryPayEntity> mList = new ArrayList<>();
    private int pageCount;

    private List<ResponseQueryPayEntity> dbList = new ArrayList();
    private List<ResponseQueryPayEntity> pubList = new ArrayList();
    private List<ReqPayMoneyOffine> orderList = new ArrayList();
    private List<ResponseQueryPayEntity.AccountlistBean> accountlist;
    private View contentView;


    //刷新数据
    private void refreshUI() {
        if (isSave) {
            pubList.clear();
        }
        for (int i = 0; i < mList.size(); i++) {
            dbList.add(mList.get(i));
            pubList.add(mList.get(i));
        }

        if (pageno == 0) {
            payMentAdapter = new PayMentAdapter(this, mList);
            payment_list.setAdapter(payMentAdapter);
            payMentAdapter.notifyDataSetChanged();
        } else {
            if (pageno <= ((pageCount - 1) / pagesize)) {
                payMentAdapter.addRes(mList);
            } else {
                mLoader.onLoadAll();
            }
        }
        payMentAdapter.setOnCheckedkType(this);

        if (mList != null)
            for (int i = 0; i < mList.size(); i++) {
                if (!mList.get(i).isChecked()) {
                    layout_bottom_check_1.setChecked(false);
                    return;
                }
            }
        if (isReSave) {
            saveLocalData();
        }
    }

    //保存数据到数据库
    private void saveLocalData() {
        WytUtils.getInstance(this).DeleteAllQueryPayEntityInfo();
        for (int i = 0; i < pubList.size(); i++) {
            ResponseQueryPayBean bean = new ResponseQueryPayBean();
            ResponseQueryPayEntity entity = pubList.get(i);
            bean.setChecked(entity.isChecked());
            bean.setExePaymoneyNum(entity.getExePaymoneyNum());
            bean.setGoodsName(entity.getGoodsName());
            bean.setId(entity.getId());
            bean.setMoney(entity.getMoney());
            bean.setOrdertitleId(entity.getOrdertitleId());
            bean.setOrdertitleNumber(entity.getOrdertitleNumber());
            bean.setPaymoneyNum(entity.getPaymoneyNum());
            bean.setSellersName(entity.getSellersName());
            String gsonString = GsonTools.createGsonString(entity.getAccountlist());
            bean.setAccountlist(gsonString);
            System.out.println("--" + gsonString);
            WytUtils.getInstance(this).insertQueryPayEntityInfo(bean);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        if (hasInternetConnected()) {
            initData(pageno);
        } else {
            getDataForLocal();
        }

        initView();
    }

    /**
     * 从本地数据库加载数据
     */
    private void getDataForLocal() {
        List<ResponseQueryPayBean> payBeen = WytUtils.getInstance(this).QuerytQueryPayEntityInfo();
        for (int i = 0; i < payBeen.size(); i++) {
            ResponseQueryPayEntity entity = new ResponseQueryPayEntity();
            ResponseQueryPayBean bean = payBeen.get(i);
            entity.setChecked(bean.isChecked());
            entity.setExePaymoneyNum(bean.getExePaymoneyNum());
            entity.setGoodsName(bean.getGoodsName());
            entity.setId(bean.getId());
            entity.setMoney(bean.getMoney());
            entity.setOrdertitleId(bean.getOrdertitleId());
            entity.setOrdertitleNumber(bean.getOrdertitleNumber());
            entity.setPaymoneyNum(bean.getPaymoneyNum());
            entity.setSellersName(bean.getSellersName());
            List<ResponseQueryPayEntity.AccountlistBean> been = GsonTools.changeGsonToList(bean.getAccountlist(), ResponseQueryPayEntity.AccountlistBean.class);

            entity.setAccountlist(been);
            mList.add(entity);
        }

        if (mList != null && mList.size() > 0) {
            refreshUI();
        }
    }

    private void initData(final int pageno) {
        Constant.JSONFATHERRESPON = "QueryMmbBankAccountRespon";
        PublicArg p = Constant.publicArg;
        RequestQueryPayMoney r = new RequestQueryPayMoney(p.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100610002,
                p.getSys_user(),
                p.getSys_member(),
                Constant.appOrderMoney_orderId,
                Constant.appOrderMoney_goodsName,
                Constant.appOrderMoney_buyersName,
                String.valueOf(pageno + 1),
                String.valueOf(pagesize));
        String param = mGson.toJson(r);
        System.out.println("r==QueryPayMoneyUrl" + r);
        waitDialogRectangle.show();
        OkGo.post(Constant.QueryPayMoneyUrl).params("param", param)
                .execute(new JsonCallBack<QueryMmbBankAccountRespon<List<ResponseQueryPayEntity>>>() {
                    @Override
                    public void onSuccess(QueryMmbBankAccountRespon<List<ResponseQueryPayEntity>> listData, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        if (listData.return_code == 0 && listData != null && listData.list != null) {
                            PaymentActivity.this.mList = listData.list;
                            PaymentActivity.this.pageCount = listData.pagecount;
                            refreshUI();
                        } else {
                            toastSHORT("查询数据为空");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    private void initView() {
        setTitle(getString(R.string.activity_panment_title));
//        getDatas();
        fb.setVisibility(View.GONE);
        layout_bottom_tv_2.setText(getString(R.string.hint_reset_title));
        layout_bottom_tv_2.setOnClickListener(this);
        layout_bottom_tv_2.setBackgroundResource(R.color.light_orange);
        layout_bottom_tv_3.setVisibility(View.GONE);
        layout_bottom_tv_4.setText(getString(R.string.bt_offline_payment_title));
        layout_bottom_tv_4.setOnClickListener(this);
        layout_bottom_tv_4.setBackgroundResource(R.color.title_bg);
        layout_head_right_tv.setOnClickListener(this);
        layout_bottom_check_1.setOnClickListener(this);
        layout_bottom_check_layout1.setOnClickListener(this);
        mLoader = new NestRefreshLayout(payment_scroll);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
//        refreshUI();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PAYMENT_REQUEST_CODE && resultCode == Constant.PAYMENTRESULTOK) {
            //如果是带参数的 就不缓存
            isEmptyParam();
            pageno = 0;
            dbList.clear();
            initData(pageno);
        }
    }


    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.PAYMENT_SEARCH_TYPE);
        intentActivity(BillsSearchActivity.class, Constant.PAYMENT_REQUEST_CODE, bundle);
    }

    @Override
    public void onBillsIDItemCilckListener(int pos) {
//        bundle.putSerializable(Constant.BUNDLE_TYPE, mList.get(pos));
//       Constant.SEARCH_sendgoods_orderId= String.valueOf(dbList.get(pos).getOrdertitleNumber());
        Constant.SEARCH_sendgoods_orderId = dbList.get(pos).getOrdertitleId();
        intentActivity(BillsDetailsActivity.class);

    }

    @Override
    public void onPayMentCodeItemClickListener(TextView view, int pos) {
        accountlist = dbList.get(pos).getAccountlist();
        if (accountlist != null && dbList.size() > 0) {
            CreateDialog(view, accountlist, pos);
        }
    }

    /**
     * 弹框
     */
    public void CreateDialog(final TextView textView,
                             final List<ResponseQueryPayEntity.AccountlistBean> accountlist,
                             final int listpos) {
        // 动态加载一个listview的布局文件进来
        LayoutInflater inflater = LayoutInflater.from(this);
        contentView = inflater.inflate(R.layout.layout_list_popup, null);
        // 给ListView绑定内容
        ListView listView = (ListView) contentView.findViewById(R.id.layout_list_popup);
        PayMentCodeAdapter qadapter = new PayMentCodeAdapter(context, accountlist);
        listView.setAdapter(qadapter);
        qadapter.notifyDataSetChanged();
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(contentView)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(accountlist.get(position).getBankname() + accountlist.get(position).getAccountno());
//                mList.get(listpos).setAddressId(addressList.get(position).getId());
                dbList.get(listpos).setPayMentCode(accountlist.get(position).getAccountno());
//                mList.get(listpos).setAddressName(addressList.get(position).getAddress());
//                LogShow(mList.get(listpos).getAddressId() + "......" + listpos);
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            //全选按钮
            case R.id.layout_bottom_check_layout1:
                selectLaytou();
                break;
//            //全选按钮
            case R.id.layout_bottom_check_1:
                selectCheck();
                break;
            case R.id.layout_bottom_tv_2:
                //重置
                isReSave = false;
                isSave = false;
                clearDatas(false);
                break;
            case R.id.layout_bottom_tv_4:
                buyAll();
                break;
            case R.id.layout_head_right_tv:
                CreateDialog(Constant.DIALOG_CONTENT_31);
                break;
        }
    }

    public void reSetBillsDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_reset_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {

//                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
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
     * @param isInitData true 代表请求网络    false    刷新数据
     */
    private void clearDatas(boolean isInitData) {
        selectAll(false);
        for (int i = 0; i < dbList.size(); i++) {
            dbList.get(i).clearData();
        }
        goodsCount = 0;
        pageno = 0;
        orderList.clear();
        layout_bottom_check_1.setChecked(false);
        if (isInitData) {
            dbList.clear();
            initData(pageno);
        } else {
            refreshUI();
        }
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageno = 0;
        isEmptyParam();
        isSave = true;
        initData(pageno);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageno++;
        isEmptyParam();
        initData(pageno);
        mLoader.onLoadFinished();//加载结束
    }

    /**
     * 收集提交所用的参数
     */
    private void buyAll() {
        if (goodsCount <= 0) {
            toastSHORT("请勾选一条订单");
            return;
        }
        for (int i = 0; i < dbList.size(); i++) {
            ResponseQueryPayEntity entity = dbList.get(i);
            if (entity.isChecked()) {
                if (entity.getPayNum() == null || !StringUtils.isStrTrue(entity.getPayNum())) {
                    toastSHORT("请输入付款金额");
                    return;
                }
                if (entity.getExePaymoneyNum() < Double.valueOf(entity.getPayNum())) {
                    toast("超出待付款金额");
                    return;
                }
                ReqPayMoneyOffine r = new ReqPayMoneyOffine();
                //订单id
                r.setId(entity.getId());
                //备注
                r.setPayMoneyCode(entity.getPayMentCode());
                //付款金额
                r.setNum(entity.getPayNum());
                //商品名
                r.setGoodName(entity.getGoodsName());
                //备注
                r.setGetPayRemark(entity.getReMark());
                orderList.add(r);
            }
        }
        showBillsDialog();


    }

    //请求网络接口
    private void executePayMoneyForNet() {
        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ResPayMoneyOffline r = new ResPayMoneyOffline(p.getSys_token(), timeUUID, Constant.SYS_FUNC101100610002, p.getSys_user(), p.getSys_member(), orderList);
        String param = mGson.toJson(r);
        log(param);
        waitDialogRectangle.show();
        OkGo.post(Constant.PayMoneyOfflineUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                waitDialogRectangle.dismiss();
                Pub pub = GsonTools.changeGsonToBean(s, Pub.class);
                if (pub.getReturn_code() == 0) {
                    new MyToast(PaymentActivity.this, pub.getReturn_message());
                    //传true是需要请求网络
                    clearDatas(true);

                } else {
                    toastSHORT(pub.getReturn_message());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                waitDialogRectangle.dismiss();
            }
        });
    }

    private void selectAll(boolean isck) {
        for (int i = 0; i < dbList.size(); i++) {
            // 改变boolean
            dbList.get(i).setChecked(isck);
            // 如果为选中
            if (dbList.get(i).isChecked()) {
                goodsCount++;
                goodsMoeny += Double.valueOf(dbList.get(i).getPaymoneyNum());
            } else {
                goodsCount = 0;
                goodsMoeny = 0;
            }
        }
        payMentAdapter.notifyDataSetChanged();
    }


    public void showBillsDialog() {
        myDialog = new MyDialog(this);
        myDialog.setMessage(getString(R.string.dialog_pay_msg));
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                executePayMoneyForNet();
//                new MyToast(context, "付款成功" + bills + "条");
            }
        });
        myDialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                orderList.clear();
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    /**
     * 全局选择
     */
    private void selectLaytou() {
        if (dbList == null || dbList.size() == 0) {
            toastSHORT("数据为空");
            return;
        }
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
        if (dbList == null || dbList.size() == 0) {
            toastSHORT("数据为空");
            layout_bottom_check_1.setChecked(false);
            return;
        }
        if (layout_bottom_check_1.isChecked()) {
            selectAll(true);
        } else {
            selectAll(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.clearDatas();
    }

    public void isEmptyParam() {
        if (StringUtils.isStrTrue(Constant.appOrderMoney_orderId) || StringUtils.isStrTrue(Constant.appOrderMoney_goodsName) || StringUtils.isStrTrue(Constant.appOrderMoney_buyersName)) {
            isSave = true;
            isReSave = false;
        } else {
            isSave = false;
            isReSave = true;
        }
    }
}
