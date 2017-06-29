package com.hj.casps.ordermanager;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBase;
import com.hj.casps.util.MathUtil;
import com.hj.casps.util.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zy on 2017/4/27.
 * 下订单的适配器
 */

public class OrderShellDetailAdapter extends WZYBaseAdapter<OrderShellModel> {
    private Context context;
    private double minPrice = 0.0;//最小单价
    private double maxPrice = 0.0;//最大单价

    public OrderShellDetailAdapter(List<OrderShellModel> data, Context context, int layoutRes) {
        super(data, context, layoutRes);
        this.context = context;
    }

    @Override
    public void bindData(ViewHolder holder, final OrderShellModel orderShellModel, final int indexPos) {
        Log.e("show", "---------" + orderShellModel.toString());
        Log.d("indexPos", indexPos + "");
        try {
            minPrice = Double.parseDouble(orderShellModel.getPrice().split("-")[0]);
            maxPrice = Double.parseDouble(orderShellModel.getPrice().split("-")[1]);
            orderShellModel.setMinPrice(minPrice);
            orderShellModel.setMaxPrice(maxPrice);
        } catch (Exception e) {
            Log.e("show", "文字截取异常");
        }
        TextView order_item_detail_name = (TextView) holder.getView(R.id.order_item_detail_name);
        order_item_detail_name.setText(orderShellModel.getName());
        final EditText order_detail_item_price = (EditText) holder.getView(R.id.order_detail_item_price);
        final EditText order_detail_item_number = (EditText) holder.getView(R.id.order_detail_item_number);
        order_detail_item_number.setText(String.valueOf(orderShellModel.getNum()));
        final TextView item_detail_order_price = (TextView) holder.getView(R.id.item_detail_order_price);
        final DecimalFormat df = new DecimalFormat(".##");
        //控制小数点后两位
        ActivityBase.setPricePoint(order_detail_item_price);
        order_detail_item_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //单价判断
                    order_detail_item_price.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (TextUtils.isEmpty(s)) {
                                return;
                            }
                            /**
                             * 1、判断输入框是否有内容
                             * 2、获取当前最大最小值
                             * 3、进行单价判断，不满足条件的进行提示用户
                             * 4、实体类设置单价、数量、总价、
                             */
                            if (!order_detail_item_price.getText().toString().isEmpty()) {
                                double onePrice = Double.valueOf(order_detail_item_price.getText().toString());
                                if (onePrice < minPrice) {
                                    //单价小于报价范围
                                    order_detail_item_price.setError("单价不能小于：" + minPrice);
                                } else if (onePrice > maxPrice) {
                                    //单价大于报价范围
                                    order_detail_item_price.setError("单价不能大于：" + maxPrice);
                                } else {
                                    //单价属于报价范围
                                    orderShellModel.setFinalprice(String.valueOf(onePrice));
                                    //判断当前有没有数量
//                        DecimalFormat df = new DecimalFormat(".##");
                                    //判断数量是否为空算总价
                                    if (StringUtils.isStrTrue(order_detail_item_number.getText().toString())) {
                                        String allPrice = df.format(
                                                MathUtil.mul(
                                                        Double.valueOf(ActivityBase.getTvVaule(order_detail_item_price)),
                                                        Double.valueOf(ActivityBase.getTvVaule(order_detail_item_number))
                                                ));
                                        item_detail_order_price.setText(allPrice);
                                        orderShellModel.setAllprice(allPrice);
                                        OrderDetail.orderDetail.refreshAllPrice();
                                    }

                                }
                            } else {
                                order_detail_item_price.setText("");
                            }

                        }
                    });
                    try {
                        String[] prices = orderShellModel.getPrice().split("-");
                        if (prices.length > 0) {
                            minPrice = Double.parseDouble(prices[0]);
                            maxPrice = Double.parseDouble(prices[1]);
                            orderShellModel.setMinPrice(minPrice);
                            orderShellModel.setMaxPrice(maxPrice);
                        }
                    } catch (Exception e) {
                        Log.e("show", "文字截取异常");
                    }
                } else {
                    order_detail_item_price.clearFocus();
                }

            }
        });
        order_detail_item_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    order_detail_item_number.clearFocus();
                } else {
                    //数量
                    order_detail_item_number.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (TextUtils.isEmpty(s)) {
                                return;
                            }
                            if (!order_detail_item_number.getText().toString().isEmpty()) {
                                int oneNumb = Integer.valueOf(ActivityBase.getTvVaule(order_detail_item_number));
                                orderShellModel.setNum(oneNumb);
                                //判断当前有没有单价
                                if (StringUtils.isStrTrue(ActivityBase.getTvVaule(order_detail_item_price))) {
                                    DecimalFormat df = new DecimalFormat(".##");
                                    String allPrice = df.format(
                                            MathUtil.mul(Double.valueOf(ActivityBase.getTvVaule(order_detail_item_price)),
                                                    Double.valueOf(ActivityBase.getTvVaule(order_detail_item_number)
                                                    )));
                                    item_detail_order_price.setText(allPrice);
                                    orderShellModel.setAllprice(allPrice);
                                    OrderDetail.orderDetail.refreshAllPrice();
                                }
                            } else {
                                order_detail_item_number.setText("0");
                            }
                        }
                    });
                }
            }
        });


    }

}
