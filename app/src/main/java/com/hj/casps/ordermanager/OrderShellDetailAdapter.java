package com.hj.casps.ordermanager;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBase;
import com.hj.casps.util.ToastUtils;

import java.util.List;

/**
 * Created by zy on 2017/4/27.
 * 下订单的适配器
 */

public class OrderShellDetailAdapter extends WZYBaseAdapter<OrderShellModel> {
    private int index1 = -1;
    private int index2 = -1;
    private Context context;

    public OrderShellDetailAdapter(List<OrderShellModel> data, Context context, int layoutRes) {
        super(data, context, layoutRes);

        this.context = context;
    }

    @Override
    public void bindData(ViewHolder holder, final OrderShellModel orderShellModel, final int indexPos) {

        final TextView order_item_detail_name = (TextView) holder.getView(R.id.order_item_detail_name);
        order_item_detail_name.setText(orderShellModel.getName());
        double minPrice = 0.0;
        double maxPrice = 0.0;
        try {
            String[] prices = orderShellModel.getPrice().split("-");
            if (prices.length > 0) {
                minPrice = Double.parseDouble(prices[0]);
                maxPrice = Double.parseDouble(prices[1]);
            }

        } catch (Exception e) {

        }
        final EditText order_detail_item_price = (EditText) holder.getView(R.id.order_detail_item_price);
        order_detail_item_price.setText(orderShellModel.getFinalprice());
        orderShellModel.setFinalprice(order_detail_item_price.getText().toString().isEmpty() ? "0.0" : order_detail_item_price.getText().toString());
//        TextView order_detail_item_size = (TextView) holder.getView(R.id.order_detail_item_size);
//        order_detail_item_size.setText(orderShellModel.getSize());
        final EditText order_detail_item_number = (EditText) holder.getView(R.id.order_detail_item_number);
        order_detail_item_number.setText(String.valueOf(orderShellModel.getNum()));
        final TextView item_detail_order_price = (TextView) holder.getView(R.id.item_detail_order_price);
        final double finalMinPrice = minPrice;
        final double finalMaxPrice = maxPrice;

        item_detail_order_price.setText(String.valueOf(Double.parseDouble(orderShellModel.getFinalprice()) * orderShellModel.getNum()));
        orderShellModel.setAllprice(String.valueOf(Double.parseDouble(orderShellModel.getFinalprice()) * orderShellModel.getNum()));

        ActivityBase.setPricePoint(order_detail_item_price);
        //价格计算及控制价格范围
        order_detail_item_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        if (finalMinPrice != 0.0 && finalMaxPrice != 0.0) {
                            if (Double.parseDouble(order_detail_item_price.getText().toString()) < finalMinPrice) {
                                ToastUtils.showToast(context, "单价不能小于" + finalMinPrice);
                            } else if (Double.parseDouble(order_detail_item_price.getText().toString()) > finalMaxPrice) {
                                ToastUtils.showToast(context, "单价不能大于" + finalMaxPrice);
                            } else {
                                orderShellModel.setFinalprice(order_detail_item_price.getText().toString());
                                item_detail_order_price.setText(String.valueOf(Double.parseDouble(orderShellModel.getFinalprice()) * orderShellModel.getNum()));
                                OrderDetail.orderDetail.refreshAllPrice();
                            }
                        }
                    } catch (Exception e) {
                        ToastUtils.showToast(context, "请输入正确的数字");
                    }
                    //让输入框失去焦点随便那个空间获取焦点都可以
                    order_item_detail_name.requestFocusFromTouch();
                }
            }
        });
        order_detail_item_price.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index1 = indexPos;
                }
                return false;
            }
        });

        //数量更新时价格计算
        order_detail_item_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        orderShellModel.setNum(Integer.parseInt(order_detail_item_number.getText().toString()));
                        item_detail_order_price.setText(String.valueOf(Double.parseDouble(orderShellModel.getFinalprice()) * orderShellModel.getNum()));
                        OrderDetail.orderDetail.refreshAllPrice();
                    } catch (Exception e) {
                    }
                    //让输入框失去焦点随便那个空间获取焦点都可以
                    order_item_detail_name.requestFocusFromTouch();
                }
            }
        });

        order_detail_item_number.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index2 = indexPos;
                }
                return false;
            }
        });
        if (index1 != -1 && index1 == indexPos) {
            //强制加上焦点
            order_detail_item_price.requestFocus();
            //设置光标显示到编辑框尾部
            order_detail_item_price.setSelection(order_detail_item_price.getText().length());
            //重置
            index1 = -1;
        }
        if (index2 != -1 && index2 == indexPos) {
            //强制加上焦点
            order_detail_item_number.requestFocus();
            //设置光标显示到编辑框尾部
            order_detail_item_number.setSelection(order_detail_item_number.getText().length());
            //重置
            index2 = -1;
        }
    }
}
