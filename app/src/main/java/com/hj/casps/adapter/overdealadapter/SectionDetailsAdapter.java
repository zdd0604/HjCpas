package com.hj.casps.adapter.overdealadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.ViewHolderTitle;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appsettle.QuerysettleDetailOneGain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zdd
 */

public class SectionDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int type_one = 1;
    public static final int type_two = 2;
    public static final int type_three = 3;

    private Context context;

    private LayoutInflater layoutInflater;
    private List<QuerysettleDetailOneGain> listOne;
    private List<QuerysettleDetailOneGain.ListBean> listTwo;
    private List<String> listTitle;
    private List<Integer> types = new ArrayList<>();
    private Map<Integer, Integer> mPositions = new HashMap<>();

    public SectionDetailsAdapter(Context context,
                                 List<QuerysettleDetailOneGain> listOne,
                                 List<QuerysettleDetailOneGain.ListBean> listTwo,
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
                return new ViewHolderTypeOne(layoutInflater.inflate(R.layout.rl_bills_details_item, parent, false));
            case Constant.type_two:
                return new ViewHolderTypeThree(layoutInflater.inflate(R.layout.viewholder_three, parent, false));
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
                ((ViewHolderTypeOne) holder).bindViewHolder(listOne.get(realPosition),position);
                break;
            case Constant.type_two:
                ((ViewHolderTypeThree) holder).bindViewHolder(listTwo.get(realPosition),position);
                break;
            case Constant.type_three:
                ((ViewHolderTitle) holder).bindViewHolder(listTitle.get(realPosition),position);
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
