package com.hj.casps.adapter.payadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hj.casps.entity.paymentmanager.response.ResponseQueryPayEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public class PublicAdapter extends BaseAdapter{

    private List<String> accountlist;

    public PublicAdapter(List<String> accountlist) {

        this.accountlist = accountlist;
    }

    @Override
    public int getCount() {
        return accountlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        textView.setTextSize(18);
        textView.setText(accountlist.get(position));
        return textView;
    }
}
