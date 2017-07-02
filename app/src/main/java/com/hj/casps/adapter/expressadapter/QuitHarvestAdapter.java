package com.hj.casps.adapter.expressadapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.appordergoods.QueryGetReturnGoodsGain;
import com.hj.casps.util.StringUtils;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 */

public class QuitHarvestAdapter extends CommonAdapter<QueryGetReturnGoodsGain> {
    private int index1 = -1;
    public static QuitHarvestAdapter.onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(QuitHarvestAdapter.onCheckedkType onCheckedkType) {
        QuitHarvestAdapter.onCheckedkType = onCheckedkType;
    }

    public QuitHarvestAdapter(Context context, List<QueryGetReturnGoodsGain> datas) {
        super(context, datas, R.layout.harvest_item);
        this.mContext = context;
    }

    @Override
    public void concert(final ViewHolder hooder, final QueryGetReturnGoodsGain queryGetReturnGoodsGain, final int indexPos) {

        hooder.getView(R.id.layout_harvest_3).setVisibility(View.VISIBLE);
        hooder.getView(R.id.harvest_tv_title_5).setVisibility(View.VISIBLE);

        final EditText harvest_ed_content_6 = hooder.getView(R.id.harvest_ed_content_6);
        final CheckBox layout_check_order_1 = hooder.getView(R.id.layout_check_order_1);
        layout_check_order_1.setChecked(queryGetReturnGoodsGain.isCheck());
        hooder.getView(R.id.layout_tv_order_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedkType.onBillsIDItemCilckListener(indexPos);
            }
        });

        layout_check_order_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryGetReturnGoodsGain.setCheck(layout_check_order_1.isChecked());
                if (layout_check_order_1.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
            }
        });
        hooder.setTextInt(R.id.harvest_tv_title_2, R.string.hint_tv_quit_harvest_express_name_title);
        hooder.setTextInt(R.id.harvest_tv_title_3, R.string.hint_tv_quit_harvest_express_title_1);
        hooder.setTextInt(R.id.harvest_tv_title_4, R.string.hint_tv_quit_harvest_express_title_2);
        hooder.setTextInt(R.id.harvest_tv_title_5, R.string.hint_tv_quit_harvest_express_title_3);
        hooder.setTextInt(R.id.harvest_tv_title_6, R.string.hint_tv_quit_express_title);
        hooder.setTextInt(R.id.harvest_tv_title_7, R.string.hint_tv_get_quit_express_title);

        if (harvest_ed_content_6.getTag() instanceof TextWatcher) {
            harvest_ed_content_6.removeTextChangedListener((TextWatcher) harvest_ed_content_6.getTag());
            harvest_ed_content_6.clearFocus();
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
                    queryGetReturnGoodsGain.setNum("");
                } else if (StringUtils.isStrTrue(harvest_ed_content_6.getText().toString().trim())) {
                    queryGetReturnGoodsGain.setNum(harvest_ed_content_6.getText().toString().trim());
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

        hooder.setText(R.id.layout_tv_order_3, queryGetReturnGoodsGain.getOrdertitleNumber() + "");
        hooder.setText(R.id.harvest_tv_title_1, queryGetReturnGoodsGain.getGoodsName());
        hooder.setText(R.id.harvest_tv_content_2, queryGetReturnGoodsGain.getBuyers_name());
        hooder.setText(R.id.harvest_tv_content_3, queryGetReturnGoodsGain.getGoods_num() + "");
        hooder.setText(R.id.harvest_tv_content_4, queryGetReturnGoodsGain.getGetreturngoodsNum() + "");
        hooder.setText(R.id.harvest_tv_content_5, queryGetReturnGoodsGain.getExeGetreturngoodsNum() + "");
        if (StringUtils.isStrTrue(queryGetReturnGoodsGain.getNum()))
            hooder.setEdiTextView(R.id.harvest_ed_content_6, queryGetReturnGoodsGain.getNum() + "");

        final TextView harvest_tv_content_7 = hooder.getView(R.id.harvest_tv_content_7);
        if (queryGetReturnGoodsGain.getAddressName() != null) {
            hooder.setText(R.id.harvest_tv_content_7, queryGetReturnGoodsGain.getAddressName());
        } else if (queryGetReturnGoodsGain.getAddress_list().size() > 0) {
            hooder.setText(R.id.harvest_tv_content_7, queryGetReturnGoodsGain.getAddress_list().get(0).getAddress());
            queryGetReturnGoodsGain.setAddressName(queryGetReturnGoodsGain.getAddress_list().get(0).getAddress());
        }
        if (queryGetReturnGoodsGain.getAddressId() == null && queryGetReturnGoodsGain.getAddress_list().size() > 0) {
            queryGetReturnGoodsGain.setAddressId(queryGetReturnGoodsGain.getAddress_list().get(0).getId());
        }
        hooder.getView(R.id.harvest_tv_content_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queryGetReturnGoodsGain.getAddress_list() != null &&
                        queryGetReturnGoodsGain.getAddress_list().size() > 0)
                    onCheckedkType.onAddressOnClickListener(harvest_tv_content_7, indexPos);
            }
        });

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

        void onAddressOnClickListener(TextView view, int pos);
    }
}