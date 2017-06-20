package com.hj.casps.adapter.expressadapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.appordergoods.QueryReturnGoodsEntity;
import com.hj.casps.util.StringUtils;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 * 退货列表
 */

public class QuitAdapter extends CommonAdapter<QueryReturnGoodsEntity> {
    private int index1 = -1;
    public static onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(QuitAdapter.onCheckedkType onCheckedkType) {
        QuitAdapter.onCheckedkType = onCheckedkType;
    }

    public QuitAdapter(Context context, List<QueryReturnGoodsEntity> datas) {
        super(context, datas, R.layout.harvest_item);
        this.mContext = context;
    }

    @Override
    public void concert(final ViewHolder hooder, final QueryReturnGoodsEntity queryReturnGoodsEntity, final int indexPos) {

        hooder.getView(R.id.layout_harvest_3).setVisibility(View.GONE);
        hooder.getView(R.id.harvest_left_layout_5).setVisibility(View.GONE);

        final EditText harvest_ed_content_6 = hooder.getView(R.id.harvest_ed_content_6);
        final CheckBox layout_check_order_1 = hooder.getView(R.id.layout_check_order_1);
        layout_check_order_1.setChecked(queryReturnGoodsEntity.isCheck());
        hooder.getView(R.id.layout_tv_order_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedkType.onBillsIDItemCilckListener(indexPos);
            }
        });

        layout_check_order_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryReturnGoodsEntity.setCheck(layout_check_order_1.isChecked());
                if (layout_check_order_1.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
            }
        });
        hooder.setTextInt(R.id.harvest_tv_title_2, R.string.hint_tv_quit_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_3, R.string.hint_tv_quit_already_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_4, R.string.hint_tv_quit_await_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_6, R.string.hint_tv_quit_new_express_title);
        if (harvest_ed_content_6.getTag() instanceof TextWatcher) {
            harvest_ed_content_6.removeTextChangedListener((TextWatcher) harvest_ed_content_6.getTag());
            harvest_ed_content_6.clearFocus();
        }
        TextWatcher moneyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.v("show1", "" + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    queryReturnGoodsEntity.setNum("");
                } else if (StringUtils.isStrTrue(harvest_ed_content_6.getText().toString().trim())) {
                    queryReturnGoodsEntity.setNum(harvest_ed_content_6.getText().toString().trim());
                }
            }
        };
        harvest_ed_content_6.setOnTouchListener(new View.OnTouchListener()

        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index1 = indexPos;
                }
                return false;
            }
        });
        harvest_ed_content_6.addTextChangedListener(moneyWatcher);
        harvest_ed_content_6.setTag(moneyWatcher);

        hooder.setText(R.id.layout_tv_order_3, queryReturnGoodsEntity.getOrdertitleNumber() + "");
        hooder.setText(R.id.harvest_tv_title_1, queryReturnGoodsEntity.getGoodsName());
        hooder.setText(R.id.harvest_tv_content_2, queryReturnGoodsEntity.getSellers_name());
        hooder.setText(R.id.harvest_tv_content_3, queryReturnGoodsEntity.getGoods_num() + "");
        hooder.setText(R.id.harvest_tv_content_4, queryReturnGoodsEntity.getExe_returngoods_num() + "");
        if (StringUtils.isStrTrue(queryReturnGoodsEntity.getNum()))
            hooder.setEdiTextView(R.id.harvest_ed_content_6, queryReturnGoodsEntity.getNum() + "");

        if (index1 != -1 && index1 == indexPos) {
            //强制加上焦点
            harvest_ed_content_6.requestFocus();
            //设置光标显示到编辑框尾部
            harvest_ed_content_6.setSelection(harvest_ed_content_6.getText().length());
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
