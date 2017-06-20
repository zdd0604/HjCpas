package com.hj.casps.commodity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hj.casps.R;
import com.hj.casps.base.FragmentBase;
import com.hj.casps.widget.ListPopupWindow;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by YaoChen on 2017/4/20.
 * 奥森学校素材库
 */

public class FragmentMyPic extends FragmentBase {
    RecyclerView recyclerView;
    View view;
    ListPopupWindow listPopupWindow;
    private LinearLayout addLevel0Layout;
    private EditText addLevel0Et;
    private Button addLevel0Bt;
    private LinearLayout levelListLayout;
    private final int RequestCodeForEdit = 1026;
    private final int RequestCodeForAddSub = 1025;
    private FancyButton addLevel0Bt2;
    private final int RequestCodeForAddLevel0 = 1027;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.frament_mypic, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listPopupWindow != null && listPopupWindow.isShowing()) {
            listPopupWindow.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCodeForEdit) {
                PictureLevelEntity levelEntity = data.getParcelableExtra(SelectPictureAddLibActivity.ExtraButtonObject);
                int position = data.getIntExtra(SelectPictureAddLibActivity.ExtraButtonPosition, -1);
                onEditLevel(levelEntity, position);
            } else if (requestCode == RequestCodeForAddSub) {
                PictureLevelEntity levelEntity = data.getParcelableExtra(SelectPictureAddLibActivity.ExtraButtonObject);
                int position = data.getIntExtra(SelectPictureAddLibActivity.ExtraButtonPosition, -1);
                onAddSubLevel(levelEntity, position);
            } else if (requestCode == RequestCodeForAddLevel0) {
                PictureLevelEntity levelEntity = data.getParcelableExtra(SelectPictureAddLibActivity.ExtraButtonObject);
                onAddLevel0(levelEntity);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_mypic_class_rv);
        addLevel0Layout = (LinearLayout) view.findViewById(R.id.fragment_mypic_empty_class_Ll);
        addLevel0Et = (EditText) view.findViewById(R.id.fragment_mypic_empty_class_Et);
        addLevel0Bt = (Button) view.findViewById(R.id.fragment_mypic_empty_class_btn);
        levelListLayout = (LinearLayout) view.findViewById(R.id.fragment_mypic_class_Ll);
        addLevel0Bt2 = (FancyButton) view.findViewById(R.id.fragment_mypic_class_add_Btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new PictureDividerItemDecoration(getActivity()));
        recyclerView.addOnItemTouchListener(new PictureSimpleItemClick());
        addLevel0Bt.setOnClickListener(new AddLevelClick());
        addLevel0Bt2.setOnClickListener(new AddLevelClick());
        levelListLayout.setVisibility(View.GONE);
    }

    @Override
    protected void loadData() {
        super.loadData();
       /* ArrayList<PictureLevelEntity> data = generateData();
        recyclerView.setAdapter(new PictureListAdapter(data));
        if(data
        */
        /*addLevel0Layout.setVisibility(View.VISIBLE);*/
    }

    private ArrayList<PictureLevelEntity> generateData() {
        ArrayList<PictureLevelEntity> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PictureLevelEntity lv0 = new PictureLevelEntity(i + "");
            lv0.setParent(null);
            for (int j = 0; j < 5; j++) {
                PictureLevelEntity lv1 = new PictureLevelEntity(i + "-" + j);
                lv1.setParent(lv0);
                for (int x = 0, size = 5; x < size; x++) {
                    PictureLevelEntity lv2 = new PictureLevelEntity(i + "-" + j + "-" + x);
                    lv1.addSubItem(lv2);
                    lv2.setParent(lv1);
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }

    /**
     * 未有子类的点击事件
     *
     * @param entity
     */
    private void onChildClick(PictureLevelEntity entity) {
        log("onChildClick() called with: " + "lv1 = [" + entity + "]");
        startActivity(new Intent(getActivity(), SelectPicture02.class));
    }

    private void onAddSubLevelClick(PictureLevelEntity levelEntity, int position) {
        Intent intent = new Intent(getActivity(), SelectPictureAddLibActivity.class);
        intent.putExtra(SelectPictureAddLibActivity.ExtraButtonName, "保存");
        intent.putExtra(SelectPictureAddLibActivity.ExtraButtonPosition, position);
        startActivityForResult(intent, RequestCodeForAddSub);
    }

    /**
     * @param levelEntity 新添加的
     * @param position    父目录的位置
     */
    private void onAddSubLevel(PictureLevelEntity levelEntity, int position) {
        if (position < 0) {
            return;
        }
        PictureListAdapter pictureListAdapter = (PictureListAdapter) recyclerView.getAdapter();
        PictureLevelEntity pictureLevelEntity = pictureListAdapter.getItem(position);
        pictureLevelEntity.addSubItem(levelEntity);
        if (pictureLevelEntity.getSubItems().size() == 1) {
            pictureListAdapter.notifyItemChanged(position);
            pictureLevelEntity.setExpanded(true);
        }
        int insertPosition = position + pictureLevelEntity.getSubItems().size();
        pictureListAdapter.getData().add(insertPosition, levelEntity);
        pictureListAdapter.notifyItemInserted(insertPosition);
    }


    private void onEditLevelClick(PictureListAdapter listAdapter, PictureLevelEntity levelEntity, int position) {
        Intent intent = new Intent(getActivity(), SelectPictureAddLibActivity.class);
        intent.putExtra(SelectPictureAddLibActivity.ExtraButtonName, "保存");
        intent.putExtra(SelectPictureAddLibActivity.ExtraButtonObject, levelEntity);
        intent.putExtra(SelectPictureAddLibActivity.ExtraLevelName, levelEntity.getName());
        intent.putExtra(SelectPictureAddLibActivity.ExtraButtonPosition, position);
        startActivityForResult(intent, RequestCodeForEdit);
    }

    private void onEditLevel(PictureLevelEntity levelEntity, int position) {
        if (position < 0) {
            return;
        }
        PictureListAdapter pictureListAdapter = (PictureListAdapter) recyclerView.getAdapter();
        PictureLevelEntity pictureLevelEntity = pictureListAdapter.getItem(position);
        pictureLevelEntity.setName(levelEntity.getName());
        pictureListAdapter.notifyItemChanged(position);
    }

    private void onAddLevel0Click() {
        Intent intent = new Intent(getActivity(), SelectPictureAddLibActivity.class);
        intent.putExtra(SelectPictureAddLibActivity.ExtraButtonName, "保存");
        startActivityForResult(intent, RequestCodeForAddLevel0);
    }

    private void onAddLevel0(PictureLevelEntity levelEntity) {
        PictureListAdapter pictureListAdapter = (PictureListAdapter) recyclerView.getAdapter();
        pictureListAdapter.addData(levelEntity);
    }

    private void onDelLevel(PictureListAdapter pictureListAdapter, PictureLevelEntity levelEntity, int position) {
        if (levelEntity.hasSubItem()) {
            /*多级目录怎么办*/
            toast("子目录不为空不能删除");
        } else {
            if (levelEntity.getParent() != null) {
                PictureLevelEntity parent = levelEntity.getParent();
                parent.removeSubItem(levelEntity);
                if (parent.getSubItems().size() == 0) {
                    /*需要更新当前组件*/
                    int parentPosition = pictureListAdapter.getParentPosition(levelEntity);
                    pictureListAdapter.notifyItemChanged(parentPosition);
                }
            }
            pictureListAdapter.getData().remove(levelEntity);
            pictureListAdapter.notifyItemRemoved(position);
        }
    }

    private class PictureListAdapter extends BaseQuickAdapter<PictureLevelEntity, BaseViewHolder> {

        public PictureListAdapter(List<PictureLevelEntity> data) {
            super(R.layout.activity_baseheader_left_item1, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, PictureLevelEntity item) {
            ImageView imageView = holder.getView(R.id.item_iv);
            TextView textView = holder.getView(R.id.item_name);
            textView.setText(item.getName());
            if (item.hasSubItem()) {
                textView.setTextColor(getResources().getColor(R.color.black));
                imageView.setImageResource(item.isExpanded() ? R.mipmap.jt3 : R.mipmap.jt1);
                imageView.setVisibility(View.VISIBLE);
            } else {
                textView.setTextColor(getResources().getColor(R.color.blue));
                imageView.setVisibility(View.GONE);
            }
            float paddingItem = getResources().getDisplayMetrics().density * 20;
            int leftPadding = (int) (paddingItem * (item.getLevel()));
            textView.setPadding(leftPadding, 0, 0, 0);

        }

    }

    /**
     * 左侧导航的适配器
     */
    public static class PictureLevelEntity extends AbstractExpandableItem implements Parcelable {
        private String name;
        private PictureLevelEntity parent;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PictureLevelEntity)) return false;

            PictureLevelEntity entity = (PictureLevelEntity) o;

            return name != null ? name.equals(entity.name) : entity.name == null;

        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        public PictureLevelEntity(String string) {
            this.name = string;
        }

        public PictureLevelEntity getParent() {
            return parent;
        }

        public void setParent(PictureLevelEntity parent) {
            this.parent = parent;
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (hasSubItem() && !expanded) {
                for (int j = 0, size = getSubItems().size(); j < size; j++) {
                    PictureLevelEntity levelChild = (PictureLevelEntity) getSubItems().get(j);
                    levelChild.setExpanded(false);
                }
            }
        }

        public String getName() {
            return name;
        }


        @Override
        public int getLevel() {
            return getLevelByParent(this);
        }

        @Override
        public void addSubItem(Object subItem) {
            super.addSubItem(subItem);
            if (subItem instanceof PictureLevelEntity) {
                PictureLevelEntity entity = (PictureLevelEntity) subItem;
                entity.parent = this;
            }
        }

        @Override
        public void addSubItem(int position, Object subItem) {
            super.addSubItem(position, subItem);
            if (subItem instanceof PictureLevelEntity) {
                PictureLevelEntity entity = (PictureLevelEntity) subItem;
                entity.setParent(this);
            }
        }

        /**
         * 根据父亲的级别获得Level
         *
         * @param entity
         * @return 从1开始
         */
        private static int getLevelByParent(PictureLevelEntity entity) {
            int t = 1;
            if (entity.getParent() != null) {
                t += getLevelByParent(entity.getParent());
            }
            return t;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeParcelable(this.parent, flags);
        }

        protected PictureLevelEntity(Parcel in) {
            this.name = in.readString();
            this.parent = in.readParcelable(PictureLevelEntity.class.getClassLoader());
        }

        public static final Parcelable.Creator<PictureLevelEntity> CREATOR = new Parcelable.Creator<PictureLevelEntity>() {
            @Override
            public PictureLevelEntity createFromParcel(Parcel source) {
                return new PictureLevelEntity(source);
            }

            @Override
            public PictureLevelEntity[] newArray(int size) {
                return new PictureLevelEntity[size];
            }
        };

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 二级目录不显示分隔线
     * 水平分隔线
     */
    private class PictureDividerItemDecoration extends RecyclerView.ItemDecoration {
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Drawable mDivider;

        public PictureDividerItemDecoration(Activity activity) {
            final TypedArray a = activity.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (checkLevelOne(view, parent)) {
                super.getItemOffsets(outRect, view, parent, state);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }

        /**
         * @param view
         * @param parent
         * @return true 应该绘制分隔线 一级菜单未展开的 二级菜单展开的最后一个
         */
        private boolean checkLevelOne(View view, RecyclerView parent) {
            int adapterPosition = parent.getChildAdapterPosition(view);
            PictureListAdapter adapter = (PictureListAdapter) parent.getAdapter();
            PictureLevelEntity levelEntity = adapter.getItem(adapterPosition);
            if (levelEntity != null) {
                /*一级元素 且收缩的*/
                if (levelEntity.hasSubItem() && !levelEntity.isExpanded()) {
                    return true;
                }
                /*二级元素 且最后一个元素时*/
                if (!levelEntity.hasSubItem() && levelEntity.getParent() != null) {
                    PictureLevelEntity levelEntityParent = levelEntity.getParent();
                    List subItems = levelEntityParent.getSubItems();
                    Object lastEntity = levelEntityParent.getSubItem(subItems.size() - 1);
                    if (levelEntity == lastEntity) {
                        return true;
                    }
                }

            }
            return false;
        }

        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                if (mDivider != null && checkLevelOne(child, parent)) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
            }
        }

    }

    private class PictureSimpleItemClick extends SimpleClickListener {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int pos) {
            log("onItemClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + pos + "]");
            PictureLevelEntity entity = (PictureLevelEntity) adapter.getItem(pos);
            if (!entity.hasSubItem()) {
                onChildClick(entity);
                return;
            } else {
                if (entity.isExpanded()) {
                    adapter.collapse(pos);
                } else {
                    adapter.expand(pos);
                }
            }
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            log("onItemLongClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + position + "]");
            FragmentActivity activity = getActivity();
            List<String> objects = new ArrayList<>();
            objects.add("编辑");
            objects.add("删除");
            objects.add("添加子分类");
            objects.add("取消");
            listPopupWindow = new ListPopupWindow(activity);
            listPopupWindow.setAdapter(new ArrayAdapter<>(activity, R.layout.simple_item_layout, R.id.item_name, objects));
            listPopupWindow.setOnItemClickListener(new PictureListPopupItemClick(listPopupWindow
                    , (PictureListAdapter) adapter, position));
            listPopupWindow.show();
        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            log("onItemChildClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + position + "]");
        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
            log("onItemChildLongClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + position + "]");
        }


    }

    /**
     * 添加一级分类的事件
     */
    private class AddLevelClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.fragment_mypic_empty_class_btn) {
                String addLevel0Str = addLevel0Et.getText().toString().trim();
                if (TextUtils.isEmpty(addLevel0Str)) {
                    toast("分类名称不能为空");
                    return;
                }
                PictureLevelEntity pictureLevelEntity = new PictureLevelEntity(addLevel0Str);
                List<PictureLevelEntity> data = new ArrayList<>();
                data.add(pictureLevelEntity);
                addLevel0Layout.setVisibility(View.GONE);
                levelListLayout.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(new PictureListAdapter(data));
                /*网络请求添加*/
            } else if (id == R.id.fragment_mypic_class_add_Btn) {
                onAddLevel0Click();
            }
        }
    }


    private class PictureListPopupItemClick implements AdapterView.OnItemClickListener {
        ListPopupWindow listPopupWindow;
        PictureLevelEntity levelEntity;
        PictureListAdapter listAdapter;
        int levelAdapterPosition;

        public PictureListPopupItemClick(ListPopupWindow listPopupWindow, PictureListAdapter adapter, int levelAdapterPosition) {
            this.levelEntity = adapter.getItem(levelAdapterPosition);
            this.levelAdapterPosition = levelAdapterPosition;
            this.listAdapter = adapter;
            this.listPopupWindow = listPopupWindow;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                onEditLevelClick(listAdapter, levelEntity, levelAdapterPosition);
            } else if (position == 1) {
                onDelLevel(listAdapter, levelEntity, levelAdapterPosition);
            } else if (position == 2) {
                onAddSubLevelClick(levelEntity, levelAdapterPosition);
            }
            if (listPopupWindow != null && listPopupWindow.isShowing()) {
                listPopupWindow.dismiss();
            }
        }

    }
}
