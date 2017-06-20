package com.hj.casps.widget;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.hj.casps.R;

/**
 * Created by 鑫 Administrator on 2017/4/20.
 */

public class ListPopupWindow extends PopupWindow {
    private final Activity context;
    private ListView listView;

    public ListPopupWindow(Activity context) {
        this.context = context;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        View inflate = LayoutInflater.from(context).inflate(R.layout.simple_list_layout, null);
        listView = (ListView) inflate.findViewById(R.id.list);
        inflate.findViewById(R.id.pop_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(inflate);
    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }

    public void setAdapter(ListAdapter adapter) {
        listView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        listView.setOnItemClickListener(onItemClickListener);
    }
}
