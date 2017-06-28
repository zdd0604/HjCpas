package com.hj.casps.adapter.overdealadapter;

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
import com.hj.casps.base.ActivityBase;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appsettle.QueryPendingSttleGain;
import com.hj.casps.util.StringUtils;

import java.util.List;

/**
 * Created by Admin on 2017/4/18.
 * 待审批结款列表
 */

public class CheckWaitAdapter extends CommonAdapter<QueryPendingSttleGain> {
    private int index1 = -1;
    private int index2 = -1;
    public static onCheckedkType onCheckedkType;

    public static void setOnCheckedkType(CheckWaitAdapter.onCheckedkType onCheckedkType) {
        CheckWaitAdapter.onCheckedkType = onCheckedkType;
    }

    public CheckWaitAdapter(Context context, List<QueryPendingSttleGain> datas) {
        super(context, datas, R.layout.layout_check_wait_bills_item);
        this.mContext = context;
    }

    @Override
    public void concert(ViewHolder hooder, final QueryPendingSttleGain queryPendingSttleGain, final int indexPos) {
        String name;
        final CheckBox bills_checkBox = hooder.getView(R.id.layout_check_order_1);
        bills_checkBox.setChecked(queryPendingSttleGain.isCheck());
        bills_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryPendingSttleGain.setCheck(bills_checkBox.isChecked());
                if (bills_checkBox.isChecked()) {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedY(indexPos);
                } else {
                    if (onCheckedkType != null)
                        onCheckedkType.onCheckedN(indexPos);
                }
            }
        });

        final EditText layout_check_ed_content_3 = hooder.getView(R.id.layout_check_ed_content_3);
        layout_check_ed_content_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(layout_check_ed_content_3);
            }
        });
        if (layout_check_ed_content_3.getTag() instanceof TextWatcher) {
            layout_check_ed_content_3.removeTextChangedListener((TextWatcher) layout_check_ed_content_3.getTag());
        }
        TextWatcher watcher3 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    queryPendingSttleGain.setMyTime("");
                } else if (StringUtils.isStrTrue(layout_check_ed_content_3.getText().toString().trim())) {
                    queryPendingSttleGain.setMyTime(layout_check_ed_content_3.getText().toString().trim());
                }
            }
        };
        layout_check_ed_content_3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index1 = indexPos;
                }
                return false;
            }
        });
        layout_check_ed_content_3.addTextChangedListener(watcher3);
        layout_check_ed_content_3.setTag(watcher3);


        final EditText layout_check_ed_content_6 = hooder.getView(R.id.layout_check_ed_content_6);
        //控制小数点后两位
        ActivityBase.setPricePoint(layout_check_ed_content_6);
        if (layout_check_ed_content_6.getTag() instanceof TextWatcher) {
            layout_check_ed_content_6.removeTextChangedListener((TextWatcher) layout_check_ed_content_6.getTag());
            layout_check_ed_content_6.clearFocus();
        }
        TextWatcher watcher6 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    queryPendingSttleGain.setMyMoney("");
                } else if (StringUtils.isStrTrue(layout_check_ed_content_6.getText().toString().trim())) {
                    queryPendingSttleGain.setMyMoney(layout_check_ed_content_6.getText().toString().trim());
                }
            }
        };
        layout_check_ed_content_6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index2 = indexPos + 1;
                }
                return false;
            }
        });
        layout_check_ed_content_6.addTextChangedListener(watcher6);
        layout_check_ed_content_6.setTag(watcher6);

        TextView bills_title2 = hooder.getView(R.id.layout_tv_order_2);
        bills_title2.setText(R.string.hint_tv_over_bills_id_title);
        TextView bills_title3 = hooder.getView(R.id.layout_tv_order_3);
        bills_title3.setText(queryPendingSttleGain.getSettleCode() + "");
        bills_title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedkType.onBillsIDItemCilckListener(indexPos);
            }
        });

//        name = queryPendingSttleGain.getMmbpayId().equals(Constant.publicArg.getSys_member())
//                ? queryPendingSttleGain.getMmbpayName() : queryPendingSttleGain.getMmbgetName();
        name = queryPendingSttleGain.getMmbpayId().equals(Constant.publicArg.getSys_member())
                ? queryPendingSttleGain.getMmbgetName() : queryPendingSttleGain.getMmbpayName();
        hooder.setText(R.id.layout_check_tv_content_1, name);
        hooder.setText(R.id.layout_check_tv_content_2, Constant.stmpToDate(queryPendingSttleGain.getCtrTime()));
        hooder.setText(R.id.layout_check_ed_content_3, queryPendingSttleGain.getMyTime());
        hooder.setText(R.id.layout_check_tv_content_4, queryPendingSttleGain.getSettleMoney() + "");
        hooder.setText(R.id.layout_check_tv_content_5, queryPendingSttleGain.getCtrMoney() + "");
        if (queryPendingSttleGain.getMyMoney() != null)
            hooder.setEdiTextView(R.id.layout_check_ed_content_6, queryPendingSttleGain.getMyMoney());

        //清除
        if (index1 != -1 && index1 == indexPos) {
            //重置
            index1 = -1;
        }
        if (index2 != -1 && index2 == indexPos + 1) {
            //强制加上焦点
            layout_check_ed_content_6.requestFocus();
            //设置光标显示到编辑框尾部
            layout_check_ed_content_6.setSelection(layout_check_ed_content_6.getText().length());
            //重置
            index2 = -1;
        }
    }

    public interface onCheckedkType {
        void onCheckedY(int pos);

        void onCheckedN(int pos);

        void onBillsIDItemCilckListener(int pos);
    }

}
