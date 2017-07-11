
package com.hj.casps.bankmanage;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appordergoods.CreateMmbWarehouseLoading;
import com.hj.casps.entity.appordergoods.GetTreeModalEntity;
import com.hj.casps.entity.appordergoods.GetTreeModalRespon;
import com.hj.casps.entity.appordergoods.ToEditMmbWarehouseLoading;
import com.hj.casps.entity.appordergoods.WarehouseEntity;
import com.hj.casps.entity.appordergoodsCallBack.AddressEditRespon;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.entity.appordermoney.MmbBankAccountEntity;
import com.hj.casps.entity.appsettle.AreaTreeBean;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.paymentmanager.CheckAccountNoEntity;
import com.hj.casps.entity.paymentmanager.RequestCheckAccountNo;
import com.hj.casps.entity.paymentmanager.RequestMmbBackAccout;
import com.hj.casps.ui.ChangeAddressPopwindow;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.CheckFormUtils;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.OkGoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zdd on 2017/4/17.
 * 账户修改
 */

public class EditCardActivity extends ActivityBaseHeader2 implements View.OnClickListener {
    @BindView(R.id.ed_account_name_title)
    TextView account_name_title;
    @BindView(R.id.ed_account_number_title)
    TextView account_number_title;
    @BindView(R.id.ed_bank_name_title)
    TextView bank_name_title;
    @BindView(R.id.ed_contacts_name_title)
    TextView contacts_name_title;
    @BindView(R.id.ed_mobile_number_title)
    TextView mobile_number_title;
    @BindView(R.id.ed_telephone_number_title)
    TextView telephone_number_title;

    @BindView(R.id.ed_account_name)
    EditText account_name;
    @BindView(R.id.ed_account_number)
    EditText account_number;
    @BindView(R.id.ed_bank_name)
    EditText bank_name;
    @BindView(R.id.ed_contacts_name)
    EditText contacts_name;
    @BindView(R.id.ed_mobile_number)
    EditText mobile_number;
    @BindView(R.id.ed_telephone_number)
    EditText telephone_number;
    @BindView(R.id.add_card_btn)
    FancyButton add_card_btn;

