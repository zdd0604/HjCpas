package com.hj.casps.commodity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.picturemanager.request.ResAddDiv;
import com.hj.casps.entity.picturemanager.request.ResUpdateDiv;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

public class ActivityAddPicRes extends ActivityBaseHeader2 {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.btn)
    FancyButton btn;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.ll)
    LinearLayout ll;
    private Intent intent;
    private String type;
    public static final String IS_ADD = "IS_ADD";
    public static final String BASE_ID = "BASE_ID";
    private String baseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actitity_add_pic_res);
        ButterKnife.bind(this);
        intent = getIntent();
        initView();
        setTitle("图片库管理");
        setTitleRight("", null);

    }

    private void initView() {
        type = intent.getStringExtra(Constant.INTENT_TYPE);
        baseId = intent.getStringExtra(Constant.INTENT_BASEID);
        switch (type) {
            case Constant.PIC_DEL:
                ll.setVisibility(View.GONE);
                String divId = intent.getStringExtra(Constant.INTENT_DIV_ID);

                break;
            case Constant.PIC_ADD:
                handleAdd();
                break;
            case Constant.PIC_EDIT:
                handleEdit();
                break;
        }
    }



    //处理编辑图片
    private void handleEdit() {
        tv_name.setText("目录名称");
        String divName = intent.getStringExtra(Constant.INTENT_DIV_NAME);
        et_name.setHint(divName);


    }
    //处理添加图片

    private void handleAdd() {
        tv_name.setText("目录名称");
        et_name.setHint("请输入目录名称");
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        String divName = et_name.getText().toString().trim();
        switch (type) {
            case Constant.PIC_ADD:
                String parentId = intent.getStringExtra(Constant.INTENT_PARENTID);
                addDivForNet(divName, parentId, baseId);
                break;
            case Constant.PIC_EDIT:
                String divId = intent.getStringExtra(Constant.INTENT_DIV_ID);
                UpdateDivForNet(divName, divId, baseId);
                break;
        }

    }

    //修改个人素材库分类
    private void UpdateDivForNet(String divName, String divId, final String baseId) {
        PublicArg p = Constant.publicArg;

        String timeUUID = Constant.getTimeUUID();
        if(timeUUID.equals("")){
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ResUpdateDiv r = new ResUpdateDiv(p.getSys_token(), timeUUID, Constant.SYS_FUNC, p.getSys_user(), p.getSys_member(), divName, divId);
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.UpdateDivUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                if (pub.getReturn_code() == 0) {
                    new MyToast(ActivityAddPicRes.this, "修改图片目录成功");
                    Intent intent = new Intent();
                    intent.putExtra(IS_ADD, true);
                    intent.putExtra(BASE_ID, baseId);
                    setResult(RESULT_OK, intent);
                    ActivityAddPicRes.this.finish();
                }else if(pub.getReturn_code()==1101||pub.getReturn_code()==1102){
                    LogoutUtils.exitUser(ActivityAddPicRes.this);
                }


                else {
                    toastSHORT(pub.getReturn_message());
                    ActivityAddPicRes.this.finish();
                    return;
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                waitDialogRectangle.dismiss();
            }
        });


    }

    //添加个人素材库分类
    private void addDivForNet(String divName, String parentId, final String baseId) {
        //如果增加顶级目录，parentId传空
        if (parentId.equals("0")) parentId = "";

        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if(timeUUID.equals("")){
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ResAddDiv r = new ResAddDiv(p.getSys_token(), timeUUID,Constant.SYS_FUNC, p.getSys_user(), p.getSys_member(), divName, parentId, baseId);
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.AddDivUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                if (pub.getReturn_code() == 0) {
                    new MyToast(ActivityAddPicRes.this, "添加图片目录成功");
                    Intent intent = new Intent();
                    intent.putExtra(IS_ADD, true);
                    intent.putExtra(BASE_ID, baseId);
                    setResult(RESULT_OK, intent);
                    ActivityAddPicRes.this.finish();
                } else if(pub.getReturn_code()==1101||pub.getReturn_code()==1102){
                    LogoutUtils.exitUser(ActivityAddPicRes.this);
                }
                else {
                    toastSHORT(pub.getReturn_message());
                    return;
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                waitDialogRectangle.dismiss();
            }
        });
        //添加个人素材库分类
    }


}
