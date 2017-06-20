package com.hj.casps.quotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.hj.casps.common.Constant.stmpToDate;

/**
 * Created by zy on 2017/4/20.
 * 报价管理的适配器
 */

public class QuotesAdapter extends WZYBaseAdapter<QuoteModel> {
    private Context context;
    private Activity a;

    public QuotesAdapter(List<QuoteModel> data, Context context, int layoutRes, Activity a) {
        super(data, context, layoutRes);
        this.context = context;
        this.a = a;
    }

    @Override
    public void bindData(ViewHolder holder, final QuoteModel quoteModel, final int indexPos) {
        ImageView quotes_prompt = (ImageView) holder.getView(R.id.quotes_prompt);
        if (quoteModel.getStatus() == 0 || quoteModel.getStatus() == 1) {
            quotes_prompt.setVisibility(View.GONE);
        } else {
            quotes_prompt.setVisibility(View.VISIBLE);

        }
        TextView quotes_query_no = (TextView) holder.getView(R.id.quotes_query_no);
        quotes_query_no.setText(quoteModel.getId());
        TextView quotes_name = (TextView) holder.getView(R.id.quotes_name);
        quotes_name.setText(quoteModel.getGoodsName());
        TextView quotes_person = (TextView) holder.getView(R.id.quotes_person);
        quotes_person.setText(quoteModel.getPublishName());
        TextView quotes_query_quantity = (TextView) holder.getView(R.id.quotes_query_quantity);
        if (quoteModel.getNum().contains(".")) {
            String[] a = quoteModel.getNum().split("\\.");
            quotes_query_quantity.setText(a[0]);
        } else {
            quotes_query_quantity.setText(quoteModel.getNum());
        }

        TextView quotes_query_status = (TextView) holder.getView(R.id.quotes_query_status);
        switch (quoteModel.getRangType()) {
            case 0:
                quotes_query_status.setText("公开报价");
                quotes_query_status.setBackgroundColor(context.getResources().getColor(R.color.light_yellow));
                break;
            case 1:
                quotes_query_status.setText("指定报价");
                quotes_query_status.setBackgroundColor(context.getResources().getColor(R.color.red));
                break;
            default:
                quotes_query_status.setText("未发布");
                quotes_query_status.setBackgroundColor(context.getResources().getColor(R.color.text_color_blue2));
                break;
        }

        TextView quotes_query_price = (TextView) holder.getView(R.id.quotes_query_price);
        quotes_query_price.setText(quoteModel.getMinPrice() + "-" + quoteModel.getMaxPrice());
        TextView quotes_query_period = (TextView) holder.getView(R.id.quotes_query_period);

        quotes_query_period.setText(stmpToDate(quoteModel.getStartTime()) + "至" + stmpToDate(quoteModel.getEndTime()));
        FancyButton quote_assign_Btn = (FancyButton) holder.getView(R.id.quote_assign_Btn);
        quote_assign_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MmbActivity.class);
                intent.putExtra("quoteId", quoteModel.getId());
                intent.putExtra("type", quoteModel.getType());
                intent.putExtra("RangType", quoteModel.getRangType());
                a.startActivityForResult(intent, 11);

            }
        });
        FancyButton quote_edit_Btn = (FancyButton) holder.getView(R.id.quote_edit_Btn);
        quote_edit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, CreateQuotes.class);
                intent.putExtra("data", quoteModel.getId());//将报价ID传到编辑报价页面
//                intent.putExtra("name", quoteModel.getGoodsName());
//                intent.putExtra("number", quoteModel.getNum());
//                intent.putExtra("price", quoteModel.getMinPrice());
//                intent.putExtra("period", quoteModel.getStartTime());
                a.startActivityForResult(intent, 11);

            }
        });
    }
}
