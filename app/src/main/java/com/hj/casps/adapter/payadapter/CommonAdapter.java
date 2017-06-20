package com.hj.casps.adapter.payadapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zdd on 2016/10/12.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mLayoutId;
    public Calendar c = Calendar.getInstance();

    public CommonAdapter(Context context, List<T> datas, int lauoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mLayoutId = lauoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder hooder = ViewHolder.get(mContext, view, viewGroup, mLayoutId, i);
        concert(hooder, getItem(i), i);
        return hooder.getComverView();
    }

    public abstract void concert(ViewHolder hooder, T t, int indexPos);

    public void refreshList(List<T> datas) {
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void addRes(List<T> datas) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                this.mDatas.add(datas.get(i));
            }
        }
        this.notifyDataSetChanged();
    }

    public List<T> getmDatas() {
        return this.mDatas;
    }

    public void setmDatas(List<T> list) {
        this.mDatas.clear();
        this.mDatas.addAll(list);
        notifyDataSetChanged();

    }


    public void showCalendar(final EditText editText) {
//        c = Calendar.getInstance();
        new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        if (month < 10 && dayOfMonth < 10) {
                            editText.setText(year + "-0" + month
                                    + "-0" + dayOfMonth);
                        } else if (month < 10 && dayOfMonth >= 10) {
                            editText.setText(year + "-0" + month
                                    + "-" + dayOfMonth);
                        } else if (month >= 10 && dayOfMonth < 10) {
                            editText.setText(year + "-" + month
                                    + "-0" + dayOfMonth);
                        } else {
                            editText.setText(year + "-" + month
                                    + "-" + dayOfMonth);
                        }

                    }
                }
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH)).show();
        editText.setCompoundDrawables(null, null, null, null);
    }
}
