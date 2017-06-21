package com.hj.casps.overdealmanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hj.casps.R;
import com.hj.casps.adapter.overdealadapter.ExexuteAdapter;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appsettle.QuerySttleManageGain;
import com.hj.casps.entity.appsettle.QuerySttleManageLoading;
import com.hj.casps.entity.appsettle.QuerySttleManageRespon;
import com.hj.casps.entity.appsettle.StopSettleLoading;
import com.hj.casps.protocolmanager.ViewPagerFragment;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.widget.WaitDialogRectangle;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Admin on 2017/5/2.
 * 执行中的结款单列表
 */

public class ExecuteFragment extends ViewPagerFragment implements
        ExexuteAdapter.onClickBillsListener,
        OnPullListener {
    private ListView listview;
    private boolean isSave = true;//是否保存数据
    private List<QuerySttleManageGain> mList;
    private List<QuerySttleManageGain> dbList = new ArrayList<>();
    private ExexuteAdapter mExexuteAdapter;
    private int fragment_type = 0;

    public ExecuteFragment(int fragment_type) {
        this.fragment_type = fragment_type;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    setInitView();
                    break;
                case Constant.HANDLERTYPE_1:
                    getNetWorkDats();
                    break;
                case Constant.HANDLERTYPE_2:
                    saveDatas();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(R.layout.execute_bills_list, container, false);
        initView();
        return rootView;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (hasInternetConnected()) {
                pageNo = 0;
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
            } else {
                addLocality();
            }
        }
    }

    //添加布局文件
    private void initView() {
        if (waitDialogRectangle == null)
            waitDialogRectangle = new WaitDialogRectangle(getActivity());
        mGson = new Gson();
        listview = (ListView) rootView.findViewById(R.id.execute_bills_listview);
        mLoader = new NestRefreshLayout(listview);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        if (hasInternetConnected()) {
            getNetWorkDats();
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

        if (pageNo == 0) {
            mExexuteAdapter = new ExexuteAdapter(getActivity(), mList);
            listview.setAdapter(mExexuteAdapter);
            mExexuteAdapter.notifyDataSetChanged();
        } else {
            if (pageNo <= ((total - 1) / pageSize)) {
                mExexuteAdapter.refreshList(mList);
            } else {
                mLoader.onLoadAll();//加载全部
            }
        }

        ExexuteAdapter.setOnClickBillsListener(this);
        if (hasInternetConnected())
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
    }

    /**
     * 获取执行中列表的数据
     */
    private void getNetWorkDats() {
        if (!hasInternetConnected())
            return;
        Constant.isRefurbishUI = false;
        waitDialogRectangle.show();
        QuerySttleManageLoading getGoods = new QuerySttleManageLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100810003,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                Constant.appOrderMoney_settleCode,
                Constant.appOrderMoney_oppositeName,
                String.valueOf(fragment_type),
                Constant.appOrderMoney_executeStartTime,
                Constant.appOrderMoney_executeEndTime,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        getNetWotk(getGoods);
    }


    /**
     * 缓存数据
     */
    private void saveDatas() {
        if (dbList.size() <= 0)
            return;

        AppOrderGoodsUtils.getInstance(getActivity()).deleteQuerySttleManageGain(Constant.FRGMENT_TYPE + 1);

        for (int i = 0; i < dbList.size(); i++) {
            dbList.get(i).setUuid(Constant.getUUID());
            dbList.get(i).setRegister_id(Constant.FRGMENT_TYPE + 1);
            AppOrderGoodsUtils.getInstance(getActivity()).insertQuerySttleManageGainInfo(dbList.get(i));
        }
    }

    /**
     * 加载本地数据
     */
    private void addLocality() {
        mList = AppOrderGoodsUtils.getInstance(getActivity()).queryQuerySttleManageGainInfo(fragment_type);
        if (mList.size() > 0) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (hasInternetConnected() && Constant.isRefurbishUI) {
            pageNo = 0;
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
        } else if (!hasInternetConnected()) {
            addLocality();
        }
    }

    @Override
    public void onBillsIDItemCilckListener(int pos) {
        //菜单详情操作
        String id = dbList.get(pos).getId();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUNDLE_TYPE, id);
        intentActivity(BillsSectionDetailsActivity.class, bundle);
    }

    @Override
    public void onBtnClickListener(int pos) {
        //列表按钮点击效果
        if (dbList == null) {
            toastSHORT("数据为空");
            return;
        }
        int status = dbList.get(pos).getStatus();
        switch (status) {
            case Constant.STATUS_TYPE_4:
                showBillsDialog(getString(R.string.dialog_stop_msg), dbList.get(pos).getId(), Constant.StopSettleUrl);
                break;
            case Constant.STATUS_TYPE_5:
                showBillsDialog(getString(R.string.dialog_revokeToStop_msg), dbList.get(pos).getId(), Constant.RevokeToStopUrl);
                break;
            case Constant.STATUS_TYPE_6:
                showBillsDialog(getString(R.string.dialog_revokeToStop_msg), dbList.get(pos).getId(), Constant.RevokeToStopUrl);
                break;
        }
    }

    /**
     * 按钮点击效果
     *
     * @param dialogTitle
     * @param id
     * @param url
     */
    public void showBillsDialog(String dialogTitle, final String id, final String url) {
        myDialog = new MyDialog(getActivity());
        myDialog.setMessage(dialogTitle);
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                commitBtn(id, url);
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
     * 请求网络数据
     */
    public void getNetWotk(QuerySttleManageLoading getGoods) {
        Constant.JSONFATHERRESPON = "QuerySttleManageRespon";
        LogShow(mGson.toJson(getGoods));
        OkGo.post(Constant.QuerySttleManageUrl)
                .tag(this)
                .params("param", mGson.toJson(getGoods))
                .execute(new JsonCallBack<QuerySttleManageRespon<List<QuerySttleManageGain>>>() {

                    @Override
                    public void onSuccess(QuerySttleManageRespon<List<QuerySttleManageGain>>
                                                  listQuerySttleManageRespon, Call call,
                                          Response response) {
                        if (listQuerySttleManageRespon.list != null) {
                            mList = listQuerySttleManageRespon.list;
                            total = listQuerySttleManageRespon.pagecount;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        LogShow("错误");
                        waitDialogRectangle.dismiss();
                        if (Constant.public_code){
                            //退出操作
                            LogoutUtils.exitUser(ExecuteFragment.this);
                        }
                    }
                });
    }

    /**
     * 请求终止协议操作
     * Constant.StopSettleUrl
     *
     * @param id
     */
    public void commitBtn(String id, String url) {
        waitDialogRectangle.show();
        StopSettleLoading respon = new StopSettleLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100810003,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                id);
        LogShow(mGson.toJson(respon));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(url)
                .tag(this)
                .params("param", mGson.toJson(respon))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidReturnMessageRespon, Call call, Response response) {
                        toastSHORT(voidReturnMessageRespon.return_message);
                        if (voidReturnMessageRespon.return_code != 999) {
                            //判断状态
                            new MyToast(getActivity(), getString(R.string.toast_stop_msg));
                            refurbishNetWork1();
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
                            LogoutUtils.exitUser(ExecuteFragment.this);
                        }
                    }
                });
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        //刷新操作
        pageNo = 0;
//        Constant.clearDatas();
        getNetWorkDats();
        isSave = true;
        mLoader.onLoadFinished();//加载全部
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        //加载更多操作
        pageNo++;
//        Constant.clearDatas();
        isSave = false; //上啦缓存数据
        getNetWorkDats();
        mLoader.onLoadFinished();//加载结束
    }

    /**
     * 刷新数据
     */
    public void refurbishNetWork1() {
        pageNo = 0;
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
    }
}
