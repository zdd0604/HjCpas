package com.hj.casps.ordermanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.apporder.BuyCartBack;
import com.hj.casps.entity.apporder.BuyCartPost;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC;
import static com.hj.casps.common.Constant.publicArg;

/**
 * Created by zy on 2017/4/27.
 * 下订单的适配器
 */

public class OrderShellAdapter extends WZYBaseAdapter<OrderShellModel> {
    private int state;
    private Context context;
    private Activity activity;

    public OrderShellAdapter(List<OrderShellModel> data, Context context, int layoutRes, int state, Activity activity) {
        super(data, context, layoutRes);
        this.state = state;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void bindData(ViewHolder holder, final OrderShellModel orderShellModel, final int indexPos) {

        CheckBox shell_ck_buy = (CheckBox) holder.getView(R.id.shell_ck_buy);
        shell_ck_buy.setChecked(orderShellModel.isChecked());
        shell_ck_buy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                orderShellModel.setChecked(b);

            }
        });
        TextView buy_shell_name = (TextView) holder.getView(R.id.buy_shell_name);
        buy_shell_name.setText(orderShellModel.getName());
        TextView buy_shell_price = (TextView) holder.getView(R.id.buy_shell_price);
        buy_shell_price.setText(orderShellModel.getPrice());
        final EditText buy_shell_num = (EditText) holder.getView(R.id.buy_shell_num);
        buy_shell_num.setText(String.valueOf(orderShellModel.getNum()));
        buy_shell_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    orderShellModel.setNum(Integer.parseInt(buy_shell_num.getText().toString().trim()));

                } catch (Exception e) {
                }
            }
        });
        TextView delete_buy_shell = (TextView) holder.getView(R.id.delete_buy_shell);
        RelativeLayout buy_shell_item_relative1 = (RelativeLayout) holder.getView(R.id.buy_shell_item_relative1);
        if (state == 0) {
            delete_buy_shell.setVisibility(View.GONE);
            buy_shell_item_relative1.setVisibility(View.VISIBLE);
        } else {
            delete_buy_shell.setVisibility(View.VISIBLE);
            buy_shell_item_relative1.setVisibility(View.GONE);
        }
        delete_buy_shell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //网络请求去删除
                delete(1, orderShellModel.getNo(), orderShellModel);
            }


        });

        final TextView view_status_product = (TextView) holder.getView(R.id.view_status_product);
        if (orderShellModel.isStatus()) {
            view_status_product.setVisibility(View.VISIBLE);
        } else {
            view_status_product.setVisibility(View.GONE);

        }
        FancyButton status_buy_shell_product = (FancyButton) holder.getView(R.id.status_buy_shell_product);
        status_buy_shell_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                orderShellModel.setStatus(true);
//                view_status_product.setVisibility(View.VISIBLE);

                Intent intent = new Intent(context, BuyShell.class);
                intent.putExtra("choose", true);
                intent.putExtra("no", indexPos);
                intent.putExtra("index", orderShellModel.getNo());
                intent.putExtra("categoryId", orderShellModel.getCategoryId());
                activity.startActivityForResult(intent, 11);
            }
        });
        FancyButton buy_shell_delete = (FancyButton) holder.getView(R.id.buy_shell_delete);
        buy_shell_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(0, orderShellModel.getNo(), orderShellModel);
//                orderShellModel.setStatus(false);
//                view_status_product.setVisibility(View.GONE);

            }
        });

    }

    //删除购物车数据
    private void delete(int type, int no, final OrderShellModel orderShellModel) {

        BuyCartPost post = new BuyCartPost(publicArg.getSys_token(),
                Constant.getUUID(),
                SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                String.valueOf(type),
                String.valueOf(no));
        final Gson mGson = new Gson();
        OkGo.post(Constant.DeleteQuoteSHPCUrl)
                .params("param", mGson.toJson(post))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        BuyCartBack backDetail = mGson.fromJson(s, BuyCartBack.class);
                        if (backDetail == null) {
                            return;
                        }
                        if (backDetail.getReturn_code() != 0) {
                            Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                        }
                        else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                            
                            LogoutUtils.exitUser(activity);
                        }


                        else {
                            Toast.makeText(context, "已删除", Toast.LENGTH_SHORT).show();
                            orderShellModel.setDelete(true);
                            BuyShell.buyShell.refresh();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
