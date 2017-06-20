package com.hj.casps.adapter.goodmanager;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.CommonAdapter;
import com.hj.casps.adapter.payadapter.ViewHolder;
import com.hj.casps.entity.goodsmanager.response.DataListBean;
import com.hj.casps.entity.goodsmanager.response.ResSearchGoodEntity;
import com.hj.casps.quotes.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public class GoodClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    List<DataListBean> list = new ArrayList<>();

    public GoodClassAdapter(Context context, List<DataListBean> list) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        if (list != null) {
            this.list = list;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new GoodViewHolder(layoutInflater.inflate(R.layout.selectpic_griditem, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GoodViewHolder) holder).bindViewHolder(list.get(position), position);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDatas(List<DataListBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void addRes(List<DataListBean> list) {
       if(list!=null&&list.size()>0){
           this.list.addAll(list);
       }
        notifyDataSetChanged();
    }
    public List<DataListBean> getDatas(){

        return this.list;
    }
}
