package com.hj.casps.adapter.expressadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hj.casps.R;
import com.hj.casps.entity.appordergoods.WarehouseEntity;

import java.util.List;

/**
 * Created by zdd on 2017/4/17.
 * 收发货地址
 */

public class ExpressAdapte extends BaseAdapter {
    private List<WarehouseEntity> mList;
    private Context context;
    public static onClickItem onClickItem;

    public static void setOnClickItem(ExpressAdapte.onClickItem onClickItem) {
        ExpressAdapte.onClickItem = onClickItem;
    }

    public ExpressAdapte(List<WarehouseEntity> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.card_bills_items, parent, false);
            viewHodler.tv_account_name_title = (TextView) convertView.findViewById(R.id.tv_account_name_title);
            viewHodler.account_name = (TextView) convertView.findViewById(R.id.ed_account_name);

            viewHodler.tv_account_number_title = (TextView) convertView.findViewById(R.id.tv_account_number_title);
            viewHodler.account_number = (TextView) convertView.findViewById(R.id.ed_account_number);

            viewHodler.tv_bank_name_title = (TextView) convertView.findViewById(R.id.tv_bank_name_title);
            viewHodler.bank_name = (TextView) convertView.findViewById(R.id.ed_bank_name);

            viewHodler.tv_contacts_name_title = (TextView) convertView.findViewById(R.id.tv_contacts_name_title);
            viewHodler.contacts_name = (TextView) convertView.findViewById(R.id.ed_contacts_name);

            viewHodler.tv_mobile_number_title = (TextView) convertView.findViewById(R.id.tv_mobile_number_title);
            viewHodler.mobile_number = (TextView) convertView.findViewById(R.id.ed_mobile_number);

            viewHodler.tv_telephone_number_title = (TextView) convertView.findViewById(R.id.tv_telephone_number_title);
            viewHodler.telephone_number = (TextView) convertView.findViewById(R.id.ed_telephone_number);

            viewHodler.item_card_edit = (RelativeLayout) convertView.findViewById(R.id.item_card_edit);
            viewHodler.item_card_delete = (RelativeLayout) convertView.findViewById(R.id.item_card_delete);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        viewHodler.tv_account_name_title.setText(R.string.hint_tv_express_address_title);
        viewHodler.tv_account_number_title.setText(R.string.hint_tv_express_area_title);
        viewHodler.tv_bank_name_title.setText(R.string.hint_tv_express_postcode_title);
        viewHodler.tv_contacts_name_title.setText(R.string.hint_tv_express_contacts_title);
        viewHodler.tv_mobile_number_title.setText(R.string.hint_tv_express_mobile_title);
        viewHodler.tv_telephone_number_title.setText(R.string.hint_tv_express_phone_title);

//        viewHodler.account_name.setHint(R.string.hint_ed_express_address_title);
//        viewHodler.account_number.setHint(R.string.hint_ed_express_area_title);
//        viewHodler.bank_name.setHint(R.string.hint_ed_express_postcode_title);
//        viewHodler.contacts_name.setHint(R.string.hint_ed_express_contacts_title);
//        viewHodler.mobile_number.setHint(R.string.hint_ed_express_mobile_title);
//        viewHodler.telephone_number.setHint(R.string.hint_ed_express_phone_title);

        viewHodler.account_name.setText(mList.get(position).getAddress());
        viewHodler.account_number.setText(mList.get(position).getAreaDesc());
        viewHodler.bank_name.setText(mList.get(position).getZipcode());
        viewHodler.contacts_name.setText(mList.get(position).getContact());
        viewHodler.mobile_number.setText(mList.get(position).getMobilephone());
        viewHodler.telephone_number.setText(mList.get(position).getPhone());
        viewHodler.item_card_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem != null) {
                    onClickItem.onCilckItemEdit(position);
                }
            }
        });

        viewHodler.item_card_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem != null) {
                    onClickItem.onClickItemDelete(position);
                }
            }
        });
        return convertView;
    }

    class ViewHodler {
        TextView tv_account_name_title;
        TextView tv_account_number_title;
        TextView tv_bank_name_title;
        TextView tv_contacts_name_title;
        TextView tv_mobile_number_title;
        TextView tv_telephone_number_title;

        TextView account_name;
        TextView account_number;
        TextView bank_name;
        TextView contacts_name;
        TextView mobile_number;
        TextView telephone_number;
        RelativeLayout item_card_edit;
        RelativeLayout item_card_delete;
    }

    public interface onClickItem {
        void onCilckItemEdit(int pos);

        void onClickItemDelete(int pos);
    }

    public void refreshList(List<WarehouseEntity> list) {
        for (int i=0;i<list.size();i++){
            this.mList.add(list.get(i));
        }
        this.notifyDataSetChanged();
    }

}
