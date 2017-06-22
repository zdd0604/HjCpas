package com.hj.casps.overdealmanager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.overdealadapter.QueryOppositeAdapter;
import com.hj.casps.adapter.overdealadapter.SellAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appsettle.QueryOppositeListEntity;
import com.hj.casps.entity.appsettle.QueryOppositeListLoading;
import com.hj.casps.entity.appsettle.QueryOppositeListRespon;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 创建结款单
 */
public class NewCreateSettlement extends ActivityBaseHeader2
        implements View.OnClickListener {
    @BindView(R.id.create_setttlement_btn_next)
    FancyButton create_setttlement_btn_next;
    @BindView(R.id.create_setttlement_rb_buyersId)
    RadioButton create_setttlement_rb_buyersId;
    @BindView(R.id.create_setttlement_tv_buyersId)
    TextView create_setttlement_tv_buyersId;
    @BindView(R.id.create_setttlement_img_buyersId)
    ImageView create_setttlement_img_buyersId;

    @BindView(R.id.create_setttlement_rb_sellersId)
    RadioButton create_setttlement_rb_sellersId;
    @BindView(R.id.create_setttlement_tv_sellersId)
    TextView create_setttlement_tv_sellersId;
    @BindView(R.id.create_setttlement_img_sellersId)
    ImageView create_setttlement_img_sellersId;

    private View contentView;
    private ListView listView;
    private List<QueryOppositeListEntity> mList;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    refreshUI();
                    break;
                case Constant.HANDLERTYPE_1:

                    break;
            }
        }
    };


    private void refreshUI() {
        if (mList.size() > 0) {
            restoreInitview(mList.get(0).getBuyersId(),
                    mList.get(0).getBuyersName(),
                    mList.get(0).getSellersId(),
                    mList.get(0).getSellersName());
        } else {
            restoreInitview("", "", "", "");
            toastSHORT("数据为空");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create_settlement);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.hint_tv_create_section_bills_title));
        create_setttlement_btn_next.setOnClickListener(this);
        create_setttlement_rb_buyersId.setOnClickListener(this);
        create_setttlement_rb_sellersId.setOnClickListener(this);
        create_setttlement_rb_buyersId.setChecked(true);
        create_setttlement_tv_buyersId.setEnabled(false);
        create_setttlement_tv_sellersId.setEnabled(true);
        create_setttlement_tv_sellersId.setOnClickListener(this);
        create_setttlement_tv_buyersId.setOnClickListener(this);
        if (hasInternetConnected()) {
            getNetWorkSendExpress(Constant.isbuy);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_setttlement_rb_buyersId:
                Constant.isbuy = Constant.buy;
                mList.clear();
                getNetWorkSendExpress(Constant.isbuy);
                create_setttlement_img_buyersId.setVisibility(View.GONE);
                create_setttlement_img_sellersId.setVisibility(View.VISIBLE);
                create_setttlement_tv_buyersId.setEnabled(false);
                create_setttlement_tv_sellersId.setEnabled(true);
                break;
            case R.id.create_setttlement_rb_sellersId:
                Constant.isbuy = Constant.sell;
                mList.clear();
                getNetWorkSendExpress(Constant.isbuy);
                create_setttlement_img_buyersId.setVisibility(View.VISIBLE);
                create_setttlement_img_sellersId.setVisibility(View.GONE);
                create_setttlement_tv_buyersId.setEnabled(true);
                create_setttlement_tv_sellersId.setEnabled(false);
                break;
            case R.id.create_setttlement_tv_sellersId:
                if (mList.size() > 0)
                    CreateDialog();
                break;
            case R.id.create_setttlement_tv_buyersId:
                if (mList.size() > 0)
                    CreateDialog();
                break;
            case R.id.create_setttlement_btn_next:
                if (StringUtils.isStrTrue(getTvVaule(create_setttlement_tv_buyersId))) {
                    intentActivity(NewCreateSettlDetails.class, bundle);
                } else {
                    toastSHORT("付款方不能为空");
                }
                break;
        }
    }


    /**
     * 网络请求获取发货列表
     */
    private void getNetWorkSendExpress(String isbuy) {
        waitDialogRectangle.show();
        QueryOppositeListLoading qEntity = new QueryOppositeListLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                isbuy);
        LogShow(mGson.toJson(qEntity));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.QueryOppositeListUrl)
                .tag(this)
                .params("param", mGson.toJson(qEntity))
                .execute(new JsonCallBack<QueryOppositeListRespon<List<QueryOppositeListEntity>>>() {
                    @Override
                    public void onSuccess(QueryOppositeListRespon<List<QueryOppositeListEntity>> listQueryOppositeListRespon, Call call, Response response) {
                        if (listQueryOppositeListRespon.list != null) {
                            mList = listQueryOppositeListRespon.list;
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
                            LogoutUtils.exitUser(NewCreateSettlement.this);
                        }
                    }
                });
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
        if (Constant.isbuy.equals(Constant.buy)) {
            QueryOppositeAdapter qadapter = new QueryOppositeAdapter(context, mList);
            listView.setAdapter(qadapter);
            qadapter.notifyDataSetChanged();
        } else {
            SellAdapter sellAdapter = new SellAdapter(context, mList);
            listView.setAdapter(sellAdapter);
            sellAdapter.notifyDataSetChanged();
        }
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(contentView)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Constant.isbuy.equals(Constant.buy)) {
                    create_setttlement_tv_sellersId.setText(mList.get(position).getSellersName());
                } else {
                    create_setttlement_tv_buyersId.setText(mList.get(position).getBuyersName());
                }
                restoreInitview(mList.get(position).getBuyersId(),
                        mList.get(position).getBuyersName(),
                        mList.get(position).getSellersId(),
                        mList.get(position).getSellersName());
                dialog.dismiss();
            }
        });
    }

    /**
     * 设置默认数据
     *
     * @param bsid
     * @param bsName
     * @param ssellid
     * @param ssellName
     */
    private void restoreInitview(String bsid, String bsName, String ssellid, String ssellName) {
        Constant.appOrderMoney_buyersId = bsid;
        Constant.appOrderMoney_buyersName = bsName;
        Constant.appOrderMoney_sellersId = ssellid;
        Constant.appOrderMoney_sellersName = ssellName;
        LogShow(bsid + "--" + bsName + "--" + ssellid + "--" + ssellName);
        create_setttlement_tv_buyersId.setText(Constant.appOrderMoney_buyersName);
        create_setttlement_tv_sellersId.setText(Constant.appOrderMoney_sellersName);
    }
}
