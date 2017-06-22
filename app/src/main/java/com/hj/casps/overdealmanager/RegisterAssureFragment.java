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
import com.hj.casps.adapter.overdealadapter.RegisterAssuerAdapter;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoodsCallBack.AppOrderGoodsUtils;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appsettle.QuerySttleManageRespon;
import com.hj.casps.entity.appsettle.QuerySttleRegistGain;
import com.hj.casps.entity.appsettle.QuerySttleRegistLoading;
import com.hj.casps.entity.appsettle.StopSettleLoading;
import com.hj.casps.protocolmanager.ViewPagerFragment;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.widget.WaitDialogRectangle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.exception.OkGoException;

import java.util.ArrayList;
import java.util.List;

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Admin on 2017/5/2.
 * 结款单登记担保Fragment
 */
public class RegisterAssureFragment extends ViewPagerFragment implements
        RegisterAssuerAdapter.onClickBillsListener,
        OnPullListener {

    ListView execute_bills_listview;
    private boolean isSave = true;//是否保存数据
    private List<QuerySttleRegistGain> mList;
    private List<QuerySttleRegistGain> dbList = new ArrayList<>();
    private RegisterAssuerAdapter mRegisterAssuerAdapter;

    private int tab_type;

    /**
     * 判断哪个界面进去的
     *
     * @param tab_type
     */
    public RegisterAssureFragment(int tab_type) {
        this.tab_type = tab_type;
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
                    getNetWotk();
                    break;
                case Constant.HANDLERTYPE_2:
                    saveDatas();
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    /**
     * 设置布局
     */
    private void initView() {
        mGson = new Gson();
        execute_bills_listview = (ListView) rootView.findViewById(R.id.execute_bills_listview);
        mLoader = new NestRefreshLayout(execute_bills_listview);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        if (waitDialogRectangle == null)
            waitDialogRectangle = new WaitDialogRectangle(getActivity());
        if (hasInternetConnected()) {
            getNetWotk();
        } else {
            addLocality();
        }
    }

    /**
     * 获取网络数据
     */
    private void setInitView() {
        if (isSave)
            dbList.clear();

        dbList.addAll(mList);

        if (pageNo == 0) {
            mRegisterAssuerAdapter = new RegisterAssuerAdapter(getActivity(), mList);
            execute_bills_listview.setAdapter(mRegisterAssuerAdapter);
            mRegisterAssuerAdapter.notifyDataSetChanged();
        } else {
            if (pageNo <= ((total - 1) / pageSize)) {
                mRegisterAssuerAdapter.refreshList(mList);
            } else {
                mLoader.onLoadAll();//加载全部
            }
        }

        RegisterAssuerAdapter.setOnClickBillsListener(this);

        if (hasInternetConnected())
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);

    }


    /**
     * 缓存数据
     */
    private void saveDatas() {
        if (dbList.size() <= 0)
            return;

        AppOrderGoodsUtils.getInstance(getActivity()).deleteQuerySttleRegist(Constant.FRGMENT_TYPE);

        for (int i = 0; i < dbList.size(); i++) {
            dbList.get(i).setRegister_id(Constant.FRGMENT_TYPE);
            dbList.get(i).setUuid(Constant.getUUID());
            AppOrderGoodsUtils.getInstance(getActivity()).insertQuerySttleRegistGainInfo(dbList.get(i));
        }
    }

    /**
     * 加载本地数据
     */
    private void addLocality() {
        mList = AppOrderGoodsUtils.getInstance(getActivity()).queryQuerySttleRegistGainInfo(tab_type);
        if (mList.size() > 0) {
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
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
        showBillsDialog(getString(R.string.dialog_stop_msg), dbList.get(pos).getId());
    }

    //登记操作
    public void showBillsDialog(String dialogTitle, final String id) {
        myDialog = new MyDialog(getActivity());
        myDialog.setMessage(dialogTitle);
        myDialog.setYesOnclickListener(getString(R.string.True), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
                waitDialogRectangle.show();
                commitBtn(id);
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
    public void getNetWotk() {
        if (!hasInternetConnected())
            return;
        Constant.isRefurbishUI = false;
        waitDialogRectangle.show();
        QuerySttleRegistLoading getRespon = new QuerySttleRegistLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                Constant.SEARCH_sendgoods_OrdertitleCode,
                Constant.SEARCH_sendgoods_OrderGoodName,
                String.valueOf(tab_type),
                Constant.appOrderMoney_executeStartTime,
                Constant.appOrderMoney_executeEndTime,
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize));
        LogShow(mGson.toJson(getRespon));
        Constant.JSONFATHERRESPON = "QuerySttleManageRespon";
        OkGo.post(Constant.QuerySttleRegistUrl)
                .tag(this)
                .params("param", mGson.toJson(getRespon))
                .execute(new JsonCallBack<QuerySttleManageRespon<List<QuerySttleRegistGain>>>() {
                    @Override
                    public void onSuccess(QuerySttleManageRespon<List<QuerySttleRegistGain>>
                                                  listQueryMyPendingSttleRespon,
                                          Call call, Response response) {
                        if (listQueryMyPendingSttleRespon.list != null) {
                            total = listQueryMyPendingSttleRespon.pagecount;
                            mList = listQueryMyPendingSttleRespon.list;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                        if (Constant.public_code){
                            //退出操作
                            LogoutUtils.exitUser(RegisterAssureFragment.this);
                        }
                    }
                });
    }

    /**
     * 申请登记
     *
     * @param id
     */
    public void commitBtn(String id) {
        StopSettleLoading respon = new StopSettleLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                id);
        LogShow(mGson.toJson(respon));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.RegistUrl)
                .tag(this)
                .params("param", mGson.toJson(respon))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidReturnMessageRespon,
                                          Call call, Response response) {
                        toastSHORT(voidReturnMessageRespon.return_message);
                        if (voidReturnMessageRespon.return_code != 999) {
                            //判断状态
                            new MyToast(context, getString(R.string.toast_stop_msg));
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
    public void onRefresh(AbsRefreshLayout listLoader) {
        //刷新操作
        pageNo = 0;
//        Constant.clearDatas();
        isSave = true;
        getNetWotk();
        mLoader.onLoadFinished();//加载全部
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        //加载更多操作
        pageNo++;
//        Constant.clearDatas();
        isSave = false;
        getNetWotk();
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.isRefurbishUI) {
            pageNo = 0;
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
        }
    }
}
