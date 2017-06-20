package com.hj.casps.operatormanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.operatoradapter.OperatorAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.AppUserUtils;
import com.hj.casps.entity.appUser.QueryUserEntity;
import com.hj.casps.entity.appUser.QueryUserLoading;
import com.hj.casps.entity.appUser.QueryUserRespon;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.lzy.okgo.OkGo;

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
 * 操作员管理界面列表
 */
public class OperatorListActivity extends ActivityBaseHeader implements OnPullListener,
        View.OnClickListener {
    @BindView(R.id.operator_list)
    ListView operator_list;
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    private List<QueryUserEntity> mList;
    private OperatorAdapter operatorAdapter;
    private int pageNo = 0;//	int	开始行
    private int pageSize = 10;//	int	页条数
    private int total;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    refreshCardUI();
                    break;
                case Constant.HANDLERTYPE_1:
                    saveDaoData();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        setTitle(getString(R.string.activity_operator_title));
        layout_head_left_btn.setOnClickListener(this);
        layout_head_right_tv.setOnClickListener(this);
        setTvVisibility();
        mLoader = new NestRefreshLayout(operator_list);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

        if (hasInternetConnected()) {
            getQueryMmbWareHouseGainDatas(pageNo);
        } else {
            addLocality();
        }
    }


    /**
     * 获取当前会员的操作员列表（获取 操作员管理 页面所需数据）
     */
    private void refreshCardUI() {
        if (pageNo == 0) {
            operatorAdapter = new OperatorAdapter(this, mList);
            operatorAdapter.notifyDataSetChanged();
            operator_list.setAdapter(operatorAdapter);
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
        } else {
            if (pageNo < (total / pageSize)) {
                operatorAdapter.refreshList(mList);
            } else {
                mLoader.onLoadAll();//加载全部
            }
        }
        operator_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundle.putString("userId", mList.get(position).getUserId());
                bundle.putInt(Constant.BUNDLE_TYPE, Constant.APPUSER_EDITUSER);
                intentActivity(OperatorAdd.class, Constant.START_ACTIVITY_TYPE, bundle);
            }
        });
    }


    /**
     * 保存数据库
     */
    private void saveDaoData() {
        AppUserUtils.getInstance(this).deleteAll();
        for (int i = 0; i < mList.size(); i++) {
            AppUserUtils.getInstance(this).insertInfo(mList.get(i));
        }
    }


    /**
     * 加载本地数据
     */
    private void addLocality() {
        List<QueryUserEntity> usrList = AppUserUtils.getInstance(this).queryInfo();
        if (usrList.size() > 0) {
            mList = usrList;
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_head_left_btn:
                addView();
                break;
            case R.id.layout_head_right_tv:
                CreateDialog(Constant.DIALOG_CONTENT_27);
                break;
        }
    }

    //添加
    private void addView() {
        bundle.putInt(Constant.BUNDLE_TYPE, Constant.APPUSER_ADDUSER);
        intentActivity(OperatorAdd.class, Constant.START_ACTIVITY_TYPE, bundle);
    }


    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageNo = 0;
        getQueryMmbWareHouseGainDatas(pageNo);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageNo++;
        getQueryMmbWareHouseGainDatas(pageNo);
        mLoader.onLoadFinished();//加载结束
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.START_ACTIVITY_TYPE && resultCode == Constant.CREATE_USER) {
            getQueryMmbWareHouseGainDatas(0);
        }
    }

    /**
     * 获取列表数据
     */
    private void getQueryMmbWareHouseGainDatas(int pageNo) {
        Constant.JSONFATHERRESPON = "QueryUserRespon";
        QueryUserLoading queryUserLoading = new QueryUserLoading(
                Constant.publicArg.getSys_token(),
                "00005",
                Constant.SYS_FUNC1021,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                String.valueOf(pageNo + 1),
                String.valueOf(pageSize + 5));
        log(mGson.toJson(queryUserLoading));
        OkGo.post(Constant.QueryUserUrl)
                .tag(this)
                .params("param", mGson.toJson(queryUserLoading))
                .execute(new JsonCallBack<QueryUserRespon<List<QueryUserEntity>>>() {

                    @Override
                    public void onSuccess(QueryUserRespon<List<QueryUserEntity>> listQueryUserRespon, Call call, Response response) {
                        if (listQueryUserRespon.dataList != null) {
                            total = listQueryUserRespon.totalCount;
                            mList = listQueryUserRespon.dataList;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (e instanceof IllegalStateException)
                            toastSHORT(e.getMessage());
                    }
                });
    }

}
