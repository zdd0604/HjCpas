package com.hj.casps.quotes.wyt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.ViewHolderTitle;
import com.hj.casps.quotes.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */

public class PriceQuoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    List<QtListEntity> list = new ArrayList<>();

    public PriceQuoteAdapter(Context context, List<QtListEntity> list) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        if (list != null) {
            this.list = list;

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.pricesearch_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindViewHolder(list.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDatas(List<QtListEntity> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addRes(List<QtListEntity> list) {
        if(list!=null&&list.size()>0){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }


}
