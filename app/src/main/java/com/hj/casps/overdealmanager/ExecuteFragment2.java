package com.hj.casps.overdealmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hj.casps.R;
import com.hj.casps.adapter.overdealadapter.ExexuteAdapter2;
import com.hj.casps.base.FragmentBase;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appsettle.QuerySttleManageGain;
import com.hj.casps.entity.appsettle.QuerySttleManageLoading;
import com.hj.casps.entity.appsettle.QuerySttleManageRespon;
import com.hj.casps.entity.appsettle.StopSettleLoading;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.widget.WaitDialogRectangle;
import com.lzy.okgo.OkGo;

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

public class ExecuteFragment2 extends FragmentBase implements
        ExexuteAdapter2.onClickBillsListener,
        OnPullListener {
    private View view;
    private ListView listview;
    private List<QuerySttleManageGain> mList;
    private ExexuteAdapter2 mExexuteAdapter;
    private int fragment_type = 0;

    public ExecuteFragment2(int fragment_type) {
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
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.execute_bills_list, container, false);
        initView();
        return view;
    }

    //添加布局文件
    private void initView() {
        if (waitDialogRectangle == null)
            waitDialogRectangle = new WaitDialogRectangle(getActivity());
        mGson = new Gson();

        listview = (ListView) view.findViewById(R.id.execute_bills_listview);
        mLoader = new NestRefreshLayout(listview);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        ExexuteAdapter2.setOnClickBillsListener(this);

        if (hasInternetConnected()) {
            getNetWorkDats();
        }
    }


    /**
     * 设置界面
     */
    private void setInitView() {
        if (pageNo == 0) {
            mExexuteAdapter = new ExexuteAdapter2(getActivity(), mList);
            listview.setAdapter(mExexuteAdapter);
            mExexuteAdapter.notifyDataSetChanged();
        } else {
            if (pageNo <= ((total - 1) / pageSize)) {
                mExexuteAdapter.refreshList(mList);
            } else {
                mLoader.onLoadAll();//加载全部
            }
        }
    }

    /**
     * 获取执行中列表的数据
     */
    private void getNetWorkDats() {
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


    @Override
    public void onResume() {
        super.onResume();
        getNetWorkDats();
        LogShow("onResume");
    }

    @Override
    public void onBillsIDItemCilckListener(int pos) {
        //菜单详情操作
        String id = mList.get(pos).getId();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUNDLE_TYPE, id);
        intentActivity(BillsSectionDetailsActivity.class, bundle);
    }

    @Override
    public void onBtnClickListener(int pos) {
        //列表按钮点击效果
        LogShow(fragment_type + "2-----------:" + mList.get(pos).getCtrMoney());

        if (mList == null) {
            toastSHORT("数据为空");
            return;
        }

        int status = mList.get(pos).getStatus();
        switch (status) {
            case Constant.STATUS_TYPE_4:
                showBillsDialog(getString(R.string.dialog_stop_msg), mList.get(pos).getId(), Constant.StopSettleUrl);
                break;
            case Constant.STATUS_TYPE_5:
                showBillsDialog(getString(R.string.dialog_revokeToStop_msg), mList.get(pos).getId(), Constant.RevokeToStopUrl);
                break;
            case Constant.STATUS_TYPE_6:
                showBillsDialog(getString(R.string.dialog_revokeToStop_msg), mList.get(pos).getId(), Constant.RevokeToStopUrl);
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
                waitDialogRectangle.show();
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
        waitDialogRectangle.show();
        LogShow(mGson.toJson(getGoods));
        Constant.JSONFATHERRESPON = "QuerySttleManageRespon";
        OkGo.post(Constant.QuerySttleManageUrl)
                .tag(this)
                .params("param", mGson.toJson(getGoods))
                .execute(new JsonCallBack<QuerySttleManageRespon<List<QuerySttleManageGain>>>() {

                    @Override
                    public void onSuccess(QuerySttleManageRespon<List<QuerySttleManageGain>>
                                                  listQuerySttleManageRespon, Call call,
                                          Response response) {
                        mList = listQuerySttleManageRespon.list;
                        total = listQuerySttleManageRespon.pagecount;
                        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        LogShow("错误");
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    /**
     * 请求种植
     * Constant.StopSettleUrl
     *
     * @param id
     */
    public void commitBtn(String id, String url) {
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
                            refurbishNetWork2();
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
        getNetWorkDats();
//        isSave = false; //下啦清除数据库
        mLoader.onLoadFinished();//加载全部
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        //加载更多操作
        pageNo++;
//        isSave = true; //上啦缓存数据
//        Constant.clearDatas();
        getNetWorkDats();
        mLoader.onLoadFinished();//加载结束
    }


    /**
     * 跳转页面
     *
     * @param to
     * @param bundle
     */
    public void intentActivity(Class to, Bundle bundle) {
        Intent intent = new Intent(getActivity(), to);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 刷新数据
     */
    public void refurbishNetWork2() {
        pageNo = 0;
        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
    }
}
