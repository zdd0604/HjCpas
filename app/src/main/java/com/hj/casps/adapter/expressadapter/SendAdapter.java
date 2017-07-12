package com.hj.casps.adapter.expressadapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.appordergoods.QuerySendGoodsEntity;
import com.hj.casps.util.StringUtils;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 */

public class SendAdapter extends CommonAdapter<QuerySendGoodsEntity> {
    private int index1 = -1;
    public static onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(SendAdapter.onCheckedkType onCheckedkType) {
        SendAdapter.onCheckedkType = onCheckedkType;
    }

    public SendAdapter(Context context, List<QuerySendGoodsEntity> datas) {
        super(context, datas, R.layout.harvest_item);
        this.mContext = context;
    }

    @Override
    public void concert(final ViewHolder hooder, final QuerySendGoodsEntity querySendGoodsEntity, final int indexPos) {

        hooder.setTextInt(R.id.harvest_tv_title_2, R.string.hint_tv_send_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_3, R.string.hint_tv_sum_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_4, R.string.hint_tv_send_already_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_5, R.string.hint_tv_send_await_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_6, R.string.hint_tv_send_new_express_title);
        final EditText moneyEd = hooder.getView(R.id.harvest_ed_content_6);
        if (moneyEd.getTag() instanceof TextWatcher) {
            moneyEd.removeTextChangedListener((TextWatcher) moneyEd.getTag());
            moneyEd.clearFocus();
        }
        TextWatcher moneyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    querySendGoodsEntity.setNum("");
                } else if (StringUtils.isStrTrue(moneyEd.getText().toString().trim())) {
                    querySendGoodsEntity.setNum(moneyEd.getText().toString().trim());
                }
            }
        };
        moneyEd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index1 = indexPos;
                }
                return false;
            }
        });

        moneyEd.addTextChangedListener(moneyWatcher);
        moneyEd.setTag(moneyWatcher);

        hooder.getView(R.id.layout_harvest_3).setVisibility(View.GONE);
        hooder.getView(R.id.layout_tv_order_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCheckedkType != null) {
                    onCheckedkType.onBillsIDItemCilckListener(indexPos);
                }
            }
        });

        final CheckBox checkBox = hooder.getView(R.id.layout_check_order_1);
        checkBox.setChecked(querySendGoodsEntity.isCheck());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                querySendGoodsEntity.setCheck(checkBox.isChecked());
                if (checkBox.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
            }
        });

        hooder.setText(R.id.layout_tv_order_3, querySendGoodsEntity.getOrdertitleNumber() + "");
        hooder.setText(R.id.harvest_tv_title_1, querySendGoodsEntity.getGoodsName());
        hooder.setText(R.id.harvest_tv_content_2, querySendGoodsEntity.getBuyers_name());
        hooder.setText(R.id.harvest_tv_content_3, querySendGoodsEntity.getGoods_num() + " 件");
        hooder.setText(R.id.harvest_tv_content_4, querySendGoodsEntity.getSendgoods_num() + " 件");
        hooder.setText(R.id.harvest_tv_content_5, querySendGoodsEntity.getExe_sendgoods_num() + " 件");
        if (index1 != -1 && index1 == indexPos) {
            //强制加上焦点
            moneyEd.requestFocus();
            //设置光标显示到编辑框尾部
            moneyEd.setSelection(moneyEd.getText().length());
            //重置
            index1 = -1;
        }
    }

    public interface onCheckedkType {
        void onCheckedY(int pos);

        void onCheckedN(int pos);

        void onBillsIDItemCilckListener(int pos);
    }
}
