package com.hj.casps.ordermanager;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBase;
import com.hj.casps.util.StringUtils;
import com.hj.casps.util.ToastUtils;

import java.util.List;

/**
 * Created by zy on 2017/4/27.
 * 下订单的适配器
 */

public class OrderShellDetailAdapter extends WZYBaseAdapter<OrderShellModel> {
    private Context context;
    double minPrice = 0.0;//最小单价
    double maxPrice = 0.0;//最大单价
    public static upDataPrice upDataPrice;

    public static OrderShellDetailAdapter.upDataPrice getUpDataPrice() {
        return upDataPrice;
    }

    public static void setUpDataPrice(OrderShellDetailAdapter.upDataPrice upDataPrice) {
        OrderShellDetailAdapter.upDataPrice = upDataPrice;
    }

    public OrderShellDetailAdapter(List<OrderShellModel> data, Context context, int layoutRes) {
        super(data, context, layoutRes);

        this.context = context;
    }

    @Override
    public void bindData(final ViewHolder holder, final OrderShellModel orderShellModel, final int indexPos) {

        final TextView order_item_detail_name = (TextView) holder.getView(R.id.order_item_detail_name);
        order_item_detail_name.setText(orderShellModel.getName());

        try {
            String[] prices = orderShellModel.getPrice().split("-");
            if (prices.length > 0) {
                minPrice = Double.parseDouble(prices[0]);
                maxPrice = Double.parseDouble(prices[1]);
            }
        } catch (Exception e) {
            Log.e("show", "文字截取异常");
        }
        //单价
        final EditText order_detail_item_price = (EditText) holder.getView(R.id.order_detail_item_price);
        order_detail_item_price.setText(orderShellModel.getFinalprice());
        orderShellModel.setFinalprice(order_detail_item_price.getText().toString().isEmpty() ? "0.0" : order_detail_item_price.getText().toString());
        //数量
        final EditText order_detail_item_number = (EditText) holder.getView(R.id.order_detail_item_number);
        order_detail_item_number.setText(String.valueOf(orderShellModel.getNum()));
        //总价
        final TextView item_detail_order_price = (TextView) holder.getView(R.id.item_detail_order_price);
        item_detail_order_price.setText(String.valueOf(Double.parseDouble(orderShellModel.getFinalprice()) * orderShellModel.getNum()));

        orderShellModel.setAllprice(String.valueOf(Double.parseDouble(orderShellModel.getFinalprice()) * orderShellModel.getNum()));
        //控制小数点后两位
        ActivityBase.setPricePoint(order_detail_item_price);

        order_detail_item_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //文本变化前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //文本变化中
            }

            @Override
            public void afterTextChanged(Editable s) {
                //文本变化后
                if (minPrice != 0.0 && maxPrice != 0.0) {
                    if (StringUtils.isStrTrue(ActivityBase.getTvVaule(order_detail_item_price)))
                        if (Double.parseDouble(ActivityBase.getTvVaule(order_detail_item_price)) < minPrice) {
                            ToastUtils.showToast(context, "单价不能小于" + minPrice);
                        } else if (Double.parseDouble(ActivityBase.getTvVaule(order_detail_item_price)) > maxPrice) {
                            ToastUtils.showToast(context, "单价不能大于" + maxPrice);
                        } else {
                            orderShellModel.setFinalprice(ActivityBase.getTvVaule(order_detail_item_price));
                            item_detail_order_price.setText(String.valueOf(Double.parseDouble(orderShellModel.getFinalprice()) * orderShellModel.getNum()));
                            upDataPrice.onRefresh();
                        }
                }
            }
        });

        order_detail_item_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isStrTrue(ActivityBase.getTvVaule(order_detail_item_number))) {
                    orderShellModel.setNum(Integer.parseInt(ActivityBase.getTvVaule(order_detail_item_number)));
                    item_detail_order_price.setText(String.valueOf(Double.parseDouble(orderShellModel.getFinalprice()) * orderShellModel.getNum()));
                    upDataPrice.onRefresh();
                }
            }
        });

    }

    /**
     * 刷新数据
     */
    public interface upDataPrice {
        void onRefresh();
    }
}
