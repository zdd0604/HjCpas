package com.hj.casps.adapter.ordermanageradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.ViewHolderTitle;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appordermoney.QueryPayMoneyOrderForSettleEntity;
import com.hj.casps.ordermanager.OrderShellModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zdd
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int type_one = 1;
    public static final int type_two = 2;
    public static final int type_three = 3;
    private Context context;
    private LayoutInflater layoutInflater;
    private List listOne;
    private List<OrderShellModel> listTwo;
    private List<String> listTitle;
    private List<Integer> types = new ArrayList<>();
    private Map<Integer, Integer> mPositions = new HashMap<>();

    public OrderDetailsAdapter(Context context, List listOne,
                               List<OrderShellModel> listTwo,
                               List<String> listTitle) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.listOne = listOne;
        this.listTwo = listTwo;
        this.listTitle = listTitle;

        addlistBytype(type_one, listOne);
        addlistBytype(type_three, listTitle);
        addlistBytype(type_two, listTwo);
    }

    public void addlistBytype(int type, List list) {
        mPositions.put(type, types.size());
        for (int i = 0; i < list.size(); i++) {
            types.add(type);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constant.type_one:
                return new ViewHolderOrderlOne(layoutInflater.inflate(R.layout.order_layout_one, parent, false));
            case Constant.type_two:
                return new ViewHolderOrderTwo(layoutInflater.inflate(R.layout.order_layout_three, parent, false));
            case Constant.type_three:
                return new ViewHolderTitle(layoutInflater.inflate(R.layout.rl_bills_title_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int intemtype = getItemViewType(position);
        int realPosition = position - mPositions.get(intemtype);
        switch (intemtype) {
            case Constant.type_one:
                ((ViewHolderOrderlOne) holder).bindViewHolder(listOne.get(realPosition), position);
                break;
            case Constant.type_two:
                ((ViewHolderOrderTwo) holder).bindViewHolder(listTwo.get(realPosition), position);
                break;
            case Constant.type_three:
                ((ViewHolderTitle) holder).bindViewHolder(listTitle.get(realPosition), position);
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

}
