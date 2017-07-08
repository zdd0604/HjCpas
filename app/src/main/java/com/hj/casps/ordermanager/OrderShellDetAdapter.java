package com.hj.casps.ordermanager;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBase;
import com.hj.casps.util.MathUtil;
import com.hj.casps.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2017/4/27.
 * 下订单的适配器
 */

public class OrderShellDetAdapter extends BaseAdapter {
    private Context context;
    private List<OrderShellModel> dataList;
    private LayoutInflater layoutInflater;
    private double minPrice = 0.0;//最小单价
    private double maxPrice = 0.0;//最大单价


    public OrderShellDetAdapter(List<OrderShellModel> dataList, Context context) {
        this.context = context;
        if (dataList == null) {
            //如果传递进来的数据源是null,我们就实例化一个size为0的数据源
            this.dataList = new ArrayList<>();
        } else {
            this.dataList = dataList;
        }
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateRes(List<OrderShellModel> data) {
        if (data != null && data.size() > 0) {
            this.dataList.clear();
            this.dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<OrderShellModel> getDataList() {
        return dataList;
    }


    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = layoutInflater.inflate(R.layout.item_order_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.refreshData(dataList.get(position));
        return convertView;
    }

    private class ViewHolder {
        EditText order_detail_item_price;
        EditText order_detail_item_number;
        OrderShellModel mInfo;
        TextView order_item_detail_name;
        TextView item_detail_order_price;

        ViewHolder(View parent) {
            initViewHolder(parent);
        }

        public void initViewHolder(View parent) {


            order_item_detail_name = (TextView) parent.findViewById(R.id.order_item_detail_name);
            item_detail_order_price = (TextView) parent.findViewById(R.id.item_detail_order_price);
            order_detail_item_price = (EditText) parent.findViewById(R.id.order_detail_item_price);
            order_detail_item_number = (EditText) parent.findViewById(R.id.order_detail_item_number);
            final DecimalFormat df = new DecimalFormat("#.##");

            order_detail_item_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        try {
                            String[] prices = mInfo.getPrice().split("-");
                            if (prices.length > 0) {
                                minPrice = Double.parseDouble(prices[0]);
                                maxPrice = Double.parseDouble(prices[1]);
                                mInfo.setMinPrice(minPrice);
                                mInfo.setMaxPrice(maxPrice);
                            }
                        } catch (Exception e) {
                            Log.e("show", "文字截取异常");
                        }
                    }
                }
            });


            order_detail_item_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    try {
                        minPrice = Double.parseDouble(mInfo.getPrice().split("-")[0]);
                        maxPrice = Double.parseDouble(mInfo.getPrice().split("-")[1]);

                        mInfo.setMinPrice(minPrice);
                        mInfo.setMaxPrice(maxPrice);
                    } catch (Exception e) {
                        Log.e("show", "文字截取异常");
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        mInfo.setFinalprice("");
                        return;
                    }
                    /**
                     * 1、判断输入框是否有内容
                     * 2、获取当前最大最小值
                     * 3、进行单价判断，不满足条件的进行提示用户
                     * 4、实体类设置单价、数量、总价、
                     */
                    if (!s.toString().isEmpty()) {
                        double onePrice = Double.valueOf(s.toString());
                        if (onePrice < minPrice) {
                            //单价小于报价范围
                            order_detail_item_price.setError("单价不能小于：" + minPrice);
                        } else if (onePrice > maxPrice) {
                            //单价大于报价范围
                            order_detail_item_price.setError("单价不能大于：" + maxPrice);
                        } else {
                            //单价属于报价范围
                            mInfo.setFinalprice(String.valueOf(onePrice));
                            //判断当前有没有数量
//                        DecimalFormat df = new DecimalFormat(".##");
                            //判断数量是否为空算总价
                            if (!TextUtils.isEmpty(String.valueOf(mInfo.getNum()))) {
                                String allPrice = df.format(
                                        MathUtil.mul(
                                                Double.valueOf(s.toString()),
                                                Double.valueOf(String.valueOf(mInfo.getNum()))
                                        ));
                                item_detail_order_price.setText(allPrice);
                                mInfo.setAllprice(allPrice);
                                OrderDetail.orderDetail.refreshAllPrice();
                            }

                        }
                    } else {
                        order_detail_item_price.setText("");
                        mInfo.setFinalprice("");
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
                        mInfo.setNum(0);
                        return;
                    }
                    if (!s.toString().isEmpty()) {
                        int oneNumb = Integer.valueOf(s.toString());
                        mInfo.setNum(oneNumb);
                        //判断当前有没有单价
                        if (!TextUtils.isEmpty(mInfo.getFinalprice())) {
                            String allPrice = df.format(
                                    MathUtil.mul(Double.valueOf(mInfo.getFinalprice()),
                                            Double.valueOf(s.toString()
                                            )));
                            item_detail_order_price.setText(allPrice);
                            mInfo.setAllprice(allPrice);
                            OrderDetail.orderDetail.refreshAllPrice();
                        }
                    } else {
                        order_detail_item_number.setText("0");
                        mInfo.setNum(0);
//                                orderShellModel.setAllprice("");
//                                item_detail_order_price.setText("");
                    }
                }
            });

        }

        public void refreshData(OrderShellModel info) {
            mInfo = info;
            order_detail_item_price.setText(info.getFinalprice());
            order_detail_item_number.setText(String.valueOf(info.getNum()));
            order_item_detail_name.setText(mInfo.getName());
            item_detail_order_price.setText(mInfo.getAllprice());

            //控制小数点后两位
            ActivityBase.setPricePoint(order_detail_item_price);
        }


    }


}
