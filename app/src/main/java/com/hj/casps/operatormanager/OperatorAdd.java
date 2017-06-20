package com.hj.casps.operatormanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.operatoradapter.RelationAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appUser.CheckUserLoading;
import com.hj.casps.entity.appUser.EditStateOfUserLoading;
import com.hj.casps.entity.appUser.EditUserLoading;
import com.hj.casps.entity.appUser.ReturnMessageRespon;
import com.hj.casps.entity.appUser.ToAddUserPageLoading;
import com.hj.casps.entity.appUser.ToAddUserPageRespon;
import com.hj.casps.entity.appUser.ToEditUserPageAllRoles;
import com.hj.casps.entity.appUser.ToEditUserPageEntity;
import com.hj.casps.entity.appUser.ToEditUserPageLoading;
import com.hj.casps.entity.appUser.ToEditUserPageRespon;
import com.hj.casps.entity.appordergoodsCallBack.JsonCallBack;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 操作员添加以及修改的共用界面
 */
public class OperatorAdd extends ActivityBaseHeader2 implements View.OnClickListener {
    @BindView(R.id.operator_ed_account)
    EditText operator_ed_account;
    @BindView(R.id.operator_ed_account_name)
    EditText operator_ed_account_name;
    @BindView(R.id.operator_ed_account_mobile)
    EditText operator_ed_account_mobile;
    @BindView(R.id.operator_ed_account_emial)
    EditText operator_ed_account_emial;

    @BindView(R.id.operator_btn_commit)
    FancyButton operator_btn_commit;
    @BindView(R.id.tl_shopping_title)
    TextView tl_shopping_title;
    @BindView(R.id.relation_recycler)
    RecyclerView relation_recycler;
    @BindView(R.id.layout_head_left_btn)
    FancyButton layout_head_left_btn;
    @BindView(R.id.layout_head_right_btn)
    FancyButton layout_head_right_btn;

