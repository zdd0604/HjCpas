package com.hj.casps.adapter.payadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hj.casps.ui.CLEditText;


/**
 * Created by zdd on 2016/10/12.
 */

public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConverView;

    //显示图片的配置
//    DisplayImageOptions options = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.mipmap.notfound)               //加载图片时的图片
//            .showImageForEmptyUri(R.mipmap.ic_launcher)             //没有图片资源时的默认图片
//            .showImageOnFail(R.mipmap.lose)                  //加载失败时的图片
//            .cacheInMemory(true)                                    //启用内存缓存
//            .cacheOnDisk(true)                                      //启用SD卡存缓存
//            .bitmapConfig(Bitmap.Config.RGB_565)
//            .build();

    public ViewHolder(Context context, ViewGroup viewGroup, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConverView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        mConverView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder hooder = (ViewHolder) convertView.getTag();
            hooder.mPosition = position;
            return hooder;
        }
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public View getComverView() {
        return mConverView;
    }

    //listview的布局空间，如果有图片就添加图片的控件以此类推

    /**
     * 文字显示的控件
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tx = getView(viewId);
        tx.setText(text);
        return this;
    }

    /**
     * 文字显示的控件
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setTextInt(int viewId, int text) {
        TextView tx = getView(viewId);
        tx.setText(text);
        return this;
    }

    /**
     * 文字显示的控件
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setEdiTextView(int viewId, String text) {
        EditText tx = getView(viewId);
        tx.setText(text);
        return this;
    }

    /**
     * 文字显示的控件
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setCLEditTextView(int viewId, String text) {
        CLEditText tx = getView(viewId);
        tx.setText(text);
        return this;
    }
    /**
     * 通过网络设置照片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setHttpUrlImage(int viewId, String url) {
        ImageView imageView = getView(viewId);
//        ImageLoader.getInstance().displayImage(url, imageView, options);
        return this;
    }

    public ViewHolder setCheckBox(int viewId,Boolean  flag){
        CheckBox cb= getView(viewId);
        cb.setChecked(flag);
        return this;
    }
}