    private int card_type;
    CreateMmbWarehouseLoading upDate;
    private MmbBankAccountEntity cardInfo;
    private boolean isOne;
    private WarehouseEntity toAddressEditInfo;
    private String addressEditId;
    private String areaText;//名称
    private List<AreaTreeBean> addressList;
    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<>();

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    //收货地址编辑查询
                    mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
                    break;
                case Constant.HANDLERTYPE_1:
                    //如果是唯一的就提交数据
                    if (isOne) {
                        switch (card_type) {
                            case Constant.CARD_EDIT:
                                editCardData();
                                break;
                            case Constant.CARD_ADD:
                                saveCardData();
                                break;
                        }
                    } else {
                        toastSHORT("账户号码重复");
                    }
                    break;
                case Constant.HANDLERTYPE_2:
                    if (hasInternetConnected())
                        getAddress();
                    break;
                case Constant.HANDLERTYPE_3:
                    initDatas();
                    break;
                case Constant.HANDLERTYPE_4:
                    new MyToast(EditCardActivity.this, "保存银行账号成功");
                    break;
                case Constant.HANDLERTYPE_5:
                    new MyToast(EditCardActivity.this, "编辑银行账号成功");
                    break;
            }
        }


    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_card_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        card_type = getIntent().getIntExtra(Constant.CARD_TYPE, 0);
        titleRight.setVisibility(View.GONE);
        add_card_btn.setOnClickListener(this);

        //判断哪个界面进去的
        setInitView(card_type);
    }

    private void setInitView(int numb) {
        switch (numb) {
            case Constant.CARD_EDIT:
                //账户编辑
                setTitle(getString(R.string.activity_title_card_edit_tv));
                cardInfo = (MmbBankAccountEntity) getIntent().getExtras().getSerializable(Constant.BUNDLE_TYPE);
                //修改
                if (cardInfo != null) {
                    setContentValue(cardInfo.getAccountname(), cardInfo.getAccountno(),
                            cardInfo.getBankname(), cardInfo.getContact(),
                            cardInfo.getMobilephone(), cardInfo.getPhone());
                }

                break;
            case Constant.CARD_ADD:
                //新增账户
                setTitle(getString(R.string.activity_title_card_add_tv));
                account_number.setHint(getText(R.string.edhint_account_number));
                setResult(Constant.CARD_ADD);
                break;
            case Constant.ADDRESS_EDIT:
                //地址修改
                setTitle(getString(R.string.activity_express_edit_title));
                setInitView();
                addressEditId = getIntent().getStringExtra(Constant.BUNDLE_TYPE_0);
                getNetWorkData(addressEditId);
                break;
            case Constant.ADDRESS_ADD:
                //新增地址
                setTitle(getString(R.string.activity_express_add_title));
                setInitView();
                setResult(Constant.ADDRESS_ADD);
                mHandler.sendEmptyMessage(Constant.HANDLERTYPE_2);
                break;
        }
    }

    /**
     * 设置默认显示
     */
    private void setInitView() {
        account_name_title.setText(R.string.hint_tv_express_address_title);
        account_number_title.setText(R.string.hint_tv_express_area_title);
        bank_name_title.setText(R.string.hint_tv_express_postcode_title);
        contacts_name_title.setText(R.string.hint_tv_express_contacts_title);
        mobile_number_title.setText(R.string.hint_tv_express_mobile_title);
        telephone_number_title.setText(R.string.hint_tv_express_phone_title);

        account_name.setHint(R.string.hint_ed_express_address_title);
        account_number.setFocusable(false);
        account_number.setOnClickListener(this);
        account_number.setHint("请选择所属地域");
        bank_name.setHint(R.string.hint_ed_express_postcode_title);
        bank_name.setInputType(InputType.TYPE_CLASS_NUMBER);
        contacts_name.setHint(R.string.hint_ed_express_contacts_title);
        mobile_number.setHint(R.string.hint_ed_express_mobile_title);
        telephone_number.setHint(R.string.hint_ed_express_phone_title);
    }

    /**
     * 设置内容
     *
     * @param accountname
     * @param accountno
     * @param bankname
     * @param contact
     * @param mobilephone
     * @param phone
     */
    private void setContentValue(String accountname, String accountno, String bankname,
                                 String contact, String mobilephone, String phone) {
        account_name.setText(accountname);
        account_number.setText(accountno);
        bank_name.setText(bankname);
        contacts_name.setText(contact);
        mobile_number.setText(mobilephone);
        telephone_number.setText(phone);
    }


    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas() {
        //修改
        if (toAddressEditInfo == null)
            return;

        //数据为空
        if (addressList == null)
            return;
        Constant.appOrderGoods_areaCode = toAddressEditInfo.getAreaId();
        for (int i = 0; i < addressList.size(); i++) {
            String areaCode1 = addressList.get(i).getId();//省地区的ID
            if (areaCode1.equals(Constant.appOrderGoods_areaCode)) {
                areaText = addressList.get(i).getText();
            } else if (addressList.get(i).getNodes() != null) {
                for (int a = 0; a < addressList.get(i).getNodes().size(); a++) {
                    String areaCode2 = addressList.get(i).getNodes().get(a).getId();
                    if (areaCode2.equals(Constant.appOrderGoods_areaCode)) {
                        areaText = addressList.get(i).getNodes().get(a).getText();
                    }
                }
            }
        }

        if (StringUtils.isStrTrue(toAddressEditInfo.getAreaDesc()))
            areaText = toAddressEditInfo.getAreaDesc();

        setContentValue(toAddressEditInfo.getAddress(),
                areaText,
                toAddressEditInfo.getZipcode(),
                toAddressEditInfo.getContact(),
                toAddressEditInfo.getMobilephone(),
                toAddressEditInfo.getPhone());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_account_number:
                if (addressList != null && addressList.size() >= 0) {
                    showCityPopupWindow();
                } else {
                    toastSHORT("地狱树获取失败");
                }
                break;
            case R.id.add_card_btn:
                saveEditInfo();
                break;
        }
    }

    private void showCityPopupWindow() {
        ChangeAddressPopwindow mChangeAddressPopwindow = new ChangeAddressPopwindow(this, addressList);
        mChangeAddressPopwindow.setAddress("北京市", "东城区");
        mChangeAddressPopwindow.showAtLocation(account_number, Gravity.BOTTOM, 0, 0);
        mChangeAddressPopwindow.setAddresskListener(new ChangeAddressPopwindow.OnAddressCListener() {
            @Override
            public void onClick(String province, String city) {
                account_number.setText(province + " " + city);
            }
        });
    }

    /**
     * 保存修改或者新增的信息
     */
    private void saveEditInfo() {
        switch (card_type) {
            case Constant.CARD_EDIT:
                if (checkForm())
                    saveCardInfo();
                break;
            case Constant.CARD_ADD:
                if (checkForm())
                    saveCardInfo();
                break;
            case Constant.ADDRESS_EDIT:
                saveExpressInfo();
                break;
            case Constant.ADDRESS_ADD:
                addExpressInfo();
                break;
        }
    }

    /**
     * 保存数据
     */
    private void saveCardInfo() {
        toCheckAccoutNo();
    }

    //编辑银行账户
    private void editCardData() {
        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        RequestMmbBackAccout r = new RequestMmbBackAccout(p.getSys_token(),
                timeUUID,
                Constant.SYS_FUNC,
                p.getSys_user(),
                p.getSys_member(),
                cardInfo.getId(),
                getEdVaule(account_number),
                getEdVaule(account_name),
                getEdVaule(bank_name),
                getEdVaule(contacts_name),
                getEdVaule(mobile_number),
                getEdVaule(telephone_number)
        );
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.UpdateBankAccountUrl).params("param", param).execute(new AbsCallback<String>() {
            @Override
            public String convertSuccess(Response response) throws Exception {
                String data = response.body().string();
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                int return_code = pub.getReturn_code();
                String return_message = pub.getReturn_message();
                if (return_code == 0) {
                    waitDialogRectangle.dismiss();
                    setResult(Constant.CARD_EDIT);
                    mHandler.sendEmptyMessage(Constant.HANDLERTYPE_5);
                    EditCardActivity.this.finish();
                } else if (return_code == 1101 || return_code == 1102) {
                    toastSHORT("重复登录或令牌超时");
                    LogoutUtils.exitUser(EditCardActivity.this);
                } else {
                    toast(return_message);
                }
                return null;
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
            }
        });


    }


    private void saveCardData() {

        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        RequestMmbBackAccout r = new RequestMmbBackAccout(p.getSys_token(),
                timeUUID,
                Constant.SYS_FUNC,
                p.getSys_user(),
                p.getSys_member(),
                getEdVaule(account_number),
                getEdVaule(account_name),
                getEdVaule(bank_name),
                getEdVaule(contacts_name),
                getEdVaule(mobile_number),
                getEdVaule(telephone_number)

        );
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.CreateMmbBackAccountUrl).params("param", param).execute(new AbsCallback<String>() {
            @Override
            public String convertSuccess(Response response) throws Exception {
                String data = response.body().string();
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                int return_code = pub.getReturn_code();
                String return_message = pub.getReturn_message();
                if (return_code == 0) {
                    waitDialogRectangle.dismiss();
                    setResult(Constant.CARD_ADD);
                    mHandler.sendEmptyMessage(Constant.HANDLERTYPE_4);
                    EditCardActivity.this.finish();
                } else if (return_code == 1101 || return_code == 1102) {
                    LogoutUtils.exitUser(EditCardActivity.this);
                } else {
                    toast(return_message);
                }
                return null;
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
            }
        });
    }

    /**
     * 验证表单信息
     */
    private boolean checkForm() {
        switch (card_type) {
            case Constant.CARD_ADD:
                if (!StringUtils.isStrTrue(getEdVaule(account_number))) {
                    toastSHORT("账户号码不能为空");
                    return false;
                }
                return checkFormValidity();

            case Constant.CARD_EDIT:
                if (cardInfo == null || cardInfo.getId() == null) {
                    toastSHORT("验证所需id不能为空");
                    return false;
                }
                if (!StringUtils.isStrTrue(getEdVaule(account_number))) {
                    toastSHORT("银行账号不能为空");
                    return false;
                }
                return checkFormValidity();
        }
        return true;

    }

    //验证表单合法性
    private boolean checkFormValidity() {
        /**
         * 验证银行账号信息  只能填数字  不可空白
         */
        if (!StringUtils.isStrTrue(getEdVaule(account_name))) {
            toastSHORT("账户全称不能为空");
            return false;
        } else if (!CheckFormUtils.stringCheck(getEdVaule(account_name))) {
            toastSHORT("账户全称只能是中文英文汉字");
            return false;
        } else if (!StringUtils.isStrTrue(getEdVaule(bank_name))) {
            toastSHORT("银行名称不能为空");
            return false;
        } else if (!StringUtils.isStrTrue(getEdVaule(account_number))) {
            toastSHORT("账户号码不能为空");
            return false;
        } else if (!CheckFormUtils.isDigits(getEdVaule(account_number))) {
            toastSHORT("账户号码只能是数字");
            return false;
        }
        return true;
    }

    private void toCheckAccoutNo() {
        String id = "";
        //如果是编辑银行账户信息才传id去验证   添加只需要传入账号就可以了
        if (card_type == Constant.CARD_EDIT) {
            id = cardInfo.getId();
        }


        PublicArg p = Constant.publicArg;
        String account_number = getEdVaule(this.account_number);
        RequestCheckAccountNo r = new RequestCheckAccountNo(p.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                p.getSys_user(),
                p.getSys_member(),
                account_number,
                id);
        String param = mGson.toJson(r);
        OkGo.post(Constant.CheckAccountNoUrl).params("param", param).execute(new AbsCallback<String>() {
            @Override
            public String convertSuccess(Response response) throws Exception {
                String data = response.body().string();
                CheckAccountNoEntity entity = GsonTools.changeGsonToBean(data, CheckAccountNoEntity.class);

                if (entity != null) {
                    int return_code = entity.getReturn_code();
                    String return_message = entity.getReturn_message();
                    int num = entity.getNum();
                    if (return_code == 0) {
                        EditCardActivity.this.isOne = num == 0 ? true : false;
                        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_1);
                        waitDialogRectangle.dismiss();
                    } else if (return_code == 1101 || return_code == 1102) {
                        LogoutUtils.exitUser(EditCardActivity.this);
                    } else {
                        toast(return_message);
                    }
                }
                return null;
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
            }
        });
    }


    /**
     * 添加收货地址列表
     */
    private void addExpressInfo() {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "AddressEditRespon";
        if (!StringUtils.isStrTrue(getEdVaule(account_name))) {
            toastSHORT("地址不能为空");
            waitDialogRectangle.dismiss();
            return;
        }

        if (StringUtils.isStrTrue(getEdVaule(mobile_number)))
            if (!isMobileNO(getEdVaule(mobile_number))) {
                toastSHORT("请输入正确的手机号");
                waitDialogRectangle.dismiss();
                return;
            }
        OkGo.post(Constant.CreateMmbWarehousetUrl)
                .tag(this)
                .params("param", mGson.toJson(addAddressContent()))
                .execute(new JsonCallBack<AddressEditRespon<Void>>() {

                    @Override
                    public void onSuccess(AddressEditRespon<Void> voidAddressEditRespon, Call call, Response response) {
                        toastSHORT(voidAddressEditRespon.successMsg);
                        setResult(Constant.ADDRESS_ADD);
                        waitDialogRectangle.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        waitDialogRectangle.dismiss();
                        if (e instanceof OkGoException)
                            toastSHORT(e.getMessage());
                    }
                });
    }


    /**
     * 收货地址列表编辑查询
     *
     * @param addressId
     */
    private void getNetWorkData(String addressId) {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "AddressEditRespon";
        final ToEditMmbWarehouseLoading tEdit = new ToEditMmbWarehouseLoading(
                Constant.publicArg.getSys_token(),
                "",
                Constant.SYS_FUNC,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_name(),
                Constant.publicArg.getSys_member(),
                addressId
        );
        OkGo.post(Constant.ToEditMmbWarehousetUrl)
                .tag(this)
                .params("param", mGson.toJson(tEdit))
                .execute(new JsonCallBack<AddressEditRespon<WarehouseEntity>>() {
                    @Override
                    public void onSuccess(AddressEditRespon<WarehouseEntity> warehouseEntitySimpleResponseGain, Call call, Response response) {
                        //获取数据成功后更新UI 界面
                        toAddressEditInfo = warehouseEntitySimpleResponseGain.row;
                        mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
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

    /**
     * 保存收货地址修改后的数据
     */
    private void saveExpressInfo() {
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        waitDialogRectangle.show();
        if (!StringUtils.isStrTrue(getEdVaule(account_name))) {
            toastSHORT("地址不能为空");
            waitDialogRectangle.dismiss();
            return;
        }
        if (StringUtils.isStrTrue(getEdVaule(mobile_number)))
            if (!isMobileNO(getEdVaule(mobile_number))) {
                toastSHORT("请输入正确的手机号");
                waitDialogRectangle.dismiss();
                return;
            }

        LogShow(mGson.toJson(getEditContent()));
        OkGo.post(Constant.UpdateMmbWarehouseUrl)
                .tag(this)
                .params("param", mGson.toJson(getEditContent()))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {

                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidAddressEditRespon, Call call, Response response) {
                        toastSHORT(voidAddressEditRespon.return_message);
                        setResult(Constant.ADDRESS_EDIT);
                        waitDialogRectangle.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (e instanceof OkGoException)
                            toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    /**
     * 获取地区选择器
     */
    private void getAddress() {
        waitDialogRectangle.show();
        GetTreeModalEntity getTreeModalEntity = new GetTreeModalEntity(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member()
        );
        OkGo.post(Constant.GetTreeModalUrl)
                .tag(this)
                .params("param", mGson.toJson(getTreeModalEntity))
                .execute(new JsonCallBack<GetTreeModalRespon<List<AreaTreeBean>>>() {
                    @Override
                    public void onSuccess(GetTreeModalRespon<List<AreaTreeBean>> listGetTreeModalRespon,
                                          Call call, Response response) {
                        if (listGetTreeModalRespon.return_code == 0) {
                            addressList = listGetTreeModalRespon.area_tree;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_3);
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


    /**
     * 编辑提交
     *
     * @return
     */
    private CreateMmbWarehouseLoading getEditContent() {
        upDate = new CreateMmbWarehouseLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                getEdVaule(account_name),
                Constant.appOrderGoods_areaCode,
                getEdVaule(bank_name),
                getEdVaule(contacts_name),
                getEdVaule(mobile_number),
                getEdVaule(telephone_number),
                addressEditId
        );
        return upDate;
    }

    /**
     * 添加地址
     *
     * @return
     */
    private CreateMmbWarehouseLoading addAddressContent() {
        upDate = new CreateMmbWarehouseLoading(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                getEdVaule(account_name),
                Constant.appOrderGoods_areaCode,
                getEdVaule(bank_name),
                getEdVaule(contacts_name),
                getEdVaule(mobile_number),
                getEdVaule(telephone_number)
        );
        return upDate;
    }
}