    private RelationAdapter relationAdapter;
    private List<ToEditUserPageAllRoles> mList;
    private String userId;
    private int activity_type;
    private StringBuffer stringBuffer;
    private String roles;//角色Id,多个使用逗号隔开
    private ToEditUserPageEntity toEditUserPageEntity = null;
    private int state = 0;    //状态
    private int statetype = 0;
    private boolean isExist = false;//默认不存在

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    refreshCardUI();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_add);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        activity_type = getIntent().getIntExtra(Constant.BUNDLE_TYPE, 0);
        initView();

    }

    /**
     * 加载布局
     */
    private void initView() {
        /**
         * 判断当前页面是修改还是添加
         */
        switch (activity_type) {
            case Constant.APPUSER_ADDUSER:
                setTitle(getString(R.string.activity_add_operator_title));
                layout_head_left_btn.setVisibility(View.GONE);
                operator_ed_account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if (StringUtils.isStrTrue(getEdVaule(operator_ed_account))) {
                                if (!isString(getEdVaule(operator_ed_account).substring(0, 1))){
                                    toastSHORT("账户必须以字母开头");
                                    return;
                                }

                                if (getTvVaule(operator_ed_account).length() < 3){
                                    toastSHORT("账户最少三位");
                                    return;
                                }
                                checkUser();
                            }
                        }
                    }
                });

                getRolesData();
                break;
            case Constant.APPUSER_EDITUSER:
                setTitle(getString(R.string.activity_edit_operator_title));
                getNetWorkData();
                break;
        }

        titleRight.setVisibility(View.GONE);
        layout_head_left_btn.setText(getString(R.string.hint_block_up_title));
        layout_head_right_btn.setVisibility(View.VISIBLE);
        layout_head_left_btn.setOnClickListener(this);
        layout_head_right_btn.setOnClickListener(this);
        operator_btn_commit.setOnClickListener(this);
        findViewById(R.id.layout_head_right_tv).setVisibility(View.GONE);
        tl_shopping_title.setText(getString(R.string.hint_about_role_title));
        relation_recycler.setLayoutManager(new GridLayoutManager(this, 3));

        operator_ed_account_mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (StringUtils.isStrTrue(getEdVaule(operator_ed_account_mobile)))
                        if (!isMobileNO(getEdVaule(operator_ed_account_mobile)))
                            toastSHORT("请填写正确的电话");
                }
            }
        });
        operator_ed_account_emial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (StringUtils.isStrTrue(getEdVaule(operator_ed_account_emial)))
                        if (!isEmail(getEdVaule(operator_ed_account_emial)))
                            toastSHORT("请填写正确的邮箱");
                }
            }
        });
    }

    /**
     * 刷新数据
     * 同时可将数据进行缓存
     */
    private void refreshCardUI() {
        if (toEditUserPageEntity != null) {
            operator_ed_account.setText(toEditUserPageEntity.getAccount());
            operator_ed_account.setEnabled(false);
            operator_ed_account_name.setText(toEditUserPageEntity.getName());
            operator_ed_account_mobile.setText(toEditUserPageEntity.getTelephone());
            operator_ed_account_emial.setText(toEditUserPageEntity.getEmail());

            state = toEditUserPageEntity.getState();
            setBtn(state);
        }
        relationAdapter = new RelationAdapter(this, mList);
        relation_recycler.setAdapter(relationAdapter);
        relationAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_head_left_btn:
                editStateOfUser();
                break;
            case R.id.layout_head_right_btn:
                intentActivity(this, OperatorPassWord.class);
                break;
            case R.id.operator_btn_commit:
                commitDatas();
                break;
        }
    }

    /**
     * 判断是新增还是修改
     */
    private void commitDatas() {
        switch (activity_type) {
            case Constant.APPUSER_ADDUSER:
                submitUserData();
                break;
            case Constant.APPUSER_EDITUSER:
                editUser();
                break;
        }
    }


    /**
     * 获取修改信息
     */
    private void getNetWorkData() {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "ToEditUserPageRespon";
        final ToEditUserPageLoading tEdit = new ToEditUserPageLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC1021,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                userId
        );
        OkGo.post(Constant.ToEditUserPageUrl)
                .tag(this)
                .params("param", mGson.toJson(tEdit))
                .execute(new JsonCallBack<ToEditUserPageRespon<List<ToEditUserPageAllRoles>>>() {

                    @Override
                    public void onSuccess(ToEditUserPageRespon<List<ToEditUserPageAllRoles>> listToEditUserPageRespon, Call call, Response response) {
                        toEditUserPageEntity = listToEditUserPageRespon.userInfo;
                        if (listToEditUserPageRespon.allRoles != null) {
                            mList = listToEditUserPageRespon.allRoles;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                            waitDialogRectangle.dismiss();
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

    /**
     * 提交编辑的数据
     */
    private void editUser() {

        if (!StringUtils.isStrTrue(getEdVaule(operator_ed_account_name))) {
            toastSHORT("请填写名称");
            return;
        }

        if (StringUtils.isStrTrue(getEdVaule(operator_ed_account_mobile)))
            if (!isMobileNO(getEdVaule(operator_ed_account_mobile))) {
                toastSHORT("请填写正确的电话号码");
                return;
            }
        if (StringUtils.isStrTrue(getEdVaule(operator_ed_account_emial)))
            if (!isEmail(getEdVaule(operator_ed_account_emial))) {
                toastSHORT("请填写正确的邮箱");
                return;
            }

        stringBuffer = new StringBuffer();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isHasRole()) {
                stringBuffer.append(mList.get(i).getRoleId() + ",");
            }
        }
        roles = stringBuffer.toString();
        if (!StringUtils.isStrTrue(roles)) {
            toastSHORT("请至少选择一个角色");
            return;
        }

        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        EditUserLoading edLoading = new EditUserLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC1021,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                toEditUserPageEntity.getUserId(),
                getEdVaule(operator_ed_account),
                getEdVaule(operator_ed_account_name),
                getEdVaule(operator_ed_account_mobile),
                getEdVaule(operator_ed_account_emial),
                Constant.editUser_password,
                Constant.editUser_password1,
                roles
        );
        LogShow(mGson.toJson(edLoading));
        OkGo.post(Constant.EditUserUrl)
                .tag(this)
                .params("param", mGson.toJson(edLoading))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon returnMessageRespon, Call call, Response response) {
                        toastSHORT(returnMessageRespon.return_message);
                        if (returnMessageRespon.return_code == 0) {
                            setResult(Constant.CREATE_USER);
                            finish();
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
     * 获取关联角色信息
     */
    private void getRolesData() {
        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "ToAddUserPageRespon";
        ToAddUserPageLoading taddLoading = new ToAddUserPageLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC1021,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member()
        );
        OkGo.post(Constant.ToAddUserPageUrl)
                .tag(this)
                .params("param", mGson.toJson(taddLoading))
                .execute(new JsonCallBack<ToAddUserPageRespon<List<ToEditUserPageAllRoles>>>() {
                    @Override
                    public void onSuccess(ToAddUserPageRespon<List<ToEditUserPageAllRoles>> listToAddUserPageRespon, Call call, Response response) {
                        if (listToAddUserPageRespon.dataList != null) {
                            mList = listToAddUserPageRespon.dataList;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
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
     * 添加操作员（添加操作员页面提交时）
     */
    private void submitUserData() {
        if (!StringUtils.isStrTrue(getEdVaule(operator_ed_account))) {
            toastSHORT("请填写账户");
            return;
        }

        if (isExist) {
            toastSHORT("账户名称已存在");
            return;
        }

        if (StringUtils.isStrTrue(getEdVaule(operator_ed_account)))
            if (!isString(getEdVaule(operator_ed_account))) {
                toastSHORT("账户必须以字母开头");
            }

        if (StringUtils.isStrTrue(getEdVaule(operator_ed_account_name)))
            if (!StringUtils.isStrTrue(getEdVaule(operator_ed_account_name))) {
                toastSHORT("请填写名称");
                return;
            }

        if (StringUtils.isStrTrue(getEdVaule(operator_ed_account_mobile)))
            if (isMobileNO(getEdVaule(operator_ed_account_mobile))) {
                toastSHORT("请填写正确的电话号码");
                return;
            }
        if (StringUtils.isStrTrue(getEdVaule(operator_ed_account_emial)))
            if (!isEmail(getEdVaule(operator_ed_account_emial))) {
                toastSHORT("请填写正确的邮箱");
                return;
            }

        stringBuffer = new StringBuffer();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isHasRole()) {
                stringBuffer.append(mList.get(i).getRoleId() + ",");
            }
        }
        roles = stringBuffer.toString();
        if (!StringUtils.isStrTrue(roles)) {
            toastSHORT("请至少选择一个角色");
            return;
        }

        if (!StringUtils.isStrTrue(Constant.editUser_password) ||
                !StringUtils.isStrTrue(Constant.editUser_password1)) {
            toastSHORT("请填写密码");
            return;
        }

        waitDialogRectangle.show();
        Constant.JSONFATHERRESPON = "CreateUserRespon";
        EditUserLoading edLoading = new EditUserLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC1021,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                getEdVaule(operator_ed_account),
                getEdVaule(operator_ed_account_name),
                getEdVaule(operator_ed_account_mobile),
                getEdVaule(operator_ed_account_emial),
                Constant.editUser_password,
                Constant.editUser_password1,
                roles
        );
        LogShow(mGson.toJson(edLoading));
        OkGo.post(Constant.CreateUserUrl)
                .tag(this)
                .params("param", mGson.toJson(edLoading))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon<Void> voidReturnMessageRespon, Call call, Response response) {
                        toastSHORT(voidReturnMessageRespon.return_message);
                        setResult(Constant.CREATE_USER);
                        waitDialogRectangle.dismiss();
                        finish();
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
     * 修改操作员状态（停用、启用操作动作）
     */
    private void editStateOfUser() {
        waitDialogRectangle.show();
        EditStateOfUserLoading edLoading = new EditStateOfUserLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC1021,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                userId,
                statetype);
        LogShow(mGson.toJson(edLoading));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.EditStateOfUserUrl)
                .tag(this)
                .params("param", mGson.toJson(edLoading))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon returnMessageRespon, Call call, Response response) {
                        toastSHORT(returnMessageRespon.return_message);
                        waitDialogRectangle.dismiss();
                        getNetWorkData();
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
     * 判断用户名是否存在
     */
    private void checkUser() {
//        waitDialogRectangle.show();
        CheckUserLoading ckLoading = new CheckUserLoading(
                Constant.publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC1021,
                Constant.publicArg.getSys_user(),
                Constant.publicArg.getSys_member(),
                getEdVaule(operator_ed_account));

        log(mGson.toJson(ckLoading));
        Constant.JSONFATHERRESPON = "ReturnMessageRespon";
        OkGo.post(Constant.CheckUserUrl)
                .tag(this)
                .params("param", mGson.toJson(ckLoading))
                .execute(new JsonCallBack<ReturnMessageRespon<Void>>() {
                    @Override
                    public void onSuccess(ReturnMessageRespon returnMessageRespon, Call call, Response response) {
                        isExist = returnMessageRespon.isExist;
                        if (isExist)
                            toastSHORT("账户名已存在");
//                        waitDialogRectangle.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
//                        waitDialogRectangle.dismiss();
                    }
                });

    }

    /**
     * 判断按钮的状态
     *
     * @param state
     */
    private void setBtn(int state) {
        switch (state) {
            case 0:
                layout_head_left_btn.setText("启用");
                statetype = 1;
                break;
            case 1:
                layout_head_left_btn.setText("停用");
                statetype = 0;
                break;
        }
    }
}
