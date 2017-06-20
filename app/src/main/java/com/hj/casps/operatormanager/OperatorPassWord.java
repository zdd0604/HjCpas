package com.hj.casps.operatormanager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * 设置密码
 */
public class OperatorPassWord extends ActivityBaseHeader2 implements View.OnClickListener {

    @BindView(R.id.operator_ed_pword)
    EditText operator_ed_pword;
    @BindView(R.id.operator_ed_affirmpword)
    EditText operator_ed_affirmpword;
    @BindView(R.id.affirm_pword_btn)
    FancyButton affirm_pword_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_pword);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.hint_layout_right_btn_title));
        titleRight.setVisibility(View.GONE);
        affirm_pword_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.affirm_pword_btn:
                isTrue();
                break;
        }
    }

    /**
     * 判断密码
     */
    private void isTrue() {
        if (!StringUtils.isStrTrue(getEdVaule(operator_ed_pword)) ||
                !StringUtils.isStrTrue(getEdVaule(operator_ed_affirmpword))) {
            toastSHORT("请填写密码");
            return;
        }

        if (getEdVaule(operator_ed_pword).length() < 6 ||
                getEdVaule(operator_ed_affirmpword).length() < 6) {
            toastSHORT("密码长度不能小于6位");
            return;
        }

        if (!StringUtils.strEquals(getEdVaule(operator_ed_pword), getEdVaule(operator_ed_affirmpword))) {
            toastSHORT("两次输入密码不同");
            return;
        }

        if (getEdVaule(operator_ed_pword).length() < 5 || getEdVaule(operator_ed_affirmpword).length() < 5) {
            toastSHORT("密码最少6个字符");
            return;
        }

        Constant.editUser_password = getEdVaule(operator_ed_pword);
        Constant.editUser_password1 = getEdVaule(operator_ed_affirmpword);
        finish();
    }
}
