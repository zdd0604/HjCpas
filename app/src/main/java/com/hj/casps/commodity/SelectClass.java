package com.hj.casps.commodity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.goodsmanager.goodsmanagerCallBack.GoodsCategoryCallBack;
import com.hj.casps.entity.goodsmanager.request.RequestPub;
import com.hj.casps.entity.goodsmanager.response.GoodsCategoryEntity;
import com.hj.casps.entity.goodsmanager.response.NoteEntity;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.GOODS_ID;
import static com.hj.casps.common.Constant.GOODS_NAME;

/**
 * Created by YaoChen on 2017/4/13.
 */

public class SelectClass extends ActivityBaseHeader2 implements View.OnClickListener {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    //获取当前用户会员的所有使用的商品分类（出这个会员的商品目录结构）
                    refrushUi();
                    break;
            }

        }
    };

    //更新UI
    private void refrushUi() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<SelectClass.GoodLevelEntity> dataList = generateData1();
        //如果数据为空，就跳到添加数据页面
        recyclerView.setAdapter(new SelectClass.GoodListAdapter(dataList));
        recyclerView.addItemDecoration(new SelectClass.GoodDividerItemDecoration(SelectClass.this));
        recyclerView.addOnItemTouchListener(new SelectClass.GoodSimpleItemClick());
    }

    private List<NoteEntity> categoryList;
    private int fra_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectclass);
        FancyButton addGoods = (FancyButton) findViewById(R.id.layout_head_left_btn);
        addGoods.setOnClickListener(this);
        setTitle(getString(R.string.select_class));
        setTitleRight(null, null);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    //初始化数据
    private void initData() {
        fra_type = getIntent().getIntExtra("fra", 0);
        PublicArg p = Constant.publicArg;
        RequestPub r = new RequestPub(p.getSys_token(), Constant.getUUID(), Constant.SYS_FUNC, p.getSys_user(), p.getSys_member());
        String param = mGson.toJson(r);
        OkGo.post(Constant.GetAllCategoryUrl)
                .params("param", param)
                .execute(new GoodsCategoryCallBack<GoodsCategoryEntity<List<NoteEntity>>>() {
                    @Override
                    public void onSuccess(GoodsCategoryEntity<List<NoteEntity>> listGoodsCategoryEntity, Call call, Response response) {
                        super.onSuccess(listGoodsCategoryEntity, call, response);
                        if (listGoodsCategoryEntity != null) {
                            categoryList = listGoodsCategoryEntity.categoryList;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        if (Constant.public_code){
                            LogoutUtils.exitUser(SelectClass.this);
                        }
                    }
                });


    }

    private void initView() {

    }

    private ArrayList<SelectClass.GoodLevelEntity> generateData1() {
        ArrayList<SelectClass.GoodLevelEntity> res = new ArrayList<>();
//        if(entityList!=null&&entityList.size()>0)
        for (int i = 0; i < categoryList.size(); i++) {
            SelectClass.GoodLevelEntity lv0 = new SelectClass.GoodLevelEntity(categoryList.get(i).getCategoryName(), categoryList.get(i).getCategoryId());
            for (int j = 0; j < categoryList.get(i).getNodes().size(); j++) {
                SelectClass.GoodLevelEntity lv1 = new SelectClass.GoodLevelEntity(categoryList.get(i).getNodes().get(j).getCategoryName(), categoryList.get(i).getNodes().get(j).getCategoryId());
                for (int x = 0; x < categoryList.get(i).getNodes().get(j).getNodes().size(); x++) {
                    SelectClass.GoodLevelEntity lv2 = new SelectClass.GoodLevelEntity(categoryList.get(i).getNodes().get(j).getNodes().get(x).getCategoryName(), categoryList.get(i).getNodes().get(j).getNodes().get(x).getCategoryId());
                    lv1.addSubItem(lv2);
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }

    private void onItemClick1(SelectClass.GoodLevelEntity lv1) {
        log("onItemClick() called with: " + "lv1 = [" + lv1 + "]");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            //添加商品
//            case R.id.layout_head_left_btn:
//                Intent intent =new Intent(SelectClass.this,ActivityEditGoods.class);
//                intent.putExtra(Constant.INTENTISADDGOODS,true);
//                startActivity(intent);
//                break;

        }
    }

    private class GoodListAdapter extends BaseMultiItemQuickAdapter<SelectClass.GoodLevelEntity, BaseViewHolder> {
        public static final int TYPE_LEVEL_PARENT = 0; /*有子类*/
        public static final int TYPE_LEVEL_CHILD = 1;/*无子类*/
        float paddingLeftUnit;

        public float getPaddingLeftUnit() {
            return paddingLeftUnit;
        }

        public void setPaddingLeftUnit(float paddingLeftUnit) {
            this.paddingLeftUnit = paddingLeftUnit;
        }

        public GoodListAdapter(List<SelectClass.GoodLevelEntity> data) {
            super(data);
            paddingLeftUnit = getResources().getDisplayMetrics().density * 20;
            addItemType(TYPE_LEVEL_PARENT, R.layout.activity_manager_good_item);
            addItemType(TYPE_LEVEL_CHILD, R.layout.activity_manager_good_item);
        }

        @Override
        protected void convert(BaseViewHolder holder, SelectClass.GoodLevelEntity item) {
            switch (holder.getItemViewType()) {
                case TYPE_LEVEL_PARENT:
                    convertLevelParent(holder, item);
                    break;
                case TYPE_LEVEL_CHILD:
                    convertLevelChild(holder, item);
                    break;
            }
        }

        /*有子列的*/
        private void convertLevelParent(final BaseViewHolder holder, final SelectClass.GoodLevelEntity entity) {
            holder.setText(R.id.item_name, entity.getName());
            holder.getView(R.id.item_name).setPadding((int) (entity.getLevel() * paddingLeftUnit), 0, 0, 0);
            holder.getView(R.id.item_iv).setVisibility(View.VISIBLE);
            holder.setImageResource(R.id.item_iv, entity.isExpanded() ? R.mipmap.jt3 : R.mipmap.jt1);
        }


        /*无子列的*/
        private void convertLevelChild(final BaseViewHolder holder, final SelectClass.GoodLevelEntity entity) {
            holder.setText(R.id.item_name, entity.getName());
            holder.getView(R.id.item_iv).setVisibility(View.GONE);
            holder.getView(R.id.item_name).setPadding((int) (entity.getLevel() * paddingLeftUnit), 0, 0, 0);
        }

    }

    /**
     * 左侧导航的适配器
     * 注意 hascode() equals() 方法，
     * 在点击item会在列表中删除，equal的法，会误删除，确保同一级中不要有相同的name
     */
    public class GoodLevelEntity extends AbstractExpandableItem<SelectClass.GoodLevelEntity> implements MultiItemEntity {
        private String name;
        private SelectClass.GoodLevelEntity parent;
        private double i = Math.random();
        private String categoryId;

        public GoodLevelEntity(String name, String categoryId) {
            this.name = name;
            this.categoryId = categoryId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public double getI() {
            return i;
        }

        public void setI(int i) {
            this.i = Math.random();
        }

        /**
         * @param string
         */
        public GoodLevelEntity(String string) {
            this.name = string;
        }


        public SelectClass.GoodLevelEntity getParent() {
            return parent;
        }

        @Override
        public void addSubItem(int position, SelectClass.GoodLevelEntity subItem) {
            super.addSubItem(position, subItem);
            subItem.parent = this;
        }

        @Override
        public void addSubItem(SelectClass.GoodLevelEntity subItem) {
            super.addSubItem(subItem);
            subItem.parent = this;
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (hasSubItem() && !expanded) {
                for (int j = 0, size = getSubItems().size(); j < size; j++) {
                    SelectClass.GoodLevelEntity levelChild = (SelectClass.GoodLevelEntity) getSubItems().get(j);
                    levelChild.setExpanded(false);
                }
            }
        }

        public String getName() {
            return name;
        }

        /**
         * 0为第一级 以次后排
         *
         * @return
         */
        @Override
        public int getLevel() {
            return getLevel2(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SelectClass.GoodLevelEntity)) return false;

            SelectClass.GoodLevelEntity that = (SelectClass.GoodLevelEntity) o;

            if (Double.compare(that.i, i) != 0) return false;
            return name.equals(that.name);

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = name.hashCode();
            temp = Double.doubleToLongBits(i);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        private int getLevel2(SelectClass.GoodLevelEntity entity) {
            int level = 1;
            if (entity.getParent() != null) {
                level += getLevel2(entity.getParent());
            }
            return level;
        }

        @Override
        public int getItemType() {
            return hasSubItem() ? SelectClass.GoodListAdapter.TYPE_LEVEL_PARENT : SelectClass.GoodListAdapter.TYPE_LEVEL_CHILD;
        }

    }

    /**
     * 二级目录不显示分隔线
     * 水平分隔线
     */
    private class GoodDividerItemDecoration extends RecyclerView.ItemDecoration {
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Drawable mDivider;

        public GoodDividerItemDecoration(Activity activity) {
            final TypedArray a = activity.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            log("getItemOffsets() called with: " + "outRect = [" + outRect + "], view = [" + view + "], parent = [" + parent + "], state = [" + state + "]");
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }


        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            log("onDraw() called with: " + "canvas = [" + canvas + "], parent = [" + parent + "], state = [" + state + "]");
            if (mDivider == null) {
                return;
            }
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            SelectClass.GoodListAdapter goodListAdapter = (SelectClass.GoodListAdapter) parent.getAdapter();
            float paddingLeftUnit = goodListAdapter.getPaddingLeftUnit();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                int adapterPosition = parent.getChildAdapterPosition(child);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                int drawableLeft = (int) (left + paddingLeftUnit * goodListAdapter.getItem(adapterPosition).getLevel());
                mDivider.setBounds(drawableLeft - 10, top, (int) (right - paddingLeftUnit * 2), bottom);
                mDivider.draw(canvas);
            }
        }

        /**
         * @param view
         * @param parent
         * @return true 应该绘制分隔线 一级菜单未展开的 二级菜单展开的最后一个
         */
        private boolean checkLevelOne(View view, RecyclerView parent) {
            int adapterPosition = parent.getChildAdapterPosition(view);
            SelectClass.GoodListAdapter adapter = (SelectClass.GoodListAdapter) parent.getAdapter();
            SelectClass.GoodLevelEntity levelEntity = adapter.getItem(adapterPosition);
            if (levelEntity != null) {
                /*一级元素 且收缩的*/
                if (levelEntity.hasSubItem() && !levelEntity.isExpanded()) {
                    return true;
                }
                /*二级元素 且最后一个元素时*/
                if (!levelEntity.hasSubItem() && levelEntity.getParent() != null) {
                    SelectClass.GoodLevelEntity levelEntityParent = levelEntity.getParent();
                    List subItems = levelEntityParent.getSubItems();
                    Object lastEntity = levelEntityParent.getSubItem(subItems.size() - 1);
                    if (levelEntity == lastEntity) {
                        return true;
                    }
                }

            }
            return false;
        }


    }

    private class GoodSimpleItemClick extends SimpleClickListener {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int pos) {
            log("onItemClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + pos + "]");
            SelectClass.GoodLevelEntity entity = (SelectClass.GoodLevelEntity) adapter.getItem(pos);
            //如果没有子条目就跳转到选择图片的页面
            if (!entity.hasSubItem()) {
                Intent intent;
                if (fra_type == 1) {
                    GOODS_ID = entity.getCategoryId();
                    GOODS_NAME = entity.getName();
                    setResult(22);
                } else {
                    intent = new Intent(SelectClass.this, ActivityEditGoods.class);
                    onItemClick1(entity);
                    intent.putExtra(Constant.INTENT_GOODSNAME, entity.getName());
                    intent.putExtra(Constant.INTENT_GOODCATEGORYID, entity.getCategoryId());
                    setResult(RESULT_OK, intent);
                }

////                intentActivity(SelectClass.this, ActivityGoodsClass.class);
//                Intent intent=new Intent(SelectClass.this, ActivityGoodsClass.class);
//                intent.putExtra(Constant.CATEGORY_ID,entity.getCategoryId());
//                startActivity(intent);
                SelectClass.this.finish();
                return;

            }
            if (entity.isExpanded()) {
                collapse(adapter, entity);
            } else {
                expand(pos, adapter, entity);
            }
        }


        /**
         * 收缩 收缩当前item 及其子item
         *
         * @param adapter
         * @param entity
         */
        private void collapse(BaseQuickAdapter adapter, SelectClass.GoodLevelEntity entity) {
            List<SelectClass.GoodLevelEntity> needDelete = new ArrayList<>();
            collapseNeedDelete(needDelete, entity.getSubItems());
            entity.setExpanded(false);
            adapter.getData().removeAll(needDelete);
            adapter.notifyDataSetChanged();
        }

        /**
         * @param result
         * @param subItems 全部删除 包括子列
         */
        private void collapseNeedDelete(List<SelectClass.GoodLevelEntity> result, List<SelectClass.GoodLevelEntity> subItems) {
            for (SelectClass.GoodLevelEntity item : subItems) {
                result.add(item);
                boolean hasSubItem = item.hasSubItem();
                if (hasSubItem && item.isExpanded()) {
                    item.setExpanded(false);
                    collapseNeedDelete(result, item.getSubItems());
                }
            }
        }

        /**
         * 伸开 收缩与其同级的及其同级的子目录
         *
         * @param pos
         * @param adapter
         * @param entity
         */
        private void expand(int pos, BaseQuickAdapter adapter, SelectClass.GoodLevelEntity entity) {
            adapter.getData().addAll(pos + 1, entity.getSubItems()); /*要先添加 要不数据会乱掉*/
            List<SelectClass.GoodLevelEntity> needDelete = new ArrayList<>();
            if (entity.getParent() == null) {
                List<SelectClass.GoodLevelEntity> level0List = getLevel0List(adapter.getData());
                expandNeedDelete(needDelete, level0List);
            } else {
                expandNeedDelete(needDelete, entity.getParent().getSubItems());
            }
            adapter.getData().removeAll(needDelete);
            entity.setExpanded(true);/*不能在expandNeedDelete否则把将当前也加到删除列表了*/
            adapter.notifyDataSetChanged();
        }

        /**
         * 获得顶级列表
         *
         * @param data
         * @return
         */
        private List<SelectClass.GoodLevelEntity> getLevel0List(List<SelectClass.GoodLevelEntity> data) {
            List<SelectClass.GoodLevelEntity> result = new ArrayList<>();
            for (SelectClass.GoodLevelEntity item : data) {
                if (item.getParent() == null) {
                    result.add(item);
                }
            }
            return result;
        }

        /**
         * 获得待从列表中删除的item
         *
         * @param result
         * @param subItems
         */
        private void expandNeedDelete(List<SelectClass.GoodLevelEntity> result, List<SelectClass.GoodLevelEntity> subItems) {
            for (SelectClass.GoodLevelEntity item : subItems) {
                boolean hasSubItem = item.hasSubItem();
                if (item.isExpanded() && hasSubItem) { /*有子类且展开的*/
                    result.addAll(item.getSubItems());
                    expandNeedDelete(result, item.getSubItems());
                    item.setExpanded(false);  /*要加expandNeedDelete之后 注意顺序否则会册不掉，因为会将子列也没为false*/
                }
            }
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            log("onItemLongClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + position + "]");
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


}
