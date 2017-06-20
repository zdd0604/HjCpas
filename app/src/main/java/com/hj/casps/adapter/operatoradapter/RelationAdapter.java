package com.hj.casps.adapter.operatoradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.hj.casps.R;
import com.hj.casps.entity.appUser.ToEditUserPageAllRoles;

import java.util.List;

/**
 * Created by zdd
 */

public class RelationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private LayoutInflater layoutInflater;
    private List<ToEditUserPageAllRoles> mList;

    public RelationAdapter(Context context, List<ToEditUserPageAllRoles> mList) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.mList = mList;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolderRelation) holder).bindViewHolder(mList.get(position).getRoleName(), position);
        final CheckBox checkBox = (CheckBox) holder.itemView.findViewById(R.id.relation_ck);
        checkBox.setChecked(mList.get(position).isHasRole());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(position).setHasRole(checkBox.isChecked());
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderRelation(layoutInflater.inflate(R.layout.relation_item, parent, false));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

}
