package com.hj.casps.commodity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPictureAddLibActivity extends ActivityBaseHeader2 {
    @BindView(R.id.fragment_mypic_empty_class_Et)
    EditText editText;
    @BindView(R.id.fragment_mypic_empty_class_btn)
    Button button;
    public static String ExtraButtonName = "name";
    public static String ExtraLevelName = "et";
    public static String ExtraButtonObject = "object";
    public static String ExtraButtonPosition = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture_add_lib);
        ButterKnife.bind(this);
        setTitle(R.string.pic_manege);
        button.setText(getIntent().getStringExtra(ExtraButtonName));
        editText.setText(getIntent().getStringExtra(ExtraLevelName));
    }

    @OnClick(R.id.fragment_mypic_empty_class_btn)
    void OnButtonClick(View v) {
        String trim = editText.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            toast("不能为空");
            return;
        }
        FragmentMyPic.PictureLevelEntity entity = null;
        if (getIntent().getParcelableExtra(ExtraButtonObject) == null) {
            entity = new FragmentMyPic.PictureLevelEntity(trim);
        } else {
            entity = getIntent().getParcelableExtra(ExtraButtonObject);
            entity.setName(trim);
        }
        Intent data = new Intent();
        data.putExtra(ExtraButtonObject, entity);
        data.putExtra(ExtraButtonPosition, getIntent().getIntExtra(ExtraButtonPosition, -1));
        setResult(RESULT_OK, data);
        finish();
    }
}
