package com.hj.casps.protocolmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appcontract.ShowAgreeBack;
import com.hj.casps.entity.appcontract.ShowAgreePost;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

//协议管理的同意合作协议
public class ProtocolAgree extends ActivityBaseHeader2 implements View.OnClickListener {

    private Spinner protocol_receipt_account;
    private Spinner protocol_delivery_address;
    private FancyButton protocol_sure;
    private String contract_id;
    private String contract_status;
    private String contract_type;
    private String[] addressLists;
    private String[] bankLists;
    private List<ShowAgreeBack.DataBean.AddresslistBean> addresslistBeen;
    private List<ShowAgreeBack.DataBean.BanklistBean> banklistBeen;
    private TestArrayAdapter stringArrayAdapter1;
    private TestArrayAdapter stringArrayAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_agree);
        initData();
        initView();
    }

    //加载数据
    private void initData() {
        contract_id = getIntent().getStringExtra("contract_id");
        contract_status = getIntent().getStringExtra("contract_status");
        contract_type = getIntent().getStringExtra("contract_type");
        ShowAgreePost post = new ShowAgreePost(publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100350004,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                contract_id,
                contract_status,
                contract_type);
        OkGo.post(Constant.ShowAgreeModalUrl)
                .tag(this)
                .params("param", mGson.toJson(post))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ShowAgreeBack backDetail = mGson.fromJson(s, ShowAgreeBack.class);
                        if (backDetail.getReturn_code() != 0) {
                            toast(backDetail.getReturn_message());
                        } else if (backDetail.getReturn_code() == 1101 || backDetail.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(ProtocolAgree.this);
                        } else {
                            addresslistBeen = backDetail.getData().getAddresslist();
                            banklistBeen = backDetail.getData().getBanklist();
                            addressLists = new String[addresslistBeen.size()];
                            bankLists = new String[banklistBeen.size()];
                            for (int i = 0; i < addresslistBeen.size(); i++) {
                                addressLists[i] = addresslistBeen.get(i).getAddress();
                            }
                            for (int i = 0; i < banklistBeen.size(); i++) {
                                bankLists[i] = banklistBeen.get(i).getBankname();
                            }
                            stringArrayAdapter1 = new TestArrayAdapter(getApplicationContext(), addressLists);
                            stringArrayAdapter2 = new TestArrayAdapter(getApplicationContext(), bankLists);
                            protocol_delivery_address.setAdapter(stringArrayAdapter1);
                            protocol_receipt_account.setAdapter(stringArrayAdapter2);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toast(e.getMessage());
                    }
                });

    }

    //设置页面
    private void initView() {
        setTitle(getString(R.string.protocol_agree));
        protocol_receipt_account = (Spinner) findViewById(R.id.protocol_receipt_account);
        protocol_delivery_address = (Spinner) findViewById(R.id.protocol_delivery_address);
        protocol_sure = (FancyButton) findViewById(R.id.protocol_sure);

        protocol_sure.setOnClickListener(this);

    }

    //点击提交
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.protocol_sure:
                submit();
                break;
        }
    }

    //提交
    private void submit() {
        if (protocol_receipt_account.getSelectedItemPosition() == -1 & protocol_delivery_address.getSelectedItemPosition() == -1) {
            toast("请您创建银行账号和地址");
            return;
        } else if (protocol_receipt_account.getSelectedItemPosition() == -1) {
            toast("请您创建银行账号");
            return;
        } else if (protocol_delivery_address.getSelectedItemPosition() == -1) {
            toast("请您创建地址");
            return;
        }
        ShowAgreePost post = new ShowAgreePost(publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC101100350004,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                contract_id,
                contract_type,
                addressLists[protocol_delivery_address.getSelectedItemPosition()],
                protocol_receipt_account.getSelectedItemPosition() == -1 ? "" : banklistBeen.get(protocol_receipt_account.getSelectedItemPosition()).getAccountno(),
                protocol_receipt_account.getSelectedItemPosition() == -1 ? "" : banklistBeen.get(protocol_receipt_account.getSelectedItemPosition()).getBankname());
        OkGo.post(Constant.AgreeContractUrl)
                .tag(this)
                .params("param", mGson.toJson(post))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ShowAgreeBack backDetail = mGson.fromJson(s, ShowAgreeBack.class);
                        if (backDetail.getReturn_code() == 1101 || backDetail.getReturn_code() == 1102) {
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(ProtocolAgree.this);
                        } else if (backDetail.getReturn_code() != 0) {
                            toast(backDetail.getReturn_message());
                        } else {
//                            ProtocolFragment.protocolFragment.refresh();
                            Constant.PROTOCOL_SEARCH = true;
//                            PROTOCOL_TYPE_NUM = Integer.valueOf(contract_type)-1;
                            setResult(33);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toast(e.getMessage());
                    }
                });

    }


}
