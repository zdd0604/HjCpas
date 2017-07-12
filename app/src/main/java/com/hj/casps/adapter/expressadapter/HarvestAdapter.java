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
import com.hj.casps.entity.appordergoods.QueryGetGoodsEntity;
import com.hj.casps.util.StringUtils;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 * 收款列表adapter
 */

public class HarvestAdapter extends CommonAdapter<QueryGetGoodsEntity> {
    private int index1 = -1;
    public static onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(HarvestAdapter.onCheckedkType onCheckedkType) {
        HarvestAdapter.onCheckedkType = onCheckedkType;
    }

    public HarvestAdapter(Context context, List<QueryGetGoodsEntity> datas) {
        super(context, datas, R.layout.harvest_item);
        this.mContext = context;
    }

    @Override
    public void concert(final ViewHolder hooder, final QueryGetGoodsEntity queryGetGoodsEntity, final int indexPos) {
        final EditText harvest_ed_content_6 = hooder.getView(R.id.harvest_ed_content_6);
        final CheckBox layout_check_order_1 = hooder.getView(R.id.layout_check_order_1);
        layout_check_order_1.setChecked(queryGetGoodsEntity.isCheck());
        hooder.setTextInt(R.id.harvest_tv_title_2, R.string.hint_tv_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_3, R.string.hint_tv_sum_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_4, R.string.hint_tv_already_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_5, R.string.hint_tv_await_deliver_title);
        hooder.setTextInt(R.id.harvest_tv_title_6, R.string.hint_tv_new_express_title);
        hooder.setTextInt(R.id.harvest_tv_title_7, R.string.hint_tv_express_addr_title);

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
                    queryGetGoodsEntity.setNum("");
                } else if (StringUtils.isStrTrue(harvest_ed_content_6.getText().toString().trim())) {
                    queryGetGoodsEntity.setNum(harvest_ed_content_6.getText().toString().trim());
                }
            }
        };
        harvest_ed_content_6.setOnTouchListener(new View.OnTouchListener() {
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

        hooder.setText(R.id.layout_tv_order_3, queryGetGoodsEntity.getOrdertitleNumber() + "");
        hooder.setText(R.id.harvest_tv_title_1, queryGetGoodsEntity.getGood_sname());
        hooder.setText(R.id.harvest_tv_content_2, queryGetGoodsEntity.getSellers_name());
        hooder.setText(R.id.harvest_tv_content_3, (queryGetGoodsEntity.getGoods_num()) + " 件");
        hooder.setText(R.id.harvest_tv_content_4, queryGetGoodsEntity.getGetgoods_num() + " 件");
        hooder.setText(R.id.harvest_tv_content_5, queryGetGoodsEntity.getExe_getgoods_num() + " 件");
        if (StringUtils.isStrTrue(queryGetGoodsEntity.getNum()))
            hooder.setEdiTextView(R.id.harvest_ed_content_6, queryGetGoodsEntity.getNum() + " 件");

        final TextView harvest_tv_content_7 = hooder.getView(R.id.harvest_tv_content_7);
        if (queryGetGoodsEntity.getAddressName() != null) {
            hooder.setText(R.id.harvest_tv_content_7, queryGetGoodsEntity.getAddressName());
        } else if (queryGetGoodsEntity.getAddress_list().size() > 0) {
            hooder.setText(R.id.harvest_tv_content_7, queryGetGoodsEntity.getAddress_list().get(0).getAddress());
            queryGetGoodsEntity.setAddressName(queryGetGoodsEntity.getAddress_list().get(0).getAddress());
        }

        if (queryGetGoodsEntity.getAddressId() == null &&
                queryGetGoodsEntity.getAddress_list().size() > 0) {
            queryGetGoodsEntity.setAddressId(queryGetGoodsEntity.getAddress_list().get(0).getId());
        }

        hooder.getView(R.id.harvest_tv_content_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queryGetGoodsEntity.getAddressId() != null &&
                        queryGetGoodsEntity.getAddress_list().size() > 0)
                    onCheckedkType.onAddressOnClickListener(harvest_tv_content_7, indexPos);
            }
        });
        hooder.getView(R.id.layout_tv_order_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedkType.onBillsIDItemCilckListener(indexPos);
            }
        });

        layout_check_order_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout_check_order_1.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
                queryGetGoodsEntity.setCheck(layout_check_order_1.isChecked());
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

    /**
     * 列表中的点击事件
     */
    public interface onCheckedkType {
        //单选框选中事件
        void onCheckedY(int pos);

        //单选框取消选中事件
        void onCheckedN(int pos);

        //订单ID点击事件
        void onBillsIDItemCilckListener(int pos);

        //地址列表选择事件
        void onAddressOnClickListener(TextView view, int pos);
    }
}
