package com.hj.casps.adapter.ordermanageradapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.TypeAbstractViewHolder;
import com.hj.casps.base.ActivityBase;
import com.hj.casps.entity.appordermoney.QueryPayMoneyOrderForSettleEntity;
import com.hj.casps.ordermanager.OrderDetail;
import com.hj.casps.ordermanager.OrderShellModel;
import com.hj.casps.util.MathUtil;
import com.hj.casps.util.StringUtils;

import java.text.DecimalFormat;

/**
 * Created by Admin on 2017/4/19.
 * 订单详情
 */

public class ViewHolderOrderTwo extends TypeAbstractViewHolder<OrderShellModel> {
    TextView order_item_detail_name;
    EditText order_detail_item_price;
    EditText order_detail_item_number;
    TextView item_detail_order_price;
    private double minPrice = 0.0;//最小单价
    private double maxPrice = 0.0;//最大单价

    public ViewHolderOrderTwo(View itemView) {
        super(itemView);
        order_item_detail_name = (TextView) itemView.findViewById(R.id.order_item_detail_name);
        order_detail_item_price = (EditText) itemView.findViewById(R.id.order_detail_item_price);
        order_detail_item_number = (EditText) itemView.findViewById(R.id.order_detail_item_number);
        item_detail_order_price = (TextView) itemView.findViewById(R.id.item_detail_order_price);
    }

    @Override
    public void bindViewHolder(final OrderShellModel orderShellModel, int postion) {
        if (orderShellModel.getNum() > 0)
            order_detail_item_number.setText(orderShellModel.getNum() + "");
        order_item_detail_name.setText(orderShellModel.getName());
        try {
            minPrice = Double.parseDouble(orderShellModel.getPrice().split("-")[0]);
            maxPrice = Double.parseDouble(orderShellModel.getPrice().split("-")[1]);
            orderShellModel.setMinPrice(minPrice);
            orderShellModel.setMaxPrice(maxPrice);
        } catch (Exception e) {
            Log.e("show", "文字截取异常");
        }
        //控制小数点后两位
        ActivityBase.setPricePoint(order_detail_item_price);
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
                    orderShellModel.setFinalprice("");
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
                        DecimalFormat df = new DecimalFormat(".##");
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
                    orderShellModel.setFinalprice("");
//                                orderShellModel.setAllprice("");
//                                item_detail_order_price.setText("");
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
                if (TextUtils.isEmpty(s)) {
                    orderShellModel.setNum(0);
                    return;
                }
                if (!order_detail_item_number.getText().toString().isEmpty()) {
                    int oneNumb = Integer.valueOf(ActivityBase.getTvVaule(order_detail_item_number));
                    orderShellModel.setNum(oneNumb);
                    //判断当前有没有单价
                    if (StringUtils.isStrTrue(ActivityBase.getTvVaule(order_detail_item_price))) {
                        DecimalFormat df = new DecimalFormat("#.##");
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
                    orderShellModel.setNum(0);
//                                orderShellModel.setAllprice("");
//                                item_detail_order_price.setText("");
                }
            }
        });
    }

}
